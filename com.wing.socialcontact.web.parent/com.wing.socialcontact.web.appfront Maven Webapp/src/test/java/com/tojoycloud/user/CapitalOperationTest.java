package com.tojoycloud.user;

import com.alibaba.fastjson.JSON;
import com.tojoycloud.common.report.base.AppInfo;
import com.tojoycloud.common.report.base.CommandInfo;
import com.tojoycloud.common.report.base.DeviceInfo;
import com.tojoycloud.common.report.base.UserProperty;
import com.tojoycloud.common.util.ReportUtil;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.service.wx.bean.InvestmentIntention;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CapitalOperationTest
{


	//private static  String url1 = "http://18.18.16.229:8181/appfront/m/capitalOperation";

	private static  String url1 = "http://www.tojoycloud.org/appfront/m/capitalOperation";

	private static void  queryCapitalOperation(){

		Map data = new HashMap();
		data.put("type", "2");
		Util.post(url1+"/queryCapitalOperation",data);
	}

	private static void  queryCapitalOperationCustomer(){

		Map data = new HashMap();
		data.put("page", "1");
		data.put("size", "10");
		Util.post(url1+"/queryCapitalOperationCustomer",data);
	}

	private static void  addCapitalOperationCustomer(){

		Map data = new HashMap();
		InvestmentIntention investmentIntention = new InvestmentIntention();
		investmentIntention.setMessage("留言");
		investmentIntention.setClassName("上市孵化,上市并购");
		investmentIntention.setPhone("13426210816");
		investmentIntention.setPosition(50000D);
		investmentIntention.setUserName("大胜");

		String json = JSON.toJSONString(investmentIntention);
		data.put("capitalOperationCustomer", json);

		Util.post(url1+"/addCapitalOperationCustomer",data);
	}

	public static void main(String[] args)
	{
		queryCapitalOperationCustomer();
		//addCapitalOperationCustomer();
	}
}
