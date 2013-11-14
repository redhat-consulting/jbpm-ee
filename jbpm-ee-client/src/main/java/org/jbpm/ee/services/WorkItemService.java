package org.jbpm.ee.services;

import java.util.Map;

import org.drools.core.process.instance.WorkItem;

/**
 * 
 * @author bdavis, abaxter
 *
 * Interface for completing, aborting, and getting a WorkItem
 */
public interface WorkItemService {

	/**
	 * Completes the specified WorkItem with the given results
	 * 
	 * @param id WorkItem ID
	 * @param results Results of the WorkItem
	 */
    void completeWorkItem(long id, Map<String, Object> results);

    /**
     * Abort the specified WorkItem
     * 
     * @param id WorkItem ID
     */
    void abortWorkItem(long id);
    
    /**
     * Returns the specified WorkItem
     * 
     * @param id WorkItem ID
     * @return The specified WorkItem
     */
    WorkItem getWorkItem(long id);

}
