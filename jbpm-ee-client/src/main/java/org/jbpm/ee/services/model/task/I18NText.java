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

@XmlRootElement(name="text")
@XmlAccessorType(XmlAccessType.FIELD)
public class I18NText implements Initializable<org.kie.api.task.model.I18NText>, org.kie.api.task.model.I18NText, Serializable {

	@XmlElement
	private Long id;
	
	@XmlElement
	private String language;
	
	@XmlElement
	private String text;
	
	public I18NText() {
		// default
	}
	
	public I18NText(org.kie.api.task.model.I18NText text) {
		initialize(text);
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public String getLanguage() {
		return this.language;
	}

	@Override
	public String getText() {
		return this.text;
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
	public void initialize(org.kie.api.task.model.I18NText text) {
		this.id = text.getId();
		this.language = text.getLanguage();
		this.text = text.getText();
	}

}
