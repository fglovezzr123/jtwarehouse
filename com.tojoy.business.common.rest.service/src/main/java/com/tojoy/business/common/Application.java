package com.tojoy.business.common;

import com.github.pagehelper.autoconfigure.MapperAutoConfiguration;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@ImportResource({"classpath:dubbo.xml","classpath:proxy-servlet.xml"})
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,MapperAutoConfiguration.class,WebMvcAutoConfiguration.class})
public class Application  implements CommandLineRunner {
	private static final Logger logger = Logger.getLogger(Application.class);

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		logger.error("项目启动 ");
	}
}