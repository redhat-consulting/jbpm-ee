package org.jbpm.ee.services.rest.request;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class JaxbMapRequest {

	private Map<String, Object> map;
	
	public JaxbMapRequest() {
	}
	
	public JaxbMapRequest(Map<String, Object> map) {
		this.map = map;
	}
	
	public Map<String, Object> getMap() {
		if(map == null) {
			return new HashMap<String, Object>();
		}
		return map;
	}
	
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
}
