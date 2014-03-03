package org.jbpm.ee.services.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jbpm.ee.services.RuleService;
import org.jbpm.ee.services.model.rules.FactHandle;


/**
 * Rest interface equivalent to {@link RuleService}.  Returns JAXB types.
 * 
 * @see RuleService
 * @author bradsdavis
 *
 */
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
@Path("/rule")
@WebService
public interface RuleServiceWS {

	@WebMethod
    @PUT
    @Path("/instance/{processInstanceId}/rule/fire/all")
	int fireAllRules(
			@WebParam(name="processInstanceId") @PathParam("processInstanceId") Long processInstanceId
	);
	

    @WebMethod(operationName="fireAllRulesWithMax")
    @PUT
    @Path("/instance/{processInstanceId}/rule/fire/max/{max}")
	int fireAllRules(
			@WebParam(name="processInstanceId") @PathParam("processInstanceId") Long processInstanceId, 
			@WebParam(name="max") @PathParam("max") int max
	);
	

    @WebMethod
    @POST
    @Path("/instance/{processInstanceId}/rule/insert")
    FactHandle insert(
    		@WebParam(name="processInstanceId") @PathParam("processInstanceId") Long processInstanceId, 
    		@WebParam(name="object") Object object
    );
    
}
