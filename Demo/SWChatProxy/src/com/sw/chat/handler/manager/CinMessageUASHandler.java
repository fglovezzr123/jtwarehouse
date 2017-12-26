package com.sw.chat.handler.manager;

import com.sqtoon.socket.common.message.Header;
import com.sqtoon.socket.common.message.HeaderType;
import com.sqtoon.socket.common.message.Request;
import com.sqtoon.socket.common.message.Response;
import com.sqtoon.socket.common.message.ResponseCode;
import com.sqtoon.socket.common.transaction.Transaction;
import com.sqtoon.socket.common.transaction.TransactionEvent;
import com.sw.chat.ProxyUser;

public class CinMessageUASHandler implements TransactionEvent
{

	protected ProxyUser _userProxy;
	protected Transaction _serverTransaction;
	protected Transaction _clientTransaction;

	public void handle() throws Exception
	{
		if (!isClientReady())
		{
			System.out.println("The request from server has been hold.  " + _serverTransaction.request());
			_userProxy.getServerTransactionHolder().holdServerTransaction(_serverTransaction);
			return;
		}

		Request clientRequest = getClientRequest(_serverTransaction.request());
		_clientTransaction = _userProxy.getCinConnection().createTransaction(clientRequest);
		_clientTransaction.TransactionEvent = this;
		_clientTransaction.SendRequest();
	}

	public void initialize(ProxyUser userProxy, Transaction transaction)
	{
		_userProxy = userProxy;
		_serverTransaction = transaction;
	}

	protected Request getClientRequest(Request request)
	{
		Request clientRequst = request.clone();
		clientRequst.removeHeaders(HeaderType.Fpid);
		clientRequst.removeHeaders(HeaderType.Tpid);
		if (!clientRequst.containsHeader(HeaderType.CallId))
			clientRequst.addHeader(new Header(HeaderType.CallId, _userProxy.getNextCallId()));
		return clientRequst;
	}

	protected boolean isClientReady()
	{
		return _userProxy.getUserInfo().isClientReady();
	}

	@Override
	public void onResponseReceived(Transaction trans, Response response)
	{
		Response serverResponse = new Response(_serverTransaction.request(), response.getStatusCode());
		for (Header header : response.getHeaders())
		{
			if (header.isTypeOf(HeaderType.From) || header.isTypeOf(HeaderType.To) || header.isTypeOf(HeaderType.CallId) || header.isTypeOf(HeaderType.Csequence))
				continue;
			serverResponse.addHeader(header);
		}
		if (response.getBody() != null)
			serverResponse.addBodys(response.getBodys());
		_serverTransaction.SendResponse(serverResponse);
	}

	@Override
	public void onTimeout(Transaction trans)
	{
		System.out.println("Transaction Send Timeout     " + _clientTransaction.request());
	}

	@Override
	public void onSendFailed(Transaction trans)
	{
		try
		{
			System.out.println("Transaction Send failed     " + _clientTransaction.request());
			_userProxy.releaseCinConnection();
			_serverTransaction.SendResponse(ResponseCode.ClientOffLine);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

}
