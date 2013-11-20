package org.jbpm.ee.services.ejb.interceptors;

import java.util.Map;

import org.jboss.ejb.client.EJBClientInterceptor;
import org.jboss.ejb.client.EJBClientInvocationContext;
import org.jbpm.ee.services.model.SerializableStringObjectMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapSerializationInterceptor implements EJBClientInterceptor {

	private static final Logger LOG = LoggerFactory.getLogger(MapSerializationInterceptor.class);
	
	@Override
	public void handleInvocation(EJBClientInvocationContext context) throws Exception {
		LOG.trace("Before conversion...");
		for(int i=0, j=context.getParameters().length; i<j; i++) {
			Object parameter = context.getParameters()[i];
			if(LOG.isTraceEnabled()) {
				LOG.trace("Parameter: "+parameter.getClass());
			}
			if(Map.class.isAssignableFrom(parameter.getClass())) {
				if(LOG.isDebugEnabled()) {
					LOG.debug("Map found in parameters of method: "+context.getInvokedMethod().getName());
				}
				SerializableStringObjectMap map = new SerializableStringObjectMap();
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
