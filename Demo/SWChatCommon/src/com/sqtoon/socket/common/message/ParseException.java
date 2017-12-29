package com.sqtoon.socket.common.message;

public final class ParseException extends Exception
{
	private static final long serialVersionUID = -4995083073131043805L;

	public ParseException()
	{
		super("Protocol conversion error");
	}

	public ParseException(String string)
	{
		super(string);
	}
}
