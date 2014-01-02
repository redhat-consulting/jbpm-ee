package org.jbpm.ee.support;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanUtils {
	private static final Logger LOG = LoggerFactory.getLogger(BeanUtils.class);
	
	private BeanUtils() {
		// seal
	}
	
	public static Object getObjectViaGetter(Field field, Object target) {
		String fieldName = field.getName();
		String getterName = "get"+StringUtils.capitalise(fieldName);
		try {
			Method getterMethod = target.getClass().getMethod(getterName);
			return getterMethod.invoke(target, null);
		}
		catch(Exception e) {
			throw new RuntimeException("Unable to set field: "+field.getName()+" with setter: "+getterName);
		}
	}
	
	public static void setObjectViaSetter(Field field, Object target, Object objectSet) {
		String fieldName = field.getName();
		String setterName = "set"+StringUtils.capitalize(fieldName);
		
		try {
			LOG.info("Calling setter: "+setterName+" on target: "+ReflectionToStringBuilder.toString(target)+ " with parameter: "+ReflectionToStringBuilder.toString(objectSet));
			Method setterMethod = target.getClass().getMethod(setterName, field.getType());
			setterMethod.invoke(target, objectSet);
		}
		catch(Exception e) {
			throw new RuntimeException("Unable to set field: "+field.getName()+" with setter: "+setterName);
		}
	}
}
