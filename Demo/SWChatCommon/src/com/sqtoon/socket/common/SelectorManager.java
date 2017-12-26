package com.sqtoon.socket.common;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.sqtoon.socket.common.message.ParseException;

public class SelectorManager extends Thread
{
	private boolean _isRunning;
	private Listener _listener;

	private Selector _selector;
	private ConcurrentLinkedQueue<SelectorItem> _items;

	SelectorManager(String name, IncomingConnectionCreated event, IConnectionDisconnected disconnectEvent)
	{
		super(name);
		try
		{
			_selector = Selector.open();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		_items = new ConcurrentLinkedQueue<SelectorItem>();
		_listener = new Listener(event, disconnectEvent);
		setPriority(6);
		setDaemon(true);
		_isRunning = true;
		start();
	}

	void listen(String ip, int port) throws Exception
	{
		registerChannel(_listener.listen(ip, port), null, SelectionKey.OP_ACCEPT);
	}

	void dispose() throws Exception
	{
		_isRunning = false;
		_listener.close();
		_selector.close();
	}

	@Override
	public void run()
	{
		while (_isRunning)
		{
			try
			{
				while (!_items.isEmpty())
				{
					SelectorItem item = _items.poll();
					SelectionKey key = null;
					if (item.getChannel().isOpen())
					{
						key = item.getChannel().register(_selector, item.getOpt());
						key.attach(item.getConnection());
					}
				}

				if (_selector.select(100) == 0)
				{
					continue;
				}
				Iterator<SelectionKey> iter = _selector.selectedKeys().iterator();
				while (iter.hasNext())
				{
					SelectionKey key = iter.next();
					iter.remove();
					if (key.isValid())
						handleKey(key);
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}

	private void handleKey(SelectionKey key) throws Exception
	{
		try
		{
			if (key.isAcceptable())
			{
				_listener.processAccept(key);
			}
			else
				if (key.isReadable())
				{
					_listener.processRecieve(key);
				}
				else
				{
					SocketChannel sc = (SocketChannel) key.channel();
					if (!sc.isConnected())
						throw new IOException();
				}
		}
		catch (ParseException ex)
		{
			ex.printStackTrace();
			_listener.handleException(ex, key, true);
		}
		catch (IOException ex)
		{
			_listener.handleException(ex, key, false);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	void registerChannel(SelectableChannel channel, Connection connection, int opt)
	{
		SelectorItem item = new SelectorItem(channel, connection, opt);
		_items.add(item);
	}

	public class SelectorItem
	{
		private SelectableChannel _channel;
		private Connection _connection;
		private int _opt;

		SelectorItem(SelectableChannel channel, Connection connection, int opt)
		{
			_channel = channel;
			_connection = connection;
			_opt = opt;
		}

		public SelectableChannel getChannel()
		{
			return _channel;
		}

		public Connection getConnection()
		{
			return _connection;
		}

		public int getOpt()
		{
			return _opt;
		}
	}
}
