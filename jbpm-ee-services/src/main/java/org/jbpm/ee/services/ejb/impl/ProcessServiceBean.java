package org.jbpm.ee.services.ejb.impl;

import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.jbpm.ee.exception.InactiveProcessInstance;
import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.ejb.impl.interceptors.JBPMContextEJBBinding;
import org.jbpm.ee.services.ejb.impl.interceptors.JBPMContextEJBInterceptor;
import org.jbpm.ee.services.ejb.local.ProcessServiceLocal;
import org.jbpm.ee.services.ejb.remote.ProcessServiceRemote;
import org.jbpm.ee.services.ejb.startup.KnowledgeManagerBean;
import org.jbpm.ee.services.model.KieReleaseId;
import org.jbpm.ee.services.model.ProcessInstanceFactory;
import org.jbpm.ee.services.support.KieReleaseIdXProcessInstanceListener;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.jbpm.workflow.instance.impl.WorkflowProcessInstanceImpl;
import org.kie.api.event.process.ProcessEventListener;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * Provides a wrapper implementation for the CDI services in order to support consistent execution by Local, Remote, REST, and SOAP execution.
 * 
 * {@see ProcessService}
 * 
 * @author bradsdavis
 *
 */
@JBPMContextEJBBinding
@Interceptors({JBPMContextEJBInterceptor.class})
@Stateless
public class ProcessServiceBean implements ProcessService, ProcessServiceLocal, ProcessServiceRemote {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProcessServiceBean.class);

	@EJB
	private KnowledgeManagerBean knowledgeManager;

	@Override
	public ProcessInstance startProcess(KieReleaseId releaseId, String processId) {
		KieSession session = knowledgeManager.getRuntimeEngine(releaseId).getKieSession();
		
		return ProcessInstanceFactory.convert(session.startProcess(processId));
	}

	@Override
	public ProcessInstance startProcess(KieReleaseId releaseId, String processId, Map<String, Object> parameters) {
		KieSession session = knowledgeManager.getRuntimeEngine(releaseId).getKieSession();
		return ProcessInstanceFactory.convert(session.startProcess(processId, parameters));
	}

	@Override
	public void signalEvent(long processInstanceId, String type, Object event) {
		knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession().signalEvent(type, event);
	}

	@Override
	public ProcessInstance getProcessInstance(long processInstanceId) {
		try {
			return ProcessInstanceFactory.convert(knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession().getProcessInstance(processInstanceId, true));
		} 
		catch(InactiveProcessInstance e) {
			return null;
		}
	}

	@Override
	public void abortProcessInstance(long processInstanceId) {
		KieSession kieSession = knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession();
		for(ProcessEventListener listener : kieSession.getProcessEventListeners()) {
			if(listener.getClass().equals(KieReleaseIdXProcessInstanceListener.class)) {
				listener.afterProcessCompleted(null);
				break;
			}
		}
		knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession().abortProcessInstance(processInstanceId);
	}

	@Override
	public void setProcessInstanceVariable(long processInstanceId, String variableName, Object variable) {
		WorkflowProcessInstance pi = (WorkflowProcessInstance)knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession().getProcessInstance(processInstanceId);
		pi.setVariable(variableName, variable);
	}

	@Override
	public Object getProcessInstanceVariable(long processInstanceId, String variableName) {
		WorkflowProcessInstance pi = (WorkflowProcessInstance)knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession().getProcessInstance(processInstanceId);
		return pi.getVariable(variableName);
	}

	@Override
	public Map<String, Object> getProcessInstanceVariables(long processInstanceId) {
		WorkflowProcessInstanceImpl pi = (WorkflowProcessInstanceImpl)knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession().getProcessInstance(processInstanceId);
		LOG.debug("Process variable size: "+pi.getVariables().size());
		return pi.getVariables();
	}


	@Override
	public KieReleaseId getReleaseId(long processInstanceId) {
		return knowledgeManager.getReleaseIdByProcessId(processInstanceId);
	}
	
}
