package org.jbpm.ee.services.ejb.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jbpm.ee.services.TaskService;
import org.jbpm.ee.services.ejb.local.TaskServiceLocal;
import org.jbpm.ee.services.ejb.remote.TaskServiceRemote;
import org.jbpm.ee.services.ejb.startup.KnowledgeManagerBean;
import org.kie.api.task.model.Attachment;
import org.kie.api.task.model.Content;
import org.kie.api.task.model.OrganizationalEntity;
import org.kie.api.task.model.Status;
import org.kie.api.task.model.Task;
import org.kie.api.task.model.TaskSummary;
import org.kie.internal.task.api.TaskAttachmentService;
import org.kie.internal.task.api.TaskContentService;
import org.kie.internal.task.api.TaskQueryService;

@Stateless
public class TaskServiceBean implements TaskService, TaskServiceLocal, TaskServiceRemote {

	@EJB
	private KnowledgeManagerBean knowledgeManager;

	@Inject
	private TaskQueryService taskQueryService;
	
	@Inject
	private TaskContentService taskContentService;
	
	@Inject
	private TaskAttachmentService taskAttachmentService;
	
	
	@Override
	public void activate(long taskId, String userId) {
		knowledgeManager.getRuntimeEngineByTaskId(taskId).getTaskService().activate(taskId, userId);
	}

	@Override
	public void claim(long taskId, String userId) {
		knowledgeManager.getRuntimeEngineByTaskId(taskId).getTaskService().claim(taskId, userId);
	}

	@Override
	public void claimNextAvailable(String userId, String language) {
		List<TaskSummary> tasks = taskQueryService.getTasksOwned(userId, language);
		if(tasks.size() < 1) {
			return;
		}
		
		
		TreeSet<TaskSummary> orderedTasks = new TreeSet<TaskSummary>(new Comparator<TaskSummary>() {
			@Override
			public int compare(TaskSummary t1, TaskSummary t2) {
				if(t1.getExpirationTime() == null && t2.getExpirationTime() == null) {
					//check priority.
					Integer p1 = t1.getPriority();
					Integer p2 = t2.getPriority();
					
					return p1.compareTo(p2);
				}
				
				if(t1.getExpirationTime() != null && t2.getExpirationTime() != null) {
					return t1.getExpirationTime().compareTo(t2.getExpirationTime());
				}
				
				if(t1.getExpirationTime() == null && t2.getExpirationTime() != null) {
					return 1;
				}
				
				if(t1.getExpirationTime() != null && t2.getExpirationTime() == null) {
					return -1;
				}
				
				return 0;
			}
		});
		orderedTasks.addAll(tasks);
		
		this.claim(orderedTasks.first().getId(), userId);
	}

	@Override
	public void complete(long taskId, String userId, Map<String, Object> data) {
		
		knowledgeManager.getRuntimeEngineByTaskId(taskId).getTaskService().complete(taskId, userId, data);

	}

	@Override
	public void delegate(long taskId, String userId, String targetUserId) {
		
		knowledgeManager.getRuntimeEngineByTaskId(taskId).getTaskService().delegate(taskId, userId, targetUserId);

	}

	@Override
	public void exit(long taskId, String userId) {
		
		knowledgeManager.getRuntimeEngineByTaskId(taskId).getTaskService().exit(taskId, userId);

	}

	@Override
	public void fail(long taskId, String userId, Map<String, Object> faultData) {
		
		knowledgeManager.getRuntimeEngineByTaskId(taskId).getTaskService().fail(taskId, userId, faultData);

	}

	@Override
	public void forward(long taskId, String userId, String targetEntityId) {
		
		knowledgeManager.getRuntimeEngineByTaskId(taskId).getTaskService().forward(taskId, userId, targetEntityId);

	}

	@Override
	public Task getTaskByWorkItemId(long workItemId) {
		return taskQueryService.getTaskByWorkItemId(workItemId);
	}

	@Override
	public Task getTaskById(long taskId) {
		
		return knowledgeManager.getRuntimeEngineByTaskId(taskId).getTaskService().getTaskById(taskId);
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsBusinessAdministrator(String userId, String language) {
		return taskQueryService.getTasksAssignedAsBusinessAdministrator(userId, language);
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsPotentialOwner(String userId, String language) {
		
		return taskQueryService.getTasksAssignedAsPotentialOwner(userId, language);
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsPotentialOwnerByStatus(String userId, List<Status> status, String language) {
		
		return taskQueryService.getTasksAssignedAsPotentialOwnerByStatus(userId, status, language);
	}

	@Override
	public List<TaskSummary> getTasksOwned(String userId, String language) {
		
		return taskQueryService.getTasksOwned(userId, language);
	}

	@Override
	public List<TaskSummary> getTasksOwnedByStatus(String userId, List<Status> status, String language) {
		
		return taskQueryService.getTasksOwnedByStatus(userId, status, language);
	}

	@Override
	public List<TaskSummary> getTasksByStatusByProcessInstanceId(long processInstanceId, List<Status> status, String language) {
		
		return taskQueryService.getTasksByStatusByProcessInstanceId(processInstanceId, status, language);
	}

	@Override
	public List<Long> getTasksByProcessInstanceId(long processInstanceId) {
		
		return taskQueryService.getTasksByProcessInstanceId(processInstanceId);
	}


	@Override
	public void release(long taskId, String userId) {
		knowledgeManager.getRuntimeEngineByTaskId(taskId).getTaskService().release(taskId, userId);
	}

	@Override
	public void resume(long taskId, String userId) {
		
		knowledgeManager.getRuntimeEngineByTaskId(taskId).getTaskService().resume(taskId, userId);

	}

	@Override
	public void skip(long taskId, String userId) {
		
		knowledgeManager.getRuntimeEngineByTaskId(taskId).getTaskService().skip(taskId, userId);

	}

	@Override
	public void start(long taskId, String userId) {
		
		knowledgeManager.getRuntimeEngineByTaskId(taskId).getTaskService().start(taskId, userId);

	}

	@Override
	public void stop(long taskId, String userId) {
		
		knowledgeManager.getRuntimeEngineByTaskId(taskId).getTaskService().stop(taskId, userId);

	}

	@Override
	public void suspend(long taskId, String userId) {
		
		knowledgeManager.getRuntimeEngineByTaskId(taskId).getTaskService().suspend(taskId, userId);

	}

	@Override
	public void nominate(long taskId, String userId, List<OrganizationalEntity> potentialOwners) {
		
		knowledgeManager.getRuntimeEngineByTaskId(taskId).getTaskService().nominate(taskId, userId, potentialOwners);

	}

	@Override
	public Content getContentById(long contentId) {
		
		return taskContentService.getContentById(contentId);
	}

	@Override
	public Attachment getAttachmentById(long attachId) {
		
		return taskAttachmentService.getAttachmentById(attachId);
	}
	
	
}
