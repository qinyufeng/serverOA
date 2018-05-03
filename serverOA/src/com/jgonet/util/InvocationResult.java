package com.jgonet.util;

import com.xwtech.xwecp.service.BaseServiceInvocationResult;

/**
 * Created by IntelliJ IDEA.
 * User: maofw
 * Date: 12-4-17
 * Time: 下午4:11
 * To change this template use File | Settings | File Templates.
 */
public class InvocationResult {
    /**
     * 逻辑接口成功编码
     */
    public static final String LOGIC_SUCCESS = "0";

    /**
     * 逻辑接口失败编码
     */
    public static final String LOGIC_FAILURE = "1";
    
    /**
     * 逻辑接口查询信息不存在
     */
    public static final String LOGIC_EXISTED = "2002";

    /**
     * 逻辑接口返回提示
     */
    public static final String LOGIC_INFO = "2";

    //客户端免登录cookie key
    public static final String SMS_LOGIN_COOKIE = "SmsNoPwdLoginCookie";

    public static boolean isResultSuccess(BaseServiceInvocationResult result) {
        if (result != null) {
            if (LOGIC_SUCCESS.equals(result.getResultCode()) || LOGIC_INFO.equals(result.getResultCode())) {
                return true;
            }
        }

        return false;
    }
}
