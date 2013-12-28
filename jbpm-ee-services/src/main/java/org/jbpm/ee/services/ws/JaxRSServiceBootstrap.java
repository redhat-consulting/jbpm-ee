package org.jbpm.ee.services.ws;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.jbpm.ee.services.ws.interceptors.JBPMContextRestInterceptor;

@ApplicationPath("/rest")
public class JaxRSServiceBootstrap extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		return new HashSet<Class<?>>(
				Arrays.asList(
						ProcessServiceWSImpl.class, 
						RuleServiceWSImpl.class,
						TaskServiceWSImpl.class,
						WorkItemServiceWSImpl.class,
						JBPMContextRestInterceptor.class
				)); 
	}
}
