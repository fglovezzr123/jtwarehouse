package com.sqtoon.socket.common;

import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sqtoon.socket.common.transaction.TransactionCreated;

class ConnectionManager implements IncomingConnectionCreated, IConnectionDisconnected
{
	private static Logger logger = LoggerFactory.getLogger(SocketConfig.LOGGER_MAIN);
	private int _currentKey;
	private StackMode _mode;
	private TransactionCreated _tranEvent;
	private ConcurrentHashMap<Byte, TransactionCreated> _tranEvents;
	private IConnectionCreatedEvent _connEvent;
	private SelectorManager _selector;
	private ConcurrentHashMap<SocketAddress, Connection> _connections;
	private MessageDispatcher _messageDispacher;
	private boolean _disposed;
	private ConnectionExpiredChecker _checker;

	ConnectionManager(IConnectionCreatedEvent connEvent, StackMode mode)
	{
		_currentKey = 0;
		_mode = mode;
		_connections = new ConcurrentHashMap<SocketAddress, Connection>();

		_connEvent = connEvent;
		_messageDispacher = new MessageDispatcher(mode);
		_disposed = false;

		if (_mode == StackMode.Mutiplex)
		{
			_tranEvents = new ConcurrentHashMap<Byte, TransactionCreated>();
			_checker = new ConnectionExpiredChecker(this);
			_checker.start();
		}
	}

	void registerChannelRegistered(SelectorManager selector)
	{
		_selector = selector;
	}

	void registerTransactionCreated(byte method, TransactionCreated event)
	{
		_tranEvents.put(method, event);
	}

	void registerTransactionCreated(TransactionCreated tranEvent)
	{
		_tranEvent = tranEvent;
	}

	void reBuildCinConnection(SocketAddress address)
	{
		Connection conn = getCinConnection(address);
		if (conn.getDirection())
		{
			Connection newConn = new MultiplexConnection(getNextKey(), address, true, null, _messageDispacher, _selector);
			reRegister(newConn);
		}
		else
		{
			unRegister(conn);
		}
	}

	synchronized Connection getCinConnection(SocketAddress address)
	{
		Connection conn = _connections.get(address);
		if (conn == null)
		{
			conn = new MultiplexConnection(getNextKey(), address, true, null, _messageDispacher, _selector);
			register(conn);
		}
		return conn;
	}

	void dispose() throws Exception
	{
		if (_disposed)
			return;

		if (_mode == StackMode.Mutiplex)
			_checker.Dispose();
		Collection<Connection> connections = _connections.values();
		for (Connection connection : connections)
		{
			connection.dispose();
			unRegister(connection);
		}
		_disposed = true;
	}

	boolean register(Connection connection)
	{
		boolean flag = _connections.put(connection.getRemoteAddress(), connection) == null;
		logger.info("CinConnection " + connection.toString() + " has been registered, flag: " + flag);
		return flag;
	}

	void unRegister(Connection connection)
	{
		Connection conn = _connections.get(connection.getRemoteAddress());
		if (conn == null || conn.getKey() != connection.getKey())
		{
			logger.info("CinConnection " + connection.toString() + " has been unregistered already");
			return;
		}
		boolean flag = _connections.remove(connection.getRemoteAddress()) != null;
		logger.info("CinConnection " + connection + " has been unregistered, flag: " + flag);
	}

	void reRegister(Connection conn)
	{
		_connections.replace(conn.getRemoteAddress(), conn);
	}

	ArrayList<Connection> getExpiredCinConnection(long expiredTime)
	{
		long current = System.currentTimeMillis();
		ArrayList<Connection> list = new ArrayList<Connection>();
		for (Connection conn : _connections.values())
		{
			if (conn.isExpired(current))
				list.add(conn);
		}
		return list;
	}

	synchronized int getNextKey()
	{
		return _currentKey++;
	}

	@Override
	public void onIncomingConnectionCreated(SelectionKey key, SocketChannel channel) throws Exception
	{
		Connection conn = null;
		if (_mode == StackMode.Dedicate)
		{
			conn = new DedicateConnection(getNextKey(), channel.socket().getRemoteSocketAddress(), false, channel, _messageDispacher);
			IConnectionCreatedEvent event = _connEvent;
			if (event != null)
			{
				try
				{
					event.onCinConnectionCreated(conn);
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					logger.error("onCinConnectionCreated Error.");
				}
			}
		}
		else
		{
			conn = new MultiplexConnection(getNextKey(), channel.socket().getRemoteSocketAddress(), false, channel, _messageDispacher, _selector);
			MultiplexConnection mConn = (MultiplexConnection) conn;
			for (Entry<Byte, TransactionCreated> event : _tranEvents.entrySet())
			{
				mConn.registerTransactionCreated(event.getKey(), event.getValue());
			}
			IConnectionCreatedEvent event = _connEvent;
			if (event != null)
			{
				try
				{
					event.onCinConnectionCreated(conn);
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					logger.error("onCinConnectionCreated Error.");
				}
			}
			conn.registerTransactionCreated(_tranEvent);
		}
		register(conn);
		key.attach(conn);
	}

	@Override
	public void onCinConnectionDisconnected(Connection connection)
	{
		try
		{
			unRegister(connection);
			connection.disconnected();
			logger.info("CinConnection disconnected.  " + connection.toString() + ". CinConnectiont Count: " + _connections.size());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("Handle OnCinConnectionDisconnect Error");
		}
	}
}
