package com.tojoy.business.common.test;

import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@ImportResource({"classpath:proxy-servlet.xml","classpath:dubbo.xml"})
public class CollectionTest
{

	public static String url = "http://localhost:28080/businessCommon/collection";


	public static void add()
	{
		Map data = new HashMap();
		data.put("key","project");
		data.put("fkId","1");
		Util.post(url+"/add",data);
	}

	public static void cancel()
	{
		Map data = new HashMap();
		data.put("key","project");
		data.put("fkId","1");
		Util.post(url+"/cancel",data);
	}

	public static void queryCountIsCollection()
	{
		Map data = new HashMap();
		data.put("key","project");
		data.put("fkId","1");
		Util.post(url+"/queryCountIsCollection",data);
	}


	public static void main(String[] args)
	{

		System.out.println(new Date());
		//cancel();
		add();
		queryCountIsCollection();
		System.out.println(new Date());

	}
}
