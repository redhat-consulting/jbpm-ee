package org.jbpm.ee.support;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

public class BeanUtils {

	public BeanUtils() {
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
		String setterName = "set"+StringUtils.capitalise(fieldName);
		try {
			Method setterMethod = target.getClass().getMethod(setterName, field.getType());
			setterMethod.invoke(target, objectSet);
		}
		catch(Exception e) {
			throw new RuntimeException("Unable to set field: "+field.getName()+" with setter: "+setterName);
		}
	}
}
