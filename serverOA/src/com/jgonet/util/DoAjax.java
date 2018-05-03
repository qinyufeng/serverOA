package com.jgonet.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class DoAjax {
	public static void doAjax(HttpServletResponse response, JSONObject obj,String cb) {
		try {
			
			response.setHeader("Cache-Control", "no-cache");
			//response.setCharacterEncoding("utf-8");
			response.setContentType("application/json;charset=utf-8");
			PrintWriter out=response.getWriter();
			if (cb != null) {
				if(cb.equals("payResult")){
					out.print("success");
				}else{
					StringBuffer sb = new StringBuffer(cb);
				    sb.append("(");
				    sb.append(obj.toString());
				    sb.append(")");
				    out.print(sb.toString());
				    System.out.println(sb.toString());
				}
			}else{
				out.print(obj.toString());
				System.out.println(obj.toString());
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void resultJson(HttpServletResponse response,Object obj) {
		try {
			String result = "";
			if(obj != null){
				JSONObject json = JSONObject.fromObject(obj);
				result = json.toString();
			}
			response.setContentType("application/json;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
			out.flush();
			out.close();
//			System.out.println("^^^^^^^^^^^^^^^^^^^"+result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}