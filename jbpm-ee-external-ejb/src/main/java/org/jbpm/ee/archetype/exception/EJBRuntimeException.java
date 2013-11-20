package org.jbpm.ee.archetype.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class EJBRuntimeException extends RuntimeException {
	
	public EJBRuntimeException(String message) {
		super(message);
	}
}
