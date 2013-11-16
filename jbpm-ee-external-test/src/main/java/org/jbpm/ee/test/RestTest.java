package org.jbpm.ee.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.apache.log4j.spi.LoggerFactory;
import org.jbpm.ee.client.RestClientFactory;
import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.TaskService;
import org.jbpm.ee.support.KieReleaseId;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.model.TaskSummary;
import org.slf4j.Logger;

@WebService(targetNamespace="http://jbpm.org/v6/RestTest/wsdl", serviceName="RestTest")
public class RestTest {

	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(RestTest.class);
	
	private static final KieReleaseId kri = new KieReleaseId("com.redhat.demo", "testProj", "1.0-SNAPSHOT");
	private static final String processId = "testProj.testProcess";
	public Long startProcess() {

		ProcessService processService =  RestClientFactory.getProcessService("http://localhost:8080/jbpm-ee-services/rest");
		
		final String variableKey = "processString";
		
		Map<String, Object> processVariables = new HashMap<String, Object>();
		processVariables.put(variableKey, "Initial");
		
		ProcessInstance processInstance = processService.startProcess(kri, processId, processVariables);
		LOG.info("Process Instance: "+processInstance.getId());
		
		return processInstance.getId();
	}
	
	public Long taskCount() {
		TaskService taskService = RestClientFactory.getTaskService("http://localhost:8080/jbpm-ee-services/rest");
		List<TaskSummary> tasks = taskService.getTasksAssignedAsPotentialOwner("abaxter", "en-UK");
		
		return new Long(tasks.size());
	}
}
