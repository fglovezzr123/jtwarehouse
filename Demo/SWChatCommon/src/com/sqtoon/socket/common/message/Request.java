package com.sqtoon.socket.common.message;

import java.util.ArrayList;

public final class Request extends Message implements Cloneable
{
	private static final long serialVersionUID = 3901435614099967090L;

	public Request(byte method)
	{
		super(method);
	}

	public byte getMethod()
	{
		return super.getMethodValue();
	}

	public boolean isMethod(byte method)
	{
		return method == super.getMethodValue();
	}

	@Override
	public Request clone()
	{
		Request request = new Request(this.getMethodValue());
		ArrayList<Header> list = this.getHeaders();
		ArrayList<Body> bodys = this.getBodys();

		for (Header header : list)
		{
			if (!(header.isTypeOf(HeaderType.RecordRoute) || header.isTypeOf(HeaderType.Route)))
				request.addHeader(new Header(header.getType(), header.getValue()));
		}

		for (Body body : bodys)
		{
			request.addBody(new Body(body.getValue()));
		}
		return request;
	}

	public void setMethod(byte method)
	{
		super.setMethod(method);
	}
}
