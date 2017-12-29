package com.sqtoon.socket.common.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sqtoon.socket.common.SocketConfig;

class TransactionCleanner extends Thread
{
	public static long lastCleanTime = System.currentTimeMillis();
	private static Logger logger = LoggerFactory.getLogger(SocketConfig.LOGGER_MAIN);

	TransactionCleanner()
	{
		super("CinTransactionCleanner");
	}

	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				TransactionManager.cleanExpiredTransaction();

				Thread.sleep(TransactionConfig.getCleanShortTransTime());
			}
			catch (Exception e)
			{
				e.printStackTrace();
				logger.error("Warn! 'CinTransactionCleanner.run()' ");
			}
		}
	}

}
