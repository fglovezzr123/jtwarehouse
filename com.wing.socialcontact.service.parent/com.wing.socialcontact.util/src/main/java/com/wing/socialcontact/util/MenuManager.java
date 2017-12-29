package com.wing.socialcontact.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wing.socialcontact.util.pojo.CommonButton;
import com.wing.socialcontact.util.pojo.ComplexButton;
import com.wing.socialcontact.util.pojo.Menu;

/**
 * 菜单管理器类
 * 
 * @author liufeng
 * @date 2016-08-08
 */
public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);
	private static String appid;
	private static String appsecret;

	public MenuManager (String appid,String appsecret) {
		this.appid = appid;
		this.appsecret = appsecret;
	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	public static Menu getMenu() {
		CommonButton btn11 = new CommonButton();
		btn11.setName("天气预报");
		btn11.setType("click");
		btn11.setKey("11");

		CommonButton btn12 = new CommonButton();
		btn12.setName("公交查询");
		btn12.setType("click");
		btn12.setKey("12");

		CommonButton btn13 = new CommonButton();
		btn13.setName("周边搜索");
		btn13.setType("click");
		btn13.setKey("13");

		CommonButton btn14 = new CommonButton();
		btn14.setName("历史上的今天");
		btn14.setType("click");
		btn14.setKey("14");

		CommonButton btn21 = new CommonButton();
		btn21.setName("歌曲点播");
		btn21.setType("click");
		btn21.setKey("21");

		CommonButton btn22 = new CommonButton();
		btn22.setName("经典游戏");
		btn22.setType("click");
		btn22.setKey("22");

		CommonButton btn23 = new CommonButton();
		btn23.setName("美女电台");
		btn23.setType("click");
		btn23.setKey("23");

		CommonButton btn24 = new CommonButton();
		btn24.setName("人脸识别");
		btn24.setType("click");
		btn24.setKey("24");

		CommonButton btn25 = new CommonButton();
		btn25.setName("聊天唠嗑");
		btn25.setType("click");
		btn25.setKey("25");

		CommonButton btn31 = new CommonButton();
		btn31.setName("Q友圈");
		btn31.setType("click");
		btn31.setKey("31");

		CommonButton btn32 = new CommonButton();
		btn32.setName("电影排行榜");
		btn32.setType("click");
		btn32.setKey("32");

		CommonButton btn33 = new CommonButton();
		btn33.setName("幽默笑话");
		btn33.setType("click");
		btn33.setKey("33");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("生活助手");
		List btn1s = new ArrayList();
		btn1s.add(btn11);
		btn1s.add(btn12);
		btn1s.add(btn13);
		btn1s.add(btn14);
		mainBtn1.setSub_button(btn1s);

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("休闲驿站");
		List btn2s = new ArrayList();
		btn2s.add(btn21);
		btn2s.add(btn22);
		btn2s.add(btn23);
		btn2s.add(btn24);
		btn2s.add(btn25);
		mainBtn2.setSub_button(btn2s);

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("更多体验");
		List btn3s = new ArrayList();
		btn3s.add(btn31);
		btn3s.add(btn32);
		btn3s.add(btn33);
		mainBtn3.setSub_button(btn3s);

		/**
		 * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
		 * 
		 * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
		 * 比如，第三个一级菜单项不是"更多体验"，而直接是"幽默笑话"，那么menu应该这样定义：<br>
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
		 */
		Menu menu = new Menu();
		List btns = new ArrayList();
		btns.add(mainBtn1);
		btns.add(mainBtn2);
		btns.add(mainBtn3);
		menu.setButton(btns);

		return menu;
	}
}