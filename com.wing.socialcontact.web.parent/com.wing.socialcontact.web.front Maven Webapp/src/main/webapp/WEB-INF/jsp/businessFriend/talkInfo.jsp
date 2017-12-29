<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">聊天管理</title>   
        <link rel="stylesheet" href="${path}/resource/css/manageGroupChat.css?v=${sversion}" />
        <script type="text/javascript" src="${path}/resource/js/libs/strophe.min.js?v=${sversion}" charset="utf-8"></script>
    </head>
    <body>
        <div class="wrapper"> 
             <div class="F-ROW">
                 <div class="icons">
                     <img src="" class ="header" >
                      <i></i>
                 </div>
                  <div class="icons active_A jq">
                     <img src="${path}/resource/img/icons/qunliaoguanli_03.png"/>     
                 </div>
                 <div style="clear:both"></div>
             </div>
             <div class="content">
                 <div class="content-item active_A" style="border-bottom:1px solid #efeef4">
                     <div style="float:left">置顶聊天</div>
                     <div class="switchBtm" yes = '0' id = 'zd' ></div>
                     <div style="clear:both"></div>
                 </div>
                 <!-- <div class="content-item active_A">
                     <div style="float:left">消息免打扰</div>
                     <div class="switchBtm" yes = '0' id = 'mdr' ></div>
                     <div style="clear:both"></div>
                 </div> -->
             </div>
             <div class="content">
                 <div class="content-item active_A" style="border-bottom:1px solid #efeef4;display:none" >
                     <div>查找聊天内容</div>
                 </div>     
                 <div class="active_A content-item kfly">
                     <div>留言</div>
                 </div>
             </div>
             <!-- <div class="clearRecord active_A">清空聊天记录</div> -->
        </div>
		<script type="text/javascript">
		 var tjyUserId = window.location.href.split("=")[1];
		 var userId = ${sessionScope.me.id };
    	// XMPP服务器BOSH地址
 		var BOSH_SERVICE = _im_path_http+':5280/http-bind/';
 		// XMPP连接
 		var jid=userId+"@"+_im_path;
 		var connection = null;
 		 // 当前状态是否连接
 		var connected = false;
 		// 连接状态改变的事件
 	/* 	connection = new Strophe.Connection(BOSH_SERVICE);
		connection.connect(userId+"@"+_im_path, null, onConnect) */
		/* joinxmpp1(); */
 		function onConnect(status) {
 			if (status == Strophe.Status.CONNFAIL) {
 				//console.log("连接失败！");
 			} else if (status == Strophe.Status.AUTHFAIL) {
 				//console.log("登录失败！");
 			} else if (status == Strophe.Status.DISCONNECTED) {
 				//console.log("连接断开！");
 				connected = false;
 			} else if (status == Strophe.Status.CONNECTED) {
 				console.log("连接成功，可以开始聊天了！");
 				connected = true;
 				// 当接收到<message>节，调用onMessage回调函数
 				connection.addHandler(onMessage, null, 'message', null, null, null);
 				//keepOling()
 				//connection.send($pres().tree());
 			}
 		}
 			// 接收到<message>
 		function onMessage(msg) {
 			console.log(msg);	
 			return true;
 		}
 		function keepOling() {
 			console.log("online");
 			connection.sendIQ($iq({
 				to: _im_path,
 				from:userId+"@"+_im_path,
 				type: "get"
 			}).c('ping', {
 				xmlns: "urn:xmpp:ping"
 			}));
 			setTimeout(keepOling, 22000);
 		}	
 		function aaab(iq) {
 			console.log("eee")
 			console.log(iq)
 		}
 		function del() {
 			//console.log("删除");
 			connection.sendIQ($iq({
 				to: _im_path,
 				id:new Date(),
 				type: "set"
 			}).c('chat-history', {
 				xmlns: 'urn:xmpp:chat-history'
 			}).c('user', {
 				'peer': tjyUserId+"@"+_im_path,
 				'action':'delete'
 			}), aaab)
 		}
		zdy_ajax({
			url : _path+ "/im/m/selMyFriendUserInfo.do",
			showLoading : false,
			data : {
				friendUserId : tjyUserId
			},
			success : function(data, res) {
				//console.log(res);
				var is_top = data.is_top
				var msg_disturb = data.msg_disturb
				if(is_top == 0){
					$("#zd").addClass('switchBtm').removeClass('on')
				}else{
					$('#zd').addClass('on').removeClass('switchBtm')
				}
				if(msg_disturb == 0){
					$("#mdr").addClass('switchBtm').removeClass('on')
				}else{
					$('#mdr').addClass('on').removeClass('switchBtm')
				}
				$(".header").attr("src",data.head_portrait)
				$(".F-ROW i").html(data.nickname)
			},
			error : function(e) {
				//alert(e);	
			}
		});
		$('.jq').on('click',function(){
			setSessionStorageValue('InviteFriends',{follow_user:tjyUserId})
			window.location.href = _path+"/wx/businessFriend/szq.do?"
		})
		$('#zd').on('click',function(){
   		 if($(this).hasClass('on')){
   			  $(this).attr('yes',0)
   		 }else{
   			 $(this).attr('yes',1)
   		 }
   	     var yes = $(this).attr('yes')
   	     qxs(yes)
   	   })
   	   $('#mdr').on('click',function(){
   		 if($(this).hasClass('on')){
   			 $(this).attr('yes',0)
   		 }else{
   			 $(this).attr('yes',1)
   		 }
   		 var yes = $(this).attr('yes')
   		 mdr(yes)
   	   })
   	   function mdr(yes){
			zdy_ajax({
				url: _path+'/im/m/updateFriendUserDisturb.do',
			    showLoading:false,
			    data:{
			    	friendUserId:tjyUserId,
			    	distrubFlag:yes
			    },
				success: function(data,res){
					/* console.log(yes) */
					if(res.code == 0){
						if(yes==0){
							$('#mdr').addClass('switchBtm').removeClass('on')
							layer.msg(res.msg)
						}
						if(yes==1){
							$('#mdr').addClass('on').removeClass('switchBtm')
							layer.msg(res.msg)
						}				
					}	 
				},
			    error:function(e){
				   //alert(e);
			    }
			});
		}
   	 function qxs(yes){
   		 /* console.log(yes) */
   		 zdy_ajax({
					url: _path+'/im/m/updateFriendUserIsTop.do',
				    showLoading:false,
				    data:{
				    	friendUserId:tjyUserId,
				    	status:yes
				    },
					success: function(data,res){
						if(res.code == 0){
							//sessionStorage.removeItem("mag_data"); //清除置顶储存
							if(yes==0){
								$('#zd').addClass('switchBtm').removeClass('on')
								layer.msg("取消置顶");
								getTopgroupuser();
							}
							if(yes==1){
								$('#zd').addClass('on').removeClass('switchBtm')
								layer.msg(res.msg);
								getTopgroupuser();
							}							
						}	 
					},
				    error:function(e){
					   //alert(e);
				    }
				});
   	 }
   	 $('.kfly').on("click",function(){
   		window.location.href = _path+ "/m/my/leaveMsgPage.do"
   	 })
		 //清空聊天记录
   	 $(".clearRecord").on('click',function(){
   		 del();
   	 })
   	 //置顶好友列表
	   function getTopgroupuser(){	
		zdy_ajax({
			url: _path+"/im/m/selTopFriendAndTopGroupListByUserId.do",
		    showLoading:false,
		    async: false,
		    data:{
		    	'pageNum':1,
		    	'pageSize':100,
		    },
			success: function(data,res){
				//console.log("2");
				//console.log(data);
				setSessionStorageValue('mag_data',data)
				//_data(data)
				
				/* console.log(message_top_data) */
			},
		    error:function(e){
			   //alert(e);
		    }
		});	
		};
   	 //新加连接
			function joinxmpp1(){
				 var cookieRID=sessionStorage.getItem('Rid');
				 var cookieSID=sessionStorage.getItem('Sid');
				 connection = new Strophe.Connection(BOSH_SERVICE);
				 if(!cookieRID &&  !cookieSID){
					 console.log(cookieRID+'   '+cookieSID);
					 connection.connect(jid, null, onConnect);
					 connection.xmlOutput = function (e) {
					             var RID = $(e).attr('rid');
					             var SID = $(e).attr('sid');
					             sessionStorage.setItem('Rid',RID);
					             sessionStorage.setItem('Sid',SID);
					            // console.log('RID=' + RID + ' [SID=' + SID + ']');
		                     
					    };
				   }else if(cookieRID && cookieSID){   
					 connection.attach(jid,cookieSID,parseInt(cookieRID)+1,attachingCallbackXX);
				 };
			 }
			
			function attachingCallbackXX(e){
				 console.log(e);
				 if(e == Strophe.Status.ATTACHED){ 
					    connected = true;
					    connection.addHandler(onMessage, null, 'message', null, null, null);
						connection.xmlOutput = function (e) {
					             var RID = $(e).attr('rid');
					             var SID = $(e).attr('sid');
					             sessionStorage.setItem('Rid',RID);
					             sessionStorage.setItem('Sid',SID);
					            // console.log('RID=' + RID + ' [SID=' + SID + ']');     
					    };   
				 }else{
					 //断开重新连接
					 console.log('连接断开');
					 connected = false;
					 sessionStorage.removeItem('Rid');
			         sessionStorage.removeItem('Sid');
			         joinxmpp1();
				 }
				 
			}
		</script>
	</body>

</html>
