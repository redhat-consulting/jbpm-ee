package org.jbpm.ee.services.ejb.remote;

import javax.ejb.Remote;

import org.jbpm.ee.services.WorkItemService;

/**
 * Remote EJB interface for WorkItemService.
 * 
 * @author bdavis, abaxter
 *
 */
@Remote
public interface WorkItemServiceRemote extends WorkItemService {

}
