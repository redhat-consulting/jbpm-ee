package org.jbpm.ee.services.util;

import java.io.File;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;
import org.jbpm.ee.runtime.KieContainerEE;
import org.jbpm.ee.services.ejb.startup.BPMClassloaderService;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.scanner.MavenRepository;
import org.mvel2.MVEL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.aether.artifact.Artifact;

public class WorkItemDefinitionUtil {

	private static final Logger LOG = LoggerFactory.getLogger(WorkItemDefinitionUtil.class);
	
	public static void loadWorkItemhandlersToSession(KieContainerEE containerEE, RuntimeEngine engine) {
		try {
			
			Map<String, String> handlers = containerEE.getWorkItemHandlers();
				for(String handlerName : handlers.keySet()) {
					String handlerClassName = handlers.get(handlerName);
					LOG.info("Handler: "+handlerName+" -> "+handlerClassName);
					Class handlerClass = containerEE.getClassLoader().loadClass(handlerClassName);
					org.kie.api.runtime.process.WorkItemHandler handlerInstance = (WorkItemHandler)handlerClass.newInstance();
					engine.getKieSession().getWorkItemManager().registerWorkItemHandler(handlerName, handlerInstance);
				}
			}
			catch(Exception e) {
				LOG.error("Exception loading handlers.", e);
			}
	}
	
	
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
	        
	        
	        
	        Enumeration<? extends ZipEntry> entries = zip.entries(); 
	        while(entries.hasMoreElements())
	        {
	        	LOG.info("Entry: "+entries.nextElement());
	        }
	        ZipEntry entry = zip.getEntry(pattern);
	        
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
