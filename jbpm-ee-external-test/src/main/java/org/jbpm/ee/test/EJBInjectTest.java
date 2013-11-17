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
import org.jbpm.ee.services.ejb.remote.ProcessServiceRemote;
import org.jbpm.ee.services.ejb.remote.TaskServiceRemote;
import org.jbpm.ee.test.exception.TestRuntimeException;
import org.slf4j.Logger;

@WebService(targetNamespace="http://jbpm.org/v6/EJBInjectTest/wsdl", serviceName="EJBInjectTest")
@LocalBean
@Stateless
public class EJBInjectTest extends BaseTest {

	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(EJBInjectTest.class);

	@Inject
	private UserTransaction tx;
	
	@EJB(lookup = "java:global/jbpm-ee-services/ProcessServiceBean!org.jbpm.ee.services.ejb.remote.ProcessServiceRemote")
	private ProcessServiceRemote processService;
	
	@EJB(lookup = "java:global/jbpm-ee-services/TaskServiceBean!org.jbpm.ee.services.ejb.remote.TaskServiceRemote")
	private TaskServiceRemote taskService;
	
	@Override
	protected ProcessService getProcessService() {
		return processService;
	}

	@Override
	protected TaskService getTaskService() {
		return taskService;
	}
	
	/**
	 * List number of tasks; creates a new process; rolls back.
	 * Number of tasks should remain consistent.
	 */
	@WebMethod
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void startProcessThenRollback() {
		int taskCountBefore = taskCount();
		LOG.info("Tasks Before: "+taskCountBefore);
		try {
			Long id = this.startProcess();
			LOG.info("Created process: "+id);
			generateException();
		}
		catch(RuntimeException e) {
			int taskCountAfter = taskCount();
			LOG.info("Tasks After: "+taskCountAfter);
			throw e;
		}
	}
	
	private void generateException() {
		throw new TestRuntimeException("Exception to show rollback.");
	}
}
