package com.sw.chat;

import java.util.concurrent.ConcurrentLinkedQueue;
import com.sqtoon.socket.common.transaction.Transaction;

public class ServerTransactionHolder
{

	private ProxyUser _userProxy;
	private ConcurrentLinkedQueue<Transaction> _transactions;

	ServerTransactionHolder(ProxyUser userProxy)
	{
		_userProxy = userProxy;
		_transactions = new ConcurrentLinkedQueue<Transaction>();
	}

	public boolean holdServerTransaction(Transaction transaction)
	{
		if (!_userProxy.getUserInfo().isClientReady())
		{
			_transactions.add(transaction);
			return true;
		}
		return false;
	}

	public void flushServerTransactions()
	{
		while (!_transactions.isEmpty())
		{
			try
			{
				Transaction transaction = _transactions.poll();
				_userProxy.receiveCinServerTransaction(transaction);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
}
