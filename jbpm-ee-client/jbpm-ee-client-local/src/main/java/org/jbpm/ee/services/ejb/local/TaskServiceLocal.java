package org.jbpm.ee.services.ejb.local;

import javax.ejb.Local;

import org.jbpm.ee.services.TaskService;

/**
 * Local EJB interface for TaskService.
 * 
 * @author bdavis, abaxter
 *
 */
@Local
public interface TaskServiceLocal extends TaskService {

}
