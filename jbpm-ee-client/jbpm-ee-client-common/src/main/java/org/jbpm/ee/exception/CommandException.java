package org.jbpm.ee.exception;

/**
 * Exception while processing commands.
 * 
 * @author bradsdavis
 * 
 */
public class CommandException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8936206270631909468L;

	public CommandException() {
		super();
	}

	public CommandException(String message) {
		super(message);
	}

	public CommandException(Throwable cause) {
		super(cause);
	}

	public CommandException(String message, Throwable t) {
		super(message, t);
	}
}
