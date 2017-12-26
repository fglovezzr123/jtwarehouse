package com.sqtoon.socket.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sqtoon.socket.common.transaction.TransactionManager;
import com.sqtoon.socket.common.util.CinLinkedList;
import com.sqtoon.socket.common.util.CinLinkedNode;

public class MessageHandleWorker extends Thread
{
	private static Logger logger = LoggerFactory.getLogger(SocketConfig.LOGGER_MAIN);
	private static final int WaitTime = 500;

	private CinLinkedList<MessageItem> _items;
	private Object _syncRoot;

	MessageHandleWorker(String name)
	{
		super(name);
		_items = new CinLinkedList<MessageItem>();
		_syncRoot = new Object();
		setDaemon(true);
	}

	void receiveCinMessage(MessageItem item)
	{
		_items.put(item);
		synchronized (_syncRoot)
		{
			_syncRoot.notify();
		}
	}

	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				CinLinkedNode<MessageItem> node = null;
				MessageItem item = null;
				_items.moveToHead();
				while (true)
				{
					node = _items.takeAwayFirst();
					if (node == null)
						break;
					else
					{
						item = node.object();
						if (item != null)
						{
							TransactionManager manager = item.GetCinTransactionManager();
							if (item.HasCinRequest())
								manager.OnRequestReceived(item.GetCinRequest());
							else
								manager.OnResponseReceived(item.GetCinResponse());
						}
					}
				}
				synchronized (_syncRoot)
				{
					_syncRoot.wait(WaitTime);
				}
			}
			catch (InterruptedException interEx)
			{
			}
			catch (Exception ex)
			{
				try
				{
					ex.printStackTrace();
					logger.error("CinMessageHandleWorker run Error");
				}
				catch (Exception e)
				{
					logger.error("CinMessageHandleWorker.run Exception Error");
					e.printStackTrace();
				}
			}
		}
	}
}
