package org.jbpm.ee.services.ejb.local;

import javax.ejb.Local;

import org.jbpm.ee.services.WorkItemService;

/**
 * Local EJB interface for WorkItemService.
 * 
 * @author bdavis, abaxter
 *
 */
@Local
public interface WorkItemServiceLocal extends WorkItemService {

}
