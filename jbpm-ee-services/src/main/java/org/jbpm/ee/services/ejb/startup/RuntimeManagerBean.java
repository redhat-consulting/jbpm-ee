package org.jbpm.ee.services.ejb.startup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.drools.compiler.kproject.ReleaseIdImpl;
import org.hibernate.SessionException;
import org.jbpm.ee.config.Configuration;
import org.jbpm.ee.persistence.KieBaseXProcessInstanceDao;
import org.jbpm.ee.runtime.KieContainerEE;
import org.jbpm.ee.runtime.RegisterableItemsFactoryEE;
import org.jbpm.ee.services.model.KieReleaseId;
import org.jbpm.runtime.manager.impl.AbstractRuntimeManager;
import org.jbpm.runtime.manager.impl.RuntimeEnvironmentBuilder;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.internal.task.api.UserGroupCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates and maintains a cache of {@link RuntimeManager}s per {@link KieReleaseId}s
**/
@Startup
@Singleton
public class RuntimeManagerBean {
	private static final Logger LOG = LoggerFactory.getLogger(RuntimeManagerBean.class);
	
	protected Map<KieReleaseId, KieContainerEE> containerCache;
	protected Map<KieReleaseId, KieScanner> scannerCache;
	protected Map<KieReleaseId, RuntimeManager> runtimeManagerCache;
	
	@Inject
	protected KieBaseXProcessInstanceDao kieBaseXProcessInstanceDao;
	
	protected KieServices kieServices;
	
	@Inject
	@Configuration(value="scannerPollFrequency")
	protected Long scannerPollFrequency;
	
	
	@Inject
	protected EntityManagerFactory emf;
	
	@Inject
	protected EntityManager em;
	
	@Inject
	protected UserGroupCallback userGroupCallback;
	
	
	@PostConstruct
	private void setup() {
		kieServices = KieServices.Factory.get();
		
		containerCache = new ConcurrentHashMap<KieReleaseId, KieContainerEE>();
		scannerCache = new ConcurrentHashMap<KieReleaseId, KieScanner>();
		runtimeManagerCache = new ConcurrentHashMap<KieReleaseId, RuntimeManager>();

		//instantiate existing managers.
		for(KieReleaseId release : kieBaseXProcessInstanceDao.queryActiveKieReleases()) {
			LOG.info("Setup: Rehydrating runtime manager for: "+release);
			getRuntimeManager(release);
		}
	}

	@PreDestroy
	private void destroy() {
		LOG.info("Stopping jBPM Resource Change Listeners.");
		
		for(KieScanner scanner : scannerCache.values()) {
			scanner.stop();
		}
		
		//set for dispose.
		this.scannerCache = null;
	}
	
	@Schedule(minute = "*/15", hour = "*", persistent = false)
	@Lock(LockType.WRITE)
	private void hydrateRuntimeManagerCache() {
		LOG.info("Running scheduled hydrate.");
		
		//instantiate existing managers.
		for(KieReleaseId release : kieBaseXProcessInstanceDao.queryActiveKieReleases()) {
			if(!runtimeManagerCache.containsKey(release)) {
				LOG.info("Cache: Rehydrating runtime manager for: "+release);
				getRuntimeManager(release);
			}
		}
		
	}
	
	/**
	 * Loads a kjar via the given Release Id (maven deployment information)
	 * Additionally, sets up scanning to monitor for kjar changes
	 * 
	 * @param resourceKey The maven deployment information for the kjar
	 * @return The in-memory loaded kjar
	 */
	@Lock(LockType.READ)
	public KieContainerEE getKieContainer(KieReleaseId resourceKey) {
		
		if (!isReleaseIdValid(resourceKey)) {
			throw new IllegalArgumentException("ReleaseId invalid: " + resourceKey);
		}
		
		if(!containerCache.containsKey(resourceKey)) {
			//create a new container.
			
			ReleaseIdImpl releaseID = new ReleaseIdImpl(resourceKey.getGroupId(), resourceKey.getArtifactId(), resourceKey.getVersion());
			KieContainerEE kieContainer = new KieContainerEE(kieServices.newKieContainer(releaseID));
			KieScanner kieScanner = kieServices.newKieScanner(kieContainer);
			kieScanner.start(scannerPollFrequency);
			
			//register the new container and scanner.
			this.containerCache.put(resourceKey, kieContainer);
			this.scannerCache.put(resourceKey, kieScanner);
		}
		return this.containerCache.get(resourceKey);
	}
	
 	/**
	 * Returns the kjar classloader for the given kjar deployment information
	 * 
	 * @param resourceKey The maven deployment information for the kjar
	 * @return
	 */
	@Lock(LockType.READ)
	protected ClassLoader getClasssloader(KieReleaseId releaseId) {
		return this.getKieContainer(releaseId).getClassLoader();
	}
	

	/**
	 * Returns the kjar resources for the given kjar deployment information
	 * 
	 * @param resourceKey The maven deployment information for the kjar
	 * @return
	 */
	@Lock(LockType.READ)
	protected KieBase getKieBase(KieReleaseId resourceKey) {
		return this.getKieContainer(resourceKey).getKieBase();
	}	
	
	

	
	/**
	 * Creates/returns the RuntimeManager for the specified kjar
	 * 
	 * @param releaseId  The maven deployment information for the kjar
	 * @return
	 * @throws SessionException
	 */
	@Lock(LockType.READ)
	public RuntimeManager getRuntimeManager(KieReleaseId releaseId) {
		if(!runtimeManagerCache.containsKey(releaseId)) {
			RuntimeEnvironment re = createEnvironment(releaseId);
			RuntimeManager runtimeManager = RuntimeManagerFactory.Factory.get().newPerProcessInstanceRuntimeManager(re, releaseId.toString());
			runtimeManagerCache.put(releaseId, runtimeManager);
		}
		
		return runtimeManagerCache.get(releaseId);
	}

	public RuntimeEnvironment getRuntimeEnvironment(KieReleaseId releaseId) {
		return ((AbstractRuntimeManager)(getRuntimeManager(releaseId))).getEnvironment();
	}
	
	/**
	 * Creates the RuntimeEnvironment for the RuntimeManager to use
	 * 
	 * @param releaseId  The maven deployment information for the kjar
	 * @return
	 */
	@Lock(LockType.READ)
	protected RuntimeEnvironment createEnvironment(KieReleaseId releaseId) {
		KieContainerEE container = getKieContainer(releaseId);
		
		RuntimeEnvironment re = RuntimeEnvironmentBuilder.Factory.get().newDefaultBuilder()
				.entityManagerFactory(emf)
				.userGroupCallback(userGroupCallback)
				.knowledgeBase(getKieBase(releaseId))
				.persistence(true)
				.classLoader(getClasssloader(releaseId))
				.registerableItemsFactory(new RegisterableItemsFactoryEE(container))
				.get();
		return re;
	}
	
	private static boolean isReleaseIdValid(KieReleaseId releaseId) {
		if ((releaseId != null) &&
				(releaseId.getGroupId() != null) &&
				(releaseId.getArtifactId() != null) &&
				(releaseId.getVersion() != null)) {
			return true;
		} else {
			return false;
		}
	}

}
