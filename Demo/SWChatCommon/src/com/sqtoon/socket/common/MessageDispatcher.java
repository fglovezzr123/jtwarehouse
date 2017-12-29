package com.sqtoon.socket.common;

import com.sqtoon.socket.common.message.HeaderType;
import com.sqtoon.socket.common.message.Request;
import com.sqtoon.socket.common.message.Response;

class MessageDispatcher
{
	private StackMode _mode;
	private MessageHandleWorker[] _workers;

	MessageDispatcher(StackMode mode)
	{
		_mode = mode;
		initWorkers(50);
	}

	private void initWorkers(int count)
	{
		_workers = new MessageHandleWorker[count];
		for (int i = 0; i < count; i++)
		{
			_workers[i] = new MessageHandleWorker("CinMessageHandleWorker-" + i + "-" + _mode);
			_workers[i].start();
		}
	}

	void receiveCinMessageItem(MessageItem item)
	{
		int id = 0;
		if (_mode == StackMode.Mutiplex)
		{
			id = Math.abs(getThreadIndex(item)) % _workers.length;
		}
		else
		{
			id = item.GetConnectionKey() % _workers.length;
		}
		_workers[id].receiveCinMessage(item);
	}

	private int getThreadIndex(MessageItem item)
	{
		if (item.HasCinRequest())
		{
			Request request = item.GetCinRequest();
			Long result = 0L;
			if (request.To != null)
			{
				result = request.To.getInt64();
				return result.intValue();
			}
			else
				if (request.From != null)
				{
					result = request.From.getInt64();
					return result.intValue();
				}
		}
		else
		{
			Response response = item.GetCinResponse();
			Long from = 0L;
			if (response.containsHeader(HeaderType.From))
				from = response.From.getInt64();
			Long to = 0L;
			if (response.containsHeader(HeaderType.To))
				to = response.To.getInt64();
			return from.intValue() + to.intValue();
		}
		return 0;
	}
}
