package com.sqtoon.socket.common.message;

public class ParserConfig
{

	private static int _maxMessageSize = 65536;
	private static boolean _printBody = true;

	public static int getMaxMessageSize()
	{
		return _maxMessageSize;
	}

	public static void setPrintBody(boolean printBody)
	{
		_printBody = printBody;
	}

	static boolean printBody()
	{
		return _printBody;
	}
}
