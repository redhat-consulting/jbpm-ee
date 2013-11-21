package org.jbpm.ee.client.adapter;

import java.util.Map;

import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.ws.ProcessServiceWS;
import org.jbpm.ee.services.ws.request.JaxbInitializeProcessRequest;
import org.jbpm.ee.support.KieReleaseId;
import org.kie.api.runtime.process.ProcessInstance;

/**
 * Adapts the Rest Service JAXB responses to the ProcessRuntime interface. 
 * 
 * @see ProcessService
 * 
 * @author bradsdavis
 *
 */
public class ProcessServiceAdapter implements ProcessService {

	private final ProcessServiceWS restService;
	
	public ProcessServiceAdapter(ProcessServiceWS restService) {
		this.restService = restService;
	}
	
	@Override
	public ProcessInstance startProcess(KieReleaseId releaseId, String processId) {
		JaxbInitializeProcessRequest request = new JaxbInitializeProcessRequest();
		request.setReleaseId(releaseId);
		
		return this.restService.startProcess(processId, request);
	}

	@Override
	public ProcessInstance startProcess(KieReleaseId releaseId, String processId, Map<String, Object> parameters) {
		try {
			JaxbInitializeProcessRequest request = new JaxbInitializeProcessRequest();
			request.setReleaseId(releaseId);
			request.setVariables(parameters);
			
			return this.restService.startProcess(processId, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public ProcessInstance startProcessInstance(long processInstanceId) {
		return this.restService.startProcessInstance(processInstanceId);
	}

	@Override
	public void signalEvent(long processInstanceId, String type, Object event) {
		this.restService.signalEvent(processInstanceId, type, event);
	}

	@Override
	public ProcessInstance getProcessInstance(long processInstanceId) {
		return this.restService.getProcessInstance(processInstanceId);
	}

	@Override
	public void abortProcessInstance(long processInstanceId) {
		this.restService.abortProcessInstance(processInstanceId);
	}

}
