package org.jbpm.ee.test;

import javax.ejb.EJB;
import javax.jws.WebService;

import org.jbpm.ee.services.ejb.remote.ProcessServiceRemote;
import org.jbpm.ee.support.KieReleaseId;

@WebService(targetNamespace="http://jbpm.org/v6/EJBInjectTest/wsdl", serviceName="EJBInjectTest")
public class EJBInjectTest {

	@EJB(lookup = "java:global/jbpm-ee-services/ProcessServiceBean!org.jbpm.ee.services.ejb.remote.ProcessServiceRemote")
	ProcessServiceRemote processService;
	
	private static final KieReleaseId kri = new KieReleaseId("com.redhat.demo", "testProj", "1.0-SNAPSHOT");
	
	private static final String processId = "testProj.testProcess";
	
	public Long startProcess() {
		return processService.startProcess(kri, processId).getId();
	}
}
