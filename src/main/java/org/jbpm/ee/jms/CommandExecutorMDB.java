package org.jbpm.ee.jms;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
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

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.jbpm.ee.cdi.KieSessionConfig;
import org.jbpm.ee.exception.CommandException;
import org.kie.api.command.Command;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @PostConstruct
    public void init() throws JMSException {
        connection = connectionFactory.createConnection();
        session = connection.createSession(); 
    }
	
	@Override
	public void onMessage(Message message) {
		ObjectMessage objectMessage = (ObjectMessage) message;

		KieSession commandServiceKieSession;
		
		try {
			// this should be the command object.
			Object commandResponse = null; //TODO: Fix this.commandService.execute((Command) objectMessage.getObject());

			if (!(commandResponse instanceof Void)) {
				// see if there is a correlation and reply to.

				String correlation = message.getJMSCorrelationID();
				Destination responseQueue = message.getJMSReplyTo();

				if (responseQueue != null && correlation != null) {
					
					if(!Serializable.class.isAssignableFrom(commandResponse.getClass())) {
						throw new CommandException("Unable to send response for command, since it is not serializable.");
					}
					
			        MessageProducer producer = session.createProducer(responseQueue);
			        
					ObjectMessage responseMessage = session.createObjectMessage();
					responseMessage.setObject((Serializable)commandResponse);
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

}
