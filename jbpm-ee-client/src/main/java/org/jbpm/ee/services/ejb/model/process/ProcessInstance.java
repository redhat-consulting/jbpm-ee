package org.jbpm.ee.services.ejb.model.process;

import java.io.Serializable;

import org.kie.api.definition.process.Process;

public class ProcessInstance implements org.kie.api.runtime.process.ProcessInstance, Serializable {

	private final String[] eventTypes; 
	private final String processId;
	private final String processName; 
	private final Long id;
	private final int state;
	
	public ProcessInstance(org.kie.api.runtime.process.ProcessInstance instance) {
		this.eventTypes = instance.getEventTypes();
		this.processId = instance.getProcessId();
		this.processName = instance.getProcessName();
		this.id = instance.getId();
		this.state = instance.getState();
	}
	
	@Override
	public void signalEvent(String type, Object event) {
		throw new UnsupportedOperationException("Use Service API to signal event.");
	}

	@Override
	public String[] getEventTypes() {
		return this.eventTypes;
	}

	@Override
	public String getProcessId() {
		return this.processId;
	}

	@Override
	public Process getProcess() {
		throw new UnsupportedOperationException("Process not provided from service API.");
	}

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public String getProcessName() {
		return this.processName;
	}

	@Override
	public int getState() {
		return this.state;
	}

}
