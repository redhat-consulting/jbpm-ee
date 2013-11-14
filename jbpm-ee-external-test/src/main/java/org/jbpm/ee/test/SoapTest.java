package org.jbpm.ee.test;

import javax.jws.WebService;

import org.jbpm.ee.client.ClientException;
import org.jbpm.ee.client.SoapClientFactory;
import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.support.KieReleaseId;

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
}
