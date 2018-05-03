package com.jgonet.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
 
/**
 * AESUtil.java
 * 
 * AES 128位加解密工具类
 * 
 * @author Taohong
 * @Date 2016-9-7
 */
public class AESUtil {
 
    public static final String MYKEY = "scmccxwtecwinwin";
    
    public final static int DAY_MS = 24 * 60 * 60 * 1000;
	
    public static void main(String[] args) {

        String content =  "18780354314";
//
//        
//        System.out.println("密　钥： " + MYKEY);
//        System.out.println("加密前： " + content);
//        // 加密
        String encryptResult = encryptRandom(content);
        System.out.println("加密后： " + encryptResult);
//        System.out.println(encryptResult.length());
//        // 解密
//        String decryptResult = decrypt(encryptResult, MYKEY);
//        System.out.println("解密后： " + decryptResult);

    }
 
    /**
     * 加密
     * 
     * @param content
     *            待加密内容
     * @param key
     *            加密的密钥
     * @return
     */
    public static String encrypt(String content, String key) {
        if (key == null || content == null || content.isEmpty()) {
            return null;
        }
        String msg = null;
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] byteRresult = cipher.doFinal(byteContent);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteRresult.length; i++) {
                String hex = Integer.toHexString(byteRresult[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                sb.append(hex.toUpperCase());
            }
            return sb.toString();
        } catch (Exception e) {
            msg = e.getMessage();
            System.out.println("encrypt Exception:" + msg);
        }

        return null;
    }
 
    /**
     * 解密
     * 
     * @param content
     *            待解密内容
     * @param key
     *            解密的密钥
     * @return
     */
    public static String decrypt(String content, String key) {
        if (key == null || content == null || content.isEmpty()) {
            return null;
        }
        String msg = null;
        
        byte[] byteRresult = new byte[content.length() / 2];
        
        for (int i = 0; i < content.length() / 2; i++) {
            int high = Integer.parseInt(content.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(content.substring(i * 2 + 1, i * 2 + 2), 16);
            byteRresult[i] = (byte) (high * 16 + low);
        }
        
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] result = cipher.doFinal(byteRresult);
            return new String(result);
        } catch (Exception e) {
            msg = e.getMessage();
            System.out.println("decrypt Exception:" + msg);
        }

        return null;
    }
    
    /**
     * 手机号加密前加上随机码及日期标识，避免每次加密值一样
     * @param telephone
     * @return
     */
    public static String encryptRandom(String telephone) {
        long t = System.currentTimeMillis();
        t += 8 * 60 * 60 * 1000;   //+8时区
        
        //从1970.1.1到今天的天数;
        int today = (int)(t/AESUtil.DAY_MS);
        //天数取后16位; 如2017.1.1为EF
        String hex = Integer.toHexString(today & 0xff);
        if (hex.length() == 1) {
            hex = "0" + hex;
        }
        System.out.println("today: " + today + ", days：" + hex);
        
        //0~255 随机值
        int random = (int)(Math.random() * 256);
        String hex2 = Integer.toHexString(random);
        if (hex2.length() == 1) {
            hex2 = "0" + hex2;
        }
        // 15位源产生32位加密串, 11位号码+2位随机码+2位天数
        String source = telephone + hex + hex2;
        String encrypted = AESUtil.encrypt(source, AESUtil.MYKEY);
        
        System.out.println("source: " + source + ", encrypted： " + encrypted);
        return encrypted;
    }
    
    /**
     * 解密后截取11位手机号
     * @param in
     * @return
     */
    public static String decryptRandom(String in) {
        String out = AESUtil.decrypt(in, AESUtil.MYKEY);
        if (out != null && out.length() > 11) {
            out = out.substring(0, 11);
        }
        System.out.println("in: " + in + ", out： " + out);
        return out;
    }

    public static String getToday() {
    	
    	long t = System.currentTimeMillis();
    	t += 8 * 60 * 60 * 1000;   //+8时区
    	
    	//从1970.1.1到今天的天数;
        int today = (int)(t/AESUtil.DAY_MS);
        //天数取后16位; 如2017.1.1为EF
        String hex = Integer.toHexString(today & 0xff);
        if (hex.length() == 1) {
            hex = "0" + hex;
        }
        System.out.println("today: " + today + ", days：" + hex);
        
        return hex;
    }
    
}