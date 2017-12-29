package com.tojoy.business.common.test;

import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@ImportResource({"classpath:proxy-servlet.xml","classpath:dubbo.xml"})
public class SwitchTest
{

	public static String url = "http://localhost:28080/businessCommon/switchCnf";


	public static void querySwitchCnfByKey()
	{
		Map data = new HashMap();
		data.put("key","project");
		Util.post(url+"/querySwitchCnfByKey",data);
	}

	public static void main(String[] args)
	{
		System.out.println(new Date());
		querySwitchCnfByKey();
		System.out.println(new Date());
	}
}
