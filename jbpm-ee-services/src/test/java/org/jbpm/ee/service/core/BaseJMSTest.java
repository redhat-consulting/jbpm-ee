package org.jbpm.ee.service.core;

import static org.jbpm.ee.test.util.KJarUtil.createKieJar;
import static org.jbpm.ee.test.util.KJarUtil.getPom;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.kie.scanner.MavenRepository.getMavenRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.drools.compiler.kproject.ReleaseIdImpl;
import org.drools.core.command.impl.GenericCommand;
import org.drools.core.command.runtime.process.GetProcessInstanceCommand;
import org.drools.core.command.runtime.process.StartProcessCommand;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jbpm.ee.services.ejb.local.AsyncCommandExecutorLocal;
import org.jbpm.ee.support.KieReleaseId;
import org.jbpm.services.task.commands.ClaimTaskCommand;
import org.jbpm.services.task.commands.CompleteTaskCommand;
import org.jbpm.services.task.commands.GetTaskAssignedAsPotentialOwnerCommand;
import org.jbpm.services.task.commands.StartTaskCommand;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieServices;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.model.TaskSummary;
import org.kie.scanner.MavenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
public class BaseJMSTest extends BaseJBPMServiceTest {

	private static final Logger LOG = LoggerFactory.getLogger(BaseJMSTest.class);
	
	private static final KieReleaseId kri = new KieReleaseId("com.redhat.demo", "testProj", "1.0-SNAPSHOT");
	
	@EJB
	AsyncCommandExecutorLocal cmdExecutor;
	
	@Before
    public void prepare() {
		KieServices ks = KieServices.Factory.get();
        List<String> processes = new ArrayList<String>();
        processes.add("src/test/resources/kjar/testProcess.bpmn2");
        ReleaseIdImpl releaseID = new ReleaseIdImpl(kri.getGroupId(), kri.getArtifactId(), kri.getVersion());
        InternalKieModule kjar = createKieJar(ks, releaseID, processes);
        File pom = new File("target/kmodule", "pom.xml");
        pom.getParentFile().mkdir();
        try {
            FileOutputStream fs = new FileOutputStream(pom);
            fs.write(getPom(kri).getBytes());
            fs.close();
        } catch (Exception e) {
            
        }
        MavenRepository repository = getMavenRepository();
        repository.deployArtifact(releaseID, kjar, pom);
    }
	
	private Object executeReturnsResponse(KieReleaseId releaseId, GenericCommand<?> command ) {
		String correlationId = cmdExecutor.execute(releaseId, command);
		int count = 0;
		Object response = null;
		while (response == null && count < 2) {
			response = cmdExecutor.pollResponse(correlationId);
			count += 1;
		}
		// A ProcessInstance will be returned null if getProcessInstance(Long) is called and the process is no longer active
		// This should be sufficient for testing purposes
		if (response == null && count == 2) {
			throw new RuntimeException("No response returned");
		}
		return response;
	}
	
	private void executeNoResponse(KieReleaseId releaseId, GenericCommand<?> command ) throws InterruptedException {
		cmdExecutor.execute(releaseId, command);
		// Since we're doing linear processing on an async system, we need to sleep to make sure the actions get handled
		// The better way to handle this would be to check the ProcessInstance information to see where the process is at
		Thread.sleep(4000);
	}
	
	@Test
	@Transactional(value=TransactionMode.DEFAULT)
	public void testSimpleProcess() throws Exception {
		final String processString = "testProj.testProcess";
		final String variableKey = "processString";
		
		Map<String, Object> processVariables = new HashMap<String, Object>();
		processVariables.put(variableKey, "Initial");
		
		GetTaskAssignedAsPotentialOwnerCommand getTasks = new GetTaskAssignedAsPotentialOwnerCommand("abaxter", "en-UK");
		
		List<TaskSummary> tasks = (List<TaskSummary>) executeReturnsResponse(null, getTasks);
		
		int initialCount = tasks.size();
		LOG.info("Number of tasks: " + initialCount);
		StartProcessCommand startProcess = new StartProcessCommand(processString, processVariables);
		ProcessInstance processInstance = (ProcessInstance) executeReturnsResponse(kri, startProcess);
		
		assertEquals(1, processInstance.getState());
		
		tasks = (List<TaskSummary>) executeReturnsResponse(null, getTasks);
		assertNotNull(tasks);
        assertEquals(initialCount + 1, tasks.size());
	
        for(TaskSummary summary : tasks) {
			LOG.info("Task: " + summary.getId());
		}
        
        long taskId = tasks.get(tasks.size()-1).getId();
        
        Map<String,Object> testResults = new HashMap<String,Object>();
        ClaimTaskCommand claimCommand = new ClaimTaskCommand(taskId, "abaxter");
        executeNoResponse(null, claimCommand);
        
        StartTaskCommand startCommand = new StartTaskCommand(taskId, "abaxter");
        executeNoResponse(null, startCommand);
        
        CompleteTaskCommand completeCommand = new CompleteTaskCommand(taskId, "abaxter", testResults);
        executeNoResponse(null, completeCommand);
        
        tasks = (List<TaskSummary>) executeReturnsResponse(null, getTasks);
        assertNotNull(tasks);
        assertEquals(initialCount, tasks.size());
        
        LOG.info("Looking up process instance: "+processInstance.getId());
        GetProcessInstanceCommand getProcessInstance = new GetProcessInstanceCommand(processInstance.getId());
        processInstance = (ProcessInstance) executeReturnsResponse(null, getProcessInstance);
        assertNull(processInstance);
	}
}
