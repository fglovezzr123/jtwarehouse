package com.tojoycloud.frame.common.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.apache.commons.io.IOUtils;
import com.tojoycloud.frame.common.ConstantDefinition;

public class ReportUtil
{
	/**
	 * 处理客户端传来的报文，返回解压解密后的字符
	 * 
	 * @return
	 */
	public static String getRequestStr(InputStream in)
	{
		try
		{
			byte[] bReaded = IOUtils.toByteArray(in);
			IOUtils.closeQuietly(in);
			return getRequestStr(bReaded);
		}
		catch (Exception e)
		{
			// todo addlog
		}
		return null;
	}

	public static String getRequestStr(byte[] bytes)
	{
		String sContentFromClient = "";
		try
		{
			byte[] bDecrypted = ReportUtil.decrypt(bytes);

			byte[] temp = ReportUtil.decompressData(bDecrypted);
			sContentFromClient = new String(temp, 0, temp.length, "utf-8");
			if (!isReport(sContentFromClient))
			{
				byte[] _bDecrypted = ReportUtil.decrypt(temp);
				byte[] _temp = ReportUtil.decompressData(_bDecrypted);
				sContentFromClient = new String(_temp, 0, _temp.length, "utf-8");
				sContentFromClient = sContentFromClient.replace("\n", "");
			}
			else
			{
				sContentFromClient = sContentFromClient.replace("\n", "");
			}
		}
		catch (Exception e)
		{
			// todo addlog
		}
		return sContentFromClient;
	}

	/*
	 * 加密
	 */
	public static byte[] ecrypt(byte[] bytes)
	{
		int len = bytes.length;
		for (int i = 0; i < len; i++)
		{
			bytes[i] = (byte) (bytes[i] ^ ConstantDefinition.CRYPTKEY);
		}
		return bytes;
	}

	// 解密 即XOR运算，将byte[]中的每个字符按位与CRYPTKEY做XOR运算以加密, 解密时只要再与CRYPTKEY进行XOR运算即可
	public static byte[] decrypt(byte[] bytes)
	{
		int len = bytes.length;
		for (int i = 0; i < len; i++)
		{
			bytes[i] = (byte) (bytes[i] ^ ConstantDefinition.CRYPTKEY);
		}
		return bytes;
	}

	/**
	 * 压缩数据通过zlib压缩
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte[] compressData(byte[] bytes)
	{
		ByteArrayOutputStream bis = new ByteArrayOutputStream();
		try
		{
			byte[] tempByte = new byte[100];
			int compressedDataLength = -1;
			Deflater compresser = new Deflater();
			compresser.setInput(bytes);
			compresser.finish();
			while (compressedDataLength != 0)
			{
				compressedDataLength = compresser.deflate(tempByte);
				bis.write(tempByte, 0, compressedDataLength);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return bis.toByteArray();
	}

	/**
	 * 解压数据通过zlib解压
	 * 
	 * @param bytes
	 * @return
	 * @throws DataFormatException
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] decompressData(byte[] bytes) throws DataFormatException, UnsupportedEncodingException
	{
		ByteArrayOutputStream bis = new ByteArrayOutputStream();
		int resultLength = -1;
		try
		{
			Inflater decompresser = new Inflater();
			decompresser.setInput(bytes, 0, bytes.length);
			byte[] result = new byte[100];
			decompresser.finished();
			while (resultLength != 0)
			{
				resultLength = decompresser.inflate(result);
				bis.write(result, 0, resultLength);
			}
			decompresser.end();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return bis.toByteArray();
	}

	private static boolean isReport(String str)
	{
		String s = str.substring(0, 1);
		if ("{".equals(s) || "<".equals(s))
			return true;
		return false;
	}
}
