package org.jbpm.ee.services.ws;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jbpm.ee.services.ejb.local.TaskServiceLocal;
import org.jbpm.ee.services.model.KieReleaseId;
import org.jbpm.ee.services.model.TaskFactory;
import org.jbpm.ee.services.model.task.Content;
import org.jbpm.ee.services.model.task.Task;
import org.jbpm.ee.services.model.task.TaskAttachment;
import org.jbpm.ee.services.model.task.TaskSummary;
import org.jbpm.ee.services.ws.exceptions.RemoteServiceException;
import org.jbpm.ee.services.ws.request.JaxbMapRequest;
import org.jbpm.services.task.impl.model.xml.adapter.OrganizationalEntityXmlAdapter;
import org.kie.api.task.model.OrganizationalEntity;
import org.kie.api.task.model.Status;

@WebService(targetNamespace="http://jbpm.org/v6/TaskService/wsdl", serviceName="TaskService", endpointInterface="org.jbpm.ee.services.ws.TaskServiceWS")
public class TaskServiceWSImpl implements TaskServiceWS {


	@EJB
	private TaskServiceLocal taskService;
	
	@Override
	public void activate(long taskId, String userId) {
		try {
			taskService.activate(taskId, userId);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public void claim(long taskId, String userId) {
		try {
			taskService.claim(taskId, userId);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public void claimNextAvailable(String userId, String language) {
		try {
			taskService.claimNextAvailable(userId, language);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public void complete(long taskId, String userId, JaxbMapRequest data) {
		try {
			taskService.complete(taskId, userId, data.getMap());
		} 
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public void delegate(long taskId, String userId, String targetUserId) {
		try {
			taskService.delegate(taskId, userId, targetUserId);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public void exit(long taskId, String userId) {
		try {
			taskService.exit(taskId, userId);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public void fail(long taskId, String userId, JaxbMapRequest faultData) {
		try {
			taskService.fail(taskId, userId, faultData.getMap());
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public void forward(long taskId, String userId, String targetEntityId) {
		try {
			taskService.forward(taskId, userId, targetEntityId);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public void release(long taskId, String userId) {
		try {
			taskService.release(taskId, userId);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public void resume(long taskId, String userId) {
		try {
			taskService.resume(taskId, userId);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public void skip(long taskId, String userId) {
		try {
			taskService.skip(taskId, userId);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public void start(long taskId, String userId) {
		try {
			taskService.start(taskId, userId);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public void stop(long taskId, String userId) {
		try{
			taskService.stop(taskId, userId);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public void suspend(long taskId, String userId) {
		try {
			taskService.suspend(taskId, userId);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public void nominate(long taskId, String userId, @XmlJavaTypeAdapter(OrganizationalEntityXmlAdapter.class) List<OrganizationalEntity> potentialOwners) {
		try {
			taskService.nominate(taskId, userId, potentialOwners);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public Task getTaskByWorkItemId(long workItemId) {
		try {
			return (Task)taskService.getTaskByWorkItemId(workItemId);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public Task getTaskById(long taskId) {
		try {
			return (Task)taskService.getTaskById(taskId);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsBusinessAdministrator(String userId, String language) {
		try {
			List<org.kie.api.task.model.TaskSummary> summaries = (TaskFactory.convertTaskSummaries(taskService.getTasksAssignedAsBusinessAdministrator(userId, language)));
			return toTaskSummaryList(summaries);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsPotentialOwner(String userId, String language) {
		try {
			List<org.kie.api.task.model.TaskSummary> summaries = (taskService.getTasksAssignedAsPotentialOwner(userId, language));
			return toTaskSummaryList(summaries);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsPotentialOwnerByStatus(String userId, List<Status> status, String language) {
		try {
			List<org.kie.api.task.model.TaskSummary> summaries = (taskService.getTasksAssignedAsPotentialOwnerByStatus(userId, status, language));
			return toTaskSummaryList(summaries);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public List<TaskSummary> getTasksOwned(String userId, String language) {
		try {
			List<org.kie.api.task.model.TaskSummary> summaries = (taskService.getTasksOwned(userId, language));
			return toTaskSummaryList(summaries);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}
	
	@Override
	public List<TaskSummary> getTasksOwnedByStatus(String userId, List<Status> status, String language) {
		try {
			List<org.kie.api.task.model.TaskSummary> summaries = taskService.getTasksOwnedByStatus(userId, status, language);
			return toTaskSummaryList(summaries);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public List<Long> getTasksByProcessInstanceId(long processInstanceId) {
		try {
			return (taskService.getTasksByProcessInstanceId(processInstanceId));
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public List<TaskSummary> getTasksByStatusByProcessInstanceId(long processInstanceId, List<Status> status, String language) {
		try {
			List<org.kie.api.task.model.TaskSummary> summaries = (taskService.getTasksByStatusByProcessInstanceId(processInstanceId, status, language));
			return toTaskSummaryList(summaries);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public Content getContentById(long contentId) {
		try {
			return (Content)TaskFactory.convert(taskService.getContentById(contentId));
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public TaskAttachment getAttachmentById(long attachId) {
		try {
			return (TaskAttachment)TaskFactory.convert(taskService.getAttachmentById(attachId));
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}


	protected List<TaskSummary> toTaskSummaryList(List<org.kie.api.task.model.TaskSummary> summaries) {
		List<TaskSummary> results = new LinkedList<TaskSummary>();
		for(org.kie.api.task.model.TaskSummary summary : summaries) {
			results.add((TaskSummary)summary);
		}
		
		return results;
		
	}

	@Override
	public KieReleaseId getReleaseId(long taskId) {
		try {
			return this.taskService.getReleaseId(taskId);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}
}
