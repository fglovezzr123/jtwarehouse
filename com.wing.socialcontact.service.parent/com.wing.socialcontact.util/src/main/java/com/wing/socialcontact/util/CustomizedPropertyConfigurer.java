package com.wing.socialcontact.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 自定义配置加载类
 *
 * @author Devil
 * @date 2017/11/3 18:19
 */
public class CustomizedPropertyConfigurer extends PropertyPlaceholderConfigurer {

    private static Map<String, Object> ctxPropertiesMap;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        ctxPropertiesMap = new HashMap<>(5);
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }
    }

    /**
     * @author devil
     * @desicription: 获取配置值
     * @param name 配置文件key值
     * @date Created in 2017/11/3 18:25
     */
    public Object getProperty(String name) {
        return ctxPropertiesMap.get(name);
    }

    /**
     * @author devil
     * @desicription: 设置配置值
     * @date Created in 2017/11/3 18:25
     */
    public Object setProperty(String name,Object value) {
        return ctxPropertiesMap.put(name, value);
    }
}
