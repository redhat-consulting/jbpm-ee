package org.jbpm.ee.services.ws.interceptors;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.spi.interception.MessageBodyReaderContext;
import org.jboss.resteasy.spi.interception.MessageBodyReaderInterceptor;
import org.jbpm.ee.services.ejb.startup.BPMClassloaderService;
import org.jbpm.ee.support.KieReleaseId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Provider
@ServerInterceptor
public class JBPMContextRestInterceptor implements MessageBodyReaderInterceptor {
	private static final Logger LOG = LoggerFactory.getLogger(JBPMContextRestInterceptor.class);
	private static final String CLASSLOADER_SERVICE = "java:global/jbpm-ee-services/BPMClassloaderService!org.jbpm.ee.services.ejb.startup.BPMClassloaderService";
	
	@Context HttpServletRequest servletRequest;
	@Context UriInfo uri;
	
	@Inject
	BPMClassloaderService classloaderService;
	
	private Long extractId(UriInfo info, String param) {
		String val = info.getPathParameters().getFirst(param);
		if(val != null) {
			return Long.parseLong(val);
		}
		
		return null;
	}
	
	public static void prettyPrint(Element xml) throws Exception {
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        tf.transform(new DOMSource(xml), new StreamResult(out));
        LOG.info("Request: \n"+out.toString());
    }

	@Override
	public Object read(MessageBodyReaderContext context) throws IOException, WebApplicationException {
		byte[] req = IOUtils.toByteArray(context.getInputStream());
		
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(req);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(bais);
			prettyPrint(doc.getDocumentElement());
			KieReleaseId releaseId = XmlUtil.extractReleaseId(doc.getDocumentElement());
			if(releaseId != null) {
				LOG.info("ReleaseId: "+releaseId.toString());
				classloaderService.bridgeClassloaderByReleaseId(releaseId);
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		ByteArrayInputStream newStream = new ByteArrayInputStream(req);
		context.setInputStream(newStream);
		
		for(String str : uri.getPathParameters().keySet()) {
			LOG.info("Path Parameters: "+str);
		}
	
		Long processInstanceId = extractId(uri, "processInstanceId");
		if(processInstanceId != null) {
			classloaderService.bridgeClassloaderByProcessInstanceId(processInstanceId);
			return context.proceed();
		}
		
		Long workItemId = extractId(uri, "workItemId");
		if(workItemId != null) {
			classloaderService.bridgeClassloaderByWorkItemId(workItemId);
			return context.proceed();
		}
		
		Long taskId = extractId(uri, "taskId");
		if(taskId != null) {
			classloaderService.bridgeClassloaderByTaskId(workItemId);
			return context.proceed();
		}
		
		
		
		return context.proceed();
	}

}
