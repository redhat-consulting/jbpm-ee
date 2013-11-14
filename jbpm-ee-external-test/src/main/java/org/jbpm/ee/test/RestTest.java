package org.jbpm.ee.test;

import javax.jws.WebService;

import org.jbpm.ee.client.RestClientFactory;
import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.support.KieReleaseId;

@WebService(targetNamespace="http://jbpm.org/v6/RestTest/wsdl", serviceName="RestTest")
public class RestTest {

	private static final KieReleaseId kri = new KieReleaseId("com.redhat.demo", "testProj", "1.0-SNAPSHOT");
	private static final String processId = "testProj.testProcess";
	public Long startProcess() {

		ProcessService processService =  RestClientFactory.getProcessService("http://localhost:8080/jbpm-services/rest");
		return processService.startProcess(kri, processId).getId();
	}
}
