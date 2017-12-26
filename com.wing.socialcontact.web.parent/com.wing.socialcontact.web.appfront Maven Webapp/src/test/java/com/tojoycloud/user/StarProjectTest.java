package com.tojoycloud.user;

import com.alibaba.fastjson.JSON;
import com.tojoycloud.common.report.base.AppInfo;
import com.tojoycloud.common.report.base.CommandInfo;
import com.tojoycloud.common.report.base.DeviceInfo;
import com.tojoycloud.common.report.base.UserProperty;
import com.tojoycloud.common.util.ReportUtil;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.service.wx.bean.ProjectSupermarketCustomer;
import com.wing.socialcontact.service.wx.bean.ProjectSupermarket;
import com.wing.socialcontact.service.wx.bean.ProjectSupermarketImages;
import com.wing.socialcontact.service.wx.bean.ProjectWill;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.util.*;

public class StarProjectTest
{
	private static   String url2 = "http://localhost:8181/appfront/m/starProject";
	private static   String url1 = "http://localhost:8181/appfront/m/attention";

/*	private static  String url1 = "http://www.tojoycloud.org/appfront/m/attention";
	private static  String url2 = "http://www.tojoycloud.org/appfront/m/starProject";*/

	private static void  queryStarProjectList(){

		Map data = new HashMap();
		data.put("page", "1");
		data.put("size","10");

		Util.post(url2+"/queryStarProjectList",data);

	}

	private static void  addStarProjectCustomer(){

		Map data = new HashMap();
		ProjectWill projectWill  = new ProjectWill();
		projectWill.setPrjId("885f24134c0a47efadcea16b1883e6b7");
		projectWill.setWillType("1d1f020b5daf4bfb90ef5f735ce607de");
		projectWill.setWillDesc("我有意向嘿嘿嘿");

		String json = JSON.toJSONString(projectWill);
		data.put("projectWill",json);

		Util.post(url2+"/addStarProjectCustomer",data);

	}

	private static void  queryStarProjectDetail(){

		Map data = new HashMap();
		data.put("id", "885f24134c0a47efadcea16b1883e6b7");

		Util.post(url2+"/queryStarProjectDetail",data);
	}

	private static void  queryIntentionType(){

		Map data = new HashMap();

		Util.post(url2+"/queryIntentionType",data);
	}


	public static void main(String[] args)
	{
		queryIntentionType();
		//addStarProjectCustomer();
		//queryStarProjectDetail();
		//queryStarProjectList();
		//cancelAttention();
	}
}
