package org.jbpm.ee.services.ws.exceptions;

import javax.xml.ws.WebFault;

/***
 * Web Service Fault exception, which transmits the cause of the exception to the calling application.
 * 
 * @author bradsdavis
 *
 */
@WebFault(name = "RemoteServiceException", targetNamespace = "http://jbpm.org/v6", faultBean="org.jbpm.ee.services.ws.exceptions.RemoteServiceFault")
public class RemoteServiceException extends RuntimeException {
	
	private final RemoteServiceFault fault;
	
	public RemoteServiceException(String message, Throwable t) {
		super(message, t);
		
		this.fault = new RemoteServiceFault(message);
	}
	
	public RemoteServiceException(Throwable t) {
		super(t.getMessage(), t);
		
		this.fault = new RemoteServiceFault(t.getMessage());
	}
	
	public RemoteServiceFault getFaultInfo() {
		return this.fault;
	}
	
	
}
