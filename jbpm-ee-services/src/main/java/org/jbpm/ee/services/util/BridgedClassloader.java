package org.jbpm.ee.services.util;

import org.slf4j.Logger;


public class BridgedClassloader extends ClassLoader {
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(BridgedClassloader.class);
	
	private final ClassLoader secondary;

	public BridgedClassloader(ClassLoader primary, ClassLoader secondary) { 
	    super(primary);
	    this.secondary = secondary;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		try {
			this.getParent().loadClass(name);
		}
		catch(ClassNotFoundException e) {
			LOG.info("Looking up class["+name+"] in secondary classloader.");
			return secondary.loadClass(name);
		}
		throw new ClassNotFoundException();
	}
	
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		LOG.info("Trying to load class: "+name);
		try {
			return this.getParent().loadClass(name);
		}
		catch(Exception e) {
			LOG.info("Looking up class["+name+"] in secondary classloader.");
			return secondary.loadClass(name);
		}
	}
}
