<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<meta name="keywords" content="群聊">
		<title>群聊</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/talkAbout.css?v=${sversion}" />
		<script type="text/javascript" src="${path}/resource/js/libs/strophe.min.js?v=${sversion}" charset="utf-8"></script>
		<script type="text/javascript" src="${path}/resource/js/libs/xmpp.mam.js?v=${sversion}" charset="utf-8"></script>
		<script type="text/javascript" src="${path}/resource/js/jquery.qqFace.js?v=${sversion}" charset="utf-8"></script>
		<script type="text/javascript" src="${path}/resource/js/libs/iscroll.js?v=${sversion}" charset="utf-8"></script>
	</head>

	<body style="background: #ebebeb">
	    <div id="recordIcon">
           <div>
             <img src="${path}/resource/img/images/recording.gif"/>
           </div>  
        </div>
		<div class="T-talkAbout" style="background: #ebebeb;width: 100%; height: 100%;">
			<div class="wrapper" id="wrapper">
				<div class="scroller" id="scroller">
				</div>
			</div>
			<div class="cfooter">
				<div class="footer-top">
				    <i class="imboard"  id="torecord"></i>
					<!-- <i class="iconfont1"  id="file_button_1" onclick="doSelectPic1();"></i> -->
					<iframe id="uploadPicFrame1" src="" style="display:none;"></iframe>
					<input type="text" name="t-content" id="t-content" value="" class="bottom_input" placeholder="请输入..." />
					<div  style="display:none"  id="pressRecord" >开始录音</div>
					<!-- <input type="file" style="display:none" id="uploadPhoto" /> -->
					<i class="imFace emotion" id="emotion"><!-- &#xe60e; --></i>
					<i class="imgSpred jihao" style=""><!-- &#xe609; --></i>
					<span class="send">发送</span>
				</div>
				<div class="hidePh">
					<div class="iconfont1"  id="file_button_1" onclick="doSelectPic1();"></div>
					<div class="sz"><i class="iconfont"><!-- &#xe619; --></i></div>
					<br style="clear:both"/>
				</div>
			</div>
		</div>
		<audio id="audio_id" src=""></audio>
    	 <%@ include file="/WEB-INF/jsp/commons/include_upload.jsp"%>
		<script type="text/javascript">
		 //获取群信息
		 function setGroupInfo(qid){
 			/* setSessionStorageValue("usersInfo"+qid,arrUser); */
 			var groupMinfor=getSessionStorageValue("usersInfo"+qid);
 			console.log(groupMinfor);
			if(groupMinfor){
				usersInfo = getSessionStorageValue("usersInfo"+qid);
				joinxmpp1();
			}else{
				
				zdy_ajax({
                    url : _path + "/im/m/selGroupInfoById.do",
					showLoading : false,
					data : {
						groupId : qid
					},
					success : function(data, res) {
						/* var objId = new Object();
						objId.nickName=data.groupInfo.groupName
						var objStr = JSON.stringify(objId);
						sessionStorage.setItem('user_info',objStr);  */
						var arrUser = [];
						$.each(data.users,function(k){
							var user_id = data.users[k].user_id;
							var head_portrait = data.users[k].head_portrait;
						 	var objUsers = {user_id:user_id,head_portrait:head_portrait}
							arrUser.push(objUsers)
						})
						setSessionStorageValue("usersInfo"+qid,arrUser)
						usersInfo=arrUser;
						joinxmpp1();
					},
					error : function(e) {
						//alert(e);
					}
               });
			}
		 }
			var usersInfo = '';
		    var fromUserId = ${sessionScope.me.id };
		    var havehistory=false;
			var jid = fromUserId + '@'+_im_path;
			var myheadpath = '<%=headpic   %>';
			var myname = "<%=nickname  %>";
			var lessThanTen=false;
			var qid = window.location.href.split("=")[1]
			var value = sessionStorage.getItem('user_info');
			var  obj = JSON.parse(value);
			console.log(obj)
			$('title').html(obj.nickName);
			var primgs = [];
			
			/* joinxmpp1(); */
			//清空此群的未读消息记录
			var msgcount = getLocalStorageValue("msg_notread_count");
			if(msgcount && msgcount[qid]){
			 	msgcount[qid]=0;
				setLocalStorageValue("msg_notread_count",msgcount);
			}
			 
		    // XMPP服务器BOSH地址
			var BOSH_SERVICE = _im_path_http+':5280/http-bind/';
			// 房间JID
			var ROOM_JID = qid + '@group.'+_im_path;
			// XMPP连接
			var connection = null;
			// 当前状态是否连接
			var connected = false;
			// 当前登录的JID
			var stamp = '';
			var scrolled=false;
			var firstEnter=true;
			//获取用户头像
			setGroupInfo(qid);
			//初始化绑定iScroll控件 
			document.addEventListener('touchmove', function(e) {
				//e.preventDefault();
			}, false);
			document.addEventListener('DOMContentLoaded', loaded, false);
			
			 function imgFt(msg){
							 var reg = /^http:\/\/tianjiu.oss*/g;
							 return reg.test(msg);
					     }
			
			function cHeader(usersInfo,from){
				        var currentpic='${path}/resource/img/icons/weixinHeader.jpg';
						$.each(usersInfo,function(i){
							if( usersInfo[i].user_id && usersInfo[i].user_id == from){
								currentpic=usersInfo[i].head_portrait; 
							}
						})
						return currentpic;
			}	
			
			function showimgs(obj){	
			 		primgs = [];
			 		$(".messageIMGS").each(function(){
			 			primgs.push($(this).attr("src"));
			 		});
			 		wx.previewImage({
			 		    current: $(obj).attr("src"), // 当前显示图片的http链接
			 		    urls: primgs // 需要预览的图片http链接列表
			 		});
			}
			
			function log2(msg,from,isimg,dur) {
				var varId=yzm();
				var FT =imgFt(msg)//判断是否为图片
				var head_portrait =cHeader(usersInfo,from);
				if (msg.toString().indexOf('?id=ppp')!=-1) {
					//判断是否是图片
					console.log(msg);
					$('#scroller').append('<div class="yourFriend " id="'+varId+'"><img src="' + head_portrait + '"  onclick="ckInfo('+from+')" class="_head" /><span>' + '<img class="messageIMGS" style="max-width:3.5rem;max-height:3.5rem;" onclick="showimgs(this);" src="' + msg + '"/>' + '</span></div><div id="'+varId+'" class="poles" style="height:2.5rem;">');
					myScroll.refresh();
				}else if(msg.toString().indexOf('mp3')!=-1){
			        var timel = dur;
			        console.log(msg);
			    	$('#scroller').append('<div class="yourFriend " id="'+varId+'"><img src="'+head_portrait+'" class="_head" onclick="ckInfo('+from+')" /><span style="line-height:0.5rem;display:flex;">'+ '<img class="faudios" style="width:0.35rem;height:0.5rem;" FR="1" count="'+timel+'" onclick="fplayvoidce(this)" audio="'+msg+'" src="${path}/resource/img/images/FFFFFFFFFFFF.png"><span style="padding:0">'+timel+'\" '+'</span></span></div>');
			    	myScroll.refresh();
				}else {
			    	console.log(msg);
					msg = msg.replace(/&lt;/g, '<');
					msg = msg.replace(/&gt;/g, '>');
					msg = msg.replace(/&quot;/g, '"');
					$('#scroller').append('<div class="yourFriend " id="'+varId+'"><img src="' + head_portrait + '"class="_head" onclick="ckInfo('+from+')"/><span>' + msg + '</span></div>');
					myScroll.refresh();
			    }
				if(!scrolled && !firstEnter){
					myScroll.refresh();
					$(".poles").remove();
					return;
				};
			    if($("#scroller").height()<$(window).height()*0.8){
					$(".poles").remove();
					myScroll.refresh();
					return;
				} 
			    
				scrollToP(varId);
				/* myScroll.refresh();  */
			};

			function log1(msg) {
				/* console.log(yzm()); */
				var varId=yzm();
				var FT =imgFt(msg)//判断是否为图片
				if (msg.toString().indexOf('?id=ppp') != -1/* ||FT */) {
					//判断是否是图片
					$('#scroller').append('<div class="myself "><span>' + '<img class="messageIMGS" style="max-width:3.5rem;max-height:3.5rem;" onclick="showimgs(this)" src="' + msg + '"/>' + '</span><img src="' + myheadpath + '" class="_head" onclick="ckInfo1()"/></div><div id="'+varId+'" class="poles" style="height:2.5rem;"></div>');
					myScroll.refresh();
				 }else if(msg.toString().indexOf('mp3')!=-1){
			    	var timel = msg.toString().split("=")[1];
			    	$('#scroller').append('<div class="myself " id="'+varId+'"><span style="display:flex;line-height:0.5rem;"><span style="padding:0">' +timel+'\" '+'</span><img class="audios" FR="0" style="width:0.35rem;height:0.5rem;" count="'+timel+'" onclick="playvoidce(this)" audio="'+msg+'" src="${path}/resource/img/images/MMMMMMMMMMMM.png">'+'</span><img src="'+myheadpath+'" class="_head" onclick="ckInfo1()" /></div>');
			    }else {
					msg = msg.replace(/&lt;/g, '<');
					msg = msg.replace(/&gt;/g, '>');
					msg = msg.replace(/&quot;/g, '"');
					$('#scroller').append('<div class="myself " id="'+varId+'"><span>' + msg + '</span><img src="' + myheadpath + '"class="_head" onclick="ckInfo1()"/></div>');
				};
				if($("#scroller").height()<$(window).height()*0.8){
					/* console.log("炒高了"); */
					$(".poles").remove();
					myScroll.refresh();
					return;
				}
				myScroll.refresh();
			    scrollToP(varId);
			};
			function log4(msg,from,isimg,dur) {
			   /*  console.log(from+'++++++++++'); */
			   var varId=yzm();
				var FT =imgFt(msg)//判断是否为图片
				/* console.log(cHeader(usersInfo,from)); */
				var head_portrait = cHeader(usersInfo,from);
				/* console.log(head_portrait) */
				if (msg.toString().indexOf('?id=ppp')!=-1) {
					//判断是否是图片
					$('#scroller').prepend('<div class="yourFriend " id="'+varId+'"><img src="' +head_portrait + '"class="_head" onclick="ckInfo2('+from+')" /><span>' + '<img class="messageIMGS" style="max-width:3.5rem;max-height:3.5rem;" onclick="showimgs(this)" src="' + msg + '"/>' + '</span></div>');
					myScroll.refresh();
				}else if(msg.toString().indexOf('mp3')!=-1){
			    	var timel = dur;
			    	$('#scroller').prepend('<div class="yourFriend " id="'+varId+'"><img src="'+head_portrait+'" class="_head" onclick="ckInfo2('+from+')" /><span style="line-height:0.5rem;display:flex;">'+'<img class="faudios" style="width:0.35rem;height:0.5rem;" FR="1"  count="'+timel+'" onclick="fplayvoidce(this)" audio="'+msg+'" src="${path}/resource/img/images/FFFFFFFFFFFF.png"><span style="padding:0">'+timel+'\" '+'</span></span></div>');
			    	myScroll.refresh();
				}else {
					msg = msg.replace(/&lt;/g, '<');
					msg = msg.replace(/&gt;/g, '>');
					msg = msg.replace(/&quot;/g, '"');
					$('#scroller').prepend('<div class="yourFriend " id="'+varId+'"><img src="' +head_portrait + '" class="_head" onclick="ckInfo2('+from+')"/><span>' + msg + '</span></div>');
					myScroll.refresh();
				}
				myScroll.refresh();
				if(firstEnter){
					 scrollToP(varId); 
				}
			};
			function log3(msg,isimg,dur) {
				var varId=yzm();
				var FT =imgFt(msg)//判断是否为图片
				if (msg.toString().indexOf('?id=ppp')!=-1) {
					//判断是否是图片
					$('#scroller').prepend('<div class="myself " id="'+varId+'"><span>' + '<img class="messageIMGS" style="max-width:3.5rem;max-height:3.5rem;" onclick="showimgs(this)" src="' + msg + '"/>' + '</span><img src="' + myheadpath + '" class="_head" onclick="ckInfo1()"  />');
					myScroll.refresh();
				}else if(msg.toString().indexOf('mp3')!=-1){
			    	var timel =dur;
			    	$('#scroller').prepend('<div class="myself " id="'+varId+'"><span style="display:flex;line-height:0.5rem;"><span style="padding:0">' +timel+'\" '+'</span><img class="audios" style="width:0.35rem;height:0.5rem;" count="'+timel+'" FR="0" onclick="playvoidce(this)" audio="'+msg+'" src="${path}/resource/img/images/MMMMMMMMMMMM.png">'+ '</span><img src="'+myheadpath+'" class="_head" onclick="ckInfo1()" /></div>');
			    	myScroll.refresh();
				}else {
					msg = msg.replace(/&lt;/g, '<');
					msg = msg.replace(/&gt;/g, '>');
					msg = msg.replace(/&quot;/g, '"');
					//console.log(msg)
					$('#scroller').prepend('<div class="myself " id="'+varId+'"><span>' + msg + '</span><img src="' + myheadpath + '"class="_head" onclick="ckInfo1()" /></div>');
					myScroll.refresh();
				};
				myScroll.refresh();
				if(firstEnter){
					 scrollToP(varId); 
				}
			};
			function ckInfo2(from) {
				$('.yourFriend ._head').on('click', function() {
				    window.location.href = _path + "/wx/businessFriend/friendInfo.do?follow_user=" +from;
				})
			}
			function ckInfo1() {
				$('.myself ._head').on('click', function() {
				    window.location.href = _path + "/wx/businessFriend/friendInfo.do?follow_user=" +fromUserId;
				})
			}
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
					//console.log("连接成功，可以开始聊天了！");
					connected = true;
					// 当接收到<message>节，调用onMessage回调函数
					connection.addHandler(onMessage, null, 'message', null, null, null);
					// 首先要发送一个<presence>给服务器（initial presence）
					connection.send($pres().tree());
					historyTalk(stamp);
					/* keepOling(); */
				}
			}
		
			//查询历史聊天记录
			var lastTime = 0
			function historyTalk(stamp1) {
				 var ssss = ""; 
				 if(stamp1!=""){
					 var time = Number(stamp1.split('.')[1].split("Z")[0])-100000+"Z";
				     ssss = stamp1.slice(0,stamp1.indexOf('.')+1)+time;
				 };
				 console.log("++++"+ssss);
				 connection.mam.query(jid, {
						"before":"",
						"with": ROOM_JID,
						"end":ssss,
						"max": "10", 
						onMessage: function(message) {
							//console.log(message)
							havehistory=true;
						},
						onComplete: function(response) {
							//console.log(arr);
							if(arr.length<10){
								lessThanTen=true;
							};
							if(arr.length){
								firstfalg=false;
								arr.reverse();
								for(var index = 0;index < arr.length;index++){
									//console.log(arr[index])
									var sjstr = $(arr[index]).find("forwarded delay").attr('stamp');
									var from = $(arr[index]).find("forwarded message").attr('from').split("@")[0];
									var text = $(arr[index]).find("forwarded message body").text();
									var isimg=false;
									if($(arr[index]).find("contentType").text()=='image'){
										isimg=true;
									}  
									
									if(from ==fromUserId) {
										  /* console.log(from ==fromUserId); */
								          log3(text,isimg,$(arr[index]).find("duration").text())
							        } else {
							        	/* console.log(from ==fromUserId); */ 
							        	var from = $(arr[index]).find("forwarded message").attr('from').split("/")[1];
								        log4(text,from,isimg,$(arr[index]).find("duration").text())
							        }
						       	}
						    	stamp = $(arr[arr.length-1]).find("forwarded delay").attr('stamp');
						    	/* myScroll.refresh(); */
							}	
						}
			});


			}
			//下拉加载
			var myScroll,
				generatedCount = 0;
			/**
			 * 下拉刷新 （自定义实现此方法）
			 * myScroll.refresh();  // 数据加载完成后，调用界面更新方法
			 */
			function pullDownAction(stamp) {
				 
				
				 if(!havehistory || lessThanTen){
					//console.log(lessThanTen);
					 return;
				 }
				 //console.log(havehistory);
				setTimeout(function() { // <-- Simulate network congestion, remove setTimeout from production!
					generatedCount++
					//console.log("下拉")
					historyTalk(stamp);
					myScroll.refresh(); //数据加载完成后，调用界面更新方法 Remember to refresh when contents are loaded (ie: on ajax completion)
				}, 1); // <-- Simulate network congestion, remove setTimeout from production!
			}
			 function pullUpAction() {
					setTimeout(function() { // <-- Simulate network congestion, remove setTimeout from production!
						myScroll.refresh(); // 数据加载完成后，调用界面更新方法 Remember to refresh when contents are loaded (ie: on ajax completion)
					}, 1000); // <-- Simulate network congestion, remove setTimeout from production!
				}

			/**
			 * 初始化iScroll控件
			 */
			 //防止重复下拉(在数据还没有加载出来之前，导致重复聊天记录获取);
			 var preventRepeat=true;
			function loaded() {
				myScroll = new iScroll('wrapper', {
					scrollbarClass: 'myScrollbar',
					/* 重要样式 */
					useTransition: false,
					/* 此属性不知用意，本人从true改为false */
					onRefresh: function() {		
					},
					onScrollStart: function () {
						firstEnter=false;
				    },
					onScrollMove: function() {
						 preventRepeat=false;
						/*  console.log('start+++++++++++'+'   '+preventRepeat); */
						if(this.y>30){
							$('#scroller').get(0).className = "down"
						}
						if(this.y<-30){
							$('#scroller').attr('up','up')
						}
					},
					onScrollEnd: function() {
						if(myScroll.y==myScroll.maxScrollY){
							scrolled=true;
						}else{
							scrolled=false;
						};
						 var position='1';
						     position=this.y;
						     if(this.y!=0){
						    	 //console.log(this.y)
						    	 return;
						     }
						 generatedCount++;
						 preventRepeat=true;
						/*  console.log('end+++++++++++'+'  '+preventRepeat ); */
						 if($('#scroller').get(0).className.match('down')&& arr){
							$('#scroller').attr('class',"")
							if(generatedCount ==1){
								 //var stamp = $(arr[0]).find("forwarded delay").attr('stamp');
							}else{
							    //var stamp = $(arr[arr.length-1]).find("forwarded delay").attr('stamp');
							}
							// console.log(stamp);
							 
							 arr=[];
							 pullDownAction(stamp)
						} 
						 if($('#scroller').attr('up')){
							 pullUpAction()
						} 
					}
				});
				
				 setTimeout(function() {
					document.getElementById('wrapper').style.left = '0';
				}, 800); 
			}

			//初始化绑定iScroll控件 
			document.addEventListener('touchmove', function(e) {
				//e.preventDefault();
			}, false);
			document.addEventListener('DOMContentLoaded', loaded, false);
			// 接收到<message>
			var arr = [];
			
			function onMessage(msg) {
				arr.push(msg)
					// 解析出<message>的from、type属性，以及body子元素
				var from = msg.getAttribute('from');
				var type = msg.getAttribute('type');
				var elems = msg.getElementsByTagName('body');
				var fromid = from.split("@")[0];
				if (fromid == qid && type == "groupchat" && elems.length > 0) {
					var from = from.split("/")[1]
					var body = elems[0];
					var isimg=false;
					if($(msg).find("contentType").text()=='image'){
						isimg=true;
						//console.log("图片");
					} 
					log2(Strophe.getText(body),from,isimg,$(msg).find("duration").text());
				/* 	console.log($('#scroller').height());
                    myScroll.refresh(); */
				} else if(elems.length > 0){
					//提示消息
					if (type == "groupchat" && elems.length > 0) {
						var body = elems[0];
						/* layer.msg(Strophe.getText(body), {
							  offset: ['20'],
							  anim: 1
							}); */
					}else if (type == "chat" && elems.length > 0){
						var body = elems[0];
						/* layer.msg(Strophe.getText(body), {
							  offset: ['20'],
							  anim: 1
							});; */
				
					} 
					//更新未读消息到local
				 	var msgcount = getLocalStorageValue("msg_notread_count");
					if(!msgcount)msgcount={};
					if(msgcount[fromid]){
						msgcount[fromid]+=1;
					}else{
						msgcount[fromid]=1;
					}
					setLocalStorageValue("msg_notread_count",msgcount);
				} 
				return true;
			}
			// 通过BOSH连接XMPP服务器
			/* if (!connected) {
				connection = new Strophe.Connection(BOSH_SERVICE);
				connection.connect(jid, null, onConnect);
				jid = jid;
			} */
			$(".jihao").on("click", function(e) {
				e.stopPropagation();
				$("#facebox").remove();
				if($(this).hasClass('on')){
					$(this).removeClass('on')
					$('.hidePh').css({"display": "none"});
					$('.sz').css('display','none');
					$('#file_button_1').css('display','none');
				}else{
					$(this).addClass('on')
					$('.hidePh').css({"display": "block"})
					$('.sz').css('display','block');
					$('#file_button_1').css('display','block');
				}
			});
			$('#t-content').on("focus", function() {
				$('.hidePh').css({"display": "none"})
				sendMsgHide()
			});
			$('#t-content').on('keyup', function() {
				sendMsgHide()
			});
			function sendMsgHide(){
				var les =$.trim($('#t-content').val()).length;
				if (les > 0) {
					$(".jihao").css({'display': 'none'});
					$(".send").css({'display': 'block'});
				} else {
					$(".jihao").css({'display': 'block'});
					$(".send").css({'display': 'none'});
				};
			}
			$(".send").click(function(e) {
				 e.stopPropagation();
				 $('.hidePh').css('display','none');
				if (connected) {
					var msg1 = replace_em($('#t-content').val());
					sendMsg(ROOM_JID,jid,msg1,2)
					 /* myScroll.refresh(); */
					/* myScroll.scrollTo(0,-$('#scroller').height()); */
					$("#t-content").val('');
					$(".jihao").css({
						'display': 'block'
					});
					$(".send").css({
						'display': 'none'
					});
				}
			});
			
			//发送消息
			function sendMsg(toId,fromId1,msg,type,dur) {
					/* alert(msg); */
				    var ctype='';
					var reply='';
					 if(type==1){
						var time=dur;
						ctype='audio';
						reply = $msg({
							to : toId,
							from : fromId1,
							type : 'groupchat',
							id:new Date()
						}).c('body').t(msg).up().c("request", {
							xmlns: 'urn:xmpp:receipts'
						}).up().c('contentType').t(ctype)
						.up().c('duration').t(dur.toString());
						connection.send(reply.tree());
						log1(msg);
					}else if(type==2){
						ctype='text';
						reply = $msg({
							to : toId,
							from : fromId1,
							type : 'groupchat',
							id:new Date()
						}).c('body').t(msg)
						  .up().c("request", {xmlns: 'urn:xmpp:receipts'}) 
						  .up().c('contentType').t(ctype);
						  connection.send(reply.tree());
						  log1(msg);
					}
					/* myScroll.refresh(); */
					/* myScroll.scrollTo(0,-$('#scroller').height()); */
		}
			
			
			$('.sz').on('click', function() {
				window.location.href = _path + "/wx/businessFriend/msg.do?follow_user=" + qid;
			})
			$('.emotion').qqFace({
				id: 'facebox',
				assign: 't-content',
				path: '${path}/resource/img/arclist/' //表情存放的路径
			});
              
			//公用
			function replace_em(str) {
				str = str.replace(/\</g, '&lt;');
				str = str.replace(/\>/g, '&gt;');
				str = str.replace(/\n/g, '<br/>');
				str = str.replace(/\[em_([0-9]*)\]/g, '<img class="emotioneee" src="${path}/resource/img/arclist/$1.gif" border="0" />');
				return str;
			}
			//微信发送图片
			function doSelectPic1() {
				zdy_chooseImg(function(data,res){
					//console.log(data)
		    	 	if(res.code == 0){
		    	 		toId = ROOM_JID;
						fromId = jid;
						var msg = data.img_url + '?id=ppp';
						sendPicture(toId, fromId, msg);
		    	 	}
		    	 },"chat");
			} 
			function sendPicture(toId, fromId, msg) {
				var reply = $msg({
					to: toId,
					from: fromId,
					type: 'groupchat',
					id:Math.random()
				}).c('body', {
					class: 'tupians'
				}).t(msg).up().c('contentType').t('image');
				connection.send(reply.tree());
				log1(msg);
				$(".jihao").click();
			}

		  //滚动到指定位置
		  function scrollToP(obj){
			 /*  myScroll.refresh(); */
			  myScroll.scrollToElement(document.querySelector('#scroller>#'+obj),200);
			  myScroll.refresh();
			  $(".poles").remove();
			  scrolled=true;
		  }
		  //生成随机Id
		   function yzm(){
                 var arr = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
                 var str = '';
                  for(var i = 0 ; i < 4 ; i ++ )
                      str += ''+arr[Math.floor(Math.random() * arr.length)];
                      return str;
            }
			
			
			//发送语音功能。
		    $("#torecord").on("click",function(e){
		    	if($("#recordIcon").css('display')=='block'){
		    		return;
		    	}
		    	 if($(this).hasClass("imKey")){
		    		 $(this).removeClass("imKey").addClass("imboard");
		    		 $("#pressRecord").hide();
		        	 $("#t-content").show();
		    	 }else{
		    		 $(this).removeClass("imboard").addClass("imKey");
		    		 $("#pressRecord").show();
		        	 $("#t-content").hide();
		    	 };
		    })
		    $("#emotion").on("click",function(){
		    	if(!$("#pressRecord").hasClass("startrecod")){
		    		$("#torecord").removeClass("imKey").addClass("imboard");
		    		$("#pressRecord").hide();
		       	    $("#t-content").show();
		    	}	
		    })
		     $("#pressRecord").on("click",function(e){
		    	if(!$(this).hasClass('startrecod')){
		    		$(this).addClass('startrecod');
		        	$(this).text('结束录音');
		        	$("#recordIcon").show();
		        	startRecord ();
		    	}else{
		    		$(this).removeClass('startrecod');
		    		$(this).text('开始录音');
		        	$("#recordIcon").hide();
		        	stopRecord();
		    	}	
		    }) 
		    var localId = ""; //本地语音id
			var serverId=""; //服务器语音id
			var t; //定时器
			var t_seconds = 0; // 计时秒数
			//录音
			function startRecord (){
				wx.startRecord();
				t_seconds=0;
				timedCount();
			};
			//停止录音
			function stopRecord() {
				if(t_seconds<2){
					stopCount();
					t_seconds=0;
					wx.stopRecord();
					layer.msg('录音时间过短，请重新录音');
					return;
				}
				wx.stopRecord({
				    success: function (res) {
				    	 stopCount();
				        wx.uploadVoice({
						    localId: res.localId, // 需要上传的音频的本地ID，由stopRecord接口获得
						    isShowProgressTips: 1, // 默认为1，显示进度提示
						        success: function (res) {
						        serverId = res.serverId; // 返回音频的服务器端ID 
						        //alert("上传录音。。。");
						        zdy_ajax({
				    				url: '${path}/m/upload/wxUploadVoice.do',
				    				data : {
				    					serverId : serverId,
										moduleName:"chat"
				    				},
				    				success: function(data,res){
				    					mediaId = data.img_url;
				    					var fromId = jid;
				    				    sendMsg(ROOM_JID, fromId,mediaId+'?t='+t_seconds,1,t_seconds); 
				    				   
				    				}
				    			}); 
						    }
						});
				    }
				   
				});
			};
			var timelength=0;
			var thisVoice='';
			function playvoidce(obj){
				           stopCount();
				           thisVoice=$(obj);
				           $(obj).attr("src","${path}/resource/img/images/msgFormM.gif"); 
					       $(".audios").not($(obj)).attr("src","${path}/resource/img/images/MMMMMMMMMMMM.png");
					       $(".faudios").attr("src","${path}/resource/img/images/FFFFFFFFFFFF.png"); 		
					       var audio=$("#audio_id")[0];
							audio.src=$(obj).attr("audio");
							audio.play();
							timelength=parseInt($(obj).attr("count")); 
							t_seconds=0;
							timedCount();
			}
			
			function fplayvoidce(obj){
		        stopCount();
		        thisVoice=$(obj);
		       $(obj).attr("src","${path}/resource/img/images/msgFromF.gif"); 
			      $(".faudios").not($(obj)).attr("src","${path}/resource/img/images/FFFFFFFFFFFF.png");
			      $(".audios").attr("src","${path}/resource/img/images/MMMMMMMMMMMM.png");
					var audio=$("#audio_id")[0];
					audio.src=$(obj).attr("audio");
					audio.play();
					timelength=parseInt($(obj).attr("count")); 
					t_seconds=0;
					timedCount();
		}
			//录音时长
			function timedCount(){  
				t_seconds++ ;
				t=setTimeout("timedCount()",1000);
				if(t_seconds==timelength){
					/* alert(typeof thisVoice.attr("FR")); */
					if(thisVoice.attr("FR")=='0'){
						thisVoice.attr("src","${path}/resource/img/images/MMMMMMMMMMMM.png");
					}else{
						thisVoice.attr("src","${path}/resource/img/images/FFFFFFFFFFFF.png");
					}
					stopCount();
					timelength=0;
					thisVoice=''
				}
				
			} 
			//清除定时器
			function stopCount(){
				clearTimeout(t);
			}
			//新连接
			function joinxmpp1(){
				 var cookieRID=sessionStorage.getItem('Rid');
				 var cookieSID=sessionStorage.getItem('Sid');
				 connection = new Strophe.Connection(BOSH_SERVICE);
				 if(jid &&!cookieRID &&  !cookieSID){
					// console.log(cookieRID+'   '+cookieSID);
					 connection.connect(jid, null, onConnect);
					 connection.xmlOutput = function (e) {
					             var RID = $(e).attr('rid');
					             var SID = $(e).attr('sid');
					             sessionStorage.setItem('Rid',RID);
					             sessionStorage.setItem('Sid',SID);
					             //console.log('RID=' + RID + ' [SID=' + SID + ']');
					    };
				   }else if(jid &&cookieRID && cookieSID){  
					 connection.attach(jid,cookieSID,parseInt(cookieRID)+1,attachingCallbackXX);
				 };
			 }
			
			function attachingCallbackXX(e){
				 console.log(e);
				 if(e == Strophe.Status.ATTACHED){ 
					    connected = true;
					    historyTalk(stamp);
					    connection.addHandler(onMessage, null, 'message', null, null, null);
						connection.xmlOutput = function (e) {
					             var RID = $(e).attr('rid');
					             var SID = $(e).attr('sid');
					             sessionStorage.setItem('Rid',RID);
					             sessionStorage.setItem('Sid',SID);
					             console.log('RID=' + RID + ' [SID=' + SID + ']');     
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