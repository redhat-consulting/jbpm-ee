package org.jbpm.ee.services.ws;

import javax.ejb.EJB;
import javax.jws.WebService;

import org.jbpm.ee.services.ejb.local.RuleServiceLocal;

@WebService(targetNamespace="http://jbpm.org/v6/RuleService/wsdl", serviceName="RuleService", endpointInterface="org.jbpm.ee.services.ws.RuleServiceWS")
public class RuleServiceWSImpl implements RuleServiceWS {
	
	@EJB
	private RuleServiceLocal ruleRuntime;
	
	@Override
	public int fireAllRules(Long processInstanceId) {
		return ruleRuntime.fireAllRules(processInstanceId);
	}

	@Override
	public int fireAllRules(Long processInstanceId, int max) {
		return ruleRuntime.fireAllRules(processInstanceId, max);
	}

	@Override
	public void insert(Long processInstanceId, Object object) {
		ruleRuntime.insert(processInstanceId, object);
	}

}
