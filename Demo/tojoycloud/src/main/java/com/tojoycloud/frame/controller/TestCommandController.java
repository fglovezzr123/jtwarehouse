package com.tojoycloud.frame.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dubbo.service.DemoService;
import com.tojoycloud.frame.report.RequestReport;
import com.tojoycloud.frame.report.ResponseReport;
import com.tojoycloud.frame.report.base.ResponseCode;

@Controller
@RequestMapping("/user")
public class TestCommandController
{
	@Resource
	private DemoService demoService;

	@RequestMapping(value = "/verification", method = RequestMethod.POST)
	public @ResponseBody
	ResponseReport sendSns(@RequestBody RequestReport user)
	{
		System.out.println(demoService.sayHello("qiaoweiran", System.currentTimeMillis()));
		ResponseReport br = new ResponseReport();
		br.setResponseCode(ResponseCode.OK);
		br.setResponseTips("发送成功!");
		return br;
	}

}
