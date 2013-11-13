package org.jbpm.ee.services.rest.impl;

import java.util.List;
import javax.inject.Inject;

import org.jbpm.ee.services.ejb.local.TaskServiceBean;
import org.jbpm.ee.services.rest.TaskServiceRest;
import org.jbpm.ee.services.rest.request.JaxbMapRequest;
import org.jbpm.services.task.impl.model.xml.JaxbAttachment;
import org.jbpm.services.task.impl.model.xml.JaxbContent;
import org.jbpm.services.task.impl.model.xml.JaxbTask;
import org.kie.api.task.model.OrganizationalEntity;
import org.kie.api.task.model.Status;
import org.kie.services.client.serialization.jaxb.impl.JaxbLongListResponse;
import org.kie.services.client.serialization.jaxb.impl.JaxbTaskSummaryListResponse;


public class TaskServiceRestImpl implements TaskServiceRest {

	@Inject
	private TaskServiceBean taskService;
	
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
	public void complete(long taskId, String userId, JaxbMapRequest data) {
		taskService.complete(taskId, userId, data.getMap());
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
	public void fail(long taskId, String userId, JaxbMapRequest faultData) {
		taskService.fail(taskId, userId, faultData.getMap());
	}

	@Override
	public void forward(long taskId, String userId, String targetEntityId) {
		taskService.forward(taskId, userId, targetEntityId);
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
	public JaxbTask getTaskByWorkItemId(long workItemId) {
		return new JaxbTask(taskService.getTaskByWorkItemId(workItemId));
	}

	@Override
	public JaxbTask getTaskById(long taskId) {
		return new JaxbTask(taskService.getTaskById(taskId));
	}

	@Override
	public JaxbTaskSummaryListResponse getTasksAssignedAsBusinessAdministrator(String userId, String language) {
		JaxbTaskSummaryListResponse response = new JaxbTaskSummaryListResponse();
		response.setResult(taskService.getTasksAssignedAsBusinessAdministrator(userId, language));
		return response;
	}

	@Override
	public JaxbTaskSummaryListResponse getTasksAssignedAsPotentialOwner(String userId, String language) {
		JaxbTaskSummaryListResponse response = new JaxbTaskSummaryListResponse();
		response.setResult(taskService.getTasksAssignedAsPotentialOwner(userId, language));
		return response;
	}

	@Override
	public JaxbTaskSummaryListResponse getTasksAssignedAsPotentialOwnerByStatus(String userId, List<Status> status, String language) {
		JaxbTaskSummaryListResponse response = new JaxbTaskSummaryListResponse();
		response.setResult(taskService.getTasksAssignedAsPotentialOwnerByStatus(userId, status, language));
		return response;
	}

	@Override
	public JaxbTaskSummaryListResponse getTasksOwned(String userId, String language) {
		JaxbTaskSummaryListResponse response = new JaxbTaskSummaryListResponse();
		response.setResult(taskService.getTasksOwned(userId, language));
		return response;
	}

	@Override
	public JaxbTaskSummaryListResponse getTasksOwnedByStatus(String userId, List<Status> status, String language) {
		JaxbTaskSummaryListResponse response = new JaxbTaskSummaryListResponse();
		response.setResult(taskService.getTasksOwnedByStatus(userId, status, language));
		return response;
	}

	@Override
	public JaxbLongListResponse getTasksByProcessInstanceId(long processInstanceId) {
		JaxbLongListResponse response = new JaxbLongListResponse();
		response.setResult(taskService.getTasksByProcessInstanceId(processInstanceId));
		return response;
	}

	@Override
	public JaxbTaskSummaryListResponse getTasksByStatusByProcessInstanceId(long processInstanceId, List<Status> status, String language) {
		JaxbTaskSummaryListResponse response = new JaxbTaskSummaryListResponse();
		response.setResult(taskService.getTasksByStatusByProcessInstanceId(processInstanceId, status, language));
		return response;
	}

	@Override
	public JaxbContent getContentById(long contentId) {
		return new JaxbContent(taskService.getContentById(contentId));
	}

	@Override
	public JaxbAttachment getAttachmentById(long attachId) {
		return new JaxbAttachment(taskService.getAttachmentById(attachId));
	}


}
