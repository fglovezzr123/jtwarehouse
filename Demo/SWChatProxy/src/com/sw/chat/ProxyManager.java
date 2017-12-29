package com.sw.chat;

import java.util.concurrent.ConcurrentLinkedQueue;
import com.sqtoon.socket.common.Connection;
import com.sqtoon.socket.common.IConnectionCreatedEvent;
import com.sqtoon.socket.common.PersonalId;
import com.sqtoon.socket.common.Stack;
import com.sqtoon.socket.common.message.HeaderType;
import com.sqtoon.socket.common.message.Request;
import com.sqtoon.socket.common.message.ResponseCode;
import com.sqtoon.socket.common.transaction.Transaction;
import com.sqtoon.socket.common.transaction.TransactionCreated;
import com.sqtoon.socket.common.util.CinLinkedList;
import com.sqtoon.socket.common.util.CinLinkedNode;

class ProxyManager extends Thread implements IConnectionCreatedEvent, TransactionCreated
{
	private static final int MAXUSERCOUNT = 100000;

	private boolean _disposed;
	private boolean _isRunning;
	private ConcurrentLinkedQueue<ProxyUser> _notLogonUsers;
	private Stack _clientStack;
	private Stack _serverStack;

	private CinLinkedList<Integer> _tokenList;
	private ProxyUser[] _userProxies;

	ProxyManager()
	{
		super("UserProxyManager");
		_disposed = false;
		_isRunning = true;
		_notLogonUsers = new ConcurrentLinkedQueue<ProxyUser>();
		_tokenList = createTokenList(MAXUSERCOUNT);
		_userProxies = new ProxyUser[MAXUSERCOUNT];
		start();
	}

	void initialize(Stack clientStack, Stack serverStack)
	{
		_clientStack = clientStack;
		_serverStack = serverStack;
	}

	ProxyUser GetUserProxy(PersonalId pid)
	{
		int connectionId = pid.getConnectionId();
		ProxyUser proxy = _userProxies[connectionId];
		if (proxy != null && proxy.getUserInfo() != null && proxy.getUserInfo().getPid() != null && pid.equals(proxy.getUserInfo().getPid()))
		{
			return proxy;
		}
		return null;
	}

	ProxyUser greateUserProxy(Connection connection) throws Exception
	{
		CinLinkedNode<Integer> token = _tokenList.takeAwayFirst();
		if (token == null)
			return null;

		_userProxies[token.object()] = new ProxyUser(token.object(), this, _clientStack, _serverStack, connection);
		System.out.println("New UserProxy has been created.  " + _userProxies[token.object()].toString());
		return _userProxies[token.object()];
	}

	synchronized void removeUserProxy(int token)
	{
		try
		{
			ProxyUser userProxy = _userProxies[token];
			if (userProxy == null)
				return;

			userProxy.dispose();
			_userProxies[token] = null;
			_tokenList.put(token);
			System.out.println("UserProxy has been removed. " + userProxy);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private CinLinkedList<Integer> createTokenList(int length)
	{
		CinLinkedList<Integer> tokenList = new CinLinkedList<Integer>();
		for (int i = 0; i < length; i++)
		{
			tokenList.put(i);
		}
		tokenList.moveToHead();
		return tokenList;
	}

	void dispose()
	{
		if (_disposed)
			return;

		_isRunning = false;
		_disposed = true;
	}

	@Override
	public void run()
	{
		while (_isRunning)
		{
			try
			{
				Thread.sleep(10 * 1000);
				long current = System.currentTimeMillis();
				while (!_notLogonUsers.isEmpty())
				{
					ProxyUser userProxy = _notLogonUsers.peek();
					if (!userProxy.isExpired(current))
						break;

					_notLogonUsers.poll();
					if (!userProxy.getUserInfo().isAuthorized())
					{
						removeUserProxy(userProxy.getToken());
						System.out.println("UserProxy unauthorized RemoveUserProxy");
					}
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void onTransactionCreated(Transaction trans)
	{
		Request request = trans.request();
		if (request == null)
			return;
		if (request.To == null || request.Tpid == null)
		{
			trans.SendResponse(ResponseCode.ClientOffLine);
			return;
		}

		PersonalId pid = PersonalId.parse(request.getHeader(HeaderType.Tpid).getValue());
		ProxyUser userProxy = GetUserProxy(pid);
		if (userProxy == null)
		{
			trans.SendResponse(ResponseCode.ClientOffLine);
			return;
		}
		userProxy.receiveCinServerTransaction(trans);
	}

	@Override
	public void onCinConnectionCreated(Connection connection) throws Exception
	{
		ProxyUser userPorxy = greateUserProxy(connection);
		if (userPorxy == null)
		{
			System.out.println("Exceed max capability. ConnectionKey: " + connection.getKey());
			connection.dispose();
			return;
		}
		_notLogonUsers.add(userPorxy);
		System.out.println("Current Not LogonUsers connection Count: " + _notLogonUsers.size());
		connection.registerTransactionCreated(userPorxy);
		connection.registerDisconnected(userPorxy);
	}
}