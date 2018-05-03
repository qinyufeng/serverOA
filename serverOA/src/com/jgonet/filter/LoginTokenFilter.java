package com.jgonet.filter;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class LoginTokenFilter {
	private final Logger logger = Logger.getLogger(LoginTokenFilter.class);
	public static String SSO__ON_OFF = "";


	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		System.out.println("Cookies====================" + request.getHeader("Cookie"));
		String reCookie = (String) request.getParameter("SSOCookie");
		System.out.println("url===================" + reCookie);

		String smsLoginMobile = reCookie;
		if (StringUtils.isEmpty(smsLoginMobile)) {
		}
		if (StringUtils.isEmpty(smsLoginMobile)) {
			//smsLoginMobile = SSOCookieUtil.getSsoCookie(request);
		}
		// 获取缓存中数据失败，从ecp获取用户信息
		// 写入服务端时间戳
	}


	/**
	 * 获取真实的客户端IP地址
	 * 
	 * @param request
	 * @return
	 */
	private String getRemoteHost(HttpServletRequest request) {
		String f_source_ip;
		// 获取真实的IP地址
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		f_source_ip = ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
		return f_source_ip;
	}
}