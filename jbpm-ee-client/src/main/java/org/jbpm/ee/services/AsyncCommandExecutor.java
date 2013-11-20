package org.jbpm.ee.services;

import org.drools.core.command.impl.GenericCommand;
import org.jbpm.ee.services.model.CommandResponse;
import org.jbpm.ee.support.KieReleaseId;

public interface AsyncCommandExecutor {

	String execute(KieReleaseId kieReleaseId, GenericCommand<?> command);
	
	String execute(GenericCommand<?> command);
	
	CommandResponse pollResponse(String correlation);
}
