package com.jgonet.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import org.apache.log4j.Logger;

public class HbUtil {
	private final static Logger log = Logger.getLogger(HbUtil.class);
	private static int suffix = 0;
	private static String orderNum;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
	private  static  final int five = 5;
	private  static  final int two = 2;
	
	  static Random random = new Random();
	  static {
	        random.setSeed(System.currentTimeMillis());
	    }

    public static int generateHB(int max) {
    	int hb = 0;
    	if(max>=five){
    		int randNum = random.nextInt(3);
    		if(randNum==0){
    			hb = 5;
    		}else if(randNum==1){
    			hb = 2;
    		}else{
    			hb = 1;
    		}
    	}else if(max>=two&&max<five){
    		int randNum = random.nextInt(2);
    		if(randNum==0){
    			hb = 2;
    		}else{
    			hb = 1;
    		}
    	}else if(max==1){
    		hb = 1;
    	}
		return hb;  
   }
    
    public static String  autoGenerateId() {
		String orderNumStr = "";
		try {
			Random random = new Random();
			String randomStr = Integer.toString(random.nextInt(100000));
			String str = sdf.format(new Date())+randomStr;
			if (orderNum == null || !orderNum.equals(str)) {
				orderNum = str;
			} else {
				suffix++;
			}
			orderNumStr = str + Integer.toString(suffix);
		} catch (Exception e) {
			log.error("**********自动生成ID号异常*****************",e);
			e.printStackTrace();
			orderNumStr = UUID.randomUUID().toString();
		}
		return orderNumStr;
	}
}