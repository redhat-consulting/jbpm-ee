package org.jbpm.ee.services.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;
import java.util.Map;

import org.jbpm.ee.services.model.adapter.JaxbSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Jaxb, serializable map implementation.
 * Lazily deserializes values in order to support inte
 * 
 * @author bradsdavis
 *
 */
public class LazyDeserializingMap extends HashMap<String, Object> implements Externalizable {

	private static final Logger LOG = LoggerFactory.getLogger(LazyDeserializingMap.class);
	private String lazyMap;
	
	public LazyDeserializingMap() throws Exception {

	}

	public LazyDeserializingMap(Map<String, Object> initial) throws Exception {
		this.putAll(initial);
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		try {
			JaxbSerializer.writeExternal(new JaxbMapRequest(this), out);
		} catch (Exception e) {
			throw new IOException("Exception marshalling map.", e);
		}
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.lazyMap = in.readUTF();
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("Setting up lazy value: "+lazyMap);
		}
	}

	public void initializeLazy() throws IOException {
		JaxbMapRequest map = JaxbSerializer.unmarshall(JaxbMapRequest.class, lazyMap);
		this.putAll(map.getMap());
		LOG.debug("Lazily initializing map: "+this.size());
	}

}
