<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="keywords" content="群聊管理" />
<title>群聊管理</title>
<link rel="stylesheet" type="text/css"
	href="${path}/resource/css/manageGroupChat.css?v=${sversion}" />
<script type="text/javascript"
	src="${path}/resource/js/libs/strophe.min.js?v=${sversion}"
	charset="utf-8"></script>
 <style>
            .box{          
		    	width: 100%;
		    	font-size: .28rem;
		    	background: #fff;
		    	display: flex;
		    	justify-content: space-between;
		    	padding-left: 5%;
                padding-right: 5%;
		    	box-sizing: border-box;
		    }
		     .box span{
		     	padding: .15rem 0;
		     }
		     .box i{
		     	padding: .15rem .30rem;
		     	align-items: center;
		     }
		    .uList{
		    	width: 70%;
		    	display: flex;
		    	flex-wrap: wrap;
		   }	
		    .uList li{
		    	padding: .15rem .30rem;
		    }
		    .clicking{
		       background:url(${path}/resource/img/icons/arrow.png) no-repeat center right;
		        background-size:0.36rem 0.36rem
		    }
 			.mc{
 				white-space:nowrap; overflow:hidden; text-overflow:ellipsis;
 			
 			}
 </style>

</head>
<body>

	<div class="wrapper">
		<div class="F-ROW">
			<div class="icons active_A jr" >
				<img src="${path}/resource/img/icons/qunliaoguanli_03.png" />
			</div>
			<div class="icons active_A sr" style="display:none">
				<img src="${path}/resource//img/icons/subtract.png" />
			</div>
		</div>
		<div class="content">
			<div class="content-item bottom-border active_A" style="display:none">
				<div class="con-l">群组号</div>
				<div class="con-r" style="background:none">123456</div>

			</div>
			<div class="content-item bottom-border active_A clear xgqm">
				<div class="con-l">群组名称</div>
				<div class="con-r mc"></div>
			</div>

			<div class="content-item bottom-border active_A clear" style="display:none">
				<div class="con-l">群组描述</div>
				<div class="con-r ms"></div>
			</div>
			<div class="content-item bottom-border active_A clear"
				style="display:none">
				<div class="con-l" style="float:left">群组地区</div>
				<div class="con-r">中国</div>
			</div>
			<div class="content-item active_A clear" style="display:none">
				<div class="con-l">我的昵称</div>
				<div class="con-r"></div>
			</div>
		</div>
		<div class="content clear">
			<div class="content-item bottom-border active_A">
				<div style="float:left">置顶聊天</div>
				<div class="switchBtm zd" yes='0' id='zd'
					url="/im/m/updateGroupIsTop.do"></div>
				<div style="clear:both"></div>
			</div>
			<div class="content-item bottom-border active_A">
				<div style="float:left">消息免打扰</div>
				<div class="switchBtm xx" yes='0' id='mdr'
					url="/im/m/updateGroupMsgDistrub.do"></div>
				<div style="clear:both"></div>
			</div>
			<div class="content-item active_A">
				<div style="float:left">私密群</div>
				<div class="switchBtm sm" yes='0' id='sm'
					url="/im/m/updateGroupIsPrivate.do"></div>
				<div style="clear:both"></div>
			</div>
		</div> 
		<div class="content ">
			<div class="content-item active_A bottom-border" style="display:none">
				<div>查找聊天内容</div>
			</div>
			<div class="content-item active_A kfly">
				<div>留言</div>
			</div>
		</div>
		 <div class="box bottom-border">
			     <span>群标签</span>
			<ul class="uList">
			  
		     </ul>
		       <i class="clicking"></i>
		   </div>
		<div class="clearRecord active_A">清空聊天记录</div>

		<div class="deleteAndQuit active_A">删除并退出</div>
	</div>
