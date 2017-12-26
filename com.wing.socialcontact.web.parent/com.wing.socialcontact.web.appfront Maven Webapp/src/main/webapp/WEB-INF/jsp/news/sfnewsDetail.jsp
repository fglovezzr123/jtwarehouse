<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>

<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="资讯">
		<title>老板新闻三分钟详情</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/informationDetail.css" />
		<link rel="stylesheet" href="${path}/resource/css/living.css?v=${sversion}" />
		
		<script type="text/javascript" src="${http}://cnstatic01.e.vhall.com/jssdk/dist/2.3.0/vhallSDK.js"></script>
		<style type="text/css">
		*{padding:0;margin:0}
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
		.newsContent .v p{
           font-size: .16rem;   
         }
         .msgBox .msg-r .imgBox_1 img{
	         width: 100%;
	       }
		</style>
	</head>

	<body style="background: #f2f3f4;width: 100%; height:100%;">
			<div class="wrapper" id="wrapper" style="padding-bottom:1rem">
				<h2 class="title" id="titlediv"></h2>
			<p class="news" id="newsdiv"></p>
			<div class="newsContent" id="newsContent">	</div>
			<div class='share' id="share">
		     <div><div class="active_A" style="width:100%;height:100%;background:white" id="tocollect"></div></div>
			<div class="active_A"  onclick="fx();">分享</div>
		     <br class="clear" />
	      </div>
		 <div class="com-backdrop" style="display: none;"></div>
	     <div class="tips-s-img" style="display: none;"></div>
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
				//分享设置
				var _title = "资讯详情";
				var _imgUrl = "http://tianjiu.oss-cn-beijing.aliyuncs.com/upload/system/a3a14eac-c89a-4e6a-bccd-99602c3f74ad.png";
				if(news.newsTitle.length > 0){
					_title = news.newsTitle;
				}
				if(news.imagePath.length > 0){
					_imgUrl =_oss_url+news.imagePath;
				}
				var _link = home_path+_path+"/m/news/summaryPage.do?id="+news.id;
				wxsharefun(_link,_title,_imgUrl,2,news.summary);
				
				var sf = "";
				if(news.charge==0||news.charge==null){
					sf += '免费';
				}else{
					sf += "收费："+news.charge+'J币';
				}
				$("#newsdiv").append(news.updateTime+'&nbsp;&nbsp;'+sf);
				var obj = res.dataobj.signObj;
				$("#newsContent").append('<p><img src="'+oss+news.imagePath+'" alt="" /><div class="vimg" id="vedios"></div><div class="v">'+news.content+'</div></p>');
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
$(function() {
	//绑定遮罩层关闭事件
	$(".com-backdrop").click(function() {
		var obj = $(this);
		$(".tips-s-img").slideUp("slow", function() {
			obj.hide();
		});
	});
	$(".tips-s-img").click(function() {
		$(".com-backdrop").click();
	});
});
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