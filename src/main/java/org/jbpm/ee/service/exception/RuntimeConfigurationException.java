package org.jbpm.ee.service.exception;

public class RuntimeConfigurationException extends RuntimeException {

	public RuntimeConfigurationException(String message) {
		super(message);
	}
	
	public RuntimeConfigurationException(String message, Throwable t) {
		super(message, t);
	}
}
