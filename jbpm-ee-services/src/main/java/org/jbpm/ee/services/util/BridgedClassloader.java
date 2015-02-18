package org.jbpm.ee.services.util;

import org.apache.commons.discovery.resource.ClassLoaders;
import org.slf4j.Logger;


public class BridgedClassloader extends ClassLoader {
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(BridgedClassloader.class);
	
	private final ClassLoader secondary;
	private final ClassLoaders classloaders;

	public BridgedClassloader(ClassLoader primary, ClassLoader secondary) {
	    super(primary);
	    this.secondary = secondary;
	    this.classloaders = new ClassLoaders();
	    this.classloaders.put(primary);
	    this.classloaders.put(secondary);
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		try {
			return this.getParent().loadClass(name);
		}
		catch(ClassNotFoundException e) {
			LOG.trace("Looking up class["+name+"] in secondary classloader.");
			return secondary.loadClass(name);
		}
		throw new ClassNotFoundException(name);
	}
	
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		LOG.trace("Trying to load class: "+name);
		try {
			return this.getParent().loadClass(name);
		}
		catch(ClassNotFoundException e) {
			LOG.trace("Looking up class["+name+"] in secondary classloader.");
			return secondary.loadClass(name);
		}
	}
	
	@Override
	public String toString() {
		return "BridgedClassloader [parent="+getParent()+"], secondary=" + secondary + "]";
	}
	
	
}
