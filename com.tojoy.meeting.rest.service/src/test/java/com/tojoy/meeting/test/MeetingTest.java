package com.tojoy.meeting.test;

import com.alibaba.fastjson.JSON;
import com.tojoy.meeting.common.report.base.*;
import com.tojoy.meeting.common.util.ReportUtil;
import com.tojoy.meeting.report.RequestReport;
import com.tojoy.meeting.report.ResponseReport;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.SystemDefaultCredentialsProvider;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@ImportResource({"classpath:proxy-servlet.xml","classpath:dubbo.xml"})
public class MeetingTest
{

	public static String url = "http://localhost:8181/meetingApi/meeting";
	//public static String url = "http://www.tojoycloud.org/meetingApi/meeting";
	private static  String url3 = "http://localhost:8181/meetingApi/comment";

	public static void getHotMeetingList()
	{
		Map data = new HashMap();
		Util.post(url+"/getHotMeeting",data);
	}

	public static void getMeetingById()
	{
		Map data = new HashMap();
		data.put("meetingId","ccd6aec862b94fe7ae509c34837298e3");
		Util.post(url+"/getMeetingById",data);
	}

	public static void thumbUp()
	{
		Map data = new HashMap();
		data.put("meetingId","ccd6aec862b94fe7ae509c34837298e3");
		Util.post(url+"/thumbUp",data);
	}

	private static void  queryCommentsPage(){

		Map data = new HashMap();
		data.put("fkId","ccd6aec862b94fe7ae509c34837298e3");
		data.put("cmeType","7");
		Util.post(url3+"/queryCommentsPage",data);
	}

	private static void  queryCommentByPId(){

		Map data = new HashMap();
		data.put("parentId","b9ed16813e494a53a371a77a3080ae77");
		/*data.put("type","2");*/
		Util.post(url3+"/queryCommentByPId",data);
	}

	private static void  addComment(){
		Map data = new HashMap();
		data.put("cmeDesc","hahahahhahhh");
		data.put("fkId","ccd6aec862b94fe7ae509c34837298e3");
		data.put("imgUrl","http://img4.imgtn.bdimg.com/it/u=3739350442,1564148734&fm=23&gp=0.jpg");
		data.put("cmeType","7");
		Util.post(url3+"/addComment",data);
	}

	private static void  addSubComment(){
		Map data = new HashMap();
		data.put("cmeDesc","aaaaaaaaaaaaa");
		data.put("parentId","6e0128606b004baf9c7624b7916e1729");
		data.put("cmeType","7");
		Util.post(url3+"/addComment",data);
	}

	public static void isWhiteList()
	{
		Map data = new HashMap();
		data.put("meetingId","ccd6aec862b94fe7ae509c34837298e3");
		Util.post(url+"/isWhiteList",data);

	}

	public static void removeAttention()
	{
		Map data = new HashMap();
		data.put("meetingId","ccd6aec862b94fe7ae509c34837298e3");
		Util.post(url+"/removeAttention",data);

	}

	public static void getMeetingSignupCount()
	{

		Map data = new HashMap();
		data.put("meetingId","ccd6aec862b94fe7ae509c34837298e3");
		Util.post(url+"/getMeetingSignupCount",data);

	}

	public static void insertMeetingSignUp()
	{
		Map data = new HashMap();
		data.put("signUpJson","{\"annualSales\":\"\",\"attendType\":\"1\",\"coupon\":\"0\",\"deleted\":\"0\",\"isFree\":\"1\",\"mainBusiness\":\"\",\"meetingId\":\"6aecb8cbfbe74b29a83e3ce2edd4bcbf\",\"mobile\":\"13426210816\",\"orderId\":\"865b0182326341608e8b50a49161a2ac\",\"orderStatus\":\"2\",\"otherReq\":\"ggggff \",\"payCapital\":\"\",\"payTime\":\"\",\"payType\":\"2\",\"recLinkMan\":\"hjnbhhh\",\"regCapital\":\"\",\"remindTime\":\"\",\"signupTime\":\"2017-10-25 09:42:28\",\"tjLinkMan\":\"yhhhhg\",\"totalAssets\":\"\",\"userId\":\"10116\"}");
		Util.post(url+"/insertMeetingSignUp",data);

	}


	public static void saveForAttention()
	{
		Map data = new HashMap();
		data.put("meetingId","ccd6aec862b94fe7ae509c34837298e3");
		Util.post(url+"/saveForAttention",data);

	}

	public static void getWebinarStatus()
	{
		Map data = new HashMap();
		data.put("webinarId","520100723");
		Util.post(url+"/getWebinarStatus",data);

	}

	public static void toVhallUser()
	{
		Map data = new HashMap();
		data.put("thirdUserId","10120");
		data.put("thirdUserName","大胜测试");
		data.put("thirdUserHead","http://bbs.vhall.com/viewforum.php?f=6");
		Util.post(url+"/toVhallUser",data);

	}

	public static void getVhallUserId()
	{
		Map data = new HashMap();
		data.put("thirdUserId","10120");
		Util.post(url+"/getVhallUserId",data);

	}

	public static void rewardJB()
	{
		Map data = new HashMap();
		data.put("meetingId","ccd6aec862b94fe7ae509c34837298e3");
		data.put("jCount","1");
		Util.post(url+"/rewardJB",data);

	}

	public static void main(String[] args)
	{

		System.out.println(new Date());
		//thumbUp();
		//getHotMeetingList();
		//addComment();
		//queryCommentsPage();
		rewardJB();
		System.out.println(new Date());
		//insertMeetingSignUp();
		//getVhallUserId();
		/*System.out.println(new Date());


		System.out.println(new Date());
		getHotMeetingList();
		System.out.println(new Date());*/
		//getVhallUserId();
		//insertMeetingSignUp();
	}
}
