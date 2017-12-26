package com.tojoycloud.frame.common;

import java.util.HashMap;
import java.util.Map;

public enum Enum_order
{
	// 订单状态(0-取消 1-下单 2—同意）
	ORDER("1"),

	AGREE("2"),

	CANCEL("0"),

	UNKNOWN("UNKNOWN");

	private String value;

	private final static Map<String, Enum_order> MAP = new HashMap<String, Enum_order>();

	private Enum_order(String value)
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
		for (Enum_order item : Enum_order.values())
		{
			MAP.put(item.getValue(), item);
		}
	}

	public static Enum_order getOptType(String value)
	{
		if (value == null || value.length() <= 0)
		{
			return Enum_order.UNKNOWN;
		}

		if (MAP.containsKey(value.toUpperCase()))
		{
			return MAP.get(value.toUpperCase());
		}
		else
		{
			return Enum_order.UNKNOWN;
		}
	}

	public static boolean validOptType(String value)
	{
		if (value == null || value.length() <= 0)
		{
			return false;
		}

		return MAP.containsKey(value.toUpperCase());
	}
}
