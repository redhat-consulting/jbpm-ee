package org.jbpm.ee.services.model.rules;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * FactHandle implementation that supports JAXB.
 * @see org.drools.core.common.DefaultFactHandle
 * 
 * @author abaxter
 *
 */

@XmlRootElement(name="fact-handle")
@XmlAccessorType(XmlAccessType.FIELD)
public class FactHandle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "external-form")
	private String externalForm;
	
	public FactHandle() {
		externalForm = "";
	}
	
	public FactHandle(String externalForm) {
		this.externalForm = externalForm;
	}

	public String getExternalForm() {
		return externalForm;
	}

	public void setExternalForm(String externalForm) {
		this.externalForm = externalForm;
	}
}
