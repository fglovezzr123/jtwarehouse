<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="资讯">
		<title>老板新闻三分钟简介</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/informationDetail.css" />
		<link rel="stylesheet" href="${path}/resource/css/living.css?v=${sversion}" />
		<script type="text/javascript" src="${http}://cnstatic01.e.vhall.com/jssdk/dist/2.3.0/vhallSDK.js"></script>
		<style type="text/css">
* {
	padding: 0;
	margin: 0
}

html, body {
	height: 100%;
}

.title {
	height: auto;
	font-size: .36rem;
	line-height: 0.5rem;
	padding-top: 0.2rem;
	padding-bottom: 0.2rem;
}

.news {
	height: .50rem;
	line-height: .50rem;
}

.newsContent .v p {
	font-size: .16rem;
}

.share{
      width:100%;
      height:0.9rem;   
      line-height:0.9rem;
      position:fixed;
      bottom:0;
      left:0;
      background:white;
      font-size:0.3rem;
      text-align:center;
}
.share div{
      font-size:0.30rem;
      height:100%;
      text-align:center;
      float:left;
}
.share div:nth-child(1){
    width:50%;
    background:url(${path}/resource/img/icons/hollowStar.png) no-repeat center left;
    background-size:0.4rem 0.4rem;
    background-position-x:20%;
}
.share div:nth-child(2){
    color:white;
    background:#1087eb;
    width:50%;
}
#tocollect img {
	height: 0.3rem;
	width: 0.3rem;
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

				</div>
			</div>
			<h2 class="title" id="titlediv"></h2>
			<p class="news" id="newsdiv"></p>
			<div class="newsContent" id="newsContent">	</div>
		<div class='share' id="share">
		<div><div class="active_A" style="width:100%;height:100%;background:white" id="tocollect"></div></div>
		<c:if test="${canlook==1}">
			<div class="active_A"  onclick="openurl('${path}/m/news/bossdetailPage.do?id=${id}')" id="ydspan">立即阅读</div>
		</c:if>
		<c:if test="${canlook==0}">
			<div class="active_A"  onclick="openurl('${path}/m/news/paynewsPage.do?id=${id}')" id="payspan">付费</div>
		</c:if>
		<br class="clear" />
	   </div>	
		 
		   </div>
	</body>
<script type="text/javascript">	
var isOk = false;
var cOrn = false;
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
				cOrn = ${iscollection};
				if (cOrn) {
					$('#tocollect').text('');
					$('#tocollect').append('<img src="${path }/resource/img/icons/gzsuccess.png"><span>取消收藏</span>');
				}else{
					$('#tocollect').text('');
					 $('#tocollect').append('<img src="${path }/resource/img/icons/gz.png"><span>收藏</span>');
				};
				console.log(news.content)
				$("#titlediv").append(news.newsTitle);
				var sf = "";
				if(news.isFree==2){
					sf += '价格：免费';
				}else if(news.isFree==1){
					sf += "价格："+news.charge+'J币';
				}
				$("#newsdiv").append(news.updateTime+'&nbsp;&nbsp;'+sf);
				var obj = res.dataobj.signObj;
				$("#newsContent").append('<p><img src="'+oss+news.imagePath+'" alt="" />简介：<div class="v">'+news.summary+'</div></p>');
				
				//分享设置
				var _title = "资讯详情";
				var _imgUrl = "http://tianjiu.oss-cn-beijing.aliyuncs.com/upload/system/a3a14eac-c89a-4e6a-bccd-99602c3f74ad.png";
				if(news.newsTitle.length > 0){
					_title = news.newsTitle;
				}
				if(news.imagePath.length > 0){
					_imgUrl =_oss_url+news.imagePath;
				}
				var _link = home_path+_path+"/m/news/hotDetailPage.do?id="+news.id;
				wxsharefun(_link,_title,_imgUrl,2,news.summary);
			}
		}
	}); 
});
function openurl(url){
	document.location.href=url;
}

//添加或取消收藏
$('#tocollect').click(function() {
	if (!cOrn) {
		zdy_ajax({
			url : _path + "/mycollection/m/add.do",
			showLoading : false,
			data : {
				'id' : '${id}',
				'type' : 3
			},
			success : function(data, bc) {
				console.log(bc)
				layer.msg("收藏成功");
				cOrn = true;
				$('#tocollect').text('');
				$('#tocollect').append('<img src="${path }/resource/img/icons/gzsuccess.png"><span>取消收藏</span>');
			},
			error : function(data) {
			}
		});
	} else if (cOrn) {
		zdy_ajax({
			url : _path + "/mycollection/m/del.do",
			showLoading : false,
			data : {
				'id' : '${id}',
				'type' : 3
			},
			success : function(data, bc) {
				layer.msg("已取消收藏");
				cOrn = false;
				$('#tocollect').text('');
				$('#tocollect').append('<img src="${path }/resource/img/icons/gz.png"><span>收藏</span>');
			},
			error : function(data) {
			}
		});
	};
});
</script>
</html>