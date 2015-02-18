package org.jbpm.ee.service.wid;

import java.util.List;
import java.util.Map;

import org.jbpm.ee.services.model.KieReleaseId;
import org.jbpm.ee.services.util.WorkItemDefinitionUtil;
import org.junit.Test;

public class WorkItemDefinitionLoadingTest {

	@Test
	public void testLoad() throws Exception {
		KieReleaseId releaseId = new KieReleaseId("org.jbpm.jbpm-ee", "jbpm-ee-kjar-sample", "1.0.0-SNAPSHOT");
		List<Map<String, Object>> map = WorkItemDefinitionUtil.loadWorkItemDefinitions(releaseId, "META-INF/WorkDefinitions.wid");
		
		System.out.println(map);
	}
}
