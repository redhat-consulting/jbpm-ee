package org.jbpm.ee.services.ws;

import javax.ejb.EJB;
import javax.jws.WebService;

import org.jbpm.ee.services.ejb.local.WorkItemServiceLocal;
import org.jbpm.ee.services.ws.request.JaxbMapRequest;
import org.kie.services.client.serialization.jaxb.impl.JaxbWorkItem;

@WebService(targetNamespace="http://jbpm.org/v6/WorkItemService/wsdl", serviceName="WorkItemService", endpointInterface="org.jbpm.ee.services.ws.WorkItemServiceWS")
public class WorkItemServiceWSImpl implements WorkItemServiceWS {

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
