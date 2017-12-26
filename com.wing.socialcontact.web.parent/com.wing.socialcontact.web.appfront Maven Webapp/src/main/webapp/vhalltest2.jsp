<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ page import="com.wing.socialcontact.util.*" %>
<% 
	String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.getSession().setAttribute("path", path);
	request.getSession().setAttribute("sversion", "0.2");

	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1 
	response.setHeader("Pragma","no-cache"); //HTTP 1.0 
	response.setDateHeader ("Expires", 0);

	java.util.Map<String, Object>  signObj =com.wing.socialcontact.vhall.api.BaseAPI.createVedioSign("6300", "小梁", "404064228");
	request.setAttribute("signObj", signObj);
%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title"></title>
<link rel="stylesheet" href="${path}/resource/css/main.css?v=${sversion}">
<link rel="stylesheet" href="${path}/resource/css/libs/public.css?v=${sversion}">

<script type="text/javascript" src="${path}/resource/js/jquery/jquery-1.10.2.min.js?v=${sversion}"></script>
<script type="text/javascript" src="${path}/resource/js/layer-v2.1/layer/layer.js?v=${sversion}"></script>
<script src="${path}/resource/js/login.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="${path}/resource/js/libs/strophe.min.js?v=${sversion}" charset="utf-8"></script> <script type="text/javascript" src="${path}/resource/js/libs/xmpp.mam.js?v=${sversion}" charset="utf-8"></script>
<script type="text/javascript" src="${path}/resource/js/jquery.qqFace.js?v=${sversion}" charset="utf-8"></script>
<script src="${path}/resource/js/libs/public.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resource/js/libs/dropload.min.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?v=${sversion}"/>
<link rel="stylesheet" href="${path}/resource/css/living.css?v=${sversion}" />
<script src="${path}/resource/js/libs/zepto.min.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
<!--script type="text/javascript" src="${path}/resource/js/vhallSDK.js"></script-->
<script type="text/javascript" src="${http}://cnstatic01.e.vhall.com/jssdk/dist/2.3.2/vhallSDK.js"></script>
<style>
</style>
</head>
<body>
<div class="T-index" style="background: #f2f3f4;width: 100%;height: 100%;">
	<div class="vimg" id="vedios">
	</div>
	<div class="tit">
		<h3 class="h-activ" index="1">聊天</h3>
		<h3 index="2">详情</h3>
	</div>
	<div class="t-cont" index="1">
	</div>
	<div class="t-cont" style="display:none;" index="2">
		<div class="msgBox" style="padding-top:0px;">
			<div class="msg-r" style="font-size: .28rem;text-indent: 2em;margin: auto;">
				呼吁
			</div>
		</div>
	</div>
	<div class="t-cont" style="height:200px;border: 1px solid red;font-size: .28rem;overflow: hidden;" id="debug">
	
	</div>
</div>
<script>
var isOk = false;
var obj = {};
</script>
<c:if test="${not empty  signObj}">
<script>
isOk = true;
obj.account = "${signObj.account}";
obj.email = "${signObj.email}";
obj.username = "${signObj.username}";
obj.roomid = "${signObj.roomid}";
obj.app_key = "${signObj.app_key}";
obj.signedat = "${signObj.signedat}";
obj.sign = "${signObj.sign}";
</script>
</c:if>
</body>
<script>
$(function(){
   if(!isOk){
  	return;
   }
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
	VHALL_SDK.on('ready', onReadyEvent);
  	VHALL_SDK.on('error', onErrorEvent); 
  	VHALL_SDK.on("playerReady", onPlayerReadyEvent);
  	VHALL_SDK.on('publishStart', onPublishStartEvent);
  	VHALL_SDK.on('streamOver', onStreamOverEvent);
  	VHALL_SDK.on('vhall_record_history_chat_msg', onHisMsgEvent);
});

function onReadyEvent(){
	if (VHALL_SDK.getRoominfo().type == 1) {
          //当前直播中
          VHALL_SDK.vhall_get_live_history_chat_msg();
          VHALL_SDK.vhall_get_live_history_question_msg();
      } else {
          //当前不在直播
          VHALL_SDK.vhall_get_record_history_chat_msg(1);
      }
}
function onErrorEvent(msg){
	//alert("error")
}
function onPlayerReadyEvent(msg){
	//alert("PlayerReady")
	VHALL_SDK.player.on('canPlayLines', function(msg) {
        var _src = '';
        //  i 的值为之后调用方法VHALL_SDK.player.setPlayerLine的传参
        for (var i in msg) {
            _src += '<li>' + i + '</li>';
        }
        log(_src);
        //VHALL_SDK.player.setPlayerLine("线路1");
    });
}
function onPublishStartEvent(msg){
	//alert("PublishStart")
}
function onStreamOverEvent(msg){
	
}
function onHisMsgEvent(msg){
	if(msg.code==200){
		var arr = msg.data;
		for(var i in arr){
			var htmlStr = 
			'<div class="msgBox">'+
				'<div class="msg-l" style="background-image: url(\''+arr[i].avatar+'\');"></div>'+
				'<div class="msg-r">'+
					'<div class="t">'+
						'<h2>'+arr[i].user_name+'</h2>'+
						'<span class="time">'+arr[i].time+'</span>'+
					'</div>'+
					'<h3>'+arr[i].content+'</h3>'+
				'</div>'+
			'</div>';
			$(".t-cont[index=1]").append(htmlStr);
		}
	}
}
function log(o){
	$("#debug").empty();
	$("#debug").text(JSON.stringify(o)+"")
}
</script>
</html>