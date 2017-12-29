package com.sw.chat;

import com.sqtoon.socket.common.PersonalId;

public class UserInfo
{
	private long _uid;
	private PersonalId _pid;
	private byte[] _randomkey;
	private long _mobileNo;
	private String _clientVersion;
	private long _oem;
	private boolean _isAuthorized;
	private boolean _clientReady;
	private boolean _checkCredential;
	private long _logonTime;
	private byte _language;

	public UserInfo()
	{
		_uid = 0;
		_pid = null;
		_randomkey = null;
		_mobileNo = 0;
		_clientVersion = "";
		_oem = 0;
		_isAuthorized = false;
		_checkCredential = false;
		_logonTime = System.currentTimeMillis();
		_language = 0;
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("_uid: ").append(_uid).append("\n");
		sb.append("_pid: ").append(_pid).append("\n");
		sb.append("_randomkey: ").append(_randomkey).append("\n");
		sb.append("_mobileNo: ").append(_mobileNo).append("\n");
		sb.append("_clientVersion: ").append(_clientVersion).append("\n");
		sb.append("_oem: ").append(_oem).append("\n");
		sb.append("_isAuthorized: ").append(_isAuthorized).append("\n");
		sb.append("_clientReady: ").append(_clientReady).append("\n");
		sb.append("_checkCredential: ").append(_checkCredential).append("\n");
		sb.append("_logonTime: ").append(_logonTime).append("\n");
		sb.append("_language: ").append(_language).append("\n");
		return sb.toString();
	}

	public void setUid(long uid)
	{
		if (uid < 0)
			throw new IllegalArgumentException("Uid MUST BE greater than 0.  Uid: " + uid);
		_uid = uid;
	}

	public long getUid()
	{
		return _uid;
	}

	public void setPid(String host, int port, int connectid)
	{
		_pid = new PersonalId(host, port, connectid);
	}

	public PersonalId getPid()
	{
		return _pid;
	}

	public void cleanRandomKey()
	{
		_randomkey = null;
	}

	public byte[] getRandomkey()
	{
		if (null == _randomkey)
			_randomkey = CinProxyUtil.getRandomkey();
		return _randomkey;
	}

	public boolean hasRandomkey()
	{
		return _randomkey != null;
	}

	public void setMobileNo(long mobileNo)
	{
		_mobileNo = mobileNo;
	}

	public long getMobileNo()
	{
		return _mobileNo;
	}

	public void setClientVersion(String clientVersion)
	{
		_clientVersion = clientVersion;
	}

	public String getClientVersion()
	{
		return _clientVersion;
	}

	public void setOEM(long oem)
	{
		_oem = oem;
	}

	public long getOEM()
	{
		return _oem;
	}

	public void setAuthorized(boolean value)
	{
		_isAuthorized = value;
	}

	public boolean isAuthorized()
	{
		return _isAuthorized;
	}

	public void setClientReady(boolean value)
	{
		_clientReady = value;
	}

	public boolean isClientReady()
	{
		return _clientReady;
	}

	public void setCheckCredential(boolean value)
	{
		_checkCredential = value;
	}

	public boolean isCheckCredential()
	{
		return _checkCredential;
	}

	public void setLogonTime(long time)
	{
		_logonTime = time;
	}

	public long getLogonTime()
	{
		return _logonTime;
	}

	public byte getLanguage()
	{
		return _language;
	}

	public void setLanguage(byte language)
	{
		this._language = language;
	}

}
