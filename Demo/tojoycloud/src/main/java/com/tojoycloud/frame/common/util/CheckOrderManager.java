package com.tojoycloud.frame.common.util;

import java.util.concurrent.ConcurrentLinkedQueue;

public class CheckOrderManager extends Thread
{
	private boolean _isRunning;

	private ConcurrentLinkedQueue<String> _items;

	CheckOrderManager()
	{
		_items = new ConcurrentLinkedQueue<String>();
		setPriority(6);
		setDaemon(true);
		_isRunning = true;
		start();
	}

	void dispose() throws Exception
	{
		_isRunning = false;
	}

	/**
	 * task任务只从队列中获取orderID，并检测此orderID是否在缓存中存在，如果不存在，调用约见服务的dubbo接口，
	 * update此条数据的结束状态，(where status=1)
	 */
	@Override
	public void run()
	{
		while (_isRunning)
		{
			try
			{
				Thread.sleep(1000);
				// if (!_items.isEmpty())
				while (!_items.isEmpty())
				{
					String orderID = _items.poll();
					System.out.println("获取的orderID === " + orderID);
					// if (1 != 1) // TODO:代表缓存中不存在
					// {
					// // TODO：调用约见dubbo接口修改数据
					// }
					// else
					// {
					// _items.add(orderID);
					// }
				}
				// else
				// {
				// synchronized (_items)
				// {
				// try
				// {
				// _items.wait();
				// }
				// catch (InterruptedException e)
				// {
				// e.printStackTrace();
				// }
				// }
				// }
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			System.out.println("ing----------------");
		}
	}

	void addOrderID(String orderID)
	{
		_items.add(orderID);
		// synchronized (_items)
		// {
		// _items.notify();
		// }
	}

	public static void main(String[] args)
	{
		CheckOrderManager manager = new CheckOrderManager();
		for (int i = 0; i < 10; i++)
			manager.addOrderID(i + "");
		try
		{
			Thread.sleep(2000);
			for (int i = 10; i < 20; i++)
				manager.addOrderID(i + "");
			Thread.sleep(10000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

	}
}
