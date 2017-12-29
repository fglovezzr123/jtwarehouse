package com.sw.chat;

import java.net.InetSocketAddress;
import com.sqtoon.socket.common.Stack;
import com.sqtoon.socket.common.StackMode;
import com.sqtoon.socket.common.message.Header;
import com.sqtoon.socket.common.message.HeaderType;
import com.sqtoon.socket.common.message.Request;
import com.sqtoon.socket.common.message.RequestMethod;
import com.sqtoon.socket.common.message.Response;
import com.sqtoon.socket.common.message.ResponseCode;
import com.sqtoon.socket.common.transaction.Transaction;
import com.sqtoon.socket.common.transaction.TransactionEvent;
import com.sqtoon.socket.common.transaction.TransactionManager;
import com.sw.chat.log.ChatLogDispatcher;

public class ChatEntry extends Thread
{

	public static void main(String[] args)
	{
		try
		{
			// Stack.initialize();
			final Stack _stack = new Stack(StackMode.Mutiplex, null);
			TransactionManager.Initialize();
			ChatLogDispatcher.initialize();
			// Configure.Initialize();
			// final Stack _stack = Stack.instance();
			HandlerCollector.initialize();
			// _stack.listen(Configure.center.split(":")[0],
			// Integer.parseInt(Configure.center.split(":")[1]));
			// _stack.registerDefaultTransactionCreated(new
			// FlcpTransactionCreated());
			System.out.println("SWChat started successfully!!!!");
			// challenge
			Request req = new Request(RequestMethod.Logon);
			req.addHeader(new Header(HeaderType.From, 10000));
			req.addHeader(new Header(HeaderType.Event, 0x01));
			Transaction ctrans = _stack.createShortTransaction(new InetSocketAddress("localhost", 6666), req);
			ctrans.TransactionEvent = new TransactionEvent() {

				@Override
				public void onResponseReceived(Transaction trans, Response response)
				{
					if (response.isResponseCode(ResponseCode.OK))
					{
						byte[] key = response.getHeader(HeaderType.Key).getValue();
						if (key != null)
						{
							// logon
							Request req = new Request(RequestMethod.Logon);
							req.addHeader(new Header(HeaderType.From, 10000));
							req.addHeader(new Header(HeaderType.Event, 0x02));
							Transaction ctrans = _stack.createShortTransaction(new InetSocketAddress("localhost", 6666), req);
							ctrans.TransactionEvent = new TransactionEvent() {

								@Override
								public void onResponseReceived(Transaction trans, Response response)
								{
									if (response.isResponseCode(ResponseCode.OK))
									{
										System.out.println("~~~~~~~");
										trans.bindTransactionCreated(new FlcpTransactionCreated());
										System.out.println("bind TransactionCreated success !");
									}
								}

								@Override
								public void onSendFailed(Transaction trans)
								{

								}

								@Override
								public void onTimeout(Transaction trans)
								{

								}
							};
							ctrans.SendRequest();
						}
					}
				}

				@Override
				public void onTimeout(Transaction trans)
				{
				}

				@Override
				public void onSendFailed(Transaction trans)
				{
				}

			};
			ctrans.SendRequest();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
