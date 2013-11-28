package org.jbpm.ee.services.util;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;
import org.jbpm.ee.runtime.KieContainerEE;
import org.jbpm.ee.services.ejb.startup.BPMClassloaderService;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.scanner.MavenRepository;
import org.mvel2.MVEL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.aether.artifact.Artifact;

public class WorkItemDefinitionUtil {

	private static final Logger LOG = LoggerFactory.getLogger(WorkItemDefinitionUtil.class);
	
	public static List<Map<String, Object>> loadWorkItemDefinitions(ReleaseId releaseId, String pattern) {
		Artifact artifact = MavenRepository.getMavenRepository().resolveArtifact(releaseId.toString());
		
		LOG.info("Artifact found: "+artifact);
		
		InputStream is = null;
		ZipFile zip = null;
		try {
			File jar = artifact.getFile();
			LOG.info("File reference: "+jar.getAbsolutePath());
			LOG.info("Loading entry: "+pattern);
	        zip = new ZipFile(jar);
	        ZipEntry entry = zip.getEntry(pattern);
	        
	        if(entry == null) {
	        	LOG.warn("Did not find "+pattern);
	        	return new LinkedList<Map<String, Object>>();
	        }
	        
	        LOG.info("Found entry: "+entry);
	        
	        is = zip.getInputStream(entry);

	        String content = IOUtils.toString(is);
			List<Map<String, Object>> vals = (List<Map<String, Object>>)MVEL.eval(content, new HashMap<String, Object>());
	        		
			LOG.info("Read: "+vals.toString());
			
			
	        return vals;
		}
		catch(Exception e) {
			LOG.error("Exception reading "+pattern, e);
		}
		finally {
			IOUtils.closeQuietly(is);
			BPMClassloaderService.closeQuietly(zip);
		}
		
		return null;
	}
	
}
