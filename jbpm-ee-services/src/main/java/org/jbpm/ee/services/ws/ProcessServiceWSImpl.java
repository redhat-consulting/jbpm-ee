
package org.jbpm.ee.services.ws;

import javax.ejb.EJB;
import javax.jws.WebService;

import org.jbpm.ee.services.ejb.local.ProcessServiceLocal;
import org.jbpm.ee.services.ws.exceptions.RemoteServiceException;
import org.jbpm.ee.services.ws.request.JaxbInitializeProcessRequest;
import org.kie.services.client.serialization.jaxb.impl.JaxbProcessInstanceResponse;

@WebService(targetNamespace="http://jbpm.org/v6/ProcessService/wsdl", serviceName="ProcessService", endpointInterface="org.jbpm.ee.services.ws.ProcessServiceWS")
public class ProcessServiceWSImpl implements ProcessServiceWS {

	@EJB
	private ProcessServiceLocal processRuntimeService;
	
	@Override
	public JaxbProcessInstanceResponse startProcess(String processId, JaxbInitializeProcessRequest request) {
		try {
			if(request.getVariables() == null) {
				return new JaxbProcessInstanceResponse(processRuntimeService.startProcess(request.getReleaseId(), processId));
			}
			else {
				return new JaxbProcessInstanceResponse(processRuntimeService.startProcess(request.getReleaseId(), processId, request.getVariables()));
			}
			
		} catch (Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public JaxbProcessInstanceResponse createProcessInstance(String processId, JaxbInitializeProcessRequest request) {
		try {
			return new JaxbProcessInstanceResponse(processRuntimeService.createProcessInstance(request.getReleaseId(), processId, request.getVariables()));
		} catch (Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public JaxbProcessInstanceResponse startProcessInstance(long processInstanceId) {
		try {
			return new JaxbProcessInstanceResponse(processRuntimeService.startProcessInstance(processInstanceId));
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public void signalEvent(String type, Object event, long processInstanceId) {
		try {
			this.processRuntimeService.signalEvent(type, event, processInstanceId);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public JaxbProcessInstanceResponse getProcessInstance(long processInstanceId) {
		try {
			org.kie.api.runtime.process.ProcessInstance instance = processRuntimeService.getProcessInstance(processInstanceId);
			
			if(instance != null) {
				return new JaxbProcessInstanceResponse(instance);
			}
			else {
				return null;
			}
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public void abortProcessInstance(long processInstanceId) {
		try {
			this.processRuntimeService.abortProcessInstance(processInstanceId);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
		
	}

}
