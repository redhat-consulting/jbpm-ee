package org.jbpm.ee.services.ejb.impl;

import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.drools.core.common.InternalRuleBase;
import org.drools.persistence.info.WorkItemInfo;
import org.jbpm.ee.services.WorkItemService;
import org.jbpm.ee.services.ejb.local.WorkItemServiceLocal;
import org.jbpm.ee.services.ejb.remote.WorkItemServiceRemote;
import org.jbpm.ee.services.ejb.startup.KnowledgeManagerBean;
import org.jbpm.ee.services.model.ProcessInstanceFactory;
import org.jbpm.ee.support.KieReleaseId;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.WorkItem;
import org.kie.internal.runtime.manager.RuntimeEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.tools.javac.util.Log;

@Stateless
public class WorkItemServiceBean implements WorkItemService, WorkItemServiceLocal, WorkItemServiceRemote {

	private static final Logger LOG = LoggerFactory.getLogger(WorkItemServiceBean.class);
	
	@Inject
	private EntityManager entityManager;
	
	@EJB
	private KnowledgeManagerBean knowledgeManager;

	@Override
	public void completeWorkItem(long id, Map<String, Object> results) {
		knowledgeManager.getRuntimeEngineByWorkItemId(id).getKieSession().getWorkItemManager().completeWorkItem(id, results);
	}

	@Override
	public void abortWorkItem(long id) {
		knowledgeManager.getRuntimeEngineByWorkItemId(id).getKieSession().getWorkItemManager().abortWorkItem(id);
	}

	@Override
	public org.kie.api.runtime.process.WorkItem getWorkItem(long id) {
		KieReleaseId releaseId = knowledgeManager.getReleaseIdByWorkItemId(id);
		RuntimeEnvironment environment = knowledgeManager.getRuntimeEnvironment(releaseId);
		
		Query query = entityManager.createQuery("from WorkItemInfo wii where wii.workItemId = :workItemId");
		query.setParameter("workItemId", id);
		WorkItemInfo info = (WorkItemInfo)query.getSingleResult();
		
		RuntimeEngine engine = knowledgeManager.getRuntimeEngineByProcessId(info.getProcessInstanceId());
		org.drools.core.process.instance.WorkItemManager wim = (org.drools.core.process.instance.WorkItemManager)(engine.getKieSession().getWorkItemManager());
		return ProcessInstanceFactory.convert(wim.getWorkItem(id));
	}
	

}
