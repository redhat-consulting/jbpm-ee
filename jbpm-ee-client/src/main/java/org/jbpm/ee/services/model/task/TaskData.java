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
import org.kie.api.task.model.Attachment;
import org.kie.api.task.model.Comment;
import org.kie.api.task.model.Status;
import org.kie.api.task.model.User;


@XmlRootElement(name="task-data")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskData implements Initializable<org.kie.api.task.model.TaskData>, org.kie.api.task.model.TaskData, Serializable {

	public TaskData() {
		// default
	}
	
	@Override
	public void initialize(org.kie.api.task.model.TaskData data) {
		this.status = data.getStatus();
		this.previousStatus = data.getPreviousStatus();
		this.createdOn = data.getCreatedOn();
		this.activationTime = data.getActivationTime();
		this.expirationTime = data.getExpirationTime();
		this.skipable = data.isSkipable();
		this.workItemId = data.getWorkItemId();
		
		this.processInstanceId = data.getProcessInstanceId();
		this.workItemId = data.getWorkItemId();
		this.processInstanceId = data.getProcessInstanceId();
		this.documentType = data.getDocumentType();
		this.documentContentId = data.getDocumentContentId();
		this.outputType = data.getOutputType();
		this.outputContentId = data.getOutputContentId();
		this.faultName = data.getFaultName();
		this.faultType = data.getFaultType();
		this.faultContentId = data.getFaultContentId();
		this.parentId = data.getParentId();
		this.processId = data.getProcessId();
		this.processSessionId = data.getProcessSessionId();
		this.deploymentId = data.getDeploymentId();
		
		this.actualOwner = TaskFactory.convert(data.getActualOwner());
		this.createdBy = TaskFactory.convert(data.getCreatedBy());
		this.comments = TaskFactory.convertComments(data.getComments());
		this.attachments = TaskFactory.convertAttachments(data.getAttachments());
	}
	
	public TaskData(org.kie.api.task.model.TaskData data) {
		initialize(data);
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}

	public void setPreviousStatus(Status previousStatus) {
		this.previousStatus = previousStatus;
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

	public void setSkipable(Boolean skipable) {
		this.skipable = skipable;
	}

	public void setWorkItemId(Long workItemId) {
		this.workItemId = workItemId;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public void setDocumentContentId(Long documentContentId) {
		this.documentContentId = documentContentId;
	}

	public void setOutputType(String outputType) {
		this.outputType = outputType;
	}

	public void setOutputContentId(Long outputContentId) {
		this.outputContentId = outputContentId;
	}

	public void setFaultName(String faultName) {
		this.faultName = faultName;
	}

	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}

	public void setFaultContentId(Long faultContentId) {
		this.faultContentId = faultContentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public void setProcessSessionId(Integer processSessionId) {
		this.processSessionId = processSessionId;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}



	@XmlElement
    private Status status;

    @XmlElement(name = "previous-status")
    private Status previousStatus;

    @XmlElement(name = "actual-owner", type=org.jbpm.ee.services.model.task.User.class)
    private User actualOwner;

    @XmlElement(name = "created-by", type=org.jbpm.ee.services.model.task.User.class)
    private User createdBy;

    @XmlElement(name = "created-on")
    private Date createdOn;

    @XmlElement(name = "activation-time")
    private Date activationTime;

    @XmlElement(name = "expiration-time")
    private Date expirationTime;

    @XmlElement
    private Boolean skipable;

    @XmlElement(name = "work-item-id")
    private Long workItemId;

    @XmlElement(name = "process-instance-id")
    private Long processInstanceId;

    @XmlElement(name = "document-type")
    private String documentType;

    @XmlElement(name = "document-content-id")
    private Long documentContentId;

    @XmlElement(name = "output-type")
    private String outputType;

    @XmlElement(name = "output-content-id")
    private Long outputContentId;

    @XmlElement(name = "fault-name")
    private String faultName;

    @XmlElement(name = "fault-type")
    private String faultType;

    @XmlElement(name = "fault-content-id")
    private Long faultContentId;

    @XmlElement(name = "parent-id")
    private Long parentId;

    @XmlElement(name = "process-id")
    private String processId;

    @XmlElement(name = "process-session-id")
    private Integer processSessionId;

    @XmlElement(name = "comment", type=TaskComment.class)
    private List<Comment> comments;

    @XmlElement(name = "attachment", type=TaskAttachment.class)
    private List<Attachment> attachments;
    
    @XmlElement(name = "deployment-id")
    private String deploymentId;
	
	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public Status getPreviousStatus() {
		return previousStatus;
	}

	@Override
	public User getActualOwner() {
		return actualOwner;
	}

	@Override
	public User getCreatedBy() {
		return createdBy;
	}

	@Override
	public Date getCreatedOn() {
		return createdOn;
	}

	@Override
	public Date getActivationTime() {
		return activationTime;
	}

	@Override
	public Date getExpirationTime() {
		return expirationTime;
	}

	@Override
	public boolean isSkipable() {
		return skipable;
	}

	@Override
	public long getWorkItemId() {
		return workItemId;
	}

	@Override
	public long getProcessInstanceId() {
		return processInstanceId;
	}

	@Override
	public String getProcessId() {
		return processId;
	}

	@Override
	public String getDeploymentId() {
		return deploymentId;
	}

	@Override
	public int getProcessSessionId() {
		return processSessionId;
	}

	@Override
	public String getDocumentType() {
		return documentType;
	}

	@Override
	public long getDocumentContentId() {
		return documentContentId;
	}

	@Override
	public String getOutputType() {
		return outputType;
	}

	@Override
	public long getOutputContentId() {
		return outputContentId;
	}

	@Override
	public String getFaultName() {
		return faultName;
	}

	@Override
	public String getFaultType() {
		return faultType;
	}

	@Override
	public long getFaultContentId() {
		return faultContentId;
	}

	@Override
	public List<Comment> getComments() {
		return comments;
	}

	@Override
	public List<Attachment> getAttachments() {
		return attachments;
	}

	@Override
	public long getParentId() {
		return parentId;
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
