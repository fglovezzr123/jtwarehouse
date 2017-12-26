<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>

<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="资讯">
		<title>资讯详情</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/informationDetail.css" />
		<link rel="stylesheet" href="${path}/resource/css/living.css?v=${sversion}" />
		
		<script type="text/javascript" src="${http}://cnstatic01.e.vhall.com/jssdk/dist/2.3.0/vhallSDK.js"></script>
		<style type="text/css">
	    html,body{height:100%;}
		 .title{
			height:auto;
			font-size: .36rem;
			line-height:0.5rem;
			padding-top:0.2rem;
			padding-bottom:0.2rem;
        }
		
		.news {
		      height:.50rem;
		      line-height: .50rem;
        }
		/* .newsContent .v p{
         font-size: .16rem;  
      } */
      .newsContent img{
       width:100%;
       height:auto;
      }
      .msgBox .msg-r .imgBox_1 img{
	       width: 100%;
	       }
		</style>
	</head>

	<body>
		<div class="T-infor" style="background: #f2f3f4;width: 100%; height:100%;">
			<div class="wrapper" id="wrapper">
				<div class="scrollbar" id="scrollbar">
					<h2 class="title" id="titlediv"></h2>
			<p class="news" id="newsdiv"></p>
			<div class="newsContent" id="newsContent">	</div>
				
		    <jsp:include page="../commons/include_comment.jsp" >
			<jsp:param  name="id" value="${id}" />
			<jsp:param name="cmeType" value="1" />
		   </jsp:include><%--cmeType 1:资讯   2：合作 3：话题  4：活动 --%>
		   </div>
				</div>
			</div>
			
	</body>
<script type="text/javascript">	
var isOk = false;
$(document).ready(function() {
	zdy_ajax({
		url: "${path}/m/news/detail.do",
		data:{
			id:'${id}'
		},
		success: function(data,res){
			if(res.code == 0){
				var news = res.dataobj.news;
				var oss = res.dataobj.ossurl;
				console.log(news.content)
				//分享设置
				var _title = "资讯详情";
				var _imgUrl = "http://tianjiu.oss-cn-beijing.aliyuncs.com/upload/system/a3a14eac-c89a-4e6a-bccd-99602c3f74ad.png";
				if(news.newsTitle.length > 0){
					_title = news.newsTitle;
				}
				if(news.imagePath.length > 0){
					_imgUrl =_oss_url+news.imagePath;
				}
				var _link = home_path+_path+"/m/news/detailPage.do?id="+news.id;
				wxsharefun(_link,_title,_imgUrl,2,news.summary);
				
				$("#titlediv").append(news.newsTitle);
				$("#newsdiv").append(news.updateTime+"&nbsp;&nbsp;摘自："+news.source);
				var obj = res.dataobj.signObj;
				$("#newsContent").append('<p><div class="vimg" id="vedios"></div><div class="v">'+news.content+'</div></p>');
				//$("#newsContent").append('<p><img src="'+oss+news.imagePath+'" alt="" /><div class="vimg" id="vedios"></div><div class="v">'+news.content+'</div></p>');
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
			}
		}
	}); 
	
});
//点击分享图片
function clickimg(){
	$(".tips-s-img").slideUp("slow", function() {
		$(".com-backdrop").hide();
	});
}
//分享
var fx = function() {
		  if (is_weixn()) {
			fx_flag = true;
			$(".com-backdrop").show();
			$(".tips-s-img").slideDown("slow");
		} else {
			layer_msg("分享功能只能在微信端使用");
		}  
};
</script>
</html>