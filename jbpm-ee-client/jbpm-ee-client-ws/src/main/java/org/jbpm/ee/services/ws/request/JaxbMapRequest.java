package org.jbpm.ee.services.ws.request;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/***
 * Wrapper for JAXB Map implementation.
 * 
 * @author bradsdavis
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="map-element")
public class JaxbMapRequest {

	@XmlElement(name="entries")
	@XmlJavaTypeAdapter(org.jbpm.ee.services.model.adapter.StringObjectMapXmlAdapter.class)
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
