package com.sqtoon.socket.common.transaction;

import com.sqtoon.socket.common.Connection;
import com.sqtoon.socket.common.ConnectionType;
import com.sqtoon.socket.common.message.Request;
import com.sqtoon.socket.common.message.Response;
import com.sqtoon.socket.common.message.ResponseCode;
import com.sqtoon.socket.common.util.CinHashMap;
import com.sqtoon.socket.common.util.CinLinkedList;
import com.sqtoon.socket.common.util.CinLinkedNode;

public class TransactionManager
{

	private CinHashMap<String, Transaction> _shortTrans;
	private CinHashMap<String, Transaction> _transactions;
	private static CinLinkedList<TransactionManager> _managers;
	private Connection _connection;
	private TransactionCreated _event;
	private static Thread _cleanner;
	private CinLinkedNode<TransactionManager> _node;
	private long _lastCleanTime = System.currentTimeMillis();

	public TransactionManager(Connection connection, TransactionCreated event)
	{
		_transactions = new CinHashMap<String, Transaction>();
		_shortTrans = new CinHashMap<String, Transaction>();
		_connection = connection;
		_event = event;
		if (_managers == null)
			_managers = new CinLinkedList<TransactionManager>();
		_node = _managers.put(this);
	}

	public static void Initialize() throws Exception
	{
		if (_cleanner == null)
		{
			_managers = new CinLinkedList<TransactionManager>();
			_cleanner = new TransactionCleanner();
			_cleanner.start();
		}
	}

	public Transaction CreateTransaction(Request request, boolean direction)
	{
		Transaction trans = new Transaction(request, direction, this);
		if (direction)
			this.addTransaction(trans);
		return trans;
	}

	public Transaction CreateShortTransaction(Request request)
	{
		Transaction trans = new Transaction(request, true, this);
		this.addShortTransaction(trans);
		return trans;
	}

	public Connection getConnection()
	{
		return _connection;
	}

	private void addTransaction(Transaction trans)
	{
		_transactions.add(trans.getKey(), trans);
	}

	private void addShortTransaction(Transaction trans)
	{
		_shortTrans.add(trans.getKey(), trans);
	}

	public static void cleanExpiredTransaction()
	{
		_managers.moveToHead();
		while (true)
		{
			CinLinkedNode<TransactionManager> node = _managers.get();
			if (node == null)
				break;
			if (node.object() != null)
			{
				clearShortTransaction(node.object());
				if (node.object().isNeedCleanLongTrans())
					clearExpireTransaction(node.object());
			}
		}
	}

	private static void clearExpireTransaction(TransactionManager manager)
	{
		long millionSeconds = TransactionConfig.expiredTimes();
		manager._transactions.linkedListMoveToHead();
		while (true)
		{
			Transaction trans = manager._transactions.linkedListGet();
			if (trans == null)
				break;
			if (trans.isExpired(millionSeconds))
			{
				trans.DoTimeoutArgs();
			}
			else
				break;
		}
	}

	private static void clearShortTransaction(TransactionManager manager)
	{
		long millionSeconds = TransactionConfig.shortExpiredTimes();
		manager._shortTrans.linkedListMoveToHead();
		while (true)
		{
			Transaction trans = manager._shortTrans.linkedListGet();
			if (trans == null)
				break;
			if (trans.isExpired(millionSeconds))
			{
				trans.DoTimeoutArgs();
			}
			else
				break;
		}
	}

	public Transaction getTransaction(String key)
	{
		Transaction v = _transactions.takeAway(key);

		if (v == null)
			return _shortTrans.takeAway(key);
		else
			return v;
	}

	public void removeTransaction(String TransactionKey)
	{
		if (_shortTrans.remove(TransactionKey) == null)
			_transactions.remove(TransactionKey);
	}

	public void dispose()
	{
		_transactions.linkedListMoveToHead();
		while (true)
		{
			Transaction trans = _transactions.linkedListGet();
			if (trans == null)
				break;
			trans.DoTimeoutArgs();
		}

		_managers.remove(_node);
	}

	public void OnRequestReceived(Request request)
	{
		TransactionCreated event = _event;
		if (event != null)
		{
			Transaction trans = CreateTransaction(request, false);
			event.onTransactionCreated(trans);
		}
	}

	public void OnResponseReceived(Response response)
	{
		Transaction trans = null;
		trans = getTransaction(response.getKey(true));
		if (trans != null)
		{
			if (response.isResponseCode(ResponseCode.Pending))
			{
				if (_connection.getType() == ConnectionType.Dedicate)
				{
					_connection.dispose();
					return;
				}
				trans.updateTransaction();
				addTransaction(trans);
				trans.saveBodies(response.getBodys());
				return;
			}
			trans.receivceResponse(response);
			trans.DoResponseReceive();
		}
		else
		{

		}
	}

	private boolean isNeedCleanLongTrans()
	{
		long currentTime = System.currentTimeMillis();
		if ((currentTime - _lastCleanTime) < TransactionConfig.getCleanLongTransTime())
			return false;
		else
		{
			_lastCleanTime = currentTime;
			return true;
		}
	}
}
