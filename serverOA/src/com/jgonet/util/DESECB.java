package com.jgonet.util;


import java.security.*;

import javax.crypto.*;


/**
 * @company 
 * @author 
 * @date 
 * */
public class DESECB {
	
    private static String strDefaultKey = "dzg2016@1234";
    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;
    

    /**
     * @throws Exception
     */
    public DESECB() throws Exception {
        this(strDefaultKey);
    }

    /**
     * @throws Exception
     */
    public DESECB(String strKey) throws Exception {
        Key key = getKey(strKey.getBytes());
        encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
       decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    /**
     * @throws Exception
     */
    public byte[] encrypt(byte[] arrB)  throws Exception {
        return encryptCipher.doFinal(arrB);
    }

    /**
     * @throws Exception
     */
    public String encrypt(String strIn) throws Exception {
        return byteArr2HexStr(encrypt(strIn.getBytes()));
    }
    public byte[] decrypt(byte[] arrB) throws Exception {
        return decryptCipher.doFinal(arrB);
    }

    public String decrypt(String strIn) throws Exception {
        return new String(decrypt(hexStr2ByteArr(strIn)));
    }

    private Key getKey(byte[] arrBTmp) throws Exception {
        byte[] arrB = new byte[8];

        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }

        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
       return key;
    }
    
    public static byte[] hexStr2ByteArr(String strIn)  throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2)
        {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }
    public static String byteArr2HexStr(byte[] arrB)  throws Exception {
        int iLen = arrB.length;
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            while (intTmp < 0){
                intTmp = intTmp + 256;
            }
            if (intTmp < 16){
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }
    
    public static void main(String[] args) throws Exception{
    	String text = "fabe767c25bd97f73118e447bdab7f6f3866da691f2943078597ab6804d21edd5181c3a804188a37855f5fbd717262f540223233cc2690eb7eba7f80cab3247cea2f4b1ebe8f15aa2d19c4b34aee9349";
    	String key = "abcd1234";
    	DESECB des = new DESECB(key);
    	String encryStr = text;
    	String aaa = des.decrypt(text);
    	System.out.println("" + aaa);
    	System.out.println(""+new String(des.decrypt(hexStr2ByteArr(encryStr))));
    }
}
