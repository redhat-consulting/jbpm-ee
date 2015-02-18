package org.jbpm.ee.services.model.adapter;

/**
 * Interface for mapping to an existing object.
 * 
 * @author bradsdavis
 *
 * @param <T>
 */
public interface Initializable<T> {
	public abstract void initialize(T thisObj);
}
