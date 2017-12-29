package org.tojoycloud.dubbo.rongba.provider;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * start the application
 */
@EnableTransactionManagement
@SpringBootApplication
public class ProviderStarter {
	public static void main(String[] args) {
		SpringApplication.run(ProviderStarter.class, args);
		try {
			System.in.read(); // 按任意键退出
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}
}
