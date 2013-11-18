package org.jbpm.ee.services.ws;

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
    @Path("{id}/complete")
    void completeWorkItem(
    		@WebParam(name="id") @PathParam("id") long id, 
    		@WebParam(name="request") JaxbMapRequest results
    );

	
	@WebMethod
    @PUT
    @Path("{id}/abort")
    void abortWorkItem(
    		@WebParam(name="id") @PathParam("id") long id
    );
    
	
	@WebMethod
    @GET
    @Path("{id}")
    WorkItem getWorkItem(
    		@WebParam(name="id") @PathParam("id") long id
    );

}
