package com.sqtoon.socket.common.message;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import com.sqtoon.socket.common.transaction.Transaction;
import com.sqtoon.socket.common.util.CinLinkedList;
import com.sqtoon.socket.common.util.CinLinkedNode;

public class Message implements Serializable
{
	private static final long serialVersionUID = -1463566058361738586L;

	private CinLinkedList<Header> _headers;
	private CinLinkedList<Body> _bodys;
	private MessageType _messageType;
	private byte _method;
	private Transaction _parentTrans;
	public Header From;
	public Header To;
	public Header CallId;
	public Header Csequence;
	public Header Fpid;
	public Header Tpid;
	public Header Event;

	public Message(byte method)
	{
		this._method = method;
		_messageType = ((this._method | 0x7F) == 0x7F) ? MessageType.CinRequest : MessageType.CinResponse;
		this._headers = new CinLinkedList<Header>();
		this._bodys = new CinLinkedList<Body>();
	}

	public void addBody(Body body)
	{
		if (body != null)
		{
			body.Node = this._bodys.put(body);
		}
	}

	public void addBody(byte[] bytes)
	{
		if (bytes != null)
		{
			Body body = new Body(bytes);
			addBody(body);
		}
	}

	public void addBodys(ArrayList<Body> bodys)
	{
		for (Body body : bodys)
		{
			addBody(body);
		}
	}

	public void addHeader(Header header)
	{
		if (header != null)
		{
			if (header.Node != null)
				header = new Header(header.getType(), header.getValue());
			switch (header.getType())
			{
				case HeaderType.From:
				{
					header.Node = this._headers.put(header);
					this.From = header;
				}
				break;
				case HeaderType.To:
				{
					header.Node = this._headers.put(header);
					this.To = header;
				}
				break;
				case HeaderType.CallId:
				{
					header.Node = this._headers.put(header);
					this.CallId = header;
				}
				break;
				case HeaderType.Csequence:
				{
					header.Node = this._headers.put(header);
					this.Csequence = header;
					// do nothing.
				}
				break;
				case HeaderType.Fpid:
				{
					header.Node = this._headers.put(header);
					this.Fpid = header;
				}
				break;
				case HeaderType.Tpid:
				{
					header.Node = this._headers.put(header);
					this.Tpid = header;
				}
				break;
				case HeaderType.Event:
				{
					header.Node = this._headers.put(header);
					this.Event = header;
				}
				break;
				default:
				{
					header.Node = this._headers.put(header);
				}
				break;
			}
		}
	}

	public void setParentTrans(Transaction trans)
	{
		_parentTrans = trans;
	}

	public Transaction getParentTrans()
	{
		return _parentTrans;
	}

	public synchronized Body getBody()
	{
		try
		{
			_bodys.moveToHead();
			return _bodys.get().object();
		}
		catch (Exception e)
		{
			return null;
		}
	}

	public synchronized ArrayList<Body> getBodys()
	{
		ArrayList<Body> ret = new ArrayList<Body>();
		_bodys.moveToHead();
		CinLinkedNode<Body> bodyNode = null;
		while ((bodyNode = _bodys.get()) != null)
		{
			ret.add(bodyNode.object());
		}
		return ret;
	}

	public synchronized Header getHeader(byte headerType)
	{
		_headers.moveToHead();
		CinLinkedNode<Header> _headerNode = null;
		while ((_headerNode = _headers.get()) != null)
		{
			if (_headerNode.object().isTypeOf(headerType))
				return _headerNode.object();
		}
		return null;
	}

	public synchronized ArrayList<Header> getHeaders()
	{
		ArrayList<Header> ret = new ArrayList<Header>();
		_headers.moveToHead();
		CinLinkedNode<Header> _headerNode = null;
		while ((_headerNode = _headers.get()) != null)
		{
			ret.add(_headerNode.object());
		}
		return ret;
	}

	public synchronized ArrayList<Header> getHeaders(byte headerType)
	{
		ArrayList<Header> ret = new ArrayList<Header>();
		_headers.moveToHead();
		CinLinkedNode<Header> _headerNode = null;
		while ((_headerNode = _headers.get()) != null)
		{
			if (_headerNode.object().isTypeOf(headerType))
				ret.add(_headerNode.object());
		}
		return ret;
	}

	public String getKey(boolean direction)
	{
		StringBuilder sb = new StringBuilder();

		for (Header header : new Header[]{this.From, this.To, this.CallId, this.Csequence, this.Fpid, this.Tpid})
		{
			if (header != null && header.isNotNullValue())
			{
				if (header.getType() != HeaderType.Fpid && header.getType() != HeaderType.Tpid)
					sb.append(header.getInt64());
				else
					sb.append(header.getHexString());
			}
			sb.append("-");
		}
		sb.append(direction);
		return sb.toString();
	}

	/**
	 * To get the CinMessage type
	 * 
	 * @return
	 */
	public MessageType getMessageType()
	{
		return _messageType;
	}

	/**
	 * To get the CinMessage business signaling
	 * 
	 * @return
	 */
	public byte getMethodValue()
	{
		return _method;
	}

	public boolean isMessageType(MessageType messageType)
	{
		return this._messageType == messageType;
	}

	public boolean isMethod(byte method)
	{
		return this._method == method;
	}

	public boolean isEvent(byte event)
	{
		if (Event != null && Event.getValueLength() == 1)
		{
			return Event.getValue()[0] == event;
		}
		return false;
	}

	public boolean isEvent(Long event)
	{
		if (Event != null && Event.getValueLength() > 0)
		{
			return Event.getInt64() == event;
		}
		return false;
	}

