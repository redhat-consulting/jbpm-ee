package org.jbpm.ee.services.model;

import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkItem;

/**
 * Factory methods for ensuring that the implementation type is JAX friendly.
 * 
 * @author bradsdavis
 *
 */
public class ProcessInstanceFactory {

	private ProcessInstanceFactory() {
		// seal
	}
	
	public static ProcessInstance convert(ProcessInstance instance) {
		if(instance == null) {
			return null;
		}
		return new org.jbpm.ee.services.model.process.ProcessInstance(instance);
	}
	
	public static WorkItem convert(WorkItem instance) {
		if(instance == null) {
			return null;
		}
		return new org.jbpm.ee.services.model.process.WorkItem(instance);
	}
}
