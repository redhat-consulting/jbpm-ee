package org.jbpm.ee.services;

import java.util.Map;

import org.jbpm.ee.services.ejb.annotations.PreprocessClassloader;
import org.jbpm.ee.services.ejb.annotations.ReleaseId;
import org.jbpm.ee.services.ejb.annotations.ProcessInstanceId;
import org.jbpm.ee.support.KieReleaseId;
import org.kie.api.runtime.process.ProcessInstance;
/**
 * 
 * @author bdavis, abaxter 
 * 
 * For starting, creating, and aborting processes and signaling events to a process
 */
public interface ProcessService {
	
	/**
	 * Starts a process with no variables
	 * 
	 * @param releaseId Deployment information for the process's kjar
	 * @param processId The process's name 
	 * @return Process instance information
	 */
	ProcessInstance startProcess(@ReleaseId KieReleaseId releaseId, String processId);
	
	/**
	 * Starts a process with provided variables
	 * 
	 * @param releaseId Deployment information for the process's kjar
	 * @param processId The process's name 
	 * @param parameters Process variables to start with
	 * @return Process instance information
	 */
	@PreprocessClassloader
	ProcessInstance startProcess(@ReleaseId KieReleaseId releaseId, String processId, Map<String, Object> parameters);
	
	/**
	 * Created, but doesn't start, a process with provided variables
	 * 
	 * @param releaseId Deployment information for the process's kjar
	 * @param processId The process's name 
	 * @param parameters Process variables to start with
	 * @return Process instance information
	 */
	@PreprocessClassloader
	ProcessInstance createProcessInstance(@ReleaseId KieReleaseId releaseId, String processId, Map<String, Object> parameters);
	
	/**
	 * Starts a previously created process
	 * 
	 * @param processInstanceId The process instance's unique identifier
	 * @return Process instance information
	 */
	ProcessInstance startProcessInstance(@ProcessInstanceId long processInstanceId);
	
	/**
	 * Signal an event to a single process
	 * 
	 * @param type The event's ID in the process
	 * @param event The event object to be passed in with the event
	 * @param processInstanceId The process instance's unique identifier
	 */
	void signalEvent(@ProcessInstanceId long processInstanceId, String type, Object event);
	
	
	/**
	 * Returns process instance information. Will return null if no
	 * active process with that id is found
	 * 
	 * @param processInstanceId The process instance's unique identifier
	 * @return Process instance information
	 */
	ProcessInstance getProcessInstance(@ProcessInstanceId long processInstanceId);
	
	/**
	 * Aborts the specified process
	 * 
	 * @param processInstanceId The process instance's unique identifier
	 */
	void abortProcessInstance(@ProcessInstanceId long processInstanceId);
}
