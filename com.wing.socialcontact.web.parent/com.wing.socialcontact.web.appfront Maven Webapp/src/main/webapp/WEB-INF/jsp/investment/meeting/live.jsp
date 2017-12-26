<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.wing.socialcontact.util.*" %>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title"></title>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?v=${sversion}"/>
<link rel="stylesheet" href="${path}/resource/css/living.css?v=${sversion}" />
<script src="${path}/resource/js/libs/zepto.min.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="${http}://cnstatic01.e.vhall.com/jssdk/dist/2.3.2/vhallSDK.js"></script>
<style>
</style>
</head>
<body>
<div class="T-index" style="background: #f2f3f4;width: 100%;height: 100%;">
	
	<div class="vimg" id="vedios" style="height:3.6rem">
	</div>
	<div class="tit">
		<h3 class="h-activ" index="1">聊天</h3>
		<h3 index="2">详情</h3>
	</div>
	<div class="t-cont" id="chatMsg" index="1" pageNum="0" pages="1" style="height:calc(100% - 4.6rem);overflow: scroll;">
	</div>
	<div class="t-cont" style="display:none;" index="2">
		<div class="msgBox" style="padding-top:0px;">
			<div class="msg-r" style="font-size: .28rem;text-indent: 2em;margin: auto;">
				${obj.contents}
			</div>
		</div>
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
	$(".tit h3").click(function(){
		$(".tit h3").removeClass("h-activ");
		$(this).addClass("h-activ");
		var index = $(this).attr("index");
		$(".t-cont").hide();
		$(".t-cont[index='"+index+"']").show();
	});
	
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
		VHALL_SDK.on('ready', onReadyEvent);
	  	VHALL_SDK.on('error', onErrorEvent); 
	  	VHALL_SDK.on("playerReady", onPlayerReadyEvent);
	  	VHALL_SDK.on('publishStart', onPublishStartEvent);
	  	VHALL_SDK.on('streamOver', onStreamOverEvent);
	  	VHALL_SDK.on('sendChat', onSendChatEvent);
	  	VHALL_SDK.on('vhall_record_history_chat_msg', onHisMsgEvent);
	  	VHALL_SDK.on('vhall_live_history_chat_msg', onLiveMsgEvent);
	}
});
function onReadyEvent(){
	if (VHALL_SDK.getRoominfo().type == 1) {
		 $("title").html("直播"); 
          //当前直播中
          VHALL_SDK.vhall_get_live_history_chat_msg();
      } else {
    	  $("title").html("回放"); 
          //当前不在直播
          var pageNum = parseInt($("#chatMsg").attr("pageNum")||0)+1;
          var pages = parseInt($("#chatMsg").attr("pageNum")||0)+1;
          if(pageNum<=pages){
	          VHALL_SDK.vhall_get_record_history_chat_msg(pageNum);
          }
      }
}
function onErrorEvent(msg){
	//alert("error")
}
function onPlayerReadyEvent(msg){
}
function onPublishStartEvent(msg){
	//alert("PublishStart")
}
function onStreamOverEvent(msg){
	
}
function onSendChatEvent(msg){
	//alert(JSON.stringify(msg))
	if(msg.code!=200){
		layer.msg('发送信息失败'); 
	}
}
function onLiveMsgEvent(msg){
	if(msg.code!=200){
		setTimeout(function(){
			onReadyEvent();
		});
	}else{
		var arr = msg.data;
		for(var i in arr){
			appendChatMsg(arr[i]);
		}
		setTimeout(function(){
			onReadyEvent();
		},200);
	}
}
function onHisMsgEvent(msg){
	//VHALL_SDK.sendChat({text:'聊天测试'});
	if(msg.code!=200){
		setTimeout(function(){
			onReadyEvent();
		});
	}else{
		$("#chatMsg").attr("pageNum",msg.curr_page);
		$("#chatMsg").attr("pages",msg.total_page);
		var arr = msg.data;
		for(var i in arr){
			appendChatMsg(arr[i]);
		}
		setTimeout(function(){
			onReadyEvent();
		},1000);
	}
}
function appendChatMsg(msg){
	var htmlStr = 
		'<div class="msgBox">'+
			'<div class="msg-l" style="background-image: url(\''+msg["avatar"]+'\');"></div>'+
			'<div class="msg-r">'+
				'<div class="t">'+
					'<h2>'+msg["user_name"]+'</h2>'+
					'<span class="time">'+msg["time"]+'</span>'+
				'</div>'+
				'<h3>'+msg["content"]+'</h3>'+
			'</div>'+
		'</div>';
	$("#chatMsg").append(htmlStr);
}
</script>
</html>