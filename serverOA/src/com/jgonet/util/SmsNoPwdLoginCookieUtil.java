package com.jgonet.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SmsNoPwdLoginCookieUtil {
    /**
     * 把发送短息验证码和失败次数加密保存到cookie【加密方法和密码的一致】
     *
     * @param request
     * @param response
     * @param key
     */
    public static void writeSmsMobileCookie(HttpServletRequest request, HttpServletResponse response, String key, String mobile) {
        Cookie cookie2 = new Cookie(key, mobile);
        cookie2.setMaxAge(Integer.MAX_VALUE);
        cookie2.setPath("/");
        //cookie2.setDomain(".js.10086.cn");
        response.addCookie(cookie2);
    }

    /**
     * 从cookie获取短息验证码和失败次数
     *
     * @param request
     * @param response
     * @param key
     */
    public static String getCookie(HttpServletRequest request, HttpServletResponse response, String key) {
        Cookie[] cc = request.getCookies();
        if (cc != null) {
            for (int i = 0; i < cc.length; i++) {
                if (cc[i].getName().equalsIgnoreCase(key)) {
                    return cc[i].getValue();
                }
            }
        }
        return null;
    }

    /**
     * 清除cookie里面数据
     *
     * @param request
     * @param response
     * @param key
     */
    public static void clearCookie(HttpServletRequest request, HttpServletResponse response, String key) {
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
