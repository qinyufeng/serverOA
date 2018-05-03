package com.jgonet.context;

import java.util.Properties;

public class WebApplicationContext {
    private static WebApplicationContext webApplicationContext = new WebApplicationContext();

	private Properties properties;

	private Properties resultProperties;

	public static WebApplicationContext getInstance() {
		return webApplicationContext;
	}

	private WebApplicationContext() {
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public Properties getResultProperties() {
		return resultProperties;
	}

	public void setResultProperties(Properties resultProperties) {
		this.resultProperties = resultProperties;
	}

	public String getConfigProperty(String key) {
		return this.properties == null || key == null ? "" : this.properties.getProperty(key);
	}

	public String getResultCodeMessage(String key) {
		return this.resultProperties == null || key == null ? "" : this.resultProperties.getProperty(key);
	}

}
