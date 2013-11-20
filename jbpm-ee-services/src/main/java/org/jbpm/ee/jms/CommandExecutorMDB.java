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
import org.jbpm.ee.services.support.KieReleaseIdXProcessInstanceListener;
import org.jbpm.services.task.commands.TaskCommand;
import org.kie.api.runtime.CommandExecutor;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
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
		
		if ((groupId == null) || (artifactId == null) || (version == null)) {
			throw new IllegalStateException("Release Id information must not be null.");
		}
		
		return new KieReleaseId(groupId, artifactId, version);
    }
    
    public RuntimeEngine getRuntimeEngine(KieReleaseId releaseId) {

		
		return knowledgeManager.getRuntimeEngine(releaseId);
    }
    
    public CommandExecutor getCommandExecutor(GenericCommand<?> command) {
    	if(TaskCommand.class.isAssignableFrom(command.getClass())) {
    		TaskCommand<?> taskCommand = (TaskCommand<?>)command;
    		if (AcceptedCommands.getCommandsThatInfluenceKieSession().contains(command.getClass())) {
    			return knowledgeManager.getRuntimeEngineByTaskId(taskCommand.getTaskId()).getKieSession();
    		} else {
    			return (InternalTaskService) knowledgeManager.getKieSessionUnboundTaskService();
    		}
    	} else {
    		if (AcceptedCommands.getCommandsWithProcessInstanceId().contains(command.getClass())) {
    			Long processInstanceId = getLongFromCommand("getProcessInstanceId", command);
    			return knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession();
    			
    		} else if (AcceptedCommands.getCommandsWithWorkItemid().contains(command.getClass())) {
    			Long workItemId = getLongFromCommand("getWorkItemId", command);
    			return knowledgeManager.getRuntimeEngineByWorkItemId(workItemId).getKieSession();
    		}
    	}
    	return null;
    }
    
    public CommandExecutor getCommandExecutor(KieReleaseId releaseId) {
    	RuntimeEngine engine = getRuntimeEngine(releaseId);
    	KieSession kSession = engine.getKieSession();
		kSession.addEventListener(new KieReleaseIdXProcessInstanceListener(releaseId, entityManager));
		return kSession;
    }
	
	@Override
	public void onMessage(Message message) {
		ObjectMessage objectMessage = (ObjectMessage) message;

		try {
			//check the command and lookup the kie session.
			GenericCommand<?> command = (GenericCommand<?>)objectMessage.getObject();
			
			CommandExecutor executor = getCommandExecutor(command);
			if (executor == null) {
				KieReleaseId releaseId = getReleaseIdFromMessage(message);
				executor = getCommandExecutor(releaseId);
			}
			// If executor is still null, throw an exception
			if (executor == null) {
				throw new IllegalStateException("Unable to determine command runtime executor.");
			}
			
			Object commandResponse = executor.execute(command);

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
					producer.close();
					session.close();
				} 
				else {
					LOG.warn("Response from Command Object, but no ReplyTo and Coorelation: " + ReflectionToStringBuilder.toString(commandResponse));
				}
			}
		} catch (JMSException e) {
			throw new CommandException("Exception processing command via JMS.", e);
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