	public void removeHeader(Header header)
	{

		switch (header.getType())
		{
			case HeaderType.From:
			{
				this._headers.remove(header.Node);
				From = null;
			}
			break;
			case HeaderType.To:
			{
				this._headers.remove(header.Node);
				To = null;
			}
			break;
			case HeaderType.Fpid:
			{
				this._headers.remove(header.Node);
				Fpid = null;
			}
			break;
			case HeaderType.Tpid:
			{
				this._headers.remove(header.Node);
				Tpid = null;
			}
			break;
			case HeaderType.CallId:
			{
				this._headers.remove(header.Node);
				CallId = null;
			}
			break;
			case HeaderType.Csequence:
			{
				this._headers.remove(header.Node);
				Csequence = null;
			}
			break;
			case HeaderType.Event:
			{
				this._headers.remove(header.Node);
				Event = null;
			}
			default:
			{
				this._headers.remove(header.Node);
			}
			break;
		}
	}

	public void removeHeaders(byte headerType)
	{
		ArrayList<Header> headers = this.getHeaders(headerType);
		for (Header header : headers)
		{
			removeHeader(header);
		}
	}

	public synchronized void releaseBodys()
	{
		_bodys.moveToHead();
		CinLinkedNode<Body> bodyNode = null;
		while ((bodyNode = _bodys.get()) != null)
		{
			_bodys.remove(bodyNode);
		}
	}

	public boolean containsHeader(byte typebyte)
	{
		return getHeader(typebyte) != null;
	}

	public int getHeaderSize()
	{
		int headerSize = 0;
		_headers.moveToHead();
		CinLinkedNode<Header> headerNode = null;
		while ((headerNode = _headers.get()) != null)
		{
			headerSize += headerNode.object().getValueLength() + 2;
		}

		return headerSize;
	}

	public int getBodySize()
	{
		int bodySize = 0;
		_bodys.moveToHead();
		CinLinkedNode<Body> bodyNode = null;
		while ((bodyNode = _bodys.get()) != null)
		{
			bodySize += bodyNode.object().getValueLength() + 3;
		}
		return bodySize;
	}

	public synchronized ByteBuffer toByteBuffer()
	{
		//TODO:结束标记由1个00 改为2个00
//		int messageSize = 3;
		int messageSize = 2;
		_headers.moveToHead();
		CinLinkedNode<Header> headerNode = null;
		while ((headerNode = _headers.get()) != null)
		{
			messageSize += headerNode.object().getValueLength() + 2;
		}
		_bodys.moveToHead();
		CinLinkedNode<Body> bodyNode = null;
		while ((bodyNode = _bodys.get()) != null)
		{
			messageSize += bodyNode.object().getValueLength() + 3;
		}
		ByteBuffer buffer = ByteBuffer.allocate(messageSize);
		buffer.put(_method);

		_headers.moveToHead();
		while ((headerNode = _headers.get()) != null)
		{
			Header h = headerNode.object();
			int length = h.getValueLength();
			buffer.put(h.type);
			buffer.put((byte) length);
			if (length > 0)
			{
				buffer.put(h.getValue(), 0, length);
			}
		}

		_bodys.moveToHead();
		while ((bodyNode = _bodys.get()) != null)
		{
			Body b = bodyNode.object();
			int length = b.getValueLength();
			buffer.put(b.getType());
			buffer.put((byte) (length & 0x000000FF));
			buffer.put((byte) ((length >> 8) & 0x000000FF));
			if (length > 0)
			{
				buffer.put(b.getValue(), 0, length);
			}
		}
		buffer.put((byte) 0);
		buffer.flip();
		return buffer;
	}

	public byte[] toBytes()
	{
		return toByteBuffer().array();
	}

	public Request toRequest()
	{
		if (_messageType == MessageType.CinRequest)
		{
			return (Request) this;
		}
		else
		{
			return null;
		}
	}

	public Response toResponse()
	{
		if (_messageType == MessageType.CinResponse)
		{
			return (Response) this;
		}
		else
		{
			return null;
		}
	}

	/**
	 * @param printBody
	 *            是否输出Message的string
	 * @return
	 */
	public String toString(boolean printBody)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("This message is: ");
		sb.append(this.getMessageType());
		sb.append("\r\n");
		if (this.isMessageType(MessageType.CinRequest))
		{
			sb.append("Method : ");
			sb.append(RequestMethod.get(this._method));
			sb.append("\r\n");
		}
		else
		{
			sb.append("ResponseCode : ");
			sb.append(ResponseCode.get(this._method));
			sb.append("\r\n");
		}
		Iterator<Header> fi = this.getHeaders().iterator();
		while (fi.hasNext())
		{
			sb.append(fi.next().toString(printBody));
		}
		Iterator<Body> bi = this.getBodys().iterator();
		while (bi.hasNext())
		{
			sb.append(bi.next().toString());
		}
		return sb.toString();
	}

	@Override
	public String toString()
	{
		return this.toString(true);
	}

	public String toHexString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("This message is: ");
		sb.append(this.getMessageType());
		sb.append("\r\n");
		if (this.isMessageType(MessageType.CinRequest))
		{
			sb.append("Method : ");
			sb.append(RequestMethod.get(this._method));
			sb.append("\r\n");
		}
		else
		{
			sb.append("ResponseCode : ");
			sb.append(ResponseCode.get(this._method));
			sb.append("\r\n");
		}
		Iterator<Header> fi = this.getHeaders().iterator();
		while (fi.hasNext())
		{
			sb.append(fi.next().toString(false));
		}
		Iterator<Body> bi = this.getBodys().iterator();
		while (bi.hasNext())
		{
			sb.append(bi.next().toHexString());
		}
		return sb.toString();
	}

	public void setMethod(byte value)
	{
		_method = value;
	}
}
