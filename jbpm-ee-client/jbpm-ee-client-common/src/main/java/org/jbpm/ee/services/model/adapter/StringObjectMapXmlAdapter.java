package org.jbpm.ee.services.model.adapter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.io.IOUtils;
import org.jbpm.services.task.impl.model.xml.adapter.JaxbStringObjectMap;
import org.jbpm.services.task.impl.model.xml.adapter.JaxbStringObjectMapEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Converts a Map to JAXB safe map.  Leverages the classloader previously set. 
 *
 */
public class StringObjectMapXmlAdapter extends XmlAdapter<JaxbStringObjectMap, Map<String, Object>> {

    private static final Logger logger = LoggerFactory.getLogger(StringObjectMapXmlAdapter.class);
    
    @Override
    public JaxbStringObjectMap marshal(Map<String, Object> map) throws Exception {
        if( map == null ) { 
            return new JaxbStringObjectMap();
        }
        JaxbStringObjectMap xmlMap = new JaxbStringObjectMap();
        for(Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            String key = entry.getKey();
            byte [] content = null;
            String className = null;
            if( value != null ) { 
                className = value.getClass().getName();
                content = serializeObject(value, key);
            } 
            
            JaxbStringObjectMapEntry xmlEntry = new JaxbStringObjectMapEntry(key, className, content);
            xmlMap.addEntry(xmlEntry);
        }
        return xmlMap;
    }
    
    public static byte [] serializeObject(Object obj, String name) { 
        Class<?> valueClass = obj.getClass();
        if( valueClass.getCanonicalName() == null ) { 
            logger.error("Unable to serialize '" + name + "' " +
                    "because serialization of weird classes is not supported: " + valueClass.getName());
            return null;
        }
        if( ! (obj  instanceof Serializable) ) { 
            logger.error("Unable to serialize '" + name + "' " +
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
            logger.error("Unable to serialize '" + name + "' " + "because of exception: " + ioe.getMessage(), ioe );
            return null;
        }
        return serializedBytes;
    }
    
    @Override
    public Map<String, Object> unmarshal(JaxbStringObjectMap xmlMap) {
        if( xmlMap == null ) { 
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        if(xmlMap.entries == null) {
        	return map;
        }
        
        for( JaxbStringObjectMapEntry xmlEntry : xmlMap.entries ) { 
            String key = xmlEntry.getKey();
            Object value = deserializeObject(xmlEntry.getBytes(), xmlEntry.getClassName(), key);
            map.put(key, value);
        }
        return map;
    }

    public static Object deserializeObject(byte [] objBytes, String className, String key) {
    	ClassLoader threadLoader = ClassloaderManager.get();
    	
    	if(threadLoader == null) {
    		//fall back on the application loader.
    		logger.info("Leveraging local classloader.");
    		threadLoader = StringObjectMapXmlAdapter.class.getClassLoader();
    	}
    	
        if( objBytes == null || objBytes.length == 0 ) { 
            return null;
        }
        try { 
        	logger.info("Classloader: "+threadLoader);
        	threadLoader.loadClass(className);
        } catch( ClassNotFoundException cnfe ) { 
            logger.error("Unable to deserialize '" + key + "' " + "because " + className + " is not on the classpath.", cnfe);
            return null;
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
        Object value;
        ObjectInputStream input = null;
        try {
            input = new ContextObjectInputStream(threadLoader, bais);
            value = input.readObject();
        } catch (IOException ioe) {
            logger.error("Unable to deserialize '" + key + "' because of exception: " + ioe.getMessage(), ioe);
            return null;
        } catch (ClassNotFoundException cnfe) {
            logger.error("Unable to deserialize '" + key + "' because " + className + " is not on the classpath.", cnfe);
            return null;
        }
        finally {
        	IOUtils.closeQuietly(input);
        }
        return value;
    }

}