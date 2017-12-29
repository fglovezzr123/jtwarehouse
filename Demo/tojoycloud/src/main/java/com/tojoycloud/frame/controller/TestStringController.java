package com.tojoycloud.frame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class TestStringController
{
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public @ResponseBody
	// String callback(@RequestHeader String CurTime, @RequestHeader String MD5,
	// @RequestHeader String CheckSum, @RequestBody String bodyString)
	String logon(@RequestParam String user)
	{
		return "success";
	}
}
