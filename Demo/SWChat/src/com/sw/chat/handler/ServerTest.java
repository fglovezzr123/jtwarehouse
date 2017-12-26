package com.sw.chat.handler;

import java.net.InetSocketAddress;
import com.sqtoon.socket.common.ServiceEvent;
import com.sqtoon.socket.common.Stack;
import com.sqtoon.socket.common.message.Header;
import com.sqtoon.socket.common.message.HeaderType;
import com.sqtoon.socket.common.message.Request;
import com.sqtoon.socket.common.message.RequestMethod;
import com.sqtoon.socket.common.message.Response;
import com.sqtoon.socket.common.message.ResponseCode;
import com.sqtoon.socket.common.transaction.Transaction;
import com.sqtoon.socket.common.transaction.TransactionEvent;
import com.sqtoon.socket.common.transaction.TransactionManager;
import com.sqtoon.socket.common.util.Convert;

public class ServerTest
{
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		try
		{
			Stack.initialize();
			TransactionManager.Initialize();
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		Request req = new Request(RequestMethod.Message);
//		Request req = new Request(RequestMethod.Logon);
		req.addHeader(new Header(HeaderType.From, 10001));
		req.addHeader(new Header(HeaderType.To, 10010));
		req.addHeader(new Header(HeaderType.Tpid, Convert.hexToBytes("7F0000011B9F0000014CB789985800000012")));
		req.addHeader(new Header(HeaderType.Event, ServiceEvent.PassAction));
//		req.addHeader(new Header(HeaderType.Event, 0x03));
		req.addBody("Server push message !".getBytes());
		// System.out.println(CinConvert.bytes2String(req.toBytes()));
		// Transaction ctrans = Stack.instance().createShortTransaction(new
		// InetSocketAddress("localhost", 7071), req);
		Transaction ctrans = Stack.instance().createShortTransaction(new InetSocketAddress("localhost", 8888), req);
		ctrans.TransactionEvent = new TransactionEvent() {

			@Override
			public void onResponseReceived(Transaction trans, Response response)
			{
				if (response.isResponseCode(ResponseCode.OK))
				{
					System.out.println(" send message ok !~~~~~~");
					// try
					// {
					// Body body = response.getBody();
					// if (body != null)
					// System.out.println(new String(body.getValue(), "UTF-8"));
					// }
					// catch (UnsupportedEncodingException e)
					// {
					// e.printStackTrace();
					// }
				}
			}

			@Override
			public void onTimeout(Transaction trans)
			{
				System.out.println(" send message timeout !");
			}

			@Override
			public void onSendFailed(Transaction trans)
			{
				System.out.println(" send message sendfailed !");
			}

		};
		ctrans.SendRequest();
	}
}
