package org.jbpm.ee.service.core;

import java.io.InputStream;

import javax.ejb.EJB;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jboss.arquillian.junit.Arquillian;
import org.jbpm.ee.services.ejb.startup.BPMClassloaderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Document;


@RunWith(Arquillian.class)
public class XmlToClassloaderTest extends BaseJBPMServiceTest {
	
	@EJB
	private BPMClassloaderService classloaderService;
	
	@Test
	public void testClassloadingFromXML() throws Exception {
		InputStream xmlFile = this.getClass().getResourceAsStream("/test-classloading.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);
		
		Assert.assertTrue(classloaderService.bridgeClassloader(doc.getDocumentElement()));
	}
}
