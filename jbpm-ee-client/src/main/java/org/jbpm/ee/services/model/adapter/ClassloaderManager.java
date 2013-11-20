package org.jbpm.ee.services.model.adapter;

/**
 * Holds reference to the classloader for the knowledge base.
 * 
 * @author bradsdavis
 *
 */
public class ClassloaderManager {

	private static final ThreadLocal<ClassLoader> context = new ThreadLocal<ClassLoader>();
	

	public static ClassLoader get() {
		return context.get();
	}
	
	public static void set(ClassLoader cl) {
		context.set(cl);
	}
	
	
}

