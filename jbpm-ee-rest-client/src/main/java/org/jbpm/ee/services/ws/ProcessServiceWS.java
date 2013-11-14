package org.jbpm.ee.services.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.ws.request.JaxbInitializeProcessRequest;
import org.kie.services.client.serialization.jaxb.impl.JaxbProcessInstanceResponse;

/**
 * Rest interface equivalent to {@link ProcessService}.  Returns JAXB types.
 * 
 * @see ProcessService
 * @author bradsdavis
 *
 */
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
@Path("/process")
@WebService
public interface ProcessServiceWS {

	@WebMethod
    @POST
    @Path("/{processId}/start")
    JaxbProcessInstanceResponse startProcess(@PathParam("processId") String processId, JaxbInitializeProcessRequest request);
    

	@WebMethod
    @POST
    @Path("/{processId}")
    @Produces({ "application/xml" })
    JaxbProcessInstanceResponse createProcessInstance(@PathParam("processId") String processId, JaxbInitializeProcessRequest request);


	@WebMethod
    @PUT
    @Path("/instance/{processInstanceId}/start")
    JaxbProcessInstanceResponse startProcessInstance(@PathParam("processInstanceId") long processInstanceId);


	@WebMethod
    @PUT
    @Path("instance/{processInstanceId}/event/signal")
    void signalEvent(String type, Object event, @PathParam("processInstanceId") long processInstanceId);


    @GET
    @Path("instance/{processInstanceId}")
    JaxbProcessInstanceResponse getProcessInstance(@PathParam("processInstanceId") long processInstanceId);


    @WebMethod
    @PUT
    @Path("instance/{processInstanceId}/abort")
    void abortProcessInstance(@PathParam("processInstanceId") long processInstanceId);

}
