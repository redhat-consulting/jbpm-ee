package org.jbpm.ee.runtime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.ee.services.util.BridgedClassloader;
import org.jbpm.ee.services.util.WorkItemDefinitionUtil;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.Results;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.process.WorkItemHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * Extends the base Kie Container to ensure that when the KieContainer is loaded, all WorkDefinitions provides for the 
 * Release ID are also loaded into memory.  This allows jBPM-EE to instantiate the WorkItemHandlers configured in the WorkDefinitions.wid
 * when the process instance is instantiated or rehydrated.
 * 
 * @author bradsdavis
 *
 */
public class KieContainerEE implements org.kie.api.runtime.KieContainer {

	private static final Logger LOG = LoggerFactory.getLogger(KieContainerEE.class);
	
	private final org.kie.api.runtime.KieContainer delegate;
	private final Map<String, WorkItemHandler> workItemHandlers = new HashMap<String, WorkItemHandler>();
	
	public KieContainerEE(org.kie.api.runtime.KieContainer delegate) {
		this.delegate = delegate;
		
		refreshWorkItemDefinitionCache();
	}

	@Override
	public ReleaseId getReleaseId() {
		return delegate.getReleaseId();
	}

	@Override
	public Results verify() {
		return delegate.verify();
	}
	
	public Map<String, WorkItemHandler> getWorkItemHandlers() {
		return workItemHandlers;
	}

	@Override
	public Results updateToVersion(ReleaseId version) {
		Results results = delegate.updateToVersion(version);

		//refresh the workitemhandlers.
		refreshWorkItemDefinitionCache();
		return results;
	}

	protected void refreshWorkItemDefinitionCache() { 
		List<Map<String, Object>> definition = WorkItemDefinitionUtil.loadWorkItemDefinitions(getReleaseId(), "META-INF/WorkDefinitions.wid");
		if(definition == null || definition.size() == 0) {
			// also try this path, since Business Central in JBPM6 seems to put it here now by default
			definition = WorkItemDefinitionUtil.loadWorkItemDefinitions(getReleaseId(), "WorkDefinitions.wid");
			if (definition == null) {
				return;
			}
		}
		
		//otherwise, populate.
		LOG.info("Loading Kie WorkItemDefinitions: "+definition.size());
		LOG.info("Definitions: "+definition);
		this.workItemHandlers.clear();
		
		for(Map<String, Object> definitions : definition) {
			String handlerName = (String)definitions.get("name");
			String defaultHandlerName = (String)definitions.get("defaultHandler");

			LOG.info("Registering Handler: "+handlerName+" => "+defaultHandlerName);
			
			Class defaultHandler;
			try {
				defaultHandler = getClassLoader().loadClass(defaultHandlerName);
				WorkItemHandler instance = (WorkItemHandler)defaultHandler.newInstance();
				this.workItemHandlers.put(handlerName, instance);
			} catch (Exception e) {
				LOG.error("Handler ["+handlerName+"] unable to register class ["+defaultHandlerName+"]");
			}
		}
	}
	
	@Override
	public KieBase getKieBase() {
		return delegate.getKieBase();
	}

	@Override
	public KieBase getKieBase(String kBaseName) {
		return delegate.getKieBase(kBaseName);
	}

	@Override
	public KieBase newKieBase(KieBaseConfiguration conf) {
		return delegate.newKieBase(conf);
	}

	@Override
	public KieBase newKieBase(String kBaseName, KieBaseConfiguration conf) {
		return delegate.newKieBase(kBaseName, conf);
	}

	@Override
	public KieSession newKieSession() {
		return delegate.newKieSession();
	}

	@Override
	public KieSession newKieSession(KieSessionConfiguration conf) {
		return delegate.newKieSession(conf);
	}

	@Override
	public KieSession newKieSession(Environment environment) {
		return delegate.newKieSession(environment);
	}

	@Override
	public KieSession newKieSession(String kSessionName) {
		return delegate.newKieSession(kSessionName);
	}

	@Override
	public KieSession newKieSession(String kSessionName, Environment environment) {
		return delegate.newKieSession(kSessionName, environment);
	}

	@Override
	public KieSession newKieSession(String kSessionName, KieSessionConfiguration conf) {
		return delegate.newKieSession(kSessionName, conf);
	}

	@Override
	public KieSession newKieSession(String kSessionName, Environment environment, KieSessionConfiguration conf) {
		return delegate.newKieSession(kSessionName, environment, conf);
	}

	@Override
	public StatelessKieSession newStatelessKieSession() {
		return delegate.newStatelessKieSession();
	}

	@Override
	public StatelessKieSession newStatelessKieSession(KieSessionConfiguration conf) {
		return delegate.newStatelessKieSession(conf);
	}

	@Override
	public StatelessKieSession newStatelessKieSession(String kSessionName) {
		return delegate.newStatelessKieSession(kSessionName);
	}

	@Override
	public StatelessKieSession newStatelessKieSession(String kSessionName, KieSessionConfiguration conf) {
		return delegate.newStatelessKieSession(kSessionName, conf);
	}

	@Override
	public ClassLoader getClassLoader() {
		BridgedClassloader cl = new BridgedClassloader(delegate.getClassLoader(), this.getClass().getClassLoader());
		return cl;
	}

	@Override
	public KieSession newKieSession(Environment env, KieSessionConfiguration conf) {
		return delegate.newKieSession(env, conf);
	}
	
}
