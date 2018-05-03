package com.jgonet.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * <p>
 * Title: Framework
 * </p>
 * <p>
 * Description: Framework
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: xwtech.com
 * </p>
 * 
 * @author
 * @version 1.0 日期公用类
 */

public class DateTimeUtil {

	// 格式：2007年06月07日 12时12分12秒234毫秒
	private final static String[] FORMAT_CHINA = { "年", "月", "日", "时", "分", "秒", "毫秒" };
	// 格式：2007-06-07 12:12:12 234
	private final static String[] FORMAT_NORMAL = { "-", "-", "", ":", ":", "", "" };
	// 格式：2007/06/07 12:12:12 234
	private final static String[] FORMAT_DATATIME = { "/", "/", "", ":", ":", "", "" };
	// 格式：中文星期
	private final static String[] FORMAT_WEEK = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

	/**
	 * 获取今日年份
	 * 
	 * @return yyyy
	 */
	public static String getTodayYear() {
		return DateFormatUtils.format(new Date(), "yyyy");
	}

	/**
	 * 获取今日月份
	 * 
	 * @return MM
	 */
	public static String getTodayMonth() {
		return DateFormatUtils.format(new Date(), "MM");
	}

	/**
	 * 获取今日日期
	 * 
	 * @return dd
	 */
	public static String getTodayDay() {
		return DateFormatUtils.format(new Date(), "dd");
	}

	/**
	 * 获取短日月
	 * 
	 * @return MMdd
	 */
	public static String getTodayChar4() {
		return DateFormatUtils.format(new Date(), "MMdd");
	}

	/**
	 * 把日期转换成6字符的格式类型字符串，如200801
	 * 
	 * @param date
	 *            日期
	 * @return String
	 */
	public static String getChar8(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(date);
	}

	/**
	 * 返回年月
	 * 
	 * @return yyyyMM
	 */
	public static String getTodayChar6() {
		return DateFormatUtils.format(new Date(), "yyyyMM");
	}

	/**
	 * 返回年月日
	 * 
	 * @return yyyyMMdd
	 */
	public static String getTodayChar8() {
		return DateFormatUtils.format(new Date(), "yyyyMMdd");
	}

	/**
	 * 返回 年月日小时分
	 * 
	 * @return yyyyMMddHHmm
	 */
	public static String getTodayChar12() {
		return DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
	}

	/**
	 * 返回 年月日小时分秒
	 * 
	 * @return yyyyMMddHHmmss
	 */
	public static String getTodayChar14() {
		return DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
	}

	/**
	 * 返回 年月日小时分秒 毫秒
	 * 
	 * @return yyyyMMddHHmmssS
	 */
	public static String getTodayChar17() {
		String dateString = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssS");
		int length = dateString.length();

		if ( length < 17 ) {
			String endStr = dateString.substring(14, length);
			int len = endStr.length();
			for (int i = 0; i < 3 - len; i++) {
				endStr = "0" + endStr;
			}
			dateString = dateString.substring(0, 14) + endStr;
		}
		return dateString;
	}

	/**
	 * 返回中文日期格式 支持4、6、8、12、14、17位转换
	 * 
	 * @param charDateTime
	 *            长整型 CHAR
	 * @return 2007年12月12日 12时12分12秒 234毫秒
	 */
	public static String getFormatChina(String charDateTime) {
		return getFormatDateTime(charDateTime, "FORMAT_CHINA");
	}

	/**
	 * 返回标准日期格式 支持4、6、8、12、14、17位转换
	 * 
	 * @param charDateTime
	 *            长整型 CHAR
	 * @return 2007-12-12 12:12:12 234
	 */
	public static String getFormatNormal(String charDateTime) {
		return getFormatDateTime(charDateTime, "FORMAT_NORMAL");
	}

	/**
	 * 返回标准日期格式 支持4、6、8、12、14、17位转换
	 * 
	 * @param charDateTime
	 *            长整型 CHAR
	 * @return 2007/12/12 12:12:12 234
	 */
	public static String getFormatDateTime(String charDateTime) {
		return getFormatDateTime(charDateTime, "FORMAT_DATATIME");
	}

