package org.jbpm.ee.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
/**
  * Based off of: http://codebias.blogspot.com/2013/04/environment-configuration-property.html
  * 
  * Reads environment.properties files at startup and makes them available for injecting
  * 
  * @author abaxter
  * 
  */
@ApplicationScoped
public class ConfigurationFactory {
 
    private static final String ENVIRONMENT_NAME_KEY = "environment.name";
    private static final String DEFAULT_PROPS_FILENAME = "environment.properties";
    private static final String PROPS_FILENAME_FORMAT = "environment_%s.properties";
    private Properties environmentProps;
 
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationFactory.class);
    
    @PostConstruct
    public void initEnvironmentProps() throws IOException {
        environmentProps = new Properties();
        String propsFilename = DEFAULT_PROPS_FILENAME;
        String environmentName = System.getProperty(ENVIRONMENT_NAME_KEY);
        if (environmentName != null) {
            propsFilename = String.format(PROPS_FILENAME_FORMAT, environmentName);
        }
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propsFilename);
        if (inputStream == null) {
            throw new FileNotFoundException("Properties file for environment " + environmentName + " not found in the classpath: " + propsFilename);
        }
        environmentProps.load(inputStream);
    }
    
    @Produces
    @Configuration
    public String getConfigValueAsString(InjectionPoint ip) {
        Configuration config = ip.getAnnotated().getAnnotation(Configuration.class);
        String configKey = config.value();
        if (configKey.isEmpty()) {
            throw new IllegalArgumentException("Properties value key is required.");
        }
        return environmentProps.getProperty(configKey);
    }
    
    @Produces
    @Configuration
    public Long getConfigValueAsLong(InjectionPoint ip) {
    	String configValue = getConfigValueAsString(ip);
    	return Long.valueOf(configValue);
    }
}