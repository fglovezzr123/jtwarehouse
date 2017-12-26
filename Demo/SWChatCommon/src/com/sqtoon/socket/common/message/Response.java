package com.sqtoon.socket.common.message;

import java.util.ArrayList;

public final class Response extends Message
{

	private static final long serialVersionUID = -2258720724185098420L;

	Response(byte code)
	{
		super(code);
	}

	public static Response createResponse(byte code)
	{
		return new Response(code);
	}

	public Response(Request request)
	{
		this(request, ResponseCode.OK);
	}

	public Response(Request request, byte code)
	{
		super(code);
		for (Header header : request.getHeaders())
		{
			if (header != null)
			{
				switch (header.getType())
				{
					case HeaderType.From:
					case HeaderType.To:
					case HeaderType.CallId:
					case HeaderType.Csequence:
					case HeaderType.Fpid:
					case HeaderType.Tpid:
						this.addHeader(header.clone());
					break;
					default:
					break;
				}
			}
		}
	}

	public byte getStatusCode()
	{
		return super.getMethodValue();
	}

	public boolean isResponseCode(byte code)
	{
		return super.getMethodValue() == code;
	}

	public Response cloneSpecialHeader()
	{
		Response response = new Response(ResponseCode.Pending);
		ArrayList<Header> list = this.getHeaders();
		for (Header header : list)
		{
			switch (header.getType())
			{
				case HeaderType.From:
				case HeaderType.To:
				case HeaderType.CallId:
				case HeaderType.Csequence:
				case HeaderType.Fpid:
				case HeaderType.Tpid:
					response.addHeader(new Header(header.getType(), header.getValue()));
				break;
			}
		}
		return response;
	}
}
