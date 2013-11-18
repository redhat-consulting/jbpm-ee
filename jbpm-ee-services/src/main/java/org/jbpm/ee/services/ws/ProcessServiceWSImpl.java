
package org.jbpm.ee.services.ws;

import javax.ejb.EJB;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.jbpm.ee.services.ejb.local.ProcessServiceLocal;
import org.jbpm.ee.services.ejb.model.process.ProcessInstance;
import org.jbpm.ee.services.ws.exceptions.RemoteServiceException;
import org.jbpm.ee.services.ws.request.JaxbInitializeProcessRequest;

@WebService(targetNamespace="http://jbpm.org/v6/ProcessService/wsdl", serviceName="ProcessService", endpointInterface="org.jbpm.ee.services.ws.ProcessServiceWS")
public class ProcessServiceWSImpl implements ProcessServiceWS {

	@EJB
	private ProcessServiceLocal processRuntimeService;
	
	@Override
	public ProcessInstance startProcess(String processId, JaxbInitializeProcessRequest request) {
		try {
			if(request.getVariables() == null) {
				return (ProcessInstance)processRuntimeService.startProcess(request.getReleaseId(), processId);
			}
			else {
				return (ProcessInstance)processRuntimeService.startProcess(request.getReleaseId(), processId, request.getVariables());
			}
			
		} catch (Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public ProcessInstance createProcessInstance(String processId, JaxbInitializeProcessRequest request) {
		try {
			return (ProcessInstance)processRuntimeService.createProcessInstance(request.getReleaseId(), processId, request.getVariables());
		} catch (Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public ProcessInstance startProcessInstance(long processInstanceId) {
		try {
			return (ProcessInstance)processRuntimeService.startProcessInstance(processInstanceId);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public void signalEvent(long processInstanceId, String type, Object event) {
		try {
			this.processRuntimeService.signalEvent(processInstanceId, type, event);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public ProcessInstance getProcessInstance(long processInstanceId) {
		try {
			return (ProcessInstance)processRuntimeService.getProcessInstance(processInstanceId);
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
