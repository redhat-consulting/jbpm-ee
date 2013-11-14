package org.jbpm.ee.service.core;

import org.jboss.arquillian.junit.Arquillian;
import org.jbpm.ee.client.ClientException;
import org.jbpm.ee.client.SoapClientFactory;
import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.TaskService;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
public class SoapServiceBeanTest extends JBPMServiceTest {

	private static final Logger LOG = LoggerFactory.getLogger(SoapServiceBeanTest.class);

	public ProcessService getProcessService() {
		try {
			return SoapClientFactory.getProcessService("http://localhost:8080/jbpm-services/ProcessService?wsdl");
		} catch (ClientException e) {
			throw new RuntimeException(e);
		}
	}
	
	public TaskService getTaskService() {
		try {
			return SoapClientFactory.getTaskService("http://localhost:8080/jbpm-services/TaskService?wsdl");
		} catch (ClientException e) {
			throw new RuntimeException(e);
		}
	}
	
}
