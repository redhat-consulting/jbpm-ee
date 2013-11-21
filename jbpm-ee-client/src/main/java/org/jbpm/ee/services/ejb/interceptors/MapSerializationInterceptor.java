package org.jbpm.ee.services.ejb.interceptors;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import org.jboss.ejb.client.EJBClientInterceptor;
import org.jboss.ejb.client.EJBClientInvocationContext;
import org.jbpm.ee.services.model.LazyDeserializingMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapSerializationInterceptor implements EJBClientInterceptor {

	private static final Logger LOG = LoggerFactory.getLogger(MapSerializationInterceptor.class);
	
	@Override
	public void handleInvocation(EJBClientInvocationContext context) throws Exception {
		if(!InterceptorUtil.requiresClassloaderInterception(context.getViewClass(), context.getInvokedMethod())) {
			LOG.info("Method: "+context.getInvokedMethod().getName()+" does not require preprocessing.");
			context.sendRequest();
			return;
		}
		LOG.info("Method: "+context.getInvokedMethod().getName()+" does require preprocessor; Parameter count: "+context.getParameters().length);
		
		
		//look for the map objects, and convert them to lazy deserializing map.
		for(int i=0, j=context.getParameters().length; i<j; i++) {
			Object parameter = context.getParameters()[i];
			if(parameter == null) {
				//skip.
				continue;
			}
			if(Map.class.isAssignableFrom(parameter.getClass())) {
				if(((Map)parameter).isEmpty())
				{
					//skip.
					continue;
				}
				
				LOG.info("Map found in parameters of method: "+context.getInvokedMethod().getName());
				LazyDeserializingMap map = new LazyDeserializingMap();
				map.putAll((Map<String, Object>)parameter);
				
				context.getParameters()[i] = map;
				LOG.debug("Replaced map with serializable map.");
			}
		}

        context.sendRequest();
	}

	@Override
	public Object handleInvocationResult(EJBClientInvocationContext context) throws Exception {
		return context.getResult();
	}

}
