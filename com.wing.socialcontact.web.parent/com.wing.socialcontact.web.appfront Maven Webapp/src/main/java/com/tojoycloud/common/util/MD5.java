package com.tojoycloud.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5
{
	public static String encode(String inStr)
	{

		MessageDigest md = null;
		String outStr = null;
		try
		{
			md = MessageDigest.getInstance("MD5"); // 可以选中其他的算法如SHA
			byte[] digest = md.digest(inStr.getBytes()); // 返回的是byte[]，要转化为String存储比较方便
			String str = "";
			String tempStr = "";
			for (int i = 0; i < digest.length; i++)
			{
				tempStr = (Integer.toHexString(digest[i] & 0xff));
				if (tempStr.length() == 1)
					str = str + "0" + tempStr;
				else
					str = str + tempStr;
			}
			outStr = str;
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return outStr;
	}

	public static String encode3(String inStr)
	{
		MessageDigest md = null;
		String outStr = null;
		try
		{
			md = MessageDigest.getInstance("MD5"); // 可以选中其他的算法如SHA
			byte[] digest1 = md.digest(inStr.getBytes());
			byte[] digest2 = md.digest(digest1);
			byte[] digest = md.digest(digest2); // 返回的是byet[]，要转化为String存储比较方便
			String str = "";
			String tempStr = "";
			for (int i = 0; i < digest.length; i++)
			{
				tempStr = (Integer.toHexString(digest[i] & 0xff));
				if (tempStr.length() == 1)
				{
					str = str + "0" + tempStr;
				}
				else
				{
					str = str + tempStr;
				}
			}
			outStr = str;
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return outStr;
	}

	public static byte[] digest(byte[] source) throws Exception
	{
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(source);
		byte[] digest = md.digest();
		return digest;
	}
}
