package org.jbpm.ee.service.core;

import java.io.InputStream;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.osgi.spi.OSGiManifestBuilder;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseJBPMServiceTest {

	private static final Logger LOG = LoggerFactory.getLogger(BaseJBPMServiceTest.class);
	
	@Deployment
	@OverProtocol("Servlet 3.0")
	public static WebArchive createDeployment() throws Exception {
		//MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");
	
		PomEquippedResolveStage resolveStage = Maven.resolver().loadPomFromFile("pom.xml");
		
		final WebArchive archive = ShrinkWrap.create(WebArchive.class, "test-jbpm-services.war");
		archive.addAsManifestResource("jbossas-ds.xml");
		archive.addAsManifestResource("hornetq-jms.xml");
		archive.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		archive.addAsWebInfResource("test-web.xml", "web.xml");
		
		System.out.println(archive);
		
		archive.setManifest(new Asset() {  
	         public InputStream openStream() {  
	             OSGiManifestBuilder builder = OSGiManifestBuilder.newInstance();  
	             builder.addManifestHeader("Dependencies", "org.osgi.core");  
	             builder.addManifestHeader("Bundle-ManifestVersion", "2");
	             builder.addManifestHeader("Bundle-SymbolicName", "jbpmeeservice_1.0.0-SNAPSHOT");
	             builder.addManifestHeader("Bund-Name", "JBPM-EE");
	             return builder.openStream();  
	         }  
	     });  
		
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "test-jbpm-services.jar")
	        .addPackages(true, "org.jbpm.ee")
	        .addAsManifestResource("META-INF/beans.xml", "beans.xml")
	        .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
	        .addAsResource("usergroup.properties", "usergroup.properties")
	        .addAsResource("environment.properties", "environment.properties");
	    //System.out.println(jar.toString(true));
		archive.addAsLibraries(jar);
		archive.addAsLibraries(resolveStage.resolve("org.jbpm.jbpm-ee:jbpm-ee-client").withTransitivity().asFile());
		archive.addAsLibraries(resolveStage.resolve("org.jbpm.jbpm-ee:jbpm-ee-rest-client").withoutTransitivity().asFile());
		archive.addAsLibraries(resolveStage.resolve("org.jbpm:jbpm-flow").withTransitivity().asFile());
		archive.addAsLibraries(resolveStage.resolve("org.jbpm:jbpm-flow-builder").withTransitivity().asFile());
		archive.addAsLibraries(resolveStage.resolve("org.jbpm:jbpm-bpmn2").withTransitivity().asFile());
		archive.addAsLibraries(resolveStage.resolve("org.jbpm:jbpm-persistence-jpa").withTransitivity().asFile());
		archive.addAsLibraries(resolveStage.resolve("org.jbpm:jbpm-runtime-manager").withTransitivity().asFile());
		archive.addAsLibraries(resolveStage.resolve("org.quartz-scheduler:quartz").withTransitivity().asFile());
		archive.addAsLibraries(resolveStage.resolve("org.jbpm:jbpm-human-task-core").withTransitivity().asFile());
		archive.addAsLibraries(resolveStage.resolve("org.kie:kie-ci").withTransitivity().asFile());
		
		return archive;
	}

	public BaseJBPMServiceTest() {
		super();
	}

}