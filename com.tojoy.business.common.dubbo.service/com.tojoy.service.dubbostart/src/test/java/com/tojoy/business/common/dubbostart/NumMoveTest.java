package com.tojoy.business.common.dubbostart;

public class NumMoveTest
{
	public static void main(String[] args)
	{
		// 基础转化
		int a = 12;
		String t = Integer.toBinaryString(a);// 十进制转二进制
		System.out.println(t);
		System.out.println(Integer.parseInt(t, 2));// 二进制转十进制

		SwitchSetting setting = new SwitchSetting(0L);

		setting.switchChange(SwitchSetting.share, true); // 设置分享状态为支持
		setting.switchChange(SwitchSetting.comment, true);  //评论
		setting.switchChange(SwitchSetting.reward, true);  //打赏
		setting.switchChange(SwitchSetting.collection, true);  //收藏
		setting.switchChange(SwitchSetting.thumbUp, false);  //点赞

/*		SwitchSetting setting = new SwitchSetting(0L);
		System.out.println("现在打赏状态" + setting.getStatus(SwitchSetting.pay));
		setting.switchChange(SwitchSetting.pay, true); // 设置打赏状态为支持
		System.out.println("修改后打赏状态" + setting.getStatus(SwitchSetting.pay));
		System.out.println("现在分享状态" + setting.getStatus(SwitchSetting.share));
		setting.switchChange(SwitchSetting.share, true); // 设置分享状态为支持
		System.out.println("修改后分享状态" + setting.getStatus(SwitchSetting.share));*/

		System.out.println("现在的开关设置值" + setting.getValue());
		System.out.println("转换成2进制后的开关状态" + Long.toBinaryString(setting.getValue()));


		/*System.out.println("share" + Long.toBinaryString(SwitchSetting.share));
		System.out.println("discus" + Long.toBinaryString(SwitchSetting.discus));
		System.out.println("pay" + Long.toBinaryString(SwitchSetting.pay));
		System.out.println("collection" + Long.toBinaryString(SwitchSetting.collection));
		System.out.println("thump" + Long.toBinaryString(SwitchSetting.thumb));*/

	}
}
