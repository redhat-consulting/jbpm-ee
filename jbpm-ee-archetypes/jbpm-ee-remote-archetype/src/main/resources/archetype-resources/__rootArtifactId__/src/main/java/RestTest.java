#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import javax.jws.WebService;

import org.jbpm.ee.client.RestClientFactory;
import org.jbpm.ee.services.AsyncCommandExecutor;
import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.TaskService;
import org.jbpm.ee.services.WorkItemService;

@WebService(targetNamespace="http://jbpm.org/v6/RestTest/wsdl", serviceName="RestTest")
public class RestTest extends BaseTest {

	private static final String REST_URL = "http://localhost:8080/jbpm-ee-services/rest";
	
	protected AsyncCommandExecutor cachedAsyncCommandExecutorService = null;
	protected ProcessService cachedProcessService = null;
	protected TaskService cachedTaskService = null;
	protected WorkItemService cachedWorkItemService = null;
	

	/**
	 * Creates the ProcessService & caches the instance for reuse.  
	 */
	@Override
	protected ProcessService getProcessService() {
		if(cachedProcessService == null) {
			cachedProcessService = RestClientFactory.getProcessService(REST_URL);
		}
		return cachedProcessService; 
	}

	/**
	 * Creates the TaskService & caches the instance for reuse.  
	 */
	@Override
	protected TaskService getTaskService() {
		if(cachedTaskService == null) {
			cachedTaskService = RestClientFactory.getTaskService(REST_URL);
		}
		
		return cachedTaskService;
	}


	@Override
	protected WorkItemService getWorkItemService() {
		if(cachedWorkItemService == null) {
			cachedWorkItemService = RestClientFactory.getWorkItemService(REST_URL);
		}
		
		return cachedWorkItemService;
	}
}
