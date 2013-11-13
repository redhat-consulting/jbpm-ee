package org.jbpm.ee.services;


/**
 * 
 * @author bdavis, abaxter
 * 
 * Interface for BRMS rules runtime
 *
 * For inserting facts and firing rules.
 * 
 * Note: fireAllRules must be run before any rules will be
 * executed, including rules in ruleflow-groups
 *
 */
public interface RuleService {


	/**
	 * Set rules to fire for a particular process 
	 * 
	 * @param processInstanceId The process instance's unique identifier
	 * @return The number of rules fired
	 */
	int fireAllRules(Long processInstanceId);
	

    /**
     * Set up to max rules to fire for a particular process 
     * 
     * @param processInstanceId The process instance's unique identifier
     * @param max The maximum number of rules to fire
     * @return The number of rules fired
     */
	int fireAllRules(Long processInstanceId, int max);
	

    /**
     * Insert a fact into the process's rule runtime
     * 
     * @param processInstanceId The process instance's unique identifier
     * @param object The fact to be inserted
     */
    void insert(Long processInstanceId, Object object);
    
}
