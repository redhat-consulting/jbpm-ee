package org.jbpm.ee.client.rest.adapter;

import java.util.Map;

import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.rest.ProcessServiceRest;
import org.jbpm.ee.services.rest.request.JaxbInitializeProcessRequest;
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

	private final ProcessServiceRest restService;
	
	public ProcessServiceAdapter(ProcessServiceRest restService) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public ProcessInstance createProcessInstance(KieReleaseId releaseId, String processId, Map<String, Object> parameters) {
		try {
			JaxbInitializeProcessRequest request = new JaxbInitializeProcessRequest();
			request.setReleaseId(releaseId);
			request.setVariables(parameters);
			
			return this.restService.createProcessInstance(processId, request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public ProcessInstance startProcessInstance(long processInstanceId) {
		return this.restService.startProcessInstance(processInstanceId);
	}

	@Override
	public void signalEvent(String type, Object event, long processInstanceId) {
		this.restService.signalEvent(type, event, processInstanceId);
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
