package com.sw.chat;

import java.nio.ByteBuffer;
import com.sqtoon.socket.common.message.Header;
import com.sqtoon.socket.common.message.HeaderType;
import com.sqtoon.socket.common.message.Request;
import com.sqtoon.socket.common.message.RequestMethod;

public class CinMessageHandlerKey
{
	private byte _method;
	private Header _event;

	public CinMessageHandlerKey(Request request)
	{
		this(request.getMethod(), request.Event);
	}

	public CinMessageHandlerKey(byte method, Header event)
	{
		_method = method;
		if (event == null)
			_event = new Header(HeaderType.Event, 0);
		else
			_event = event;
	}

	public int getInt64(byte[] value)
	{
		if (value.length > 8)
			return -1;
		byte[] buff = new byte[8];
		int i = 7;
		for (byte b : value)
			buff[i--] = b;
		return ByteBuffer.wrap(buff).getInt();
	}

	@Override
	public int hashCode()
	{
		return (ByteBuffer.wrap(new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, _method}).getInt() << 16) + (int) _event.getInt64();
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Method: ");
		sb.append(RequestMethod.get(_method));
		sb.append(";  Event: ");
		if (_event != null)
			sb.append(_event.getHexString());
		else
			sb.append("null");
		sb.append("; Key: ");
		sb.append(hashCode());
		return sb.toString();
	}
}
