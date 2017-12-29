package com.tojoy.business.common.test;

import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@ImportResource({"classpath:proxy-servlet.xml","classpath:dubbo.xml"})
public class CommentTest
{

	private static  String url3 = "http://localhost:28080/businessCommon/comment";
	//public static String url3 = "http://www.tojoycloud.org/businessCommon/comment";

	private static void  queryCommentList(){
		Map data = new HashMap();
		data.put("fkId","ccd6aec862b94fe7ae509c34837298e3");
		data.put("key","library");
		Util.post(url3+"/queryCommentList",data);
	}

	private static void  querySubCommentByPId(){

		Map data = new HashMap();
		data.put("parentId","1cdf693f677549c5828aa9e5adcb812b");
		data.put("key","library");
		Util.post(url3+"/querySubCommentByPId",data);
	}

	private static void  addComment(){
		Map data = new HashMap();
		data.put("commentDesc","hahaah");
		data.put("fkId","ccd6aec862b94fe7ae509c34837298e3");
		data.put("imgUrl","tianjiu/wx/2017/8/24/be95aaaf-f141-4664-8e25-e1c08339f67c.jpgg");
		data.put("key","library");
		Util.post(url3+"/addComment",data);
	}

	private static void  addSubComment(){
		Map data = new HashMap();
		data.put("commentDesc","aaaaaaaaaaaaa");
		data.put("parentId","1cdf693f677549c5828aa9e5adcb812b");
		data.put("key","library");
		Util.post(url3+"/addComment",data);
	}


	private static void  thumbUp(){
		Map data = new HashMap();
		data.put("id","1cdf693f677549c5828aa9e5adcb812b");
		data.put("key","library");
		Util.post(url3+"/thumbUp",data);
	}

	private static void  cancelThumbUp(){
		Map data = new HashMap();
		data.put("id","1cdf693f677549c5828aa9e5adcb812b");
		data.put("key","library");
		Util.post(url3+"/cancelThumbUp",data);
	}

	public static void main(String[] args)
	{
		System.out.println(new Date());
		//addComment();
		//addSubComment();
		//thumbUp();
		cancelThumbUp();
		//queryCommentList();
		//querySubCommentByPId();
		System.out.println(new Date());
	}
}
