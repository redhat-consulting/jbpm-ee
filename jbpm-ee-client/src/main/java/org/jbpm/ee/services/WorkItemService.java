package org.jbpm.ee.services;

import java.util.Map;

import org.jbpm.ee.services.ejb.annotations.PreprocessClassloader;
import org.jbpm.ee.services.ejb.annotations.WorkItemId;
import org.kie.api.runtime.process.WorkItem;

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
	@PreprocessClassloader
    void completeWorkItem(@WorkItemId long id, Map<String, Object> results);

    /**
     * Abort the specified WorkItem
     * 
     * @param id WorkItem ID
     */
    void abortWorkItem(@WorkItemId long id);
    
    /**
     * Returns the specified WorkItem
     * 
     * @param id WorkItem ID
     * @return The specified WorkItem
     */
    WorkItem getWorkItem(@WorkItemId long id);

}
