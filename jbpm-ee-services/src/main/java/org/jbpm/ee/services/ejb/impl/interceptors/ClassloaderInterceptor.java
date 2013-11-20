package org.jbpm.ee.services.ejb.impl.interceptors;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.jbpm.ee.services.ejb.startup.BPMClassloaderService;
import org.jbpm.ee.services.model.SerializableStringObjectMap;
import org.jbpm.ee.support.KieReleaseId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Interceptor
@ClassloaderBinding
public class ClassloaderInterceptor {
	private static final Logger LOG = LoggerFactory.getLogger(ClassloaderInterceptor.class);

	@Inject
	BPMClassloaderService classloaderService;
	
	@AroundInvoke
	public Object intercept(InvocationContext ctx) throws Exception {
		LOG.info("Interceptor called: "+ctx.getMethod().getName());
		for(int i=0, j=ctx.getParameters().length; i<j; i++) {
			Object parameter = ctx.getParameters()[i];
			
			if(KieReleaseId.class.isAssignableFrom(parameter.getClass())) {
				classloaderService.bridgeClassloaderByReleaseId((KieReleaseId)parameter);
			}
			
			if(SerializableStringObjectMap.class.isAssignableFrom(parameter.getClass())) {
				SerializableStringObjectMap obj = (SerializableStringObjectMap)parameter;
				obj.initializeLazy();
			}
		}
		
		
		return ctx.proceed();
	}
}
