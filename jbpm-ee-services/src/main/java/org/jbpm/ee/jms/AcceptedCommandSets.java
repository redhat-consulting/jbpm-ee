package org.jbpm.ee.jms;

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
import org.jbpm.services.task.commands.GetTaskByWorkItemIdCommand;
import org.jbpm.services.task.commands.GetTasksByProcessInstanceIdCommand;
import org.jbpm.services.task.commands.GetTasksByStatusByProcessInstanceIdCommand;

@SuppressWarnings("rawtypes")
public class AcceptedCommandSets {

	private static final Set<Class> commandsWithProcessInstanceId =
			new HashSet<Class>();
	
	private static final Set<Class> commandsWithWorkItemId =
			new HashSet<Class>();

	private static final Set<Class> commandsWithoutExistingKSession=
			new HashSet<Class>();
	
	static {
		commandsWithProcessInstanceId.add(AbortProcessInstanceCommand.class);
		commandsWithProcessInstanceId.add(GetProcessInstanceCommand.class);
		commandsWithProcessInstanceId.add(SetProcessInstanceVariablesCommand.class);
		commandsWithProcessInstanceId.add(SignalEventCommand.class);
		commandsWithProcessInstanceId.add(StartProcessInstanceCommand.class);
		commandsWithProcessInstanceId.add(GetTasksByProcessInstanceIdCommand.class);
		commandsWithProcessInstanceId.add(GetTasksByStatusByProcessInstanceIdCommand.class);
		
		commandsWithWorkItemId.add(AbortWorkItemCommand.class);
		commandsWithWorkItemId.add(CompleteWorkItemCommand.class);
		commandsWithWorkItemId.add(GetWorkItemCommand.class);
		commandsWithWorkItemId.add(GetTaskByWorkItemIdCommand.class);
	}

	public static Set<Class> getCommandsWithProcessInstanceId() {
		return commandsWithProcessInstanceId;
	}

	public static Set<Class> getCommandsWithWorkItemid() {
		return commandsWithWorkItemId;
	}
	
	public static Set<Class> getCommandsWithoutExistingKSession() {
		return commandsWithoutExistingKSession;
	}
	
	
}

