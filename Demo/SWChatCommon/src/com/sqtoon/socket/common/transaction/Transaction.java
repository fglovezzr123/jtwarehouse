package com.sqtoon.socket.common.transaction;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sqtoon.socket.common.SocketConfig;
import com.sqtoon.socket.common.message.Body;
import com.sqtoon.socket.common.message.Header;
import com.sqtoon.socket.common.message.HeaderType;
import com.sqtoon.socket.common.message.ParserConfig;
import com.sqtoon.socket.common.message.Request;
import com.sqtoon.socket.common.message.Response;

public class Transaction<T>
{
	private static Logger logger = LoggerFactory.getLogger(SocketConfig.LOGGER_MAIN);

	private Request _request;
	private Response _response;
	private String _key;
	private long _updatetime;
	private TransactionManager _manager;
	public TransactionEvent TransactionEvent;
	public ArrayList<Body> _bodyList;

	private T _result;
	private Object _syncRoot = new Object();

	private static AtomicLong cseq = new AtomicLong();

	private Transaction(Request request)
	{
		_request = request;
		_request.setParentTrans(this);
		_updatetime = System.currentTimeMillis();
	}

	Transaction(Request request, boolean direction, TransactionManager manager)
	{
		this(request);
		if (direction)
		{
			request.removeHeaders(HeaderType.Csequence);
			request.addHeader(new Header(HeaderType.Csequence, getCseqValue()));
		}
		_key = request.getKey(direction);
		_manager = manager;
	}

	public Request request()
	{
		return _request;
	}

	public Response response()
	{
		return _response;
	}

	void receivceResponse(Response response)
	{
		_response = response;
		if (_bodyList != null)
		{
			_response.addBodys(_bodyList);
		}
	}

	public void SendRequest()
	{
		try
		{
			if (_request != null)
			{
				if (TransactionEvent == null)
					_manager.removeTransaction(_key);
				_manager.getConnection().sendRequest(_request);
			}
		}
		catch (Exception ex)
		{
			DoSendFailed();
		}
	}

	public void bindTransactionCreated(TransactionCreated event)
	{
		_manager.getConnection().registerTransactionCreated(event);
	}

	public void SendResponse(byte code)
	{
		Response response = new Response(_request, code);
		try
		{
			doSendResponse(response);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("Send Response Failed ", response.toString());
		}
	}

	private void doSendResponse(Response response)
	{
		try
		{
			if (response != null)
			{
				_manager.getConnection().sendResponse(response);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("Send Response Failed  ", response.toString());
		}
	}

	public void SendResponse(Response response)
	{
		if (response != null)
		{
			_response = response;
			int bodySize = response.getBodySize();
			int messageSize = response.getHeaderSize() + bodySize + 2;
			if (messageSize > ParserConfig.getMaxMessageSize())
			{
				Response baseResp = response.cloneSpecialHeader();
				int initSize = baseResp.getHeaderSize() + baseResp.getBodySize() + 2;
				Header sizeHeader = new Header(HeaderType.Status, bodySize - (3 * response.getBodys().size()));
				baseResp.addHeader(sizeHeader);
				int messagelength = initSize + sizeHeader.getValueLength() + 2;
				for (Body body : response.getBodys())
				{
					if (body.getValueLength() > TransactionConfig.getMaxResponseBodySize())
					{
						continue;
					}
					messagelength += body.getValueLength() + 3;
					if (messagelength > ParserConfig.getMaxMessageSize())
					{
						doSendResponse(baseResp);
						baseResp = response.cloneSpecialHeader();
						messagelength = initSize + body.getValueLength() + 3;
					}
					baseResp.addBody(body);
				}
				if (baseResp.getBodys().size() > 0)
					SendResponse(baseResp);
				response.releaseBodys();
				doSendResponse(response);
			}
			else
			{
				doSendResponse(response);
			}
		}
	}

	boolean isExpired(long millionSeconds)
	{
		return (System.currentTimeMillis() - _updatetime) > millionSeconds;
	}

	String getKey()
	{
		return _key;
	}

	void DoResponseReceive()
	{
		StringBuilder sb = new StringBuilder(255);
		sb.append("Method: [").append(_request.getMethod());
		sb.append("]; Transaction ResponseReceive. key:[").append(_key).append("]");
		logger.info(sb.toString());
		if (TransactionEvent != null)
			TransactionEvent.onResponseReceived(this, this.response());
	}

	public T getResult() throws InterruptedException
	{
		if (_result == null)
		{
			synchronized (_syncRoot)
			{
				_syncRoot.wait(1000);
			}
		}
		return _result;
	}

	public void setResult(T result)
	{
		_result = result;
		synchronized (_syncRoot)
		{
			_syncRoot.notify();
		}
	}

	public void DoSendFailed()
	{
		try
		{
			TransactionEvent event = TransactionEvent;
			_manager.removeTransaction(_key);

			StringBuilder sb = new StringBuilder(255);
			sb.append("Method: [");
			sb.append(_request.getMethod());
			sb.append("]; Transaction SendFailed  key:[");
			sb.append(_key);
			sb.append("]");
			logger.info(sb.toString());
			if (event != null)
				event.onSendFailed(this);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("CinTransaction  DoSendFailed()");
		}
	}

	void DoTimeoutArgs()
	{
		try
		{
			_manager.removeTransaction(_key);
			StringBuilder sb = new StringBuilder(255);
			sb.append("Method: [");
			sb.append(_request.getMethod());
			sb.append("]; Transaction Timeout  key:[");
			sb.append(_key);
			sb.append("]");
			logger.info(sb.toString());
			if (TransactionEvent != null)
				TransactionEvent.onTimeout(this);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("CinTransaction DoTimeoutArgs()");
		}
	}

	private static synchronized long getCseqValue()
	{
		if (cseq.longValue() == 0x00000000FFFFFFFFL)
			cseq.set(0);
		return cseq.incrementAndGet();
	}

	public void updateTransaction()
	{
		_updatetime = System.currentTimeMillis();
	}

	public void saveBodies(ArrayList<Body> bodies)
	{
		if (_bodyList == null)
		{
			_bodyList = new ArrayList<Body>();
		}
		_bodyList.addAll(bodies);
	}
}
