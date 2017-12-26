package com.tojoycloud.report;

public class UserReport extends ResponseReport
{
	private static final long serialVersionUID = 1L;

	public UserReport()
	{
		super();
	}

	public UserReport(Byte responseCode)
	{
		super(responseCode);
	}

	private long userid;
	private String username;
	private String nickname;
	private String phonenum;
	private String gender;
	private String identification;// 身份证号
	private String age;
	private String occupation;// 职业
	private String location;
	private String headimgurl;
	private String sk;
	private String vercode;

	public long getUserid()
	{
		return userid;
	}

	public void setUserid(long userid)
	{
		this.userid = userid;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getNickname()
	{
		return nickname;
	}

	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}

	public String getPhonenum()
	{
		return phonenum;
	}

	public void setPhonenum(String phonenum)
	{
		this.phonenum = phonenum;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public String getSk()
	{
		return sk;
	}

	public void setSk(String sk)
	{
		this.sk = sk;
	}

	public String getIdentification()
	{
		return identification;
	}

	public void setIdentification(String identification)
	{
		this.identification = identification;
	}

	public String getAge()
	{
		return age;
	}

	public void setAge(String age)
	{
		this.age = age;
	}

	public String getOccupation()
	{
		return occupation;
	}

	public void setOccupation(String occupation)
	{
		this.occupation = occupation;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getHeadimgurl()
	{
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl)
	{
		this.headimgurl = headimgurl;
	}

	public String getVercode()
	{
		return vercode;
	}

	public void setVercode(String vercode)
	{
		this.vercode = vercode;
	}

}
