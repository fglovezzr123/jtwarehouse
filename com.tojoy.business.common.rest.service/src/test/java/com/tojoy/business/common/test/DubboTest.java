package com.tojoy.business.common.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:dubbo.xml" })
//可以运行 自行修改 dubbo.xml中address
public class DubboTest {


	@Test
	public void pors() {

	}
}
