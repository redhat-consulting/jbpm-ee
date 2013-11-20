package org.jbpm.ee.services.ws;

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
import javax.ws.rs.core.MediaType;

import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.model.process.ProcessInstance;
import org.jbpm.ee.services.ws.request.JaxbInitializeProcessRequest;

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
	@WebResult(name="process-instance") 
	ProcessInstance startProcess(
	    @WebParam(name="process-id") @PathParam("processId") String processId, 
	    @WebParam(name="request") JaxbInitializeProcessRequest request
	);


	@WebMethod
	@POST
	@Path("/{processId}")
	@Produces({ "application/xml" })
	@WebResult(name="process-instance") 
	ProcessInstance createProcessInstance(
	    @WebParam(name="process-id") @PathParam("processId") String processId,
	    @WebParam(name="request") JaxbInitializeProcessRequest request
	);


	@WebMethod
	@PUT
	@Path("/instance/{processInstanceId}/start")
	@WebResult(name="process-instance") 
	ProcessInstance startProcessInstance(
	    @WebParam(name="process-instance-id") @PathParam("processInstanceId") long processInstanceId
	);


	@WebMethod
	@PUT
	@Path("instance/{processInstanceId}/event/signal")
	void signalEvent(
	    @WebParam(name="process-instance-id") @PathParam("processInstanceId") long processInstanceId,
	    @WebParam(name="type") String type,
	    @WebParam(name="event") Object event
	);


	@GET
	@Path("instance/{processInstanceId}")
	@WebResult(name="process-instance") 
	ProcessInstance getProcessInstance(
	    @WebParam(name="process-instance-id") @PathParam("processInstanceId") long processInstanceId
	);


	@WebMethod
	@PUT
	@Path("instance/{processInstanceId}/abort")
	void abortProcessInstance(
	    @WebParam(name="process-instance-id") @PathParam("processInstanceId") long processInstanceId
	);

}
