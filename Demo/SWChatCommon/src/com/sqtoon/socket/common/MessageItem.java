package com.sqtoon.socket.common;

import com.sqtoon.socket.common.message.Request;
import com.sqtoon.socket.common.message.Response;
import com.sqtoon.socket.common.transaction.TransactionManager;


class MessageItem
{
	private Request _request;
	private Response _response;
	private TransactionManager _manager;
	private int _connectionKey;

	MessageItem(Request request, Response response, TransactionManager manager, int connectionKey)
	{
		_request = request;
		_response = response;
		_manager = manager;
		_connectionKey = connectionKey;
	}

	Request GetCinRequest()
	{
		return _request;
	}

	Response GetCinResponse()
	{
		return _response;
	}

	boolean HasCinRequest()
	{
		return _request != null;
	}

	TransactionManager GetCinTransactionManager()
	{
		return _manager;
	}

	int GetConnectionKey()
	{
		return _connectionKey;
	}
}