package com.tojoy.business.common.test;

import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@ImportResource({"classpath:proxy-servlet.xml","classpath:dubbo.xml"})
public class RewardTest
{

	public static String url = "http://localhost:28080/businessCommon/reward";


	public static void rewardJB()
	{
		Map data = new HashMap();
		data.put("key","project");
		data.put("fkId","1");
		data.put("jAmount","1");
		data.put("businessType","17");
		data.put("remark","打赏文章");

		Util.post(url+"/rewardJB",data);
	}

	public static void main(String[] args)
	{
		System.out.println(new Date());
		rewardJB();
		System.out.println(new Date());
	}
}
