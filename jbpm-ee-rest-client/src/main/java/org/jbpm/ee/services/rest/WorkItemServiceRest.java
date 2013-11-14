package org.jbpm.ee.services.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jbpm.ee.services.WorkItemService;
import org.jbpm.ee.services.rest.request.JaxbMapRequest;
import org.kie.services.client.serialization.jaxb.impl.JaxbWorkItem;

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
public interface WorkItemServiceRest {

    @PUT
    @Path("{id}/complete")
    void completeWorkItem(@PathParam("id") long id, JaxbMapRequest results);

    @PUT
    @Path("{id}/abort")
    void abortWorkItem(@PathParam("id") long id);
    
    @GET
    @Path("{id}")
    JaxbWorkItem getWorkItem(@PathParam("id") long id);

}
