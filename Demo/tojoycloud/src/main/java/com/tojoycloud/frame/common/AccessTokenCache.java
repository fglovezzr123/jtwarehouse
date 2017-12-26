package com.tojoycloud.frame.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class AccessTokenCache extends Thread
{
	private static int expiretime = 60 * 1000;

	private String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + ConstantDefinition.appid + "&secret=" + ConstantDefinition.secret;

	private String access_token = "";

	private static AccessTokenCache _instance;

	private final static Object syncLock = new Object();

	private AccessTokenCache()
	{
		this.start();
	}

	public static AccessTokenCache getInstance()
	{
		if (_instance == null)
		{
			synchronized (syncLock)
			{
				if (_instance == null)
					_instance = new AccessTokenCache();
			}
		}
		return _instance;
	}

	public String getAccessToken()
	{
		if (access_token.length() == 0)
		{
			try
			{
				synchronized (syncLock)
				{
					syncLock.wait(expiretime);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return access_token;
	}

	@Override
	public void run()
	{
		while (true)
		{
			String sb = HttpsRequest.sendHttpsRequest(url, null);
			JSONObject object = JSON.parseObject(sb.toString());
			if (object.containsKey("access_token"))
				access_token = (String) object.get("access_token");
			synchronized (syncLock)
			{
				syncLock.notify();
			}
			try
			{
				Thread.sleep(expiretime);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args)
	{
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + ConstantDefinition.appid + "&secret=" + ConstantDefinition.secret;
		String sb = HttpsRequest.sendHttpsRequest(url, null);
		System.out.println(sb);
		JSONObject object = JSON.parseObject(sb.toString());
		if (object.containsKey("access_token"))
			System.out.println((String) object.get("access_token"));
		else
			System.out.println(111);
	}
}
