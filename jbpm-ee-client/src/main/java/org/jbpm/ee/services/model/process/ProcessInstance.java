package org.jbpm.ee.services.model.process;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.kie.api.definition.process.Process;


@XmlRootElement(name="process-instance")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProcessInstance implements org.kie.api.runtime.process.ProcessInstance, Serializable {

	@XmlElement(name = "process-id")
	private String processId;

	@XmlElement
	private Long id;

	@XmlElement
	private Integer state;

	@XmlElement(name="process-name")
	private String processName;

	
	@XmlElement(name = "event-types")
	private List<String> eventTypes;

	public ProcessInstance() {
		// default
	}
	
	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public void setState(Integer state) {
		this.state = state;
	}



	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setEventTypes(List<String> eventTypes) {
		this.eventTypes = eventTypes;
	}

	public ProcessInstance(org.kie.api.runtime.process.ProcessInstance instance) {
		this.processId = instance.getProcessId();
		this.id = instance.getId();
		this.state = instance.getState();
		this.processName = instance.getProcessName();

		if(instance.getEventTypes() != null) {
			eventTypes = Arrays.asList(instance.getEventTypes());
		} else {
			eventTypes = new LinkedList<String>();
		}
	}
	
	
	@Override
	public void signalEvent(String type, Object event) {
		throw new java.lang.UnsupportedOperationException("Use the ProcessService to signal events on the ProcessInstance.");
	}

	@Override
	public String[] getEventTypes() {
		if(eventTypes != null) {
			return eventTypes.toArray(new String[eventTypes.size()]);
		}
		return new String[0];
	}

	@Override
	public String getProcessId() {
		return this.processId;
	}

	@Override
	public Process getProcess() {
		throw new java.lang.UnsupportedOperationException("Unable to obtain the Process from the ProcessInstance.");
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
