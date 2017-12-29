package com.sw.chat;

import com.sqtoon.socket.common.EventHandlerManager;
import com.sqtoon.socket.common.ServiceEvent;
import com.sqtoon.socket.common.message.RequestMethod;
import com.sw.chat.handler.PassActionHandler;

public class HandlerCollector
{

	public static void initialize() throws Exception
	{
		EventHandlerManager.initialize(RequestMethod.Message, ServiceEvent.PassAction, PassActionHandler.class);
	}
}
