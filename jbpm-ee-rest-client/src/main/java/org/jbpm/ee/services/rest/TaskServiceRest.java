package org.jbpm.ee.services.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jbpm.ee.services.TaskService;
import org.jbpm.ee.services.rest.request.JaxbMapRequest;
import org.jbpm.services.task.impl.model.xml.JaxbAttachment;
import org.jbpm.services.task.impl.model.xml.JaxbContent;
import org.jbpm.services.task.impl.model.xml.JaxbTask;
import org.kie.api.task.model.OrganizationalEntity;
import org.kie.api.task.model.Status;
import org.kie.services.client.serialization.jaxb.impl.JaxbLongListResponse;
import org.kie.services.client.serialization.jaxb.impl.JaxbTaskSummaryListResponse;

/**
 * Rest interface equivalent to {@link TaskService}.  Returns JAXB types.
 * 
 * @see TaskService
 * @author bradsdavis
 *
 */
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
@Path("/task")
public interface TaskServiceRest {

    @PUT
    @Path("{taskId}/activate")
    void activate(@PathParam("taskId") long taskId, @QueryParam("userId") String userId);

    @PUT
    @Path("{taskId}/claim")
    void claim(@PathParam("taskId") long taskId, @QueryParam("userId") String userId);


    @PUT
    @Path("claim/next")
    void claimNextAvailable(@QueryParam("userId") String userId, @QueryParam("lang") String language);


    @PUT
    @Path("{taskId}/complete")
    void complete(@PathParam("taskId") long taskId, @QueryParam("userId") String userId, JaxbMapRequest data);


    @PUT
    @Path("{taskId}/delegate")
    void delegate(@PathParam("taskId") long taskId, @QueryParam("userId") String userId, String targetUserId);


    @POST
    @Path("{taskId}/exit")
    void exit(@PathParam("taskId") long taskId, @QueryParam("userId") String userId);


    @PUT
    @Path("{taskId}/fail")
    void fail(@PathParam("taskId") long taskId, @QueryParam("userId") String userId, JaxbMapRequest faultData);


    @PUT
    @Path("{taskId}/forward")
    void forward(@PathParam("taskId") long taskId, @QueryParam("userId") String userId, String targetEntityId);

    @PUT
    @Path("{taskId}/release")
    void release(@PathParam("taskId") long taskId, @QueryParam("userId") String userId);

    @PUT
    @Path("{taskId}/resume")
    void resume(@PathParam("taskId") long taskId, @QueryParam("userId") String userId);

    @PUT
    @Path("{taskId}/skip")
    void skip(@PathParam("taskId") long taskId, @QueryParam("userId") String userId);

    @PUT
    @Path("{taskId}/start")
    void start(@PathParam("taskId") long taskId, @QueryParam("userId") String userId);

    @PUT
    @Path("{taskId}/stop")
    void stop(@PathParam("taskId") long taskId, @QueryParam("userId") String userId);

    @PUT
    @Path("{taskId}/suspend")
    void suspend(@PathParam("taskId") long taskId, @QueryParam("userId") String userId);

    @PUT
    @Path("{taskId}/nominate")
    void nominate(@PathParam("taskId") long taskId, @QueryParam("userId") String userId, List<OrganizationalEntity> potentialOwners);


    @GET
    @Path("/workitem/{workItemId}/task")
    JaxbTask getTaskByWorkItemId(@PathParam("workItemId") long workItemId);


    @GET
    @Path("{taskId}")
    JaxbTask getTaskById(@PathParam("taskId") long taskId);

    @PUT
    @Path("assigned/{userId}/administrator")
    JaxbTaskSummaryListResponse getTasksAssignedAsBusinessAdministrator(@PathParam("userId") String userId, @QueryParam("lang") String language);

    @PUT
    @Path("assigned/{userId}/potential/all")
    JaxbTaskSummaryListResponse getTasksAssignedAsPotentialOwner(@PathParam("userId") String userId, @QueryParam("lang") String language);


    @PUT
    @Path("assigned/{userId}/potential/status")
    JaxbTaskSummaryListResponse getTasksAssignedAsPotentialOwnerByStatus(@PathParam("userId") String userId, List<Status> status, @QueryParam("lang") String language);


    @PUT
    @Path("assigned/{userId}/owner/all")
    JaxbTaskSummaryListResponse getTasksOwned(@PathParam("userId") String userId, @QueryParam("lang") String language);


    @PUT
    @Path("assigned/{userId}/owner/status")
    JaxbTaskSummaryListResponse getTasksOwnedByStatus(@PathParam("userId") String userId, List<Status> status, @QueryParam("lang") String language);


    @PUT
    @Path("/process/instance/{processInstanceId}/tasks/all")
    JaxbLongListResponse getTasksByProcessInstanceId(@PathParam("processInstanceId") long processInstanceId);

    
    @PUT
    @Path("/process/instance/{processInstanceId}/tasks/status")
    JaxbTaskSummaryListResponse getTasksByStatusByProcessInstanceId(@PathParam("processInstanceId") long processInstanceId, List<Status> status, @QueryParam("lang") String language);

    
    @GET
    @Path("/content/{contentId}")
    JaxbContent getContentById(@PathParam("contentId") long contentId);

    @GET
    @Path("/attachment/{attachId}")
    JaxbAttachment getAttachmentById(@PathParam("attachId") long attachId);
    
}
