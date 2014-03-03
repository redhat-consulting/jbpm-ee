package org.jbpm.ee.client.adapter;

import org.jbpm.ee.services.RuleService;
import org.jbpm.ee.services.model.rules.FactHandle;
import org.jbpm.ee.services.ws.RuleServiceWS;


/**
 * Adapts the WS Services JAXB responses to the {@link RuleService} interface. 
 * 
 * @see RuleService
 * 
 * @author bradsdavis
 *
 */
public class RuleServiceAdapter implements RuleService {

	private final RuleServiceWS ruleService;
	
	public RuleServiceAdapter(RuleServiceWS ruleService) {
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
	public FactHandle insert(Long processInstanceId, Object object) {
		return this.ruleService.insert(processInstanceId, object);
	}

	@Override
	public void retract(Long processInstanceId, FactHandle factHandle) {
		this.ruleService.retract(processInstanceId, factHandle);
		
	}
	

}
