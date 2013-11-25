package org.jbpm.ee.test;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.transaction.UserTransaction;

import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.TaskService;
import org.jbpm.ee.services.WorkItemService;
import org.jbpm.ee.services.ejb.local.ProcessServiceLocal;
import org.jbpm.ee.services.ejb.local.TaskServiceLocal;
import org.jbpm.ee.services.ejb.local.WorkItemServiceLocal;
import org.jbpm.ee.test.exception.TestRuntimeException;
import org.slf4j.Logger;

@WebService(targetNamespace="http://jbpm.org/v6/EJBInjectTest/wsdl", serviceName="EJBInjectTest")
@LocalBean
@Stateless
public class EJBInjectTest extends BaseTest {

	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(EJBInjectTest.class);

	@Inject
	private UserTransaction tx;
	
	@EJB(lookup = "java:global/jbpm-ee-external-ear-1.0.0-SNAPSHOT/jbpm-ee-services-1.0.0-SNAPSHOT/ProcessServiceBean!org.jbpm.ee.services.ejb.local.ProcessServiceLocal")
	private ProcessServiceLocal processService;
	
	@EJB(lookup = "java:global/jbpm-ee-external-ear-1.0.0-SNAPSHOT/jbpm-ee-services-1.0.0-SNAPSHOT/TaskServiceBean!org.jbpm.ee.services.ejb.local.TaskServiceLocal")
	private TaskServiceLocal taskService;
	
	@EJB(lookup = "java:global/jbpm-ee-external-ear-1.0.0-SNAPSHOT/jbpm-ee-services-1.0.0-SNAPSHOT/WorkItemServiceBean!org.jbpm.ee.services.ejb.local.WorkItemServiceLocal")
	private WorkItemServiceLocal workItemService;
	
	
	
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
		throw new TestRuntimeException("Exception to show rollback.");
	}

}
