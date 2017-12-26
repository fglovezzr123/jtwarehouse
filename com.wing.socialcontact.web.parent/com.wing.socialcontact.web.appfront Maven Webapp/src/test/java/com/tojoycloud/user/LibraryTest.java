package com.tojoycloud.user;

import com.alibaba.fastjson.JSON;
import com.wing.socialcontact.service.wx.bean.ProjectWill;

import java.util.HashMap;
import java.util.Map;

public class LibraryTest
{
	private static   String url2 = "http://localhost:8181/appfront/m/library";
	private static   String url1 = "http://localhost:8181/appfront/m/myCollection";
	private static  String url3 = "http://localhost:8181/appfront/m/comment";

/*	private static  String url1 = "http://www.tojoycloud.org/appfront/m/myCollection";
	private static  String url2 = "http://www.tojoycloud.org/appfront/m/library";*/

	private static void  queryLibraryList(){

		Map data = new HashMap();
		data.put("classId", "c993ec37dec341d28c205bfdba6441b1");
		data.put("page", "1");
		data.put("size","10");
		data.put("readTimesFlag",1);
		Util.post(url2+"/queryLibraryList",data);

	}

	private static void  queryLibraryDetail(){

		Map data = new HashMap();
		data.put("libraryId", "5bdba39b4ca54a5ea5d2111666a856e5");
		Util.post(url2+"/queryLibraryDetail",data);

	}

	private static void  queryCommentsPage(){

		Map data = new HashMap();
		data.put("fkId","5bdba39b4ca54a5ea5d2111666a856e5");
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
		data.put("fkId","fc3f62867f4048c894d9e9b69e632e2e");
		data.put("imgUrl","http://img4.imgtn.bdimg.com/it/u=3739350442,1564148734&fm=23&gp=0.jpg");
		data.put("cmeType","7");
		Util.post(url3+"/addComment",data);
	}

	private static void  addSubComment(){
		Map data = new HashMap();
		data.put("cmeDesc","aaaaaaaaaaaaa");
		data.put("parentId","6e0128606b004baf9c7624b7916e1729");
		///data.put("imgUrl","http://img4.imgtn.bdimg.com/it/u=3739350442,1564148734&fm=23&gp=0.jpg");
		data.put("cmeType","7");
		Util.post(url3+"/addComment",data);
	}

	private static void  thumbUpLibrary(){

		Map data = new HashMap();
		data.put("libraryId", "fc3f62867f4048c894d9e9b69e632e2e");
		Util.post(url2+"/thumbUpLibrary",data);
	}

	private static void  rewardJB(){

		Map data = new HashMap();
		data.put("libraryId", "fc3f62867f4048c894d9e9b69e632e2e");
		data.put("jCount", "1");
		Util.post(url2+"/rewardJB",data);
	}

	private static void  add(){

		Map data = new HashMap();
		data.put("collectionId", "fc3f62867f4048c894d9e9b69e632e2e");
		data.put("type", "1");
		Util.post(url1+"/add",data);
	}

	private static void  cancel(){

		Map data = new HashMap();
		data.put("collectionId", "fc3f62867f4048c894d9e9b69e632e2e");
		data.put("type", "1");
		Util.post(url1+"/cancel",data);
	}

	private static void  ThumbUp(){
		Map data = new HashMap();
		data.put("id","6e0128606b004baf9c7624b7916e1729");
		Util.post(url3+"/ThumbUp",data);
	}

	private static void  cancelThumbUp(){
		Map data = new HashMap();
		data.put("id","6e0128606b004baf9c7624b7916e1729");
		Util.post(url3+"/cancelThumbUp",data);
	}

	public static void main(String[] args)
	{
		//queryLibraryList();
		//queryLibraryDetail();
		//queryCommentsPage();
		//queryCommentByPId();

		//addComment();
		//addSubComment();
		//ThumbUp();
		//cancelThumbUp();


		//add();
		//cancel();

		//thumbUpLibrary();
		rewardJB();

	}
}
