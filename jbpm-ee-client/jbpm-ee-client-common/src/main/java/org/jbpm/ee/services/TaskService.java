package org.jbpm.ee.services;

import java.util.List;
import java.util.Map;

import org.jbpm.ee.services.ejb.annotations.ContentId;
import org.jbpm.ee.services.ejb.annotations.LazilyDeserialized;
import org.jbpm.ee.services.ejb.annotations.PreprocessClassloader;
import org.jbpm.ee.services.ejb.annotations.ProcessInstanceId;
import org.jbpm.ee.services.ejb.annotations.TaskId;
import org.jbpm.ee.services.ejb.annotations.WorkItemId;
import org.jbpm.ee.services.model.KieReleaseId;
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
 *         Interface for interacting with Tasks to the BPMS system.
 */
public interface TaskService {

	/**
	 * Activate the task, i.e. set the task to status Ready.
	 * 
	 * @param taskId
	 * @param userId
	 */
	void activate(@TaskId long taskId, String userId);

	/**
	 * Claim responsibility for a task, i.e. set the task to status Reserved
	 * 
	 * @param taskId
	 * @param userId
	 */
	void claim(@TaskId long taskId, String userId);

	/**
	 * Claim next task user is eligible for
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
	void complete(@TaskId long taskId, String userId, @LazilyDeserialized Map<String, Object> data);

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
	 * Requesting application is no longer interested in the task output
	 * 
	 * 
	 * @param taskId
	 * @param userId
	 */
	void exit(@TaskId long taskId, String userId);

	/**
	 * Actual owner completes the execution of the task raising a fault. The
	 * fault illegalOperationFault is returned if the task interface defines no
	 * faults. If fault name or fault data is not set the operation returns
	 * illegalArgumentFault.
	 * 
	 * 
	 * @param taskId
	 * @param userId
	 * @param faultData
	 */
	@PreprocessClassloader
	void fail(@TaskId long taskId, String userId, @LazilyDeserialized Map<String, Object> faultData);

	/**
	 * Forward the task to another organization entity. The caller has to
	 * specify the receiving organizational entity. Potential owners can only
	 * forward a task while the task is in the Ready state. For details on
	 * forwarding human tasks refer to section 4.7.3 in WS-HumanTask_v1.pdf
	 * 
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
	 * Resume a previously suspended task
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
	 * Start the execution of the task, i.e. set the task to status InProgress.
	 * 
	 * 
	 * @param taskId
	 * @param userId
	 */
	void start(@TaskId long taskId, String userId);

	/**
	 * Cancel/stop the processing of the task. The task returns to the Reserved
	 * state.
	 * 
	 * 
	 * @param taskId
	 * @param userId
	 */
	void stop(@TaskId long taskId, String userId);

	/**
	 * Suspend a claimed task.
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
	void nominate(@TaskId long taskId, String userId,
			List<OrganizationalEntity> potentialOwners);

	/**
	 * Return a task by its workItemId.
	 * 
	 * @param workItemId
	 * @return
	 */
	Task getTaskByWorkItemId(@WorkItemId long workItemId);

	/**
	 * Return a task by its taskId.
	 * 
	 * @param taskId
	 * @return
	 */
	Task getTaskById(@TaskId long taskId);

	/**
	 * Return a list of forwarded tasks as a Business Administrator. Business
	 * administrators play the same role as task stakeholders but at task type
	 * level. Therefore, business administrators can perform the exact same
	 * operations as task stakeholders. Business administrators may also observe
	 * the progress of notifications.
	 * 
	 * @param userId
	 * @param language
	 * @return
	 */
	List<TaskSummary> getTasksAssignedAsBusinessAdministrator(String userId,
			String language);

	/**
	 * Return a list of tasks the user is eligible for.
	 * 
	 * @param userId
	 * @param language
	 * @return
	 */
	List<TaskSummary> getTasksAssignedAsPotentialOwner(String userId,
			String language);

	/**
	 * Return a list of tasks the user is eligible for with one of the listed
	 * statuses.
	 * 
	 * @param userId
	 * @param status
	 * @param language
	 * @return
	 */
	List<TaskSummary> getTasksAssignedAsPotentialOwnerByStatus(String userId,
			List<Status> status, String language);

	/**
	 * Return a list of tasks the user has claimed.
	 * 
	 * @param userId
	 * @param language
	 * @return
	 */
	List<TaskSummary> getTasksOwned(String userId, String language);

	/**
	 * Return a list of tasks the user has claimed with one of the listed
	 * statuses.
	 * 
	 * @param userId
	 * @param status
	 * @param language
	 * @return
	 */
	List<TaskSummary> getTasksOwnedByStatus(String userId, List<Status> status,
			String language);

	/**
	 * Get a list of tasks the Process Instance is waiting on.
	 * 
	 * @param processInstanceId
	 * @return
	 */
	List<Long> getTasksByProcessInstanceId(
			@ProcessInstanceId long processInstanceId);

	/**
	 * Get a list of tasks the Process Instance is waiting on with one of the
	 * listed statuses.
	 * 
	 * @param processInstanceId
	 * @param status
	 * @param language
	 * @return
	 */
	List<TaskSummary> getTasksByStatusByProcessInstanceId(
			@ProcessInstanceId long processInstanceId, List<Status> status, String language);

	/**
	 * 
	 * @param contentId
	 * @return
	 */
	Content getContentById(@ContentId long contentId);

	/**
	 * 
	 * @param attachId
	 * @return
	 */
	Attachment getAttachmentById(long attachId);

	/**
	 * Gets the release id for the task instance.
	 * @param taskId Task ID
	 * @return
	 */
	KieReleaseId getReleaseId(@TaskId long taskId);

}
