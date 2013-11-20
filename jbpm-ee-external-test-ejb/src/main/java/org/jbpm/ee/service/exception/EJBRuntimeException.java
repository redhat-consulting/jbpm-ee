package org.jbpm.ee.service.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class EJBRuntimeException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5352770984401702346L;

	public EJBRuntimeException(String message) {
		super(message);
	}
}
