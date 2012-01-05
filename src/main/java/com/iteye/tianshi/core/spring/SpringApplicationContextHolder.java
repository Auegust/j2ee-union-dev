package com.iteye.tianshi.core.spring;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @author jiangzx@yahoo.com
 * @version 1.0
 */
public class SpringApplicationContextHolder implements ApplicationContextAware {
	
	private static ApplicationContext context;

	public void setApplicationContext(
			ApplicationContext applicationContext) {
		context = applicationContext;
	}

	public static void setAppContext(
			ApplicationContext applicationContext) {
		context = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return context;
	}

	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}
	
	public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
		return getApplicationContext().getBeansOfType(clazz);
	}
}
