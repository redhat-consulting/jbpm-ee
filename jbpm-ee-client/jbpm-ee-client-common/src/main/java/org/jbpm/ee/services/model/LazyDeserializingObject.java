package org.jbpm.ee.services.model;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.jbpm.ee.services.model.adapter.JaxbSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Jaxb, serializable map implementation.
 * Lazily deserializes values in order to support server side classloader setup prior to deserialization.
 * 
 * @author bradsdavis
 *
 */
public class LazyDeserializingObject implements LazyDeserializing<Serializable> {

	private static final Logger LOG = LoggerFactory.getLogger(LazyDeserializingObject.class);
	private String lazyObj;
	private String lazyObjType;
	
	private Serializable obj;
	
	public LazyDeserializingObject() throws Exception {

	}

	public LazyDeserializingObject(Serializable obj) throws Exception {
		this.obj = obj;
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		try {
			if(StringUtils.isNotBlank(lazyObj)) {
				out.writeUTF(lazyObjType);
				out.writeUTF(lazyObj);
			}
			else {
				String objectType = obj.getClass().getName();
				out.writeUTF(objectType);
				JaxbSerializer.writeExternal(obj, out);
			}
		} catch (Exception e) {
			throw new IOException("Exception marshalling map.", e);
		}
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.lazyObjType = in.readUTF();
		this.lazyObj = in.readUTF();
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("Setting up lazy value: "+lazyObjType);
		}
	}

	public void initializeLazy() throws IOException {
		LOG.debug("Lazily object: "+this.lazyObjType);
		try {
			Class clz = Class.forName(lazyObjType);
			this.obj = (Serializable)JaxbSerializer.unmarshall(clz, lazyObj);	
		}
		catch(Exception e) {
			throw new IOException("Exception reading class.", e);
		}
	}

	@Override
	public Serializable getDelegate() {
		return this.obj;
	}

}
