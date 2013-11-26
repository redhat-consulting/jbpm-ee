package org.jbpm.ee.services.model.task;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jbpm.ee.services.model.TaskFactory;
import org.jbpm.ee.services.model.adapter.Initializable;
import org.jbpm.ee.services.model.adapter.JaxbSerializer;
import org.kie.api.task.model.Status;
import org.kie.api.task.model.User;

/**
 * TaskSummary implementation that supports JAXB.
 * @see org.kie.api.task.model.TaskSummary
 * 
 * @author bradsdavis
 *
 */
@XmlRootElement(name="task-summary")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskSummary implements Initializable<org.kie.api.task.model.TaskSummary>, org.kie.api.task.model.TaskSummary, Serializable 
{
    @XmlElement
    private long id;
    
    @XmlElement
    private String name;
    
    @XmlElement
    private String subject;
    
    @XmlElement
    private String description;
    
    @XmlElement
    private Status status;
    
    @XmlElement
    private int priority;
    
    @XmlElement
    private boolean skipable;
    
    @XmlElement(name="actual-owner", type=org.jbpm.ee.services.model.task.User.class)
    private User actualOwner;
    
    @XmlElement(name="created-by", type=org.jbpm.ee.services.model.task.User.class)
    private User createdBy;
    
    @XmlElement(name="created-on")
    private Date createdOn;
    
    @XmlElement(name="activation-time")
    private Date activationTime;
    
    @XmlElement(name="expiration-time")
    private Date expirationTime;
    
    @XmlElement(name="process-instance-id")
    private long processInstanceId;
    
    @XmlElement(name="process-id")
    private String processId;
    
    @XmlElement(name="process-session-id")
    private int processSessionId;

    @XmlElement(name="potential-owner")
    private List<String> potentialOwners;

    public TaskSummary() {
    	//default
    }
    
	public TaskSummary(org.kie.api.task.model.TaskSummary summary) {
		initialize(summary);
	}
	
	public void initialize(org.kie.api.task.model.TaskSummary summary) {
		this.id = summary.getId();
		this.name = summary.getName();
		this.subject = summary.getSubject();
		this.description = summary.getDescription();
		this.status = summary.getStatus();
		this.priority = summary.getPriority();
		this.skipable = summary.isSkipable();
		this.createdOn = summary.getCreatedOn();
		this.activationTime = summary.getActivationTime();
		this.expirationTime = summary.getExpirationTime();
		this.processInstanceId = summary.getProcessInstanceId();
		this.processId = summary.getProcessId();
		this.potentialOwners = summary.getPotentialOwners();
		
		this.actualOwner = TaskFactory.convert(summary.getActualOwner());		
		this.createdBy = TaskFactory.convert(summary.getCreatedBy());
	}
    
	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setSkipable(boolean skipable) {
		this.skipable = skipable;
	}

	public void setActualOwner(User actualOwner) {
		this.actualOwner = actualOwner;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public void setActivationTime(Date activationTime) {
		this.activationTime = activationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	public void setProcessInstanceId(long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public void setProcessSessionId(int processSessionId) {
		this.processSessionId = processSessionId;
	}

	public void setPotentialOwners(List<String> potentialOwners) {
		this.potentialOwners = potentialOwners;
	}

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public long getProcessInstanceId() {
		return this.processInstanceId;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getSubject() {
		return this.subject;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public Status getStatus() {
		return this.status;
	}

	@Override
	public int getPriority() {
		return this.priority;
	}

	@Override
	public boolean isSkipable() {
		return this.skipable;
	}

	@Override
	public User getActualOwner() {
		return this.actualOwner;
	}

	@Override
	public User getCreatedBy() {
		return this.createdBy;
	}

	@Override
	public Date getCreatedOn() {
		return this.createdOn;
	}

	@Override
	public Date getActivationTime() {
		return this.activationTime;
	}

	@Override
	public Date getExpirationTime() {
		return this.expirationTime;
	}

	@Override
	public String getProcessId() {
		return this.processId;
	}

	@Override
	public int getProcessSessionId() {
		return this.processSessionId;
	}

	@Override
	public List<String> getPotentialOwners() {
		return this.potentialOwners;
	}

	

	@Override
	public String toString() {
		return "TaskSummary [id=" + id + ", name=" + name + ", subject="
				+ subject + ", description=" + description + ", status="
				+ status + ", priority=" + priority + ", skipable=" + skipable
				+ ", actualOwner=" + actualOwner + ", createdBy=" + createdBy
				+ ", createdOn=" + createdOn + ", activationTime="
				+ activationTime + ", expirationTime=" + expirationTime
				+ ", processInstanceId=" + processInstanceId + ", processId="
				+ processId + ", processSessionId=" + processSessionId
				+ ", potentialOwners=" + potentialOwners + "]";
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		JaxbSerializer.writeExternal(this, out);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		JaxbSerializer.readExternal(this, in);
	}
}
