package com.jgonet.util;

public class CityNumUtil {

	
	
	public static String CityNumUtil(String cityNum){
		
		
		if("A".equals(cityNum)){//郑州市
			return "11";
		}else if("B".equals(cityNum)){//开封市
			return "12";
		}else if("C".equals(cityNum)){//洛阳市
			return "13";
		}else if("D".equals(cityNum)){//平顶山
			return "14";
		}else if("E".equals(cityNum)){//安阳市
			return "18";
		}else if("F".equals(cityNum)){//鹤壁市
			return "16";
		}else if("G".equals(cityNum)){//新乡市
			return "17";
		}else if("H".equals(cityNum)){//焦作市
			return "15";
		}else if("U".equals(cityNum)){//济源市
			return "28";
		}else if("J".equals(cityNum)){//濮阳市
			return "19";
		}else if("K".equals(cityNum)){//许昌市
			return "20";
		}else if("L".equals(cityNum)){//漯河市
			return "21";
		}else if("M".equals(cityNum)){//三门峡
			return "22";
		}else if("N".equals(cityNum)){//商丘市
			return "24";
		}else if("Q".equals(cityNum)){//驻马店
			return "27";
		}else if("R".equals(cityNum)){//南阳市
			return "23";
		}else if("S".equals(cityNum)){//信阳市
			return "25";
		}else if("P".equals(cityNum)){//周口市
			return "26";
		}else{
			return "0";
		}
	}
}
