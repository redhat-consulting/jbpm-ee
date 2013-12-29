package org.jbpm.ee.services.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="element")
public class JaxbObject {

    @XmlAttribute(name="class-name")
    private String className;

    @XmlValue
    @XmlSchemaType(name = "base64Binary")
    private byte[] value;
    

    public String getClassName() {
		return className;
	}
    
    public void setClassName(String className) {
		this.className = className;
	}
    
    public byte[] getValue() {
		return value;
	}
    
    public void setValue(byte[] value) {
		this.value = value;
	}
}
