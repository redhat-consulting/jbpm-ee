package org.jbpm.ee.jms;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import org.jbpm.ee.exception.CommandException;
import org.jbpm.ee.services.model.KieReleaseId;

public class MessageUtil {

	// Static functions only
	private MessageUtil() {}
	
	private static final String COMMAND_REQUIRES_RELEASE_ID = "COMMAND_REQUIRES_RELEASE_ID";
	
	private static final String RELEASE_ID_GROUP = "GROUP_ID";
	
	private static final String RELEASE_ID_ARTIFACT = "ARTIFACT_ID";
	
	private static final String RELEASE_ID_VERSION = "VERSION_ID";
	
	public static void setReleaseIdNotRequired(ObjectMessage msg) throws JMSException {
		msg.setBooleanProperty(COMMAND_REQUIRES_RELEASE_ID, false);
	}
	
	public static void setReleaseIdRequired(ObjectMessage msg, KieReleaseId releaseId) throws JMSException {
		msg.setBooleanProperty(COMMAND_REQUIRES_RELEASE_ID, true);
		msg.setStringProperty(RELEASE_ID_GROUP, releaseId.getGroupId());
		msg.setStringProperty(RELEASE_ID_ARTIFACT, releaseId.getArtifactId());
		msg.setStringProperty(RELEASE_ID_VERSION, releaseId.getVersion());
		
	}
	
	public static boolean isReleaseIdRequired(ObjectMessage msg) throws JMSException {
		return msg.getBooleanProperty(COMMAND_REQUIRES_RELEASE_ID);
	}
	
	public static KieReleaseId getReleaseId(ObjectMessage msg) throws JMSException {
		String groupId = msg.getStringProperty(RELEASE_ID_GROUP);
		String artifactId = msg.getStringProperty(RELEASE_ID_ARTIFACT);
		String version = msg.getStringProperty(RELEASE_ID_VERSION);

		if ((groupId == null) || (artifactId == null) || (version == null)) {
			throw new CommandException("Release Id information must not be null.");
		}

		return new KieReleaseId(groupId, artifactId, version);
	}
}
