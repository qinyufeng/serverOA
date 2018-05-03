package com.jgonet.util;


import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;



public class DesUtil4ClientService {
	private final static String encoding = "utf-8";
	
	private static String getIVToday(){
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}
	
	private static String getIVYesterday(){
		return new SimpleDateFormat("yyyyMMdd").format(new Date(new Date().getTime()-86400000));
	}
	
	private static String getIVTomorrow(){
		return new SimpleDateFormat("yyyyMMdd").format(new Date(new Date().getTime()+86400000));
	}
	
	private static String getSecretKeyToday(){
		return "cmcc_"+getIVToday()+"_asiainfo_ocs";
	}
	
	private static String getSecretKeyYesterday(){
		return "cmcc_"+getIVYesterday()+"_asiainfo_ocs";
	}
	
	private static String getSecretKeyTomorrow(){
		return "cmcc_"+getIVTomorrow()+"_asiainfo_ocs";
	}
	
	/**
	 * 加密
	 * @param plainText
	 * @return
	 */
	public static String encode(String plainText) {
		Key deskey = null;
		byte[] encryptData = null;
		String secretKey = getSecretKeyToday();
		try {
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);

			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(getIVToday().getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			encryptData = cipher.doFinal(plainText.getBytes(encoding));
			
	        return Base64ClientService.encode(encryptData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 解密
	 * @param data
	 * @return
	 */
	public static String decode(String data){
		try {
			Key deskey = null;
			String secretKey = getSecretKeyToday();
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);
			Cipher deCipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(getIVToday().getBytes());
			deCipher.init(Cipher.DECRYPT_MODE, deskey, ips);
			
			byte[] pasByte = null;
			try{
				pasByte=deCipher.doFinal(Base64.decode(data));
			}catch(BadPaddingException e){
				secretKey = getSecretKeyYesterday();
				spec = new DESedeKeySpec(secretKey.getBytes());
				deskey = keyfactory.generateSecret(spec);
				ips = new IvParameterSpec(getIVYesterday().getBytes());
				deCipher.init(Cipher.DECRYPT_MODE, deskey, ips);
				try{
					pasByte=deCipher.doFinal(Base64.decode(data));
				}catch(BadPaddingException e1){
					secretKey = getSecretKeyTomorrow();
					spec = new DESedeKeySpec(secretKey.getBytes());
					deskey = keyfactory.generateSecret(spec);
					ips = new IvParameterSpec(getIVTomorrow().getBytes());
					deCipher.init(Cipher.DECRYPT_MODE, deskey, ips);
					
					pasByte=deCipher.doFinal(Base64.decode(data));
				}
			}
			return new String(pasByte,"UTF-8");
		} catch (Exception e) {}
		return "";
    }

	public static void main(String[] args) {
//		System.out.println(DesUtil4ClientService.decode("PP01uBUId3mAWwkq9+8gobxiAOf9OX9mRZG5j67JcXJcXclzZncSMu3JCHuEZttOKh7vjl7NCvhjCscgi1LmaJgiOpGQkkhd45Rir9lus1I1jK8UjiCf/0sZWTbpAVsMUOp2y736ZbI="));
//		System.out.println(DesUtil4ClientService.decode("pHieSXeUuWdRc050%2BE8MyG7WE2fLPsbjz1VOcbPARTTmHXYZ0YgTxg%2FYrqonnYk4ym7dripzE0hv%0D%0A3BnHySJuAw8%2FdjQxW26XZ%2BqiSwM%2FjDZVCElrUeeK2Eq8%2Fby7rGMsO%2FwwFjrUiUf%2FBTPV6Mf26g%3D%3D"));
System.out.println(DesUtil4ClientService.encode("15108491484"));
System.out.println(DesUtil4ClientService.decode("xO1Tk/gOfmaxTMBaSpxe+w=="));
	}
}
