<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="user-scalable=1, initial-scale=1,maximum-scale=1, minimum-scale=1, width=device-width, height=device-height"/>
	<%@ include file="/WEB-INF/jsp/commons/include.com.jsp" %>
	<%@ include file="/WEB-INF/jsp/commons/include_login.jsp" %>
    <title>意见反馈</title>
    <style>
         strong,b{font-weight:normal;}
    </style>
</head>
<body>
<div class="wrap">
	<header>
    	<a href="javaScript:void(0)" onclick="backForAndroid()"><span class="header_return"><img src="${path}/resource/images/return.png"></span></a>
        <span class="header_tijiao"  onclick="save()"><a>提交</a></span>
        <h1>意见反馈</h1>
        
    </header>
	<section style="overflow: inherit;">
    	<div class="box_box yjfh_box">
        <p>意见反馈(500字)</p>
        <textarea name="message" id="message" maxlength="500" cols="" rows="10" placeholder="请描叙您的意见与建议，谢谢您的反馈。"></textarea>
        </div>
    
    </section>
    

</div>
</body>
</html>

<script>
//var m = "${me.getId()}";

function backForAndroid(){
	
	document.location.href= "${path}/m/qfy/mineIndex.do";
}

 function save(){
	 var content = $("#message").val();
	 if(!content){
		 layer.msg("请填写意见！");
		 return;
	 }
	 zdy_ajax({
			url: "${path}/m/qfy/messagesave.do",
			showLoading:false,
			data:{
				//m:m,
				content:content
			},
			success: function(data,res){
					
					 var last_url="${path}/m/qfy/mineIndex.do";
						top.location.href=last_url;
			},
		    error:function(e){
			   //alert(e);
		    }
		}); 
 }

</script>