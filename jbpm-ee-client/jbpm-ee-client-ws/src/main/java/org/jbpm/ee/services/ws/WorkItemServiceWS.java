package org.jbpm.ee.services.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jbpm.ee.services.WorkItemService;
import org.jbpm.ee.services.model.process.WorkItem;
import org.jbpm.ee.services.ws.request.JaxbMapRequest;

/**
 * Rest interface equivalent to {@link WorkItemService}.  Returns JAXB types.
 * 
 * @see WorkItemService
 * @author bradsdavis
 *
 */
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
@Path("/workitem")
@WebService
public interface WorkItemServiceWS {

	@WebMethod
    @PUT
    @Path("{workItemId}/complete")
    void completeWorkItem(
    		@WebParam(name="work-item-id") @PathParam("workItemId") long id, 
    		@WebParam(name="request") JaxbMapRequest results
    );

	
	@WebMethod
    @PUT
    @Path("{workItemId}/abort")
    void abortWorkItem(
    		@WebParam(name="work-item-id") @PathParam("workItemId") long id
    );
    
	
	@WebMethod
    @GET
    @Path("{workItemId}")
    WorkItem getWorkItem(
    		@WebParam(name="work-item-id") @PathParam("workItemId") long id
    );
	
	@WebMethod
    @GET
    @Path("by/process/instance/{processInstanceId}")
	List<WorkItem> getWorkItemByProcessInstance(
			@WebParam(name="process-instance-id") 
			@PathParam("processInstanceId") long processInstanceId
	);
}
