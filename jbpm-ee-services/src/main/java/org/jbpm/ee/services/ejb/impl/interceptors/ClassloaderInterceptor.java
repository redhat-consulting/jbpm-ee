package org.jbpm.ee.services.ejb.impl.interceptors;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.jbpm.ee.services.ejb.interceptors.InterceptorUtil;
import org.jbpm.ee.services.ejb.startup.BPMClassloaderService;
import org.jbpm.ee.services.model.KieReleaseId;
import org.jbpm.ee.services.model.LazyDeserializingMap;
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
		if(!InterceptorUtil.requiresClassloaderInterception(ctx.getTarget().getClass(), ctx.getMethod())) {
			LOG.info("Interceptor not required for method: "+ctx.getMethod().getName()+".  Target: "+ctx.getTarget().getClass());
			LOG.info("  - Source: "+ctx.getMethod().getDeclaringClass().getName()+" Method: "+ctx.getMethod().getName());
			LOG.info("  - Parameter count: "+ctx.getParameters().length);
			for(Object param : ctx.getParameters()) {
				LOG.info("  - Parameter: "+ReflectionToStringBuilder.toString(param));
			}
			return ctx.proceed();
		}
		LOG.info("Method: "+ctx.getMethod().getName()+" does require preprocessor; Parameter count: "+ctx.getParameters().length);
		
		setupClassloader(ctx.getTarget().getClass(), ctx.getMethod(), ctx.getParameters());
		
		try {
			lazyInitializeMaps(ctx.getParameters());
		}
		catch(IOException e) {
			throw new ClassloaderException("Exception deserializing object.", e); 
		}
		
		return ctx.proceed();
	}
	
	private void lazyInitializeMaps(Object[] parameters) throws IOException {
		for(int i=0, j=parameters.length; i<j; i++) {
			Object parameter = parameters[i];
			if(parameter == null) {
				//skip.
				continue;
			}
			if(LazyDeserializingMap.class.isAssignableFrom(parameter.getClass())) {
				LazyDeserializingMap obj = (LazyDeserializingMap)parameter;
				obj.initializeLazy();
			}
		}
	}
	
	
	/**
	 * Determines the parameter that is going to be used to setup the classloader on the
	 * server side.
	 * 
	 * @param method
	 * @param parameters
	 */
	private void setupClassloader(Class clz, Method method, Object[] parameters) {
		
		//setup the classloder..
		KieReleaseId releaseId = InterceptorUtil.extractReleaseId(clz, method, parameters);
		if(releaseId != null) {
			classloaderService.bridgeClassloaderByReleaseId(releaseId);
			return;
		}
		
		Long processInstanceId = InterceptorUtil.extractProcessInstanceId(clz, method, parameters);
		if(processInstanceId != null) {
			classloaderService.bridgeClassloaderByProcessInstanceId(processInstanceId);
			return;
		}
		
		Long taskId = InterceptorUtil.extractTaskId(clz, method, parameters);
		if(taskId != null) {
			classloaderService.bridgeClassloaderByTaskId(taskId);
			return;
		}
		
		Long workItemId = InterceptorUtil.extractWorkItemId(clz, method, parameters);
		if(workItemId != null) {
			classloaderService.bridgeClassloaderByWorkItemId(workItemId);
			return;
		}
			
		
		
	}
}
