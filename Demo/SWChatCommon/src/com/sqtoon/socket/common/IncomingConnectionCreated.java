package com.sqtoon.socket.common;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

interface IncomingConnectionCreated
{
	void onIncomingConnectionCreated(SelectionKey key, SocketChannel channel) throws Exception;
}