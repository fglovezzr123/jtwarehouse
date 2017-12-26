package com.sw.chat.handler;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import com.sw.chat.ChatEntry;

public class ClientTest
{
	public static void main(String[] args)
	{
		ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(10);
		exec.scheduleAtFixedRate(new ChatEntry(), 500, 500, TimeUnit.MILLISECONDS);

	}
}
