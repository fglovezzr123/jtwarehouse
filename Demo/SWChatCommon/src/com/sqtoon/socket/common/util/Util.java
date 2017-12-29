package com.sqtoon.socket.common.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util
{
	public static ByteBuffer getUUID()
	{
		ByteBuffer buffer = ByteBuffer.allocate(16);
		UUID uuid = UUID.randomUUID();
		buffer.putLong(uuid.getLeastSignificantBits());
		buffer.putLong(uuid.getMostSignificantBits());
		return buffer;
	}

	public static boolean isGroupId(long id)
	{
		return (id >= 900000000000L && id <= 900999999999L);
	}

	public static boolean isValidMobilenum(Long mobilenum)
	{
		return (mobilenum != null && mobilenum > 10000000000L && mobilenum < 20000000000L);
	}

	public static int getGBKLength(String s)
	{
		return s.getBytes(Charset.forName("GBK")).length;
	}

	public static int GetUTF8Length(String s)
	{
		return s.getBytes(Charset.forName("UTF-8")).length;
	}

	public static String getGBKCutString(String s, int cut)
	{
		if (s.length() < (cut / 2))
			return s;
		StringBuilder sb = new StringBuilder();
		for (Character c : s.toCharArray())
		{
			if (sb.toString().getBytes(Charset.forName("GBK")).length > cut + 1)
				break;
			sb.append(c);
		}
		return sb.toString();
	}

	public static int bytesToInt(byte[] b)
	{
		ByteBuffer bf = ByteBuffer.wrap(b);
		return bf.getInt();
	}

	public static byte[] intToBytes(int i)
	{
		ByteBuffer bf = ByteBuffer.allocate(4);
		bf.putInt(i);
		return bf.array();
	}

	public static long bytesToLong(byte[] b)
	{
		ByteBuffer bf = ByteBuffer.wrap(b);
		return bf.getLong();
	}

	public static byte[] longToBytes(long l)
	{
		ByteBuffer bf = ByteBuffer.allocate(8);
		bf.putLong(l);
		return bf.array();
	}

	public static char bytesToChar(byte[] b)
	{
		ByteBuffer bf = ByteBuffer.wrap(b);
		return bf.getChar();
	}

	public static byte[] charToBytes(char c)
	{
		ByteBuffer bf = ByteBuffer.allocate(2);
		bf.putChar(c);
		return bf.array();
	}

	public static byte[] getBytes(byte[] bytes, int length)
	{
		if (bytes.length > length)
		{
			ByteBuffer bf = ByteBuffer.allocate(length);
			bf.put(bytes, 0, length);
			return bf.array();
		}
		else
		{
			return bytes;
		}
	}

	public static String cutString(String s, int length)
	{
		if (length <= 0)
			return TextUtil.EmptyString;
		if (s.length() > length)
			return s.substring(0, length - 1);
		return s;
	}

	public static boolean isNullOrEmpty(CinLinkedList<?> list)
	{
		return list == null || list.length() == 0;
	}

	public static boolean isNullOrEmpty(String str)
	{
		return str == null || str.equals(TextUtil.EmptyString);
	}

	public static boolean isNullOrEmpty(Object[] array)
	{
		return array == null || array.length == 0;
	}

	public static boolean isNullOrEmpty(CinHashMap<?, ?> map)
	{
		return map == null || map.length() == 0;
	}

	public static boolean isNullOrEmpty(List<?> list)
	{
		return list == null || list.isEmpty();
	}

	public static long doubleIntegerToLong(int first, int seconde)
	{
		return ((long) first << 32) + seconde;
	}

	public static boolean isNullOrEmpty(HashMap<?, ?> map)
	{
		return map == null || map.size() == 0;
	}

	public static boolean checkRegular(String regular, String name)
	{
		Pattern p = Pattern.compile(regular);
		Matcher m = p.matcher(name);
		return m.find();
	}

	public static String clearByRegular(String regular, String mobileno)
	{
		Pattern p = Pattern.compile(regular);
		Matcher m = p.matcher(mobileno);
		return m.replaceAll("").toString();
	}

	public static int getBetweenDays(long last, long now)
	{
		try
		{
			String t1 = Util.getDate(new Date(last));
			String t2 = Util.getDate(new Date(now));
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			int betweenDays = 0;
			Date d1 = format.parse(t1);
			Date d2 = format.parse(t2);
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(d1);
			c2.setTime(d2);

			if (c1.after(c2))
			{
				c1 = c2;
				c2.setTime(d1);
			}
			int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
			betweenDays = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
			for (int i = 0; i < betweenYears; i++)
			{
				c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
				betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
			}
			return betweenDays;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	public static String getDate(Date date)
	{
		Date nowTime = null;
		if (date != null)
			nowTime = date;
		else
			nowTime = new Date(System.currentTimeMillis());
		SimpleDateFormat Formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return Formatter.format(nowTime);
	}
}