	/**
	 * 把日期格式转换为长整型格式
	 * 
	 * @param inputDateTime
	 * @return
	 */
	public static String getDateTimeToChar(String inputDateTime) {
		String strResult = "";
		if ( null == inputDateTime ) { return strResult = ""; }

		if ( ("".equals(inputDateTime.trim())) ) { return strResult = ""; }

		// 替换
		strResult = inputDateTime.replaceAll("年", "");
		strResult = strResult.replaceAll("月", "");
		strResult = strResult.replaceAll("日", "");
		strResult = strResult.replaceAll("时", "");
		strResult = strResult.replaceAll("分", "");
		strResult = strResult.replaceAll("秒", "");
		strResult = strResult.replaceAll("毫", "");
		strResult = strResult.replaceAll(" ", "");
		strResult = strResult.replaceAll("-", "");
		strResult = strResult.replaceAll("/", "");
		strResult = strResult.replaceAll(":", "");
		return strResult;

	}

	/**
	 * 对日期进行转换
	 * 
	 * @param charDateTime
	 * @param formatType
	 * @return
	 */
	public static String getFormatDateTime(String charDateTime, String formatType) {
		String strResult = "";
		if ( null == charDateTime ) { return strResult = ""; }

		if ( ("".equals(charDateTime.trim())) ) { return strResult = ""; }

		String[] FORMAT_CHAR = null;
		if ( formatType.equals("FORMAT_CHINA") ) {
			FORMAT_CHAR = FORMAT_CHINA;
		}
		else if ( formatType.equals("FORMAT_NORMAL") ) {
			FORMAT_CHAR = FORMAT_NORMAL;
		}
		else if ( formatType.equals("FORMAT_DATATIME") ) {
			FORMAT_CHAR = FORMAT_DATATIME;
		}
		else {
			return strResult = charDateTime;
		}

		// 去掉空格
		charDateTime = charDateTime.trim();

		if ( charDateTime.length() == 4 ) {
			// MMdd 转换 MM月dd日
			strResult = charDateTime.substring(0, 2) + FORMAT_CHAR[1] + charDateTime.substring(2, 4) + FORMAT_CHAR[2];
		}
		else if ( charDateTime.length() == 6 ) {
			// yyyyMM 转换 yyyy年MM月
			strResult = charDateTime.substring(0, 4) + FORMAT_CHAR[0] + charDateTime.substring(4, 6) + FORMAT_CHAR[1];
		}
		else if ( charDateTime.length() == 8 ) {
			// yyyyMMdd
			strResult = charDateTime.substring(0, 4) + FORMAT_CHAR[0] + charDateTime.substring(4, 6) + FORMAT_CHAR[1] + charDateTime.substring(6, 8) + FORMAT_CHAR[2];
		}
		else if ( charDateTime.length() == 12 ) {
			// yyyyMMddHHmm
			strResult = charDateTime.substring(0, 4) + FORMAT_CHAR[0] + charDateTime.substring(4, 6) + FORMAT_CHAR[1] + charDateTime.substring(6, 8) + FORMAT_CHAR[2] + " "
					+ charDateTime.substring(8, 10) + FORMAT_CHAR[3] + charDateTime.substring(10, 12) + FORMAT_CHAR[4];
		}
		else if ( charDateTime.length() == 14 ) {
			// yyyyMMddHHmmss
			strResult = charDateTime.substring(0, 4) + FORMAT_CHAR[0] + charDateTime.substring(4, 6) + FORMAT_CHAR[1] + charDateTime.substring(6, 8) + FORMAT_CHAR[2] + " "
					+ charDateTime.substring(8, 10) + FORMAT_CHAR[3] + charDateTime.substring(10, 12) + FORMAT_CHAR[4] + charDateTime.substring(12, 14) + FORMAT_CHAR[5];
		}
		else if ( charDateTime.length() == 17 ) {
			// yyyyMMddHHmmssS
			strResult = charDateTime.substring(0, 4) + FORMAT_CHAR[0] + charDateTime.substring(4, 6) + FORMAT_CHAR[1] + charDateTime.substring(6, 8) + FORMAT_CHAR[2] + " "
					+ charDateTime.substring(8, 10) + FORMAT_CHAR[3] + charDateTime.substring(10, 12) + FORMAT_CHAR[4] + charDateTime.substring(12, 14) + FORMAT_CHAR[5] + " "
					+ charDateTime.substring(14, 17) + FORMAT_CHAR[6];
		}
		else {
			strResult = charDateTime;
		}

		return strResult;
	}

