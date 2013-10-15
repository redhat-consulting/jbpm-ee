package org.jbpm.ee.service;

import java.util.Collection;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.jbpm.ee.cdi.JBPMServiceBean;
import org.jbpm.ee.service.exception.RuntimeConfigurationException;
import org.jbpm.ee.service.remote.ProcessRuntimeRemote;
import org.jbpm.ee.support.KieReleaseId;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.ProcessRuntime;
import org.kie.api.runtime.process.WorkItemManager;

@LocalBean
@Stateful
@Remote(ProcessRuntimeRemote.class)
@RequestScoped
public class ProcessRuntimeBean implements ProcessRuntime, ProcessRuntimeRemote, KieReleaseIdAware {

	@Inject
	private JBPMServiceBean jbpmServiceBean;
	
	private ProcessRuntime processRuntimeDelegate;

	@Override
	public ProcessInstance startProcess(String processId) {
		validateKieReleaseIdState();
		
		return processRuntimeDelegate.startProcess(processId);
	}

	@Override
	public ProcessInstance startProcess(String processId, Map<String, Object> parameters) {
		validateKieReleaseIdState();
		
		return processRuntimeDelegate.startProcess(processId, parameters);
	}

	@Override
	public ProcessInstance createProcessInstance(String processId, Map<String, Object> parameters) {
		validateKieReleaseIdState();
		
		return processRuntimeDelegate.createProcessInstance(processId, parameters);
	}

	@Override
	public void signalEvent(String type, Object event) {
		validateKieReleaseIdState();
		
		processRuntimeDelegate.signalEvent(type, event);
	}

	@Override
	public void signalEvent(String type, Object event, long processInstanceId) {
		validateKieReleaseIdState();
		
		processRuntimeDelegate.signalEvent(type, event, processInstanceId);
	}

	@Override
	public Collection<ProcessInstance> getProcessInstances() {
		validateKieReleaseIdState();
		
		return processRuntimeDelegate.getProcessInstances();
	}

	@Override
	public ProcessInstance getProcessInstance(long processInstanceId) {
		validateKieReleaseIdState();
		
		return processRuntimeDelegate.getProcessInstance(processInstanceId);
	}

	@Override
	public ProcessInstance getProcessInstance(long processInstanceId, boolean readonly) {
		validateKieReleaseIdState();
		
		return processRuntimeDelegate.getProcessInstance(processInstanceId, readonly);
	}

	@Override
	public void abortProcessInstance(long processInstanceId) {
		validateKieReleaseIdState();
		
		processRuntimeDelegate.abortProcessInstance(processInstanceId);
	}

	@Override
	public WorkItemManager getWorkItemManager() {
		validateKieReleaseIdState();
		
		return processRuntimeDelegate.getWorkItemManager();
	}

	@Override
	public ProcessInstance startProcessInstance(long processInstanceId) {
		validateKieReleaseIdState();
		
		return processRuntimeDelegate.startProcessInstance(processInstanceId);
	}

	@Override
	public void setKieReleaseId(KieReleaseId releaseId) {
		this.processRuntimeDelegate = jbpmServiceBean.getProcessRuntime(releaseId);
	}
	
	@Override
	public  void validateKieReleaseIdState() {
		if(processRuntimeDelegate == null) {
			throw new RuntimeConfigurationException("ProcessRuntime requires a KieReleaseId.  Make sure to set setKieReleaseId prior to making calls to the EJB.");
		}
	}
	
}
