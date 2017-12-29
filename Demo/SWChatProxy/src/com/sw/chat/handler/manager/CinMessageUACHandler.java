package com.sw.chat.handler.manager;

import com.sqtoon.socket.common.message.Header;
import com.sqtoon.socket.common.message.HeaderType;
import com.sqtoon.socket.common.message.Request;
import com.sqtoon.socket.common.message.Response;
import com.sqtoon.socket.common.message.ResponseCode;
import com.sqtoon.socket.common.router.Router;
import com.sqtoon.socket.common.router.ServiceName;
import com.sqtoon.socket.common.transaction.Transaction;
import com.sqtoon.socket.common.transaction.TransactionEvent;
import com.sw.chat.CinProxyUtil;
import com.sw.chat.ProxyUser;

public class CinMessageUACHandler implements TransactionEvent
{
	protected ProxyUser _userProxy;
	protected Transaction _clientTransaction;
	protected Transaction _serverTransaction;

	public void handle() throws Exception
	{
		Request serverRequest = createServerRequest(_clientTransaction.request());
		_serverTransaction = _userProxy.getCinServerStack().createTransaction(serverRequest);
		_serverTransaction.TransactionEvent = this;
		_serverTransaction.SendRequest();
	}

	public void initialize(ProxyUser userProxy, Transaction transaction)
	{
		_userProxy = userProxy;
		_clientTransaction = transaction;
	}

	protected Request createServerRequest(Request request)
	{
		ServiceName serviceName = CinProxyUtil.getCinServiceName(request.getMethod());
		return createServerRequest(request, serviceName);
	}

	protected Request createServerRequest(Request request, ServiceName serviceName)
	{
		Request serverRequest = request.clone();
		serverRequest.removeHeaders(HeaderType.From);
		serverRequest.addHeader(new Header(HeaderType.From, _userProxy.getUserInfo().getUid()));
		serverRequest.addHeader(new Header(HeaderType.Fpid, _userProxy.getUserInfo().getPid().getBytes()));
		serverRequest.addHeader(new Header(HeaderType.Language, _userProxy.getUserInfo().getLanguage()));
		serverRequest.removeHeaders(HeaderType.CallId);
		serverRequest.addHeader(new Header(HeaderType.CallId, _userProxy.getNextCallId()));

		if (serviceName != null)
			Router.setRoute(serverRequest, serviceName);

		return serverRequest;
	}

	protected Response createClientResponse(byte code)
	{
		Response clientResponse = new Response(_clientTransaction.request(), code);
		return clientResponse;
	}

	@Override
	public void onResponseReceived(Transaction trans, Response response)
	{
		Response clientResponse = createClientResponse(response.getStatusCode());
		for (Header header : response.getHeaders())
		{
			if (header.isTypeOf(HeaderType.From) || header.isTypeOf(HeaderType.Fpid) || header.isTypeOf(HeaderType.To) || header.isTypeOf(HeaderType.Tpid) || header.isTypeOf(HeaderType.CallId) || header.isTypeOf(HeaderType.Csequence))
				continue;
			clientResponse.addHeader(header);
		}
		if (response.getBody() != null)
			clientResponse.addBodys(response.getBodys());
		_clientTransaction.SendResponse(clientResponse);
	}

	@Override
	public void onTimeout(Transaction trans)
	{
		System.out.println("Transaction Send Timeout   " + trans.request());
	}

	@Override
	public void onSendFailed(Transaction trans)
	{
		System.out.println("Transaction Send Failed    " + trans.request());
		_clientTransaction.SendResponse(ResponseCode.Busy);
	}
}
