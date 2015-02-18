package org.jbpm.ee.client;

public class ClientException extends Exception {

	private static final long serialVersionUID = -3718330086178615573L;

	public ClientException(String message, Throwable t) {
		super(message, t);
	}
}
