package org.jbpm.ee.services.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jbpm.ee.services.TaskService;
import org.jbpm.ee.services.model.KieReleaseId;
import org.jbpm.ee.services.model.task.Content;
import org.jbpm.ee.services.model.task.Task;
import org.jbpm.ee.services.model.task.TaskAttachment;
import org.jbpm.ee.services.model.task.TaskSummary;
import org.jbpm.ee.services.ws.request.JaxbMapRequest;
import org.jbpm.services.task.impl.model.xml.adapter.OrganizationalEntityXmlAdapter;
import org.kie.api.task.model.OrganizationalEntity;
import org.kie.api.task.model.Status;

/**
 * Rest interface equivalent to {@link TaskService}.  Returns JAXB types.
 * 
 * @see TaskServiceWS
 * @author bradsdavis
 *
 */
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
@Path("/task")
@WebService
public interface TaskServiceWS {

	@WebMethod
    @PUT
    @Path("{taskId}/activate")
    void activate(
    		@PathParam("taskId") @WebParam(name="taskId") long taskId, 
    		@QueryParam("userId") @WebParam(name="userId") String userId
    );

	@WebMethod
    @PUT
    @Path("{taskId}/claim")
    void claim(
    		@WebParam(name="taskId") @PathParam("taskId") long taskId, 
    		@WebParam(name="userId") @QueryParam("userId") String userId
    );

	@WebMethod
    @PUT
    @Path("claim/next")
    void claimNextAvailable(
    		@WebParam(name="userId") @QueryParam("userId") String userId, 
    		@WebParam(name="language") @QueryParam("lang") String language
    );

	@WebMethod
    @PUT
    @Path("{taskId}/complete")
    void complete(
    		@WebParam(name="taskId") @PathParam("taskId") long taskId, 
    		@WebParam(name="userId") @QueryParam("userId") String userId, 
    		@WebParam(name="taskData") JaxbMapRequest data
    );

	@WebMethod
    @PUT
    @Path("{taskId}/delegate")
    void delegate(
    		@WebParam(name="taskId") @PathParam("taskId") long taskId, 
    		@WebParam(name="userId") @QueryParam("userId") String userId, 
    		@WebParam(name="targetUserId") String targetUserId
    );
	
	@WebMethod
	@POST
	@Path("{taskId}/exit")
	void exit(
	    @WebParam(name="taskId") @PathParam("taskId") long taskId, 
	    @WebParam(name="userId") @QueryParam("userId") String userId
	);
	
	@WebMethod
	@PUT
	@Path("{taskId}/fail")
	void fail(
	    @WebParam(name="taskId") @PathParam("taskId") long taskId, 
	    @WebParam(name="userId") @QueryParam("userId") String userId, 
	    @WebParam(name="faultData") JaxbMapRequest faultData
	);
	
	@WebMethod
	@PUT
	@Path("{taskId}/forward")
	void forward(
	    @WebParam(name="taskId") @PathParam("taskId") long taskId, 
	    @WebParam(name="userId") @QueryParam("userId") String userId, 
	    @WebParam(name="targetEntityId") String targetEntityId
	);
	
	@WebMethod
	@PUT
	@Path("{taskId}/release")
	void release(
	    @WebParam(name="taskId") @PathParam("taskId") long taskId, 
	    @WebParam(name="userId") @QueryParam("userId") String userId
	);
	
	@WebMethod
	@PUT
	@Path("{taskId}/resume")
	void resume(
	    @WebParam(name="taskId") @PathParam("taskId") long taskId, 
	    @WebParam(name="userId") @QueryParam("userId") String userId
	);
	
	@WebMethod
	@PUT
	@Path("{taskId}/skip")
	void skip(
	    @WebParam(name="taskId") @PathParam("taskId") long taskId, 
	    @WebParam(name="userId") @QueryParam("userId") String userId
	);
	
	@WebMethod
	@PUT
	@Path("{taskId}/start")
	void start(
	    @WebParam(name="taskId") @PathParam("taskId") long taskId, 
	    @WebParam(name="userId") @QueryParam("userId") String userId
	);
	
	@WebMethod
	@PUT
	@Path("{taskId}/stop")
	void stop(
	    @WebParam(name="taskId") @PathParam("taskId") long taskId, 
	    @WebParam(name="userId") @QueryParam("userId") String userId
	);
	
