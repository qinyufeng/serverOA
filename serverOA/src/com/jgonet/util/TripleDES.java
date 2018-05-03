package com.jgonet.util;



import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by yuanrui on 15/5/30.
 */
public class TripleDES {
    // 算法名称
    public static final String KEY_ALGORITHM = "DESede";
    // 算法名称/加密模式/填充方式
    public static final String CIPHER_ALGORITHM_ECB = "DESede/ECB/PKCS5Padding";
    public static final String CIPHER_ALGORITHM_CBC = "DESede/CBC/PKCS5Padding";

    private KeyGenerator keyGen;
    private SecretKey secretKey;
    private Cipher cipher;

    public TripleDES(String mode, String key) throws Exception {
        key = "Eastern=(!@#$%^&*)=World";
        if ("ECB".equals(mode)) {
            cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
            keyGen = KeyGenerator.getInstance(KEY_ALGORITHM);
            secretKey = new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);
        } else if ("CBC".equals(mode)) {
            cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            keyGen = KeyGenerator.getInstance(KEY_ALGORITHM);
            secretKey = keyGen.generateKey();
        }
    }

    public static byte[] TripleDES_CBC_Encrypt(byte[] sourceBuf,
                                               SecretKeySpec deskey, IvParameterSpec ivParam) throws Exception {
        byte[] cipherByte;
        // 使用DES对称加密算法的CBC模式加密
        Cipher encrypt = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");

        encrypt.init(Cipher.ENCRYPT_MODE, deskey, ivParam);

        cipherByte = encrypt.doFinal(sourceBuf, 0, sourceBuf.length);
        // 返回加密后的字节数组
        return cipherByte;
    }

    /**
     * 加密
     *
     * @param str
     * @return
     * @throws Exception
     */
    public byte[] encrypt(String str) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(str.getBytes());
    }

    /**
     * 解密
     *
     * @param encrypt
     * @return
     * @throws Exception
     */
    public byte[] decrypt(byte[] encrypt) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return  cipher.doFinal(encrypt);
    }

    byte[] getIV() {
        return "administ".getBytes();
    }

    /**
     * 加密
     *
     * @param str
     * @return
     * @throws Exception
     */
//    public byte[] encrypt2(String str) throws Exception {
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey2, new IvParameterSpec(getIV()));
//        return cipher.doFinal(str.getBytes());
//    }

    /**
     * 解密
     *
     * @param encrypt
     * @return
     * @throws Exception
     */
//    public byte[] decrypt2(byte[] encrypt) throws Exception {
//        cipher.init(Cipher.DECRYPT_MODE, secretKey2, new IvParameterSpec(getIV()));
//        return cipher.doFinal(encrypt);
//    }
	public static  byte[] convertHEXStr2Bytes(String srcBuff) {
		byte tmp = 0;
		int i = 0, j = 0;
		String subBuff = "";
		byte[] btResult = null;
		if (srcBuff == null)
			return null;
		btResult = new byte[srcBuff.length() / 2];
		for (i = 0, j = 0; i < srcBuff.length(); i += 2, j++) {
			subBuff = srcBuff.substring(i, i + 2);
			tmp = (byte) Integer.parseInt(subBuff, 16);
			btResult[j] = tmp;
		}

		return btResult;
	}
    
    public static void main(String[] args) throws Exception {
		TripleDES t = new TripleDES("ECB",null);
		byte[] convertHEXStr2Bytes = TripleDES.convertHEXStr2Bytes("401f8eac41fa41e78f52b645526ab3523e70b1729e144ef132a0359e62f97925f84927811cab05296ddf6cbb831f2793");
		byte[] decrypt = t.decrypt(convertHEXStr2Bytes);
	    String a = new String(decrypt);
		System.out.println(a);
	}
}
