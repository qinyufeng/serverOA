package com.jgonet.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class RequestUtils {

	/*
	 * 设置当日有效
	 */
	public static long getCurrentDayExpireTime() {
		Calendar calendar = Calendar.getInstance();
		String date = DateTimeUtil.getTodayChar8();
		calendar.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
		calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(4, 6)) - 1);
		calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.substring(6, 8)) + 1);
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);

		Calendar ccalendar = Calendar.getInstance();// 当前时间
		ccalendar.setTime(new Date());
		return calendar.getTimeInMillis() - ccalendar.getTimeInMillis();
	}

	/*
	 * 设置当月有效
	 */
	public static long getCurrentMonthExpireTime() {
		Calendar calendar = Calendar.getInstance();
		String date = DateTimeUtil.getTodayChar8();
		calendar.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
		calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(4, 6)) - 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);

		Calendar ccalendar = Calendar.getInstance();// 当前时间
		ccalendar.setTime(new Date());
		return calendar.getTimeInMillis() - ccalendar.getTimeInMillis();
	}

	// 将分转换为元，并格式化保留两位小数
	public static String formartNumber(double number, int divide) {
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");
		if (divide == 0) {
			return "0";
		} else {
			return decimalFormat.format(number / divide);
		}
	}

	/**
	 * 保留两位小数
	 * @param number
	 * @return
	 */
	public static double flex2(double number) {
		BigDecimal b = new BigDecimal(number);
		double f = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return f;
	}
	
	/**
	 * 将元转为分并保留两位小数
	 * @param number
	 * @return
	 */
	public static double flex2fen(String number) {
		return flex2(number, 100);
	}

	/**
	 * 传入参数相除，并保留两位小数
	 * @param number
	 * @param dev
	 * @return
	 */
	public static double flex2(String number, int dev) {
		if(StringUtils.isEmpty(number))
			return 0.0D;
		BigDecimal b = new BigDecimal(number);
		double f = b.divide(new BigDecimal(dev)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return f;
	}
	
	/**
	 * 将元转为分并保留两位小数，返回String
	 * @param number
	 * @return
	 */
	public static String flex2fenStr(String number) {
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");
		double f = flex2(number, 100);
		return decimalFormat.format(f);
	}
	
	/**
	 * 传入参数相除，并保留两位小数，返回String
	 * @param number
	 * @param dev
	 * @return
	 */
	public static String flex2Str(String number, int dev) {
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");
		double f = flex2(number, dev);
		return decimalFormat.format(f);
	}

	
	public static String formatMobile(String mobile) {
		if(mobile.length() != 11){
			return mobile;
		}
		String m1 = mobile.substring(0, 3);
		String m2 = mobile.substring(3, 7);
		String m3 = mobile.substring(7, 11);
		return m1+" "+m2+" "+m3;
	}
	
	/**
	 * 字符串转double
	 * @param str
	 * @return
	 * @author chenfei@xwtec.cn 2015-9-14
	 */
	public static double doubleParse(String str){
		if(StringUtils.isBlank(str)){
			return 0.0D;
		}
		double d = 0.0D;
		try {
			d = Double.parseDouble(str);
		} catch (NumberFormatException e) {
			d = 0.0D;
		}
		return d;
	}
	
	public static void main(String[] args) {
		String s = "7892.52";
		double d = flex2(s, 100);
		
		System.out.println(flex2Str(s, 100));
	}
}
