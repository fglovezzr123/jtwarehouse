package com.tojoy.business.common.test;

import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@ImportResource({"classpath:proxy-servlet.xml","classpath:dubbo.xml"})
public class ShareTest
{

	public static String url = "http://localhost:28080/businessCommon/share";


	public static void add()
	{
		Map data = new HashMap();
		data.put("key","project");
		data.put("fkId","1");
		Util.post(url+"/add",data);
	}

	public static void main(String[] args)
	{
		System.out.println(new Date());
		add();
		System.out.println(new Date());
	}
}
