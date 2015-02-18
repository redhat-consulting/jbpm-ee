package org.jbpm.ee.services.model.task;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * OrganizationEntity implementation that supports JAXB.
 * @see org.kie.api.task.model.OrganizationalEntity
 * 
 * @author bradsdavis
 *
 */
@XmlRootElement(name="organizational-entity")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({User.class, Group.class})
public abstract class OrganizationalEntity implements org.kie.api.task.model.OrganizationalEntity, Serializable  {

	@XmlElement
	private String id;

	public OrganizationalEntity() {
		// default
	}
	
	public OrganizationalEntity(org.kie.api.task.model.OrganizationalEntity entity) {
		this.id = entity.getId();
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return this.id;
	}

}
