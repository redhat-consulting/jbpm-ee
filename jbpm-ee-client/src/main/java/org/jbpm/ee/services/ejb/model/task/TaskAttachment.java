package org.jbpm.ee.services.ejb.model.task;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jbpm.ee.services.ejb.model.TaskFactory;
import org.jbpm.ee.services.ejb.model.adapter.Initializable;
import org.jbpm.ee.services.ejb.model.adapter.JaxbSerializer;
import org.kie.api.task.model.Attachment;
import org.kie.api.task.model.User;

@XmlRootElement(name="attachment")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskAttachment implements Initializable<Attachment>, Attachment, Serializable {

    @XmlElement
    private Long id;

    @XmlElement
    private String name;

    @XmlElement
    private String contentType;

    @XmlElement(name="attached-at")
    private Date attachedAt;

    @XmlElement(name="attached-by", type=org.jbpm.ee.services.ejb.model.task.User.class)
    private User attachedBy;

    @XmlElement
    private Integer size;

    @XmlElement(name="attachment-content-id")
    private Long attachmentContentId;

    public TaskAttachment() {
		// default
	}
    
    @Override
    public void initialize(Attachment attachment) {
    	this.id = attachment.getId();
    	this.name = attachment.getName();
    	this.contentType = attachment.getContentType();
    	this.attachedAt = attachment.getAttachedAt();
    	this.attachedBy = TaskFactory.convert(attachment.getAttachedBy());
    	this.size = attachment.getSize();
    	this.attachmentContentId = attachment.getAttachmentContentId();
    }
    
    public TaskAttachment(Attachment attachment) {
    	initialize(attachment);
    }
    
    public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setAttachedAt(Date attachedAt) {
		this.attachedAt = attachedAt;
	}

	public void setAttachedBy(User attachedBy) {
		this.attachedBy = attachedBy;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public void setAttachmentContentId(Long attachmentContentId) {
		this.attachmentContentId = attachmentContentId;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getContentType() {
		return this.contentType;
	}

	@Override
	public Date getAttachedAt() {
		return this.attachedAt;
	}

	@Override
	public User getAttachedBy() {
		return this.attachedBy;
	}

	@Override
	public int getSize() {
		return this.size;
	}

	@Override
	public long getAttachmentContentId() {
		return this.attachmentContentId;
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