	/**
	 * 将指定Date类型参数转换为指定的Oracle日期时间格式字符串
	 * 
	 * @return String
	 */
	public static String getOracleDate(Date inputDateTime) throws NullPointerException {
		if ( null == inputDateTime ) { return ""; }
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(inputDateTime);
	}

	/**
	 * 比对两个时间间隔
	 * 
	 * @param startDateTime
	 *            开始时间
	 * @param endDateTime
	 *            结束时间
	 * @param distanceType
	 *            计算间隔类型 天d 小时h 分钟m 秒s
	 * @return
	 */
	public static String getDistanceDT(String startDateTime, String endDateTime, String distanceType) {
		String strResult = "";
		long lngDistancVal = 0;
		try {
			SimpleDateFormat tempDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date startDate = tempDateFormat.parse(startDateTime);
			Date endDate = tempDateFormat.parse(endDateTime);
			if ( distanceType.equals("d") ) {
				lngDistancVal = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
			}
			else if ( distanceType.equals("h") ) {
				lngDistancVal = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60);
			}
			else if ( distanceType.equals("m") ) {
				lngDistancVal = (endDate.getTime() - startDate.getTime()) / (1000 * 60);
			}
			else if ( distanceType.equals("s") ) {
				lngDistancVal = (endDate.getTime() - startDate.getTime()) / (1000);
			}
			strResult = String.valueOf(lngDistancVal);
		}
		catch (Exception e) {
			strResult = "0";
		}
		return strResult;
	}

	public static long currentTimeMillis(String strDate) {
		long timeMillis = 0;
		if ( strDate == null || strDate.trim().length() != 10 ) { return timeMillis; }
		String year = strDate.trim().substring(0, 4);
		String month = strDate.trim().substring(5, 7);
		String day = strDate.trim().substring(8, 10);
		timeMillis = Long.parseLong(year + month + day + "000000");
		return timeMillis;
	}

	public static long currentDateMillis(String strDate) {
		long timeMillis = 0;
		try {
			String tem = dateFormat.format(dateFormat_3.parse(strDate));
			timeMillis = Long.parseLong(tem);
		}
		catch (ParseException e) {
		}
		return timeMillis;
	}

	public static long currentWeekMillis(int year, int month) {
		try {
			Calendar c = Calendar.getInstance();
			c.set(year, month - 1, 1);
			int tem = c.get(Calendar.DAY_OF_WEEK);
			return currentDateMillis(year + "-" + month + "-" + (10 - tem));
		}
		catch (Exception e) {
			return 0;
		}
	}

	public static long currentLastDayOfMonthMillis(int year, int month) {
		try {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.YEAR, year);
			c.set(Calendar.MONTH, month - 1);
			int tem = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			return currentDateMillis(year + "-" + month + "-" + tem);
		}
		catch (Exception e) {
			return 0;
		}
	}

	public static String getHour(String date) {
		try {
			return dateFormat_4.format(dateFormat.parse(date));
		}
		catch (ParseException e) {
			return "";
		}
	}

	public static String getDay(String date) {
		try {
			return dateFormat_5.format(dateFormat.parse(date));
		}
		catch (ParseException e) {
			return "";
		}
	}

	public static String formatDateToOther(String date) {
		try {
			return dateFormat_3.format(dateFormat.parse(date));
		}
		catch (ParseException e) {
			return "";
		}
	}

	public static String formatDateTo8(String date) {
		try {
			return dateFormat8.format(dateFormat_3.parse(date));
		}
		catch (ParseException e) {
			return "";
		}
	}

	public static String formatDateTo14(String date) {
		try {
			return dateFormat.format(dateFormat_3.parse(date));
		}
		catch (ParseException e) {
			return "";
		}
	}

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static SimpleDateFormat dateFormat8 = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat dateFormat9 = new SimpleDateFormat("yyyyMM");
	private static SimpleDateFormat dateFormat_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat dateFormat_3 = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat dateFormat_4 = new SimpleDateFormat("HH:mm:ss");
	private static SimpleDateFormat dateFormat_5 = new SimpleDateFormat("dd/MM");
	private static SimpleDateFormat dateFormat_6 = new SimpleDateFormat("yyyy年MM月dd日");
	
	public static String formatDateStrToOtherStr(String time) {
		String reTime = "";
		try {
			reTime = dateFormat_2.format(dateFormat.parse(time));
		}
		catch (ParseException e) {
		}
		return reTime;
	}
	public static String getYearAndMonth(String date){
		String s = "";
		try {
			s = dateFormat9.format(dateFormat_2.parse(date));
		} catch (ParseException e) {
			
		}
		return s;
	}
	public static long formatDate(String strDate) {
		long reDate = 0;
		try {
			Date date = dateFormat.parse(strDate);
			String tem = dateFormat.format(date);
			reDate = Long.parseLong(tem);
		}
		catch (ParseException e) {
		}
		return reDate;
	}

	/**
	 * 日期差计算 例如在某个日期增加几天后的日期 返回几天后日期
	 * 
	 * @param startDate
	 * @param addDate
	 * @return
	 */
	public static String getIncreaseDT(String startDate, int addDate) {
		String strResult = "0000-00-00";

		try {
			// 把字符串型日期转换为日期
			Calendar localDate = new GregorianCalendar();
			SimpleDateFormat tempDateFormat = new SimpleDateFormat("yyyyMMdd");
			Date tempDate = tempDateFormat.parse(startDate);
			localDate.setTime(tempDate);
			localDate.add(Calendar.DATE, addDate);

			String curyear = "" + localDate.get(Calendar.YEAR);
			int intmonth = localDate.get(Calendar.MONTH) + 1;
			String curmonth = "" + intmonth;
			String curday = "" + localDate.get(Calendar.DAY_OF_MONTH);

			if ( curmonth.length() == 1 ) {
				curmonth = "0" + curmonth;
			}
			if ( curday.length() == 1 ) {
				curday = "0" + curday;
			}
			strResult = curyear + "" + curmonth + "" + curday;
		}
		catch (Exception e) {
		}
		return strResult;
	}

	/**
	 * 获取本周的开始日期 （星期天的日期）20070201
	 * 
	 * @return
	 */
	public static String getWeekStartDate() {
		String strResult = "19000101";
		try {
			Calendar calendar = Calendar.getInstance();
			int intWeekNum = calendar.get(Calendar.DAY_OF_WEEK);
			intWeekNum = intWeekNum - 1;
			strResult = getIncreaseDT(getTodayChar8(), -intWeekNum);
		}
		catch (Exception ex) {
			strResult = "19000101";
		}
		return strResult;
	}

	/**
	 * 获取今天星期几 中文
	 * 
	 * @return
	 */
	public static String getWeekChina() {
		String strResult = " ";
		try {
			Calendar calendar = Calendar.getInstance();
			int intWeekNum = calendar.get(Calendar.DAY_OF_WEEK);
			intWeekNum = intWeekNum - 1;
			strResult = FORMAT_WEEK[intWeekNum];
		}
		catch (Exception ex) {
			strResult = " ";
		}
		return strResult;
	}

	public static String getBeforeMonth(int beforeMonth) {

		java.text.Format formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date todayDate = new Date();

		Calendar Cal = Calendar.getInstance();
		Cal.setTime(todayDate);
		Cal.add(Calendar.MONTH, beforeMonth);
		return formatter.format(Cal.getTime());
	}

	/**
	 * 判断终止日期是否大于开始日期
	 * 
	 * @param startDateTime
	 * @param endDateTime
	 * @return
	 */
	public static boolean getDistanceDate(String startDateTime, String endDateTime) {
		boolean blnResult = false;
		long lngDistancVal = 0;
		try {
			SimpleDateFormat tempDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date startDate = tempDateFormat.parse(startDateTime);
			Date endDate = tempDateFormat.parse(endDateTime);
			lngDistancVal = endDate.getTime() - startDate.getTime();
			blnResult = lngDistancVal > 0 ? true : false;
		}
		catch (Exception e) {
			blnResult = false;
			e.printStackTrace();
		}
		return blnResult;
	}

	/**
	 * 判断开始日期与终止日期是否相差几个月
	 * 
	 * @param startDateTime
	 * @param endDateTime
	 * @param num
	 *            相差几个月
	 * @return
	 */
	public static boolean getDisMonth(String startDateTime, String endDateTime, int num) {
		boolean blnResult = false;
		try {
			SimpleDateFormat tempDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date startDate = tempDateFormat.parse(startDateTime);
			Date endDate = tempDateFormat.parse(endDateTime);
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			cal.add(Calendar.MONTH, num);
			blnResult = cal.getTime().after(endDate);
		}
		catch (Exception e) {
			blnResult = false;
		}
		return blnResult;
	}

	/**
	 * 获取某个日期若干天后的时间
	 * 
	 * @param days
	 * @return
	 */
	public static String getStartDateNextTime(String startDateTime, int days) {
		try {
			SimpleDateFormat tempDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date startDate = tempDateFormat.parse(startDateTime);
			Calendar theCa = Calendar.getInstance();
			theCa.setTime(startDate);
			theCa.add(theCa.DATE, days);
			return DateFormatUtils.format(theCa.getTime(), "yyyyMMddHHmmss");
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取当前时间若干天后的时间
	 * 
	 * @param days
	 * @return
	 */
	public static String getNextTime(int days) {
		Calendar theCa = Calendar.getInstance();
		theCa.setTime(new Date());
		theCa.add(theCa.DATE, days);
		return DateFormatUtils.format(theCa.getTime(), "yyyyMMddHHmmss");
	}

	/**
	 * 获取前几个月
	 * 
	 * @param formatType
	 *            日期格式化格式
	 * @param month
	 *            月数
	 * @return
	 */
	public static String[] getMothBefore(String formatType, int month) {
		String dateStr[] = new String[2];
		// 使用默认时区和语言环境获得一个日历。
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MONTH, month);
		// 通过格式化输出日期
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(formatType);

		dateStr[0] = String.valueOf((Integer.valueOf((format.format(Calendar.getInstance().getTime()))) - 1));

		dateStr[1] = format.format(cal.getTime());

		return dateStr;
	}

	/**
	 * 获取现在和之前五个月的数据
	 */
	public static String[] getNowAndBeforeFiveMonthDate(String dataFormat) {
		String dateStr[] = new String[2];

		Calendar cal = Calendar.getInstance();// 使用默认时区和语言环境获得一个日历。
		cal.add(Calendar.MONTH, -5);// 取当前日期的前一天.

		// 通过格式化输出日期
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(dataFormat);

		dateStr[0] = format.format(Calendar.getInstance().getTime());

		dateStr[1] = format.format(cal.getTime());

		return dateStr;
	}

	public static String getYearMonth(Calendar cal, int addMonth) {
		cal.add(Calendar.MONTH, addMonth);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		return (year + (month < 10 ? "0" : "") + month);
	}

	public static String[] getLast6Months() {
		String[] months = new String[5];

		Calendar cal = Calendar.getInstance();

		months[0] = getYearMonth(cal, 0);
		months[1] = getYearMonth(cal, -1);
		months[2] = getYearMonth(cal, -1);
		months[3] = getYearMonth(cal, -1);
		months[4] = getYearMonth(cal, -1);

		return months;

	}

	/*
	 * 获取当天的上一个月
	 */
	public static String getBeforeMonthDay(){
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
	}
	/*
	 * 获取当天的上一个月
	 */
	public static String getBeforeMonth(){
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        return new SimpleDateFormat("yyyyMM").format(cal.getTime());
	}
	
	/*
	 * 获取当天的上一个月第一天
	 */
	public static String getBeforeMonthFirstDay(){
		Calendar calendar = Calendar.getInstance();
		String date = DateTimeUtil.getTodayChar8();
		calendar.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
		calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(4, 6))-2);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
	}
	/**
	 * 获取当前月天数
	 * @return
	 */
	public static int getCurrentMonthDays(){
		Calendar a = Calendar.getInstance();  
		a.set(Calendar.DATE, 1);//把日期设置为当月第一天  
		a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天  
		return a.get(Calendar.DATE);
	}
	
	
	/**
	 * 获取当前天前几个月日期
	 * @return
	 */
	public static String getBeforeNMonthFirstDate(String format,int month,int day){
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, month);
        cal.set(Calendar.DATE, day);
		return new SimpleDateFormat(format).format(cal.getTime());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -5);
        cal.set(Calendar.DATE, 1);
        System.out.println(new SimpleDateFormat("yyyyMMdd").format(cal.getTime()));
	}
	
	/**
	 * 查询前一天的最近6个月月份信息
	 * 
	 * @return
	 */
	public static String[] getQueryMonth() {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String[] query_ym = new String[5];

		String yearInfo = String.valueOf(cal.get(Calendar.YEAR));
		int monthTemp = cal.get(Calendar.MONTH) + 1;
		String monthInfo = String.valueOf(monthTemp);
		if (monthTemp < 10) {
			monthInfo = "0" + monthInfo;
		}
		query_ym[4] = yearInfo + monthInfo;

		for (int i = 4; i >= 0; i--) {
			cal.add(Calendar.MONTH, -1);
			int month = cal.get(Calendar.MONTH) + 1;
			String monthStr = String.valueOf(month);
			if (month < 10) {
				monthStr = "0" + monthStr;
			}

			query_ym[i] = String.valueOf(cal.get(Calendar.YEAR)) + monthStr;
		}
		return query_ym;
	}
	
	public static String getMonthDays2(String yearmonth) {
		int year = Integer.parseInt(yearmonth.substring(0, 4));
		int month = Integer.parseInt(yearmonth.substring(4, 6));
		
		Calendar cal = Calendar.getInstance();
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		String day = "01";
		if(year == yearNow && month == monthNow){
			int dayTemp = cal.get(Calendar.DATE)-1;
			String dayTempInfo = String.valueOf(dayTemp);
			if (dayTemp < 10) {
				dayTempInfo = "0" + dayTempInfo;
			}
			day = dayTempInfo+"日24时";
		}else{
			switch(month){
			case 1: case 3: case 5: case 7: case 8: case 10: case 12:
				day = "31日24时";break;
			case 4: case 6: case 9: case 11:
				day = "30日24时";break;
			case 2:
				if((year%400==0)||(year%4==0&&year%100!=0)){
					day = "29日24时";break;
				}else{
					day = "28日24时";break;
				}
			}
		}
		return day;
	}
	
	public static String getMonthDays(String yearmonth) {
		int year = Integer.parseInt(yearmonth.substring(0, 4));
		int month = Integer.parseInt(yearmonth.substring(4, 6));
		
		Calendar cal = Calendar.getInstance();
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		String day = "01";
		if(year == yearNow && month == monthNow){
			int dayTemp = cal.get(Calendar.DATE)-1;
			String dayTempInfo = String.valueOf(dayTemp);
			if (dayTemp < 10) {
				dayTempInfo = "0" + dayTempInfo;
			}
			day = dayTempInfo+"";
		}else{
			switch(month){
			case 1: case 3: case 5: case 7: case 8: case 10: case 12:
				day = "31";break;
			case 4: case 6: case 9: case 11:
				day = "30";break;
			case 2:
				if((year%400==0)||(year%4==0&&year%100!=0)){
					day = "29";break;
				}else{
					day = "28";break;
				}
			}
		}
		return day;
	}
	/**
	 * 获取当月最后几天
	 * @param days   最好几天
	 * @return
	 */
	public static List<String> getLastDays(int days) {
		Calendar calendar = Calendar.getInstance();
		List<String> list = new ArrayList<String>();
		for (int i = 1; i <= days; i++) {
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DATE, 1);
			calendar.add(Calendar.DATE, -i);
			Date theDate = calendar.getTime();
			String s = dateFormat8.format(theDate);
			list.add(s);
		}
		return list;
	}
	/**
	 * 将时间转成年月日格式
	 * @param date
	 * @return
	 */
	public static String getStringDate(Date date){
		if (date == null) {
			return "";
		}
		return dateFormat_6.format(date);
	}
}
