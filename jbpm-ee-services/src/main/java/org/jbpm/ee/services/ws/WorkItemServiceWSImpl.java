package org.jbpm.ee.services.ws;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.jws.WebService;

import org.jbpm.ee.services.ejb.local.WorkItemServiceLocal;
import org.jbpm.ee.services.model.KieReleaseId;
import org.jbpm.ee.services.model.process.WorkItem;
import org.jbpm.ee.services.ws.exceptions.RemoteServiceException;
import org.jbpm.ee.services.ws.request.JaxbMapRequest;

@WebService(targetNamespace="http://jbpm.org/v6/WorkItemService/wsdl", serviceName="WorkItemService", endpointInterface="org.jbpm.ee.services.ws.WorkItemServiceWS")
public class WorkItemServiceWSImpl implements WorkItemServiceWS {

	@EJB
	private WorkItemServiceLocal workItemManager;
	
	@Override
	public void completeWorkItem(long id, JaxbMapRequest results) {
		try {
			Map<String, Object> resultMap = null;
			if(results != null) {
				resultMap = results.getMap();
			}
			
			this.workItemManager.completeWorkItem(id, resultMap);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public void abortWorkItem(long id) {
		try {
			this.workItemManager.abortWorkItem(id);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public WorkItem getWorkItem(long id) {
		try {
			return (WorkItem)this.workItemManager.getWorkItem(id);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public List<WorkItem> getWorkItemByProcessInstance(long processInstanceId) {
		return (List)workItemManager.getWorkItemByProcessInstance(processInstanceId);
	}

	@Override
	public KieReleaseId getReleaseId(long workItemId) {
		return this.workItemManager.getReleaseId(workItemId);
	}

}
