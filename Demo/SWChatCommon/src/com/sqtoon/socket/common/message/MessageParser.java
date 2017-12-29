package com.sqtoon.socket.common.message;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;

public class MessageParser
{
	private Header _header;
	private Message _message;
	private ArrayList<Message> _parsedMessages;
	private Byte _bodyLengthByte;
	private int _valueParsed;
	private int _valueLength;
	private int _messagebytes;
	private boolean _dedicate;
	private static final String UNKNOWN = "Unknown";

	public MessageParser()
	{
		this(false);
	}

	public MessageParser(boolean dedicate)
	{
		_dedicate = dedicate;
		_parsedMessages = new ArrayList<Message>();
		_valueParsed = 0;
		_valueLength = 0;
		_messagebytes = 0;
		_bodyLengthByte = null;
	}

	public LinkedList<Message> parse(ByteBuffer buffer) throws ParseException
	{
		LinkedList<Message> list = parse(buffer.array(), buffer.limit());
		buffer.position(buffer.limit());
		return list;
	}

	public LinkedList<Message> parse(byte[] buff, int buffLength) throws ParseException
	{
		boolean isEnd = false;
		// int endcount = 0;
		int pos = 0;
		while (pos < buffLength)
		{
			if (_message == null)
			{
				if (buff[pos] == 0x00 && isEnd)
				{
					pos++;
					continue;
				}
				if ((buff[pos] | 0x7F) == 0x7F)
				{
					String method = RequestMethod.get(buff[pos]);
					if (method == null || method.equals(UNKNOWN))
						throw new ParseException("CinRequestMethod.Unknown");
					_message = new Request(buff[pos]);
				}
				else
				{
					String code = ResponseCode.get(buff[pos]);
					if (code == null || code.equals(UNKNOWN))
						throw new ParseException("CinResponseCode.Unknown");
					_message = new Response(buff[pos]);
				}
				_messagebytes++;
				pos++;
				continue;
			}
			else
			{
				if (_header == null)
				{
					if (buff[pos] == HeaderType.End)
					{
//						endcount++;
						if (_messagebytes >= ParserConfig.getMaxMessageSize())
							throw new ParseException("Out of MaxMessageSize.MessageSize is:" + _messagebytes);
						_messagebytes = 0;
						_parsedMessages.add(_message);
						// TODO:结束标记由1个00 改为2个00
						isEnd = true;
						// if (endcount == 2)
						_message = null;
						pos++;
						continue;
					}
					else
					{
						if (buff[pos] == HeaderType.Body)
							_header = new Body();
						else
						{
							if (_dedicate && HeaderType.get(buff[pos]) == null)
								throw new ParseException("Unexpected HeaderName.");
							_header = new Header(buff[pos]);
						}
						pos++;
						continue;
					}
				}
				else
				{
					if (_valueLength == 0)
					{
						if (_header.isTypeOf(HeaderType.Body))
						{
							if (_bodyLengthByte == null)
							{
								_bodyLengthByte = buff[pos];
								pos++;
								continue;
							}
							else
							{
								_valueLength = _bodyLengthByte & 0xFF | ((buff[pos] & 0xFF) << 8);
								_bodyLengthByte = null;
								pos++;
								if (_valueLength == 0)
									resetHeader();
								continue;
							}
						}
						else
						{
							_valueLength = buff[pos] & 0xFF;
							pos++;
							if (_valueLength == 0)
								resetHeader();
							continue;
						}
					}
					else
					{
						int bufflen = buffLength - pos;
						if (_valueParsed == 0 && _valueLength <= bufflen)
						{
							_header.value = new byte[_valueLength];
							System.arraycopy(buff, pos, _header.value, 0, _header.value.length);
							pos += _valueLength;
							resetHeader();
							continue;
						}
						else
						{
							if (_header.value == null)
								_header.value = new byte[_valueLength];
							if (_valueLength - _valueParsed <= bufflen)
							{
								int len = _valueLength - _valueParsed;
								System.arraycopy(buff, pos, _header.value, _valueParsed, len);
								pos += len;
								resetHeader();
								continue;
							}
							else
							{
								System.arraycopy(buff, pos, _header.value, _valueParsed, bufflen);
								_valueParsed += bufflen;
								pos = buffLength;
								continue;
							}
						}
					}
				}
			}
		}
		if (_parsedMessages.size() > 0)
		{
			LinkedList<Message> list = new LinkedList<Message>();
			for (Message msg : _parsedMessages)
			{
				list.add(msg);
			}
			_parsedMessages.clear();
			return list;
		}
		else
		{
			return null;
		}
	}

	private void resetHeader() throws ParseException
	{
		if (_header.isTypeOf(HeaderType.Unknown))
			throw new ParseException("CinHeaderType.Unknown");
		if (_header.isTypeOf(HeaderType.Body))
		{
			_message.addBody((Body) _header);
			_messagebytes += 3 + _valueLength;
		}
		else
		{
			_message.addHeader(_header);
			_messagebytes += 2 + _valueLength;
		}
		_header = null;
		_valueLength = 0;
		_valueParsed = 0;
	}
}
