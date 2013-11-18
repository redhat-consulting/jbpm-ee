package org.jbpm.ee.services.model.task;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jbpm.ee.services.model.adapter.Initializable;
import org.jbpm.ee.services.model.adapter.JaxbSerializer;

@XmlRootElement(name="group")
@XmlAccessorType(XmlAccessType.FIELD)
public class Group extends OrganizationalEntity implements Initializable<org.kie.api.task.model.Group>, org.kie.api.task.model.Group, Serializable {

	@XmlElement
	private String id;

	public Group() {
		// default
	}
	
	public Group(org.kie.api.task.model.Group group) {
		initialize(group);
	}
	
	public void initialize(org.kie.api.task.model.Group group) {
		this.id = group.getId();
	}
	
	@Override
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

    public void writeExternal(ObjectOutput out) throws IOException {
    	JaxbSerializer.writeExternal(this, out);
    } 
    
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    	JaxbSerializer.readExternal(this, in);
    }    

}
