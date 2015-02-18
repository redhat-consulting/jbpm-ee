package org.jbpm.ee.services.model.process;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jbpm.services.task.impl.model.xml.adapter.StringObjectMapXmlAdapter;

/**
 * WorkItem implementation that supports JAXB.
 * @see org.kie.api.runtime.process.WorkItem
 * 
 * @author bradsdavis
 *
 */
@XmlRootElement(name="work-item")
@XmlAccessorType(XmlAccessType.FIELD)
public class WorkItem implements org.kie.api.runtime.process.WorkItem, Serializable {

    @XmlElement
    private Long id;
    
    @XmlElement
    private String name;
    
    @XmlElement
    private Integer state = 0;
    
    @XmlElement(name="param-map")
    @XmlJavaTypeAdapter(StringObjectMapXmlAdapter.class)
    private Map<String, Object> parameters = new HashMap<String, Object>();
    
    @XmlElement(name="results-map")
    @XmlJavaTypeAdapter(StringObjectMapXmlAdapter.class)
    private Map<String, Object> results = new HashMap<String, Object>();
    
    @XmlElement
    private Long processInstanceId;

    public WorkItem() {
		// default
	}
    
    public WorkItem(org.kie.api.runtime.process.WorkItem workItem) {
    	this.id = workItem.getId();
    	this.name = workItem.getName();
    	this.state = workItem.getState();
    	this.parameters = workItem.getParameters();
    	this.results = workItem.getResults();
    	this.processInstanceId = workItem.getProcessInstanceId();
    }
    
    

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public void setResults(Map<String, Object> results) {
		this.results = results;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getState() {
		return this.state;
	}

	@Override
	public Object getParameter(String name) {
		return this.parameters.get(name);
	}

	@Override
	public Map<String, Object> getParameters() {
		return this.parameters;
	}

	@Override
	public Object getResult(String name) {
		return this.results.get(name);
	}

	@Override
	public Map<String, Object> getResults() {
		return this.results;
	}

	@Override
	public long getProcessInstanceId() {
		return this.processInstanceId;
	}
}
