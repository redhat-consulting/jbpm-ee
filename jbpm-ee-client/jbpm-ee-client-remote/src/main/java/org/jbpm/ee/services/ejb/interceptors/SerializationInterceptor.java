package org.jbpm.ee.services.ejb.interceptors;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.jboss.ejb.client.EJBClientInterceptor;
import org.jboss.ejb.client.EJBClientInvocationContext;
import org.jbpm.ee.services.model.LazyDeserializingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerializationInterceptor implements EJBClientInterceptor {

	private static final Logger LOG = LoggerFactory.getLogger(SerializationInterceptor.class);
	
	private String parentInitializer;
	
	public SerializationInterceptor() {
		parentInitializer = "unknown";
	}
	
	public SerializationInterceptor(String parentInitializer) {
		this.parentInitializer = parentInitializer;
	}
	
	@Override
	public void handleInvocation(EJBClientInvocationContext context) throws Exception {
		Set<Integer> lazyParameterIndex = new HashSet<Integer>(); 
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("Interceptor initialized by: " + parentInitializer);
		}
		
		boolean response = InterceptorUtil.getInstance().requiresClassloaderInterception(context.getViewClass(), context.getInvokedMethod(), lazyParameterIndex);
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
				LOG.debug("Parameter["+pos+"]"+context.getInvokedMethod().getParameterTypes()[pos]);
			}
		}
		
		//look for the map objects, and convert them to lazy deserializing map.
		for(int i=0, j=context.getParameters().length; i<j; i++) {
			if(lazyParameterIndex.contains(i)) {
				//serialize it to lazy implementation.
				Serializable parameter = (Serializable)context.getParameters()[i];
				if (parameter instanceof LazyDeserializingObject) {
					LOG.warn("Serialization interceptor should only be registered once per application. Lazy deserialization appears to be invoked multiple times. Please validate that " + SerializationInterceptor.class + "is only registered once per application.");
					continue;
				}
				LazyDeserializingObject obj = new LazyDeserializingObject(parameter);
				context.getParameters()[i] = obj;
				
				if(LOG.isDebugEnabled()) {
					LOG.debug("Wrapped serializable parameter["+i+"]: "+parameter.getClass().getName());
				}
			}
		}

        context.sendRequest();
	}
	
	@Override
	public Object handleInvocationResult(EJBClientInvocationContext context) throws Exception {
		return context.getResult();
	}

}
