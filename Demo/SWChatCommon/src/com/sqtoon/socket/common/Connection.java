package com.sqtoon.socket.common;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sqtoon.socket.common.message.Message;
import com.sqtoon.socket.common.message.MessageParser;
import com.sqtoon.socket.common.message.Request;
import com.sqtoon.socket.common.message.Response;
import com.sqtoon.socket.common.transaction.Transaction;
import com.sqtoon.socket.common.transaction.TransactionCreated;
import com.sqtoon.socket.common.transaction.TransactionManager;

public abstract class Connection implements TransactionCreated
{
	private static Logger logger = LoggerFactory.getLogger(SocketConfig.LOGGER_MAIN);
	private int _key;
	private SocketAddress _remoteAddress;
	protected boolean _direction;
	protected SocketChannel _channel;
	protected MessageDispatcher _dispacher;
	private IConnectionDisconnected _connDisconected;
	protected TransactionCreated _tranCreated;

	private ByteBuffer _buffer;
	protected ConnectionType _type;
	protected MessageParser _parser;
	protected TransactionManager _txManager;
	protected long _expiredTime;
	protected long _updateTime;

	Connection(int key, SocketAddress remoteAddress, boolean direction, SocketChannel channel, MessageDispatcher dispacher)
	{
		_key = key;
		_remoteAddress = remoteAddress;
		_direction = direction;
		_channel = channel;
		_dispacher = dispacher;

		_buffer = ByteBuffer.allocate(1024);
		_txManager = new TransactionManager(this, this);
		_expiredTime = System.currentTimeMillis() + 540000;
		_updateTime = System.currentTimeMillis();
	}

	void setChannel(SocketChannel channel)
	{
		_channel = channel;
	}

	public Transaction createTransaction(Request request)
	{
		return _txManager.CreateTransaction(request, true);
	}

	public Transaction createShortTransaction(Request request)
	{
		return _txManager.CreateShortTransaction(request);
	}

	public void dispose()
	{
		try
		{
			_tranCreated = null;
			_connDisconected = null;
			if (_channel != null)
				_channel.close();
			_txManager.dispose();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("CinConnection.Dispose()");
		}
	}

	public int getKey()
	{
		return _key;
	}

	public SocketAddress getRemoteAddress()
	{
		return _remoteAddress;
	}

	public InetAddress getRemoteIP()
	{
		try
		{
			return InetAddress.getByName(_remoteAddress.toString().split(":")[0].substring(1));
		}
		catch (Exception ex)
		{
			try
			{
				return InetAddress.getByName("0.0.0.0");
			}
			catch (Exception e)
			{
				return null;
			}
		}
	}

	public boolean getDirection()
	{
		return _direction;
	}

	abstract boolean isExpired(long expiredTime);

	abstract boolean canDispose();

	public void registerDisconnected(IConnectionDisconnected event)
	{
		_connDisconected = event;
	}

	public void registerTransactionCreated(TransactionCreated event)
	{
		_tranCreated = event;
	}

	void receiveData() throws Exception
	{
		if (_channel == null || !_channel.isOpen())
		{
			return;
		}

		int count = _channel.read(_buffer);
		if (count == -1)
		{
			_channel.close();
			throw new IOException("Connection has been terminated by the peer set.");
		}
		while (count > 0)
		{
			_buffer.flip();
			receiveData(_buffer);
			_buffer.clear();
			count = _channel.read(_buffer);
		}
	}

	/**
	 * Receive data
	 * 
	 * @param data
	 * @throws Exception
	 */
	protected abstract void receiveData(ByteBuffer data) throws Exception;

	/**
	 * Send the request on the connection
	 * 
	 * @param request
	 * @throws Exception
	 */
	public void sendRequest(Request request) throws Exception
	{
		sendData(request);
		_updateTime = System.currentTimeMillis();
	}

	public void sendResponse(Response response) throws Exception
	{
		sendData(response);
	}

	protected abstract void sendData(Message message);

	public abstract void onTransactionCreated(Transaction trans);

	void disconnected()
	{
		IConnectionDisconnected event = _connDisconected;
		if (event != null)
			event.onCinConnectionDisconnected(this);
		dispose();
	}

	public abstract long getUpNetworkTraffic();

	public abstract long getDownNetworkTraffic();

	public ConnectionType getType()
	{
		return _type;
	}
}
