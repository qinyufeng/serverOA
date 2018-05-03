package com.jgonet.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

/**
 * 请求处理工具
 * @author xxy
 *
 */
public class RequestParemUtil {
	/**
	 * 从session作用域中取出用户信息
	 * @param request
	 * @return
	 */
	public static JSONObject getCustomer(HttpServletRequest request) {
		JSONObject json =  new JSONObject();
		// 获得session用户信息
		HttpSession session = request.getSession();
		if (session != null) {
			json = JSONObject.fromObject(session.getAttribute("USER_TOKEN"));
		}
		return json;
	}
	/**
	 * 从session作用域中取出用户手机号码
	 * @param request
	 * @return
	 */
	public static String sessionToPhoneNum(HttpServletRequest request) {
		JSONObject userInfoJson =  new JSONObject();
		String phoneNum = null;
		// 获得session用户信息
		HttpSession session = request.getSession();
		if (session != null) {
			userInfoJson = JSONObject.fromObject(session.getAttribute("USER_TOKEN"));
		}
		if(userInfoJson.containsKey("mobile")){
			phoneNum = userInfoJson.getString("mobile");
		}
		return phoneNum;
	}
	
}



