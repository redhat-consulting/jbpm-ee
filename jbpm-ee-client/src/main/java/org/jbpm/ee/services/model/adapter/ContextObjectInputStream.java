package org.jbpm.ee.services.model.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * Implementation of an object input stream that delegates to a given classloader.
 * 
 * @author bradsdavis
 *
 */
public class ContextObjectInputStream extends ObjectInputStream {

	private ClassLoader classloader = null;
	
	public ContextObjectInputStream(ClassLoader classloader) throws IOException, SecurityException {
		this.classloader = classloader;
	}
	
	public ContextObjectInputStream(ClassLoader classloader, InputStream in) throws IOException {
		super(in);
		this.classloader = classloader;
	}
	
	@Override
	protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        String name = desc.getName();
        try {
            return Class.forName(name, false, classloader);
        } catch (ClassNotFoundException ex) {
            super.resolveClass(desc);
        }
        
        return null;
    }

}
