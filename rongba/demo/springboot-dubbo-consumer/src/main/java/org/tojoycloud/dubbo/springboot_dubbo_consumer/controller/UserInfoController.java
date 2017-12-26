/**
 * 
 */
package org.tojoycloud.dubbo.springboot_dubbo_consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tojoycloud.dubbo.springboot_dubbo_api.service.UserInfoService;

/**
 * @author zhangpengzhi
 *
 */
@Controller
@RequestMapping(value = "/user/")
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;
	
	@RequestMapping(value = "hello/")
	public String sayHello(String name){
		
		return userInfoService.sayHello("慌张");
	}
}
