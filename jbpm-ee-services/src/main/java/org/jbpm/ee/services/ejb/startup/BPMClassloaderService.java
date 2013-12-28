package org.jbpm.ee.services.ejb.startup;

import java.io.Closeable;
import java.io.IOException;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.jbpm.ee.services.model.KieReleaseId;
import org.jbpm.ee.services.model.adapter.ClassloaderManager;
import org.jbpm.ee.services.util.BridgedClassloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Startup
@Singleton
public class BPMClassloaderService {
	
	private static final Logger LOG = LoggerFactory.getLogger(BPMClassloaderService.class);

	@Inject
	private RuntimeManagerBean runtimeManager;
	
	@Inject
	private KnowledgeManagerBean knowledgeManager;
	
	public void bridgeClassloaderByProcessInstanceId(Long processInstanceId) {
		LOG.debug("Bridging by process instance ID: "+processInstanceId);
		
		KieReleaseId kid = knowledgeManager.getReleaseIdByProcessId(processInstanceId);
		bridgeClassloaderByReleaseId(kid);
	}
		
	public void bridgeClassloaderByTaskId(Long taskInstanceId) {
		LOG.debug("Bridging by task ID: "+taskInstanceId);
		
		KieReleaseId kid = knowledgeManager.getReleaseIdByTaskId(taskInstanceId);
		bridgeClassloaderByReleaseId(kid);
	}
	
	public void bridgeClassloaderByWorkItemId(Long workItemId) {
		LOG.debug("Bridging by work item ID: "+workItemId);
		
		KieReleaseId kid = knowledgeManager.getReleaseIdByWorkItemId(workItemId);
		bridgeClassloaderByReleaseId(kid);
	}
	
	public void bridgeClassloaderByReleaseId(KieReleaseId releaseId) {
		LOG.debug("Bridging by release id: " + releaseId);
		
		ClassLoader appLoader = Thread.currentThread().getContextClassLoader();
		if (releaseId != null) {
			ClassLoader bpmClassloader = runtimeManager.getKieContainer(releaseId).getClassLoader();
			BridgedClassloader bridged = new BridgedClassloader(appLoader,bpmClassloader);
			ClassloaderManager.set(bridged);
			LOG.debug("Set thread classloader: " + bridged);
		} else {
			ClassloaderManager.set(appLoader);
			LOG.debug("Set thread classloader: " + appLoader);
		}
		
	}

	public static void closeQuietly(Closeable c) {
		try {
            if (c != null) {
                c.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
	}
}
