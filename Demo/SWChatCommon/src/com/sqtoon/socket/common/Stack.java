package com.sqtoon.socket.common;

import java.net.SocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sqtoon.socket.common.message.Request;
import com.sqtoon.socket.common.router.Router;
import com.sqtoon.socket.common.transaction.Transaction;
import com.sqtoon.socket.common.transaction.TransactionCreated;

public class Stack
{
	private static Logger logger = LoggerFactory.getLogger(SocketConfig.LOGGER_MAIN);
	private static Stack _instance;

	private StackMode _mode;
	private ConnectionManager _manager;
	private SelectorManager _selector;
	private boolean _listened;
	private int _port;

	private Stack() throws Exception
	{
		this(StackMode.Mutiplex, null);
	}

	public Stack(IConnectionCreatedEvent connEvent) throws Exception
	{
		this(StackMode.Dedicate, connEvent);
		logger.info("The constructor 'CinStack(ICinConnectionCreatedEvent)' is for CMP only~~!!!! Please use CinStack.GetInstance() to get an instance of CinStack.");
	}

	public Stack(StackMode mode, IConnectionCreatedEvent connEvent)
	{
		_mode = mode;
		_manager = new ConnectionManager(connEvent, _mode);
		_selector = new SelectorManager("CinSelector-" + _mode, _manager, _manager);
		_manager.registerChannelRegistered(_selector);
		_listened = false;
	}

	public synchronized static void initialize() throws Exception
	{
		if (_instance == null)
		{
			_instance = new Stack();
		}
	}

	public synchronized static Stack instance()
	{
		return _instance;
	}

	public void registerTransactionCreated(byte method, TransactionCreated event)
	{
		_manager.registerTransactionCreated(method, event);
	}

	public void registerDefaultTransactionCreated(TransactionCreated event)
	{
		_manager.registerTransactionCreated(event);
	}

	public void listen(String ip, int port) throws Exception
	{
		try
		{
			if (_listened)
				throw new IllegalAccessError("CinStack has already listened a port. Listening Port: " + _port);

			_selector.listen(ip, port);
			_port = port;
			_listened = true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("Stack Start Failed");
			throw ex;
		}
	}

	public void dispose() throws Exception
	{
		try
		{
			_selector.dispose();
			_manager.dispose();
		}
		catch (Exception ex)
		{
			logger.error("Stack Stop Failed");
			throw ex;
		}
	}

	public Transaction createTransaction(Request request)
	{
		SocketAddress address = Router.takeRoute(request);
		return createTransaction(address, request);
	}

	public Transaction createTransaction(SocketAddress address, Request request)
	{
		if (request == null)
			return null;

		try
		{
			Connection conn = _manager.getCinConnection(address);
			return conn.createTransaction(request);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("createTransaction error.");
			return null;
		}
	}

	public Transaction createShortTransaction(Request request)
	{
		SocketAddress address = Router.takeRoute(request);
		return createShortTransaction(address, request);
	}

	public Transaction createShortTransaction(SocketAddress address, Request request)
	{
		if (request == null)
			return null;

		try
		{
			Connection conn = _manager.getCinConnection(address);
			return conn.createShortTransaction(request);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("createShortTransaction error.");
			return null;
		}
	}

	public void releaseCinConnection(Connection conn)
	{
		_manager.onCinConnectionDisconnected(conn);
	}
}
