package org.jbpm.ee.services;

import org.drools.core.command.impl.GenericCommand;
import org.jbpm.ee.services.ejb.annotations.LazilyDeserialized;
import org.jbpm.ee.services.ejb.annotations.PreprocessClassloader;
import org.jbpm.ee.services.ejb.annotations.ReleaseId;
import org.jbpm.ee.services.model.CommandResponse;
import org.jbpm.ee.services.model.KieReleaseId;

public interface AsyncCommandExecutor {

	/***
	 * Executes a generic command on the jBPM 
	 * @param kieReleaseId
	 * @param command
	 * @return
	 */
	@PreprocessClassloader
	String execute(@ReleaseId KieReleaseId kieReleaseId, @LazilyDeserialized GenericCommand<?> command);
	
	String execute(GenericCommand<?> command);
	
	CommandResponse pollResponse(String correlation);
}
