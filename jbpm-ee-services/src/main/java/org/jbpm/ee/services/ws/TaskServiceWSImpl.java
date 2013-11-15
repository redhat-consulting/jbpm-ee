package org.jbpm.ee.services.ws;

import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jbpm.ee.services.ejb.local.TaskServiceLocal;
import org.jbpm.ee.services.ws.exceptions.RemoteServiceException;
import org.jbpm.ee.services.ws.request.JaxbMapRequest;
import org.jbpm.services.task.impl.model.xml.JaxbAttachment;
import org.jbpm.services.task.impl.model.xml.JaxbContent;
import org.jbpm.services.task.impl.model.xml.JaxbTask;
import org.jbpm.services.task.impl.model.xml.adapter.OrganizationalEntityXmlAdapter;
import org.kie.api.task.model.OrganizationalEntity;
import org.kie.api.task.model.Status;
import org.kie.services.client.serialization.jaxb.impl.JaxbLongListResponse;
import org.kie.services.client.serialization.jaxb.impl.JaxbTaskSummaryListResponse;

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
	public JaxbTask getTaskByWorkItemId(long workItemId) {
		try {
			return new JaxbTask(taskService.getTaskByWorkItemId(workItemId));
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public JaxbTask getTaskById(long taskId) {
		try {
			return new JaxbTask(taskService.getTaskById(taskId));
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public JaxbTaskSummaryListResponse getTasksAssignedAsBusinessAdministrator(String userId, String language) {
		try {
			JaxbTaskSummaryListResponse response = new JaxbTaskSummaryListResponse();
			response.setResult(taskService.getTasksAssignedAsBusinessAdministrator(userId, language));
			return response;
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public JaxbTaskSummaryListResponse getTasksAssignedAsPotentialOwner(String userId, String language) {
		try {
			JaxbTaskSummaryListResponse response = new JaxbTaskSummaryListResponse();
			response.setResult(taskService.getTasksAssignedAsPotentialOwner(userId, language));
			return response;
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public JaxbTaskSummaryListResponse getTasksAssignedAsPotentialOwnerByStatus(String userId, List<Status> status, String language) {
		try {
			JaxbTaskSummaryListResponse response = new JaxbTaskSummaryListResponse();
			response.setResult(taskService.getTasksAssignedAsPotentialOwnerByStatus(userId, status, language));
			return response;
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public JaxbTaskSummaryListResponse getTasksOwned(String userId, String language) {
		try {
			JaxbTaskSummaryListResponse response = new JaxbTaskSummaryListResponse();
			response.setResult(taskService.getTasksOwned(userId, language));
			return response;
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}
	
	@Override
	public JaxbTaskSummaryListResponse getTasksOwnedByStatus(String userId, List<Status> status, String language) {
		try {
			JaxbTaskSummaryListResponse response = new JaxbTaskSummaryListResponse();
			response.setResult(taskService.getTasksOwnedByStatus(userId, status, language));
			return response;
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public JaxbLongListResponse getTasksByProcessInstanceId(long processInstanceId) {
		try {
			JaxbLongListResponse response = new JaxbLongListResponse();
			response.setResult(taskService.getTasksByProcessInstanceId(processInstanceId));
			return response;
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public JaxbTaskSummaryListResponse getTasksByStatusByProcessInstanceId(long processInstanceId, List<Status> status, String language) {
		try {
			JaxbTaskSummaryListResponse response = new JaxbTaskSummaryListResponse();
			response.setResult(taskService.getTasksByStatusByProcessInstanceId(processInstanceId, status, language));
			return response;
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public JaxbContent getContentById(long contentId) {
		try {
			return new JaxbContent(taskService.getContentById(contentId));
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public JaxbAttachment getAttachmentById(long attachId) {
		try {
			return new JaxbAttachment(taskService.getAttachmentById(attachId));
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}


}
