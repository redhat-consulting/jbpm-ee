package org.jbpm.ee.jms;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
import org.jbpm.ee.exception.CommandException;
import org.jbpm.ee.services.ejb.startup.KnowledgeManagerBean;
import org.jbpm.ee.support.KieReleaseId;
import org.jbpm.ee.support.KieReleaseIdXProcessInstanceListener;
import org.jbpm.services.task.commands.TaskCommand;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
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
		 @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/JBPMCommandRequestQueue")
})
public class CommandExecutorMDB implements MessageListener {

	private static final Logger LOG = LoggerFactory.getLogger(CommandExecutorMDB.class);

	@Resource(mappedName = "java:/JmsXA")
	private ConnectionFactory connectionFactory;

	private Connection connection;
	private Session session;
	
	@EJB
	private KnowledgeManagerBean knowledgeManager;
	
	@Inject
	private EntityManager entityManager;
	
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
    
    public KieReleaseId getReleaseIdFromMessage(Message request) throws JMSException {
		String groupId = request.getStringProperty("groupId");
		String artifactId = request.getStringProperty("artifactId");
		String version = request.getStringProperty("version");
		
		return new KieReleaseId(groupId, artifactId, version);
    }
    
    public RuntimeEngine getRuntimeEngine(KieReleaseId releaseId) {

		
		return knowledgeManager.getRuntimeEngine(releaseId);
    }
    
    public RuntimeEngine getRuntimeEngine(GenericCommand<?> command) {
		
		if(TaskCommand.class.isAssignableFrom(command.getClass())) {
			TaskCommand<?> taskCommand = (TaskCommand<?>)command;
			return knowledgeManager.getRuntimeEngineByTaskId(taskCommand.getTaskId());
		}
		
		//else, try it by process instance id.
		Long processInstanceId = getProcessInstanceIdFromCommand(command);
		if(processInstanceId!=null) {
			return knowledgeManager.getRuntimeEngineByProcessId(processInstanceId);
		}
		
		//else, try it by work item id.
		Long workItemId = getWorkItemIdFromCommand(command);
		if(workItemId != null) {
			return knowledgeManager.getRuntimeEngineByWorkItemId(workItemId);
		}
		
		return null;
    }
	
	@Override
	public void onMessage(Message message) {
		ObjectMessage objectMessage = (ObjectMessage) message;

		try {
			//check the command and lookup the kie session.
			GenericCommand<?> command = (GenericCommand<?>)objectMessage.getObject();
			
			RuntimeEngine engine = getRuntimeEngine(command);
			KieSession kSession = null;
			
			//build it from the message properties...
			if(engine != null) {
				kSession = engine.getKieSession();
			} else {
				KieReleaseId releaseId = getReleaseIdFromMessage(message);
				engine = getRuntimeEngine(releaseId);
				kSession = engine.getKieSession();
				kSession.addEventListener(new KieReleaseIdXProcessInstanceListener(releaseId, entityManager));
			}
			
			// this should be the command object.
			Object commandResponse = kSession.execute(command);

			if (!(commandResponse instanceof Void)) {
				// see if there is a correlation and reply to.

				
				String correlation = message.getJMSCorrelationID();
				Destination responseQueue = message.getJMSReplyTo();

				if (responseQueue != null && correlation != null) {
					
					if(!Serializable.class.isAssignableFrom(commandResponse.getClass())) {
						throw new CommandException("Unable to send response for command, since it is not serializable.");
					}
					
					session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			        MessageProducer producer = session.createProducer(responseQueue);
			        
					ObjectMessage responseMessage = session.createObjectMessage();
					responseMessage.setObject((Serializable) commandResponse);
					responseMessage.setJMSCorrelationID(correlation);
					LOG.info("Sending message");
					producer.send(responseMessage);
				} 
				else {
					LOG.warn("Response from Command Object, but no ReplyTo and Coorelation: " + ReflectionToStringBuilder.toString(commandResponse));
				}
			}
		} catch (JMSException e) {
			throw new CommandException("Exception processing command via JMS.", e);
		}

	}


	private Long getWorkItemIdFromCommand(final GenericCommand<?> command) {
		if(AcceptedCommandSets.getCommandsWithWorkItemid().contains(command.getClass())) {
			return getLongFromCommand("getWorkItemId", command);
		} else {
			return null;
		}
		
	}
	
	private Long getProcessInstanceIdFromCommand(final GenericCommand<?> command) {
		if(AcceptedCommandSets.getCommandsWithProcessInstanceId().contains(command.getClass())) {
			return getLongFromCommand("getProcessInstanceId", command);	
		} else {
			return null;
		}
		
	}
	
	//TODO: Is it better to explicitly look for commands?
	
	private Long getLongFromCommand(final String methodName, final GenericCommand<?> command) {
		try {
			Method longMethod = command.getClass().getMethod(methodName);
			Long result = (Long) longMethod.invoke(command, (Object[]) null);
			return result;
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// We do not expect this
			throw new CommandException(e);
		}	
	}
	
}
