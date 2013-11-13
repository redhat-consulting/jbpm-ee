package org.jbpm.ee.services.rest.impl;

import java.util.Map;

import javax.inject.Inject;

import org.jbpm.ee.services.ejb.local.WorkItemServiceBean;
import org.jbpm.ee.services.rest.WorkItemServiceRest;
import org.kie.services.client.serialization.jaxb.impl.JaxbWorkItem;

public class WorkItemServiceRestImpl implements WorkItemServiceRest {

	@Inject
	private WorkItemServiceBean workItemManager;
	
	@Override
	public void completeWorkItem(long id, Map<String, Object> results) {
		this.workItemManager.completeWorkItem(id, results);
	}

	@Override
	public void abortWorkItem(long id) {
		this.workItemManager.abortWorkItem(id);
	}

	@Override
	public JaxbWorkItem getWorkItem(long id) {
		return new JaxbWorkItem(this.workItemManager.getWorkItem(id));
	}

}