package org.jbpm.ee.services.ejb.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.drools.persistence.info.WorkItemInfo;
import org.jboss.ejb3.annotation.Clustered;
import org.jbpm.ee.services.WorkItemService;
import org.jbpm.ee.services.ejb.impl.interceptors.JBPMContextEJBBinding;
import org.jbpm.ee.services.ejb.impl.interceptors.JBPMContextEJBInterceptor;
import org.jbpm.ee.services.ejb.local.WorkItemServiceLocal;
import org.jbpm.ee.services.ejb.remote.WorkItemServiceRemote;
import org.jbpm.ee.services.ejb.startup.KnowledgeManagerBean;
import org.jbpm.ee.services.model.KieReleaseId;
import org.jbpm.ee.services.model.ProcessInstanceFactory;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.WorkItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/***
 * Provides a wrapper implementation for the CDI services in order to support consistent execution by Local, Remote, REST, and SOAP execution.
 * 
 * {@see WorkItemService}
 * 
 * @author bradsdavis
 *
 */
@JBPMContextEJBBinding
@Interceptors({JBPMContextEJBInterceptor.class})
@Clustered
@Stateless
public class WorkItemServiceBean implements WorkItemService, WorkItemServiceLocal, WorkItemServiceRemote {

	private static final Logger LOG = LoggerFactory.getLogger(WorkItemServiceBean.class);
	
	@Inject
	private EntityManager entityManager;
	
	@EJB
	private KnowledgeManagerBean knowledgeManager;

	private KieSession getSessionByProcess(Long processInstanceId) {
		return knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession();
	}
	
	private KieSession getSessionByWorkItem(long workItemId) {
		return knowledgeManager.getRuntimeEngineByWorkItemId(workItemId).getKieSession();
	}
	
	@Override
	public void completeWorkItem(long id, Map<String, Object> results) {
		getSessionByWorkItem(id).getWorkItemManager().completeWorkItem(id, results);
	}

	@Override
	public void abortWorkItem(long id) {
		getSessionByWorkItem(id).getWorkItemManager().abortWorkItem(id);
	}

	@Override
	public org.kie.api.runtime.process.WorkItem getWorkItem(long workItemId) {
		Query query = entityManager.createQuery("from WorkItemInfo wii where wii.workItemId = :workItemId");
		query.setParameter("workItemId", workItemId);
		WorkItemInfo info = (WorkItemInfo)query.getSingleResult();
		
		org.drools.core.process.instance.WorkItemManager wim = 
				(org.drools.core.process.instance.WorkItemManager)
				(getSessionByProcess(info.getProcessInstanceId()).getWorkItemManager());
		return ProcessInstanceFactory.convert(wim.getWorkItem(workItemId));
	}

	@Override
	public List<WorkItem> getWorkItemByProcessInstance(long processInstanceId) {
		final Long pendingStatus = new Long(WorkItem.PENDING);
		
		Query query = entityManager.createQuery("from WorkItemInfo wii where wii.processInstanceId = :processInstanceId and wii.state = :state");
		query.setParameter("processInstanceId",  processInstanceId);
		
		query.setParameter("state", pendingStatus);
		
		List<WorkItemInfo> infos = (List)query.getResultList();
		org.drools.core.process.instance.WorkItemManager wim = 
				(org.drools.core.process.instance.WorkItemManager)
				(getSessionByProcess(processInstanceId).getWorkItemManager());

		List<org.kie.api.runtime.process.WorkItem> workItem = new LinkedList<WorkItem>(); 
		for(WorkItemInfo info : infos) {
			workItem.add(ProcessInstanceFactory.convert(wim.getWorkItem(info.getId())));
		}
		return workItem;
	}
	
	@Override
	public KieReleaseId getReleaseId(long id) {
		return knowledgeManager.getReleaseIdByWorkItemId(id);
	}
	

}
