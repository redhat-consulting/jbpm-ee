package org.jbpm.ee.service;

import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;

import org.jbpm.ee.service.remote.TaskServiceRemote;
import org.jbpm.ee.support.KieReleaseId;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.Attachment;
import org.kie.api.task.model.Content;
import org.kie.api.task.model.OrganizationalEntity;
import org.kie.api.task.model.Status;
import org.kie.api.task.model.Task;
import org.kie.api.task.model.TaskSummary;

@LocalBean
@Stateful
@Remote(TaskServiceRemote.class)
@RequestScoped
public class TaskServiceBean implements TaskService, TaskServiceRemote {

	private TaskService taskService;
	
	@Override
	public void activate(long taskId, String userId) {
		taskService.activate(taskId, userId);
	}

	@Override
	public void claim(long taskId, String userId) {
		taskService.claim(taskId, userId);

	}

	@Override
	public void claimNextAvailable(String userId, String language) {
		taskService.claimNextAvailable(userId, language);

	}

	@Override
	public void complete(long taskId, String userId, Map<String, Object> data) {
		taskService.complete(taskId, userId, data);

	}

	@Override
	public void delegate(long taskId, String userId, String targetUserId) {
		taskService.delegate(taskId, userId, targetUserId);

	}

	@Override
	public void exit(long taskId, String userId) {
		taskService.exit(taskId, userId);

	}

	@Override
	public void fail(long taskId, String userId, Map<String, Object> faultData) {
		taskService.fail(taskId, userId, faultData);

	}

	@Override
	public void forward(long taskId, String userId, String targetEntityId) {
		taskService.forward(taskId, userId, targetEntityId);

	}

	@Override
	public Task getTaskByWorkItemId(long workItemId) {
		return taskService.getTaskByWorkItemId(workItemId);
	}

	@Override
	public Task getTaskById(long taskId) {
		return taskService.getTaskById(taskId);
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsBusinessAdministrator(String userId, String language) {
		return taskService.getTasksAssignedAsBusinessAdministrator(userId, language);
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsPotentialOwner(String userId, String language) {
		return taskService.getTasksAssignedAsPotentialOwner(userId, language);
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsPotentialOwnerByStatus(String userId, List<Status> status, String language) {
		return taskService.getTasksAssignedAsPotentialOwnerByStatus(userId, status, language);
	}

	@Override
	public List<TaskSummary> getTasksOwned(String userId, String language) {
		return taskService.getTasksOwned(userId, language);
	}

	@Override
	public List<TaskSummary> getTasksOwnedByStatus(String userId, List<Status> status, String language) {
		return taskService.getTasksOwnedByStatus(userId, status, language);
	}

	@Override
	public List<TaskSummary> getTasksByStatusByProcessInstanceId(long processInstanceId, List<Status> status, String language) {
		return taskService.getTasksByStatusByProcessInstanceId(processInstanceId, status, language);
	}

	@Override
	public List<Long> getTasksByProcessInstanceId(long processInstanceId) {
		return taskService.getTasksByProcessInstanceId(processInstanceId);
	}

	@Override
	public long addTask(Task task, Map<String, Object> params) {
		return taskService.addTask(task, params);
	}

	@Override
	public void release(long taskId, String userId) {
		taskService.release(taskId, userId);

	}

	@Override
	public void resume(long taskId, String userId) {
		taskService.resume(taskId, userId);

	}

	@Override
	public void skip(long taskId, String userId) {
		taskService.skip(taskId, userId);

	}

	@Override
	public void start(long taskId, String userId) {
		taskService.start(taskId, userId);

	}

	@Override
	public void stop(long taskId, String userId) {
		taskService.stop(taskId, userId);

	}

	@Override
	public void suspend(long taskId, String userId) {
		taskService.suspend(taskId, userId);

	}

	@Override
	public void nominate(long taskId, String userId, List<OrganizationalEntity> potentialOwners) {
		taskService.nominate(taskId, userId, potentialOwners);

	}

	@Override
	public Content getContentById(long contentId) {
		return taskService.getContentById(contentId);
	}

	@Override
	public Attachment getAttachmentById(long attachId) {
		return taskService.getAttachmentById(attachId);
	}
	
}
