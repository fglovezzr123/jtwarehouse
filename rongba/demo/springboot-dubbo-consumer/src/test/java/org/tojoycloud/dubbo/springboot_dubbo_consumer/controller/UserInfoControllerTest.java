package org.tojoycloud.dubbo.springboot_dubbo_consumer.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tojoycloud.dubbo.springboot_dubbo_api.service.UserInfoService;
import org.tojoycloud.dubbo.springboot_dubbo_consumer.ConsumerStarter;

/**
 * @author zhangpengzhi
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ConsumerStarter.class)
public class UserInfoControllerTest {

	@Autowired
	private UserInfoService userInfoService;

	@Test
	public void sayHelloTest() {

		System.out.println(userInfoService.sayHello("慌张"));
	}
}
