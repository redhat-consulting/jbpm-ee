package org.jbpm.ee.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.jboss.ejb.client.EJBClientContext;
import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.RuleService;
import org.jbpm.ee.services.TaskService;
import org.jbpm.ee.services.WorkItemService;
import org.jbpm.ee.services.ejb.interceptors.SerializationInterceptor;
import org.jbpm.ee.services.model.KieReleaseId;
import org.jbpm.ee.services.model.rules.FactHandle;
import org.jbpm.ee.test.model.Account;
import org.jbpm.ee.test.model.OrderDetails;
import org.jbpm.ee.test.model.OrderEligibility;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.task.model.Status;
import org.kie.api.task.model.Task;
import org.kie.api.task.model.TaskData;
import org.kie.api.task.model.TaskSummary;
import org.slf4j.Logger;

public abstract class BaseTest {
	
	static {
		EJBClientContext.getCurrent().registerInterceptor(0, new SerializationInterceptor());
	}
	
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(BaseTest.class);
	
	protected abstract ProcessService getProcessService();
	protected abstract TaskService getTaskService();
	protected abstract WorkItemService getWorkItemService();
	protected abstract RuleService getRuleService();
	
	
	protected static final KieReleaseId taskTestReleaseId = new KieReleaseId("org.jbpm.jbpm-ee", "jbpm-ee-kjar-sample", "1.0.0-SNAPSHOT");
	protected static final String taskTestProcessId = "testTaskProcess.bpmn2";
	
	protected static final KieReleaseId loanTestReleaseId = new KieReleaseId("org.jbpm.jbpm-ee", "jbpm-ee-kjar-sample", "1.0.0-SNAPSHOT");
	protected static final String loanTestProcessId = "testWorkItemProcess.bpmn2";
	
