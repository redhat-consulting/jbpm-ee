package org.jbpm.ee.services.ws.interceptors;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Set;

import javax.naming.InitialContext;
import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.hibernate.service.classloading.spi.ClassLoaderService;
import org.jbpm.ee.services.ejb.startup.BPMClassloaderService;
import org.jbpm.ee.services.model.KieReleaseId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

/***
 * This interceptor introspects the message for SOAP and determines the identifier that can be used to re-associate the 
 * jBPM context to the request on the server side.  For example, if the request contains the process instance id, this can be
 * leveraged to determine the kie release id, which is needed to re-instantiate the classpath on the server side for the incoming request. 
 * 
 * @author bradsdavis
 *
 */
public class JBPMContextSoapInterceptor implements SOAPHandler<SOAPMessageContext> {

	private static final Logger LOG = LoggerFactory.getLogger(JBPMContextSoapInterceptor.class);
	private static final String CLASSLOADER_SERVICE = "java:app/jbpm-ee-services/BPMClassloaderService!org.jbpm.ee.services.ejb.startup.BPMClassloaderService";
	
	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		LOG.debug("Handling message...");
		try {
			InitialContext initialContext = new InitialContext();
			BPMClassloaderService classloaderService = (BPMClassloaderService)(initialContext.lookup(CLASSLOADER_SERVICE));
			
			Element body = context.getMessage().getSOAPBody();
			prettyPrint(body);
			
			
			KieReleaseId releaseId = XmlUtil.extractReleaseId(context.getMessage().getSOAPBody());
			
			if(releaseId != null) {
				classloaderService.bridgeClassloaderByReleaseId(releaseId);
			}
			
			//if that doesn't exist, look for the processInstanceId or taskId
			Long processInstanceId = XmlUtil.extractValueId(body, "//process-instance-id");
			if(processInstanceId != null) {
				classloaderService.bridgeClassloaderByProcessInstanceId(processInstanceId);
				return true;
			}
			
			Long taskInstanceId = XmlUtil.extractValueId(body, "//task-id");
			if(taskInstanceId != null) {
				classloaderService.bridgeClassloaderByTaskId(taskInstanceId);
				return true;
			}
			
			Long workItemId = XmlUtil.extractValueId(body, "//work-item-id");
			if(workItemId != null) {
				classloaderService.bridgeClassloaderByWorkItemId(workItemId);
				return true;
			}
			
			Long contentId = XmlUtil.extractValueId(body, "//content-id");
			if(contentId != null) {
				classloaderService.bridgeClassloaderByContentId(contentId);
				return true;
			}
			
			classloaderService.useThreadClassloader();
			
		} catch (Exception e) {
			LOG.error("Exception handing XML.", e);
		}
		
		return true;
	}
	
	
	
	public static final void prettyPrint(Element xml) throws Exception {
		//only log on debug.
		if(!LOG.isDebugEnabled()) {
			return;
		}
		
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        tf.transform(new DOMSource(xml), new StreamResult(out));
        LOG.debug("Request: \n"+out.toString());
    }


	@Override
	public boolean handleFault(SOAPMessageContext context) {
		LOG.debug("Handling fault...");
		return true;
	}

	@Override
	public void close(MessageContext context) {
		LOG.debug("Close...");
	}

	@Override
	public Set<QName> getHeaders() {
		LOG.debug("Get headers...");
		return null;
	}

}
