package com.sqtoon.socket.common;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Listener
{
	private static Logger logger = LoggerFactory.getLogger(SocketConfig.LOGGER_MAIN);
	private IncomingConnectionCreated _event;
	private IConnectionDisconnected _disconnectEvent;
	private ServerSocket _ss;

	public Listener(IncomingConnectionCreated event, IConnectionDisconnected disconnectEvent)
	{
		_event = event;
		_disconnectEvent = disconnectEvent;
	}

	ServerSocketChannel listen(String ip, int port) throws Exception
	{
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.configureBlocking(false);
		_ss = ssc.socket();
		_ss.setReuseAddress(true);
		bindPort(_ss, ip, port);
		logger.info("Listening port " + port + " has been opened.");
		return ssc;
	}

	void close() throws IOException
	{
		if (_ss != null && _ss.isBound())
			_ss.close();
	}

	private void bindPort(ServerSocket ss, String ip, int port) throws Exception
	{
		boolean isBinded = false;
		while (!isBinded)
		{
			try
			{
				ss.bind(new InetSocketAddress(ip, port));
				isBinded = true;
			}
			catch (BindException ex)
			{
				ex.printStackTrace();
				logger.error("BindPort Error. IP: " + ip + "; Port: " + port);
				Thread.sleep(3000);
			}
			catch (Exception ex)
			{
				throw ex;
			}
		}
	}

	void processAccept(SelectionKey key) throws Exception
	{
		IncomingConnectionCreated event = _event;
		ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
		SocketChannel sc = ssc.accept();
		sc.configureBlocking(false);
		SelectionKey cKey = sc.register(key.selector(), SelectionKey.OP_READ);
		if (event != null)
		{
			event.onIncomingConnectionCreated(cKey, sc);
		}
	}

	void processRecieve(SelectionKey key) throws Exception
	{
		Connection conn = (Connection) key.attachment();
		conn.receiveData();
	}

	void handleException(Exception ex, SelectionKey key, boolean disconnectedBySelf) throws Exception
	{
		key.channel().close();
		Connection conn = (Connection) key.attachment();
		if (disconnectedBySelf)
			logger.info("The connection has been disconnected by CinStack. \r\n" + conn);
		else
			logger.info("Maybe the connection has been disconnected. \r\n" + conn);
		_disconnectEvent.onCinConnectionDisconnected((Connection) key.attachment());
	}
}
