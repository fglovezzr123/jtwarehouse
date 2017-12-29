package com.tojoycloud.user;

import com.alibaba.fastjson.JSON;
import com.wing.socialcontact.service.wx.bean.InvestmentIntention;

import java.util.HashMap;
import java.util.Map;

public class UserPhotoTest
{


	private static  String url1 = "http://18.18.16.229:8181/appfront/m/userPhoto";

	//private static  String url1 = "http://www.tojoycloud.org/appfront/m/userPhoto";

	//private static  String url2 = "http://www.tojoycloud.org/appfront/m/app";
	private static  String url2 = "http://localhost:8181/appfront/m/app";

	private static void  queryStatusByUserId(){
		Map data = new HashMap();
		data.put("userId","10116");
		Util.post(url1+"/queryStatusByUserId",data);
	}

	private static void  mobile_login(){
		Map data = new HashMap();
		data.put("mobile","13426210816");
		data.put("dyz","301731");
		Util.post(url2+"/mobile_login",data);
	}

	private static void  setUserPhoto(){

		Map data = new HashMap();
		data.put("userId","10116");
		data.put("photoUrl", "tianjiu/news/2017/5/14/de375224-8895-4bf5-8059-c9bdc9bde454.png");

		Util.post(url1+"/setUserPhoto",data);
	}

	public static void main(String[] args)
	{
		//queryStatusByUserId();
		setUserPhoto();
		//mobile_login();
	}
}
