package org.jbpm.ee.service.core;

import org.jboss.arquillian.junit.Arquillian;
import org.jbpm.ee.client.rest.RestClientFactory;
import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.TaskService;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
public class RestServiceBeanTest extends JBPMServiceTest {

	private static final Logger LOG = LoggerFactory.getLogger(RestServiceBeanTest.class);

	public ProcessService getProcessService() {
		return RestClientFactory.getProcessService("http://localhost:8080/test-jbpm-services/rest");
	}
	
	public TaskService getTaskService() {
		return RestClientFactory.getTaskService("http://localhost:8080/test-jbpm-services/rest");
	}
	
}
