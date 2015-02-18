package org.jbpm.ee.services.ejb.startup;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.SessionException;
import org.jbpm.ee.exception.InactiveProcessInstance;
import org.jbpm.ee.persistence.KieBaseXProcessInstance;
import org.jbpm.ee.services.model.KieReleaseId;
import org.jbpm.services.task.HumanTaskConfigurator;
import org.jbpm.services.task.HumanTaskServiceFactory;
import org.jbpm.services.task.lifecycle.listeners.BAMTaskEventListener;
import org.jbpm.services.task.lifecycle.listeners.TaskLifeCycleEventListener;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.Task;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;
import org.kie.internal.task.api.EventService;
import org.kie.internal.task.api.UserGroupCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles loading, scanning, and providing runtime information.
 * 
 * 1 RuntimeManager per application, loaded from kjar in a maven repository
 * 1 RuntimeEngine per session
 * 1 Process per RuntimeEngine
 * 
 * No two processes will share the same KieSession/RuntimeEngine
 * 
 * @author bradsdavis, abaxter
 *
 */
@Startup
@Singleton(name="KnowledgeAgentManager")
@DependsOn("ResourceManager")
public class KnowledgeManagerBean {
	private static final Logger LOG = LoggerFactory.getLogger(KnowledgeManagerBean.class);

	@Inject
	protected EntityManager em;
	
	@Inject
	protected RuntimeManagerBean runtimeManager;

	@Inject
	protected EntityManagerFactory emf;

	@Inject
	UserGroupCallback userGroupCallback;
	
	protected TaskService taskService;
	
	protected BAMTaskEventListener bamTaskEventListener;
	
	@PostConstruct
	private void setup() {
		HumanTaskConfigurator configurator = HumanTaskServiceFactory.newTaskServiceConfigurator()
				.entityManagerFactory(emf)
				.userGroupCallback(userGroupCallback);
		taskService = configurator.getTaskService();
		bamTaskEventListener  = new BAMTaskEventListener();
	}
	
	@Produces
	public TaskService getKieSessionUnboundTaskService() {
		return taskService;
	}
	
	
	/**
	 * Returns the default RuntimeEngine for a specified kjar
	 * 
	 * @param releaseId The maven deployment information for the kjar
	 * @return
	 * @throws SessionException
	 */
	public RuntimeEngine getRuntimeEngine(KieReleaseId releaseId) {
		RuntimeManager rm = runtimeManager.getRuntimeManager(releaseId);
		RuntimeEngine engine = rm.getRuntimeEngine(ProcessInstanceIdContext.get());

		addListeners(engine);
		return engine;
	}
	
	/**
	 * Returns the RuntimeEngine for the specified ProcessInstance
	 * 
	 * Note: At this point, the Process must have been started by jbpm-ee or we will
	 * be unable to find the deployment information 
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public RuntimeEngine getRuntimeEngineByProcessId(Long processInstanceId) {
		LOG.debug("Loading instance: "+processInstanceId);
		ProcessInstanceIdContext context = ProcessInstanceIdContext.get(processInstanceId);
		
		LOG.debug("Context: "+context);
		KieReleaseId releaseId = getReleaseIdByProcessId(processInstanceId);
		if(releaseId == null) {
			throw new InactiveProcessInstance(processInstanceId);
		}
		LOG.debug("Kie Release: "+releaseId);
		
		RuntimeManager manager = runtimeManager.getRuntimeManager(releaseId);
		RuntimeEngine engine = manager.getRuntimeEngine(context);
		addListeners(engine);
		return engine;
	}


	/**
	 * Returns the RuntimeEngine for a given Task
	 * 
	 * @param taskId
	 * @return
	 */
	public RuntimeEngine getRuntimeEngineByTaskId(Long taskId) {
		Long processInstanceId = getProcessInstanceIdByTaskId(taskId);
		RuntimeEngine engine = getRuntimeEngineByProcessId(processInstanceId);
		addListeners(engine);
		return engine;
	}
	
	/**
	 * Returns the RuntimeEngine for a given WorkItem
	 * @param workItemId
	 * @return
	 */
	public RuntimeEngine getRuntimeEngineByWorkItemId(Long workItemId) {
		Long processInstanceId = getProcessInstanceIdByWorkItemId(workItemId);
		RuntimeEngine engine = getRuntimeEngineByProcessId(processInstanceId);
		addListeners(engine);
		return engine;
	}
	
