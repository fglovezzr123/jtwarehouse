package com.sw.chat;

import com.sqtoon.socket.common.Connection;
import com.sqtoon.socket.common.IConnectionDisconnected;
import com.sqtoon.socket.common.Stack;
import com.sqtoon.socket.common.message.ResponseCode;
import com.sqtoon.socket.common.transaction.Transaction;
import com.sqtoon.socket.common.transaction.TransactionCreated;
import com.sw.chat.handler.LogoffUACHandler;
import com.sw.chat.handler.manager.CinMessageUACHandler;
import com.sw.chat.handler.manager.CinMessageUASHandler;

public class ProxyUser implements TransactionCreated, IConnectionDisconnected
{

	private int _token;
	private long _expiredTime;
	private ProxyManager _manager;
	private Stack _clientStack;
	private Stack _serverStack;
	private Connection _connection;
	private UserInfo _userInfo;
	private ServerTransactionHolder _serverTransactionHolder;
	private int _callId;
	private int _cseq;
	private boolean _disposed;

	ProxyUser(int token, ProxyManager manager, Stack clientStack, Stack serverStack, Connection connection)
	{
		_token = token;
		_expiredTime = System.currentTimeMillis() + 30 * 1000;
		_manager = manager;
		_clientStack = clientStack;
		_serverStack = serverStack;
		_connection = connection;
		_userInfo = new UserInfo();
		_serverTransactionHolder = new ServerTransactionHolder(this);

		_callId = 1;
		_cseq = 1;
		_disposed = false;
		_connection.registerTransactionCreated(this);
	}

	public int getToken()
	{
		return _token;
	}

	public boolean isExpired(long time)
	{
		return time > _expiredTime;
	}

	public Stack getCinClientStack()
	{
		return _clientStack;
	}

	public Stack getCinServerStack()
	{
		return _serverStack;
	}

	public Connection getCinConnection()
	{
		return _connection;
	}

	public UserInfo getUserInfo()
	{
		return _userInfo;
	}

	public ServerTransactionHolder getServerTransactionHolder()
	{
		return _serverTransactionHolder;
	}

	public int getNextCallId()
	{
		return _callId++;
	}

	public int getNextCseq()
	{
		return _cseq++;
	}

	public synchronized void dispose()
	{
		if (_disposed)
			return;

		_connection.dispose();
		System.out.println("UserProxy has been disposed.  " + toString());
		_disposed = true;
	}

	public void receiveCinServerTransaction(Transaction transaction)
	{
		try
		{
			CinMessageUASHandler handler = CinMessageDispatcher.getCinMessageUASHandler(this, transaction);
			handler.handle();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public void onTransactionCreated(Transaction trans)
	{
		if (!_userInfo.isAuthorized())
		{
			trans.SendResponse(ResponseCode.NotAvailable);
			releaseCinConnection();
			return;
		}
		try
		{
			CinMessageUACHandler handler = CinMessageDispatcher.getCinMessageUACHandler(this, trans);
			handler.handle();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			System.out.println("onTransactionCreated Error.\r\n" + getCinConnection() == null ? "No Connection Info." : getCinConnection().toString());
		}
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Token: ").append(_token).append("\n");
		sb.append("UserId: ").append(_userInfo.getUid()).append("\n");
		sb.append("Pid: ").append(_userInfo.getPid() == null ? "NULL" : _userInfo.toString()).append("\n");
		return sb.toString();
	}

	@Override
	public void onCinConnectionDisconnected(Connection connection)
	{
		try
		{
			if (_userInfo.isAuthorized())
			{
				LogoffUACHandler handler = new LogoffUACHandler();
				handler.setConnectionDisconnected(true);
				handler.initialize(this, null);
				handler.handle();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			_manager.removeUserProxy(_token);
			System.out.println("onCinConnectionDisconnected RemoveUserProxy");
		}
	}

	public void releaseCinConnection()
	{
		_clientStack.releaseCinConnection(_connection);
	}
}
