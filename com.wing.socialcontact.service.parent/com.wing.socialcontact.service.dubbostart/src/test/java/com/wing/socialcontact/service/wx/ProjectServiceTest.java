package com.wing.socialcontact.service.wx;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wing.socialcontact.service.wx.api.IProjectService;
import com.wing.socialcontact.service.wx.bean.Project;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProjectServiceTest extends BaseJunit4Test {
	
	@Resource
	private IProjectService projectService;

	@Test
	public void insertProject() {
		for(int i=0;i<10;i++){
			Project t = new Project();
			t.setTitles("测试项目"+System.currentTimeMillis());
			t.setPrjType("1");//项目类型
			t.setPrjPoster("");//项目图片地址
			t.setFinancingSubject("融资主"+System.currentTimeMillis());
			t.setFinancingPurpose("融资用途");
			projectService.insertProject(t);
		}
	}
}
