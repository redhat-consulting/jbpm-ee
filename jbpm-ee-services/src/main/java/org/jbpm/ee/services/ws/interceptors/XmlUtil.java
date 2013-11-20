package org.jbpm.ee.services.ws.interceptors;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringUtils;
import org.jbpm.ee.support.KieReleaseId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlUtil {
	private static final XPathFactory factory = XPathFactory.newInstance();
	private static final Logger LOG = LoggerFactory.getLogger(XmlUtil.class);
	
	public static KieReleaseId extractReleaseId(Element element) throws XPathExpressionException {
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
			String nodeName = child.getNodeName() != null ? child.getNodeName() : child.getLocalName(); 

			if(StringUtils.equals("group-id", nodeName)) {
				releaseId.setGroupId(child.getTextContent());
			}
			else if(StringUtils.equals("artifact-id", nodeName)) {
				releaseId.setArtifactId(child.getTextContent());
			}
			else if(StringUtils.equals("version", nodeName)) {
				releaseId.setVersion(child.getTextContent());
			}
		}
		
		return releaseId;
	}
	
	public static Long extractValueId(Element element, String xpath) throws XPathExpressionException {
		XPath xpathObj = factory.newXPath();
		NodeList nodeList = (NodeList) xpathObj.compile(xpath).evaluate(element, XPathConstants.NODESET);
		
		for (int i = 0; i < nodeList.getLength(); i++) {
		    String val = nodeList.item(i).getFirstChild().getNodeValue();
		    return Long.parseLong(val);
		}
		
		return null;
	}
}
