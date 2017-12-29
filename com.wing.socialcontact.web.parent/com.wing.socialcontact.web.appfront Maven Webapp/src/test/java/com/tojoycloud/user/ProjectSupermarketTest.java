package com.tojoycloud.user;

import com.alibaba.fastjson.JSON;
import com.tojoycloud.common.report.base.AppInfo;
import com.tojoycloud.common.report.base.CommandInfo;
import com.tojoycloud.common.report.base.DeviceInfo;
import com.tojoycloud.common.report.base.UserProperty;
import com.tojoycloud.common.util.ReportUtil;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.service.wx.bean.ProjectSupermarketCustomer;
import com.wing.socialcontact.service.wx.bean.ProjectSupermarket;
import com.wing.socialcontact.service.wx.bean.ProjectSupermarketImages;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.util.*;

public class ProjectSupermarketTest
{
	private static   String url2 = "http://localhost:8181/appfront/m/projectSupermarket";
	private static   String url1 = "http://localhost:8181/appfront/m/attention";


/*	private static  String url1 = "http://www.tojoycloud.org/appfront/m/attention";
	private static  String url2 = "http://www.tojoycloud.org/appfront/m/projectSupermarket";*/


	private static void  queryProvinceList(){

		Map data = new HashMap();
		Util.post(url2+"/queryProvinceList",data);

	}

	private static void  queryIndustryList(){

		Map data = new HashMap();
		Util.post(url2+"/queryIndustryList",data);

	}

	private static void  queryCooperativeModeList(){

		Map data = new HashMap();
		Util.post(url2+"/queryCooperativeModeList",data);

	}

	private static void  getCityOrCounty(){

		Map data = new HashMap();
		data.put("pId", "402881ec3f74d2d5013f74dc2e5d0f4b");
		Util.post(url2+"/getCityOrCounty",data);

	}

	private static void  queryProjectSupermarketList(){

		Map data = new HashMap();
		data.put("page", "1");
		data.put("size","10");
		//data.put("province","402881ec3f74d2d5013f74dc2e3d0f11");
	/*	data.put("city","1");
		data.put("county","1");
		data.put("industry","1");*/
		Util.post(url2+"/queryProjectSupermarketList",data);

	}

	private static void  queryProjectSupermarketDetail(){

		Map data = new HashMap();

		data.put("id","606ce247cfb94f33927448b46a40083c");
		Util.post(url2+"/queryProjectSupermarketDetail",data);
	}

	private static void  addProjectSupermarket(){

		Map data = new HashMap();
		ProjectSupermarket projectSupermarket = new ProjectSupermarket();
		projectSupermarket.setUserId("10116");
		projectSupermarket.setIntroduce("介绍");
		projectSupermarket.setName("阿里巴巴单车");
		projectSupermarket.setIllustration("说明");
		projectSupermarket.setTeamPresentation("团队合作");
		projectSupermarket.setHighlights("亮点");
		projectSupermarket.setIsShow(0);
		projectSupermarket.setSort(0);
		projectSupermarket.setIsTop(0);
		projectSupermarket.setIsDelete(0);
		projectSupermarket.setProvince("402881ec3f74d2d5013f74dc2e5d0f12");
		projectSupermarket.setCity("402881ec3f74d2d5013f74dc2e5d0f4b");
		projectSupermarket.setCounty("402881ec3f74d2d5013f74dc2e5d0f5b");
		projectSupermarket.setIndustry("bf83c52618fe11e799d17cd30abeb079");
		projectSupermarket.setCooperativeMode("25bad7d1808f4779aba40e54f8552940");
		projectSupermarket.setCreateTime(new Date());
		projectSupermarket.setCreateUserId("402881f73e1c4ba4013e1c4c08470001");

		ProjectSupermarketImages projectSupermarketImages = new ProjectSupermarketImages();
		projectSupermarketImages.setImageUrl("http://tianjiutest.oss-cn-beijing.aliyuncs.com/tojoy/tojoyClould/projectSupermarket/201711/8/image/b91f6904-bdcc-4fe0-b4e5-ca1139d4b6e1.png?x-oss-process=style/YS200200");
		projectSupermarketImages.setCreateTime(new Date());

		List<ProjectSupermarketImages> list = new ArrayList<>();
		list.add(projectSupermarketImages);
		projectSupermarket.setProjectSupermarketImagesList(list);


		String json = JSON.toJSONString(projectSupermarket);
		data.put("projectSupermarket",json);
		Util.post(url2+"/addProjectSupermarket",data);
	}

	private static void  addProjectCustomer(){

		Map data = new HashMap();
		ProjectSupermarketCustomer projectCustomer = new ProjectSupermarketCustomer();
		projectCustomer.setUserId("10116");
		projectCustomer.setFkId("3799968e45e0443eb09c862d51ae51c3");
		projectCustomer.setIsHandle(0);
		projectCustomer.setDeleted(0);
		projectCustomer.setCreateUserId("10116");
		projectCustomer.setCreateTime(new Date());
		String json = JSON.toJSONString(projectCustomer);
		data.put("projectCustomer",json);
		Util.post(url2+"/addProjectCustomer",data);
	}

	private static void  addAttention(){

		Map data = new HashMap();
		data.put("fkId", "b051290b73174c6fae1f4465d3d4986e");
		data.put("attentionType","prj");

		Util.post(url1+"/addAttention",data);
	}

	private static void  cancelAttention(){

		Map data = new HashMap();
		data.put("fkId", "c7ec2f8366a6425fb02099ec2be167ac");

		Util.post(url1+"/cancelAttention",data);
	}

	private static void  queryBannerList(){

		Map data = new HashMap();

		Util.post(url2+"/queryBannerList",data);
	}


	public static void main(String[] args)
	{
		queryCooperativeModeList();
		//addProjectCustomer();
		//queryBannerList();
		//cancelAttention();
	}
}
