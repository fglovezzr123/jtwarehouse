package com.sqtoon.socket.common.message;

import com.sqtoon.socket.common.util.CinLinkedNode;

public class Body extends Header
{
	private static final long serialVersionUID = -185949573787064002L;

	CinLinkedNode<Body> Node;

	public Body()
	{
		super(HeaderType.Body);
	}

	public Body(byte[] bodyValue)
	{
		super(HeaderType.Body, bodyValue);
	}

	public Body(String bodyValue)
	{
		super(HeaderType.Body, bodyValue);
	}

	public void setValue(byte[] value)
	{
		if (value == null)
		{
			this.value = null;
		}
		else
			if (value.length > 0xffff)
			{
				this.value = null;
			}
			else
			{
				this.value = value;
			}
	}

	public String toString()
	{
		if (getValueLength() > 255)
			return "Body: TOOOOOOOOOOOOLOOOOOOONG";
		return super.toString();
	}

	public String toHexString()
	{
		if (getValueLength() > 255)
			return "Body: TOOOOOOOOOOOOLOOOOOOONG";
		return super.toString(false);
	}
}
