package com.jgonet.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * 单点登录cookie信息
 * 
 * @deprecated:
 * @Company: www.xwtec.com **
 * @author chenfei@xwtec.cn 2015-8-20 下午5:25:06
 * @version 1.0.0
 */
public class SSOCookieUtil {
	/**
	 * 保存单点登录cookie
	 * @param paramResponse
	 * @param paramRequest
	 * @param mobile
	 * @author chenfei@xwtec.cn 2015-8-20
	 */
	public static void setSsoCookieForMyDomain(HttpServletResponse paramResponse,
			HttpServletRequest paramRequest, String mobile) {
		String desKey = ThirdDESToolsUtil.getInstace().getEncString(mobile);

		String str = (String) paramRequest.getAttribute("isAutoLogin");
		Cookie localCookie1 = new Cookie("SSOCookie", desKey);
		localCookie1.setPath("/");
		if ("true".equals(str))
			localCookie1.setMaxAge(1209600);
		if (paramResponse != null) {
			paramResponse.addHeader("P3P", "CP=CAO PSA OUR");
			paramResponse.addCookie(localCookie1);
		}
		Cookie localCookie2 = new Cookie("tokenid", MD5Util.getMD5(desKey + 1209600));
		localCookie2.setPath("/");
		if ("true".equals(str))
			localCookie2.setMaxAge(1209600);
		if (paramResponse == null)
			return;
		paramResponse.addHeader("P3P", "CP=CAO PSA OUR");
		paramResponse.addCookie(localCookie2);
	}

	public static String getSsoCookie(HttpServletRequest paramHttpServletRequest) {
		String so = getCookieValue(paramHttpServletRequest, "SSOCookie");
		String td = getCookieValue(paramHttpServletRequest, "tokenid");
		if (StringUtils.isBlank(so))
			return null;
		else if(MD5Util.getMD5(so + 1209600).equals(td)){
			return so;
		}
		return null;
	}

	/**
	 * 获取cookie里面数据
	 * @param paramHttpServletRequest
	 * @param paramString
	 * @return
	 * @author chenfei@xwtec.cn 2015-8-20
	 */
	public static String getCookieValue(HttpServletRequest paramHttpServletRequest,
			String paramString) {
		String str1 = null;
		String str2 = paramHttpServletRequest.getHeader("Cookie");
		if ((str2 == null) || (str2.equals("")))
			return str1;
		String[] arrayOfString = str2.split(";");
		for (int i = 0; i < arrayOfString.length; ++i) {
			arrayOfString[i] = arrayOfString[i].trim();
			if (arrayOfString[i].indexOf(paramString + "=") != 0)
				continue;
			str1 = arrayOfString[i].substring(arrayOfString[i].indexOf(paramString + "=")
					+ paramString.length() + 1);
			if (str1.startsWith("\""))
				str1 = str1.substring(1, str1.length());
			if (!(str1.endsWith("\"")))
				break;
			str1 = str1.substring(0, str1.length() - 1);
			break;
		}
		return str1;
	}

	/**
	 * 清除cookie里面数据
	 * 
	 * @param request
	 * @param response
	 * @param key
	 */
	public static void clearCookie(HttpServletRequest request, HttpServletResponse response,
			String key) {
		Cookie[] cc = request.getCookies();
		if (cc != null) {
			for (int i = 0; i < cc.length; i++) {
				if (cc[i].getName().equalsIgnoreCase(key)) {
					cc[i].setMaxAge(0);
					cc[i].setValue(null);
					response.addCookie(cc[i]);
				}
			}
		}
	}
}
