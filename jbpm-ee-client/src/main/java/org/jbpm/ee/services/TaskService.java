package org.jbpm.ee.services;

import java.util.List;
import java.util.Map;

import org.jbpm.ee.services.ejb.annotations.PreprocessClassloader;
import org.jbpm.ee.services.ejb.annotations.ProcessInstanceId;
import org.jbpm.ee.services.ejb.annotations.TaskId;
import org.jbpm.ee.services.ejb.annotations.WorkItemId;
import org.kie.api.task.model.Attachment;
import org.kie.api.task.model.Content;
import org.kie.api.task.model.OrganizationalEntity;
import org.kie.api.task.model.Status;
import org.kie.api.task.model.Task;
import org.kie.api.task.model.TaskSummary;

/**
 * 
 * @author bdavis, abaxter
 *
 * Interface for interacting with Tasks to the BPMS system.
 */
public interface TaskService {

	/**
	 * Reactivate a previously suspended task (?)
	 * 
	 * @param taskId
	 * @param userId
	 */
	void activate(@TaskId long taskId, String userId);

	/**
	 * Claim a task for user
	 * 
	 * @param taskId
	 * @param userId
	 */
	void claim(@TaskId long taskId, String userId);

	/**
	 * Claim next task user is eligable for
	 * 
	 * @param userId
	 * @param language
	 */
	void claimNextAvailable(String userId, String language);

	/**
	 * Complete a task with the given data
	 * 
	 * @param taskId
	 * @param userId
	 * @param data
	 */
	@PreprocessClassloader
	void complete(@TaskId long taskId, String userId, Map<String, Object> data);

	/**
	 * 
	 * Delegate a task from userId to targetUserId
	 * 
	 * @param taskId
	 * @param userId
	 * @param targetUserId
	 */
	void delegate(@TaskId long taskId, String userId, String targetUserId);

	/**
	 * Exit a task (?)
	 * 
	 * @param taskId
	 * @param userId
	 */
	void exit(@TaskId long taskId, String userId);

	/**
	 * Fail a task with the provided fault data (?)
	 * 
	 * @param taskId
	 * @param userId
	 * @param faultData
	 */
	void fail(@TaskId long taskId, String userId, Map<String, Object> faultData);

	/**
	 * Forward a task from userId to target user/organization (?)
	 * 
	 * @param taskId
	 * @param userId
	 * @param targetEntityId
	 */
	void forward(@TaskId long taskId, String userId, String targetEntityId);

	/**
	 * Release a previously claimed task
	 * 
	 * @param taskId
	 * @param userId
	 */
	void release(@TaskId long taskId, String userId);

	/**
	 * Resume a previously suspended task (?)
	 * 
	 * @param taskId
	 * @param userId
	 */
	void resume(@TaskId long taskId, String userId);

	/**
	 * Skip a claimed task
	 * 
	 * @param taskId
	 * @param userId
	 */
	void skip(@TaskId long taskId, String userId);

	/**
	 * Start a claimed task
	 * 
	 * @param taskId
	 * @param userId
	 */
	void start(@TaskId long taskId, String userId);

	/**
	 * Stop a claimed task (?)
	 * 
	 * @param taskId
	 * @param userId
	 */
	void stop(@TaskId long taskId, String userId);

	/**
	 * Suspend a claimed task (?)
	 * 
	 * @param taskId
	 * @param userId
	 */
	void suspend(@TaskId long taskId, String userId);

	/**
	 * Nominate a task to be handled by potentialOwners
	 * 
	 * @param taskId
	 * @param userId
	 * @param potentialOwners
	 */
	void nominate(@TaskId long taskId, String userId, List<OrganizationalEntity> potentialOwners);

	/**
	 * Return a task by its workItemId 
	 *  
	 * @param workItemId
	 * @return
	 */
	Task getTaskByWorkItemId(@WorkItemId long workItemId);

	/**
	 * Return a task by its taskId
	 * 
	 * @param taskId
	 * @return
	 */
	Task getTaskById(@TaskId long taskId);

	/**
	 * Return a list of forwarded tasks (?)
	 * 
	 * @param userId
	 * @param language
	 * @return
	 */
	List<TaskSummary> getTasksAssignedAsBusinessAdministrator(String userId, String language);

	/**
	 * Return a list of tasks the user is eligable for
	 * 
	 * @param userId
	 * @param language
	 * @return
	 */
	List<TaskSummary> getTasksAssignedAsPotentialOwner(String userId, String language);

	/**
	 * Return a list of tasks the user is eligable for with one of the listed statuses
	 * 
	 * @param userId
	 * @param status
	 * @param language
	 * @return
	 */
	List<TaskSummary> getTasksAssignedAsPotentialOwnerByStatus(String userId, List<Status> status, String language);

	/**
	 * Return a list of tasks the user has claimed
	 * 
	 * @param userId
	 * @param language
	 * @return
	 */
	List<TaskSummary> getTasksOwned(String userId, String language);

	/**
	 * Return a list of tasks the user has claimed with one of the listed statuses
	 * 
	 * @param userId
	 * @param status
	 * @param language
	 * @return
	 */
	List<TaskSummary> getTasksOwnedByStatus(String userId, List<Status> status, String language);

	/**
	 * Get a list of tasks the Process Instance is waiting on
	 * 
	 * @param processInstanceId
	 * @return
	 */
	List<Long> getTasksByProcessInstanceId(@ProcessInstanceId long processInstanceId);

	/**
	 * Get a list of tasks the Process Instance is waiting on with one of the listed statuses
	 * 
	 * @param processInstanceId
	 * @param status
	 * @param language
	 * @return
	 */
	List<TaskSummary> getTasksByStatusByProcessInstanceId(@ProcessInstanceId long processInstanceId, List<Status> status, String language);

	/**
	 * 
	 * @param contentId
	 * @return
	 */
	Content getContentById(long contentId);

	/**
	 * 
	 * @param attachId
	 * @return
	 */
	Attachment getAttachmentById(long attachId);
    
}
