package com.sw.chat;

import com.sqtoon.socket.common.EventHandler;
import com.sqtoon.socket.common.EventHandlerManager;
import com.sqtoon.socket.common.message.ResponseCode;
import com.sqtoon.socket.common.transaction.Transaction;
import com.sqtoon.socket.common.transaction.TransactionCreated;


public class FlcpTransactionCreated implements TransactionCreated
{
	@Override
	public void onTransactionCreated(Transaction trans)
	{
		try
		{
			EventHandler handler = EventHandlerManager.getHandler(trans);
			if (null == handler)
			{
				trans.SendResponse(ResponseCode.Error);
			}
			handler.handle();
		}
		catch (Exception e)
		{
			trans.SendResponse(ResponseCode.Error);
		}
	}
}
