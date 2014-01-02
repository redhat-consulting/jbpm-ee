package org.jbpm.ee.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

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

/**
 * Leverage this class to setup the SOAP proxy to the jBPM services.
 * 
 * @author bradsdavis
 *
 */
public class SoapClientFactory {

	private SoapClientFactory() {
		//seal
	}
	
	public static ProcessService getProcessService(String url) throws ClientException {
		Service proxyService;
		try {
			proxyService = Service.create(new URL(url), new QName("http://jbpm.org/v6/ProcessService/wsdl", "ProcessService"));
			ProcessServiceWS client = proxyService.getPort(ProcessServiceWS.class);
			return new ProcessServiceAdapter(client);
		} catch (MalformedURLException e) {
			throw new ClientException("Exception creating client.", e);
		}
	}

	public static TaskService getTaskService(String url) throws ClientException {
		Service proxyService;
		try {
			proxyService = Service.create(new URL(url), new QName("http://jbpm.org/v6/TaskService/wsdl", "TaskService"));
			TaskServiceWS client = proxyService.getPort(TaskServiceWS.class);
			return new TaskServiceAdapter(client);
		} catch (MalformedURLException e) {
			throw new ClientException("Exception creating client.", e);
		}
	}

	public static WorkItemService getWorkItemService(String url) throws ClientException {
		Service proxyService;
		try {
			proxyService = Service.create(new URL(url), new QName("http://jbpm.org/v6/WorkItemService/wsdl", "WorkItemService"));
			WorkItemServiceWS client = proxyService.getPort(WorkItemServiceWS.class);
			return new WorkItemServiceAdapter(client);
		} catch (MalformedURLException e) {
			throw new ClientException("Exception creating client.", e);
		}
	}

	public static RuleService getRuleService(String url) throws ClientException {
		Service proxyService;
		try {
			proxyService = Service.create(new URL(url), new QName("http://jbpm.org/v6/RuleService/wsdl", "RuleService"));
			RuleServiceWS client = proxyService.getPort(RuleServiceWS.class);
			return new RuleServiceAdapter(client);
		} catch (MalformedURLException e) {
			throw new ClientException("Exception creating client.", e);
		}
	}
}
