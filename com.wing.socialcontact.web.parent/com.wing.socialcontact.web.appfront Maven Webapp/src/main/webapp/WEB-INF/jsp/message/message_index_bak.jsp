<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">消息</title>

<link rel="stylesheet" href="${path}/resource/css/messages.css?v=${sversion}" />
<script type="text/javascript" src="${path}/resource/js/libs/strophe.min.js?v=${sversion}" charset="utf-8"></script>
<script type="text/javascript" src="${path}/resource/js/jquery.qqFace.js?v=${sversion}" charset="utf-8"></script>
   <style>
          .layui-layer-loading .layui-layer-content{
	      width:100%
	 }
         .imgGreet{
         	margin-top:.16rem;
         }
         .emotioneee{
          width:.26rem!important;
          height:.26rem!important;
         }
         .talkList{
         margin-bottom:.90rem;
         }
         .content-item .con-ll{
            float:none;
            height:1.22rem;
         }
         .content .content-item{
            line-height:1.22rem;
         }
         .sysMes ul li:nth-child(1){
              margin-top:0rem;
         }
   </style>
</head>
<body>
	<div class="wrapper">
		<div class="content">
			<div class="content-item bottom-border"  onclick="systemMes();">
				<div class="con-l active_A " style="background:none">
					<div style="float:left" class="imgbox">
						<img src="${path}/resource/img/images/systemMes.png" />
					</div>
					<div class="message-jishu" id="sysMessage"  style="height: 1.22rem;top: 0rem; line-height: 1.1rem;">0</div>
					<div class="sysMes ">
						<ul>
							<li style="display:block;height:50%" class="li1">系统信息</li>
							<!-- <li class="sign" style="display:block;height:50%;">
                                                    提醒:恭喜您 ，于12月17日获得300积分！
                                                </li> -->
						</ul>
					</div>
					<div class="iconfont " style="float:right" >&#xe605;</div>
				</div>
				<div class="con-r"></div>
			</div>

			<div class="content-item bottom-border active_A clear" onclick="activityMes();">
				<div class="message-jishu" id="actMessage"  style="height: 1.22rem;top: 0rem; line-height: 1.1rem;">0</div>
				<div class="con-l con-ll " style="background:none">
					<img src="${path}/resource/img/images/activityInfo.png" class="imgbox" /> <span class="dw">活动信息</span>
				    <div class="iconfont " style="float:right" >&#xe605;</div>
				</div>
				<div class="con-r"></div>

			</div>

			<div class="content-item bottom-border active_A clear"
				onclick="greetings();">
				<div class="message-jishu" id="greetings" style="height: 1.22rem;top: 0rem; line-height: 1.1rem;">0</div>
				<div class="con-l con-ll" style="background:none">
					<img class="imgbox" src="${path}/resource/img/images/gesture.png" /> <span class="dw">打招呼</span>
					<div class="iconfont " style="float:right" >&#xe605;</div>
				</div>
				<div class="con-r"></div>

			</div>

			<div class="content-item bottom-border active_A clear" onclick="latestVistors();">
				<div class="message-jishu" id="latestVistors"  style="height: 1.22rem;top: 0rem; line-height: 1.1rem;">0</div>
				<div class="con-l con-ll" style="float:left;background:none">
					<img class="imgbox" src="${path}/resource/img/icons/xiaoxi_10.png" /> 
					<span class="dw">最近访客</span>
					<div class="iconfont " style="float:right" >&#xe605;</div>
				</div>
				

			</div>
		</div>
		<div class="content talkList">
			<div id="topmsg_div"></div>
			<div id="msg_div"></div>
		</div>
		
		<jsp:include page="../commons/include_footer.jsp" >
				<jsp:param name="menuid" value="3" />
			</jsp:include>
		
	</div>
	
	<script type="text/javascript">
		/* var message_data=[{
			"id":"",
			"name":"",
			"headpath":"",
			"lastmsg":"",
			"lasttime":"",
			"isgroup":true,
			"msg_count":0
		}]; */
		//最近聊天记录
		var message_data=[];
		//置顶聊天记录
		var message_top_data=[];
		inittxl();
		var sessionss=getSessionStorageValue("txl_map");
		//console.log(sessionss);
		$(document).ready(function() {
			//系统消息个数
			$.ajax({
				url : "${path}/m/message/systemMessageCount.do",
				type : 'post',
				dataType : "json",
				data : {},
				success : function(res) {
					if (res.code == 0) {
						$("#sysMessage").html(res.dataobj);
					} else {

					}
				}
			});
			
			//活动消息个数
			$.ajax({
				url : "${path}/m/message/activitMessageCount.do",
				type : 'post',
				dataType : "json",
				data : {},
				success : function(res) {
					if (res.code == 0) {
						$("#actMessage").html(res.dataobj);
					} else {

					}
				}
			});
			//打招呼个数
			$.ajax({
				url : "${path}/m/message/toGreetingsCount.do",
				type : 'post',
				dataType : "json",
				data : {},
				success : function(res) {
					if (res.code == 0) {
						$("#greetings").html(res.dataobj);
					} else {

					}
				}
			});
			//最近访客个数
			$.ajax({
				url : "${path}/m/message/latestVistorsCount.do",
				type : 'post',
				dataType : "json",
				data : {},
				success : function(res) {
					if (res.code == 0) {
						$("#latestVistors").html(res.dataobj);
					} else {

					}
				}
			});
		});
		
		//查看打招呼
		var greetings=function(){
			self.location.href="${path}/m/message/user_greets.do";
		}
		//系统消息
		var systemMes=function(){
			self.location.href="${path}/m/message/system_mes.do";
		}
		//活动消息
		var activityMes=function(){
			self.location.href="${path}/m/message/activity_mes.do";
		}
		//最近访客
		var latestVistors=function(){
			self.location.href="${path}/m/message/lasetVistor.do";
		}
		
		//置顶好友列表
		zdy_ajax({
			url: _path+"/im/m/selTopFriendListByUserId.do",
		    showLoading:true,
		    data:{
		    	'pageNum':1,
		    	'pageSize':100,
		    },
			success: function(data,res){
				//console.log(res);
				$.each(data,function(index){
			    	message_top_data.push({
						"id":data[index].relat_user,
						"name":data[index].friend_memo,
						"isgroup":false,
						"headpath":data[index].head_portrait,
						"lastmsg":"最近没有信息",
						"lasttime":"",
						"msg_count":0
					})
				});	
			},
		    error:function(e){
			   //alert(e);
		    }
		});	
		
		//连接im
		var fromUserId = ${sessionScope.me.id };
	    var jid=fromUserId+'@'+_im_path;
		var BOSH_SERVER = _im_path_http+":5280/http-bind/";
		var connection = null;
		connection = new Strophe.Connection(BOSH_SERVER);
		connection.connect(jid, null, onConnect);
		
		
		function instantMessage(msg) {
			//console.log($(msg).attr('from').split('@')[0]); 
			//获取昵称，或者群名称
			// 解析出<message>的from、type属性，以及body子元素
			var from = msg.getAttribute('from');
			var type = msg.getAttribute('type');
			var elems = msg.getElementsByTagName('body');
			var jid = from.split("@")[0];
			if (type == "groupchat" && elems.length > 0) {
				var body = elems[0];
				layer.msg(Strophe.getText(body), {
					  offset: ['20'],
					  anim: 1
					});
			}else if (type == "chat" && elems.length > 0){
				var body = elems[0];
				layer.msg(Strophe.getText(body), {
					  offset: ['20'],
					  anim: 1
					});;
		
			}
			
			//更新未读消息到local
			var msgcount = getLocalStorageValue("msg_notread_count");
			if(!msgcount)msgcount={};
			if(msgcount[jid]){
				msgcount[jid]+=1;
			}else{
				msgcount[jid]=1;
			}
			setLocalStorageValue("msg_notread_count",msgcount);
			
			return true;
		}
		function onConnect(status) {
			console.log(status);
			if(status == Strophe.Status.CONNECTING) {
			} else if(status == Strophe.Status.CONNFAIL) {
				$('#connect').get(0).value = 'connect';
			} else if(status == Strophe.Status.DISCONNECTING) {
				
			} else if(status == Strophe.Status.DISCONNECTED) {
				$('#connect').get(0).value = 'connect';
			} else if(status == Strophe.Status.CONNECTED) {
				console.log('连上了');
				connection.addHandler(onMessage, null, 'message', null, null, null);
				//connection.addHandler(instantMessage, null, 'message', null, null, null);
				connection.sendIQ($iq({
					to: _im_path,
					id:new Date(),
					type: "get"
				}).c('recent-contact', {
					xmlns: 'urn:xmpp:recent-contact'
				}), aaa);
				
				keepOling();	
				}		
			}
		function onMessage(msg) {
			return true;
		};
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
		
		
		
		function aaa(iq) {
		/* 	console.log("回执")
			console.log(iq) */
		   	var data = $(iq).find('item')
		   	$.each(data,function(index){
			    var jid = $(data[index]).attr('jid').split("@")[0]
			    var isGroup = $(data[index]).attr('jid').split("@")[1].indexOf("group")!=-1
			    var timestamp = $(data[index]).attr('timestamp');
			    var msg = $(data[index]).attr('msg');
			    var time = getNowFormatDate(timestamp)
			    //var bj = getNowFormat(timestamp)
			    var msg = clMsg(msg)
			   /*  console.log(msg) */
			   // if(bj){
			    	//time = time.split(" ")[1]
			   // }else{
			    	//time = time.split(" ")[0]
			   // }
			    //console.log('isGroup:'+'  '+isGroup+' msg:'+'  '+msg+' time:'+'  '+time+' 1timestamp:'+'  '+timestamp+' bj:'+'  '+bj);
            
			    updateMessage(isGroup,jid,msg,time);
			    showdata();
		   })
		}
		
		function updateMessage(isGroup,jid,msg,time){
			
			var istopflag = false;
			$.each(message_top_data,function(index){
				var obj = message_top_data[index];
			    if(obj.id==jid){
			    	istopflag = true;
			    	obj.lastmsg=msg;
			    	obj.lasttime=time;
			    }
		    })
			if(!istopflag){
				if(isGroup){
					var g = getGroupInfo(jid);
					//console.log("group:"+jid+JSON.stringify(g));
					var flag = true;
					$.each(message_data,function(index){
					    var obj = message_data[index];
					    if(obj.id==jid){
					    	flag = false;
					    	obj.lastmsg=msg;
					    	obj.lasttime=time;
					    }
				    })
				    if(flag){
				    	message_data.push({
							"id":jid,
							"name":g.name,
							"isgroup":isGroup,
							"headpath":g.headpath,
							"lastmsg":msg,
							"lasttime":time,
							"msg_count":0
						})
				    }
				}else{
					var u = getUserInfo(jid);
					//console.log("user:"+jid+JSON.stringify(u));
					var flag = true;
					$.each(message_data,function(index){
					    var obj = message_data[index];
					    if(obj.id==jid){
					    	flag = false;
					    	obj.lastmsg=msg;
					    	obj.lasttime=time;
					    }
				    })
				    if(flag){
				    	message_data.push({
							"id":jid,
							"name":u.name,
							"isgroup":isGroup,
							"headpath":u.headpath,
							"lastmsg":msg,
							"lasttime":time,
							"msg_count":0
						})
					
				    }
				}
			}
		}
        //获取群信息
		function getGroupInfo(gid){
        	//console.log(gid);
			var txl_map=getSessionStorageValue("txl_map");
			if(!txl_map)
				txl_map = inittxl();
			if(txl_map[gid]){
				return txl_map[gid];
			}else{
				zdy_ajax({
					url: _path+"/im/m/selGroupInfoById.do",
				    showLoading:false,
				    async:false,
				    data:{
				    	groupId:gid	
				    },
					success: function(data,res){
						txl_map[gid]={"id":data.groupInfo.id,"name":data.groupInfo.groupName,"isgroup":true,"headpath":data.groupInfo.headPortrait};
						setSessionStorageValue("txl_map",txl_map);
						return txl_map[gid];
					},
				    error:function(e){
					   
				    }
				});
				return txl_map[gid];
			}
		
		}
        
        //获取用户信息
		function getUserInfo(gid){
			var txl_map=getSessionStorageValue("txl_map");
			if(!txl_map)
				txl_map = inittxl();
			if(txl_map[gid]){
				return txl_map[gid];
			}else{
				  
				zdy_ajax({
					url: _path+"/im/m/selTjyUserInfoByTyjUserid.do",
				    showLoading:false,
				    async:false,
				    data:{
				    	tjyUserId:gid	
				    },
					success: function(data,res){
						txl_map[data.id]={"id":data.id,"name":data.nickname,"isgroup":false,"headpath":data.head_portrait};
						setSessionStorageValue("txl_map",txl_map);
					},
				    error:function(e){
					   
				    }
				});
				return txl_map[gid];
			}
        	
		}
