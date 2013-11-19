package org.jbpm.ee.service.core;

import java.io.File;
import java.io.InputStream;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.osgi.spi.OSGiManifestBuilder;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseJBPMServiceTest {

	private static final Logger LOG = LoggerFactory.getLogger(BaseJBPMServiceTest.class);
	
	private static final File ENV_REF = new File("src/main/resources/environment.properties");
	private static final File USER_REF = new File("src/main/resources/usergroup.properties");
	
	private static final File PERSISTENCE_REF = new File("src/main/resources/META-INF/persistence.xml");
	private static final File BEAN_REF = new File("src/main/resources/META-INF/beans.xml");
	
	
	private static final File JMS_REF = new File("resources/jbpm-ee-jms.xml");
	private static final File WEB_REF = new File("src/main/webapp/WEB-INF/web.xml");
	private static final File DS_REF = new File("resources/jbpm-ee-ds.xml");
	
	@Deployment
	@OverProtocol("Servlet 3.0")
	public static WebArchive createDeployment() throws Exception {
		//MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");
	
		PomEquippedResolveStage resolveStage = Maven.resolver().loadPomFromFile("pom.xml");
		
		final WebArchive archive = ShrinkWrap.create(WebArchive.class, "jbpm-services.war");
		archive.addAsWebInfResource(ENV_REF, "classes/environment.properties");
		archive.addAsWebInfResource(USER_REF, "classes/usergroup.properties");
		archive.addAsWebInfResource(DS_REF, "jbossas-ds.xml");
		archive.addAsWebInfResource(JMS_REF, "hornetq-jms.xml");
		archive.addAsWebInfResource(PERSISTENCE_REF, "classes/META-INF/persistence.xml");
		
		archive.addAsWebInfResource(BEAN_REF, "beans.xml");
		archive.addAsWebInfResource(WEB_REF, "web.xml");
		
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
		
		archive.addPackages(true, "org.jbpm.ee");
		
		archive.addAsLibraries(resolveStage.resolve("org.jbpm.jbpm-ee:jbpm-ee-client").withTransitivity().asFile());
		archive.addAsLibraries(resolveStage.resolve("org.jbpm.jbpm-ee:jbpm-ee-ws-client").withoutTransitivity().asFile());
		archive.addAsLibraries(resolveStage.resolve("org.jbpm:jbpm-flow").withTransitivity().asFile());
		archive.addAsLibraries(resolveStage.resolve("org.jbpm:jbpm-flow-builder").withTransitivity().asFile());
		archive.addAsLibraries(resolveStage.resolve("org.jbpm:jbpm-bpmn2").withTransitivity().asFile());
		archive.addAsLibraries(resolveStage.resolve("org.jbpm:jbpm-persistence-jpa").withTransitivity().asFile());
		archive.addAsLibraries(resolveStage.resolve("org.jbpm:jbpm-runtime-manager").withTransitivity().asFile());
		archive.addAsLibraries(resolveStage.resolve("org.quartz-scheduler:quartz").withTransitivity().asFile());
		archive.addAsLibraries(resolveStage.resolve("org.jbpm:jbpm-human-task-core").withTransitivity().asFile());
		archive.addAsLibraries(resolveStage.resolve("org.kie:kie-ci").withTransitivity().asFile());
		archive.addAsLibraries(resolveStage.resolve("org.kie.remote:kie-services-client").withoutTransitivity().asFile());
		
		System.out.println(archive.toString(true));
		
		return archive;
	}

	public BaseJBPMServiceTest() {
		super();
	}

}