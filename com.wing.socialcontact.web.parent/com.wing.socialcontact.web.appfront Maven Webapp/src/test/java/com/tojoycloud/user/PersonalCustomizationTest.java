package com.tojoycloud.user;

import com.alibaba.fastjson.JSON;
import com.tojoycloud.common.report.base.AppInfo;
import com.tojoycloud.common.report.base.CommandInfo;
import com.tojoycloud.common.report.base.DeviceInfo;
import com.tojoycloud.common.report.base.UserProperty;
import com.tojoycloud.common.util.ReportUtil;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class PersonalCustomizationTest
{


	private static  String url1 = "http://18.18.16.229:8181/appfront/m/personalCustomization";

	//private static  String url1 = "http://www.tojoycloud.org/appfront/m/personalCustomization";

	private static void  queryPersonalCustomizations(){

		Map data = new HashMap();
		data.put("page", "1");
		data.put("size", "10");
		Util.post(url1+"/queryPersonalCustomizations",data);

	}

	private static void  getPersonalCustomization(){

		Map data = new HashMap();
		data.put("id", "55a4b26884c14a7f9950433b7da2cf8f");
		Util.post(url1+"/getPersonalCustomization",data);

	}

	private static void  addIntentionalCustomer(){

		Map data = new HashMap();
		data.put("pcId", "55a4b26884c14a7f9950433b7da2cf8f");
		Util.post(url1+"/addIntentionalCustomer",data);

	}


	public static void main(String[] args)
	{
		addIntentionalCustomer();
	}
}
