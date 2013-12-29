package org.jbpm.ee.services.model;

import java.io.Externalizable;
import java.io.IOException;

public interface LazyDeserializing<T> extends Externalizable {
	
	/**
	 * Method to trigger deserialization; the object remains serialized until this method is called.
	 * 
	 * @throws IOException
	 */
	public void initializeLazy() throws IOException;
	public T getDelegate();
}
