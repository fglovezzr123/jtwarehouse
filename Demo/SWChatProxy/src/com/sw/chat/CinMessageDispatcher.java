package com.sw.chat;

import java.util.concurrent.ConcurrentHashMap;
import com.sqtoon.socket.common.message.Header;
import com.sqtoon.socket.common.message.HeaderType;
import com.sqtoon.socket.common.message.Request;
import com.sqtoon.socket.common.message.RequestMethod;
import com.sqtoon.socket.common.transaction.Transaction;
import com.sw.chat.handler.LogoffUACHandler;
import com.sw.chat.handler.LogoffUASHandler;
import com.sw.chat.handler.manager.CinMessageUACHandler;
import com.sw.chat.handler.manager.CinMessageUASHandler;

@SuppressWarnings("rawtypes")
class CinMessageDispatcher
{

	private static ConcurrentHashMap<Integer, Class> _uacMap;
	private static ConcurrentHashMap<Integer, Class> _uasMap;

	CinMessageDispatcher()
	{
	}

	static void initialize()
	{
		_uacMap = new ConcurrentHashMap<Integer, Class>();
		addUACMap(RequestMethod.Service, 0x01, LogoffUACHandler.class);
		_uasMap = new ConcurrentHashMap<Integer, Class>();
		addUASMap(RequestMethod.Message, 0x01, LogoffUASHandler.class);
	}

	private static void addUACMap(byte method, long eventValue, Class c)
	{
		Header event = new Header(HeaderType.Event, eventValue);
		CinMessageHandlerKey key = new CinMessageHandlerKey(method, event);
		if (_uacMap.containsKey(key.hashCode()))
		{
			System.out.println("The following handler can't be added, because the key already exist in the UACMap.\r\n" + key.toString());
		}
		else
		{
			_uacMap.put(key.hashCode(), c);
			System.out.println(key.toString() + " has been added to the UACMap.");
		}
	}

	private static void addUASMap(byte method, long eventValue, Class c)
	{
		Header event = new Header(HeaderType.Event, eventValue);
		CinMessageHandlerKey key = new CinMessageHandlerKey(method, event);
		if (_uasMap.containsKey(key.hashCode()))
		{
			System.out.println("The following handler can't be added, because the key already exist in the UASMap.\r\n" + key.toString());
		}
		else
		{
			_uasMap.put(key.hashCode(), c);
			System.out.println(key.toString() + " has been added to the UASMap.");
		}
	}

	static CinMessageUACHandler getCinMessageUACHandler(ProxyUser userProxy, Transaction transaction) throws Exception
	{
		Request request = transaction.request();
		int key = new CinMessageHandlerKey(request).hashCode();
		Class c = _uacMap.get(key);
		CinMessageUACHandler handler;
		if (c != null)
		{
			handler = (CinMessageUACHandler) c.newInstance();
			handler.initialize(userProxy, transaction);
			return handler;
		}

		key = new CinMessageHandlerKey(request.getMethod(), null).hashCode();
		c = _uacMap.get(key);
		if (c != null)
		{
			handler = (CinMessageUACHandler) c.newInstance();
			handler.initialize(userProxy, transaction);
			return handler;
		}

		handler = new CinMessageUACHandler();
		handler.initialize(userProxy, transaction);
		return handler;
	}

	static CinMessageUASHandler getCinMessageUASHandler(ProxyUser userProxy, Transaction transaction) throws Exception
	{
		Request request = transaction.request();
		int key = new CinMessageHandlerKey(request).hashCode();
		Class c = _uasMap.get(key);
		CinMessageUASHandler handler;
		if (c != null)
		{
			handler = (CinMessageUASHandler) c.newInstance();
			handler.initialize(userProxy, transaction);
			return handler;
		}

		key = new CinMessageHandlerKey(request.getMethod(), null).hashCode();
		c = _uasMap.get(key);
		if (c != null)
		{
			handler = (CinMessageUASHandler) c.newInstance();
			handler.initialize(userProxy, transaction);
			return handler;
		}

		handler = new CinMessageUASHandler();
		handler.initialize(userProxy, transaction);
		return handler;
	}
}