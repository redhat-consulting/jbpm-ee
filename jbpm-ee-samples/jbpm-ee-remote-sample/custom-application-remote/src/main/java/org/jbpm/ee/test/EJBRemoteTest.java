package org.jbpm.ee.test;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebService;

import org.jbpm.ee.services.AsyncCommandExecutor;
import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.RuleService;
import org.jbpm.ee.services.TaskService;
import org.jbpm.ee.services.WorkItemService;
import org.jbpm.ee.services.ejb.remote.AsyncCommandExecutorRemote;
import org.jbpm.ee.services.ejb.remote.ProcessServiceRemote;
import org.jbpm.ee.services.ejb.remote.RuleServiceRemote;
import org.jbpm.ee.services.ejb.remote.TaskServiceRemote;
import org.jbpm.ee.services.ejb.remote.WorkItemServiceRemote;
import org.slf4j.Logger;

@WebService(targetNamespace="http://jbpm.org/v6/EJBRemoteTest/wsdl", serviceName="EJBRemoteTest")
@LocalBean
@Stateless
public class EJBRemoteTest extends BaseEJBTest {

	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(EJBRemoteTest.class);

	@EJB(lookup = "java:global/jbpm-ee-services/AsyncCommandExecutorBean!org.jbpm.ee.services.ejb.remote.AsyncCommandExecutorRemote")
	private AsyncCommandExecutorRemote asyncCommandExecutorService;
	
	
	@EJB(lookup = "java:global/jbpm-ee-services/ProcessServiceBean!org.jbpm.ee.services.ejb.remote.ProcessServiceRemote")
	private ProcessServiceRemote processService;
	
	@EJB(lookup = "java:global/jbpm-ee-services/TaskServiceBean!org.jbpm.ee.services.ejb.remote.TaskServiceRemote")
	private TaskServiceRemote taskService;

	@EJB(lookup = "java:global/jbpm-ee-services/WorkItemServiceBean!org.jbpm.ee.services.ejb.remote.WorkItemServiceRemote")
	private WorkItemServiceRemote workItemService;
	
	@EJB(lookup = "java:global/jbpm-ee-services/RuleServiceBean!org.jbpm.ee.services.ejb.remote.RuleServiceRemote")
	private RuleServiceRemote ruleService;
	
	@Override
	protected AsyncCommandExecutor getAsyncCommandExecutor() {
		return asyncCommandExecutorService;
	}
	
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

	@Override
	protected RuleService getRuleService() {
		return ruleService;
	}

}
