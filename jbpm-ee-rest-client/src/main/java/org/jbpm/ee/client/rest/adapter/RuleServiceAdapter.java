package org.jbpm.ee.client.rest.adapter;

import org.jbpm.ee.services.RuleService;
import org.jbpm.ee.services.rest.RuleServiceRest;

public class RuleServiceAdapter implements RuleService {

	private final RuleServiceRest ruleService;
	
	public RuleServiceAdapter(RuleServiceRest ruleService) {
		this.ruleService = ruleService;
	}
	
	@Override
	public int fireAllRules(Long processInstanceId) {
		return this.ruleService.fireAllRules(processInstanceId);
	}

	@Override
	public int fireAllRules(Long processInstanceId, int max) {
		return this.ruleService.fireAllRules(processInstanceId, max);
	}

	@Override
	public void insert(Long processInstanceId, Object object) {
		this.ruleService.insert(processInstanceId, object);
	}

}