	/**
	 * Returns the RuntimeEngine for a given Content
	 * @param contentId
	 * @return
	 */
	public RuntimeEngine getRuntimeEngineByContentId(Long contentId) {
		Long taskId = getTaskIdByContentId(contentId);
		RuntimeEngine engine = getRuntimeEngineByTaskId(taskId);
		addListeners(engine);
		return engine;
	}
	
	/**
	 * Returns the ProcessInstance associated with the given Task
	 * 
	 * @param taskId
	 * @return
	 */
	public Long getProcessInstanceIdByTaskId(Long taskId) {
		Task task = taskService.getTaskById(taskId);
		Long processInstanceId = task.getTaskData().getProcessInstanceId();
		
		return processInstanceId;
	}
	
	/**
	 * Returns the ProcessInstance associated with the given WorkItem
	 * 
	 * @param workItemId
	 * @return
	 */
	public Long getProcessInstanceIdByWorkItemId(Long workItemId) {
		Query q = em.createQuery("select processInstanceId from WorkItemInfo wii where wii.workItemId=:workItemId");
		q.setParameter("workItemId", workItemId);
		Long processInstanceId = (Long)q.getSingleResult();
		
		return processInstanceId;
	}

	/**
	 * Returns the Task Id associated with the given Content Id
	 * 
	 * @param contentId
	 * @return
	 */
	public Long getTaskIdByContentId(Long contentId) {
		Query q = em.createQuery("SELECT id FROM TaskImpl task WHERE task.taskData.documentContentId=:contentId OR task.taskData.faultContentId=:contentId OR task.taskData.outputContentId=:contentId");
		q.setParameter("contentId", contentId);
		Long taskId = (Long) q.getSingleResult();
		return taskId;
	}
	
	/**
	 * Returns the KieReleaseId associated with a given ProcessInstance
	 * 
	 * Note: At this point, the Process must have been started by jbpm-ee or we will
	 * be unable to find the deployment information 
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public KieReleaseId getReleaseIdByProcessId(Long processInstanceId) {
		Query q = em.createQuery("from KieBaseXProcessInstance kb where kb.kieProcessInstanceId=:processInstanceId");
		q.setParameter("processInstanceId", processInstanceId);
		
		try {
			KieBaseXProcessInstance kb = (KieBaseXProcessInstance)q.getSingleResult();
			return new KieReleaseId(kb.getReleaseGroupId(), kb.getReleaseArtifactId(), kb.getReleaseVersion());
		}
		catch(NoResultException e) {
			return null;
		}
	}
	/**
	 * Returns the KieReleaseId associated with a given Task
	 * 
	 * Note: At this point, the Process must have been started by jbpm-ee or we will
	 * be unable to find the deployment information 
	 * 
	 * @param taskId
	 * @return
	 */
	public KieReleaseId getReleaseIdByTaskId(Long taskId) {
		Task task = taskService.getTaskById(taskId);
		long processInstanceId = task.getTaskData().getProcessInstanceId();
		
		return this.getReleaseIdByProcessId(processInstanceId);
	}
	
	/**
	 * Returns the KieReleaseId associated with a given WorkItem
	 * 
	 * Note: At this point, the Process must have been started by jbpm-ee or we will
	 * be unable to find the deployment information 
	 * 
	 * @param workItemId
	 * @return
	 */
	public KieReleaseId getReleaseIdByWorkItemId(Long workItemId) {
		Query q = em.createQuery("select processInstanceId from WorkItemInfo wii where wii.workItemId=:workItemId");
		q.setParameter("workItemId", workItemId);
		Long processInstanceId = (Long)q.getSingleResult();
		
		return this.getReleaseIdByProcessId(processInstanceId);
	}
	
	
	@SuppressWarnings("unchecked")
	private void addListeners(RuntimeEngine engine) {
		EventService<TaskLifeCycleEventListener> eventService = 
				(EventService<TaskLifeCycleEventListener>) engine.getTaskService();
		boolean hasBamEventListener = false;
		for( TaskLifeCycleEventListener listener: eventService.getTaskEventListeners()) {
			if(listener instanceof BAMTaskEventListener) {
				hasBamEventListener = true;
				break;
			}
		}
		if (!hasBamEventListener) {
			eventService.registerTaskEventListener(bamTaskEventListener);
		}
	}
	
}
