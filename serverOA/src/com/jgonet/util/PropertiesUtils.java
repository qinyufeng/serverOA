package com.jgonet.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class PropertiesUtils {
    private static final Logger logger = Logger.getLogger(PropertiesUtils.class);
    public static final String PROPERTIES_FILE_PATH = "config.properties";

    public static Properties loadProperties() {
        return loadProperties(PROPERTIES_FILE_PATH);
    }

    public static Properties loadProperties(String fileStr) {
        Properties properties = null;
        if (!StringUtil.isNull(fileStr)) {
            InputStream is = PropertiesUtils.class.getResourceAsStream("/" + fileStr.trim());
            // 解析文件内容
            properties = new Properties();
            try {
                properties.load(new BufferedInputStream(is));
            } catch (IOException e) {
                logger.error("加载[ " + fileStr + " ]配置文件失败!");
                // e.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                        is = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return properties;
    }
    
    public static void writeProperties(String keyname,String keyvalue,String fileStr) {   
    	Properties props = new Properties();
    	 OutputStream fos=null;
    	if(!StringUtil.isNull(fileStr)){
        	try {          
                fos = new FileOutputStream(fileStr); 
                props.setProperty(keyname, keyvalue);  
                
                props.load(new FileInputStream(fileStr));
                
                props.store(fos, "Update '" + keyname + "' value");   
            } catch (IOException e) {   
                System.err.println("属性文件更新错误");   
            } finally{
            	try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
    	}
  
    }
    
    /**
     * 返回信息国际化支持
     * @param key
     * @param lang
     * @return
     * @author chenfei@xwtec.cn 2015-7-16
     */
    public static String getLocalValue(String key, String lang){
    	Locale locale = new Locale(lang);
    	if(lang.indexOf("-")>0){
    		String[] lans = lang.split("-");
    		locale = new Locale(lans[1], lans[0]);
    	}
    	
    	String msg = "";
    	try {
    		ResourceBundle rb = ResourceBundle.getBundle("i18n.resultcode", locale);
        	msg = rb.getString(key);
    		msg = new String(msg.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		} catch (MissingResourceException e) {
			logger.error("Can't find key " + key);
			msg = "error code "+key;
		}
    	return msg;
    }
    
    public static String getLocalValue(String name, String key, String lang){
    	Locale locale = new Locale(lang);
    	if(lang.indexOf("-")>0){
    		String[] lans = lang.split("-");
    		locale = new Locale(lans[1], lans[0]);
    	}
    	
    	String msg = "";
    	try {
    		ResourceBundle rb = ResourceBundle.getBundle("i18n."+name, locale);
        	msg = rb.getString(key);
    		msg = new String(msg.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
			msg = "";
		} catch (MissingResourceException e) {
			logger.error("Can't find key " + key);
			msg = "";
		} catch (Exception e) {
			logger.error(e, e);
			msg = "";
		}
    	return msg;
    }
}
