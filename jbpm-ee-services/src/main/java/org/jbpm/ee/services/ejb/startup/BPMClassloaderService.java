package org.jbpm.ee.services.ejb.startup;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringUtils;
import org.jbpm.ee.services.ws.interceptors.BridgedClassloader;
import org.jbpm.ee.support.KieReleaseId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Startup
@Singleton
public class BPMClassloaderService {
	private static final Logger LOG = LoggerFactory.getLogger(BPMClassloaderService.class);

	private static final XPathFactory factory = XPathFactory.newInstance();

	@Inject
	private KnowledgeManagerBean knowledgeManager;
	
	public boolean bridgeClassloader(Element element) {
	    try {
			//first, look for the kie-release-id...
			KieReleaseId releaseId = extractReleaseId(element);
			if(releaseId != null) {
				bridgeClassloaderByReleaseId(releaseId);
				return true;
			}
			
			//if that doesn't exist, look for the processInstanceId or taskId
			Long processInstanceId = extractValueId(element, "//process-instance-id");
			if(processInstanceId != null) {
				bridgeClassloaderByProcessInstanceId(processInstanceId);
				return true;
			}
			
			Long taskInstanceId = extractValueId(element, "//task-id");
			if(taskInstanceId != null) {
				bridgeClassloaderByTaskId(taskInstanceId);
				return true;
			}
		} catch (Exception e) {
			LOG.error("Exception reading soap body.", e);
		}
	    
	    LOG.warn("No reference to bridge classloader.");
	    return false;
	}

	protected void bridgeClassloaderByProcessInstanceId(Long processInstanceId) {
		LOG.info("Bridging by process instance ID: "+processInstanceId);
		
		KieReleaseId kid = knowledgeManager.getReleaseIdByProcessId(processInstanceId);
		bridgeClassloaderByReleaseId(kid);
	}
		
	protected void bridgeClassloaderByTaskId(Long taskInstanceId) {
		LOG.info("Bridging by task ID: "+taskInstanceId);
		
		KieReleaseId kid = knowledgeManager.getReleaseIdByTaskId(taskInstanceId);
		bridgeClassloaderByReleaseId(kid);
	}
	
	protected void bridgeClassloaderByReleaseId(KieReleaseId releaseId) {
		LOG.info("Bridging by release id: "+releaseId);
		
		ClassLoader appLoader = Thread.currentThread().getContextClassLoader();
		ClassLoader bpmClassloader = knowledgeManager.getRuntimeEnvironment(releaseId).getClassLoader();
		
		BridgedClassloader bridged = new BridgedClassloader(appLoader, bpmClassloader);
		Thread.currentThread().setContextClassLoader(bridged);
	}
	
	protected KieReleaseId extractReleaseId(Element element) throws XPathExpressionException {
		XPath xpathObj = factory.newXPath();
		NodeList nodeList = (NodeList)xpathObj.compile("//kie-release-id").evaluate(element, XPathConstants.NODESET);

		if(nodeList.getLength() == 0) {
			return null;
		}

		KieReleaseId releaseId = new KieReleaseId();
		org.w3c.dom.Node node = nodeList.item(0);
		NodeList children = node.getChildNodes();
		
		for(int i=0, j=children.getLength(); i<j; i++) {
			Node child = children.item(i);
			if(StringUtils.equals("artifact-id", child.getNodeName())) {
				releaseId.setArtifactId(child.getNodeValue());
			}
			if(StringUtils.equals("group-id", child.getNodeName())) {
				releaseId.setArtifactId(child.getNodeValue());
			}
			if(StringUtils.equals("version", child.getNodeName())) {
				releaseId.setArtifactId(child.getNodeValue());
			}
		}
		
		return releaseId;
	}
	
	private Long extractValueId(Element element, String xpath) throws XPathExpressionException {
		XPath xpathObj = factory.newXPath();
		NodeList nodeList = (NodeList) xpathObj.compile(xpath).evaluate(element, XPathConstants.NODESET);
		
		for (int i = 0; i < nodeList.getLength(); i++) {
		    String val = nodeList.item(i).getFirstChild().getNodeValue();
		    LOG.info("Found value: "+val+" at XPATH: "+xpath);
		    return Long.parseLong(val);
		}
		
		return null;
	}
}
