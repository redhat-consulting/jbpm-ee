package org.jbpm.ee.services.ws;

import java.util.Collection;

import javax.ejb.EJB;
import javax.jws.HandlerChain;
import javax.jws.WebService;

import org.jbpm.ee.services.ejb.local.RuleServiceLocal;
import org.jbpm.ee.services.model.rules.FactHandle;
import org.jbpm.ee.services.ws.exceptions.RemoteServiceException;

/**
 * @see RuleServiceWS
 * @author bradsdavis
 *
 */
@WebService(targetNamespace="http://jbpm.org/v6/RuleService/wsdl", serviceName="RuleService", endpointInterface="org.jbpm.ee.services.ws.RuleServiceWS")
@HandlerChain(file="jbpm-context-handler.xml")
public class RuleServiceWSImpl implements RuleServiceWS {
	
	@EJB
	private RuleServiceLocal ruleRuntime;
	
	@Override
	public int fireAllRules(Long processInstanceId) {
		try {
			return ruleRuntime.fireAllRules(processInstanceId);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public int fireAllRules(Long processInstanceId, int max) {
		try {
			return ruleRuntime.fireAllRules(processInstanceId, max);
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public FactHandle insert(Long processInstanceId, Object object) {
		try {
			return ruleRuntime.insert(processInstanceId, object);			
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public void delete(Long processInstanceId, FactHandle factHandle) {
		try {
			ruleRuntime.delete(processInstanceId, factHandle);			
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
		
	}

	@Override
	public Object getObject(Long processInstanceId, FactHandle factHandle) {
		try {
			return ruleRuntime.getObject(processInstanceId, factHandle);			
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

	@Override
	public Collection<? extends Object> getObjects(Long processInstanceId) {
		try {
			return ruleRuntime.getObjects(processInstanceId);			
		}
		catch(Exception e) {
			throw new RemoteServiceException(e);
		}
	}

}
