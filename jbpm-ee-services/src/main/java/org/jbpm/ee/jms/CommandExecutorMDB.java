package org.jbpm.ee.jms;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.persistence.EntityManager;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.drools.core.command.impl.GenericCommand;
import org.drools.core.command.runtime.process.GetProcessInstanceCommand;
import org.jbpm.ee.exception.CommandException;
import org.jbpm.ee.exception.InactiveProcessInstance;
import org.jbpm.ee.services.ejb.startup.KnowledgeManagerBean;
import org.jbpm.ee.support.KieReleaseId;
import org.jbpm.ee.services.model.ProcessInstanceFactory;
import org.jbpm.ee.services.model.TaskFactory;
import org.jbpm.ee.services.model.CommandResponse;
import org.jbpm.ee.services.support.KieReleaseIdXProcessInstanceListener;
import org.jbpm.services.task.commands.TaskCommand;
import org.kie.api.runtime.CommandExecutor;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.task.model.Content;
import org.kie.api.task.model.TaskSummary;
import org.kie.internal.task.api.InternalTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import antlr.collections.List;


/**
 * Executes a single command and returns a response object
 * 
 * @author bdavis, abaxter
 * 
 */
@MessageDriven(name = "CommandRequestMDB", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/JBPMCommandRequestQueue") })
public class CommandExecutorMDB implements MessageListener {

	private static final Logger LOG = LoggerFactory
			.getLogger(CommandExecutorMDB.class);

	@Resource(mappedName = "java:/JmsXA")
	private ConnectionFactory connectionFactory;

	private Connection connection;
	private Session session;

	@EJB
	private KnowledgeManagerBean knowledgeManager;

	@Inject
	private EntityManager entityManager;

	private static final Class[] executeArgs = new Class[1];
	static {
		executeArgs[0] = org.kie.internal.command.Context.class;
	}

	@PostConstruct
	public void init() throws JMSException {
		connection = connectionFactory.createConnection();
	}

	@PreDestroy
	public void cleanup() throws JMSException {
		if (connection != null) {
			connection.close();
		}
		if (session != null) {
			session.close();
		}
	}

	public KieReleaseId getReleaseIdFromMessage(Message request)
			throws JMSException {
		String groupId = request.getStringProperty("groupId");
		String artifactId = request.getStringProperty("artifactId");
		String version = request.getStringProperty("version");

		if ((groupId == null) || (artifactId == null) || (version == null)) {
			throw new IllegalStateException(
					"Release Id information must not be null.");
		}

		return new KieReleaseId(groupId, artifactId, version);
	}

	public RuntimeEngine getRuntimeEngine(KieReleaseId releaseId) {

		return knowledgeManager.getRuntimeEngine(releaseId);
	}

	public CommandExecutor getCommandExecutor(GenericCommand<?> command) {
		if (TaskCommand.class.isAssignableFrom(command.getClass())) {
			TaskCommand<?> taskCommand = (TaskCommand<?>) command;
			if (AcceptedCommands.influencesKieSession(command.getClass())) {
				return (InternalTaskService) knowledgeManager
						.getRuntimeEngineByTaskId(taskCommand.getTaskId())
						.getTaskService();
			} else {
				return (InternalTaskService) knowledgeManager
						.getKieSessionUnboundTaskService();
			}
		} else {
			if (AcceptedCommands.containsProcessInstanceId(command.getClass())) {
				Long processInstanceId = getLongFromCommand(
						"getProcessInstanceId", command);
				return knowledgeManager.getRuntimeEngineByProcessId(
						processInstanceId).getKieSession();

			} else if (AcceptedCommands.containsWorkItemId(command.getClass())) {
				Long workItemId = getLongFromCommand("getWorkItemId", command);
				return knowledgeManager
						.getRuntimeEngineByWorkItemId(workItemId)
						.getKieSession();
			}
		}
		return null;
	}

	public CommandExecutor getCommandExecutor(KieReleaseId releaseId) {
		RuntimeEngine engine = getRuntimeEngine(releaseId);
		KieSession kSession = engine.getKieSession();
		kSession.addEventListener(new KieReleaseIdXProcessInstanceListener(
				releaseId, entityManager));
		return kSession;
	}

	public Object executeCommand(GenericCommand<?> command,
			ObjectMessage objectMessage) throws JMSException {
		CommandExecutor executor = null;
		try {
			executor = getCommandExecutor(command);
		} catch (InactiveProcessInstance e) {
			if (!command.getClass().equals(GetProcessInstanceCommand.class)) {
				throw new IllegalStateException("Unknown process for command",
						e);
			} else {
				LOG.info("Null process for GetProcessInstance command");
				return null;
			}
		}
		if (executor == null) {
			KieReleaseId releaseId = getReleaseIdFromMessage(objectMessage);
			executor = getCommandExecutor(releaseId);
		}
		// If executor is still null, throw an exception
		if (executor == null) {
			throw new IllegalStateException(
					"Unable to determine runtime executor.");
		}
		return executor.execute(command);
	}
	
	
	/**
	 * 
	 * @param commandResponse
	 * @param returnType
	 * @return
	 */
	private Object getResponseObjectByReturnType(Object commandResponse, Class<?> returnType){
		Object response = null;
    	
    	if(commandResponse==null){
    		return null;
    	}else if(commandResponse instanceof Collection<?>){
    		//if commandReponse returns a Collection, retrieve generic type information
    		Class<?> genericClassType = getClassFromCollection(commandResponse);
    		
    		if(genericClassType.equals(org.kie.api.runtime.process.ProcessInstance.class) && returnType.equals(Collection.class)){
    			
    			response = ProcessInstanceFactory.convertProcessInstances((Collection<org.kie.api.runtime.process.ProcessInstance>)commandResponse);
    			
    		}else if(genericClassType.equals(Long.class) && returnType.equals(List.class)){
    			
    			response = commandResponse;
    			
    		}else if(genericClassType.equals(org.kie.api.task.model.TaskSummary.class) && returnType.equals(List.class)){
    			
    			response = TaskFactory.convertTaskSummaries((java.util.List<TaskSummary>)commandResponse);
    			
    		}else if(genericClassType.equals(String.class) && returnType.equals(List.class)){
    			
    			response = commandResponse;
    			
    		}else{
    			
    			throw new IllegalStateException("No converter for class Collection<"+genericClassType.getCanonicalName()+">.");
        		
    		}
    		
    	}else{
    			
    		if(returnType.equals(org.drools.core.process.instance.WorkItem.class)){
    			
    			response = ProcessInstanceFactory.convert((WorkItem)commandResponse);
    			
    		}else if(returnType.equals(org.kie.api.task.model.Content.class)){
    			
    			response = TaskFactory.convert((Content)commandResponse);
    			
    		}else if(returnType.equals(Long.class)){
    			
    			response = commandResponse;
    			
    		}else  if(returnType.equals(org.kie.api.runtime.process.ProcessInstance.class)){
    			
    			response = ProcessInstanceFactory.convert((org.kie.api.runtime.process.ProcessInstance)commandResponse);
    			
    		}else if(returnType.equals(org.kie.api.task.model.Attachment.class)){
    			
    			response = TaskFactory.convert((org.kie.api.task.model.Attachment) commandResponse);
    			
    		}else if(returnType.equals(Integer.class)){
    			
    			response = commandResponse;
    			
    		}else if(returnType.equals(org.kie.api.task.model.Task.class)){
    			
    			response = TaskFactory.convert((org.kie.api.task.model.Task) commandResponse);
    			
    		}else{
    			throw new IllegalStateException("No converter for class "+returnType.getCanonicalName()+".");
        		
    		}
    		
    	}
    	
    	return response;
    }

	/**
	 * This method returns the generic type of a non-empty Collection or null
	 * 
	 * @param commandResponse
	 * @return returns the generic type of a non-empty Collection
	 */
	private static Class<?> getClassFromCollection(Object commandResponse) {
		Class<?> clazz = null;
		if (commandResponse instanceof Collection<?>) {
			Collection<?> col = (Collection<?>) commandResponse;

			if (!col.isEmpty()) {
				clazz = col.toArray()[0].getClass();
			}
		}

		return clazz;
	}

	@Override
	public void onMessage(Message message) {
		ObjectMessage objectMessage = (ObjectMessage) message;

		try {
			// check the command and lookup the kie session.
			GenericCommand<?> command = (GenericCommand<?>) objectMessage
					.getObject();
			Method executeMethod = command.getClass().getMethod("execute",
					executeArgs);

			Object commandResponse = executeCommand(command, objectMessage);

			// Check to see if the execute method is supposed to return
			// something
			Class<?> returnType = executeMethod.getReturnType();

			if (!(returnType.equals(Void.class))) {
				// see if there is a correlation and reply to.ok

				Object convertedObject = getResponseObjectByReturnType(commandResponse, returnType);

				String correlation = message.getJMSCorrelationID();
				Destination responseQueue = message.getJMSReplyTo();

				if (responseQueue != null && correlation != null) {

					if ((convertedObject != null)
							&& (!Serializable.class
									.isAssignableFrom(convertedObject
											.getClass()))) {
						throw new CommandException(
								"Unable to send response for command, since it is not serializable.");
					}
					
					session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
					MessageProducer producer = session.createProducer(responseQueue);
			        
					ObjectMessage responseMessage = session.createObjectMessage();
					CommandResponse responseObject = new CommandResponse();
					responseObject.setResponse(convertedObject);
					responseObject.setCommand(command);
					
					responseMessage.setObject(responseObject);
					responseMessage.setJMSCorrelationID(correlation);
					LOG.info("Sending message");
					producer.send(responseMessage);
					producer.close();
					session.close();
				} else {
					LOG.warn("Response from Command Object, but no ReplyTo and Coorelation: "
							+ ReflectionToStringBuilder
									.toString(convertedObject));
				}
			}
		} catch (JMSException | NoSuchMethodException | SecurityException e) {
			throw new CommandException("Exception processing command via JMS.",
					e);
		}

	}

	// TODO: Is it better to explicitly look for commands?

	private Long getLongFromCommand(final String methodName,
			final GenericCommand<?> command) {
		try {
			Method longMethod = command.getClass().getMethod(methodName);
			Long result = (Long) longMethod.invoke(command, (Object[]) null);
			return result;
		} catch (NoSuchMethodException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// We do not expect this
			throw new CommandException(e);
		}
	}

}
