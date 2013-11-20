#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package};

import javax.jws.WebService;

import org.jbpm.ee.client.ClientException;
import org.jbpm.ee.client.SoapClientFactory;
import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.TaskService;
import org.jbpm.ee.services.WorkItemService;

@WebService(targetNamespace="http://jbpm.org/v6/SoapService/wsdl", serviceName="SOAPService")
public class SoapService extends BaseService {

	private TaskService cachedTaskService = null;
	private ProcessService cachedProcessService = null;
	private WorkItemService cachedWorkItemService = null;
	
	private static final String PROCESS_SERVICE_URL = "http://localhost:8080/jbpm-ee-services/ProcessService?wsdl";
	private static final String TASK_SERVICE_URL = "http://localhost:8080/jbpm-ee-services/TaskService?wsdl"; 
	private static final String WORK_ITEM_SERVICE_URL = "http://localhost:8080/jbpm-ee-services/WorkItemService?wsdl"; 
	
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

	@Override
	protected WorkItemService getWorkItemService() {
		if(cachedWorkItemService == null) {
			try {
				cachedWorkItemService = SoapClientFactory.getWorkItemService(WORK_ITEM_SERVICE_URL);
			} catch (ClientException e) {
				throw new RuntimeException("Unable to create WorkItemService from ["+WORK_ITEM_SERVICE_URL+"]. Please validate URL.", e);
			}
		}
		return cachedWorkItemService;
	}
}
