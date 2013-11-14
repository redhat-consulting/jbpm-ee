package org.jbpm.ee.client.rest.adapter;

import java.util.Map;

import org.drools.core.process.instance.WorkItem;
import org.jbpm.ee.services.WorkItemService;
import org.jbpm.ee.services.rest.WorkItemServiceRest;
import org.jbpm.ee.services.rest.request.JaxbMapRequest;

public class WorkItemServiceAdapter implements WorkItemService {

	private WorkItemServiceRest restService;
	
	public WorkItemServiceAdapter(WorkItemServiceRest restManager) {
		this.restService = restManager;
	}
	
	@Override
	public void completeWorkItem(long id, Map<String, Object> results) {
		this.restService.completeWorkItem(id, new JaxbMapRequest(results));
	}

	@Override
	public void abortWorkItem(long id) {
		this.restService.abortWorkItem(id);
	}

	@Override
	public WorkItem getWorkItem(long id) {
		return (WorkItem)this.restService.getWorkItem(id);
	}

}
