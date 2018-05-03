package com.jgonet.util;

import java.io.UnsupportedEncodingException;

import com.wee.security.suport.a;

/**
 * 3DES加密
 *
 */
public class ThirdDESToolsUtil {
	
	private final static String key = "72834344293405015544332211934234";// 分配的密钥
	
	private final static String traderandom = "8141";//随机数 只能是数字
	
	public static  ThirdDESToolsUtil instance;
	
	/**
	 * 单例
	 * @return
	 */
	public static ThirdDESToolsUtil   getInstace()
	{
		if(instance == null)
		{
			instance = new ThirdDESToolsUtil();
		}
		return instance;
	}


	public static final byte[] convertHEXStr2Bytes(String srcBuff) {
		byte tmp = 0;
		int i = 0, j = 0;
		String subBuff = "";
		byte[] btResult = null;
		if (srcBuff == null)
			return null;
		btResult = new byte[srcBuff.length() / 2];
		for (i = 0, j = 0; i < srcBuff.length(); i += 2, j++) {
			subBuff = srcBuff.substring(i, i + 2);
			tmp = (byte) Integer.parseInt(subBuff, 16);
			btResult[j] = tmp;
		}

		return btResult;
	}
	
	/**
	 * 加密String明文输入,String密文输出
	 */
	@SuppressWarnings("static-access")
	public String getEncString(String strMing) {
		String eticketpwd = "";
		try {
			// 加密
			byte[] encBts = a.a(ThirdDESToolsUtil.getInstace().convertHEXStr2Bytes(key), traderandom.getBytes("utf-8"),strMing.getBytes("utf-8"));
			eticketpwd = convertBytes2HEXStr(encBts);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return eticketpwd;
	}
	
	/**
	 * 解密 以String密文输入,String明文输出
	 * @param strMi
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String getDesString(String strMi) {
		String str = "";
		try {
			// 解密
			byte[] btsPWD = a.b(ThirdDESToolsUtil.getInstace().convertHEXStr2Bytes(key), traderandom.getBytes("utf-8"),convertHEXStr2Bytes(strMi));
			str = new String(btsPWD, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 功能描述： 加解密相关工具类 公司：拓维信息 部门：美盛 姓名：jiangwei 时间：2011年3月
	 * 
	 * @author javy
	 * 
	 */
	public static String convertBytes2HEXStr(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int i = 0; i < b.length; i++) {
			stmp = Integer.toHexString(b[i] & 0xFF);
			if (stmp.length() == 1) {
				hs += "0" + stmp;
			} else {
				hs += stmp;
			}
		}
		return hs.toUpperCase();
	}
	
	public static void main(String[] args) throws Exception {
		
		//=================加解密处理
		String passwdTxt = "13550016267";//手机号码|失败次数
		// 加密
		String eticketpwd = ThirdDESToolsUtil.getInstace().getEncString(passwdTxt);
		System.out.println("加密密文[SmsNoPwdLoginCookie=" + eticketpwd + "]");
		// 解密
		String btsPWD = ThirdDESToolsUtil.getInstace().getDesString(eticketpwd);
		System.out.println("解密明文[" + btsPWD + "]");
		
		System.out.println(passwdTxt+"免登陆cookie[SmsNoPwdLoginCookie=" + eticketpwd + "]");

		/*String tt = "杨幂杨幂杨qd幂杨qd幂杨幂杨幂ASW杨幂杨幂幂杨幂杨幂杨幂";
		System.out.println(tt.length());
		String name = ThirdDESToolsUtil.getInstace().getEncString(tt);
		System.out.println("name="+name.length());
		System.out.println(ThirdDESToolsUtil.getInstace().getDesString(name));
		System.out.println("===="+ThirdDESToolsUtil.getInstace().getEncString(null));*/
	}
}
