package com.jgonet.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * http调用类。每次创建一个连接，不通过线程池管理
 * @author Administrator
 *
 */
public class ChargeHttpClient {

	public static  CookieStore cookieStore;
	
	public static final String UTF8 = "UTF-8";

	public static final String GBK = "GBK";
	
	private static final Logger log = Logger.getLogger(ChargeHttpClient.class);

	/** 设置编码格式 */
	private String encoding = UTF8;

	// 请求超时
	private int connectTimeout = 60000;

	// 读取超时
	private int readTimeout = 60000;

	private DefaultHttpClient httpclient = null;

	private void _init() {
		httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, this.connectTimeout);
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, this.readTimeout);
		DefaultHttpRequestRetryHandler dhr = new DefaultHttpRequestRetryHandler(3,true);
		httpclient.setHttpRequestRetryHandler(dhr);  
	}

	/**
	 * 默认构造方法 ，
	 * 
	 * @param maxConnector
	 */
	public ChargeHttpClient() {
		_init();
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	
	/**
	 * 通过post方式提交发送请求
	 * @param url请求url地址
	 * @param params请求参数
	 * @return String
	 * @throws Exception
	 */
	public String sendChargeRequestByPost(String url, List<NameValuePair> params) throws Exception {
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = null;
		HttpResponse response = null;
		HttpPost httpPost = null;
		try {
			httpPost = new HttpPost(url);
			HttpEntity entity = new UrlEncodedFormEntity(params, encoding);
			httpPost.setEntity(entity);
			response = httpclient.execute(httpPost);
			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), encoding));
			String lines = "";
			while ((lines = reader.readLine()) != null) {
				builder.append(lines);
			}
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (null != reader) {
				reader.close();
				reader = null;
			}
			if (null != response) {
				EntityUtils.consume(response.getEntity());
				response = null;
			}
			
			if (httpPost != null)
			{
				httpPost.releaseConnection();
			}
			
			if(httpclient.getConnectionManager()!=null){
				log.info("shutdown  ConnectionManager start");
				httpclient.getConnectionManager().shutdown();
				log.info("shutdown  ConnectionManager end");
			}
		}
		return builder.toString();
	}
	
	/**
	 * 通过get方式提交发送请求
	 * @param url 请求url地址
	 * @return Map<String,Object> 返回状态码和返回内容Map date:2014-5-28
	 * @author wangfengtong@xwtec.cn
	 */
	public Map<String, Object> sendRequestByGetUpgrades(String url) throws Exception {
		return sendRequestByGetUpgrades(url, "utf-8");
	}

	/**
	 * 通过post方式提交发送请求
	 * 
	 * @param url
	 *            请求url地址
	 * @param params
	 *            请求参数
	 * @return Map<String,Object> 返回状态码和返回内容Map date:2014-5-28
	 * @author wangfengtong@xwtec.cn
	 */
	public Map<String, Object> sendRequestByPostUpgrades(String url, List<NameValuePair> params) throws Exception {
		return sendRequestByPostUpgrades(url, params, "utf-8");
	}

	/**
	 * 方法描述:使用get方式从服务器转发请求
	 * 
	 * @param url
	 *            请求url地址
	 * @param encodeType
	 *            编码格式
	 * @return Map<String,Object> 返回状态码和返回内容Map date:May 21, 2013 add by:
	 *         houxu@xwtec.cn
	 */
	public Map<String, Object> sendRequestByGetUpgrades(String url, String encodeType) throws Exception {
		Map<String, Object> requestResult = new HashMap<String, Object>();
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = null;
		HttpResponse response = null;
		HttpGet httpGet = null;
		try {

			httpGet = new HttpGet(url);
			response = httpclient.execute(httpGet);

			// 返回状态码
			requestResult.put("statusCode", response.getStatusLine().getStatusCode());


			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), encodeType));

			String lines = "";
			while ((lines = reader.readLine()) != null) {
				builder.append(lines);
			}
		}
		catch (Exception e) {
			throw e;
		}
		finally {

			if (null != reader) {
				reader.close();
				reader = null;
			}

			if (null != response) {
				EntityUtils.consume(response.getEntity());
				response = null;
			}

			httpGet = null;
			httpclient.getConnectionManager().closeExpiredConnections();
		}


		// 返回内容
		requestResult.put("content", builder.toString());
		return requestResult;
	}

	/**
	 * 通过post方式提交发送请求
	 * 
	 * @param url
	 *            请求url地址
	 * @param params
	 *            请求参数
	 * @param encodingType
	 *            请求编码
	 * @return Map<String,Object> 返回状态码和返回内容Map date:2014-5-28
	 * @author wangfengtong@xwtec.cn
	 */
	public Map<String, Object> sendRequestByPostUpgrades(String url, List<NameValuePair> params, String encodingType) throws Exception {
		Map<String, Object> requestResult = new HashMap<String, Object>();
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = null;
		HttpResponse response = null;
		HttpPost httpPost = null;
		try {
			httpPost = new HttpPost(url);
			HttpEntity entity = new UrlEncodedFormEntity(params, encodingType);
			httpPost.setEntity(entity);

			response = httpclient.execute(httpPost);

			// 返回状态码
			requestResult.put("statusCode", response.getStatusLine().getStatusCode());
			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), encodingType));
			String lines = "";
			while ((lines = reader.readLine()) != null) {
				builder.append(lines);
			}
		}
		catch (Exception e) {
			throw e;
		}
		finally {

			if (null != reader) {
				reader.close();
				reader = null;
			}

			if (null != response) {
				EntityUtils.consume(response.getEntity());
				response = null;
			}

			httpPost = null;

			httpclient.getConnectionManager().closeExpiredConnections();
		}
		// 返回内容
		requestResult.put("content", builder.toString());
		return requestResult;
	}

	/**
	 * 方法描述:通过post方式提交发送请求
	 * 
	 * @param url
	 *            请求url地址
	 * @param strContent
	 *            请求参数
	 * @return 请求结果 date:2014-3-19 add by: liu_tao@xwtec.cn
	 */
	public String sendRequestByPost(String url, String strContent) throws Exception {
		return sendRequestByStream(url, strContent);
	}

	/**
	 * 通过流形式方式提交发送请求
	 * 
	 * @param url
	 *            请求url地址
	 * @param strContent
	 *            请求参数
	 * @return String
	 * @throws Exception
	 */
	public String sendRequestByStream(String url, String strContent) throws Exception {
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = null;
		HttpResponse response = null;
		HttpPost httpPost = null;
		try {

			httpPost = new HttpPost(url);
			HttpEntity entity = new StringEntity(strContent, encoding);
			httpPost.setEntity(entity);
			httpPost.setHeader("platform","android");
			response = httpclient.execute(httpPost);
			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), encoding));

			String lines = "";
			while ((lines = reader.readLine()) != null) {
				builder.append(lines);
			}
		}
		catch (Exception e) {

			throw e;
		}
		finally {

			if (null != reader) {
				reader.close();
				reader = null;
			}

			if (null != response) {
				EntityUtils.consume(response.getEntity());
				response = null;
			}

			httpPost = null;
			httpclient.getConnectionManager().closeExpiredConnections();
		}

		return builder.toString();
	}

	/**
	 * 通过Get方式提交发送请求
	 * 
	 * @param url
	 *            请求url地址
	 * @param strContent
	 *            请求参数
	 * @return String
	 * @throws Exception
	 */
	public String sendRequestByGet(String url) throws Exception {
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = null;
			HttpResponse response = null;
			HttpGet httpGet = null;
			try {
				httpGet = new HttpGet(url);
				response = httpclient.execute(httpGet);
				reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), encoding));

				String lines = "";
				while ((lines = reader.readLine()) != null) {
					builder.append(lines);
				}
			}
			catch (Exception e) {
				throw e;
			}
			finally {

				if (null != reader) {
					reader.close();
					reader = null;
				}

				if (null != response) {
					EntityUtils.consume(response.getEntity());
					response = null;
				}

				httpGet = null;
				httpclient.getConnectionManager().closeExpiredConnections();
			}
			return builder.toString();
		}

	public static JSONObject xmlStr2JsonObj(String xml) {
		if (null == xml || "".equals(xml)) {
			return null;
		}
		XMLSerializer xmlSerializer = new XMLSerializer();
		String jsonStr = xmlSerializer.read(xml).toString();
		if (getJSONType(jsonStr) == JSON_TYPE.JSON_TYPE_OBJECT) {
			return JSONObject.fromObject(jsonStr);
		}
		return null;
	}

	/**
	 * JSON类型
	 * 
	 * @author Administrator
	 */
	public static enum JSON_TYPE {
		JSON_TYPE_OBJECT, JSON_TYPE_ARRAY, JSON_TYPE_ERROR
	}

	/**
	 * 判断json字符串格式对应的JSON类型
	 * 
	 * @param str
	 * @return
	 */
	public static JSON_TYPE getJSONType(String str) {
		if (null == str)
			return JSON_TYPE.JSON_TYPE_ERROR;
		str = str.trim();
		if (str.startsWith("{") && str.endsWith("}")) {
			return JSON_TYPE.JSON_TYPE_OBJECT;
		}
		if (str.startsWith("[") && str.endsWith("]")) {
			return JSON_TYPE.JSON_TYPE_ARRAY;
		}
		return JSON_TYPE.JSON_TYPE_ERROR;
	}

public String sendRequestByGet(String url,String resUrl,String appId,String appKey) throws Exception {
	StringBuilder builder = new StringBuilder();
	BufferedReader reader = null;
	HttpResponse response = null;
	HttpGet httpGet = null;
	try {
		httpGet = new HttpGet(url);
		response = httpclient.execute(httpGet);
		reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), encoding));
		String lines = "";
		while ((lines = reader.readLine()) != null) {
			builder.append(lines);
		}
	}
	catch (Exception e) {
		throw e;
	}
	finally {

		if (null != reader) {
			reader.close();
			reader = null;
		}

		if (null != response) {
			EntityUtils.consume(response.getEntity());
			response = null;
		}

		httpGet = null;
		httpclient.getConnectionManager().closeExpiredConnections();
	}
	return builder.toString();
}


}
