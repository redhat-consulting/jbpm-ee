package org.jbpm.ee.test.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class TestRuntimeException extends RuntimeException {
	
	public TestRuntimeException(String message) {
		super(message);
	}
}
