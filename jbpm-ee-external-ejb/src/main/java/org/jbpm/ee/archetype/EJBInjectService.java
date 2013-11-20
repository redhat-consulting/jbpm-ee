package org.jbpm.ee.archetype;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.jbpm.ee.archetype.exception.EJBRuntimeException;
import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.TaskService;
import org.jbpm.ee.services.WorkItemService;
import org.jbpm.ee.services.ejb.remote.ProcessServiceRemote;
import org.jbpm.ee.services.ejb.remote.TaskServiceRemote;
import org.jbpm.ee.services.ejb.remote.WorkItemServiceRemote;
import org.slf4j.Logger;

@WebService(targetNamespace="http://jbpm.org/v6/EJBInjectService/wsdl", serviceName="EJBInjectService")
@LocalBean
@Stateless
public class EJBInjectService extends BaseService {

	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(EJBInjectService.class);
	
	@EJB(lookup = "java:global/jbpm-ee-services/ProcessServiceBean!org.jbpm.ee.services.ejb.remote.ProcessServiceRemote")
	private ProcessServiceRemote processService;
	
	@EJB(lookup = "java:global/jbpm-ee-services/TaskServiceBean!org.jbpm.ee.services.ejb.remote.TaskServiceRemote")
	private TaskServiceRemote taskService;
	
	@EJB(lookup = "java:global/jbpm-ee-services/WorkItemServiceBean!org.jbpm.ee.services.ejb.remote.WorkItemServiceRemote")
	private WorkItemServiceRemote workItemService;
	
	
	@Override
	protected ProcessService getProcessService() {
		return processService;
	}

	@Override
	protected TaskService getTaskService() {
		return taskService;
	}

	@Override
	protected WorkItemService getWorkItemService() {
		return workItemService;
	}
	
	/**
	 * List number of tasks; creates a new process; rolls back.
	 * Number of tasks should remain consistent.
	 */
	@WebMethod
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void startProcessThenRollback() {
		try {
			this.startProcess();
			generateException();
		}
		catch(RuntimeException e) {
			LOG.info("Started process, then rolled back.  Number of tasks should remain the same before this test was executed.");
			throw e;
		}
	}
	
	private void generateException() {
		throw new EJBRuntimeException("Exception to show rollback.");
	}

}
