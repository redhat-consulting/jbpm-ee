package org.jbpm.ee.services.ejb.impl;

import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jbpm.ee.exception.InactiveProcessInstance;
import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.ejb.local.ProcessServiceLocal;
import org.jbpm.ee.services.ejb.remote.ProcessServiceRemote;
import org.jbpm.ee.services.ejb.startup.KnowledgeManagerBean;
import org.jbpm.ee.support.KieReleaseId;
import org.jbpm.ee.services.support.KieReleaseIdXProcessInstanceListener;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class ProcessServiceBean implements ProcessService, ProcessServiceLocal, ProcessServiceRemote {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProcessServiceBean.class);

	@Inject
	private EntityManager entityManager;
	
	@EJB
	private KnowledgeManagerBean knowledgeManager;

	@Override
	public ProcessInstance startProcess(KieReleaseId releaseId, String processId) {
		KieSession session = knowledgeManager.getRuntimeEngine(releaseId).getKieSession();
		session.addEventListener(new KieReleaseIdXProcessInstanceListener(releaseId, entityManager));
		return session.startProcess(processId);
	}

	@Override
	public ProcessInstance startProcess(KieReleaseId releaseId, String processId, Map<String, Object> parameters) {
		KieSession session = knowledgeManager.getRuntimeEngine(releaseId).getKieSession();
		session.addEventListener(new KieReleaseIdXProcessInstanceListener(releaseId, entityManager));
		return session.startProcess(processId, parameters);
	}

	@Override
	public ProcessInstance createProcessInstance(KieReleaseId releaseId, String processId, Map<String, Object> parameters) {
		KieSession session = knowledgeManager.getRuntimeEngine(releaseId).getKieSession();
		session.addEventListener(new KieReleaseIdXProcessInstanceListener(releaseId, entityManager));
		return session.createProcessInstance(processId, parameters);
	}

	@Override
	public void signalEvent(String type, Object event, long processInstanceId) {
		knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession().signalEvent(type, event);
	}

	@Override
	public ProcessInstance getProcessInstance(long processInstanceId) {
		try {
			return knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession().getProcessInstance(processInstanceId, true);
		} 
		catch(InactiveProcessInstance e) {
			return null;
		}
	}

	@Override
	public void abortProcessInstance(long processInstanceId) {
		knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession().abortProcessInstance(processInstanceId);
	}

	@Override
	public ProcessInstance startProcessInstance(long processInstanceId) {		
		return knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession().startProcessInstance(processInstanceId);
	}

	
}
