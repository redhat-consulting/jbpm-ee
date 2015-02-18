package org.jbpm.ee.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.ee.services.support.KieReleaseIdXProcessInstanceListener;
import org.jbpm.runtime.manager.impl.DefaultRegisterableItemsFactory;
import org.kie.api.event.process.ProcessEventListener;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.WorkItemHandler;

/***
 * Registers the {@link KieReleaseIdXProcessInstanceListener} and the {@link WorkItemHandler}s that are 
 * configured by the {@link KieContainerEE} within the WorkDefinitions.wid file for each KJar.
 * @author bradsdavis
 *
 */
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
	
	/* 
	 * Workaround for eager rules not firing correctly. https://bugzilla.redhat.com/show_bug.cgi?id=1075745
	 */
	@Override
    public List<AgendaEventListener> getAgendaEventListeners(
                    RuntimeEngine runtime) {

		return new ArrayList<AgendaEventListener>();
    }
}
