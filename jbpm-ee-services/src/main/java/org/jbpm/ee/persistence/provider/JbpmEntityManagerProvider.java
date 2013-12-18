package org.jbpm.ee.persistence.provider;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.transaction.InvalidTransactionException;
import javax.transaction.SystemException;

import org.jbpm.ee.persistence.provider.JbpmDatabasePropertyProvider.JbpmDatabaseProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JbpmEntityManagerProvider {
	
	private static final Logger LOG = LoggerFactory.getLogger(JbpmEntityManagerProvider.class);
	
	@Inject
	private JbpmDatabaseProperties jbpmDatabaseProperties;
	
	@Produces
	@PersistenceUnit(unitName = "org.jbpm.persistence.jpa")
	public EntityManagerFactory getEntityManagerFactory() throws SystemException, InvalidTransactionException, IllegalStateException {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("hibernate.dialect", jbpmDatabaseProperties.getHibernateDialect());
		
		for(String property : properties.keySet()) {
			LOG.info("Setting ["+property+"]: "+properties.get(property));
		}
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa", properties);
		return emf; 
	}
}
