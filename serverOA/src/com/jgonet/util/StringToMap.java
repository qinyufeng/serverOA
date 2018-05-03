package com.jgonet.util;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class StringToMap {
	public static Map transStringToMap(String mapString){  
		  Map map = new HashMap();  
		  java.util.StringTokenizer items;  
		  for(StringTokenizer entrys = new StringTokenizer(mapString, "^");entrys.hasMoreTokens();   
		    map.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))  
		      items = new StringTokenizer(entrys.nextToken(), "'");  
		  return map;  
		}  
}
