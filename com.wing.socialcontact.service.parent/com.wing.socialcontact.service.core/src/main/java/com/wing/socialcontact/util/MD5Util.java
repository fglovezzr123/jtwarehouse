package com.wing.socialcontact.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class MD5Util {
	static MessageDigest getDigest() {
		try {
			return MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] md5(byte[] data) {
		return getDigest().digest(data);
	}

	public static byte[] md5(String data) {
		return md5(data.getBytes());
	}

	public static String md5Hex(byte[] data) {
		return HexUtil.toHexString(md5(data));
	}

	public static String md5Hex(String data) {
		return HexUtil.toHexString(md5(data));
	}

	public static void main(String[] args) {
		long timestamp = new Date().getTime();
		String sinstr = MD5Util.MD5(timestamp + Constants.PRIVATE_KEY);
		String str = timestamp + Constants.PRIVATE_KEY;
		System.out.println(timestamp + Constants.PRIVATE_KEY);
		System.out.println(sinstr);
		System.out.println(MD5Util.MD5(Constants.PRIVATE_KEY));
		System.out.println(MD5Util.to_MD5(Constants.PRIVATE_KEY));
		System.out.println(MD5Util.MD5Validate2(Constants.PRIVATE_KEY, MD5Util.to_MD5(Constants.PRIVATE_KEY)));
	}

	/**
	 * 加密
	 * 
	 * @param s
	 * @return
	 */
	public static String MD5(String s) {

		if (s == null || s.length() == 0) {
			return null;
		}
		char hexDigits[] = { 'A', '1', 'B', '3', 'C', '5', 'D', '7', 'E', '9', 'F', '0', 'G', '2', 'H', '4' };
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
	
	public final static String to_MD5(String s) {  
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};         
        try {  
            byte[] btInput = s.getBytes();  
            // 获得MD5摘要算法的 MessageDigest 对象  
            MessageDigest mdInst = MessageDigest.getInstance("MD5");  
            // 使用指定的字节更新摘要  
            mdInst.update(btInput);  
            // 获得密文  
            byte[] md = mdInst.digest();  
            // 把密文转换成十六进制的字符串形式  
            int j = md.length;  
            char str[] = new char[j * 2];  
            int k = 0;  
            for (int i = 0; i < j; i++) {  
                byte byte0 = md[i];  
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
                str[k++] = hexDigits[byte0 & 0xf];  
            }  
            return new String(str);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  

	/**
	 * 比较加密是否一致
	 * 
	 * @param str
	 * @param md5Str
	 * @return
	 */
	public static boolean MD5Validate(String str, String md5Str) {

		if (md5Str == null || md5Str.length() == 0) {
			return false;
		}
		if (md5Str.equals(MD5(str))) {
			return true;
		} else {
			return false;
		}

	}
	
	/**
	 * 比较加密是否一致
	 * 
	 * @param str
	 * @param md5Str
	 * @return
	 */
	public static boolean MD5Validate2(String str, String md5Str) {

		if (md5Str == null || md5Str.length() == 0) {
			return false;
		}
		if (md5Str.equals(to_MD5(str))) {
			return true;
		} else {
			return false;
		}

	}
}
