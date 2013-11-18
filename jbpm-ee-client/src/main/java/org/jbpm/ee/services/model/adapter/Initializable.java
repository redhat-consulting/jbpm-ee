package org.jbpm.ee.services.model.adapter;


public interface Initializable<T> {
	public abstract void initialize(T thisObj);
}
