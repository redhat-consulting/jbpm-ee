package org.jbpm.ee.services.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="response")
public class JaxbObjectRequest {

	@XmlElement(name="object")
	@XmlJavaTypeAdapter(org.jbpm.ee.services.model.adapter.ObjectXmlAdapter.class)
	private Serializable object;

	public Serializable getObject() {
		return object;
	}
	
	public void setObject(Serializable object) {
		this.object = object;
	}
	
	public JaxbObjectRequest() {
	}
	
	public JaxbObjectRequest(Serializable obj) {
		this.object = obj;
	}
	
	
}
