package org.jbpm.ee.services.ws.exceptions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/***
 * JAXB safe transport for exception information.
 * 
 * @author bradsdavis
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RemoteServiceFault {

	private String message;
	
	public RemoteServiceFault(String message) {
		this.message = message;
	}
	
	public RemoteServiceFault() { }
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
