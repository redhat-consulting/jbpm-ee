package org.jbpm.ee.client.rest;

import org.jboss.resteasy.client.ProxyFactory;
import org.jbpm.ee.client.rest.adapter.ProcessServiceAdapter;
import org.jbpm.ee.client.rest.adapter.RuleServiceAdapter;
import org.jbpm.ee.client.rest.adapter.TaskServiceAdapter;
import org.jbpm.ee.client.rest.adapter.WorkItemServiceAdapter;
import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.RuleService;
import org.jbpm.ee.services.TaskService;
import org.jbpm.ee.services.WorkItemService;
import org.jbpm.ee.services.rest.ProcessServiceRest;
import org.jbpm.ee.services.rest.RuleServiceRest;
import org.jbpm.ee.services.rest.TaskServiceRest;
import org.jbpm.ee.services.rest.WorkItemServiceRest;

public class RestClientFactory {
	
	private RestClientFactory() {
		// seal
	}
	
	public static ProcessService getProcessService(String url) {
		ProcessServiceRest client = ProxyFactory.create(ProcessServiceRest.class, url);
		return new ProcessServiceAdapter(client);
	}

	public static TaskService getTaskService(String url) {
		TaskServiceRest client = ProxyFactory.create(TaskServiceRest.class, url);
		return new TaskServiceAdapter(client);
	}
	
	public static WorkItemService getWorkItemService(String url) {
		WorkItemServiceRest client = ProxyFactory.create(WorkItemServiceRest.class, url);
		return new WorkItemServiceAdapter(client);
	}
	
	public static RuleService getRuleService(String url) {
		RuleServiceRest client = ProxyFactory.create(RuleServiceRest.class, url);
		return new RuleServiceAdapter(client);
	}
}
