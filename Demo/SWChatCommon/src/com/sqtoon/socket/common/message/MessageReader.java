package com.sqtoon.socket.common.message;


public class MessageReader
{

	public MessageReader()
	{
	}

	public static Message parse(byte[] buff)
	{
		return parse(buff, buff.length);
	}

	public static Message parse(byte[] buff, int buffLength)
	{
		Header parseHeader = null;
		Message parseMessage = null;
		Byte BodyLengthByte = null;
		int ValueLength = 0;
		int pos = 0;
		while (pos < buffLength)
		{
			if (parseMessage == null)
			{
				if (buff[pos] == 0x00)
				{
					return null;
				}
				if ((buff[pos] | 0x7F) == 0x7F)
				{
					parseMessage = new Request(buff[pos]);
				}
				else
				{
					parseMessage = new Response(buff[pos]);
				}
				pos++;
				continue;
			}
			else
			{
				if (parseHeader == null)
				{
					if (buff[pos] == HeaderType.End)
					{
						return parseMessage;
					}
					else
					{
						if (buff[pos] == HeaderType.Body)
							parseHeader = new Body();
						else
							parseHeader = new Header(buff[pos]);
						pos++;
						continue;
					}
				}
				else
				{
					if (ValueLength == 0)
					{
						if (parseHeader.isTypeOf(HeaderType.Body))
						{
							if (BodyLengthByte == null)
							{
								BodyLengthByte = buff[pos];
								pos++;
								continue;
							}
							else
							{
								ValueLength = BodyLengthByte & 0xFF | ((buff[pos] & 0xFF) << 8);
								BodyLengthByte = null;
								pos++;
								if (ValueLength == 0)
								{
									if (parseHeader.isTypeOf(HeaderType.Body))
									{
										parseMessage.addBody((Body) parseHeader);
									}
									else
									{
										parseMessage.addHeader(parseHeader);
									}
									parseHeader = null;
									ValueLength = 0;
								}
								continue;
							}
						}
						else
						{
							ValueLength = buff[pos] & 0xFF;
							pos++;
							if (ValueLength == 0)
							{
								if (parseHeader.isTypeOf(HeaderType.Body))
								{
									parseMessage.addBody((Body) parseHeader);
								}
								else
								{
									parseMessage.addHeader(parseHeader);
								}
								parseHeader = null;
								ValueLength = 0;
							}
							continue;
						}
					}
					else
					{
						int bufflen = buffLength - pos;
						if (ValueLength <= bufflen)
						{
							parseHeader.value = new byte[ValueLength];
							System.arraycopy(buff, pos, parseHeader.value, 0, ValueLength);
							pos += ValueLength;
							if (parseHeader.isTypeOf(HeaderType.Body))
							{
								parseMessage.addBody((Body) parseHeader);
							}
							else
							{
								parseMessage.addHeader(parseHeader);
							}
							parseHeader = null;
							ValueLength = 0;
							continue;
						}
						else
						{
							break;
						}
					}
				}
			}
		}
		return parseMessage;
	}

	private Header _header;
	private Message _message;
	private Byte _bodyLengthByte;
	private int _valueLength;

	public Message load(byte[] buff)
	{
		return load(buff, buff.length);
	}

	public Message load(byte[] buff, int buffLength)
	{
		int pos = 0;
		_valueLength = 0;
		_bodyLengthByte = null;
		_message = null;
		_header = null;
		while (pos < buffLength)
		{
			if (_message == null)
			{
				if (buff[pos] == 0x00)
				{
					return null;
				}
				if ((buff[pos] | 0x7F) == 0x7F)
				{
					_message = new Request(buff[pos]);
				}
				else
				{
					_message = new Response(buff[pos]);
				}
				pos++;
				continue;
			}
			else
			{
				if (_header == null)
				{
					if (buff[pos] == HeaderType.End)
					{
						return _message;
					}
					else
					{
						if (buff[pos] == HeaderType.Body)
							_header = new Body();
						else
							_header = new Header(buff[pos]);
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
						if (_valueLength <= bufflen)
						{
							_header.value = new byte[_valueLength];
							System.arraycopy(buff, pos, _header.value, 0, _valueLength);
							pos += _valueLength;
							resetHeader();
							continue;
						}
						else
						{
							break;
						}
					}
				}
			}
		}
		return _message;
	}

	private void resetHeader()
	{
		if (_header.isTypeOf(HeaderType.Body))
		{
			_message.addBody((Body) _header);
		}
		else
		{
			_message.addHeader(_header);
		}
		_header = null;
		_valueLength = 0;
	}
}
