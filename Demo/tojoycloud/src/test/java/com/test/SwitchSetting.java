package com.test;

public class SwitchSetting
{
	public static final long share = 1;
	public static final long discus = 2;
	public static final long pay = 4;

	private long setting;

	public SwitchSetting(Long setting)
	{
		this.setting = setting;
	}

	public boolean getStatus(long settingType)
	{
		return (setting & settingType) == settingType;
	}

	public void switchChange(long switchType, boolean b)
	{
		if (b)
			setting = setting | switchType;
		else
			setting = setting & ~switchType;
	}

	public long getValue()
	{
		return this.setting;
	}

	public void setValue(long setting)
	{
		this.setting = setting;
	}

	public static long getDefaultSwtich()
	{
		SwitchSetting us = new SwitchSetting(0L);
		return us.getValue();
	}

	public static boolean isOpen(long value, long type)
	{
		return (value & type) == type;
	}
}
