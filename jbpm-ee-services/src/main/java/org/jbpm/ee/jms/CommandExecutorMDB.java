package org.jbpm.ee.jms;

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
import org.jbpm.ee.exception.CommandException;
import org.jbpm.ee.services.ejb.startup.BPMClassloaderService;
import org.jbpm.ee.services.ejb.startup.KnowledgeManagerBean;
import org.jbpm.ee.services.model.CommandResponse;
import org.jbpm.ee.services.model.KieReleaseId;
import org.jbpm.ee.services.model.LazyDeserializingObject;
import org.jbpm.ee.services.model.ProcessInstanceFactory;
import org.jbpm.ee.services.model.TaskFactory;
import org.jbpm.ee.services.model.adapter.ClassloaderManager;
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

	public KieReleaseId getReleaseIdFromMessage(Message request) throws JMSException {
		String groupId = request.getStringProperty("groupId");
		String artifactId = request.getStringProperty("artifactId");
		String version = request.getStringProperty("version");

		if ((groupId == null) || (artifactId == null) || (version == null)) {
			throw new CommandException("Release Id information must not be null.");
		}

		return new KieReleaseId(groupId, artifactId, version);
	}


	public CommandExecutor getCommandExecutor(KieReleaseId releaseId) {
		RuntimeEngine engine = knowledgeManager.getRuntimeEngine(releaseId);
		KieSession kSession = engine.getKieSession();
		return kSession;
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
			
			CommandExecutor executor = null;
			
			boolean commandRequiresReleaseId = objectMessage.getBooleanProperty("commandRequiresReleaseId");
			if (commandRequiresReleaseId) {
				KieReleaseId releaseId = getReleaseIdFromMessage(message);
			
				//now, setup the classloader.
				classloaderService.bridgeClassloaderByReleaseId(releaseId);
				
				executor = getCommandExecutor(releaseId);
			} else {
				executor = (CommandExecutor) knowledgeManager.getKieSessionUnboundTaskService();
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

			Object commandResponse = executor.execute(command);

			// Check to see if the execute method is supposed to return something
			Class<?> returnType = commandResponse.getClass();

			if (!(returnType.equals(Void.class))) {
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
					
					responseMessage.setObject(responseObject);
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
		} catch (JMSException | IOException | SecurityException e) {
			throw new CommandException("Exception processing command via JMS.", e);
		}

	}
}
