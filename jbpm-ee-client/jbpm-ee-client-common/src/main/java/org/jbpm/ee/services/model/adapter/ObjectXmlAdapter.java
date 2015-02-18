package org.jbpm.ee.services.model.adapter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.io.IOUtils;
import org.jbpm.ee.services.model.JaxbObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Converts a Map to JAXB safe map.  Leverages the classloader previously set. 
 *
 */
public class ObjectXmlAdapter extends XmlAdapter<JaxbObject, Serializable> {

    private static final Logger logger = LoggerFactory.getLogger(ObjectXmlAdapter.class);
    
    @Override
    public JaxbObject marshal(Serializable obj) throws Exception {
        if( obj == null ) { 
            return new JaxbObject();
        }
        JaxbObject jax = new JaxbObject();
        
        byte [] content = null;
        String className = null;
        
        className = obj.getClass().getName();
        content = serializeObject(obj);
        
        jax.setClassName(className);
        jax.setValue(content);
        
        return jax;
    }
    
    public static byte [] serializeObject(Object obj) { 
        Class<?> valueClass = obj.getClass();
        if( valueClass.getCanonicalName() == null ) { 
            logger.error("Unable to serialize " +
                    "because serialization of classes is not supported: " + valueClass.getName());
            return null;
        }
        if( ! (obj  instanceof Serializable) ) { 
            logger.error("Unable to serialize "+
                    "because " + valueClass.getName() + " is an unserializable class" );
            return null;
        }
        
        byte [] serializedBytes = null;
        try { 
            ByteArrayOutputStream bais = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bais);
            out.writeObject(obj);
            serializedBytes = bais.toByteArray();
        } catch( IOException ioe ) { 
            logger.error("Unable to serialize  because of exception: " + ioe.getMessage(), ioe );
            return null;
        }
        return serializedBytes;
    }
    
    @Override
    public Serializable unmarshal(JaxbObject xmlObj) {
        if( xmlObj == null ) { 
            return null;
        }
        
        return (Serializable)deserializeObject(xmlObj.getValue(), xmlObj.getClassName());
    }

    public static Object deserializeObject(byte [] objBytes, String className) {
    	ClassLoader threadLoader = ClassloaderManager.get();
    	
    	if(threadLoader == null) {
    		logger.info("Leveraging local classloader.");
    		threadLoader = ObjectXmlAdapter.class.getClassLoader();
    	}
    	
        if( objBytes == null || objBytes.length == 0 ) { 
            return null;
        }
        try { 
        	logger.info("Classloader: "+threadLoader);
        	threadLoader.loadClass(className);
        } catch( ClassNotFoundException cnfe ) { 
            logger.error("Unable to deserialize because " + className + " is not on the classpath.", cnfe);
            return null;
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
        Object value;
        ObjectInputStream input = null;
        try {
            input = new ContextObjectInputStream(threadLoader, bais);
            value = input.readObject();
        } catch (IOException ioe) {
            logger.error("Unable to deserialize because of exception: " + ioe.getMessage(), ioe);
            return null;
        } catch (ClassNotFoundException cnfe) {
            logger.error("Unable to deserialize because " + className + " is not on the classpath.", cnfe);
            return null;
        }
        finally {
        	IOUtils.closeQuietly(input);
        }
        return value;
    }

}