	@WebMethod
	@PUT
	@Path("{taskId}/suspend")
	void suspend(
	    @WebParam(name="taskId") @PathParam("taskId") long taskId, 
	    @WebParam(name="userId") @QueryParam("userId") String userId
	);
	
	@WebMethod
	@PUT
	@Path("{taskId}/nominate")
	void nominate(
	    @WebParam(name="taskId") @PathParam("taskId") long taskId, 
	    @WebParam(name="userId") @QueryParam("userId") String userId, 
	    @WebParam(name="potentialOwners") @XmlJavaTypeAdapter(OrganizationalEntityXmlAdapter.class)List<OrganizationalEntity> potentialOwners
	);
	
	@WebMethod
	@GET
	@Path("/workitem/{workItemId}/task")
	@WebResult(name="task")
	Task getTaskByWorkItemId(
	    @WebParam(name="workItemId") @PathParam("workItemId") long workItemId
	);
	
	@WebMethod
	@GET
	@Path("{taskId}")
	@WebResult(name="task")
	Task getTaskById(
	    @WebParam(name="task-id") @PathParam("taskId") long taskId
	);
	
	@WebMethod
	@PUT
	@Path("assigned/{userId}/administrator")
	@WebResult(name="task-summaries")
	List<TaskSummary> getTasksAssignedAsBusinessAdministrator(
	    @WebParam(name="userId") @PathParam("userId") String userId, 
	    @WebParam(name="language") @QueryParam("lang") String language
	);
	
	@WebMethod
	@PUT
	@Path("assigned/{userId}/potential/all")
	@WebResult(name="task-summaries")
	List<TaskSummary> getTasksAssignedAsPotentialOwner(
	    @WebParam(name="userId") @PathParam("userId") String userId, 
	    @WebParam(name="language") @QueryParam("lang") String language
	);
	
	@WebMethod
	@PUT
	@Path("assigned/{userId}/potential/status")
	@WebResult(name="task-summaries")
	List<TaskSummary> getTasksAssignedAsPotentialOwnerByStatus(
	    @WebParam(name="userId") @PathParam("userId") String userId, 
	    List<Status> status, 
	    @WebParam(name="language") @QueryParam("lang") String language
	);
	
	@WebMethod
	@PUT
	@Path("assigned/{userId}/owner/all")
	@WebResult(name="task-summaries")
	List<TaskSummary> getTasksOwned(
	    @WebParam(name="userId") @PathParam("userId") String userId, 
	    @WebParam(name="language") @QueryParam("lang") String language
	);
	
	@WebMethod
	@PUT
	@Path("assigned/{userId}/owner/status")
	@WebResult(name="task-summaries")
	List<TaskSummary> getTasksOwnedByStatus(
	    @WebParam(name="userId") @PathParam("userId") String userId, 
	    @WebParam(name="status") List<Status> status, 
	    @WebParam(name="language") @QueryParam("lang") String language
	);
	
	@WebMethod
	@PUT
	@Path("/process/instance/{processInstanceId}/tasks/all")
	@WebResult(name="task-summaries")
	List<Long> getTasksByProcessInstanceId(
	    @WebParam(name="process-instance-id") @PathParam("processInstanceId") long processInstanceId
	);
	
	@WebMethod
	@PUT
	@Path("/process/instance/{processInstanceId}/tasks/status")
	@WebResult(name="task-summaries")
	List<TaskSummary> getTasksByStatusByProcessInstanceId(
	    @WebParam(name="process-instance-id") @PathParam("processInstanceId") long processInstanceId, 
	    List<Status> status, 
	    @WebParam(name="language") @QueryParam("lang") String language
	);
	
	@WebMethod
	@GET
	@Path("/content/{contentId}")
	Content getContentById(
	    @WebParam(name="content-id") @PathParam("contentId") long contentId
	);
	
	@WebMethod
	@GET
	@Path("/attachment/{attachId}")
	TaskAttachment getAttachmentById(
	    @WebParam(name="attachId") @PathParam("attachId") long attachId
	);


	@WebMethod
	@GET
	@Path("/release/by/task/{taskId}")
	public KieReleaseId getReleaseId(
			@WebParam(name="task-id") @PathParam("taskId") long taskId
	);
}
