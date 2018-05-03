package com.jgonet.util.config;

import net.sf.json.JSONObject;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 *  @ClassName CustomPropertyPlaceholderConfigurer
 *  @Description TODO
 *
 *	@author xujiancheng@xwtec.cn 
 *  @date 2016年3月4日 下午4:35:45
 * 	@version 1.0.0
 */

public class CustomPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	  protected void processProperties(ConfigurableListableBeanFactory beanFactory,  
	            java.util.Properties props)throws BeansException {  
	  
	        super.processProperties(beanFactory, props);  
	        //load properties to CommonsConstants.GLOBAL_CONFIG_PROPERTIES
	        for (Object key : props.keySet()) {  
	            String keyStr = key.toString();  
	            String value = props.getProperty(keyStr);  
	            WebConfig.GLOBAL_CONFIG_PROPERTIES.put(keyStr, value);  
	        }  
	        System.out.println(JSONObject.fromObject(WebConfig.GLOBAL_CONFIG_PROPERTIES).toString());
	        System.out.println("end");
	        
	    }
}
