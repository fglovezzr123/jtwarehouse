package com.sw.chat;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;
import com.sqtoon.socket.common.message.RequestMethod;
import com.sqtoon.socket.common.router.ServiceName;

public class CinProxyUtil
{
	private final static Random randomserverkey = new Random();

	static byte[] getRandomkey()
	{
		byte[] svrkey = new byte[16];
		randomserverkey.nextBytes(svrkey);
		return svrkey;
	}

	public static boolean checkPwd(byte[] from, byte[] token, byte[] randomKey, byte[] clientPwd, byte[] serverPwd)
	{
		if (clientPwd == null || clientPwd.length != 32)
		{
			return false;
		}

		ByteBuffer toMd5 = ByteBuffer.allocate(64);
		toMd5.put(from);
		toMd5.put(token);
		toMd5.put(randomKey);
		MessageDigest md5 = null;
		try
		{
			md5 = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException ex)
		{
			return false;
		}
		byte[] result = md5.digest(Arrays.copyOfRange(toMd5.array(), 0, toMd5.position()));

		if (Arrays.equals(result, Arrays.copyOfRange(clientPwd, 0, 16)))
			return Arrays.equals(md5.digest(Arrays.copyOfRange(clientPwd, 16, 32)), serverPwd);

		return false;
	}

	public static ServiceName getCinServiceName(byte method)
	{
		switch (method)
		{
			case RequestMethod.Message:
				return ServiceName.MessageCenter;
			case RequestMethod.Service:
				return ServiceName.UserCacheCenter;
			default:
				return null;
		}
	}
}
