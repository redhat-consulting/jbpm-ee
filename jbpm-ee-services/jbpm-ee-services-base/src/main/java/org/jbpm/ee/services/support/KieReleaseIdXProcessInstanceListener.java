package org.jbpm.ee.services.support;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jbpm.ee.persistence.KieBaseXProcessInstanceDao;
import org.kie.api.builder.ReleaseId;
import org.kie.api.event.process.ProcessCompletedEvent;
import org.kie.api.event.process.ProcessEventListener;
import org.kie.api.event.process.ProcessNodeLeftEvent;
import org.kie.api.event.process.ProcessNodeTriggeredEvent;
import org.kie.api.event.process.ProcessStartedEvent;
import org.kie.api.event.process.ProcessVariableChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ProcessEventListener to insert and delete KieBaseXProcessInstance JPA entities
 * based on the Process starting and stopping, respectively
 *  
 * @author bdavis
 *
 */
public class KieReleaseIdXProcessInstanceListener implements ProcessEventListener {

	private static final Logger LOG = LoggerFactory.getLogger(KieReleaseIdXProcessInstanceListener.class);
	private static final String KBPI_SERVICE = "java:app/jbpm-ee-services/KieBaseXProcessInstanceDao";
	
	private final ReleaseId kieReleaseId;
	
	public KieReleaseIdXProcessInstanceListener(ReleaseId kri) {
		this.kieReleaseId = kri;
	}
	
	@Override
	public void beforeProcessStarted(ProcessStartedEvent event) {
		try {
			InitialContext initialContext = new InitialContext();
			KieBaseXProcessInstanceDao dao = (KieBaseXProcessInstanceDao)initialContext.lookup(KBPI_SERVICE);
			dao.addKieBaseXProcessInstanceReference(kieReleaseId, event.getProcessInstance().getId());
		} catch (NamingException e) {
			LOG.error("Exception looking up KieBaseXProcessInstanceDao", e);
		}
		
	}

	@Override
	public void afterProcessStarted(ProcessStartedEvent event) {
		
	}

	@Override
	public void beforeProcessCompleted(ProcessCompletedEvent event) {
		
	}

	@Override
	public void afterProcessCompleted(ProcessCompletedEvent event) {
		Long processInstanceId = event.getProcessInstance().getId();
		
		try {
			InitialContext initialContext = new InitialContext();
			KieBaseXProcessInstanceDao dao = (KieBaseXProcessInstanceDao)initialContext.lookup(KBPI_SERVICE);
			dao.removeKieBaseXProcessInstanceReference(processInstanceId);
		} catch (NamingException e) {
			LOG.error("Exception looking up KieBaseXProcessInstanceDao", e);
		}
	}

	@Override
	public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {
		
	}

	@Override
	public void afterNodeTriggered(ProcessNodeTriggeredEvent event) {
		
	}

	@Override
	public void beforeNodeLeft(ProcessNodeLeftEvent event) {
		
	}

	@Override
	public void afterNodeLeft(ProcessNodeLeftEvent event) {
		
	}

	@Override
	public void beforeVariableChanged(ProcessVariableChangedEvent event) {
		
	}

	@Override
	public void afterVariableChanged(ProcessVariableChangedEvent event) {
		
	}

}
