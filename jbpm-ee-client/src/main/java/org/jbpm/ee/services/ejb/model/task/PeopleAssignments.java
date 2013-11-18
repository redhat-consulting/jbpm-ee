package org.jbpm.ee.services.ejb.model.task;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.jbpm.ee.services.ejb.model.TaskFactory;
import org.jbpm.ee.services.ejb.model.adapter.Initializable;
import org.jbpm.ee.services.ejb.model.adapter.JaxbSerializer;
import org.kie.api.task.model.OrganizationalEntity;
import org.kie.api.task.model.User;


@XmlRootElement(name="people-assignments")
@XmlAccessorType(XmlAccessType.FIELD)
public class PeopleAssignments implements Initializable<org.kie.api.task.model.PeopleAssignments>, org.kie.api.task.model.PeopleAssignments, Serializable  {

	@XmlElement(type=org.jbpm.ee.services.ejb.model.task.User.class)
	private User taskInitiator;
	
	@XmlElementWrapper(name="potential-owners")
	@XmlElement(type=org.jbpm.ee.services.ejb.model.task.OrganizationalEntity.class)
	private List<OrganizationalEntity> potentialOwners;
	
	@XmlElementWrapper(name="business-administrators")
	@XmlElement(type=org.jbpm.ee.services.ejb.model.task.OrganizationalEntity.class)
	private List<OrganizationalEntity> businessAdministrators;

	public PeopleAssignments() {
		// default
	}
	
	public PeopleAssignments(org.kie.api.task.model.PeopleAssignments assignment) {
		initialize(assignment);
	}
	
	public void setTaskInitiator(User taskInitiator) {
		this.taskInitiator = taskInitiator;
	}

	public void setPotentialOwners(List<OrganizationalEntity> potentialOwners) {
		this.potentialOwners = potentialOwners;
	}

	public void setBusinessAdministrators(
			List<OrganizationalEntity> businessAdministrators) {
		this.businessAdministrators = businessAdministrators;
	}

	@Override
	public User getTaskInitiator() {
		return this.taskInitiator;
	}

	@Override
	public List<OrganizationalEntity> getPotentialOwners() {
		return this.potentialOwners;
	}

	@Override
	public List<OrganizationalEntity> getBusinessAdministrators() {
		return this.businessAdministrators;
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
	public void initialize(org.kie.api.task.model.PeopleAssignments assignment) {
		this.taskInitiator = TaskFactory.convert(assignment.getTaskInitiator());
		
		this.potentialOwners = TaskFactory.convertOrganizationalEntityList(assignment.getPotentialOwners());
		this.businessAdministrators = TaskFactory.convertOrganizationalEntityList(assignment.getBusinessAdministrators());		
	}

}
