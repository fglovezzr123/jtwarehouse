package com.tojoy.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * JavaBean and map converter.
 * 
 */
public final class BeanMapUtils {

	public static Object toBean(Map<String, Object> map, Class<?> beanClass) throws Exception {    
        if (map == null)  
            return null;  
        Object obj = beanClass.newInstance();  
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);  
        return obj;  
    }    

	public static final Map<String, Object> toMap(Object bean){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(bean, new Object[0]);
					if (result != null) {
						returnMap.put(propertyName, result);
					} else {
						returnMap.put(propertyName, null);
					}
				}
			}
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}

		return returnMap;
	}
}