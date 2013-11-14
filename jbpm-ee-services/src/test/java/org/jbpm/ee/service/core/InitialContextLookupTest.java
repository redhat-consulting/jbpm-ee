package org.jbpm.ee.service.core;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.arquillian.junit.Arquillian;
import org.jbpm.ee.services.ejb.local.ProcessServiceLocal;
import org.jbpm.ee.services.ejb.local.RuleServiceLocal;
import org.jbpm.ee.services.ejb.local.TaskServiceLocal;
import org.jbpm.ee.services.ejb.local.WorkItemServiceLocal;
import org.jbpm.ee.services.ejb.remote.ProcessServiceRemote;
import org.jbpm.ee.services.ejb.remote.RuleServiceRemote;
import org.jbpm.ee.services.ejb.remote.TaskServiceRemote;
import org.jbpm.ee.services.ejb.remote.WorkItemServiceRemote;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class InitialContextLookupTest extends BaseJBPMServiceTest {
	
	private final String TASK_SERVICE_REMOTE = "java:global/jbpm-services/TaskServiceBean!org.jbpm.ee.services.ejb.remote.TaskServiceRemote";
	private final String TASK_SERVICE_LOCAL = "java:global/jbpm-services/TaskServiceBean!org.jbpm.ee.services.ejb.local.TaskServiceLocal";

	private final String PROCESS_SERVICE_REMOTE = "java:global/jbpm-services/ProcessServiceBean!org.jbpm.ee.services.ejb.remote.ProcessServiceRemote";
	private final String PROCESS_SERVICE_LOCAL = "java:global/jbpm-services/ProcessServiceBean!org.jbpm.ee.services.ejb.local.ProcessServiceLocal";
	
	private final String WORKITEM_SERVICE_REMOTE = "java:global/jbpm-services/WorkItemServiceBean!org.jbpm.ee.services.ejb.remote.WorkItemServiceRemote";
	private final String WORKITEM_SERVICE_LOCAL = "java:global/jbpm-services/WorkItemServiceBean!org.jbpm.ee.services.ejb.local.WorkItemServiceLocal";
	
	private final String RULE_SERVICE_REMOTE = "java:global/jbpm-services/RuleServiceBean!org.jbpm.ee.services.ejb.remote.RuleServiceRemote";
	private final String RULE_SERVICE_LOCAL = "java:global/jbpm-services/RuleServiceBean!org.jbpm.ee.services.ejb.local.RuleServiceLocal";
	
	@Test
	public void testRemoteLookups() throws Exception {
		InitialContext ic = new InitialContext();
		TaskServiceRemote taskServiceRemote = InitialContextLookupTest.lookup(ic, TASK_SERVICE_REMOTE);
		ProcessServiceRemote processServiceRemote = InitialContextLookupTest.lookup(ic, PROCESS_SERVICE_REMOTE);
		RuleServiceRemote ruleServiceRemote = InitialContextLookupTest.lookup(ic, RULE_SERVICE_REMOTE);
		WorkItemServiceRemote workItemServiceRemote = InitialContextLookupTest.lookup(ic, WORKITEM_SERVICE_REMOTE);
		
		Assert.assertNotNull(taskServiceRemote);
		Assert.assertNotNull(processServiceRemote);
		Assert.assertNotNull(ruleServiceRemote);
		Assert.assertNotNull(workItemServiceRemote);
	}
	


	@Test
	public void testLocalLookups() throws Exception {
		InitialContext ic = new InitialContext();
		TaskServiceLocal taskService = InitialContextLookupTest.lookup(ic, TASK_SERVICE_LOCAL);
		ProcessServiceLocal processService = InitialContextLookupTest.lookup(ic, PROCESS_SERVICE_LOCAL);
		RuleServiceLocal ruleService = InitialContextLookupTest.lookup(ic, RULE_SERVICE_LOCAL);
		WorkItemServiceLocal workItemService = InitialContextLookupTest.lookup(ic, WORKITEM_SERVICE_LOCAL);
		
		Assert.assertNotNull(taskService);
		Assert.assertNotNull(processService);
		Assert.assertNotNull(ruleService);
		Assert.assertNotNull(workItemService);
	}
	
	
	
	
	
	
	
	
	private static <T> T lookup(InitialContext ic, String uri) throws NamingException {
		return (T)ic.lookup(uri);
	}
}
