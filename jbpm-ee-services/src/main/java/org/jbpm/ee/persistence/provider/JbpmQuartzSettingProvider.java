package org.jbpm.ee.persistence.provider;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.solder.servlet.WebApplication;
import org.jboss.solder.servlet.event.Initialized;
import org.jbpm.ee.persistence.provider.JbpmDatabasePropertyProvider.JbpmDatabaseProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JbpmQuartzSettingProvider {

	private static final Logger LOG = LoggerFactory.getLogger(JbpmQuartzSettingProvider.class);
	
	@Inject
	private JbpmDatabaseProperties jbpmDatabaseProperties;
	
    public void onStartup(@Observes @Initialized WebApplication webApplication){
    	String quartzLocation = "quartz/"+jbpmDatabaseProperties.getQuartzConfiguration();
    	LOG.info("Setting org.quartz.properties to: "+quartzLocation);
    	System.setProperty("org.quartz.properties", quartzLocation);
    }
}
