package org.jbpm.ee.services.rest.impl;

import javax.ejb.EJB;

import org.jbpm.ee.services.ejb.local.RuleServiceLocal;
import org.jbpm.ee.services.rest.RuleServiceRest;

public class RuleServiceRestImpl implements RuleServiceRest {

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
