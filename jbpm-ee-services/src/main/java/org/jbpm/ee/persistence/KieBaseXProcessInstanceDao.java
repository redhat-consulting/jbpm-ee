package org.jbpm.ee.persistence;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.kie.api.builder.ReleaseId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@LocalBean
public class KieBaseXProcessInstanceDao {

	private static final Logger LOG = LoggerFactory.getLogger(KieBaseXProcessInstanceDao.class);
	
	@Inject
	private EntityManager entityManager;
	
	public void addKieBaseXProcessInstanceReference(ReleaseId kieReleaseId, Long processInstanceId) {
		KieBaseXProcessInstance kbx = new KieBaseXProcessInstance();
		
		kbx.setKieProcessInstanceId(processInstanceId);
		kbx.setReleaseArtifactId(kieReleaseId.getArtifactId());
		kbx.setReleaseVersion(kieReleaseId.getVersion());
		kbx.setReleaseGroupId(kieReleaseId.getGroupId());
		
		entityManager.persist(kbx);
		
		LOG.debug("Created KieBaseXProcessInstance for Process Instance ID: "+kbx.getKieProcessInstanceId());
	}
	
	public void removeKieBaseXProcessInstanceReference(Long processInstanceId) {
		Query q = entityManager.createQuery("from KieBaseXProcessInstance kb where kb.kieProcessInstanceId=:processInstanceId");
		q.setParameter("processInstanceId", processInstanceId);
		
		try {
			KieBaseXProcessInstance xref = (KieBaseXProcessInstance)q.getSingleResult();
			entityManager.remove(xref);
			LOG.debug("Deleted KieBaseXProcessInstance for Process Instance ID: "+processInstanceId);
		}
		catch(NoResultException e) {
			LOG.warn("No result found for ProcessInstance: "+processInstanceId, e);
		}
	}
	
	
	
}
