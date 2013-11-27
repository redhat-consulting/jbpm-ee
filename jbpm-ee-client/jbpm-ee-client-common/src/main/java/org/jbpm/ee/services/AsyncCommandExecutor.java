package org.jbpm.ee.services;

import org.drools.core.command.impl.GenericCommand;
import org.jbpm.ee.services.ejb.annotations.PreprocessClassloader;
import org.jbpm.ee.services.ejb.annotations.ReleaseId;
import org.jbpm.ee.services.model.CommandResponse;
import org.jbpm.ee.services.model.KieReleaseId;

public interface AsyncCommandExecutor {

	@PreprocessClassloader
	String execute(@ReleaseId KieReleaseId kieReleaseId, GenericCommand<?> command);
	
	String execute(GenericCommand<?> command);
	
	CommandResponse pollResponse(String correlation);
}
