package com.jgonet.util;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 提供和三方合作厂家加密对接方案,不同厂家维护不同channel
 * 
 * @author xwtec
 *
 */
public class DesUtil3PClient {
	private final static String encoding = "utf-8";
	private static String channel = "";
	private final static String IV = "20100101";

	private static String getIVToday() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}

	private static String getSecretKeyToday() {
		return "cmcc_" + channel + "_" + getIVToday() + "_asiainfo_ocs";
	}

	/**
	 * 加密
	 * 
	 * @param plainText
	 * @param channel
	 * @return
	 */
	public static String encode(String plainText, String channel) {
		DesUtil3PClient.channel = channel;
		return encode(plainText);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 * @return
	 */
	public static String decode(String data, String channel) {
		DesUtil3PClient.channel = channel;
		return decode(data);
	}

	/**
	 * 加密
	 * 
	 * @param plainText
	 * @return
	 */
	private static String encode(String plainText) {
		Key deskey = null;
		byte[] encryptData = null;
		String secretKey = getSecretKeyToday();
		try {
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);

			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
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
	 * 
	 * @param data
	 * @return
	 */
	private static String decode(String data) {
		try {
			Key deskey = null;
			String secretKey = getSecretKeyToday();
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);
			Cipher deCipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
			deCipher.init(Cipher.DECRYPT_MODE, deskey, ips);

			byte[] pasByte = null;
			try {
				pasByte = deCipher.doFinal(Base64.decode(data));
			} catch (BadPaddingException e) {}
			return new String(pasByte, "UTF-8");
		} catch (Exception e) {}
		return "";
	}

	public static void main(String[] args) {
		System.out.println(DesUtil3PClient.encode("15108491484", "139mail"));
		System.out.println(DesUtil3PClient.decode("MzX4b3+W1YXUE7jAMc7MKw==", "139mail"));
	}
}
