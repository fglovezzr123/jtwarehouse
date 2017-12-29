package com.sw.chat.handler;

import com.sqtoon.socket.common.EventHandler;
import com.sqtoon.socket.common.message.Body;
import com.sqtoon.socket.common.message.Response;
import com.sqtoon.socket.common.message.ResponseCode;

public class PassActionHandler extends EventHandler
{
	@Override
	public void handle() throws Exception
	{
		// long userId = _transaction.request().From.getInt64();
		Body body = _transaction.request().getBody();
		String text = new String(body.getValue(), "UTF-8");
		System.out.println("收到的text ==== " + text);
		Response response = new Response(_transaction.request(), ResponseCode.OK);
		response.addBody(text.getBytes());
		_transaction.SendResponse(response);
	}

}
