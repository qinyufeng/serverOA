package com.jgonet.util;

import java.util.Calendar;

public class DetailRecordsUtil {
	/**
	 * 查询最近六个月份
	 * 
	 * @return
	 */
	public static String[] getQueryMonth() {

		Calendar cal = Calendar.getInstance();
		String[] query_ym = new String[6];
		for (int i = 5; i >= 0; i--) {
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
}
