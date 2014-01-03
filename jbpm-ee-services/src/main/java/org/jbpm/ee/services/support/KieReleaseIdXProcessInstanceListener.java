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
	private static final String KBPI_SERVICE_APP = "java:app/jbpm-ee-services/KieBaseXProcessInstanceDao";
	private static final String KBPI_SERVICE_GLOBAL = "java:global/jbpm-ee-services/KieBaseXProcessInstanceDao!org.jbpm.ee.persistence.KieBaseXProcessInstanceDao";
	
	
	
	private final ReleaseId kieReleaseId;
	
	public KieReleaseIdXProcessInstanceListener(ReleaseId kri) {
		this.kieReleaseId = kri;
	}
	
	private KieBaseXProcessInstanceDao getDao() {
		KieBaseXProcessInstanceDao dao = null;
		try {
			InitialContext initialContext = new InitialContext();
			dao = (KieBaseXProcessInstanceDao)initialContext.lookup(KBPI_SERVICE_APP);
			
		} catch (NamingException e) {
			LOG.info("Exception looking up KieBaseXProcessInstanceDao: "+KBPI_SERVICE_APP+" retrying with "+KBPI_SERVICE_GLOBAL, e.getMessage());
			try {
				InitialContext initialContext = new InitialContext();
				dao = (KieBaseXProcessInstanceDao)initialContext.lookup(KBPI_SERVICE_GLOBAL);
			} catch (NamingException ne) {
				LOG.error("Exception looking up KieBaseXProcessInstanceDao: "+KBPI_SERVICE_GLOBAL, ne);
				throw new IllegalStateException(ne);
			}
		}
		return dao;
	}
	
	@Override
	public void beforeProcessStarted(ProcessStartedEvent event) {
		KieBaseXProcessInstanceDao dao = getDao();
		dao.addKieBaseXProcessInstanceReference(kieReleaseId, event.getProcessInstance().getId());
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
		LOG.info("afterProcessCompleted called for: " + processInstanceId);
		KieBaseXProcessInstanceDao dao = getDao();
		dao.removeKieBaseXProcessInstanceReference(processInstanceId);
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
