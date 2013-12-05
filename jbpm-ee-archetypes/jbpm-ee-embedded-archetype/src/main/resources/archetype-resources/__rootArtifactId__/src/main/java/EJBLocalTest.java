#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.jbpm.ee.services.AsyncCommandExecutor;
import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.TaskService;
import org.jbpm.ee.services.WorkItemService;
import org.jbpm.ee.services.ejb.local.AsyncCommandExecutorLocal;
import org.jbpm.ee.services.ejb.local.ProcessServiceLocal;
import org.jbpm.ee.services.ejb.local.TaskServiceLocal;
import org.jbpm.ee.services.ejb.local.WorkItemServiceLocal;
import ${package}.exception.TestRuntimeException;
import org.slf4j.Logger;

@WebService(targetNamespace="http://jbpm.org/v6/EJBLocalTest/wsdl", serviceName="EJBLocalTest")
@LocalBean
@Stateless
public class EJBLocalTest extends BaseEJBTest {

	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(EJBLocalTest.class);

	@EJB
	private AsyncCommandExecutorLocal asyncCommandExecutorService;
	
	@EJB
	private ProcessServiceLocal processService;
	
	@EJB
	private TaskServiceLocal taskService;

	@EJB
	private WorkItemServiceLocal workItemService;
	
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
}
