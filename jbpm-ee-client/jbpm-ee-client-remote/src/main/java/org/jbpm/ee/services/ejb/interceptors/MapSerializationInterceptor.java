package org.jbpm.ee.services.ejb.interceptors;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.drools.core.command.impl.GenericCommand;
import org.jboss.ejb.client.EJBClientInterceptor;
import org.jboss.ejb.client.EJBClientInvocationContext;
import org.jbpm.ee.services.model.LazyDeserializingMap;
import org.jbpm.ee.services.model.LazyDeserializingObject;
import org.jbpm.ee.support.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapSerializationInterceptor implements EJBClientInterceptor {

	private static final Logger LOG = LoggerFactory.getLogger(MapSerializationInterceptor.class);
	
	@Override
	public void handleInvocation(EJBClientInvocationContext context) throws Exception {
		Set<Integer> lazyParameterIndex = new HashSet<Integer>(); 
		
		boolean response = InterceptorUtil.requiresClassloaderInterception(context.getViewClass(), context.getInvokedMethod(), lazyParameterIndex);
		if(!response) {
			if(LOG.isDebugEnabled()) {
				LOG.debug("Method: "+context.getInvokedMethod().getName()+" does not require preprocessing.");
			}
			context.sendRequest();
			return;
		}
		if(LOG.isDebugEnabled()) {
		LOG.debug("Method: "+context.getInvokedMethod().getName()+" does require preprocessor; Parameter count: "+context.getParameters().length);
			LOG.debug("Needs lazy initialization on parameters: ");
			for(Integer pos : lazyParameterIndex) {
				LOG.info("Parameter["+pos+"]"+context.getInvokedMethod().getParameterTypes()[pos]);
			}
		}
		
		
		//look for the map objects, and convert them to lazy deserializing map.
		for(int i=0, j=context.getParameters().length; i<j; i++) {
			if(lazyParameterIndex.contains(i)) {
				//serialize it to lazy implementation.
				Serializable parameter = (Serializable)context.getParameters()[i];
				LazyDeserializingObject obj = new LazyDeserializingObject(parameter);
				context.getParameters()[i] = obj;
				
				LOG.info("Wrapped serializable parameter["+i+"]: "+parameter.getClass().getName());
			}
		}

        context.sendRequest();
	}
	
	@Override
	public Object handleInvocationResult(EJBClientInvocationContext context) throws Exception {
		return context.getResult();
	}

}
