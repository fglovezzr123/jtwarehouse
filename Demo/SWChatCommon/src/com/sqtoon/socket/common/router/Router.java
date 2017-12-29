package com.sqtoon.socket.common.router;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import com.sqtoon.socket.common.PersonalId;
import com.sqtoon.socket.common.message.Header;
import com.sqtoon.socket.common.message.HeaderType;
import com.sqtoon.socket.common.message.Request;

public class Router
{
	public static boolean isInitialized = false;
	public static String RouterSeparator = ":";

	public Router()
	{
	}

	public static void setRoute(Request request, ServiceName serviceName)
	{
		request.addHeader(new Header(HeaderType.Route, serviceName.getValue()));
	}

	private static String takeStringRoute(Request request)
	{
		// CinHeader route = request.getHeader(CinHeaderType.Route);
		String ipEndPoint = "";
		return ipEndPoint;
	}

	public static InetSocketAddress takeRoute(Request request)
	{
		Header route = request.getHeader(HeaderType.Route);
		if (ServiceName.get(route.getString()) == ServiceName.MessageProxy)
		{
			Header tpid = request.Tpid;
			route.setType(HeaderType.RecordRoute);
			return PersonalId.parse(tpid.getValue()).getAddress();
		}
		else
		{
			String[] ipEndPoint = takeStringRoute(request).split(RouterSeparator);
			return new InetSocketAddress(ipEndPoint[0], Integer.parseInt(ipEndPoint[1]));
		}
	}

	public static ArrayList<InetSocketAddress> takeServiceRoute(ServiceName serviceName)
	{
		// CinRouterConfig.takeServiceRoute(serviceName);
		return null;
	}

	public static ArrayList<InetSocketAddress> takeServiceRoute(String serviceName)
	{
		// CinRouterConfig.takeServiceRoute(serviceName);
		return null;
	}

	public static InetSocketAddress takeRoute(ServiceName serviceName)
	{
		// return CinRouterConfig.takeServiceRoute(serviceName).get(0);
		return null;
	}
}
