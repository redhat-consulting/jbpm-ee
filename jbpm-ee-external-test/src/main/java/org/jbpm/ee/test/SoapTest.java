package org.jbpm.ee.test;

import javax.jws.WebService;

import org.jbpm.ee.client.ClientException;
import org.jbpm.ee.client.SoapClientFactory;
import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.TaskService;

@WebService(targetNamespace="http://jbpm.org/v6/SoapTest/wsdl", serviceName="SOAPTest")
public class SoapTest extends BaseTest {

	private TaskService cachedTaskService = null;
	private ProcessService cachedProcessService = null;
	
	private static final String PROCESS_SERVICE_URL = "http://localhost:8080/jbpm-ee-services/ProcessService?wsdl";
	private static final String TASK_SERVICE_URL = "http://localhost:8080/jbpm-ee-services/TaskService?wsdl"; 
	
	/**
	 * Creates the ProcessService & caches the instance for reuse.  
	 */
	@Override
	protected ProcessService getProcessService() {
		if(cachedProcessService == null) {
			try {
				cachedProcessService = SoapClientFactory.getProcessService(PROCESS_SERVICE_URL); 
			} catch (ClientException e) {
				throw new RuntimeException("Unable to create process service from ["+PROCESS_SERVICE_URL+"]. Please validate URL.", e);
			}
		}
		return cachedProcessService;
		
	}

	/**
	 * Creates the TaskService & caches the instance for reuse.  
	 */
	@Override
	protected TaskService getTaskService() {
		if(cachedTaskService == null) {
			try {
				cachedTaskService = SoapClientFactory.getTaskService(TASK_SERVICE_URL);
			} catch (ClientException e) {
				throw new RuntimeException("Unable to create TaskService from ["+TASK_SERVICE_URL+"]. Please validate URL.", e);
			}
		}
		return cachedTaskService;
	}
}
