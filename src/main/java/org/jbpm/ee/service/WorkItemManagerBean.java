package org.jbpm.ee.service;

import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.jbpm.ee.cdi.JBPMServiceBean;
import org.jbpm.ee.service.exception.RuntimeConfigurationException;
import org.jbpm.ee.service.remote.WorkItemManagerRemote;
import org.jbpm.ee.support.KieReleaseId;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

@LocalBean
@Remote(WorkItemManagerRemote.class)
@Stateful
@SessionScoped
public class WorkItemManagerBean implements WorkItemManager, WorkItemManagerRemote, KieReleaseIdAware {

	@Inject
	private JBPMServiceBean jbpmService;
	
	private WorkItemManager workItemManager;
	
	
	@Override
	public void completeWorkItem(long id, Map<String, Object> results) {
		this.workItemManager.abortWorkItem(id);
	}

	@Override
	public void abortWorkItem(long id) {
		this.workItemManager.abortWorkItem(id);
	}

	@Override
	public void registerWorkItemHandler(String workItemName, WorkItemHandler handler) {
		this.workItemManager.registerWorkItemHandler(workItemName, handler);
	}

	@Override
	public void setKieReleaseId(KieReleaseId releaseId) {
		this.workItemManager = jbpmService.getWorkItemService(releaseId);
	}

	@Override
	public void validateKieReleaseIdState() {
		if(this.workItemManager == null) {
			throw new RuntimeConfigurationException("WorkItemManager requires a KieReleaseId.  Make sure to set setKieReleaseId prior to making calls to the EJB.");
		}
	}

}
