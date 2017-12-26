package com.sqtoon.socket.common;

import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sqtoon.socket.common.message.Header;
import com.sqtoon.socket.common.message.HeaderType;
import com.sqtoon.socket.common.message.Message;
import com.sqtoon.socket.common.message.MessageParser;
import com.sqtoon.socket.common.message.Request;
import com.sqtoon.socket.common.message.Response;
import com.sqtoon.socket.common.transaction.Transaction;
import com.sqtoon.socket.common.transaction.TransactionCreated;
import com.sqtoon.socket.common.util.Convert;

class DedicateConnection extends Connection
{
	private static Logger logger = LoggerFactory.getLogger(SocketConfig.LOGGER_MAIN);
	private long _upNetworkTraffic;
	private long _downNetworkTraffic;
	private short _sendCounter;

	DedicateConnection(int key, SocketAddress remoteAddress, boolean directiion, SocketChannel channel, MessageDispatcher dispacher)
	{
		super(key, remoteAddress, directiion, channel, dispacher);
		_type = ConnectionType.Dedicate;
		_parser = new MessageParser(true);
		_upNetworkTraffic = 0;
		_downNetworkTraffic = 0;
		_sendCounter = 0;
	}

	@Override
	protected synchronized void sendData(Message message)
	{
		try
		{
			CleanWithSendWhitelist(message);
			ByteBuffer data = message.toByteBuffer();
			_sendCounter = 0;
			while (data.hasRemaining())
			{
				if (_channel.write(data) == 0)
				{
					_sendCounter++;
					Thread.sleep(50);
				}
				else
					_sendCounter = 0;
				if (_sendCounter > 256)
				{
					_channel.close();
				}

				logger.info(Convert.bytes2String(data.array()));
			}
			_downNetworkTraffic += data.limit();
			logger.info("CinMessage has been sent by " + toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("SendData Error. " + toString());
		}
	}

	@Override
	protected void receiveData(ByteBuffer data) throws Exception
	{
		_upNetworkTraffic += data.limit();
		LinkedList<Message> list = _parser.parse(data);
		if (list == null)
			return;
		for (Message message : list)
		{
			MessageItem item = null;
			if (message instanceof Request)
			{
				Request request = (Request) message;
				logger.info("CinRequest has been received. " + toString());
				CleanWithRecvWhiteList(message);
				item = new MessageItem(request, null, _txManager, getKey());
			}
			else
				if (message instanceof Response)
				{
					Response response = (Response) message;
					logger.info("CinResponse has been received. " + toString());
					CleanWithRecvWhiteList(message);
					item = new MessageItem(null, response, _txManager, getKey());
				}
			if (item != null)
				_dispacher.receiveCinMessageItem(item);
		}
	}

	private void CleanWithSendWhitelist(Message msg)
	{
		Iterator<Header> iterator = msg.getHeaders().iterator();
		while (iterator.hasNext())
		{
			Header s = iterator.next();
			switch (s.getType())
			{
				case HeaderType.From:
				case HeaderType.To:
				case HeaderType.CallId:
				case HeaderType.Csequence:
				case HeaderType.MessageID:
				case HeaderType.DateTime:
				case HeaderType.Event:
				case HeaderType.Key:
					continue;
				default:
					msg.removeHeaders(s.getType());
			}
		}
		msg.Fpid = null;
		msg.Tpid = null;
	}

	private void CleanWithRecvWhiteList(Message msg)
	{
		Iterator<Header> iterator = msg.getHeaders().iterator();
		while (iterator.hasNext())
		{
			Header s = iterator.next();
			switch (s.getType())
			{
				case HeaderType.From:
				case HeaderType.To:
				case HeaderType.CallId:
				case HeaderType.Csequence:
				case HeaderType.MessageID:
				case HeaderType.DateTime:
				case HeaderType.Event:
				case HeaderType.Key:
					continue;
				default:
					msg.removeHeaders(s.getType());
			}
		}
		msg.Fpid = null;
		msg.Tpid = null;
	}

	@Override
	public String toString()
	{
		Socket socket = _channel.socket();
		if (socket == null)
			return "Socket is null. direction: " + _direction + ", mode: Dedicate, key: " + getKey();
		String l = socket.getLocalSocketAddress() == null ? "null" : socket.getLocalSocketAddress().toString();
		String r = socket.getRemoteSocketAddress() == null ? "null" : socket.getRemoteSocketAddress().toString();
		return "L" + l + " - R" + r + ", direction: " + _direction + ", mode: Dedicate, key: " + getKey();
	}

	@Override
	boolean isExpired(long expiredTime)
	{
		return false;
	}

	@Override
	boolean canDispose()
	{
		return false;
	}

	@Override
	public void onTransactionCreated(Transaction trans)
	{
		TransactionCreated event = _tranCreated;
		if (event != null)
			event.onTransactionCreated(trans);
	}

	public long getUpNetworkTraffic()
	{
		return _upNetworkTraffic;
	}

	public long getDownNetworkTraffic()
	{
		return _downNetworkTraffic;
	}
}