#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.LoggerFactory;

public class TestHandler implements WorkItemHandler {

	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(TestHandler.class);
	
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		LOG.info("Completing work item.");
		manager.completeWorkItem(workItem.getId(), null);
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		manager.abortWorkItem(workItem.getId());
	}

}
