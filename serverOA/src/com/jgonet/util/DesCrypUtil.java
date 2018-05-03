package com.jgonet.util;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DesCrypUtil {
	
	/** 加密算法,可用 DES,DESede,Blowfish. */
    private final static String ALGORITHM = "DES";
    private final static String ALGORITHM_FORMATE = "DES/ECB/PKCS5Padding";
    

    /**
     * 对用DES加密过的数据进行解密.   
     * @param data DES加密数据
     * @return 返回解密后的数据, 如果传入为null返回null
     */
    public final static String DESDecrypt(String data, String key){
    	String result = "";
    	try{
    		if (data == null || data.length() == 0) {
 	        	return null;   
 	        } else {  
 	        	byte[] bytes=Base64.decode(data, Base64.GZIP,Base64.PREFERRED_ENCODING);
 	        	if (bytes != null) {
 	        		result = new String(decrypt(bytes,
 	        				key.getBytes(Base64.PREFERRED_ENCODING)),Base64.PREFERRED_ENCODING);
 	        	}
 	        }   
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    	return result;
    }
    
    /**
     * 对数据进行DES加密.
     * @param data 待进行DES加密的数据
     * @return 返回经过DES加密后的数据
     */
    public final static String DESEncrypt(String data, String key)  {
    	String result = "";
    	try{
        	byte[] source = encrypt(data.getBytes(Base64.PREFERRED_ENCODING), key
                    .getBytes(Base64.PREFERRED_ENCODING));
        	result = Base64.encodeBytes(source, Base64.GZIP,Base64.PREFERRED_ENCODING);
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
        return result;
    }
    
    
    /**
     * 用指定的key对数据进行DES加密.
     * @param data 待加密的数据
     * @param key DES加密的key
     * @return 返回DES加密后的数据
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
    	//System.out.println("encrypt:"+data.length);
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(ALGORITHM_FORMATE);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        // 现在，获取数据并加密
        // 正式执行加密操作
        return cipher.doFinal(data);
    }
    
    /**
     * 用指定的key对数据进行DES解密.
     * @param data 待解密的数据
     * @param key DES解密的key
     * @return 返回DES解密后的数据
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(ALGORITHM_FORMATE);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        // 现在，获取数据并解密
        // 正式执行解密操作
        return cipher.doFinal(data);
    }
    
    public final static Map convertInParm(String parm){
    	//PHONE_NO=13678138741&LOGIN_PHONE_NO=13678138741&
		//TIME_STAMP=20150107194907256&IP=223.104.9.13&
		//clientRequestTime=2015010719482293&
		//token=cKlRGH7O39Rh0vFXTXAWo53nTlYWpKEmMbqRTmMRdq&
		//imei=356440042297802&imsi=460008040438363&
		//ip=d8:b3:77:15:7e:d0&net_type=WIFI&LOGIN_NO=ob0014&timestep=1420631299010
    	Map map = new HashMap(); 
    	String[] params = parm.split("&");  
        for (int i = 0; i < params.length; i++) {  
            String[] p = params[i].split("=");  
            if (p.length == 2) {  
                map.put(p[0], p[1]);  
            }  
        }  
	    return map;
    }
    
    public final static long getTimeDiff(String parmIn,String parmLocal){
    	long sec = 10;
    	try{
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			Date d1 = df.parse(parmLocal);//20140730142615
			Date d2 = df.parse(parmIn.substring(0, 14));//20140730142559
			                           //20140801152600
			long diff = d1.getTime() - d2.getTime();
			sec = diff / (1000*60);
			System.out.println(sec);
		}catch(Exception e){
			e.printStackTrace();
		}
		return sec;
    }
    
    
    public static void main(String[] args) { 
    	
		//String oldStr = "PHONE_NO=13678138888&LOGIN_PHONE_NO=13540669235&TIME_STAMP=20150108101850123&IP=117.136.64.115&clientRequestTime=2015010719482293&token=cKlRGH7O39Rh0vFXTXAWo53nTlYWpKEmMbqRTmMRdq&imei=356440042297802&imsi=460008040438363&ip=d8:b3:77:15:7e:d0&net_type=WIFI&LOGIN_NO=ob0014&timestep=1420631299010&phoneNum=13678138888&price=30";
		///String oldStr = "H4sIAAAAAAAAAAEoAdf+JJmL62kwF/7Fo8k3v/tenJk9bLn7feOx2TvX/G5HI14jvxQVbGvpUAFizYt9sEqFSbIxoDZRhReeZnsuFGTvRHmNErnvSuv2D2G5AvsYVxuvQDyUGjS6vop9hOdJZ/m8aXtSbR0AlnljoTAe+YGMlvv/EUwes1EKHtDLJoXdvhE6ESEt1stTF7nq8gtbCWNn4bNUoApieoS0SEZd6vOaoI+d+OHyj/nBJqUcUL4IFR6IWXfJKJzGnjFaeecBPqYbfpoK0xp/6/wx3mh+tFkAF9r5HOyFWvk22DRnnFsj0zBXsPv4q+vorGlcdSgloFFJI+PyhzL/L4jIwQn1TMRmwclJnM2lIMEL2o0aEQIY5VM7Y5nuIZ97NpAqcFaQtXr3bcXbCj6JyLIwb0x0KAEAAA==";
		String oldStr = "5";
		String key = "abcd1234";
		String destr1 = DESEncrypt(oldStr,key);
		System.out.println("query"+destr1);
		
		String destr = DESDecrypt(destr1,key);
		System.out.println(destr);
		
    }
}
