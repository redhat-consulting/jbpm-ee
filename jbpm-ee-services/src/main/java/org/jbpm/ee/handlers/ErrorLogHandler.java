package org.jbpm.ee.handlers;

import java.util.Map;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorLogHandler implements WorkItemHandler {

	private static final Logger LOG = LoggerFactory.getLogger(ErrorLogHandler.class);
	
	// In some instances the WIH will need to know the KieSession
	public ErrorLogHandler(KieSession kieSession) {}
	
	// In some instances, not
	public ErrorLogHandler() {}
	
	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub
		Map<String, Object> parameterMap = workItem.getParameters();
		LOG.info(parameterMap.toString());
		manager.completeWorkItem(workItem.getId(), null);
	}

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub
	}

}
