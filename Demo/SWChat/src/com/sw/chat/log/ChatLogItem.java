package com.sw.chat.log;

public class ChatLogItem
{
	private int source;

	private String receiveMessage;

	private String replyMessage;

	private String receiveTime;

	public int getSource()
	{
		return source;
	}

	public void setSource(int source)
	{
		this.source = source;
	}

	public String getReceiveMessage()
	{
		return receiveMessage;
	}

	public void setReceiveMessage(String receiveMessage)
	{
		this.receiveMessage = receiveMessage;
	}

	public String getReplyMessage()
	{
		return replyMessage;
	}

	public void setReplyMessage(String replyMessage)
	{
		this.replyMessage = replyMessage;
	}

	public String getReceiveTime()
	{
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime)
	{
		this.receiveTime = receiveTime;
	}

}
