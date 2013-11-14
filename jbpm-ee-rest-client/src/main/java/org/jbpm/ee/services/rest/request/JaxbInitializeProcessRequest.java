package org.jbpm.ee.services.rest.request;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jbpm.ee.support.KieReleaseId;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbInitializeProcessRequest {
	
	private KieReleaseId releaseId;
	
	@XmlElement(name="entries")
	private Map<String, Object> variables; 
	
	
	public KieReleaseId getReleaseId() {
		return releaseId;
	}
	
	public void setReleaseId(KieReleaseId releaseId) {
		this.releaseId = releaseId;
	}
	
	public Map<String, Object> getVariables() {
		return variables;
	}
	
	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}
}
