<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
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
         .content-item .con-ll{
            float:none;
            height:1.22rem;
         }
         .content .content-item{
            line-height:1.22rem;
         }
         .sysMes ul li:nth-child(1){
              margin-top:0rem;
              width:41%;
              white-space: nowrap;text-overflow: ellipsis;-o-text-overflow: ellipsis;overflow: hidden;
         }
         .sysMes{
            width:41%;
            overflow:hidden;
         }
         .wrapper{
          padding-bottom:0.9rem;
         }
   </style>
</head>
<body>
	<div class="wrapper">
		<div class="content" style="margin-top:0">
			<div class="content-item bottom-border"  onclick="systemMes();">
				<div class="con-l active_A " style="background:none">
					<div style="float:left" class="imgbox">
						<img src="${path}/resource/img/images/systemMes.png" />
					</div>
					<div class="message-jishu" id="sysMessage"  style="height: 1.22rem;top: 0rem; line-height: 1.1rem;">0</div>
					<div class="sysMes ">
						<ul>
							<li style="display:block;width:50%" class="li1">系统信息</li>
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
	var u = navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
	//最近聊天记录
		var message_data=[];
		//置顶聊天记录
		var message_top_data=[];
		var sessionss=getSessionStorageValue("txl_map");
		var myUserId =localStorage.getItem("currentUserId");
		var jid = myUserId+'@'+_im_path;
		//console.log(sessionss);
		joinxmpp1();
		$(document).ready(function() {
			var mag_data = getSessionStorageValue("mag_data")
			if(mag_data){
				_data(mag_data)
			}else{
				getTopgroupuser();
			}	
			zdy_ajax({
				url: "${path}/m/message/messageCount.do",
			    showLoading:false,
			    data:{},
				success: function(data,res){
					$("#sysMessage").html(data.systemMessageCount);
					$("#actMessage").html(data.activitMessageCount);
					$("#greetings").html(data.toGreetingsCount);
					$("#latestVistors").html(data.latestVistorsCount);
				}
			});	
		});	
		//查看打招呼
		var greetings=function(){
			self.location.href="${path}/m/message/user_greets.do";
		}
		//系统消息
		var systemMes=function(){
			if(isiOS){
				$("#sysMessage").html(0);
				self.location.href="${path}/m/message/message_list.do?type=3";
			}else{
				self.location.href="${path}/m/message/message_list.do?type=3";
			}	
		}
		//活动消息
		var activityMes=function(){
			self.location.href="${path}/m/message/message_list.do?type=4";
		}
		//最近访客
		var latestVistors=function(){
			self.location.href="${path}/m/message/lasetVistor.do";
		}
		
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
				setSessionStorageValue('mag_data',data)
				_data(data)
				
				/* console.log(message_top_data) */
			},
		    error:function(e){
			   //alert(e);
		    }
		});	
		};
		function _data(data){
			//console.log(data);
			$.each(data.topGgroupList,function(index){
		    		message_top_data.push({
						"id":data.topGgroupList[index].group_id,
						"name":data.topGgroupList[index].group_name,
						"isgroup":true,
						"headpath":data.topGgroupList[index].head_portrait,
						"lastmsg":"最近没有信息",
						"lasttime":"",
						"msg_count":0
					})
			});
				
			$.each(data.topUserList,function(index){
		    	message_top_data.push({
					"id":data.topUserList[index].relat_user,
					"name":data.topUserList[index].friend_memo,
					"isgroup":false,
					"headpath":data.topUserList[index].head_portrait,
					"lastmsg":"最近没有信息",
					"lasttime":"",
					"msg_count":0
				})
			});
		}
		//连接im
		var fromUserId = ${sessionScope.me.id };
	    var jid=fromUserId+'@'+_im_path;
		var BOSH_SERVER = _im_path_http+":5280/http-bind/";
		
		function getrecentContact(){
			//console.log('1111111111111');
			connection.sendIQ($iq({
				to: _im_path,
				id:new Date(),
				type: "get"
			}).c('recent-contact', {
				xmlns: 'urn:xmpp:recent-contact'
			}), aaa);
		}
		/* var connection = null; */
	/* 	connection = new Strophe.Connection(BOSH_SERVER);
		connection.connect(jid, null, onConnect); */
	    function instantMessage(msg) {
			 //console.log(msg);
			//获取昵称，或者群名称
			// 解析出<message>的from、type属性，以及body子元素
			var from = msg.getAttribute('from');
			var type = msg.getAttribute('type');
			var elems = msg.getElementsByTagName('body');
			var jid = from.split("@")[0];
			if (type == "groupchat" && elems.length > 0) {
				var body = elems[0];
				/* layer.msg(Strophe.getText(body), {
					  offset: ['20'],
					  anim: 1
					});  */
			}else if (type == "chat" && elems.length > 0){
				var body = elems[0];
				/* layer.msg(Strophe.getText(body), {
					  offset: ['20'],
					  anim: 1
					}); */
		
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
			if(status == Strophe.Status.CONNECTING) {
			} else if(status == Strophe.Status.CONNFAIL) {
				$('#connect').get(0).value = 'connect';
			} else if(status == Strophe.Status.DISCONNECTING) {
				
			} else if(status == Strophe.Status.DISCONNECTED) {
				$('#connect').get(0).value = 'connect';
			} else if(status == Strophe.Status.CONNECTED) {
				//console.log('连上了');
				connected=true;
				//connection.addHandler(onMessage, null, 'message', null, null, null);
				connection.addHandler(instantMessage, null, 'message', null, null, null);
				getrecentContact();
				keepOling();	
				}		
			} 
		
		function keepOling() {
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
			//console.log(iq) ;
		   	var data = $(iq).find('item')
		   	$.each(data,function(index){
			    var jid = $(data[index]).attr('jid').split("@")[0]
			    /* console.log(jid) */
			    var isGroup = $(data[index]).attr('jid').split("@")[1].indexOf("group")!=-1
			    /* console.log(isGroup) */
			    var timestamp = $(data[index]).attr('timestamp');
			    var msg = $(data[index]).attr('msg');
			    var head_portrait = $(data[index]).attr('head_portrait');
			    var nickname = $(data[index]).attr('nickname');
			    var time = getNowFormatDate(timestamp)
			    //var bj = getNowFormat(timestamp)
			    var msg = clMsg(msg)
			   /*  console.log(msg) */
			   // if(bj){
			    	//time = time.split(" ")[1]
			   // }else{
			    	//time = time.split(" ")[0]
			   // }
            
			    updateMessage(isGroup,jid,msg,time,nickname,head_portrait);
			    showdata();
		   })
		}
		
		function updateMessage(isGroup,jid,msg,time,nickname,head_portrait){
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
				    	if(!head_portrait){
				    		message_data.push({
								"id":jid,
								"name":nickname,
								"isgroup":isGroup,
								"headpath":"${path}/resource/img/images/qun.png",
								"lastmsg":msg,
								"lasttime":time,
								"msg_count":0
							})
				    	}else{
				    		message_data.push({
								"id":jid,
								"name":nickname,
								"isgroup":isGroup,
								"headpath":head_portrait,
								"lastmsg":msg,
								"lasttime":time,
								"msg_count":0
							})
				    	}
				    	
				    }
				 
				}else{
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
							"name":nickname,
							"isgroup":isGroup,
							"headpath":head_portrait,
							"lastmsg":msg,
							"lasttime":time,
							"msg_count":0
						})
					
				    }
				}
			}
		}	
		//
		function showdata(){
			var msgcount = getLocalStorageValue("msg_notread_count");
			var links='';
			/* message_top_data.sort(sortTime); */
			var topstr = '';
			/* console.log(message_top_data) */
			$.each(message_top_data,function(index){
			    var obj = message_top_data[index];
			    //console.log(obj.headpath);
			    var msgcountstr =0;
			   /*  console.log(index) */
			    if(msgcount && msgcount[obj.id]){
			    	msgcountstr = msgcount[obj.id];
			    	//console.log(typeof msgcount[obj.id]);
			    };
			    if(obj.lastmsg!="最近没有信息"){
			     topstr += '<div class="goingto content-item bottom-border active_A" id="'+obj.id+'" nickName="'+obj.name+'" data-headpath="'+obj.headpath+'" data-id="+obj.id+" data-isgroup="'+obj.isgroup+'">'+
							'		<div class="con-l " style="background:none">'+
							'	<div style="float:left">'+
							'		<img class="portrait imgbox" src="'+obj.headpath+'" data-isgroup="'+obj.isgroup+'" data-headpath="'+obj.headpath+'" data-id="'+obj.id+'"/>'+
							'	</div>'+
							'	<div class="message-jishu">'+msgcountstr+'</div>'+
							'	<div class="sysMes">'+
							'		<ul>'+
							'			<li style="display:block;height:50%;font-size:0.3rem;">'+obj.name+'</li>'+
							'			<li class="sign-text clear" style="display:block;height:50%;width:2.7rem;white-space: nowrap;text-overflow: ellipsis;-o-text-overflow: ellipsis;overflow: hidden;">'+obj.lastmsg+'</li>'+
							'		</ul>'+
							'	</div>'+
							'<div class="iconfont " style="float:right" >&#xe605;</div>'+
							'	<div class="textDate">'+obj.lasttime+'</div>'+
							'</div>'+
							'<div style="clear:both"></div>'+
						'</div>';
			    
			    }else{
			    	topstr +='';
			    }  
			   
		    })
			/* message_data.sort(sortTime); */
			 $("#topmsg_div").html(topstr);
			var str="";
			$.each(message_data,function(index){
			    var obj = message_data[index];
			    var msgcountstr = 0;
			    //console.log(obj.headpath);
			    if(msgcount && msgcount[obj.id]){
			    	msgcountstr = msgcount[obj.id];
			    	//console.log(typeof msgcount[obj.id]);
			    }
			    
			    str +=  '<div class="goingto content-item bottom-border active_A" data-headpath="'+obj.headpath+'" data-id="'+obj.id+'" id="'+obj.id+'" nickName="'+obj.name+'" data-isgroup="'+obj.isgroup+'" >'+
							'<div class="con-l " style="background:none">'+
							'	<div style="float:left">'+
							'		<img class="portrait imgbox" src="'+obj.headpath+'" data-id="+obj.id+" data-isgroup="'+obj.isgroup+'" data-headpath="'+obj.headpath+'" />'+
							'	</div>'+
							'	<div class="message-jishu">'+msgcountstr+'</div>'+
							'	<div class="sysMes">'+
							'		<ul>'+
							'			<li style="display:block;height:50%;font-size:0.3rem;">'+obj.name+'</li>'+
							'			<li class="sign-text clear" style="display:block;height:50%;width:2.7rem;white-space: nowrap;text-overflow: ellipsis;-o-text-overflow: ellipsis;overflow: hidden;">'+obj.lastmsg+'</li>'+
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
				
				if(isgroup==true){
					 //console.log(isgroup);
		    		var objId = new Object()
		    		objId.follow_user = $(this).attr("id");
					objId.head_portrait = $(this).attr("headpath");
					objId.nickName=$(this).attr('nickName');
					var objStr = JSON.stringify(objId);
					sessionStorage.setItem('user_info',objStr); 
					window.location.href = "${path}/wx/businessFriend/talkAboutQl.do?qid="+$(this).attr("id"); 
				}else {
		    		var objId = new Object()
		    		objId.follow_user = $(this).attr("id");
		    		console.log($(this).attr("id"))
					objId.head_portrait = $(this).attr("data-headpath");
					objId.nickName=$(this).attr('nickName');
					//console.log(isgroup);
					var objStr = JSON.stringify(objId);
					sessionStorage.setItem('user_info',objStr);
					window.location.href = "${path}/wx/businessFriend/talkAbout.do?follow_user="+$(this).attr("id");
				}
	    	});
		}
		
		function sortTime(a,b){  
			var atime = new Date(a.lasttime);
			var btime = new Date(b.lasttime);
	        return atime.getTime()-btime.getTime();
	    }
		
		//对msg的处理
		function clMsg(msg){
			 if(msg.toString().indexOf('?id=ppp') != -1){
			      var msg = "图片";
			   }else if(msg.toString().indexOf('.mp3') != -1){
				   var msg ="语音";
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
		//时间处理
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
		    var minutess=date.getMinutes();
		    if(minutess<10){
		    	minutess= '0'+minutess;	
		    }
		    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
		            + " " + date.getHours() + seperator2 + minutess;
		    
		     return currentdate;
		}
		
		//新加连接
		 function joinxmpp1(){
			console.log(myUserId);
			console.log(jid);
			 var cookieRID=sessionStorage.getItem('Rid');
			 var cookieSID=sessionStorage.getItem('Sid');
			 connection = new Strophe.Connection(BOSH_SERVICE);
			 if(jid && !cookieRID &&  !cookieSID){
				 console.log(cookieRID+'   '+cookieSID);
				 connection.connect(jid, null, onConnects);
					 getrecentContact();
				 connection.xmlOutput = function (e) {
	                      if(connected){
				             var RID = $(e).attr('rid');
				             var SID = $(e).attr('sid');
				             sessionStorage.setItem('Rid',RID);
				             sessionStorage.setItem('Sid',SID);
				            // console.log('RID=' + RID + ' [SID=' + SID + ']');
	                      }
				    };
			   }else if(jid  && cookieRID && cookieSID){
				 connection.attach(jid,cookieSID,parseInt(cookieRID)+1,attachingCallback1);
			 };
		 }	
		
		 function attachingCallback1(e){
			 console.log(e);
			 if(e == Strophe.Status.ATTACHED){ 
				    connected = true;
				    getrecentContact();
				    connection.addHandler(instantMessage, null, 'message', null, null, null);
					connection.xmlOutput = function (e) {
						  /*  console.log(e); */
				             var RID = $(e).attr('rid');
				             var SID = $(e).attr('sid');
				             sessionStorage.setItem('Rid',RID);
				             sessionStorage.setItem('Sid',SID);
				             //console.log('RID=' + RID + ' [SID=' + SID + ']');     
				    };   
			 }else{
				 //断开重新连接
				// console.log('连接断开');
				 sessionStorage.removeItem('Rid');
		         sessionStorage.removeItem('Sid');
		         joinxmpp1();
			 }
			 
		}
	</script>
</body>
</html>