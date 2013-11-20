package org.jbpm.ee.services.ejb.interceptors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.jbpm.ee.services.ejb.annotations.PreprocessClassloader;
import org.jbpm.ee.services.ejb.annotations.ProcessInstanceId;
import org.jbpm.ee.services.ejb.annotations.ReleaseId;
import org.jbpm.ee.services.ejb.annotations.TaskId;
import org.jbpm.ee.services.ejb.annotations.WorkItemId;
import org.jbpm.ee.support.KieReleaseId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InterceptorUtil {

	private static final Logger LOG = LoggerFactory.getLogger(InterceptorUtil.class);
	
	private InterceptorUtil() {
		// seal
	}
	
	public static boolean requiresClassloaderInterception(Method method) {
		LOG.info("Present?" + method.isAnnotationPresent(PreprocessClassloader.class));
		
		return method.isAnnotationPresent(PreprocessClassloader.class);
	}
	
	public static KieReleaseId extractReleaseId(Method method, Object[] parameters) {
		return (KieReleaseId)extractMethodParameterValue(method, parameters, ReleaseId.class);
	}
	
	public static Long extractProcessInstanceId(Method method, Object[] parameters) {
		return (Long)extractMethodParameterValue(method, parameters, ProcessInstanceId.class);
	}
	
	public static Long extractTaskId(Method method, Object[] parameters) {
		return (Long)extractMethodParameterValue(method, parameters, TaskId.class);
	}
	
	public static Long extractWorkItemId(Method method, Object[] parameters) {
		return (Long)extractMethodParameterValue(method, parameters, WorkItemId.class);
	}
	
	public static Object extractMethodParameterValue(Method method, Object[] parameters, Class annotation) {
		//annotations on all parameters
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();

		for(int i=0, j=parameters.length; i<j; i++) {
			//annotations on each parameter
			for(Annotation parameterAnnotation : parameterAnnotations[i]) {
				if(annotation.isAssignableFrom(parameterAnnotation.annotationType())) {
					return parameters[i];
				}
			}
		}
		return null;
	}
}
