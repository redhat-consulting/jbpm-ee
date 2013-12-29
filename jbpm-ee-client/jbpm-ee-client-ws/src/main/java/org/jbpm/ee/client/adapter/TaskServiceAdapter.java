package org.jbpm.ee.client.adapter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jbpm.ee.services.TaskService;
import org.jbpm.ee.services.model.KieReleaseId;
import org.jbpm.ee.services.ws.TaskServiceWS;
import org.jbpm.ee.services.ws.request.JaxbMapRequest;
import org.kie.api.task.model.Attachment;
import org.kie.api.task.model.Content;
import org.kie.api.task.model.OrganizationalEntity;
import org.kie.api.task.model.Status;
import org.kie.api.task.model.Task;
import org.kie.api.task.model.TaskSummary;

public class TaskServiceAdapter implements TaskService {

	private final TaskServiceWS taskService;
	
	public TaskServiceAdapter(TaskServiceWS taskService) {
		this.taskService = taskService;
	}
	
	@Override
	public void activate(long taskId, String userId) {
		this.taskService.activate(taskId, userId);
	}

	@Override
	public void claim(long taskId, String userId) {
		this.taskService.claim(taskId, userId);
	}

	@Override
	public void claimNextAvailable(String userId, String language) {
		this.taskService.claimNextAvailable(userId, language);
	}

	@Override
	public void complete(long taskId, String userId, Map<String, Object> data) {
		this.taskService.complete(taskId, userId, new JaxbMapRequest(data));
	}

	@Override
	public void delegate(long taskId, String userId, String targetUserId) {
		this.taskService.delegate(taskId, userId, targetUserId);
	}

	@Override
	public void exit(long taskId, String userId) {
		this.taskService.exit(taskId, userId);
	}

	@Override
	public void fail(long taskId, String userId, Map<String, Object> faultData) {
		this.taskService.fail(taskId, userId, new JaxbMapRequest(faultData));
	}

	@Override
	public void forward(long taskId, String userId, String targetEntityId) {
		this.taskService.forward(taskId, userId, targetEntityId);
	}

	@Override
	public void release(long taskId, String userId) {
		this.taskService.release(taskId, userId);
	}

	@Override
	public void resume(long taskId, String userId) {
		this.taskService.resume(taskId, userId);
	}

	@Override
	public void skip(long taskId, String userId) {
		this.taskService.skip(taskId, userId);
	}

	@Override
	public void start(long taskId, String userId) {
		this.taskService.start(taskId, userId);
	}

	@Override
	public void stop(long taskId, String userId) {
		this.taskService.stop(taskId, userId);
	}

	@Override
	public void suspend(long taskId, String userId) {
		this.taskService.suspend(taskId, userId);
	}

	@Override
	public void nominate(long taskId, String userId, List<OrganizationalEntity> potentialOwners) {
		this.taskService.nominate(taskId, userId, potentialOwners);
	}

	@Override
	public Task getTaskByWorkItemId(long workItemId) {
		return this.taskService.getTaskByWorkItemId(workItemId);
	}

	@Override
	public Task getTaskById(long taskId) {
		return this.taskService.getTaskById(taskId);
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsBusinessAdministrator(String userId, String language) {
		List<TaskSummary> results = new LinkedList<TaskSummary>();
		
		List<org.jbpm.ee.services.model.task.TaskSummary> summaries = this.taskService.getTasksAssignedAsBusinessAdministrator(userId, language);
		if(summaries != null) {
			results.addAll(summaries);
		}
		
		return results;
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsPotentialOwner(String userId, String language) {
		List<TaskSummary> results = new LinkedList<TaskSummary>();
		
		List<org.jbpm.ee.services.model.task.TaskSummary> summaries = taskService.getTasksAssignedAsPotentialOwner(userId, language);
		if(summaries != null) {
			results.addAll(summaries);
		}
		
		return results;
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsPotentialOwnerByStatus(String userId, List<Status> status, String language) {
		List<TaskSummary> results = new LinkedList<TaskSummary>();

		List<org.jbpm.ee.services.model.task.TaskSummary> summaries = this.taskService.getTasksAssignedAsPotentialOwnerByStatus(userId, status, language);
		if(summaries != null) {
			results.addAll(summaries);
		}
		
		return results;
	}

	@Override
	public List<TaskSummary> getTasksOwned(String userId, String language) {
		List<TaskSummary> results = new LinkedList<TaskSummary>();
		
		List<org.jbpm.ee.services.model.task.TaskSummary> summaries = this.taskService.getTasksOwned(userId, language);
		if(summaries != null) {
			results.addAll(summaries);
		}
		
		return results;
	}

	@Override
	public List<TaskSummary> getTasksOwnedByStatus(String userId, List<Status> status, String language) {
		List<TaskSummary> results = new LinkedList<TaskSummary>();

		List<org.jbpm.ee.services.model.task.TaskSummary> summaries = this.taskService.getTasksOwnedByStatus(userId, status, language);
		if(summaries != null) {
			results.addAll(summaries);
		}

		return results;
	}

	@Override
	public List<Long> getTasksByProcessInstanceId(long processInstanceId) {
		return this.taskService.getTasksByProcessInstanceId(processInstanceId);
	}

	@Override
	public List<TaskSummary> getTasksByStatusByProcessInstanceId(long processInstanceId, List<Status> status, String language) {
		List<TaskSummary> results = new LinkedList<TaskSummary>();
		
		List<org.jbpm.ee.services.model.task.TaskSummary> summaries = this.taskService.getTasksByStatusByProcessInstanceId(processInstanceId, status, language);
		if(summaries != null) {
			results.addAll(summaries);
		}

		return results;
	}

	@Override
	public Content getContentById(long contentId) {
		return this.taskService.getContentById(contentId);
	}

	@Override
	public Attachment getAttachmentById(long attachId) {
		return this.taskService.getAttachmentById(attachId);
	}
	
	@Override
	public KieReleaseId getReleaseId(long taskId) {
		return this.taskService.getReleaseId(taskId);
	}

}
