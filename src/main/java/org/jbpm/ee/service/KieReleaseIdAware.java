package org.jbpm.ee.service;

import org.jbpm.ee.support.KieReleaseId;

public interface KieReleaseIdAware {
	public void setKieReleaseId(KieReleaseId releaseId);
	void validateKieReleaseIdState();
}
