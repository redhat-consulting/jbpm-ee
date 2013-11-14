package org.jbpm.ee.services.rest.impl;

import javax.ejb.EJB;
import org.jbpm.ee.services.ejb.local.WorkItemServiceLocal;
import org.jbpm.ee.services.rest.WorkItemServiceRest;
import org.jbpm.ee.services.rest.request.JaxbMapRequest;
import org.kie.services.client.serialization.jaxb.impl.JaxbWorkItem;

public class WorkItemServiceRestImpl implements WorkItemServiceRest {

	@EJB
	private WorkItemServiceLocal workItemManager;
	
	@Override
	public void completeWorkItem(long id, JaxbMapRequest results) {
		this.workItemManager.completeWorkItem(id, results.getMap());
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
