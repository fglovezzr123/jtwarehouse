<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1"/>
		<meta name="keywords" content="邀请好友"/>
		<style type="text/css">
        		body{
        		width:100%;
        		background: #05112b;
        		box-sizing: border-box;
        		padding: 0 1.3rem;
        		padding-top: 1.3rem;
        	}
        	.imgheader{
        		width: 4.9rem;
        	}
        	img{
        		width: 100%;
        		vertical-align: middle;
        	}
        	.tj{
        		width: 100%;
        		padding-bottom: .60rem;
        		font-size: .40rem;
        		color: #fff;
        		box-sizing: border-box;
        	}
        	.zt{
        		width: 100%;
        		display: flex;
        		justify-content: space-between;
        	}
        	.zt .ztl p{
        		font-size: .24rem;
        		line-height: .40rem;
        		width: 2rem;
        		color: #efdfdf;
        	}
        	.zt .box p{
        		margin-top: .20rem;
        		color: #fff;
        		font-size: .26rem;
        		text-align: center;
        	}
        	.zt .wxer{
        		width: 2.10rem;
        		height: 2.1rem;
        	}
        	</style>
		<title>天九共享—邀请好友</title>
	</head>

	<body style="background:#101e41;">
       	<div class="imgheader">
       		<img src="${user_img}" />
       	</div>
       	<div class="tj">${user_name} 推荐</div>
       	<div class="zt">
       		<div class="ztl"  id="listDesc">
       			<p>${yqConfig.listDesc}</p>
       		</div>
       	<div class="box">
       		<div class="wxer">
       			<img src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=${hdticket}"/>
       		  </div>
       			<p>长按二维码关注</p>
       	</div>
       		
       	</div>
	</body>
	<script>
	$(function(){
		var title="天九共享网";
		var _imgUrl = "http://tianjiu.oss-cn-beijing.aliyuncs.com/upload/system/a3a14eac-c89a-4e6a-bccd-99602c3f74ad.png";
		var _link = home_path+_path+"/m/my/fxylshare.do?t=${hdticket}&uid=${uid}";
		//var summary = $("#listDesc").html().replace(/[\r\n]/g, "").replace(/<[^>]+>/g,"").replace(/[ ]/g, "");
		var summary = "海量企业家共聚共享共赢的超O2O商务社交平台，促进百万企业家强强联手，抱团发展";
		console.log(summary);
		wxsharefun(_link,title,_imgUrl,"0",summary);
	})
	
	</script>
</html>