	protected static final KieReleaseId ruleTestReleaseId = new KieReleaseId("org.jbpm.jbpm-ee", "jbpm-ee-kjar-sample", "1.0.0-SNAPSHOT");
	protected static final String ruleTestProcessId = "testRuleProcess.bpmn2";
	
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
	public void executeTestRun() {
		
		ProcessService processService = getProcessService();
		final String variableKey = "processString";
		
		Map<String, Object> processVariables = new HashMap<String, Object>();
		processVariables.put(variableKey, "Initial");
		
		LOG.info("Starting Processes");
/*		for(int i = 0; i < 3000; i++) {
			ProcessInstance processInstance = processService.startProcess(taskTestReleaseId, taskTestProcessId, processVariables);
			
		}*/
		LOG.info("Finished Starting Processes");
		TaskService taskService = getTaskService();
		
		List<Status> statusList = new ArrayList<Status>();
        statusList.add(Status.Ready);
        
        LOG.info("Searching for Ready Tasks");
        List<TaskSummary> tSummaryList = taskService.getTasksAssignedAsPotentialOwner("abaxter", "en-UK");
        LOG.info("TaskList Count: " + tSummaryList.size());
        //LOG.info("Task Status is: " + tSummaryList.get(0).getStatus());
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
	public String completeTaskAsNull() {

		TaskService service = getTaskService();
		List<TaskSummary> tasks = service.getTasksAssignedAsPotentialOwner("abaxter", "en-UK");
		
		int i=0;
		for(TaskSummary summary : tasks) {
			if(summary.getActualOwner() == null || !"abaxter".equals(summary.getActualOwner().getId())) {
					service.claim(summary.getId(), "abaxter");
					service.start(summary.getId(), "abaxter");
			}
			
			service.complete(summary.getId(), "abaxter", null);
			i++;
		}
		
		return "Completed: "+i;
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
			testResults.put("ResultValue", "Hello World");
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
		
		ProcessInstance processInstance = processService.startProcess(loanTestReleaseId, loanTestProcessId, processVariables);
		return processInstance.getId();
	}
	

	
	@WebMethod
	public Long startProcessNullValues() {
		ProcessService processService =  getProcessService();
		
		Map<String, Object> processVariables = null;
		ProcessInstance processInstance = processService.startProcess(taskTestReleaseId, taskTestProcessId, processVariables);
		LOG.info("Process Instance: "+processInstance.getId());
		
		return processInstance.getId();
	}
	
	@WebMethod
	public String getProcessVariables(long processInstanceId) {
		ProcessService processService =  getProcessService();
		Map<String, Object> variables = processService.getProcessInstanceVariables(processInstanceId);
		
		StringBuilder builder = new StringBuilder();
		builder.append("Total variables: "+variables.size()+"\n");
		for(String key : variables.keySet()) {
			builder.append("Variable: "+key+" :: "+ReflectionToStringBuilder.toString(variables.get(key))+"\n");
		}
		return builder.toString();
	}
	
	@WebMethod
	public String getProcessVariable(long processInstanceId, String key) {
		ProcessService processService =  getProcessService();
		Object variable = processService.getProcessInstanceVariable(processInstanceId, key);
		
		StringBuilder builder = new StringBuilder();
		builder.append("Variable: "+key+" :: "+ReflectionToStringBuilder.toString(variable)+"\n");
		return builder.toString();
	}
	
	
	@WebMethod
	public String setProcessVariable(long processInstanceId, String key, String firstName) {
		ProcessService processService =  getProcessService();
		Object variable = processService.getProcessInstanceVariable(processInstanceId, key);
		
		if(variable != null && variable instanceof LoanOrder) {
			LoanOrder order = (LoanOrder)variable;
			order.setFirstName(firstName);
			processService.setProcessInstanceVariable(processInstanceId, key, order);
		}
		
		return getProcessVariable(processInstanceId, key);
	}
	
	
	@WebMethod
	public void testRuleProcess() throws ParseException {
		
		ProcessService processService =  getProcessService();
		
		Map<String, Object> processVariables = new HashMap<String, Object>();
		
		ProcessInstance processInstance = processService.startProcess(ruleTestReleaseId, ruleTestProcessId, processVariables);
		LOG.info("Process Instance: "+processInstance.getId());
		
		long processInstanceId =  processInstance.getId();
		
		RuleService ruleService = getRuleService();
		
		Account accountOpen = new Account("O");
        Account accountClosed = new Account("C");
        
        FactHandle accountOpen_fh = ruleService.insert(processInstanceId, accountOpen);
        FactHandle accountClosed_fh = ruleService.insert(processInstanceId, accountClosed);
        
        SimpleDateFormat stdDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date today = stdDateFormat.parse("2014/03/10");
        Date yesterday = stdDateFormat.parse("2014/03/09");
        Date lastYear = stdDateFormat.parse("2013/03/10");
        
        OrderDetails lastYearOrder = new OrderDetails(lastYear, today);
        OrderDetails thisYearOrder = new OrderDetails(yesterday, today);
        
        OrderEligibility lastYearEligibility = new OrderEligibility(lastYearOrder);
        OrderEligibility thisYearEligibility = new OrderEligibility(thisYearOrder);
        
        FactHandle lye_fh = ruleService.insert(processInstanceId, lastYearEligibility);
        FactHandle tye_fh = ruleService.insert(processInstanceId, thisYearEligibility);
        
        ruleService.fireAllRules(processInstanceId);
        
        accountOpen = (Account) ruleService.getObject(processInstanceId, accountOpen_fh);
        accountClosed = (Account) ruleService.getObject(processInstanceId, accountClosed_fh);
        
        System.out.println("Account Open eligibility: " + accountOpen.getAccountEligible());
        System.out.println("Account Closed eligibility: " + accountClosed.getAccountEligible());
        
        lastYearEligibility = (OrderEligibility) ruleService.getObject(processInstanceId, lye_fh);
        thisYearEligibility = (OrderEligibility) ruleService.getObject(processInstanceId, tye_fh);
        
        System.out.println("Last Year Eligibility: " + lastYearEligibility.getOrderEligibile());
        System.out.println("This Year Eligibility: " + thisYearEligibility.getOrderEligibile());
        processService.signalEvent(processInstanceId, "complete", null);
        
	}
}
