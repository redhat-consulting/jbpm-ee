package org.jbpm.ee.persistence.provider;

import javax.enterprise.inject.Produces;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * This loads the jBPM Database Property from the system properties, and then uses this to setup 
 * the appropriate Hibernate Dialect and Quartz configuration properties. 
 * 
 * @author bradsdavis
 *
 */
public class JbpmDatabasePropertyProvider {

	private static final Logger LOG = LoggerFactory.getLogger(JbpmDatabasePropertyProvider.class);
	private static final String DATABASE_KEY = "jbpm.ee.database.configuration.type";
	
	@Produces
	public JbpmDatabaseProperties setupEntityManager() {
		String databaseType = System.getProperty(DATABASE_KEY);
		JbpmDatabaseProperties properties = resolveEntityManager(databaseType);
		return properties;
	}
	
	public static JbpmDatabaseProperties resolveEntityManager(String databaseType) {
		if(StringUtils.isBlank(databaseType)) {
			throw new IllegalStateException("Must provide system property: "+DATABASE_KEY);
		}
		
		JbpmDatabaseProperties databaseInformation = null;
		for(JbpmDatabaseProperties dbType : JbpmDatabaseProperties.values()) {
			if(StringUtils.equalsIgnoreCase(dbType.name(), databaseType)) {
				LOG.info("Database type: "+dbType.description);
				databaseInformation = dbType;
			}
		}
		
		if(databaseInformation == null) {
			throw new IllegalStateException("Property ["+DATABASE_KEY+"] must provide supported database type: Oracle, MySQL, Postgres, MSSQL; currently providing: "+databaseType);
		}
		
		return databaseInformation;
	}
	
	
	public enum JbpmDatabaseProperties {

		ORACLE("Oracle", "org.hibernate.dialect.Oracle10gDialect", "jbpm-ee-quartz-oracle.properties"),
		MYSQL("MySQL", "org.hibernate.dialect.MySQL5InnoDBDialect", "jbpm-ee-quartz-mysql.properties"),
		POSTGRES("Postgres", "org.hibernate.dialect.PostgreSQLDialect", "jbpm-ee-quartz-postgres.properties"),
		MSSQL("SQLServer", "org.hibernate.dialect.SQLServerDialect", "jbpm-ee-quartz-sqlserver.properties"),
		DB2("DB2", "org.hibernate.dialect.DB2Dialect", "jbpm-ee-quartz-db2.properties");

		private final String description;
		private final String hibernateDialect;
		private final String quartzConfiguration;
		
		private JbpmDatabaseProperties(String description, String hibernateDialect, String quartzDialect) {
			this.description = description;
			this.hibernateDialect = hibernateDialect;
			this.quartzConfiguration = quartzDialect;
		}
		
		public String getDescription() {
			return description;
		}
		
		public String getHibernateDialect() {
			return hibernateDialect;
		}
		
		public String getQuartzConfiguration() {
			return quartzConfiguration;
		}
		
	}
	
}
