package com.sqtoon.socket.common;

import java.util.ArrayList;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ConnectionExpiredChecker extends Thread
{
	private static Logger logger = LoggerFactory.getLogger(SocketConfig.LOGGER_MAIN);
	private static final int WaitTime = 20 * 1000;

	private ConnectionManager _manager;
	private LinkedList<Connection> _queue;
	private boolean _isRunning;
	private boolean _disposed;

	ConnectionExpiredChecker(ConnectionManager manager)
	{
		super("CinConnectionExpiredChecker");
		setDaemon(true);
		_manager = manager;

		_queue = new LinkedList<Connection>();
		_isRunning = true;
		_disposed = false;
	}

	@Override
	public void run()
	{
		while (_isRunning)
		{
			try
			{
				Thread.sleep(WaitTime);

				while (!_queue.isEmpty())
				{
					Connection conn = _queue.peek();
					if (conn.canDispose())
					{

						logger.info("Connection " + conn.toString() + " is ready to be disposed.");
						conn.dispose();
						_queue.poll();
					}
					else
					{
						break;
					}
				}

				ArrayList<Connection> list = _manager.getExpiredCinConnection(System.currentTimeMillis());
				for (Connection conn : list)
				{
					_manager.reBuildCinConnection(conn.getRemoteAddress());
					_queue.add(conn);
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				logger.error("Check Expired Connection Error.");
			}
		}
	}

	void Dispose()
	{
		if (_disposed)
			return;
		_isRunning = false;
		_disposed = true;
	}
}
