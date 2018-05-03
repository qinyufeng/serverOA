package com.jgonet.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DZPUtil {
     /**
     * 判断当前日期是星期几
     * 
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
	 public static int dayForWeek(String pTime) throws Exception {
		  SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		  Calendar c = Calendar.getInstance();
		  c.setTime(format.parse(pTime));
		  int dayForWeek = 0;
		  if(c.get(Calendar.DAY_OF_WEEK) == 1){
		   dayForWeek = 7;
		  }else{
		   dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		  }
		  return dayForWeek;
	 }
	 
     /**
     * 判断当前日期是否是周五特权日
     * 
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
	 public static String isPrivilegeDay(String pTime) throws Exception {
		  if(DZPUtil.dayForWeek(pTime)==5){
			  return "1";
		  }else{
			  return "0";
		  }
	 }  	 
	 public static void main(String[] args) {
		 try {
			 System.out.println(DZPUtil.isPrivilegeDay("20160422"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
