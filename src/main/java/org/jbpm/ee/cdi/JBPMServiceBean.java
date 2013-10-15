package org.jbpm.ee.cdi;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.TransactionManager;

import org.jbpm.ee.exception.SessionException;
import org.jbpm.ee.startup.KnowledgeManagerBean;
import org.jbpm.ee.support.AwareStatefulKnowledgeSession;
import org.jbpm.ee.support.KieReleaseId;
import org.jbpm.services.task.HumanTaskServiceFactory;
import org.jbpm.shared.services.impl.JbpmJTATransactionManager;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.EnvironmentName;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessRuntime;
import org.kie.api.runtime.process.WorkItemManager;
import org.kie.api.task.TaskService;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.persistence.jpa.JPAKnowledgeService;
import org.kie.internal.task.api.UserGroupCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sets up the task service to use the local task service.
 * Handles the cleanup of the task service and knowledge session.
 *   
 * Creates a stateful knowledge session 
 * @author bradsdavis
 *
 */
@Stateless
public class JBPMServiceBean {
	private static final Logger LOG = LoggerFactory.getLogger(JBPMServiceBean.class);
	
	@PersistenceContext(name="org.jbpm.persistence.jpa", unitName="org.jbpm.persistence.jpa")
	EntityManager entityManagerMain;

	@Produces
	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerMain.getEntityManagerFactory();
	}
	
	@Inject
	private KnowledgeManagerBean knowledgeManagerManager;
	
	@Resource(mappedName="java:jboss/TransactionManager")
	private TransactionManager transactionManager;
	
	@Produces @TaskServiceConfig
	public TaskService getTaskService() {
		HumanTaskServiceFactory htsf = new HumanTaskServiceFactory();
		htsf.setEntityManagerFactory(entityManagerMain.getEntityManagerFactory());
		
		JbpmJTATransactionManager transactionManager = new JbpmJTATransactionManager();
		htsf.setJbpmServicesTransactionManager(transactionManager);
		
		return htsf.newTaskService();
	}
	
	@Produces
	public EntityManager getEntityManager() {
		return entityManagerMain;
	}

	@Produces
	public UserGroupCallback getUserGroupCallback() {
		return HumanTaskServiceFactory.createUserGroupCallback();
	}
	
	/**
	 * Sets up a new KnowledgeSession with the Human Task Handler defined.
	 * 
	 * @return
	 * @throws SessionException
	 */
	public KieSession getKnowledgeSession(KieReleaseId releaseId) throws SessionException {
		Environment environment = KnowledgeBaseFactory.newEnvironment();
		environment.set( EnvironmentName.ENTITY_MANAGER_FACTORY, entityManagerMain.getEntityManagerFactory());
		environment.set( EnvironmentName.TRANSACTION_MANAGER, transactionManager);

		//create a session configuration that delegates timers.
		KieSession knowledgeSession = JPAKnowledgeService.newStatefulKnowledgeSession( knowledgeManagerManager.getKieBase(releaseId), null, environment);
		return new AwareStatefulKnowledgeSession(knowledgeSession);
	}

	/**
	 * Cleaner API for working with the jBPM Processes.
	 * @return
	 * @throws SessionException
	 */
	public ProcessRuntime getProcessRuntime(KieReleaseId releaseId) throws SessionException {
		return this.getKnowledgeSession(releaseId);
	}
	
	public WorkItemManager getWorkItemService(KieReleaseId releaseId) {
		return this.getProcessRuntime(releaseId).getWorkItemManager();
	}
	
}

