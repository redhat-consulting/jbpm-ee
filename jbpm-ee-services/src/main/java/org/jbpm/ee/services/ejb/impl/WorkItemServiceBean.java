package org.jbpm.ee.services.ejb.impl;

import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.drools.core.common.InternalRuleBase;
import org.drools.core.process.instance.WorkItem;
import org.drools.persistence.info.WorkItemInfo;
import org.jbpm.ee.services.WorkItemService;
import org.jbpm.ee.services.ejb.local.WorkItemServiceLocal;
import org.jbpm.ee.services.ejb.remote.WorkItemServiceRemote;
import org.jbpm.ee.services.ejb.startup.KnowledgeManagerBean;
import org.jbpm.ee.support.KieReleaseId;
import org.kie.internal.runtime.manager.RuntimeEnvironment;

@Stateful
public class WorkItemServiceBean implements WorkItemService, WorkItemServiceLocal, WorkItemServiceRemote {

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
	public WorkItem getWorkItem(long id) {
		KieReleaseId releaseId = knowledgeManager.getReleaseIdByWorkItemId(id);
		RuntimeEnvironment environment = knowledgeManager.getRuntimeEnvironment(releaseId);
		
		Query query = entityManager.createQuery("from WorkItemInfo wii where wii.workItemId = :workItemId");
		query.setParameter("workItemId", id);
		WorkItemInfo info = (WorkItemInfo)query.getSingleResult();
		
		return info.getWorkItem(environment.getEnvironment(), (InternalRuleBase)environment.getKieBase());
	}
	

}
