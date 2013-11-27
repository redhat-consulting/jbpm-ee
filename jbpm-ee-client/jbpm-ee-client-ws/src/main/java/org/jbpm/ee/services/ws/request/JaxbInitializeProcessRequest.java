package org.jbpm.ee.services.ws.request;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jbpm.ee.services.model.KieReleaseId;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbInitializeProcessRequest {
	
	@XmlElement(name="kie-release-id")
	private KieReleaseId releaseId;
	
	@XmlElement(name="entries")
	@XmlJavaTypeAdapter(org.jbpm.ee.services.model.adapter.StringObjectMapXmlAdapter.class)
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
