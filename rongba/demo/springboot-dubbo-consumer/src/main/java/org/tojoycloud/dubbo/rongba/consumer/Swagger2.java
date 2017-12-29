package org.tojoycloud.dubbo.rongba.consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {
	
	@Bean
	public Docket investApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("投吧接口文档").apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("org.tojoycloud.dubbo.rongba.consumer.controller.invest"))
				.paths(PathSelectors.any()).build();
	}
	
	@Bean
	public Docket initPageApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("首页接口文档").apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("org.tojoycloud.dubbo.rongba.consumer.controller.initPage"))
				.paths(PathSelectors.any()).build();
	}
	
	@Bean
	public Docket orgApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("机构接口文档").apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("org.tojoycloud.dubbo.rongba.consumer.controller.org"))
				.paths(PathSelectors.any()).build();
	}
	
	@Bean
	public Docket orderTipApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("融资超市文档").apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("org.tojoycloud.dubbo.rongba.consumer.controller.finance.market"))
				.paths(PathSelectors.any()).build();
	}

	@Bean
	public Docket feedBackApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("股权融资接口文档").apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("org.tojoycloud.dubbo.rongba.consumer.controller.finance.stock"))
				.paths(PathSelectors.any()).build();
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("融吧接口").description("融吧相关接口").termsOfServiceUrl("")
				.contact(new Contact("", "", "")).version("1.0").build();
	}
	

}