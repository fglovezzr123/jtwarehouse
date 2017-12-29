package com.sqtoon.socket.common;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import com.sqtoon.socket.common.util.Convert;

public class PersonalId
{

	/** Initialize the each service startup, represent different services */
	private ByteBuffer value;
	private final static short valueLength = 18;

	private static final int POS_SERVER_IPENDPOINT = 0;
	private static final int POS_PERSONALID_CREATEDDATETIME = 6;
	private static final int POS_CONNECTIONID = 14;

	private PersonalId()
	{

	}

	public PersonalId(String host, int port, int connectid)
	{
		this(host, (char) port, connectid);
	}

	private PersonalId(String host, char port, int connectid)
	{
		ByteBuffer serviceaddress = ByteBuffer.allocate(POS_PERSONALID_CREATEDDATETIME - POS_SERVER_IPENDPOINT);
		try
		{
			serviceaddress.put(InetAddress.getByName(host).getAddress());
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		serviceaddress.putChar(port);
		byte[] address = serviceaddress.array();
		value = ByteBuffer.allocate(valueLength);
		value.put(address);
		value.putLong(System.currentTimeMillis());
		value.putInt(connectid);
		value.flip();
	}

	public static PersonalId parse(byte[] data)
	{
		if (data.length <= valueLength)
		{
			byte[] pid = new byte[valueLength];
			System.arraycopy(data, 0, pid, 0, data.length);
			data = pid;
		}
		else
		{
			return null;
		}

		PersonalId temp = new PersonalId();
		temp.value = ByteBuffer.wrap(data);
		return temp;
	}

	public int getConnectionId()
	{
		return value.getInt(POS_CONNECTIONID);
	}

	public long getTimeStamp()
	{
		return value.getLong(POS_PERSONALID_CREATEDDATETIME);
	}

	public byte[] getBytes()
	{
		return value.array();
	}

	public byte[] getByte(int index)
	{
		byte[] temp = new byte[index];
		value.get(temp);
		value.rewind();
		return temp;
	}

	public InetSocketAddress getAddress()
	{
		byte[] ipaddr = new byte[4];
		value.get(ipaddr);
		try
		{
			return new InetSocketAddress(InetAddress.getByAddress(ipaddr), value.getChar(4));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public String getHexString()
	{
		return Convert.bytes2String(value.array());
	}

	@Override
	public String toString()
	{
		return getHexString();
	}

	@Override
	public boolean equals(Object obj)
	{
		return (obj != null) && (Arrays.equals(this.getBytes(), ((PersonalId) obj).getBytes()));
	}

	@Override
	public int hashCode()
	{
		int hash = 0;
		for (int i = 0; i < (value.limit() / 2); i++)
		{
			hash = 37 * hash + value.getShort(i * 2);
		}
		return hash;
	}

	public static void main(String[] args)
	{
		byte[] bytes = Convert.toByteArray("0A0AD124177000000142E9E522D0000006F10100000000000000");
		PersonalId pid = PersonalId.parse(bytes);
		System.out.println(Convert.bytes2String(pid.getBytes()));
		System.out.println(pid.getConnectionId());
	}

}
