package org.jbpm.ee.client.adapter;

import java.util.Collection;

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
		return ruleService.fireAllRules(processInstanceId);
	}

	@Override
	public int fireAllRules(Long processInstanceId, int max) {
		return ruleService.fireAllRules(processInstanceId, max);
	}

	@Override
	public FactHandle insert(Long processInstanceId, Object object) {
		return ruleService.insert(processInstanceId, object);
	}

	@Override
	public void delete(Long processInstanceId, FactHandle factHandle) {
		ruleService.delete(processInstanceId, factHandle);
		
	}

	@Override
	public Object getObject(Long processInstanceId, FactHandle factHandle) {
		return ruleService.getObject(processInstanceId, factHandle);
	}

	@Override
	public Collection<? extends Object> getObjects(Long processIntanceId) {
		return ruleService.getObjects(processIntanceId);
	}
	

}
