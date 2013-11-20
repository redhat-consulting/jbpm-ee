package org.jbpm.ee.services.ws.interceptors;

import javax.xml.bind.Unmarshaller.Listener;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.tools.javac.util.Log;

public class JaxbListener extends Listener {
	private static final Logger LOG = LoggerFactory.getLogger(JaxbListener.class);
	
	@Override
	public void beforeUnmarshal(Object target, Object parent) {
		LOG.info("Parent: "+ReflectionToStringBuilder.toString(parent));
		LOG.info("Target: "+ReflectionToStringBuilder.toString(target));
		
	}
}
