package com.jgonet.util;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * JSON工具类
 * @author zhumeng
 *
 */
@SuppressWarnings("unchecked")
public class JSONUtil {
	/**
	 * 转换object为JSON字符串
	 * @param obj
	 * @return
	 */
	public static String toJSONString(Object obj){
		return JSONObject.fromObject(obj).toString();
	}
	
	/**
	 * 将jsonArray转换为List<Bean>
	 * @param arr
	 * @param objClass
	 * @return
	 */
	public static List toBeanList(Object arr , Class objClass){
		return (List)JSONArray.toCollection( JSONArray.fromObject(arr),objClass);
	}
	
	/**
	 * 转换object为JSON字符串
	 * @param obj
	 * @return
	 */
	public static String toJSONArrayString(Collection c){
		return JSONArray.fromObject(c).toString();
	}
	
	/**
	 * 
	 * @param jsonStr
	 * @return
	 */
    public static Map<String, Object> parseJSON2Map(String jsonStr){  
        Map<String, Object> map = new HashMap<String, Object>();  
        //最外层解析  
        JSONObject json = JSONObject.fromObject(jsonStr);  
        for(Object k : json.keySet()){  
            Object v = json.get(k);   
            //如果内层还是数组的话，继续解析  
            if(v instanceof JSONArray){  
                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();  
                Iterator<JSONObject> it = ((JSONArray)v).iterator();  
                while(it.hasNext()){  
                    JSONObject json2 = it.next();  
                    list.add(parseJSON2Map(json2.toString()));  
                }  
                map.put(k.toString(), list);  
            } else {  
                map.put(k.toString(), v);  
            }  
        }  
        return map;  
    }
    /**
	 * 将json格式的字符串解析成Map对象 <li> 
	 * json格式：{'0.3':1000,'4':100,'10':50,'30':20,'55.7':10}
	 */
	   public static HashMap<Double, Integer> toHashMap(String str){  
	       HashMap<Double, Integer> data = new HashMap<Double, Integer>();  
	       // 将json字符串转换成jsonObject  
	       JSONObject jsonObject = JSONObject.fromObject(str);  
	       Iterator it = jsonObject.keys();  
	       // 遍历jsonObject数据，添加到Map对象  
	       while (it.hasNext()){  
	    	   String key = it.next().toString();  
	    	   Integer value = Integer.valueOf(jsonObject.get(key).toString());  
	           data.put(Double.valueOf(key), value);  
	       }  
	       return data;  
	   }  
	   
	   public static List<Integer> JSONToList(String str){
		   JSONArray jsonArray = JSONArray.fromObject(str); 
	        List<Integer>list = (List<Integer>) JSONArray.toCollection(jsonArray);
			return list;
	    }
}
