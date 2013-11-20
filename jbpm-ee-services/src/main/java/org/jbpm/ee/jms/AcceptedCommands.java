package org.jbpm.ee.jms;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.drools.core.command.runtime.process.AbortProcessInstanceCommand;
import org.drools.core.command.runtime.process.AbortWorkItemCommand;
import org.drools.core.command.runtime.process.CompleteWorkItemCommand;
import org.drools.core.command.runtime.process.GetProcessInstanceCommand;
import org.drools.core.command.runtime.process.GetWorkItemCommand;
import org.drools.core.command.runtime.process.SetProcessInstanceVariablesCommand;
import org.drools.core.command.runtime.process.SignalEventCommand;
import org.drools.core.command.runtime.process.StartProcessInstanceCommand;
import org.jbpm.services.task.commands.ActivateTaskCommand;
import org.jbpm.services.task.commands.ClaimTaskCommand;
import org.jbpm.services.task.commands.CompleteTaskCommand;
import org.jbpm.services.task.commands.DelegateTaskCommand;
import org.jbpm.services.task.commands.ExitTaskCommand;
import org.jbpm.services.task.commands.FailTaskCommand;
import org.jbpm.services.task.commands.ForwardTaskCommand;
import org.jbpm.services.task.commands.GetTaskByWorkItemIdCommand;
import org.jbpm.services.task.commands.GetTaskCommand;
import org.jbpm.services.task.commands.GetTasksByProcessInstanceIdCommand;
import org.jbpm.services.task.commands.GetTasksByStatusByProcessInstanceIdCommand;
import org.jbpm.services.task.commands.NominateTaskCommand;
import org.jbpm.services.task.commands.ReleaseTaskCommand;
import org.jbpm.services.task.commands.SkipTaskCommand;
import org.jbpm.services.task.commands.StartTaskCommand;
import org.jbpm.services.task.commands.StopTaskCommand;
import org.jbpm.services.task.commands.SuspendTaskCommand;

// should extend org.kie.services.client.api.command.AcceptedCommands once it's available
@SuppressWarnings("rawtypes")
public class AcceptedCommandSets {

	private static Set<Class> commandsWithProcessInstanceId =
			new HashSet<Class>();
	
	private static Set<Class> commandsWithWorkItemId =
			new HashSet<Class>();

	private static Set<Class> commandsWithTaskId =
			new HashSet<Class>();
	
	private static Set<Class> commandsThatInfluenceKieSession =
			new HashSet<Class>();
	
	static {
		commandsWithProcessInstanceId.add(AbortProcessInstanceCommand.class);
		commandsWithProcessInstanceId.add(GetProcessInstanceCommand.class);
		commandsWithProcessInstanceId.add(SetProcessInstanceVariablesCommand.class);
		commandsWithProcessInstanceId.add(SignalEventCommand.class);
		commandsWithProcessInstanceId.add(StartProcessInstanceCommand.class);
		commandsWithProcessInstanceId.add(GetTasksByProcessInstanceIdCommand.class);
		commandsWithProcessInstanceId.add(GetTasksByStatusByProcessInstanceIdCommand.class);
		commandsWithProcessInstanceId = Collections.unmodifiableSet(commandsWithProcessInstanceId);
		
		commandsWithWorkItemId.add(AbortWorkItemCommand.class);
		commandsWithWorkItemId.add(CompleteWorkItemCommand.class);
		commandsWithWorkItemId.add(GetWorkItemCommand.class);
		commandsWithWorkItemId.add(GetTaskByWorkItemIdCommand.class);
		commandsWithWorkItemId = Collections.unmodifiableSet(commandsWithWorkItemId);
		
		commandsWithTaskId.add(ActivateTaskCommand.class);
		commandsWithTaskId.add(ClaimTaskCommand.class);
		commandsWithTaskId.add(CompleteTaskCommand.class);
		commandsWithTaskId.add(DelegateTaskCommand.class);
		commandsWithTaskId.add(ExitTaskCommand.class);
		commandsWithTaskId.add(FailTaskCommand.class);
		commandsWithTaskId.add(ForwardTaskCommand.class);
		commandsWithTaskId.add(GetTaskCommand.class);
		commandsWithTaskId.add(NominateTaskCommand.class);
		commandsWithTaskId.add(ReleaseTaskCommand.class);
		commandsWithTaskId.add(SkipTaskCommand.class);
		commandsWithTaskId.add(StartTaskCommand.class);
		commandsWithTaskId.add(StopTaskCommand.class);
		commandsWithTaskId.add(SuspendTaskCommand.class);
		commandsWithTaskId = Collections.unmodifiableSet(commandsWithTaskId);
		
		commandsThatInfluenceKieSession.add(CompleteTaskCommand.class);
		commandsThatInfluenceKieSession.add(ExitTaskCommand.class);
		commandsThatInfluenceKieSession.add(FailTaskCommand.class);
		commandsThatInfluenceKieSession.add(SkipTaskCommand.class);
		commandsThatInfluenceKieSession = Collections.unmodifiableSet(commandsThatInfluenceKieSession);
	}

	public static Set<Class> getCommandsWithProcessInstanceId() {
		return commandsWithProcessInstanceId;
	}

	public static Set<Class> getCommandsWithWorkItemid() {
		return commandsWithWorkItemId;
	}
	
	public static Set<Class> getCommandsThatInfluenceKieSession() {
		return commandsThatInfluenceKieSession;
	}
	
	public static Set<Class> getCommandsWithTaskId() {
		return commandsWithTaskId;
	}
	
}

