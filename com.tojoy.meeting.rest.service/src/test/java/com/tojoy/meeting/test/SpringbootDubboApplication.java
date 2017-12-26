package com.tojoy.meeting.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import com.alibaba.dubbo.config.annotation.Reference;

@ImportResource({"classpath:dubbo.xml"})
@SpringBootApplication
//此处 userService 报空指针 如果是注解方式
public class SpringbootDubboApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(SpringbootDubboApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
