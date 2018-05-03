package com.jgonet.listener;

import java.io.File;
import java.net.URL;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.jgonet.context.WebApplicationContext;
import com.jgonet.util.PropertiesUtils;
import com.jgonet.util.StringUtil;
import com.xwtech.xwecp.service.logic.client.XWECPLIClient;

public class ApplicationListener implements ServletContextListener {
	public static String SSO_PAHT;

	public static String SSO_CHANNEL;

	public static String SSO_PWD;

	public static String SSO_WAY;

	private static final Logger logger = Logger.getLogger(ApplicationListener.class);

	/** redis客户端 */

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		// 加载config.properties配置文件
		Properties properties = PropertiesUtils.loadProperties("config.properties");
		if (properties != null) {
			WebApplicationContext.getInstance().setProperties(properties);
			// 建立ECP上下文环境

			Properties props = new Properties();

			String xwecpURL = properties.getProperty("platform.url");
			String clientChannel = properties.getProperty("client.channel");
			String clientUser = properties.getProperty("platform.user");
			String clientPassword = properties.getProperty("platform.password");
			String connectionTimeout = properties.getProperty("connection.timeout");
			String soTimeout = properties.getProperty("connection.soTimeout");

			if (!StringUtil.isNull(xwecpURL) && !StringUtil.isNull(clientChannel) && !StringUtil.isNull(clientUser)
					&& !StringUtil.isNull(clientPassword)) {
				props.put("platform.url", xwecpURL);
				props.put("client.channel", clientChannel);
				props.put("platform.user", clientUser);
				props.put("platform.password", clientPassword);
				props.put("connection.timeout", connectionTimeout);
				props.put("connection.soTimeout", soTimeout);

				XWECPLIClient client = XWECPLIClient.createInstance(props);

				logger.info("初始化XWECPLIClient成功！");
			} else {
				logger.error("初始化XWECPLIClient失败！请检查以下ECP初始化参数是否配置：boss.xwecpURL|boss.clientChannel|boss.clientUser|boss.clientPassword");
			}
			URL realPath = this.getClass().getClassLoader().getResource("server.xml");
			// properties.getProperty("redisFileRealPath");
			// redis配置文件
			File redisConfig = new File(realPath.getPath());
			if (redisConfig.exists()) {
				logger.info("redisFileRealPath isExists !");
			} else {
				logger.info("redisFileRealPath isNotExists !");
			}

			// 设置sso标记
			// LoginTokenFilter.SSO__ON_OFF = properties
			// .getProperty("SSO_ON_OFF");
			// if ("ON".equalsIgnoreCase(LoginTokenFilter.SSO__ON_OFF)) {
			// // logger.info("----before OAMUtil.initSso()!!!");
			// // OAMUtil.initSso();
			// // logger.info("----after OAMUtil.initSso()!!!");
			// // 新sso初始化
			// SSO_PAHT = properties.getProperty("sso.path");
			// SSO_CHANNEL = properties.getProperty("sso.channel");
			// SSO_PWD = properties.getProperty("sso.pwd");
			// SSO_WAY = properties.getProperty("sso.way");
			// SsoClient.initSsoClient(SSO_PAHT, SSO_CHANNEL, SSO_PWD, SSO_WAY);
			// }

		}

		URL realPath = this.getClass().getClassLoader().getResource("redis_config.xml");
		// redis配置文件
		File redisConfig = new File(realPath.getPath());
		if (!redisConfig.exists()) {
			logger.error("通用缓存服务器配置文件：" + redisConfig.getAbsolutePath() + "不存在！请确认配置文件.");
		}
		// 初始化新缓存服务管理器
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {}
}
