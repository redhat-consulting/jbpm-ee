package org.jbpm.ee.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.TaskService;
import org.jbpm.ee.services.WorkItemService;
import org.jbpm.ee.support.KieReleaseId;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.task.model.Task;
import org.kie.api.task.model.TaskData;
import org.kie.api.task.model.TaskSummary;
import org.slf4j.Logger;

public abstract class BaseTest {
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(BaseTest.class);
	
	protected abstract ProcessService getProcessService();
	protected abstract TaskService getTaskService();
	protected abstract WorkItemService getWorkItemService();
	
	protected static final KieReleaseId taskTestReleaseId = new KieReleaseId("com.redhat.demo", "testProj", "1.0-SNAPSHOT");
	protected static final String taskTestProcessId = "testProj.testProcess";
	
	protected static final KieReleaseId loanTestReleaseId = new KieReleaseId("org.jbpm.jbpm-ee", "jbpm-ee-test-kjar", "1.0.0-SNAPSHOT");
	protected static final String loadTestProcessId = "testProj.testProcess";
	
	@WebMethod
	public Long startProcess() {
		ProcessService processService =  getProcessService();
		
		final String variableKey = "processString";
		
		Map<String, Object> processVariables = new HashMap<String, Object>();
		processVariables.put(variableKey, "Initial");
		
		ProcessInstance processInstance = processService.startProcess(taskTestReleaseId, taskTestProcessId, processVariables);
		LOG.info("Process Instance: "+processInstance.getId());
		
		return processInstance.getId();
	}
	
	@WebMethod
	public int taskCount() {
		TaskService taskService = getTaskService();;
		List<TaskSummary> tasks = taskService.getTasksAssignedAsPotentialOwner("abaxter", "en-UK");
		
		return tasks.size();
	}
	
	@WebMethod
	public String listTasks() {

		TaskService service = getTaskService();
		List<TaskSummary> tasks = service.getTasksAssignedAsPotentialOwner("abaxter", "en-UK");
		
		StringBuilder builder = new StringBuilder();
		for(TaskSummary summary : tasks) {
			builder.append("Task: "+summary.getId()+", "+summary.getName()+", "+summary.getStatus()+"\n");
		}
			
		return builder.toString();
	}
	
	@WebMethod
	public String completeTasks() {

		TaskService service = getTaskService();
		List<TaskSummary> tasks = service.getTasksAssignedAsPotentialOwner("abaxter", "en-UK");
		
		int i=0;
		for(TaskSummary summary : tasks) {
			service.claim(summary.getId(), "abaxter");
			service.start(summary.getId(), "abaxter");
			
			Map<String,Object> testResults = new HashMap<String,Object>();
			service.complete(summary.getId(), "abaxter", testResults);
			i++;
		}
		
		return "Completed: "+i;
	}
	
	@WebMethod
	public String describeWorkItemsForOpenTasks() {
		TaskService service = getTaskService();
		List<TaskSummary> tasks = service.getTasksAssignedAsPotentialOwner("abaxter", "en-UK");
		
		StringBuilder builder = new StringBuilder();
		for(TaskSummary taskSummary : tasks) {
			Task task = service.getTaskById(taskSummary.getId());
			TaskData data = task.getTaskData();
			
			WorkItem workItem = getWorkItemService().getWorkItem(data.getWorkItemId());
			
			builder.append(ReflectionToStringBuilder.toString(workItem));
			builder.append("\n");
		}
		
		return builder.toString();
	}
	

	@WebMethod
	public Long testLoanProcess() throws Exception {
		
		final String variableKey = "loanOrder";
		
		Map<String, Object> processVariables = new HashMap<String, Object>();
		LoanOrder order = new LoanOrder();
		order.setFirstName("Adam");
		order.setLastName("Baxter");
		order.setLoanAmount(500000L);
		processVariables.put(variableKey, order);
		
		ProcessService processService = getProcessService();
		
		ProcessInstance processInstance = processService.startProcess(loanTestReleaseId, loadTestProcessId, processVariables);
		return processInstance.getId();
	}
	
	
}
