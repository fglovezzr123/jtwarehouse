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
		 .msgBox .msg-r .imgBox_1 img{
	       width: 100%;
	       }
      .newsContent img{
       width:100%;
       height:auto;
      }
		</style>
	</head>

	<body>
		<div class="vimg" id="vedios"></div>
			
	</body>
<script type="text/javascript">	
var isOk = false;
$(document).ready(function() {
	zdy_ajax({
		url: "${path}/m/news/testdetail.do",
		data:{
			id:'${id}'
		},
		success: function(data,res){
			if(res.code == 0){
				var oss = res.dataobj.ossurl;
				var obj = res.dataobj.signObj;
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

</script>
</html>