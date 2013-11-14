package org.jbpm.ee.jms;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.drools.core.command.impl.GenericCommand;
import org.jbpm.ee.support.KieReleaseId;
import org.mvel2.sh.CommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides simple wrapper to send a Command via JMS and expect a response
 * 
 * @author bdavis, abaxter
 *
 */
@Stateless
@LocalBean
public class AsyncCommandExecutorBean {

	private static final Logger LOG = LoggerFactory.getLogger(AsyncCommandExecutorBean.class);
	
	@Resource(mappedName = "java:/JmsXA")
	private ConnectionFactory connectionFactory;

	@Resource(mappedName = "java:/jms/JBPMCommandRequestQueue")
	private Queue requestQueue;

	@Resource(mappedName = "java:/jms/JBPMCommandResponseQueue")
	private Topic responseQueue;

	private Connection connection;
	private Session session;
	private MessageProducer producer;
	

    @PostConstruct
    public void init() throws JMSException {
        connection = connectionFactory.createConnection();
        session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);;
        producer = session.createProducer(requestQueue); 
        connection.start();
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
    
	/**
	 * Executes a command asynchronously, via JMS. Returns a correlation id
	 * 
	 * At this time, only one command at a time
	 * 
	 * @param kieReleaseId
	 * @param command
	 * @return
	 */
	public String execute(KieReleaseId kieReleaseId, GenericCommand<?> command) {
		String uuid = UUID.randomUUID().toString();
		try {
			ObjectMessage request = session.createObjectMessage();
			request.setJMSCorrelationID(uuid);
			request.setObject(command);
			request.setJMSReplyTo(responseQueue);
			
			request.setStringProperty("groupId", kieReleaseId.getGroupId());
			request.setStringProperty("artifactId", kieReleaseId.getArtifactId());
			request.setStringProperty("version", kieReleaseId.getVersion());
			
			producer.send(request);
			
			return uuid.toString();
		} catch (JMSException e) {
			throw new CommandException("Exception sending Command Message.", e);
		}
	}

	/**
	 * Waits for the response object for a given correlation id.
	 * 
	 * @param correlation
	 * @return
	 */
	public Object pollResponse(String correlation) {
		final String correlationSelector = "JMSCorrelationID = '" + correlation + "'";

		try {
			MessageConsumer consumer = session.createConsumer(responseQueue, correlationSelector);
			Message response =  consumer.receive(10000);

			if(response == null) {
				LOG.debug("Message not yet recieved: "+correlation);
				return null;
			}
			else {
				LOG.debug("Recieved message for correlation: "+correlation);
				return ((ObjectMessage)response).getObject();
			}
		}
		catch (JMSException e) {
			throw new CommandException("Exception receiving Command Message Response.", e);
		}
	}

}
