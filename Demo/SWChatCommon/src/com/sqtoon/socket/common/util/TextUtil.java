package com.sqtoon.socket.common.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

/**
 * @author yujianchi
 * 
 */
public class TextUtil
{
	public static final String EmptyString = "";
	public static final String UnderScore = "_";
	public static final String WildCard = "*";
	public static final String SPACE = "    ";
	public static final String POINT = "\\.";
	public static final String SLASH = "/";
	public static final String Comma = ",";
	public static final String Dash = "-";
	public static final String Plus = "+";
	public static final String SHARP = "#";
	public static final String RETURN = "\r\n";
	public static final String UTF8 = "UTF-8";
	public static final String ASCII = "ASCII";

	private static final String COMPLETEDATEFORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	private static final String SIMPLEDATEFORMAT = "yyyyMM";

	private static int[] rawdata = {0, 1, 2, 3, 5, 6, 7, 8, 9};

	private static ThreadLocal<SimpleDateFormat> threadLocalcompletedate = new ThreadLocal<SimpleDateFormat>() {
		protected synchronized SimpleDateFormat initialValue()
		{
			return new SimpleDateFormat(COMPLETEDATEFORMAT);
		}
	};

	private static ThreadLocal<SimpleDateFormat> threadLocalsimpledate = new ThreadLocal<SimpleDateFormat>() {
		protected synchronized SimpleDateFormat initialValue()
		{
			return new SimpleDateFormat(SIMPLEDATEFORMAT);
		}
	};

	public static String getDateTimeString(Long dateTime)
	{
		return threadLocalcompletedate.get().format(new Date(dateTime));
	}

	public static String getMonthTablePrefix(Date date)
	{
		return threadLocalsimpledate.get().format(date);
	}

	public static String getThreadName(Thread t)
	{
		if (t == null)
			return EmptyString;

		if (t.getName().length() > 64)
			return t.getName().substring(0, 63);
		else
			return t.getName();
	}

	public static String RandomNumViaLength(int length)
	{
		Random r = new Random();
		int rand = r.nextInt(rawdata.length);
		char[] res = new char[length];
		for (int i = 0; i < length; i++)
		{
			res[i] = String.valueOf(rawdata[rand]).charAt(0);
			rand = r.nextInt(rawdata.length);
		}
		return String.copyValueOf(res);
	}

	public static boolean equals(String a, String b)
	{
		return a != null && b != null && a.equals(b);
	}

	public static String cutUTF8String(String s, int maxlength)
	{
		if (maxlength == 0)
			return "";
		if (s.getBytes().length <= maxlength)
			return s;
		else
		{
			int idx = 0;
			int lastadded = 0;

			while (idx < maxlength)
			{
				if ((s.getBytes()[idx] & 0xff) >> 7 == 0)
					lastadded = 1;
				else
					if ((s.getBytes()[idx] & 0xff) >> 5 == 6)
						lastadded = 2;
					else
						if ((s.getBytes()[idx] & 0xff) >> 4 == 14)
							lastadded = 3;
						else
							if ((s.getBytes()[idx] & 0xff) >> 3 == 30)
								lastadded = 4;

				if (idx + lastadded > maxlength)
					break;
				else
					idx += lastadded;
			}
			return new String(Arrays.copyOf(s.getBytes(), idx));
		}
	}
}
