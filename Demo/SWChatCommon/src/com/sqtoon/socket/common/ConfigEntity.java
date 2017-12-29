package com.sqtoon.socket.common;

import java.util.Set;
import com.sqtoon.socket.common.util.EnhancedHashMap;

public class ConfigEntity
{
	private EnhancedHashMap<String, String> _configtable;

	public ConfigEntity()
	{
		_configtable = new EnhancedHashMap<String, String>();
	}

	public void put(String key, String value)
	{
		_configtable.put(key, value);
	}

	public String get(String key)
	{
		return _configtable.get(key);
	}

	public String get(String key, String defaultvalue)
	{
		return _configtable.get(key, defaultvalue);
	}

	public Set<String> getKeySet()
	{
		return _configtable.keySet();
	}
}