</body>
<script type="text/javascript">       
	   var userId = '<%=userid%>';
	var qid = window.location.href.split('=')[1]
	// XMPP服务器BOSH地址
	var BOSH_SERVICE = _im_path_http+':5280/http-bind/';
	// XMPP连接
	var connection = null;
	// 当前状态是否连接
	var connected = false;
	var jid=userId+"@"+_im_path;
	// 当前登录的JID
	// 连接状态改变的事件
	/* connection = new Strophe.Connection(BOSH_SERVICE);
	connection.connect(userId + "@"+_im_path, null, onConnect) */
	joinxmpp1();
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
			keepOling()
			connection.send($pres().tree());
		}
	}
	// 接收到<message>
	function onMessage(msg) {
		return true;
	}
	function keepOling() {
		//console.log("online");
		connection.sendIQ($iq({
			to : _im_path,
			from : userId + "@"+_im_path,
			type : "get"
		}).c('ping', {
			xmlns : "urn:xmpp:ping"
		}));
		setTimeout(keepOling, 22000);
	}

	function aaab(iq) {
		layer.msg("聊天记录已清空")
	}
	function del() {
		console.log("删除");
		connection.sendIQ($iq({
			to : _im_path,
			id : new Date(),
			type : "set"
		}).c('chat-history', {
			xmlns : 'urn:xmpp:chat-history'
		}).c('user', {
			'peer' : qid + "@group."+_im_path,
			'action' : 'delete'
		}), aaab)

	}
    var k1 = false
	zdy_ajax({
		url : _path + "/im/m/selGroupInfoById.do",
		showLoading : false,
		data : {
			groupId : qid
		},
		success : function(data, res) {
			console.log(res)
			if (res.code == 0) {
				var main_user = data.groupInfo.mainUser;
				if (main_user == userId) {
					k1 = true;
					$('.sr').css({
						"display" : "block"
					})
					 var url1 = "/im/m/deleteGroup.do"
					 usermain(url1)
					 //群主修改群名
					   $('.xgqm').on('click',function(){
	        	             window.location.href = _path + "/wx/businessFriend/xgqm.do?qid=" + qid;
	                  })
	                  //群主修改群标签
	                   $(".uList").on('click', function() {
				          window.location.href = _path + "/wx/businessFriend/GroupLabel.do?qid1=" + qid;
	                    })
				} else {
					k1 = false
					$('.sr').css({
						"display" : "none"
					})
					var url1 = "/im/m/updateBackGroup.do"
					usermain(url1)
				}
				var groupType = data.groupInfo.groupType;
				if (groupType == 1) {
					$("#sm").addClass('switchBtm').removeClass('on')
				} else {
					$('#sm').addClass('on').removeClass('switchBtm')
				}
				var groupName = data.groupInfo.groupName;
				var groupDesc = data.groupInfo.groupDesc;
				$(".mc").html(groupName)
				var arrUserId = [];
				$.each(data.users,function(index) {
					arrUserId.push(data.users[index].user_id);
					console.log(1)
					var str1 = '<div class="icons">'+
						 '<img src="'+data.users[index].head_portrait+'" onclick="friendInfo('+data.users[index].user_id+')" />'+ 
						 '<i> '+ data.users[index].nickname+'</i>'+
						 '</div>'
					 if(main_user==data.users[index].user_id){
						$('.F-ROW').prepend(str1)
					 }else{
						$('.jr').before(str1)
					} 
					if (data.users[index].user_id == userId) {
						if (data.users[index].msg_disturb == 0) {
							$("#mdr").addClass('switchBtm')
									.removeClass('on')
						} else {
							$("#mdr").addClass('on')
									.removeClass('switchBtm')
						}
						 if (data.users[index].top_flag == 0) {
							$("#zd").addClass('switchBtm').removeClass('on')
						} else {
							$("#zd").addClass('on').removeClass('switchBtm')
						} 
					}
				})
			}
			yaoUser(arrUserId)
			var qbqId = [];
			$.each(data.favs,function(index) {
				qbqId.push(data.favs[index].fav_id)
				var str = '<li>'+data.favs[index].list_value+'</li>'
				$('.uList').append(str)
            })
            setSessionStorageValue('xgqbq',qbqId)
		},
		error : function(e) {
			//alert(e);
		}
	});
    //修改群标签
            var label = getSessionStorageValue("label")
			var ps = window.location.href.split("=")[1]
			if (ps==999) {
				if (label) {
					$.each(label, function(i) {
						var str = '<li>' + label[i].label + '</li>'
						$('.uList').append(str)
					})
				}
			}
	$('.switchBtm').on('click', function() {
		if($(this).hasClass('sm')){
			if(k1){
				if ($(this).hasClass('on')) {
					$(this).addClass('switchBtm').removeClass('on')
					$(this).attr('yes', 0)
					var yes = $(this).attr('yes')
			        var url = $(this).attr('url')
				} else {
					$(this).addClass('on').removeClass('switchBtm')
					$(this).attr('yes', 1)
					var yes = $(this).attr('yes')
			       var url = $(this).attr('url')
				}
				yes++
				qxs(url, yes)
			}else{
				layer.msg("你不是群主，不能设置")
			}
		}else{
			if ($(this).hasClass('on')) {
				$(this).addClass('switchBtm').removeClass('on')
				$(this).attr('yes', 0)
			} else {
				$(this).addClass('on').removeClass('switchBtm')
				$(this).attr('yes', 1)
			}
			var yes = $(this).attr('yes')
			var url = $(this).attr('url')
			qxs(url, yes)
		}
	})
	function qxs(url, yes) {
		console.log(yes)
		console.log(url)
		zdy_ajax({
			url : _path + url,
			showLoading : false,
			data : {
				groupId : qid,
				status : yes
			},
			success : function(data, res) {
				if (res.code == 0) {
					if('/im/m/updateGroupIsTop.do'==url){
						sessionStorage.removeItem("mag_data"); //清除置顶储存
					}
					layer.msg(res.msg)
				}
			},
			error : function(e) {
				//alert(e);
			}
		});
	}
	//邀请好友入群
	function yaoUser(arrUserId){
		$(".jr").on('click',function() {
			 setSessionStorageValue('jr',arrUserId)
			window.location.href = _path+ "/wx/businessFriend/myFriend.do?qid1=" + qid
		})
	}
	
	//群主删人
	$(".sr").on('click',function() {
				window.location.href = _path+ "/wx/businessFriend/shanren.do?qid2=" + qid
			})
	//客服留言
	$(".kfly").on('click', function() {
		window.location.href = _path + "/m/my/leaveMsgPage.do"
	})
	//清空聊天记录
	$(".clearRecord").on('click', function() {
		del();
	})
	function usermain(url1) {
		//console.log(url1)
		$(".deleteAndQuit").on('click',function() {
			 del();
					zdy_ajax({
						url : _path + url1,
						showLoading : false,
						data : {
							groupId : qid
						},
						success : function(data, res) {
							if (res.code == 0) {
								/* layer.msg(res.msg) */
								window.location.href = _path + "/wx/businessFriend/myTalk.do?"
							}

						},
						error : function(e) {
							//alert(e);
						}
					});
				})
	}
	
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
			             //console.log('RID=' + RID + ' [SID=' + SID + ']');
                     
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
</html>