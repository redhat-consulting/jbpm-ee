package org.jbpm.ee.services.ejb.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.jboss.ejb3.annotation.Clustered;
import org.jbpm.ee.services.RuleService;
import org.jbpm.ee.services.ejb.impl.interceptors.JBPMContextEJBBinding;
import org.jbpm.ee.services.ejb.impl.interceptors.JBPMContextEJBInterceptor;
import org.jbpm.ee.services.ejb.local.RuleServiceLocal;
import org.jbpm.ee.services.ejb.remote.RuleServiceRemote;
import org.jbpm.ee.services.ejb.startup.KnowledgeManagerBean;


/***
 * Provides a wrapper implementation for the CDI services in order to support consistent execution by Local, Remote, REST, and SOAP execution.
 * 
 * {@see RuleService}
 * 
 * @author bradsdavis
 *
 */
@JBPMContextEJBBinding
@Interceptors({JBPMContextEJBInterceptor.class})
@Clustered
@Stateless
public class RuleServiceBean implements RuleService, RuleServiceLocal, RuleServiceRemote {

	@EJB
	private KnowledgeManagerBean knowledgeManager;

	@Override
	public int fireAllRules(Long processInstanceId) {
		return knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession().fireAllRules();
	}

	@Override
	public int fireAllRules(Long processInstanceId, int max) {
		return knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession().fireAllRules(max);
	}

	@Override
	public void insert(Long processInstanceId, Object object) {
		knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession().insert(object);
	}
	
	

	
	
	
}
