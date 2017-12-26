package com.sw.chat;

import com.sqtoon.socket.common.Configure;
import com.sqtoon.socket.common.Stack;
import com.sqtoon.socket.common.transaction.TransactionManager;

public class ProxyEntry
{
	public static void main(String[] args)
	{
		try
		{
			// 本地 启动Server服务
			Stack.initialize();
			TransactionManager.Initialize();
			Configure.Initialize();
			CinMessageDispatcher.initialize();// 初始化Handlers
			// 初始化监听 Server 请求
			ProxyManager manager = new ProxyManager();
			Stack serverStack = Stack.instance();
			serverStack.registerDefaultTransactionCreated(manager);
			// 启动client服务
			Stack clientStack = new Stack(manager);
			// 给manager添加client和server服务
			manager.initialize(clientStack, serverStack);
			// 启动监听
			serverStack.listen("localhost", 8888);
			clientStack.listen("localhost", 6666);
			System.out.println("CMP start successful ~~~~");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
