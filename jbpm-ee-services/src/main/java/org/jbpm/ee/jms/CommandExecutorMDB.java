package org.jbpm.ee.jms;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
import org.jbpm.ee.services.model.CommandResponse;
import org.jbpm.ee.services.model.KieReleaseId;
import org.jbpm.ee.services.model.LazyDeserializingMap;
import org.jbpm.ee.services.model.ProcessInstanceFactory;
import org.jbpm.ee.services.model.TaskFactory;
import org.jbpm.ee.services.model.adapter.ClassloaderManager;
import org.jbpm.ee.support.BeanUtils;
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
		return kSession;
	}

	public Object executeCommand(GenericCommand<?> command, ObjectMessage objectMessage) throws JMSException, IOException {
		CommandExecutor executor = null;
		try {
			executor = getCommandExecutor(command);
			initializeLazyMaps(command);
		} catch (InactiveProcessInstance e) {
			if (!command.getClass().equals(GetProcessInstanceCommand.class)) {
				throw new IllegalStateException("Unknown process for command",
						e);
			} else {
				LOG.debug("Null process for GetProcessInstance command");
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
	
	protected void initializeLazyMaps(Object obj) throws IOException {
		if(LOG.isDebugEnabled()) {
			LOG.debug("Reflected Object: "+ReflectionToStringBuilder.toString(obj));
		}
		for(Field field : obj.getClass().getDeclaredFields()) {
			if(LOG.isDebugEnabled()) {
				LOG.debug("Field: "+field);
			}
			if(Map.class.isAssignableFrom(field.getType())) {
				Object mapObj = BeanUtils.getObjectViaGetter(field, obj);
				if(mapObj == null) {
					//do nothing.
					continue;
				}
				
				if(LOG.isDebugEnabled()) {
					LOG.debug("Map Object: "+ReflectionToStringBuilder.toString(mapObj));
				}
				
				if(LazyDeserializingMap.class.isAssignableFrom(mapObj.getClass())) {
					LOG.debug("Lazy map!");
					LazyDeserializingMap lazyMap = (LazyDeserializingMap)mapObj;
					lazyMap.initializeLazy(ClassloaderManager.get());
					
					Map<String, Object> newMap = new HashMap<String, Object>();
					newMap.putAll(lazyMap);
					
					BeanUtils.setObjectViaSetter(field, obj, newMap);
					LOG.debug("Reset object after initializing.");
				}
			}
		}
	}
	
	
	/**
	 * 
	 * @param commandResponse
	 * @param returnType
	 * @return
	 */
	private static Object getResponseObjectFromCommandResponse(Object commandResponse){
		Object response = null;
    	
    	if(commandResponse==null){
    		return null;
    	}else if(commandResponse instanceof Collection<?>){
    		
    		Collection commandResponses = (Collection)commandResponse;
    		Collection convertedResponses = new ArrayList(commandResponses.size());
    		
    		for (Iterator iterator = commandResponses.iterator(); iterator.hasNext();) {
    			convertedResponses.add(convertResponse(iterator.next()));
    			
    		}
    		
    		response = convertedResponses;
    		
    		
    	}else{
    			
    		response = convertResponse(commandResponse);
    		
    	}
    	
    	return response;
    }

	private static Object convertResponse(Object commandResponse) {
		
		Object response = null;
		if(commandResponse instanceof org.kie.api.task.model.TaskSummary) {
			
			response = TaskFactory.convert((TaskSummary)commandResponse);
			
		}else if(commandResponse instanceof org.drools.core.process.instance.WorkItem){
			
			response = ProcessInstanceFactory.convert((WorkItem)commandResponse);
			
		}else if(commandResponse instanceof org.kie.api.task.model.Content){
			
			response = TaskFactory.convert((Content)commandResponse);
			
		}else if(commandResponse instanceof Long){
			
			response = commandResponse;
			
		}else if(commandResponse instanceof String){
			
			response = commandResponse;
			
		}else  if(commandResponse instanceof org.kie.api.runtime.process.ProcessInstance){
			
			response = ProcessInstanceFactory.convert((org.kie.api.runtime.process.ProcessInstance)commandResponse);
			
		}else if(commandResponse instanceof org.kie.api.task.model.Attachment){
			
			response = TaskFactory.convert((org.kie.api.task.model.Attachment) commandResponse);
			
		}else if(commandResponse instanceof Integer){
			
			response = commandResponse;
			
		}else if(commandResponse instanceof org.kie.api.task.model.Task){
			
			response = TaskFactory.convert((org.kie.api.task.model.Task) commandResponse);
			
		}else{
			throw new IllegalStateException("No converter for class "+commandResponse.getClass().getCanonicalName()+".");
			
		}
		return response;
	}

	@Override
	public void onMessage(Message message) {
		ObjectMessage objectMessage = (ObjectMessage) message;

		try {
			// check the command and lookup the kie session.
			GenericCommand<?> command = (GenericCommand<?>) objectMessage
					.getObject();
			
			if(LOG.isInfoEnabled()) {
				LOG.info("Request: "+ReflectionToStringBuilder.toString(command));
			}
			
			Method executeMethod = command.getClass().getMethod("execute", executeArgs);

			Object commandResponse = executeCommand(command, objectMessage);

			// Check to see if the execute method is supposed to return
			// something
			Class<?> returnType = executeMethod.getReturnType();

			if (!(returnType.equals(Void.class))) {
				// see if there is a correlation and reply to.ok

				Object convertedObject = getResponseObjectFromCommandResponse(commandResponse);

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
					if(LOG.isDebugEnabled()) {
						LOG.debug("Sending response: "+ReflectionToStringBuilder.toString(convertedObject));
					}
					
					producer.send(responseMessage);
					producer.close();
					session.close();
				} else {
					LOG.warn("Response from Command Object, but no ReplyTo and Coorelation: "
							+ ReflectionToStringBuilder
									.toString(convertedObject));
				}
			}
		} catch (JMSException | NoSuchMethodException | IOException | SecurityException e) {
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
