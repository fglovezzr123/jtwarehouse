package com.tojoycloud.user;

import com.alibaba.fastjson.JSON;
import com.tojoycloud.common.report.base.AppInfo;
import com.tojoycloud.common.report.base.CommandInfo;
import com.tojoycloud.common.report.base.DeviceInfo;
import com.tojoycloud.common.report.base.UserProperty;
import com.tojoycloud.common.util.ReportUtil;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.nutz.dao.util.cri.Static;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ZhuGeTest
{
	private static   String url2 = "http://localhost:8181/appfront/m/zhuGeFAQ";
	private static  String url1 = "http://localhost:8181/appfront/m/comment";

/*	private static  String url1 = "http://www.tojoycloud.org/appfront/m/comment";
	private static  String url2 = "http://www.tojoycloud.org/appfront/m/zhuGeFAQ";*/


	private static void  queryIndustryList(){

		Map data = new HashMap();
/*		data.put("mobile", "18500610761");
		data.put("id", "investmentTreasure");*/
		Util.post(url2+"/queryIndustryList",data);

	}

	private static void  queryZhuGeFAQ(){

		Map data = new HashMap();
		data.put("page", "1");
		data.put("size", "10");
		data.put("type","1");
		data.put("voType","70c7c6118768416fbaa748a88a6a05ef");
		Util.post(url2+"/queryZhuGeFAQ",data);

	}

	private static void  queryMyZhuGeFAQ(){

		Map data = new HashMap();
		data.put("page", "1");
		data.put("size", "10");
		data.put("type","2");
		Util.post(url2+"/queryMyZhuGeFAQ",data);

	}

	private static void  add(){

		Map data = new HashMap();
		data.put("question", "上来自己动122");
		data.put("iTypeId", "16aaf8ee40184777abec7400c638c07f");
		data.put("rewardPrice","200");
		data.put("isShow","1");
		Util.post(url2+"/add",data);

	}

	private static void  queryDetail(){

		Map data = new HashMap();

		data.put("id","b3410fb2f6ff44e7aa3bad6e1d968dab");
		Util.post(url2+"/queryDetail",data);
	}

	private static void  addAnswer(){

		Map data = new HashMap();

		data.put("fkId","2b69ddc1e17343839b72c0ad323bcd6e");
		data.put("content","嘿嘿嘿2");
		Util.post(url2+"/addAnswer",data);

	}

	private static void  setBestAnswer(){

		Map data = new HashMap();

		data.put("fqaId","2b69ddc1e17343839b72c0ad323bcd6e");
		String ids = "78305e23210a45b28f46dbdb5204eb9e,ee022bd6eb0a4db182959c60be5e0f01";
		data.put("answerIds",ids);
		Util.post(url2+"/setBestAnswer",data);

	}

	private static void  queryCommentByPId(){

		Map data = new HashMap();

		data.put("parentId","25578edf82d24837a7f443fb7ea3487b");
		data.put("type","6");
		Util.post(url1+"/queryCommentByPId",data);
	}

	private static void  ThumbUp(){
		Map data = new HashMap();
		data.put("id","25578edf82d24837a7f443fb7ea3487b");
		Util.post(url1+"/ThumbUp",data);
	}

	private static void  cancelThumbUp(){
		Map data = new HashMap();
		data.put("id","25578edf82d24837a7f443fb7ea3487b");
		Util.post(url1+"/cancelThumbUp",data);
	}

	private static void  addComment(){
		Map data = new HashMap();
		data.put("cmeDesc","hahahahhahhh");
		data.put("parentId","25578edf82d24837a7f443fb7ea3487b");
		data.put("cmeType","6");
		Util.post(url1+"/addComment",data);
	}

	public static void main(String[] args)
	{
		//add();
		//addAnswer();
		setBestAnswer();
		//queryCommentByPId();
		//add();
		//queryZhuGeFAQ();
		//queryDetail();
		//queryCommentByPId();
		//queryIndustryList();
	}
}
