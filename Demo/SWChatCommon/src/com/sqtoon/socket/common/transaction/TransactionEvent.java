package com.sqtoon.socket.common.transaction;

import com.sqtoon.socket.common.message.Response;


public interface TransactionEvent
{
	/**
	 * Sent request after receiving the response of the callback processing logic interface
	 * 
	 * @param trans
	 *            事务
	 * @param response
	 *            应答
	 */
	void onResponseReceived(Transaction trans, Response response);

	/**
	 * Request failed callback processing logic interface
	 * 
	 * @param trans
	 */
	void onSendFailed(Transaction trans);

	/**
	 * Request timeout callback processing logic interface
	 * 
	 * @param trans
	 */
	void onTimeout(Transaction trans);
}
