package com.sqtoon.socket.common.util;

import java.util.HashMap;

public class EnhancedHashMap<K, V> extends HashMap<K, V>
{
	private static final long serialVersionUID = 6545317797581440617L;

	public V get(K key,V defaultValue)
	{
		if (this.containsKey(key))
			return this.get(key);
		else
			return defaultValue;
	}
}
