package com.tojoycloud.frame.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Dom4jUtil
{
	public synchronized static String getXMLForWebChat(Object b)
	{
		StringBuffer result = new StringBuffer();
		result.append("<xml>");
		Field[] field = b.getClass().getDeclaredFields();
		for (int i = 0; i < field.length; i++)
		{
			String name = field[i].getName();
			String propertievalue;
			try
			{
				Method m = b.getClass().getMethod("get" + field[i].getName().substring(0, 1).toUpperCase() + field[i].getName().substring(1));
				propertievalue = (String) m.invoke(b);
				if (propertievalue != null)
				{
					result.append("<" + name + ">");
					result.append(propertievalue);
					result.append("</" + name + ">");
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		result.append("</xml>");
		return result.toString();
	}

	public synchronized static String returnSuccess()
	{
		StringBuffer result = new StringBuffer();
		result.append("<xml>");
		result.append("<return_code><![CDATA[SUCCESS]]></return_code>");
		result.append("<return_msg><![CDATA[OK]]></return_msg>");
		result.append("</xml>");
		return result.toString();
	}

	public synchronized static String returnFail(String errorMsg)
	{
		StringBuffer result = new StringBuffer();
		result.append("<xml>");
		result.append("<return_code>FAIL></return_code>");
		result.append("<return_msg>" + errorMsg + "</return_msg>");
		result.append("</xml>");
		return result.toString();
	}
}
