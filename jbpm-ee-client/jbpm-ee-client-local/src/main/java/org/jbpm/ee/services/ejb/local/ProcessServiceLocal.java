package org.jbpm.ee.services.ejb.local;

import javax.ejb.Local;

import org.jbpm.ee.services.ProcessService;

/**
 * Local EJB interface for ProcessService.
 * 
 * @author bdavis, abaxter
 *
 */
@Local
public interface ProcessServiceLocal extends ProcessService {
	
}
