package org.jbpm.ee.client.adapter;

import java.util.Map;

import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.model.KieReleaseId;
import org.jbpm.ee.services.ws.ProcessServiceWS;
import org.jbpm.ee.services.ws.request.JaxbInitializeProcessRequest;
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

	private final ProcessServiceWS processService;
	
	public ProcessServiceAdapter(ProcessServiceWS restService) {
		this.processService = restService;
	}
	
	@Override
	public ProcessInstance startProcess(KieReleaseId releaseId, String processId) {
		JaxbInitializeProcessRequest request = new JaxbInitializeProcessRequest();
		request.setReleaseId(releaseId);
		
		return this.processService.startProcess(processId, request);
	}

	@Override
	public ProcessInstance startProcess(KieReleaseId releaseId, String processId, Map<String, Object> parameters) {
		try {
			JaxbInitializeProcessRequest request = new JaxbInitializeProcessRequest();
			request.setReleaseId(releaseId);
			request.setVariables(parameters);
			
			return this.processService.startProcess(processId, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void signalEvent(long processInstanceId, String type, Object event) {
		this.processService.signalEvent(processInstanceId, type, event);
	}

	@Override
	public ProcessInstance getProcessInstance(long processInstanceId) {
		return this.processService.getProcessInstance(processInstanceId);
	}

	@Override
	public void abortProcessInstance(long processInstanceId) {
		this.processService.abortProcessInstance(processInstanceId);
	}

}
