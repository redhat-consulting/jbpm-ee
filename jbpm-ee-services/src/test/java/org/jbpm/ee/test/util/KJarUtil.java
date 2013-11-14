package org.jbpm.ee.test.util;

import java.util.List;

import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Primarily copied from org.jbpm.test.util.AbstractBaseTest
// A set of static functions to help in creating kjars on the fly
public class KJarUtil {

	private KJarUtil() {}
	
	private static final Logger LOG = LoggerFactory.getLogger(KJarUtil.class);
	
	private static final String defaultKieBaseModel = "defaultKieBase";
	private static final String defaultKieSessionModel = "defaultKieSession";
	
	public static String getPom(ReleaseId releaseId, ReleaseId... dependencies) {
		StringBuilder pomSb = new StringBuilder();
		pomSb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		pomSb.append("<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
		pomSb.append("         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n");
		pomSb.append("  <modelVersion>4.0.0</modelVersion>\n");
		pomSb.append("\n");
		pomSb.append("  <groupId>");
		pomSb.append(releaseId.getGroupId());
		pomSb.append("</groupId>\n");
		pomSb.append("  <artifactId>");
		pomSb.append(releaseId.getArtifactId());
		pomSb.append("</artifactId>\n");
		pomSb.append("  <version>");
		pomSb.append(releaseId.getVersion());
		pomSb.append( "</version>\n");
		pomSb.append("\n");
		
		if (dependencies != null && dependencies.length > 0) {
			pomSb.append("<dependencies>\n");
			for (ReleaseId dep : dependencies) {
				pomSb.append("<dependency>\n");
				pomSb.append("  <groupId>");
				pomSb.append(dep.getGroupId());
				pomSb.append("</groupId>\n");
				pomSb.append("  <artifactId>");
				pomSb.append(dep.getArtifactId());
				pomSb.append("</artifactId>\n");
				pomSb.append("  <version>");
				pomSb.append(dep.getVersion());
				pomSb.append( "</version>\n");
				pomSb.append("</dependency>\n");
			}
			pomSb.append("</dependencies>\n");
		}
		pomSb.append("</project>");
		return pomSb.toString();
	}
	
	public static InternalKieModule createKieJar(KieServices ks, ReleaseId releaseId, List<String> resources) {
		return createKieJar(ks, releaseId, resources, defaultKieBaseModel, defaultKieSessionModel);
	}
	
	public static InternalKieModule createKieJar(KieServices ks, ReleaseId releaseId, List<String> resources, String kieBaseModelId, String kieSessionModelId ) {
     
        
        KieFileSystem kfs = createKieFileSystemWithKProject(ks, kieBaseModelId, kieSessionModelId);
        kfs.writePomXML( getPom(releaseId) );

        
        for (String resource : resources) {
            kfs.write("src/main/resources/" + kieBaseModelId + "/" + resource, ResourceFactory.newFileResource(resource));
        }
        
        KieBuilder kieBuilder = ks.newKieBuilder(kfs);
        if (!kieBuilder.buildAll().getResults().getMessages().isEmpty()) {
            for (Message message : kieBuilder.buildAll().getResults().getMessages()) {
            	LOG.error("Error Message: ({}) {}", message.getPath(), message.getText());
            }
            throw new RuntimeException(
                    "There are errors builing the package, please check your knowledge assets!");
        }
        
        return ( InternalKieModule ) kieBuilder.getKieModule();
    }
	
	public static KieFileSystem createKieFileSystemWithKProject(KieServices ks, String kieBaseModelId, String kieSessionModelId) {
		KieModuleModel kproj = ks.newKieModuleModel();
		
		 KieBaseModel kieBaseModel1 = kproj.newKieBaseModel(kieBaseModelId).setDefault(true).addPackage("*")
	                .setEqualsBehavior( EqualityBehaviorOption.EQUALITY )
	                .setEventProcessingMode( EventProcessingOption.STREAM );
		 
		 kieBaseModel1.newKieSessionModel(kieSessionModelId).setDefault(true)
         .setType(KieSessionModel.KieSessionType.STATEFUL)
         .setClockType( ClockTypeOption.get("realtime") );
		 KieFileSystem kfs = ks.newKieFileSystem();
		 kfs.writeKModuleXML(kproj.toXML());
		 return kfs;
	}
}
