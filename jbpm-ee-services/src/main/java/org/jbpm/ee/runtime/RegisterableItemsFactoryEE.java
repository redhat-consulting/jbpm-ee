package org.jbpm.ee.runtime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.ee.services.support.KieReleaseIdXProcessInstanceListener;
import org.jbpm.runtime.manager.impl.DefaultRegisterableItemsFactory;
import org.kie.api.event.process.ProcessEventListener;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.WorkItemHandler;

public class RegisterableItemsFactoryEE extends DefaultRegisterableItemsFactory {

	protected KieContainerEE container;
	
	public RegisterableItemsFactoryEE(KieContainerEE ee) {
		container = ee;
	}
	
	@Override
	public Map<String, WorkItemHandler> getWorkItemHandlers(RuntimeEngine runtime) {
		Map<String, WorkItemHandler> handlers = new HashMap<String, WorkItemHandler>();
		handlers.putAll(super.getWorkItemHandlers(runtime));
		handlers.putAll(container.getWorkItemHandlers());

		return handlers;
	}
	
	@Override
	public List<ProcessEventListener> getProcessEventListeners(RuntimeEngine runtime) {
		List<ProcessEventListener> listeners = super.getProcessEventListeners(runtime);
		listeners.add(new KieReleaseIdXProcessInstanceListener(container.getReleaseId()));
		
		return listeners;
	}
	
	
}
