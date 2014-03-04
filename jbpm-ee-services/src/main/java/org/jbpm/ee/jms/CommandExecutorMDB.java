package org.jbpm.ee.jms;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.drools.core.command.impl.GenericCommand;
import org.drools.core.command.runtime.process.GetProcessInstanceCommand;
import org.kie.internal.command.Context;
import org.jbpm.ee.exception.CommandException;
import org.jbpm.ee.exception.InactiveProcessInstance;
import org.jbpm.ee.services.ejb.startup.BPMClassloaderService;
import org.jbpm.ee.services.ejb.startup.KnowledgeManagerBean;
import org.jbpm.ee.services.model.CommandResponse;
import org.jbpm.ee.services.model.KieReleaseId;
import org.jbpm.ee.services.model.LazyDeserializingObject;
import org.jbpm.ee.services.model.ProcessInstanceFactory;
import org.jbpm.ee.services.model.TaskFactory;
import org.jbpm.ee.services.model.adapter.ClassloaderManager;
import org.jbpm.services.task.commands.TaskCommand;
import org.kie.internal.task.api.InternalTaskService;
import org.kie.api.runtime.CommandExecutor;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.task.model.Content;
import org.kie.api.task.model.TaskSummary;
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

	private static final Logger LOG = LoggerFactory.getLogger(CommandExecutorMDB.class);

	@Resource(mappedName = "java:/JmsXA")
	private ConnectionFactory connectionFactory;

	private Connection connection;
	private Session session;

	@EJB
	private KnowledgeManagerBean knowledgeManager;

	@EJB
	private BPMClassloaderService classloaderService;
	
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

	public CommandExecutor getCommandExecutor(KieReleaseId releaseId, GenericCommand<?> cmd) {
		if (TaskCommand.class.isAssignableFrom(cmd.getClass())) {
			
			TaskCommand<?> taskCmd = (TaskCommand<?>) cmd;
			return (InternalTaskService) knowledgeManager
					.getRuntimeEngineByTaskId(taskCmd.getTaskId())
					.getTaskService();
		} else if (AcceptedCommands.containsProcessInstanceId(cmd)) {
			Long processInstanceId = getLongFromCommand("getProcessInstanceId", cmd);
			return knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession();
		} else if (AcceptedCommands.containsWorkItemId(cmd)) {
			Long workItemId = getLongFromCommand("getWorkItemId", cmd);
			return knowledgeManager.getRuntimeEngineByWorkItemId(workItemId).getKieSession();
		}
		
		if (releaseId != null) {
			RuntimeEngine engine = knowledgeManager.getRuntimeEngine(releaseId);
			KieSession kSession = engine.getKieSession();
			return kSession;
		} else {
			throw new CommandException("Unknown executor for null release id.");
		}
	}
	
	private static Type getCommandReturnType(GenericCommand<?> command) throws NoSuchMethodException, SecurityException {
		Class commandClass = command.getClass();
		Method executeMethod = commandClass.getDeclaredMethod("execute", Context.class);
		return executeMethod.getReturnType();
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
			LazyDeserializingObject obj = (LazyDeserializingObject)objectMessage.getObject();
			
			KieReleaseId releaseId = null;
			
			boolean commandRequiresReleaseId = MessageUtil.isReleaseIdRequired(objectMessage);
			if (commandRequiresReleaseId) {
				releaseId = MessageUtil.getReleaseId(objectMessage);
				classloaderService.bridgeClassloaderByReleaseId(releaseId);
			} else {
				classloaderService.useThreadClassloader();
			}
			//now, load the command into memory.
			obj.initializeLazy(ClassloaderManager.get());
			GenericCommand<?> command = (GenericCommand<?>)obj.getDelegate();
			
			if(!commandRequiresReleaseId &&
					AcceptedCommands.influencesKieSession(command)) {
				throw new CommandException("Command requires Release Id, but none provided: " + ReflectionToStringBuilder.toString(command));
			}
			
			if(LOG.isDebugEnabled()) {
				LOG.debug("Request: "+ReflectionToStringBuilder.toString(command));
			}

			Type commandReturnType = getCommandReturnType(command);
			
			System.out.println("Command Return Type: " + commandReturnType);
			
			Object commandResponse = null;
			try {
				CommandExecutor executor = getCommandExecutor(releaseId, command);
				commandResponse = executor.execute(command);
			} catch (InactiveProcessInstance e) {
				if (!command.getClass().equals(GetProcessInstanceCommand.class)) {
					throw new IllegalStateException("Unknown process for command",
						e);
				} else {
					LOG.debug("Null process for GetProcessInstance cmd");
					commandResponse = null;
				}
			}
			
			


			if (!(commandReturnType.equals(Void.TYPE))) {
				// see if there is a correlation and reply to.ok

				Object convertedObject = getResponseObjectFromCommandResponse(commandResponse);

				String correlation = message.getJMSCorrelationID();
				Destination responseQueue = message.getJMSReplyTo();

				if (responseQueue != null && correlation != null) {

					if ((convertedObject != null) && (!Serializable.class.isAssignableFrom(convertedObject.getClass()))) {
						throw new CommandException("Unable to send response for command, since it is not serializable.");
					}
					
					session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
					MessageProducer producer = session.createProducer(responseQueue);
			        
					ObjectMessage responseMessage = session.createObjectMessage();
					CommandResponse responseObject = new CommandResponse();
					responseObject.setResponse(convertedObject);
					responseObject.setCommand(command);
					
					if(commandRequiresReleaseId) {
						MessageUtil.setReleaseIdRequired(responseMessage, releaseId);
					} else {
						MessageUtil.setReleaseIdNotRequired(responseMessage);
					}
					
					LazyDeserializingObject lazyObject = new LazyDeserializingObject(responseObject);
					responseMessage.setObject(lazyObject);
					responseMessage.setJMSCorrelationID(correlation);
					
					if(LOG.isDebugEnabled()) {
						LOG.debug("Sending response: "+ReflectionToStringBuilder.toString(convertedObject));
					}
					
					producer.send(responseMessage);
					producer.close();
					session.close();
				} else {
					LOG.warn("Response from Command Object, but no ReplyTo and Coorelation: " + ReflectionToStringBuilder .toString(convertedObject));
				}
			}
		} catch (JMSException | IOException | SecurityException
				| NoSuchMethodException e) {
			throw new CommandException("Exception processing command via JMS.",
					e);
		}

	}

	private static Long getLongFromCommand(final String methodName,
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
