package com.jgonet.util;

import java.util.Random;

public class DumplingsUtil {
	private final static double _first =0.001d;
	private final static double _two =0.03d;
	private final static double _three =0.15d;
	private final static double _four =0.2d;
	private final static double _five =0.3d;
	
	public static String Dumplings(){
		
		double randomNum=Math.random();

		if(randomNum>=0 && randomNum<=_first){
			Random r = new Random();
			int rs=r.nextInt(2);
			if(rs==0){
				return "1";
			}else{
				return "3";
			}
			
		}else if(randomNum>=_first && randomNum<=_first+_two){
			
			return "5";
		}else if(randomNum>=_first+_two && randomNum<=_first+_two+_three){
			
			return "0";
		}else if(randomNum>=_first+_two+_three && randomNum<=_first+_two+_three+_four){
			
			return "4";
		}else if(randomNum>=_first+_two+_three+_four && randomNum<=_first+_two+_three+_four+_five){
			Random r = new Random();
			int rs=r.nextInt(2);
			if(rs==0){
				return "2";
			}else{
				return "7";
			}
		}else{
			return "none";
		}
	}

}
