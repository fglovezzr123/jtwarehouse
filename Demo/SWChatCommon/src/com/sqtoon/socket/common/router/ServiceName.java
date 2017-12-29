package com.sqtoon.socket.common.router;

import java.util.HashMap;

public enum ServiceName
{
	ConfigCenter("CNC"), MessageProxy("CMP"), MessageCenter("MSC"), UserCacheCenter("UCC"), Unknown("Unknow");

	private static HashMap<String, ServiceName> _map;
	private String value;

	static
	{
		_map = new HashMap<String, ServiceName>();
		for (ServiceName requestMethod : ServiceName.values())
			_map.put(requestMethod.getValue(), requestMethod);
	}

	public static ServiceName get(String key)
	{
		ServiceName method = _map.get(key);
		if (method == null)
			return Unknown;
		return method;
	}

	ServiceName(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}

}
