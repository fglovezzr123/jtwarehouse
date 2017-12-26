package com.sw.chat.log;

import com.sqtoon.socket.common.util.CinLinkedList;
import com.sqtoon.socket.common.util.CinLinkedNode;

public class ChatLogWorker extends Thread
{
//	private static Logger logger = LoggerFactory.getLogger(ChatConfig.LOGGER_MAIN);

//	private String threadName = "";
	private CinLinkedList<ChatLogItem> _items;

	ChatLogWorker(String name)
	{
		super(name);
		_items = new CinLinkedList<ChatLogItem>();
		setDaemon(true);
//		threadName = name;
	}

	void addItem(ChatLogItem item)
	{
		_items.put(item);
	}

	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				CinLinkedNode<ChatLogItem> node = null;
				ChatLogItem item = null;
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
//							ChatLogDao chatDao = new ChatLogDao();
//							chatDao.receivemessage = item.getReceiveMessage();
//							chatDao.replymessage = item.getReplyMessage();
//							chatDao.receivetime = item.getReceiveTime();
//							chatDao.addChatLog();
						}
					}
				}
				Thread.sleep(1000);
			}
			catch (Exception ex)
			{
				try
				{
					ex.printStackTrace();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
