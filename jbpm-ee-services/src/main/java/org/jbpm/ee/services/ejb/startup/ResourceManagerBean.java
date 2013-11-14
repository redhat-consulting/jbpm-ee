package org.jbpm.ee.services.ejb.startup;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import org.jbpm.services.task.identity.JBossUserGroupCallbackImpl;
import org.kie.internal.task.api.UserGroupCallback;

/**
 * Provides CDI Resources
 * @author abaxter
 *
 */
@Startup
@Singleton(name="ResourceManager")
public class ResourceManagerBean {

	@PersistenceContext(name="org.jbpm.persistence.jpa", unitName="org.jbpm.persistence.jpa")
	private EntityManager em;
	
	@Produces
	public EntityManagerFactory getEntityManagerFactory() {
		return em.getEntityManagerFactory();
	}
	
	@Produces
	public EntityManager getEntityManager() {
		return em;
	}
	
	@Produces
	public UserGroupCallback getUserGroupCallback() {
		// will be JAASUserGroupCallbackImpl() once we switch to LDAP
		// JAASUserGroupCallbackImpl will require a security context, which means changing quite a bit
		return new JBossUserGroupCallbackImpl("classpath:/usergroup.properties");
	}
}
