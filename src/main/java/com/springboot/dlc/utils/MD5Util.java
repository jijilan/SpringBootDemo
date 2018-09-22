package com.springboot.dlc.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.io.IOException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class MD5Util {

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}


	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}
	
	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
		"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };


	private static Key getKey(String strKey) {
		Key key = null;
		try {
			KeyGenerator _generator = KeyGenerator.getInstance("DES");
			_generator.init(new SecureRandom(strKey.getBytes()));
			key = _generator.generateKey();
			_generator = null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return key;
	}

	public static String getEncString(String strMing, String strKey) {
		byte[] byteMi = null;
		byte[] byteMing = null;
		String strMi = "";
		Key key = getKey(strKey);
		BASE64Encoder encoder = new BASE64Encoder();
		try {
			byteMing = strMing.getBytes("utf-8");
			byteMi = getEncCode(byteMing, key);
			strMi = encoder.encode(byteMi);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			encoder = null;
			byteMi = null;
			byteMing = null;
		}
		return strMi;
	}

	public static String getTwiceEncString(String strMing, String strKey) {
		return getEncString(getEncString(strMing, strKey), strKey);
	}

	public static String getDecString(String strMi, String strKey) {
		BASE64Decoder base64Decoder = new BASE64Decoder();
		byte[] byteMing = null;
		byte[] byteMi = null;
		String strMing = "";
		Key key = getKey(strKey);
		try {
			byteMi = base64Decoder.decodeBuffer(strMi);
			byteMing = getDecCode(byteMi, key);
			strMing = new String(byteMing, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			base64Decoder = null;
			byteMing = null;
			byteMi = null;
		}
		return strMing;
	}

	public static String getTwiceDecString(String strMi, String strKey) {
		return getDecString(getDecString(strMi, strKey), strKey);
	}

	private static byte[] getEncCode(byte[] byts, Key key) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byts);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	private static byte[] getDecCode(byte[] bytd, Key key) {
		byte[] byteFina = null;
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byteFina = cipher.doFinal(bytd);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/*
	 *  加密
	 */
	public static String endCode(String paramData){
		String encyStr=getEncString(paramData, "!@adsga770a");
		return encyStr;
	}
	/*
	 *  解密
	 */
	public static String decCode(String paramData){
		String encyStr=getDecString(paramData, "!@adsga770a");
		return encyStr;
	}

	public static void main(String[] args) {
		System.out.println(decCode("123456"));
		//System.out.println(decCode("505A3BC7C9A4B231"));
	}

}