/* 
        //获取群信息
		function getGroupInfo(gid){
			return {"id":gid,"name":"gname","headpath":"http://img4.imgtn.bdimg.com/it/u=3739350442,1564148734&fm=23&gp=0.jpg"}
		}
        
        //获取用户信息
		function getUserInfo(gid){
			return {"id":gid,"name":"uname","headpath":"http://img4.imgtn.bdimg.com/it/u=3739350442,1564148734&fm=23&gp=0.jpg"}
		} */
		
		
		
		
		
		
		
		//
		function showdata(){
			var msgcount = getLocalStorageValue("msg_notread_count");
			var links='';
			message_top_data.sort(sortTime);
			var topstr = '';
			$.each(message_top_data,function(index){
			    var obj = message_top_data[index];
			    var msgcountstr =0;
			    if(msgcount && msgcount[obj.id]){
			    	msgcountstr = msgcount[obj.id];
			    	console.log(typeof msgcount[obj.id]);
			    };
			    
			    topstr += '<div class="goingto content-item bottom-border active_A" id="'+obj.id+'" data-headpath="'+obj.headpath+'" data-id="+obj.id+" data-isgroup="'+obj.isgroup+'">'+
							'		<div class="con-l " style="background:none">'+
							'	<div style="float:left">'+
							'		<img class="portrait imgbox" src="'+obj.headpath+'" data-isgroup="'+obj.isgroup+'" data-headpath="'+obj.headpath+'" data-id="'+obj.id+'"/>'+
							'	</div>'+
							'	<div class="message-jishu">'+msgcountstr+'</div>'+
							'	<div class="sysMes">'+
							'		<ul>'+
							'			<li style="display:block;height:50%;font-size:0.3rem;">'+obj.name+'</li>'+
							'			<li class="sign-text clear" style="display:block;height:50%;width:3.2rem;white-space: nowrap;text-overflow: ellipsis;-o-text-overflow: ellipsis;overflow: hidden;">'+obj.lastmsg+'</li>'+
							'		</ul>'+
							'	</div>'+
							'<div class="iconfont " style="float:right" >&#xe605;</div>'+
							'	<div class="textDate">'+obj.lasttime+'</div>'+
							'</div>'+
							
							'<div style="clear:both"></div>'+
						'</div>';
		    })
			message_data.sort(sortTime);
			$("#topmsg_div").html(topstr);
			var str="";
			$.each(message_data,function(index){
			    var obj = message_data[index];
			    var msgcountstr = 0;
			    if(msgcount && msgcount[obj.id]){
			    	msgcountstr = msgcount[obj.id];
			    	console.log(typeof msgcount[obj.id]);
			    }
			    
			    str +=  '<div class="goingto content-item bottom-border active_A" data-headpath="'+obj.headpath+'" data-id="'+obj.id+'" id="'+obj.id+'" data-isgroup="'+obj.isgroup+'" >'+
							'<div class="con-l " style="background:none">'+
							'	<div style="float:left">'+
							'		<img class="portrait imgbox" src="'+obj.headpath+'" data-id="+obj.id+" data-isgroup="'+obj.isgroup+'" data-headpath="'+obj.headpath+'" />'+
							'	</div>'+
							'	<div class="message-jishu">'+msgcountstr+'</div>'+
							'	<div class="sysMes">'+
							'		<ul>'+
							'			<li style="display:block;height:50%;font-size:0.3rem;">'+obj.name+'</li>'+
							'			<li class="sign-text clear" style="display:block;height:50%;width:3.2rem;white-space: nowrap;text-overflow: ellipsis;-o-text-overflow: ellipsis;overflow: hidden;">'+obj.lastmsg+'</li>'+
							'		</ul>'+
							'	</div>'+
							'<div class="iconfont " style="float:right" >&#xe605;</div>'+
							'	<div class="textDate">'+obj.lasttime+'</div>'+
							'</div>'+
							'<div style="clear:both"></div>'+
						'</div>';
		    })
			$("#msg_div").html(str);
			$('.goingto').bind("click",function(){
				var isgroup = $(this).data("isgroup");
				
				if(isgroup=='true'){
					/* console.log(isgroup);
		    		var objId = new Object()
		    		objId.follow_user = $(this).data("id");
					objId.head_portrait = $(this).data("headpath");
					var objStr = JSON.stringify(objId);
					sessionStorage.setItem('user_info',objStr); */
					window.location.href = "${path}/wx/businessFriend/talkAboutQl.do?qid="+$(this).attr("id"); 
				}else {
		    		var objId = new Object()
		    		objId.follow_user = $(this).data("id");
					objId.head_portrait = $(this).data("headpath");
					console.log(objId);
					var objStr = JSON.stringify(objId);
					sessionStorage.setItem('user_info',objStr);
					window.location.href = "${path}/wx/businessFriend/talkAbout.do?follow_user="+$(this).attr("id");
				}
	    	});
		}
		
		function sortTime(a,b){  
			var atime = new Date("2017/05/07 19:37");
			var btime = new Date("2017/05/07 19:38");
	        return atime.getTime()-btime.getTime();
	    }
		sortTime();
	
		
		
		function inittxl(){
			//通讯录map
			var txl_map=getSessionStorageValue("txl_map");
			if(txl_map){
				return txl_map;
			}else{
				txl_map={};
				//我的好友列表
				zdy_ajax({
					url: _path+"/im/m/selMyFriendList.do",
				    showLoading:true,
				    data:{
				    	'pageNum':1,
				    	'pageSize':50,
				    	'isAll':0
				    },
					success: function(data,res){
						console.log(res);
						$.each(data,function(index){
							var u = data[index];
							txl_map[u.user_id]={"id":u.user_id,"name":u.friend_memo,"isgroup":false,"headpath":u.head_portrait};
						});
					},
				    error:function(e){
					   //alert(e);
				    }
				});
				
				//群列表
				zdy_ajax({
					url: _path+"/im/m/selMyGroupInfoList.do",
				    showLoading:true,
				    data:{
				    	'pageNum':1,
				    	'pageSize':50
				    	
				    },
					success: function(data,res){
						//console.log(res);
						$.each(data,function(index){
							var g = data[index];
							txl_map[g.group_id]={"id":g.group_id,"name":g.group_name,"isgroup":true,"headpath":g.head_portrait};
						});
					},
				    error:function(e){
				    }
				}); 
				setSessionStorageValue("txl_map",txl_map);
			//	console.log(txl_map);
				return txl_map;
			}
		}
		//获取群列表
		
		
		
		
		
		//对msg的处理
		function clMsg(msg){
			 if(!msg.search(/http:/)){
			      var msg = "图片"
			   }else{
			    var msg = replace_em(msg)
			  }
			 return msg
		}
		//表情处理
		function replace_em(str) {	
			str = str.replace(/&lt;/g, '\<');
			str = str.replace(/&gt;/g, '\>');
			str = str.replace(/&quot;/g, '"');
			str = str.replace(/<br\/>/g, '\n');
			//str = str.replace(/<img class="emotioneee" src="${path}/resource/img/arclist/$1.gif" border="0" />/g,'\[em_([0-9]*)\]');
			//console.log(str)
			return str;
		}
		//日期比较
		function getNowFormat(timestamp) {
		    var year = new Date(timestamp).getFullYear();
		    var year1 = new Date().getFullYear()
		    var month = new Date(timestamp).getMonth() + 1;
		    var month1 = new Date().getMonth() + 1;
		    var strDate = new Date(timestamp).getDate();
		    var strDate1 = new Date().getDate();
		    if (month >= 1 && month <= 9) {
		        month = "0" + month;
		    }
		    if (strDate == strDate1 && month == month1 && year == year1) {
		    	return true;
		    }else{
		    	return false
		    }
		}
		//时间处理韩硕
		function getNowFormatDate(timestamp) {
			
		    var date = new Date(timestamp);
		    var seperator1 = "/";
		    var seperator2 = ":";
		    var month = date.getMonth() + 1;
		    var strDate = date.getDate();
		    if (month >= 1 && month <= 9) {
		        month = "0" + month;
		    }
		    if (strDate >= 0 && strDate <= 9) {
		        strDate = "0" + strDate;
		    }
		    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
		            + " " + date.getHours() + seperator2 + date.getMinutes()
		    return currentdate;
		}
		
		
	</script>
</body>
</html>