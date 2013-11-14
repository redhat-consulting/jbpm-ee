package org.jbpm.ee.client;

import org.jboss.resteasy.client.ProxyFactory;
import org.jbpm.ee.client.adapter.ProcessServiceAdapter;
import org.jbpm.ee.client.adapter.RuleServiceAdapter;
import org.jbpm.ee.client.adapter.TaskServiceAdapter;
import org.jbpm.ee.client.adapter.WorkItemServiceAdapter;
import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.RuleService;
import org.jbpm.ee.services.TaskService;
import org.jbpm.ee.services.WorkItemService;
import org.jbpm.ee.services.ws.ProcessServiceWS;
import org.jbpm.ee.services.ws.RuleServiceWS;
import org.jbpm.ee.services.ws.TaskServiceWS;
import org.jbpm.ee.services.ws.WorkItemServiceWS;

public class RestClientFactory {

	private RestClientFactory() {
		// seal
	}
	
	public static ProcessService getProcessService(String url) {
		ProcessServiceWS client = ProxyFactory.create(ProcessServiceWS.class, url);
		return new ProcessServiceAdapter(client);
	}

	public static TaskService getTaskService(String url) {
		TaskServiceWS client = ProxyFactory.create(TaskServiceWS.class, url);
		return new TaskServiceAdapter(client);
	}
	
	public static WorkItemService getWorkItemService(String url) {
		WorkItemServiceWS client = ProxyFactory.create(WorkItemServiceWS.class, url);
		return new WorkItemServiceAdapter(client);
	}
	
	public static RuleService getRuleService(String url) {
		RuleServiceWS client = ProxyFactory.create(RuleServiceWS.class, url);
		return new RuleServiceAdapter(client);
	}
}
