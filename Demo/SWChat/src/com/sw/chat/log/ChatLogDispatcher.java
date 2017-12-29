package com.sw.chat.log;

public class ChatLogDispatcher
{
	private final static Object syncLock = new Object();
	private static ChatLogDispatcher _instance;
	private ChatLogWorker[] _workers;

	private ChatLogDispatcher()
	{
		initWorkers(10);
	}

	public synchronized static void initialize() throws Exception
	{
		if (_instance == null)
			_instance = new ChatLogDispatcher();
	}

	private void initWorkers(int count)
	{
		_workers = new ChatLogWorker[count];
		for (int i = 0; i < count; i++)
		{
			_workers[i] = new ChatLogWorker("ChatLogWorker-" + i);
			_workers[i].start();
		}
	}

	public static ChatLogDispatcher getInstance()
	{
		if (_instance == null)
		{
			synchronized (syncLock)
			{
				if (_instance == null)
					_instance = new ChatLogDispatcher();
			}
		}
		return _instance;
	}

	public synchronized void addItem(ChatLogItem item)
	{
		int id = Math.abs(getThreadIndex()) % _workers.length;
		_workers[id].addItem(item);
	}

	private synchronized int getThreadIndex()
	{
		return (int) ((Math.random()) * _workers.length);
	}
}
