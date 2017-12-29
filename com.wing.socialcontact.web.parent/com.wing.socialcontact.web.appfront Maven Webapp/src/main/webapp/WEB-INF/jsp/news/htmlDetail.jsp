<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>

<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="资讯">
		<title></title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css" />
		<link rel="stylesheet" href="${path}/resource/css/text-design.css" />
		<style>
		   .share{
		      background:#1087eb;
		   }
		</style>
	</head>

	<body>
	<div class="wrapper" id="newsContent">
       </div>
       
       <div class='share active_A' style="color:white" id="downfile">  
                                       下载APP,了解更多详情
       </div>
	
	</body>
<script type="text/javascript">	
$(document).ready(function() {
	zdy_ajax({
		url: "${path}/m/news/jflmDetail.do",
		data:{
			id:'${id}'
		},
		success: function(data,res){
			if(res.code == 0){
				var news = res.dataobj.news;
				$(document).attr("title",news.newsTitle);
				var isAppLogin = res.dataobj.isAppLogin;
				$("#newsContent").append("<p>"+news.content+"</p>");
				var url = "";
				var ua = navigator.userAgent.toLowerCase();	
				if (/iphone|ipad|ipod/.test(ua)) {//ios
					url = news.url2;	
				}else if(/android/.test(ua)) {//安卓
					url = news.url;	  	
				}
				if(isAppLogin == 1){
					$("#downfile").html("下载APP,了解更多详情");
				}else{
					$("#downfile").html("下载APP,了解更多详情");
				}
				$('#downfile').click(function(){
					window.location.href=url;
				});
			}
		}
	}); 
});
</script>
</html>