package org.jbpm.ee.services.ejb.model.task;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jbpm.ee.services.ejb.model.adapter.Initializable;
import org.jbpm.ee.services.ejb.model.adapter.JaxbSerializer;


@XmlRootElement(name="user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Initializable<org.kie.api.task.model.User>, org.kie.api.task.model.User, Serializable {

	@XmlElement
	private String id;
	
	public User(org.kie.api.task.model.User user) {
		initialize(user);
	}
	
	public User() {
		// default
	}
	
	@Override
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		JaxbSerializer.writeExternal(this, out);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		JaxbSerializer.readExternal(this, in);
	}

	@Override
	public void initialize(org.kie.api.task.model.User user) {
		this.id = user.getId();
	}

}
