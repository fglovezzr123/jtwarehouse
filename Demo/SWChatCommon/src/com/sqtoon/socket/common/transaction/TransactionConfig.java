package com.sqtoon.socket.common.transaction;

public class TransactionConfig
{

	private static long _expiredtimes = 64 * 1000;
	private static long _shortexpiredtimes = 4 * 1000;
	private static long cleanLongTransTime = 6 * 1000;
	private static long cleanShortTransTime = 1000;
	private static long maxResponseBodySize = 60 * 1024;

	public static long expiredTimes()
	{
		return _expiredtimes;
	}

	public static long shortExpiredTimes()
	{
		return _shortexpiredtimes;
	}

	public static long getCleanLongTransTime()
	{
		return cleanLongTransTime;
	}

	public static long getCleanShortTransTime()
	{
		return cleanShortTransTime;
	}

	public static long getMaxResponseBodySize()
	{
		return maxResponseBodySize;
	}

}
