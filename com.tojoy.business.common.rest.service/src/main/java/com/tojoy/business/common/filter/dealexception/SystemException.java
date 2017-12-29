package com.tojoy.business.common.filter.dealexception;

public class SystemException extends Exception
{

	private static final long serialVersionUID = 1L;

	public SystemException()
	{
		super();
	}

	public SystemException(String message, Throwable throwable)
	{
		super(message, throwable);
	}

	public SystemException(String message)
	{
		super(message);
	}

	public SystemException(Throwable throwable)
	{
		super(throwable);
	}

}
