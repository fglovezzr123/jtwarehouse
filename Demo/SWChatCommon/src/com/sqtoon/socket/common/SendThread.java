package com.sqtoon.socket.common;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sqtoon.socket.common.message.Message;
import com.sqtoon.socket.common.message.MessageType;
import com.sqtoon.socket.common.util.CinLinkedList;
import com.sqtoon.socket.common.util.CinLinkedNode;

public class SendThread extends Thread
{
	private static Logger logger = LoggerFactory.getLogger(SocketConfig.LOGGER_MAIN);
	private static final int WaitTime = 500;

	private SocketAddress _remoteAddress;
	private boolean _direction;
	private SocketChannel _channel;
	private SelectorManager _selector;
	private Connection _connection;
	private boolean _isRunning;
	private CinLinkedList<Message> _messages;
	private Object _syncRoot;

	SendThread(String name, SocketAddress remoteAddress, boolean direction, SocketChannel channel, SelectorManager selector, Connection connection)
	{
		super(name);
		_remoteAddress = remoteAddress;
		_direction = direction;
		_channel = channel;
		_selector = selector;
		_connection = connection;

		_isRunning = true;
		_messages = new CinLinkedList<Message>();
		_syncRoot = new Object();
		setDaemon(true);
		start();
	}

	void sendCinMessage(Message message)
	{
		if (!_isRunning)
			return;
		_messages.put(message);
		try
		{
			synchronized (_syncRoot)
			{
				_syncRoot.notify();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		try
		{
			while (_isRunning)
			{
				try
				{
					processCinMessage();
					synchronized (_syncRoot)
					{
						_syncRoot.wait(WaitTime);
					}
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					logger.error("While in SendThread.run Error");
				}
			}
			processCinMessage();
			if (_channel != null && _channel.isConnected())
				_channel.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("SendThread.run Error");
		}
	}

	private void processCinMessage()
	{
		_messages.moveToHead();
		CinLinkedNode<Message> node = null;
		while (true)
		{
			node = _messages.takeAwayFirst();
			if (node != null)
			{
				if (node.object() != null)
					sendData(node.object());
			}
			else
			{
				break;
			}
		}
	}

	private synchronized void sendData(Message message)
	{
		try
		{
			connect();
			ByteBuffer data = message.toByteBuffer();
			while (data.hasRemaining() && _channel.write(data) != -1)
				;
			StringBuilder sb = new StringBuilder();
			sb.append("CinMessage has been sent by: ");
			sb.append(_connection.toString() + "\n");
			sb.append(message.toString() + "\n");
			logger.info(sb.toString());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			StringBuilder sb = new StringBuilder();
			sb.append("CinMessage sent failed. from: ");
			sb.append(_connection.toString());
			logger.info(sb.toString() + "\n");
			if (message.isMessageType(MessageType.CinRequest))
			{
				if (message.getParentTrans() != null)
					message.getParentTrans().DoSendFailed();
			}
		}
	}

	private void connect()
	{
		if (!_direction)
			return;

		if (_channel != null && _channel.isConnected())
			return;

		try
		{
			logger.info("Try to connect " + _remoteAddress.toString());
			if (_channel != null)
				_channel.close();
			_channel = SocketChannel.open();
			_channel.connect(_remoteAddress);
			_channel.configureBlocking(false);
			_channel.socket().setTcpNoDelay(true);
			_connection.setChannel(_channel);
			_selector.registerChannel(_channel, _connection, SelectionKey.OP_READ);
			StringBuilder sb = new StringBuilder();
			sb.append("TCP connection has been established. L");
			sb.append(_channel.socket().getLocalSocketAddress().toString());
			sb.append(" - R");
			sb.append(_channel.socket().getRemoteSocketAddress().toString());
			logger.info(sb.toString());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	void dispose()
	{
		_isRunning = false;
		logger.info(getName() + " has been disposed.");
	}
}
