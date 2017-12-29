package com.sqtoon.socket.common.message;

import java.lang.reflect.Field;
import java.util.HashMap;

public class RequestMethod
{
	public static final byte Service = (byte) 0x01;
	public static final byte Logon = (byte) 0x02;
	public static final byte Message = (byte) 0x03;

	private static HashMap<Byte, String> _map;
	static
	{
		try
		{
			Class<?> headertype = Class.forName(RequestMethod.class.getCanonicalName());
			Field[] fields = headertype.getFields();
			_map = new HashMap<Byte, String>();

			for (Field field : fields)
			{
				_map.put(Byte.valueOf(field.getByte(null)), field.getName());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Through the method of value for string expression
	 * 
	 * @param type
	 * @return Representation the method in String format
	 */
	public static String get(byte type)
	{
		return _map.get(type);
	}
}
