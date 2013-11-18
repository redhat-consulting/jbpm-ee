package org.jbpm.ee.services.ejb.model.adapter;


public interface Initializable<T> {
	public abstract void initialize(T thisObj);
}
