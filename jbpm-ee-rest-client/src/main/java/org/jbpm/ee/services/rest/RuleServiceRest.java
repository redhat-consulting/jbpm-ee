package org.jbpm.ee.services.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jbpm.ee.services.RuleService;


/**
 * Rest interface equivalent to {@link RuleService}.  Returns JAXB types.
 * 
 * @see RuleService
 * @author bradsdavis
 *
 */
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
@Path("/process")
public interface RuleServiceRest {

    @PUT
    @Path("/instance/{processInstanceId}/rule/fire/all")
	int fireAllRules(@PathParam("processInstanceId") Long processInstanceId);
	

    @PUT
    @Path("/instance/{processInstanceId}/rule/fire/max/{max}")
	int fireAllRules(@PathParam("processInstanceId") Long processInstanceId, @PathParam("max") int max);
	

    @POST
    @Path("/instance/{processInstanceId}/rule/insert")
    void insert( @PathParam("processInstanceId") Long processInstanceId, Object object);
    
}
