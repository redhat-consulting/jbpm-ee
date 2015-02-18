package org.jbpm.ee.services.ejb.startup;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jbpm.services.task.identity.JBossUserGroupCallbackImpl;
import org.jbpm.services.task.identity.LDAPUserGroupCallbackImpl;
import org.kie.internal.task.api.UserGroupCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides the UserGroupCallback, loaded via System Properties.  If the system property indicates LDAP, the {@link LDAPUserGroupCallbackImpl} is returned.
 * Otherwise, the {@link JBossUserGroupCallbackImpl} is provided.
 * 
 * @author abaxter
 * 
 */
@Startup
@Singleton(name = "ResourceManager")
public class ResourceManagerBean {

	private static final Logger LOG = LoggerFactory.getLogger(ResourceManagerBean.class);
	
	@PersistenceContext(name = "org.jbpm.persistence.jpa", unitName = "org.jbpm.persistence.jpa")
	private EntityManager em;


	@Produces
	public EntityManager getEntityManager() {
		return em;
	}

	@Produces
	public UserGroupCallback getUserGroupCallback() throws IOException {

		Properties props = new Properties();
		
		if (System.getProperty("jbpm.ee.user.group.callback.ldap.properties") != null) {
			
			
			String ldapFileLocation = System.getProperty("jbpm.ee.user.group.callback.ldap.properties");
			LOG.info("Loading LDAP file from location: "+ ldapFileLocation);
			
			props.load(new FileInputStream(ldapFileLocation));
			
			return new LDAPUserGroupCallbackImpl(props);
			
		}else if (System.getProperty("jbpm.ee.user.group.callback.file.properties") != null) {
			
			String fileLocation = System.getProperty("jbpm.ee.user.group.callback.file.properties");
			LOG.info("Loading Properties file from location: "+ fileLocation);
			
			props.load(new FileInputStream(fileLocation));

			return new JBossUserGroupCallbackImpl(props);
			
		}else{
			throw new IllegalStateException("Please configure a User Group Callback Implementation.");
		}
		
	}
}
