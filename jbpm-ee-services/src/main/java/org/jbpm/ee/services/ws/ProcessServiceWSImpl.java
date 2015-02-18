
package org.jbpm.ee.services.ws;

import java.io.Serializable;
import java.util.Map;

import javax.ejb.EJB;
import javax.jws.HandlerChain;
import javax.jws.WebService;

import org.jbpm.ee.services.ejb.local.ProcessServiceLocal;
import org.jbpm.ee.services.model.JaxbMapResponse;
import org.jbpm.ee.services.model.JaxbObjectRequest;
import org.jbpm.ee.services.model.JaxbObjectResponse;
import org.jbpm.ee.services.model.KieReleaseId;
import org.jbpm.ee.services.model.process.ProcessInstance;
import org.jbpm.ee.services.ws.exceptions.RemoteServiceException;
import org.jbpm.ee.services.ws.request.JaxbInitializeProcessRequest;

/**
 * @see ProcessServiceWS
 * @author bradsdavis
 *
 */
@WebService(targetNamespace="http://jbpm.org/v6/ProcessService/wsdl", serviceName="ProcessService", endpointInterface="org.jbpm.ee.services.ws.ProcessServiceWS")
@HandlerChain(file="jbpm-context-handler.xml")
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

	@Override
	public void setProcessInstanceVariable(long processInstanceId, String variableName, JaxbObjectRequest variable) {
		try {
			this.processRuntimeService.setProcessInstanceVariable(processInstanceId, variableName, variable.getObject());
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public JaxbObjectResponse getProcessInstanceVariable(long processInstanceId, String variableName) {
		try {
			return new JaxbObjectResponse((Serializable)this.processRuntimeService.getProcessInstanceVariable(processInstanceId, variableName));
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public JaxbMapResponse getProcessInstanceVariables(long processInstanceId) {
		try {
			Map<String, Object> variables = this.processRuntimeService.getProcessInstanceVariables(processInstanceId);
			JaxbMapResponse response = new JaxbMapResponse(variables); 
			return response;
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public KieReleaseId getReleaseId(long processInstanceId) {
		try {
			return this.processRuntimeService.getReleaseId(processInstanceId);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}
	
	
}
