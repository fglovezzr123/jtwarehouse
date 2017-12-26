/**
 * 
 */
package com.tojoy.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * 本类中包括日期转换相关的方法
 * 
 * @author Dec 31, 2008 2:56:33 PM
 * 
 */
public class DateUtils {
	/**
	 * 把日期字符串转换为具体日期
	 * 
	 * @param dateString
	 *            要转换的日期字符串，格式：yyyy-MM-dd,如果字符串为""，返回null
	 * @return 转换后的日期
	 */
	public static Date stringToDate(String dateString) {
		return stringToDate(dateString, "yyyy-MM-dd");
	}

	/**
	 * 根据传进来的时间对象返回一个指定格式的时间字符串 假如传进来的时间为空则返回当前时间的字符串 by songyj
	 * 
	 * @param date
	 * @param dataformax
	 *            一定格式的日期类型，例如：yyyy-MM-dd HH:mm:ss，通用性更强
	 * @return
	 */
	public static String getFormaxDateString(Date date, String dataformax) {
		SimpleDateFormat formatter = new SimpleDateFormat(dataformax);

		if (date == null) {
			return formatter.format(new Date());
		}
		return formatter.format(date);
	}

	/**
	 * 获得当前年份 by songyj
	 * 
	 * @return
	 */
	public static String getnowyear() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int nowyear = cal.get(Calendar.YEAR);
		return String.valueOf(nowyear);
	}

	/**
	 * 把日期字符串转换为具体日期
	 * 
	 * @param dateString
	 *            要转换的日期字符串
	 * @param fmt
	 *            格式，例如"yyyy-MM-dd"
	 * @return 转换后的日期
	 */
	public static Date stringToDate(String dateString, String fmt) {
		Date tempDate = null;

		if (dateString == null)
			return tempDate;
		if (dateString.equals(""))
			return tempDate;

		SimpleDateFormat dateformat = new SimpleDateFormat(fmt);
		try {
			tempDate = dateformat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return tempDate;
	}

	/**
	 * 把日期转换为字符串
	 * 
	 * @param date
	 *            要转换的日期
	 * @return 转换后的字符串，格式"yyyy-MM-dd"
	 */
	public static String dateToString(Date date) {
		return dateToString(date, "yyyy-MM-dd");
	}

	/**
	 * 把日期转换为字符串
	 * 
	 * @param date
	 *            要转换的日期
	 * @param fmt
	 *            格式,例如"yyyy-MM-dd"
	 * @return
	 */
	public static String dateToString(Date date, String fmt) {
		String string = "";

		if (date == null)
			return string;

		SimpleDateFormat dateformat = new SimpleDateFormat(fmt);
		string = dateformat.format(date);

		return string;
	}

	/**
	 * 把日期字符串转换为具体日期
	 * 
	 * @param dateString
	 *            要转换的日期字符串，格式：yyyy-MM-dd HH:mm:ss,如果字符串为""，返回null
	 * @return 转换后的日期
	 */
	public static Date stringToDatetime(String dateString) {
		Date tempDate = null;

		if (dateString == null)
			return tempDate;
		if (dateString.equals(""))
			return tempDate;

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			tempDate = dateformat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return tempDate;
	}

	/**
	 * 把日期字符串转换为具体日期sq;
	 * 
	 * @param dateString
	 *            要转换的日期字符串，格式：yyyy-MM-dd HH:mm:ss,如果字符串为""，返回null
	 * @return 转换后的日期
	 */
	public static Timestamp stringToSqlDatetime(String dateString) {
		java.sql.Timestamp tempDate = null;

		if (dateString == null)
			return tempDate;
		if (dateString.equals(""))
			return tempDate;

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			tempDate = new Timestamp(dateformat.parse(dateString).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return tempDate;
	}

	/**
	 * 把日期转换为字符串
	 * 
	 * @param date
	 *            要转换的日期
	 * @return 转换后的字符串，格式"yyyy-MM-dd HH:mm:ss"
	 */
	public static String datetimeToString(Date date) {
		String string = "";

		if (date == null)
			return string;

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		string = dateformat.format(date);

		return string;
	}

	/**
	 * 取得当前时间的字符串
	 * 
	 * @return 当前时间的字符串，格式"yyyy-MM-dd"
	 */

	public static String getToday() {
		String tempString = "";
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			tempString = dateformat.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	/**
	 * 取得当前时间的字符串
	 * 
	 * @return 当前时间的字符串，格式"yyyy-MM-dd HH:mm:ss"
	 */
	public static String getDatetime() {
		String tempString = "";
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			tempString = dateformat.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempString;
	}

	/**
	 * 计算两日期差多少天。第二个日期减第一个日期。可能为负数。
	 * 
	 * @param time1
	 *            yyyy-MM-dd格式
	 * @param time2
	 *            yyyy-MM-dd格式
	 * @return
	 */
	public static Long getDateDiff(String time1, String time2) {
		long diff = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			diff = date2.getTime() - date1.getTime();
			diff = diff / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Long(diff);
	}

	/**
	 * 计算两日期差多少分钟。第二个日期减第一个日期。可能为负数。
	 * 
	 * @param time1
	 *            yyyy-MM-dd格式
	 * @param time2
	 *            yyyy-MM-dd格式
	 * @return
	 */
	public static Long getDateDiffMinutes(String time1, String time2) {
		long diff = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			diff = date2.getTime() - date1.getTime();
			diff = diff / 1000 / 60;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Long(diff);
	}

	/**
	 * 计算两日期差多少分钟。第二个日期减第一个日期。可能为负数。
	 * 
	 * @param time1
	 *            yyyy-MM-dd格式
	 * @param time2
	 *            yyyy-MM-dd格式
	 * @return
	 */
	public static Long getDateDiffMinutes(Date date1, Date date2) {
		long diff = 0;
		try {
			diff = date2.getTime() - date1.getTime();
			diff = diff / 1000 / 60;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Long(diff);
	}

	/**
	 * 计算两日期差多少天。第二个日期减第一个日期。可能为负数。
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Long getDateDiff(Date d1, Date d2) {
		long diff = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			d1 = ft.parse(ft.format(d1));
			d2 = ft.parse(ft.format(d2));
			diff = d2.getTime() - d1.getTime();
			diff = diff / 1000 / 60 / 60 / 24;
			//// System.out.println(diff);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Long(diff);
	}

	/**
	 * 获得每个月的有多少天
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @return 天数
	 * 
	 * @author Jun 10, 2009 11:58:56 AM
	 */
	public static int getMonthDays(int year, int month) {
		int monthDays = 0;

		// 这段代码不生效，不知道为什么
		// GregorianCalendar cal = new GregorianCalendar();
		// cal.set(year,month-1,1);
		// monthDays = cal.getMaximum(Calendar.DAY_OF_MONTH);

		// 暂时用这段代码代替
		GregorianCalendar cal = new GregorianCalendar();
		int[] daysArray = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		if (month == 2 && cal.isLeapYear(year)) {
			monthDays = 29;
		} else {
			monthDays = daysArray[month - 1];
		}
		return monthDays;
	}

	/**
	 * 通过出生日期返回年龄
	 * 
	 * @param birthDay
	 * @return
	 * @throws Exception
	 */
	public static String getAge(Date birthDay) {
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthDay)) {
			return "未出生";
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		int age = yearNow - yearBirth;
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}
		return age + "";
	}

	public static String getCurrTimeStr(String fmt) {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		return sdf.format(new Date());
	}
	
	public static void main(String[] args)throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.parse("2017-06-01 14:38:27").getTime());
	}
}
