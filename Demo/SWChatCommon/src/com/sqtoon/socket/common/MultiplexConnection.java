package com.sqtoon.socket.common;

import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sqtoon.socket.common.message.Message;
import com.sqtoon.socket.common.message.MessageParser;
import com.sqtoon.socket.common.message.Request;
import com.sqtoon.socket.common.message.Response;
import com.sqtoon.socket.common.transaction.Transaction;
import com.sqtoon.socket.common.transaction.TransactionConfig;
import com.sqtoon.socket.common.transaction.TransactionCreated;

class MultiplexConnection extends Connection
{
	private static Logger logger = LoggerFactory.getLogger(SocketConfig.LOGGER_MAIN);
	private ConcurrentHashMap<Byte, TransactionCreated> _tranEvents;
	private SendThread _sender;

	MultiplexConnection(int key, SocketAddress remoteAddress, boolean direction, SocketChannel channel, MessageDispatcher dispacher, SelectorManager selector)
	{
		super(key, remoteAddress, direction, channel, dispacher);
		_type = ConnectionType.Multiplex;
		_parser = new MessageParser(false);
		_tranEvents = new ConcurrentHashMap<Byte, TransactionCreated>();
		_sender = new SendThread("SendThread - " + getRemoteAddress() + " - " + System.currentTimeMillis(), getRemoteAddress(), _direction, channel, selector, this);
	}

	void registerTransactionCreated(byte method, TransactionCreated event)
	{
		_tranEvents.put(Byte.valueOf(method), event);
	}

	@Override
	protected void sendData(Message message)
	{
		_sender.sendCinMessage(message);
	}

	@Override
	protected void receiveData(ByteBuffer data) throws Exception
	{
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
				logger.info(message.toString() + "\n");
				item = new MessageItem(request, null, _txManager, getKey());
			}
			else
				if (message instanceof Response)
				{
					Response response = (Response) message;
					logger.info("CinResponse has been received. " + toString());
					logger.info(message.toString() + "\n");
					item = new MessageItem(null, response, _txManager, getKey());
				}
			if (item != null)
				_dispacher.receiveCinMessageItem(item);
		}
	}

	@Override
	boolean isExpired(long expiredTime)
	{
		if (!_direction)
		{
			if (expiredTime > _expiredTime + 10 * 60 * 1000)
			{
				logger.info("Incoming Mutiplex Connection '" + getRemoteAddress() + "' is over the expired time more than 10 minute, " + "and it will be terminated later forcely");
				return true;
			}
			else
				return false;
		}
		if (_channel != null && !_channel.isConnected())
			return true;
		return _expiredTime < expiredTime;
	}

	@Override
	boolean canDispose()
	{
		long now = System.currentTimeMillis();
		if (!isExpired(now))
			return false;
		if (_updateTime + TransactionConfig.expiredTimes() < now)
			return true;
		return false;
	}

	@Override
	public String toString()
	{
		if (_channel == null)
			return "Channel is null. direction: " + _direction + ", mode: Multiplex, key: " + getKey();
		Socket socket = _channel.socket();
		if (socket == null)
			return "Socket is null. direction: " + _direction + ", mode: Multiplex, key: " + getKey();
		String l = socket.getLocalSocketAddress() == null ? "null" : socket.getLocalSocketAddress().toString();
		String r = socket.getRemoteSocketAddress() == null ? "null" : socket.getRemoteSocketAddress().toString();
		return "L" + l + " - R" + r + ", direction: " + _direction + ", mode: Multiplex, key: " + getKey();
	}

	@Override
	public void onTransactionCreated(Transaction trans)
	{
		TransactionCreated event = _tranEvents.get(trans.request().getMethod());
		if (event == null)
			event = _tranCreated;

		if (event != null)
			event.onTransactionCreated(trans);
	}

	@Override
	public void dispose()
	{
		_sender.dispose();
		super.dispose();
	}

	@Override
	public long getUpNetworkTraffic()
	{
		return 0;
	}

	@Override
	public long getDownNetworkTraffic()
	{
		return 0;
	}
}
