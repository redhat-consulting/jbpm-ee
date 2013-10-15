package org.jbpm.ee.service.remote;

import java.util.Map;

public interface WorkItemManagerRemote {
	public void completeWorkItem(long id, Map<String, Object> results);
	public void abortWorkItem(long id);
}
