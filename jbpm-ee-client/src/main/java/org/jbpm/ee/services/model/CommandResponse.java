package org.jbpm.ee.services.model;

import java.io.Serializable;

import org.drools.core.command.impl.GenericCommand;

/**
 * CommandResponse represents the full Command and its response. 
 * Allows for null responses for commands that allow null as return value
 * 
 * @author abaxter
 *
 */
public class CommandResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7629488473951391225L;

	private GenericCommand<?> command;
	
	private Object response;

	public GenericCommand<?> getCommand() {
		return command;
	}

	public void setCommand(GenericCommand<?> command) {
		this.command = command;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((command == null) ? 0 : command.hashCode());
		result = prime * result
				+ ((response == null) ? 0 : response.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommandResponse other = (CommandResponse) obj;
		if (command == null) {
			if (other.command != null)
				return false;
		} else if (!command.equals(other.command))
			return false;
		if (response == null) {
			if (other.response != null)
				return false;
		} else if (!response.equals(other.response))
			return false;
		return true;
	}
}
