package com.tojoycloud.user;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.alibaba.fastjson.JSON;
import com.tojoycloud.common.report.base.AppInfo;
import com.tojoycloud.common.report.base.CommandInfo;
import com.tojoycloud.common.report.base.DeviceInfo;
import com.tojoycloud.common.report.base.UserProperty;
import com.tojoycloud.common.util.ReportUtil;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;

public class UserTest
{
	public static void main(String[] args)
	{
		String url = "http://localhost:8080/mailList/m/app/maillist/uploadmaillist";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/x-json");
		try
		{
			RequestReport report = new RequestReport();
			// 加载事件信息
//			CommandInfo userCommand = new CommandInfo();
//			userCommand.setPhonenum("18515356361");
//			userCommand.setCommandName("Logon");
//			report.setCommandInfo(userCommand);
			Map data = new HashMap();
			data.put("mobiles", "18511595601,18511595601,18511595604,18511595603,18511595602");
			data.put("isLast", "1");
			CommandInfo userCommand = new CommandInfo();
			userCommand.setCommandName("sss");
			userCommand.setData(data);
			report.setCommandInfo(userCommand);
			// 加载appinfo
			AppInfo appInfo = new AppInfo();
			appInfo.setProductId("tojoycloud_app");
			appInfo.setVersionCode("1.0.0");
			appInfo.setVisitDevice("1");
			report.setAppInfo(appInfo);
			// 加载userProperty
			UserProperty userProperty = new UserProperty();
			userProperty.setUserId("10188");
			report.setUserProperty(userProperty);
			DeviceInfo deviceInfo = new DeviceInfo();
			deviceInfo.setImei("000000");
			deviceInfo.setImsi("");
			deviceInfo.setLanguage("CN");
			deviceInfo.setModel("MHA");
			deviceInfo.setIsEmulator("false");
			deviceInfo.setSerialNo("3HX5T17726004042000000000000000");
			deviceInfo.setManufacturer("HUAWEI");
			deviceInfo.setSdkLevel("24");
			deviceInfo.setScreenHeight("1340");
			deviceInfo.setScreenWidth("750");
			deviceInfo.setDeviceName("HUAWEIMHA-AL00");
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
			System.out.println("--------" + rr.getResponseTips() + "---------");
			System.out.println("--------" + rr.getResponseCode() + "---------");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
