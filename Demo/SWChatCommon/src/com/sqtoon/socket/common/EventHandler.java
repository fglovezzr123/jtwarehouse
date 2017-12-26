package com.sqtoon.socket.common;

import com.sqtoon.socket.common.transaction.Transaction;


public abstract class EventHandler
{
	protected Transaction _transaction;

	public abstract void handle() throws Exception;

	void setTransaction(Transaction transaction)
	{
		this._transaction = transaction;
	}

	void setTracer()
	{
	}
}
