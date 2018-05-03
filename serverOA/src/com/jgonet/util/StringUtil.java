package com.jgonet.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * 判断传入的字符串如果为null则返回"",否则返回其本身
	 * 
	 * @param string
	 * @param instant
	 * @return String
	 */
	public static String convertNull(String string, String instant) {
		return isNull(string) ? instant : string;
	}

	/**
	 * {@link #convertNull(String, String)}
	 * 
	 * @param string
	 * @return String
	 */
	public static String convertNull(String string) {
		return convertNull(string, "");
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param obj
	 *            Object
	 * @return boolean 空返回true,非空返回false
	 */
	public static boolean isNull(Object obj) {
		return (null == obj) ? true : false;
	}

	/**
	 * Description:判断字段空null <br>
	 * 
	 * @param s
	 * @return boolean
	 */
	public static boolean isNull(String s) {
		if ( s == null || "".equals(s.trim()) ) { return true; }

		return false;
	}

	public static boolean isFullNull(String s) {
		if ( s == null || "".equals(s.trim()) || "null".equals(s) ) { return true; }

		return false;
	}
	
	public static boolean isFullAllNull(String s) {
		if ( s == null || "".equals(s.trim()) || "null".equals(s) || "(null)".equals(s)) { 
				return true; 
			}else{
				return false;
			}
	}
	public static boolean isFullAllNullForIos(String s) {
		if ( s == null || "".equals(s.trim()) || "null".equals(s) || "(null)".equals(s) || s.length()<11) { 
				return true; 
			}else{
				return false;
			}
	}
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断输入的字符串是否满足要求
	 * 
	 * @param str
	 *            匹配的字符串
	 * @param regex
	 *            正则表达式字符串
	 * @return
	 */
	public static boolean replace(String str, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	public static int byteLength(String s) {
		int res = 0;
		if ( !isNull(s) ) {
			res = s.getBytes().length;
		}
		return res;
	}

//	public static String stringLimit(String s, int len) {
//		if ( !isFullNull(s) ) {
//			byte[] b = s.getBytes();
//			int res = b.length;
//			if ( res > len ) {
//				return new String(b, 0, len);
//			}
//			else {
//				return s;
//			}
//		}
//		return null;
//	}
	public static String stringLimit(String s, int len) {
		if (!isFullNull(s)) {
			byte[] b;
			try {
				b = s.getBytes("UTF-8");
				int res = b.length;
				if (res > len) {
					return new String(b, 0, len-1);
				} else {
					return s;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		}
		return null;
	}
	/**
	 * 适用于服务端对客户端版本判断
	 * 
	 * @param str1
	 *            前一个版本
	 * @param str2
	 *            后一个版本
	 * @return 小于0,前者小于后者 ; 0:相等 ;大于0,前者大于后者
	 * 
	 * @author NZF 2014年12月9日
	 */
	public static int compareTwoStr(String str1, String str2) {
		str1 = str1 == null ? "" : str1;
		str2 = str2 == null ? "" : str2;
		if (StringUtil.isFullNull(str1) && !StringUtil.isFullNull(str2)) {
			return -1;
		} else if (!StringUtil.isFullNull(str1) && StringUtil.isFullNull(str2)) {
			return 1;
		} else if (str1.equals(str2)) {
			return 0;
		} else {
			return str1.compareTo(str2);
		}
	}

	/**
	 * 适用于服务端判断版本升级,比较 一个数是否在区间返回内
	 * 
	 * @param str1
	 *            需比较的参数,不能为空,如果为空,则默认返回不再区间内
	 * @param str2
	 *            区间下限 
	 * @param str3
	 *            区间上限
	 * @return  1:无需升级(等于区间上限值);2:建议升级(不包括区间的两个极限值);3:强制升级(包括区间下限值)
	 * 
	 * @author NZF 2014年12月9日
	 */
	public static int compareRangeStr(String str1, String str2, String str3) {
		str1 = str1 == null ? "" : str1;
		str2 = str2 == null ? "" : str2;
		str3 = str3 == null ? "" : str3;
		//当前版本号如果为空,即str1为空,则默认无需升级
		if (StringUtil.isFullNull(str1)) {
			return 1;
		}else{
			//当前版本大于等于区间上限,则是无需升级
			if(compareTwoStr(str1,str3)>=0){
				return 1;
			}
			//当前版本小于等于区间下限,则是强制升级
			if(compareTwoStr(str1,str2)<=0){
				return 3;
			}
			//其他情况都是建议升级
			return 2;
		}
	}

}
