package com.sw.chat.handler;

import com.sqtoon.socket.common.message.Header;
import com.sqtoon.socket.common.message.HeaderType;
import com.sqtoon.socket.common.message.Request;
import com.sqtoon.socket.common.message.RequestMethod;
import com.sqtoon.socket.common.message.Response;
import com.sqtoon.socket.common.message.ResponseCode;
import com.sqtoon.socket.common.transaction.Transaction;
import com.sw.chat.handler.manager.CinMessageUACHandler;

public class LogoffUACHandler extends CinMessageUACHandler
{

	// 当因为连接断开需要给UCC发LOGOFF时，调用此构造方法，并将参数置为TRUE
	private boolean isConnectionDisconnected = false;

	/**
	 * 当因为连接断开需要给UCC发LOGOFF时，调用此方法，并将参数置为TRUE
	 * 
	 * @param isConnectionDisconnected
	 */
	public void setConnectionDisconnected(boolean isConnectionDisconnected)
	{
		this.isConnectionDisconnected = isConnectionDisconnected;
	}

	@Override
	public void handle() throws Exception
	{
		_userProxy.getUserInfo().setAuthorized(false);
		Request request = new Request(RequestMethod.Service);
		request.addHeader(new Header(HeaderType.Event, 0x01));
		Request serverRequest = createServerRequest(request);
		if (!isConnectionDisconnected)
			serverRequest.addHeader(new Header(HeaderType.ServerKey, 1));
		Transaction tran = _userProxy.getCinServerStack().createTransaction(serverRequest);
		tran.TransactionEvent = this;
		tran.SendRequest();
	}

	@Override
	public void onResponseReceived(Transaction trans, Response response)
	{

		if (response.isResponseCode(ResponseCode.OK))
		{
			if (null != _clientTransaction)
			{
				_clientTransaction.SendResponse(ResponseCode.OK);
			}
		}

	}
}