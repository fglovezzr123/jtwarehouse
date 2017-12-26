package com.sqtoon.socket.common;

import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sqtoon.socket.common.message.HeaderType;
import com.sqtoon.socket.common.transaction.Transaction;

public class EventHandlerManager
{
	private static Logger logger = LoggerFactory.getLogger(SocketConfig.LOGGER_MAIN);
	public static final int DefaultEventValue = 0;
	private static HashMap<Byte, HashMap<Integer, Class<? extends EventHandler>>> _handlers;

	public static void initialize(byte method, Integer event, Class<? extends EventHandler> handler) throws Exception
	{
		if (_handlers == null)
			_handlers = new HashMap<Byte, HashMap<Integer, Class<? extends EventHandler>>>();

		put(method, event, handler);
	}

	public static EventHandler getHandler(Transaction transaction)
	{
		try
		{
			byte method = transaction.request().getMethod();
			if (_handlers.containsKey(Byte.valueOf(method)))
			{
				Integer event = transaction.request().containsHeader(HeaderType.Event) && transaction.request().Event.isNotNullValue() ? (int) transaction.request().Event.getInt64() : DefaultEventValue;

				if (_handlers.get(method).containsKey(event))
				{
					EventHandler handler = _handlers.get(method).get(event).newInstance();
					handler.setTracer();
					handler.setTransaction(transaction);
					return handler;
				}
				else
					return null;
			}
			else
				return null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("Get Handler Exception ");
			return null;
		}
	}

	private static void put(byte method, Integer event, Class<? extends EventHandler> value) throws Exception
	{
		if (!_handlers.containsKey(Byte.valueOf(method)))
			_handlers.put(Byte.valueOf(method), new HashMap<Integer, Class<? extends EventHandler>>());
//		if (_handlers.get(Byte.valueOf(method)).containsKey(event))
//			throw new Exception();
		_handlers.get(Byte.valueOf(method)).put(event, value);
	}
}
