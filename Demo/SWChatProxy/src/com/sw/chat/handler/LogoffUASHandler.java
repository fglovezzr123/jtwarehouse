package com.sw.chat.handler;

import com.sqtoon.socket.common.message.HeaderType;
import com.sqtoon.socket.common.message.Request;
import com.sqtoon.socket.common.message.ResponseCode;
import com.sw.chat.handler.manager.CinMessageUASHandler;

public class LogoffUASHandler extends CinMessageUASHandler
{

	@Override
	public void handle() throws Exception
	{
		_userProxy.getUserInfo().setAuthorized(false);
		_serverTransaction.SendResponse(ResponseCode.OK);

		// 没有Status头，表示只断链接，不给客户端下Logoff信令
		if (_serverTransaction.request().containsHeader(HeaderType.Status))
		{
			Request clientRequest = getClientRequest(_serverTransaction.request());
			_clientTransaction = _userProxy.getCinConnection().createTransaction(clientRequest);
			_clientTransaction.SendRequest();
		}
		_userProxy.releaseCinConnection();
	}

}