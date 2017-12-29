<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_upload.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<meta name="keywords" content="创建群聊">
		<title>创建群聊</title>
		
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/myTalk.css?v=${sversion}"/>
		<script type="text/javascript" src="${path}/resource/js/libs/strophe.min.js?v=${sversion}" charset="utf-8"></script>
	<style type="text/css">
			.c-footer{
				position: fixed;
				bottom: 0;
				left: 0;
				width: 100%;
				height: 1rem;
				text-align: center;
				font-size: .30rem;
				color: #fff;
				background: #F07800;
				line-height: 1rem;
			}
		</style>	
	</head>

	<body>
		<div class="T-myTalk" style="background: #f2f3f4;width: 100%; height: 100%;">
			<div class="wrapper" id="wrapper">
				<div class="scrollbar" id="scrollbar">
				   <div class="myTalk active_A">
				   		<div class="myTalk-l">
				   			<span class="iconsssss"></span>
				   			<span>来海拔</span>
				   		</div>
				   		<span class="time">12:33</span>
				   </div>
				</div>
			</div>
			<div class="c-footer active_A" id='createGroup'>
				完成
			</div>
		</div>
		<script type="text/javascript">
			<%-- function getNowFormatDate() {
				var date = new Date();
				var seperator1 = "-";
				var seperator2 = ":";
				var month = date.getMonth() + 1;
				var strDate = date.getDate();
				if (month >= 1 && month <= 9) {
					month = "0" + month;
				}
				if (strDate >= 0 && strDate <= 9) {
					strDate = "0" + strDate;
				}
				var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate + " " + date.getHours() + seperator2 + date.getMinutes() + seperator2 + date.getSeconds();
				return currentdate;}
			var time = getNowFormatDate();
			$('.time').html(time);
		     var jid='<%=userid   %>@'+_im_path;
			 var BOSH_SERVICE = _im_path_http+':5280/http-bind/';
             
			// XMPP连接
			var connection = null;

			// 当前状态是否连接
			var connected = false;
			// 连接状态改变的事件
			function onConnect(status) {
				if (status == Strophe.Status.CONNFAIL) {
					console.log("连接失败！");
				} else if (status == Strophe.Status.AUTHFAIL) {
					console.log("登录失败！");
				} else if (status == Strophe.Status.DISCONNECTED) {
					console.log("连接断开！");
					connected = false;
				} else if (status == Strophe.Status.CONNECTED) {
					console.log("连接成功，可以开始聊天了！");
					connected = true;
					keepOling();

				}
			}
		
			// 通过BOSH连接XMPP服务器
		
				if (!connected) {
					connection = new Strophe.Connection(BOSH_SERVICE);
					connection.connect(jid, null, onConnect);
					jid = jid;

				};

			// 发送消息
			
			$('#createGroup').click(function() {
				console.log('ssss')
				var b = $iq({
					from: jid,
					type: "set",
					to: 'group.'+_im_path
				}).c('group', {
					xmlns: "http://www.ucfgroup.com/protocol/group"
				}).c('create', {
					group_name:'杨路路1144444yyy'
				})

				connection.sendIQ(b, getBound);
			})

			function getBound(iq) {
				console.log(iq);
				var group_id = $(iq).find('create').attr('group_id')
				console.log(group_id)
				 window.location.href="${path}/wx/businessFriend/talkAboutQl.do?id="+group_id

			}
			function keepOling() {
				console.log("online");
				connection.sendIQ($iq({
					to: _im_path,
					from: jid,
					type: "get"
				}).c('ping', {
					xmlns: "urn:xmpp:ping"
				}));
				setTimeout(keepOling, 22000);

			}

			function getRoomate(iq) {
				console.log(iq);
				$(iq).find("item").each(function() {
					console.log($(this).attr("name"));

				});

			}
 --%>
			 /* $('#createGroup').click(function() {
				 zdy_ajax({
						url: _path+"/im/m/insertMyGroupInfo.do",
					    showLoading:false,
					    data:{
					    	"groupName":"楊路路eeyanglulu12",
					    	"groupDesc":"介绍群",
					    	"groupType":1,
					    	"userIds":'1000,1012',
					    	"isTop":"1",
					    	"disturb":"1",
					    	"headPortrait":"http://t1.mmonly.cc/uploads/tu/201607/175/mbdtc1zyh2s.jpg"
					    },
					    success: function(data,res){
							console.log(data)
						},
					    error:function(e){
						   //alert(e);
					    }
					});
				}) */
 			
		</script>
	</body>

</html>
