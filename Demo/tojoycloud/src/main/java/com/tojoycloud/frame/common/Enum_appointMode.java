package com.tojoycloud.frame.common;

import java.util.HashMap;
import java.util.Map;

public enum Enum_appointMode
{
	// 约见模式（1—立即 2—定时）
	IMMEDIATELY("1"),

	TIMING("2"),

	UNKNOWN("UNKNOWN");

	private String value;

	private final static Map<String, Enum_appointMode> MAP = new HashMap<String, Enum_appointMode>();

	private Enum_appointMode(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	static
	{
		for (Enum_appointMode item : Enum_appointMode.values())
			MAP.put(item.getValue(), item);
	}

	public static Enum_appointMode getOptType(String value)
	{
		if (value == null || value.length() <= 0)
			return Enum_appointMode.UNKNOWN;

		if (MAP.containsKey(value.toUpperCase()))
			return MAP.get(value.toUpperCase());
		else
			return Enum_appointMode.UNKNOWN;
	}

	public static boolean validOptType(String value)
	{
		if (value == null || value.length() <= 0)
			return false;
		return MAP.containsKey(value.toUpperCase());
	}
}
