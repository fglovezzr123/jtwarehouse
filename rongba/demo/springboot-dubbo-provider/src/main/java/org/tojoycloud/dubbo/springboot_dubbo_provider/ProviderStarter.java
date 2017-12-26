package org.tojoycloud.dubbo.springboot_dubbo_provider;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * start the application
 */
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
