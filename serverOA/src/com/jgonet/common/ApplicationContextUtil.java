package com.jgonet.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 非spring管理对象使用spring管理的对象帮助类
 * 
 * @author husq
 * 
 */
public class ApplicationContextUtil implements ApplicationContextAware {
	private static ApplicationContext context = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	public static Object getBean(String name, Class className) {
		return context.getBean(name, className);
	}
}
