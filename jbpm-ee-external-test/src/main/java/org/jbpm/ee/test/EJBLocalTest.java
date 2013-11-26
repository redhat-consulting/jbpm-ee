package org.jbpm.ee.test;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.TaskService;
import org.jbpm.ee.services.WorkItemService;
import org.jbpm.ee.services.ejb.local.ProcessServiceLocal;
import org.jbpm.ee.services.ejb.local.TaskServiceLocal;
import org.jbpm.ee.services.ejb.local.WorkItemServiceLocal;
import org.jbpm.ee.test.exception.TestRuntimeException;
import org.slf4j.Logger;

@WebService(targetNamespace="http://jbpm.org/v6/EJBLocalTest/wsdl", serviceName="EJBLocalTest")
@LocalBean
@Stateless
public class EJBLocalTest extends BaseTest {

	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(EJBLocalTest.class);

	
	@EJB
	private ProcessServiceLocal processService;
	
	@EJB
	private TaskServiceLocal taskService;

	@EJB
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
