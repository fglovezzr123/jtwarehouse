package com.test;

public class NumMoveTest
{
	public static void main(String[] args)
	{
		// 基础转化
		int a = 4;
		String t = Integer.toBinaryString(a);// 十进制转二进制
		System.out.println(t);
		System.out.println(Integer.parseInt(t, 2));// 二进制转十进制

		SwitchSetting setting = new SwitchSetting(0L);
		System.out.println("现在打赏状态" + setting.getStatus(SwitchSetting.pay));
		setting.switchChange(SwitchSetting.pay, true); // 设置打赏状态为支持
		System.out.println("修改后打赏状态" + setting.getStatus(SwitchSetting.pay));
		System.out.println("现在分享状态" + setting.getStatus(SwitchSetting.share));
		setting.switchChange(SwitchSetting.share, true); // 设置分享状态为支持
		System.out.println("修改后分享状态" + setting.getStatus(SwitchSetting.share));
		System.out.println("现在的开关设置值" + setting.getValue());
		System.out.println("转换成2进制后的开关状态" + Long.toBinaryString(setting.getValue()));
	}
}
