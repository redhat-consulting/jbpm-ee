package org.jbpm.ee.services.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

import org.jbpm.ee.services.model.adapter.ClassloaderManager;

public class LazyDeserializingObjectInputStream extends ObjectInputStream {

	protected LazyDeserializingObjectInputStream(InputStream is) throws IOException, SecurityException {
		super(is);
	}
	
	@Override
	protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
		return ClassloaderManager.get().loadClass(desc.getName());
	}
	
	

}
