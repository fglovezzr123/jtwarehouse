<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>

<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="资讯">
		<title>金服联盟</title>
		<link rel="stylesheet" href="${path}/resource/css/text-design.css" />
		<link rel="stylesheet" href="${path}/resource/css/gold-service.css" />
		<style type="text/css">
			.alcontent{
			padding:0
			}
		
		</style>
	</head>

	<body>
	<div class="wrapper" >
	 <div class="alcontent" id="newsContent">
	 
	   </div>
       </div>
       
       <div class='share active_A' style="color:white;background:#1087eb" id="downfile" >  
                                      
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