package com.test;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import com.alibaba.fastjson.JSON;
import com.tojoycloud.frame.common.ReportUtil;
import com.tojoycloud.frame.report.RequestReport;
import com.tojoycloud.frame.report.ResponseReport;
import com.tojoycloud.frame.report.entity.AppInfo;
import com.tojoycloud.frame.report.entity.CommandInfo;
import com.tojoycloud.frame.report.entity.DeviceInfo;
import com.tojoycloud.frame.report.entity.UserProperty;

public class Test
{
	public static String url = "http://localhost:8080/tojoycloud";

	// String url = "http://123.56.19.166:8080/tojoycloud/user/verification";
	// String url = "http://47.94.101.100:18088/tojoycloud/user/verification";

	public static void main(String[] args)
	{
		// testReport();
		testString();
	}

	public static void testString()
	{
		try
		{
			String newUrl = url + "/user/check?user=1233211234567";
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(newUrl);
			HttpResponse response = httpClient.execute(httpGet);
			System.out.println(response.getStatusLine());
			InputStream in = response.getEntity().getContent();
			byte[] bReaded = IOUtils.toByteArray(in);
			IOUtils.closeQuietly(in);
			String decodeString = new String(bReaded, 0, bReaded.length, "utf-8");
			System.out.println(decodeString);
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void testReport()
	{
		String newUrl = url + "/user/verification";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(newUrl);
		httpPost.setHeader("Content-Type", "application/x-json");
		try
		{
			RequestReport report = new RequestReport();
			// 加载事件信息
			CommandInfo userCommand = new CommandInfo();
			userCommand.setCommandName("Logon");
			report.setCommandInfo(userCommand);

			// 加载appinfo
			AppInfo appInfo = new AppInfo();
			appInfo.setProduct_id("tojoycloud_app");
			appInfo.setVersion_code("1.0.0");
			appInfo.setVisit_device("1");
			report.setAppInfo(appInfo);

			// 加载userProperty
			UserProperty userProperty = new UserProperty();
			userProperty.setUser_id("");
			report.setUserProperty(userProperty);
			DeviceInfo deviceInfo = new DeviceInfo();
			deviceInfo.setImei("000000");
			deviceInfo.setImsi("");
			deviceInfo.setLanguage("CN");
			deviceInfo.setModel("MHA");
			deviceInfo.setIs_emulator("false");
			deviceInfo.setSerialno("3HX5T17726004042000000000000000");
			deviceInfo.setManufacturer("HUAWEI");
			deviceInfo.setSdk_level("24");
			deviceInfo.setScreenHeight("1340");
			deviceInfo.setScreenWidth("750");
			deviceInfo.setDevice_name("HUAWEIMHA-AL00");
			report.setDeviceInfo(deviceInfo);

			byte[] bResponseToClient = new byte[0];
			String request = JSON.toJSONString(report);
			byte[] bufCompressed = ReportUtil.compressData(request.getBytes("utf-8")); // 压缩
			bResponseToClient = ReportUtil.ecrypt(bufCompressed); // 加密

			ByteArrayEntity reqEntity = new ByteArrayEntity(bResponseToClient);
			httpPost.setEntity(reqEntity);

			HttpResponse response = httpClient.execute(httpPost);
			System.out.println(response.getStatusLine());
			InputStream in = response.getEntity().getContent();
			String responseReport = ReportUtil.getRequestStr(in);//
			System.out.println(responseReport);
			ResponseReport rr = JSON.parseObject(responseReport, ResponseReport.class);
			System.out.println("--------" + rr.getResponseCode() + "---------");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
