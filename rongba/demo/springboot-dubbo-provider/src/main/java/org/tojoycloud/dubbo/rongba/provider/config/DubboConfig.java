package org.tojoycloud.dubbo.rongba.provider.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:dubbo/dubbo.properties")
@ImportResource({"classpath:dubbo/*.xml"})
public class DubboConfig {
	
}
