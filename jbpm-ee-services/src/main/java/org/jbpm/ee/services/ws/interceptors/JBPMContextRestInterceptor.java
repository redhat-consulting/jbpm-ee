package org.jbpm.ee.services.ws.interceptors;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
import org.jbpm.ee.services.model.KieReleaseId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/***
 * This interceptor introspects the message for REST and determines the identifier that can be used to re-associate the 
 * jBPM context to the request on the server side.  For example, if the request contains the process instance id, this can be
 * leveraged to determine the kie release id, which is needed to re-instantiate the classpath on the server side for the incoming request. 
 * 
 * @author bradsdavis
 *
 */
@Provider
@ServerInterceptor
public class JBPMContextRestInterceptor implements MessageBodyReaderInterceptor {
	private static final Logger LOG = LoggerFactory.getLogger(JBPMContextRestInterceptor.class);
	
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
		//only pretty print on debug.
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
	public Object read(MessageBodyReaderContext context) throws IOException, WebApplicationException {
		byte[] req = IOUtils.toByteArray(context.getInputStream());
		
		try {
			LOG.debug("Handling message...");
			ByteArrayInputStream bais = new ByteArrayInputStream(req);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(bais);
			prettyPrint(doc.getDocumentElement());
			KieReleaseId releaseId = XmlUtil.extractReleaseId(doc.getDocumentElement());
			if(releaseId != null) {
				LOG.debug("ReleaseId: "+releaseId.toString());
				classloaderService.bridgeClassloaderByReleaseId(releaseId);
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		ByteArrayInputStream newStream = new ByteArrayInputStream(req);
		context.setInputStream(newStream);
		
		for(String str : uri.getPathParameters().keySet()) {
			LOG.debug("Path Parameters: "+str);
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
			classloaderService.bridgeClassloaderByTaskId(taskId);
			return context.proceed();
		}
		
		
		
		return context.proceed();
	}

}
