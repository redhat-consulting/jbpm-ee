package org.jbpm.ee.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.jbpm.ee.client.ClientException;
import org.jbpm.ee.client.SoapClientFactory;
import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.TaskService;
import org.jbpm.ee.support.KieReleaseId;
import org.kie.api.task.model.TaskSummary;

@WebService(targetNamespace="http://jbpm.org/v6/SoapTest/wsdl", serviceName="SOAPTest")
public class SoapTest {

	
	private static final KieReleaseId kri = new KieReleaseId("com.redhat.demo", "testProj", "1.0-SNAPSHOT");
	private static final String processId = "testProj.testProcess";
	public Long startProcess() {

		ProcessService processService;
		try {
			processService = SoapClientFactory.getProcessService("http://localhost:8080/jbpm-ee-services/ProcessService?wsdl");
			return processService.startProcess(kri, processId).getId();
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0L;
	}
	
	public String listTasks() {

		TaskService service;
		try {
			service = SoapClientFactory.getTaskService("http://localhost:8080/jbpm-ee-services/TaskService?wsdl");
			List<TaskSummary> tasks = service.getTasksAssignedAsPotentialOwner("abaxter", "en-UK");
			
			StringBuilder builder = new StringBuilder();
			for(TaskSummary summary : tasks) {
				builder.append("Task: "+summary.getId()+", "+summary.getName()+", "+summary.getStatus());
			}
			
			return builder.toString();
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String completeTasks() {

		TaskService service;
		try {
			service = SoapClientFactory.getTaskService("http://localhost:8080/jbpm-ee-services/TaskService?wsdl");
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
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
