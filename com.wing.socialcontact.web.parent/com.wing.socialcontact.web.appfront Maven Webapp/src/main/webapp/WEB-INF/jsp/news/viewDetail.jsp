<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>商友专访详情</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/informationDetail.css?v=${sversion}" />
		<link rel="stylesheet" href="${path}/resource/css/living.css?v=${sversion}" />
		<script type="text/javascript" src="${http}://cnstatic01.e.vhall.com/jssdk/dist/2.3.2/vhallSDK.js?v=${sversion}"></script>
			<style>
				.newsContent img{
					width:100%;
					height:auto;
				 }
			</style>
	</head>

	<body>
		<div class="T-infor" style="background: #f2f3f4;width: 100%; height:100%;">
			<div class="wrapper" id="wrapper">
				<div class="scrollbar" id="scrollbar">

				</div>
			</div>
			<h2 class="titleview" id="titlediv"></h2>
			<p class="news" id="newsdiv"></p>
			<div class="newsContent" id="newsContent">	</div>
			 </div>	
	</body>
<script type="text/javascript">	
var isOk = false;
$(document).ready(function() {
	zdy_ajax({
		url: "${path}/m/news/viewDetail.do",
		data:{
			id:'${id}'
		},
		success: function(data,res){
			if(res.code == 0){
				var news = res.dataobj.news;
				var obj = res.dataobj.signObj;
				var oss = res.dataobj.ossurl;
				$("#titlediv").append(news.newsTitle);
				$("#newsdiv").append(news.createTime+"&nbsp;&nbsp;&nbsp;&nbsp;"+news.source);
				var con = '<p>摘要 :'+news.summary+'<br/><img src="'+oss+news.imagePath+'" alt="" /><div class="vimg" id="vedios" style="height:3.6rem"></div>'+news.content+'</p>';
				$("#newsContent").append(con);
				if(obj!=null){
					isOk = true;
				}
				if(isOk){
					VHALL_SDK.init({
					   account : obj.account,
					   email : obj.email,
					   username : obj.username,
					   roomid : obj.roomid,
					   app_key : obj.app_key,
					   signedat : obj.signedat,
					   sign : obj.sign,
					   facedom :'',
					   textdom : '',
					   videoContent : '#vedios',
					});
					VHALL_SDK.on('vhall_record_history_chat_msg', function(msg) {
						alert(JSON.stringify(msg))
				    });
				    VHALL_SDK.on('ready', function() {});
				    VHALL_SDK.on('error', function(msg) {});
				    VHALL_SDK.on("playerReady", function(){});
				}else{
					$("#vedios").hide();
				}			
				//分享设置
				var _title = "商友专访";
				var _imgUrl = "http://tianjiu.oss-cn-beijing.aliyuncs.com/upload/system/a3a14eac-c89a-4e6a-bccd-99602c3f74ad.png";
				if(news.newsTitle.length > 0){
					_title = news.newsTitle;
				}
				if(news.imagePath.length > 0){
					_imgUrl =_oss_url+news.imagePath;
				}
				var _link = home_path+_path+"/m/news/viewDetailPage.do?id="+news.id;
				wxsharefun(_link,_title,_imgUrl);
			}
		}
	}); 
});
</script>
</html>