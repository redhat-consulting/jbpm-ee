package org.jbpm.ee.services.model.task;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jbpm.ee.services.model.adapter.Initializable;
import org.jbpm.ee.services.model.adapter.JaxbSerializer;
import org.kie.api.task.model.Comment;
import org.kie.api.task.model.User;

/**
 * Task Comment implementation that supports JAXB.
 * @see Comment
 * 
 * @author bradsdavis
 *
 */
@XmlRootElement(name="comment")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskComment implements Initializable<Comment>, Comment, Serializable {

    @XmlElement
    private Long id;

    @XmlElement
    private String text;
    
    @XmlElement(name="added-by", type=org.jbpm.ee.services.model.task.User.class)
    private User addedBy;
    
    @XmlElement(name="added-at")
    private Date addedAt;
    
    public TaskComment() {
		// default
	}
    
    @Override
    public void initialize(Comment comment) {
    	this.id = comment.getId();
    	this.text = comment.getText();
    	this.addedBy = comment.getAddedBy();
    	this.addedAt = comment.getAddedAt();
    }
    
    public TaskComment(Comment comment) {
    	initialize(comment);
    }
    
	public void setId(Long id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setAddedBy(User addedBy) {
		this.addedBy = addedBy;
	}

	public void setAddedAt(Date addedAt) {
		this.addedAt = addedAt;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public String getText() {
		return this.text;
	}

	@Override
	public Date getAddedAt() {
		return this.addedAt;
	}

	@Override
	public User getAddedBy() {
		return this.addedBy;
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
