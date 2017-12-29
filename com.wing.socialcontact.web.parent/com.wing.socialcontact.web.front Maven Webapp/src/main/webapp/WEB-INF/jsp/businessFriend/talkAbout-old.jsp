<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="keywords" content="聊天"> 
<title>聊天</title>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/talkAbout.css?v=${sversion}" />
<script type="text/javascript" src="${path}/resource/js/libs/strophe.min.js?v=${sversion}" charset="utf-8"></script> <script type="text/javascript" src="${path}/resource/js/libs/xmpp.mam.js?v=${sversion}" charset="utf-8"></script>
<script type="text/javascript" src="${path}/resource/js/jquery.qqFace.js?v=${sversion}" charset="utf-8"></script>
<script type="text/javascript" src="${path}/resource/js/libs/iscroll.js?v=${sversion}" charset="utf-8"></script> 
</head>
<body style="background: #ebebeb">
        <div id="recordIcon">
           <div>
             <img src="${path}/resource/img/images/recording.gif"/>
           </div>  
        </div>
		<div class="wrapper" id="wrapper">
			<div class="scroller" id="scroller" >
			</div>
		</div>
		<div class="cfooter">
				<div class="footer-top">
					<i class="imboard"  id="torecord"></i>
					<iframe id="uploadPicFrame1" src="" style="display:none;"></iframe>
					<!-- <input type="file" style="display:none" id="uploadPhoto" /> -->
					<input type="text" name="t-content" id="t-content" value="" placeholder="请输入..." />
					<div style="display:none"  id="pressRecord" >开始录音</div>
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
			<audio id="audio_id" src=""></audio>
    <%@ include file="/WEB-INF/jsp/commons/include_upload.jsp"%>
	<script type="text/javascript">
		/*  $('#t-content').on('focus',function(event){      
	       //自动反弹 输入法高度自适应
	        setTimeout(function(){
	        	//alert("s");
	        	this.scrollIntoViewIfNeeded();
	        	//this.scrollIntoView(true);
	        },500);
	    });  */
	    //下拉加载
		 var myScroll,
		 generatedCount = 0;
		 var fromUserId = ${sessionScope.me.id };
	     var jid=fromUserId+'@'+_im_path;
	     var myheadpath = '<%=headpic%>';
	     var myname = "<%=nickname%>";
		 var value = sessionStorage.getItem('user_info');
		 var  obj = JSON.parse(value);
		 console.log(obj)
		 $('title').html(obj.nickName);
		 var follow_user = obj.follow_user;
		 console.log(follow_user)
		 //清空此人的未读消息记录
		 var msgcount = getLocalStorageValue("msg_notread_count");
		 if(msgcount && msgcount[follow_user]){
		 	msgcount[follow_user]=0;
			setLocalStorageValue("msg_notread_count",msgcount);
		 } 
		 var head_portrait = obj.head_portrait;
		 var toId = follow_user+'@'+_im_path;
		 //console.log(toId);
		 var BOSH_SERVER = _im_path_http+":5280/http-bind/";
		 var connection = null;
		 var stamp="";
		 var firstfalg=true;
		 var scrolled=false;
		 var firstEnter=true;
		 joinxmpp1();
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
		 
		 function imgFt(msg){
			 var reg = /^http:\/\/tianjiu.oss*/g;
			 return reg.test(msg);
	     }
		 function log2(msg,dur,isimg) { 
			var varId=yzm();
			var FT =imgFt(msg)//判断是否为图片
		    if( isimg/* msg.toString().indexOf('?id=ppp')!=-1 *//* ||FT */){
		      //判断是否是图片
		    	$('#scroller').append('<div class="yourFriend" ><img src="'+head_portrait+'" class="_head" /><span>' + '<img class="messageIMGS" style="max-width:3.5rem;max-height:3.5rem;" onclick="showimgs(this)" src="'+msg+'"/></div>' + '</span></div><div id="'+varId+'" class="poles" style="height:2.5rem;"></div>');
		    	myScroll.refresh();
		    }else if(msg.toString().indexOf('mp3')!=-1){
		        var timel = dur;
		    	$('#scroller').append('<div class="yourFriend" id="'+varId+'"><img src="'+head_portrait+'" class="_head" /><span style="line-height:0.5rem;display:flex;">'+ '<img class="faudios" style="width:0.35rem;height:0.5rem;" FR="1" count="'+timel+'" onclick="fplayvoidce(this)" audio="'+msg+'" src="${path}/resource/img/images/FFFFFFFFFFFF.png"><span style="padding:0">'+timel+'\" '+'</span></span></div>');
		    	myScroll.refresh();
		    }else{
		    	msg = msg.replace(/&lt;/g, '<');
				msg = msg.replace(/&gt;/g, '>');
				msg = msg.replace(/&quot;/g, '"');
				$('#scroller').append('<div class="yourFriend" id="'+varId+'"><img src="'+head_portrait+'" class="_head" /><span>' + msg + '</span></div>');
				myScroll.refresh();
		    }
			ckInfo2();
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
		   /*  myScroll.refresh(); */
			scrollToP(varId);	
		};
		function log1(msg) {
			var varId=yzm();
			var FT =imgFt(msg)//判断是否为图片'<div onclick="playMedia('+msg+')" >语音</div>'
			   if(msg.toString().indexOf('?id=ppp')!=-1/* ||FT */){
				 //判断是否是图片
			       $('#scroller').append('<div class="myself "><span>'+'<img class="messageIMGS" style="max-width:3.5rem;max-height:3.5rem;" onclick="showimgs(this)" src="'+msg+'"/>' + '</span><img src="'+myheadpath+'" class="_head" /></div><div id="'+varId+'" class="poles" style="height:2.5rem;"></div>');
			       myScroll.refresh(); 
			   }else if(msg.toString().indexOf('mp3')!=-1){
			    	var timel = msg.toString().split("=")[1];
			    	$('#scroller').append('<div class="myself" id="'+varId+'"><span style="display:flex;line-height:0.5rem;"><span style="padding:0">' +timel+'\" '+'</span><img class="audios" FR="0" style="width:0.35rem;height:0.5rem;" count="'+timel+'" onclick="playvoidce(this)" audio="'+msg+'" src="${path}/resource/img/images/MMMMMMMMMMMM.png">'+'</span><img src="'+myheadpath+'" class="_head" /></div>');
			    }else{
			    	msg = msg.replace(/&lt;/g, '<');
					msg = msg.replace(/&gt;/g, '>');
					msg = msg.replace(/&quot;/g, '"');
			        $('#scroller').append('<div class="myself" id="'+varId+'"><span>' + msg + '</span><img src="'+myheadpath+'" class="_head"/></div>');
			    };
			        ckInfo1();
			        if($("#scroller").height()<$(window).height()*0.8){
						$(".poles").remove();
						myScroll.refresh();
						return;
					} 
			        myScroll.refresh();
				    scrollToP(varId); 
		}; 
		function log4(msg,dur,isimg) {
			var FT =imgFt(msg)//判断是否为图片
			var varId=yzm();
		    if(isimg/* msg.toString().indexOf('?id=ppp')!=-1 *//* ||FT */){
		    				    	//判断是否是图片
		    	$('#scroller').prepend('<div class="yourFriend" id="'+varId+'"><img src="'+head_portrait+'" class="_head" /><span>' + '<img class="messageIMGS" style="max-width:3.5rem;max-height:3.5rem;" onclick="showimgs(this)" src="'+msg+'"/>' + '</span></div>');
		    	myScroll.refresh();
		    }else if(msg.toString().indexOf('mp3')!=-1){
		    	var timel = dur;
		    	$('#scroller').prepend('<div class="yourFriend" id="'+varId+'"><img src="'+head_portrait+'" class="_head" /><span style="line-height:0.5rem;display:flex;">'+'<img class="faudios" style="width:0.35rem;height:0.5rem;" FR="1"  count="'+timel+'" onclick="fplayvoidce(this)" audio="'+msg+'" src="${path}/resource/img/images/FFFFFFFFFFFF.png"><span style="padding:0">'+timel+'\" '+'</span></span></div>');
		    	myScroll.refresh();
		    }else{
		    	msg = msg.replace(/&lt;/g, '<');
				msg = msg.replace(/&gt;/g, '>');
				msg = msg.replace(/&quot;/g, '"');
				$('#scroller').prepend('<div class="yourFriend" id="'+varId+'"><img src="'+head_portrait+'" class="_head"/><span>' + msg + '</span></div>');
				myScroll.refresh();
		    }
			ckInfo2();
			myScroll.refresh();
			if(firstEnter){
				 scrollToP(varId); 
			} 
		};
		function log3(msg,dur,isimg) {
			   var varId=yzm();
			   var FT =imgFt(msg)//判断是否为 图片
			   if(isimg/* msg.toString().indexOf('?id=ppp')!=-1 *//* ||FT */){
				 //判断是否是图片
			    	$('#scroller').prepend('<div class="myself" id="'+varId+'"><span>' + '<img class="messageIMGS" style="max-width:3.5rem;max-height:3.5rem;" onclick="showimgs(this)" src="'+msg+'"/>' + '</span><img src="'+myheadpath+'" class="_head" /></div>');
			    	myScroll.refresh(); 
			   }else if(msg.toString().indexOf('mp3')!=-1){
			    	var timel =dur;
			    	$('#scroller').prepend('<div class="myself" id="'+varId+'"><span style="display:flex;line-height:0.5rem;"><span style="padding:0">' +timel+'\" '+'</span><img class="audios" style="width:0.35rem;height:0.5rem;" count="'+timel+'" FR="0" onclick="playvoidce(this)" audio="'+msg+'" src="${path}/resource/img/images/MMMMMMMMMMMM.png">'+ '</span><img src="'+myheadpath+'" class="_head" /></div>');
			    	myScroll.refresh(); 
			   }else{
			    	msg = msg.replace(/&lt;/g, '<');
					msg = msg.replace(/&gt;/g, '>');
					msg = msg.replace(/&quot;/g, '"');
			$('#scroller').prepend('<div class="myself" id="'+varId+'"><span>' + msg + '</span><img src="'+myheadpath+'" class="_head"/></div>');
			myScroll.refresh();
			};
			ckInfo1();
			myScroll.refresh();
			if(firstEnter){
				 scrollToP(varId); 
			}
		};
		function ckInfo2(){
			$('.yourFriend ._head').on('click',function(){
				window.location.href = _path+ "/wx/businessFriend/friendInfo.do?follow_user="+follow_user;	 
		     })		
		}	
		function ckInfo1(){
			$('.myself ._head').on('click',function(){
				window.location.href = _path+ "/wx/businessFriend/friendInfo.do?follow_user="+fromUserId;	 
		     })		
		}	
		
		function onConnect(status) {
			if(status == Strophe.Status.CONNECTING) {
			} else if(status == Strophe.Status.CONNFAIL) {
			} else if(status == Strophe.Status.DISCONNECTING) {
				setTimeout(function() {
					var jid = jid;
					connection.connect(jid, null, onConnect);
				}, 1000);
			} else if(status == Strophe.Status.DISCONNECTED) {
				$('#connect').get(0).value = 'connect';
			} else if(status == Strophe.Status.CONNECTED) {
				connected=true;
			    historyTalk(stamp);
				connection.addHandler(onMessage1, null, 'message', null, null, null);
				connection.send($pres().tree())
				keepOling();
			}
		};
		 //判断是否是最后一条信息
		 var lastTime = 0
		 var lastTime1 = 0
		 function historyTalk(stamp1){
			 var ssss = "";
			 if(stamp1!=""){
				 var time = Number(stamp1.split('.')[1].split("Z")[0])-100000+"Z"
			     ssss = stamp1.slice(0,stamp1.indexOf('.')+1)+time
			 }
			 connection.mam.query(jid, {
					"before":"",
					"with": toId,
					"end":ssss,
					"max": "10", 
					onMessage: function(message) {
					},
					onComplete: function(response) {	
						if(arr.length){
							firstfalg=false;
							arr.reverse();
							for(var index = 0;index < arr.length;index++){
								var sjstr = $(arr[index]).find("forwarded delay").attr('stamp');
								var from = $(arr[index]).find("forwarded message").attr('from').split("@")[0];
								var text = $(arr[index]).find("forwarded message body").text();
								var isimg=false;
								if($(arr[index]).find("contentType").text()=='image'){
									isimg=true;
								}
								if(from ==fromUserId) {
							          log3(text,$(arr[index]).find("duration").text(),isimg);
						        } else {
							           log4(text,$(arr[index]).find("duration").text(),isimg)
						        }
					       	}
					    	stamp = $(arr[arr.length-1]).find("forwarded delay").attr('stamp');
						};
						myScroll.refresh();
					}
		});

	}
/**
 * 下拉刷新 （自定义实现此方法）
 * myScroll.refresh();  // 数据加载完成后，调用界面更新方法
 */
		function pullDownAction(stamp) {
			 if(!firstfalg){
				setTimeout(function() { // <-- Simulate network congestion, remove setTimeout from production!
					generatedCount++
					//console.log("下拉")
					historyTalk(stamp);
					myScroll.refresh(); //数据加载完成后，调用界面更新方法 Remember to refresh when contents are loaded (ie: on ajax completion)
				}, 1); // <-- Simulate network congestion, remove setTimeout from production!
			 }
		}
		 function pullUpAction() {
				setTimeout(function() { // <-- Simulate network congestion, remove setTimeout from production!
					myScroll.refresh(); // 数据加载完成后，调用界面更新方法 Remember to refresh when contents are loaded (ie: on ajax completion)
				}, 1000); // <-- Simulate network congestion, remove setTimeout from production!
			}
		/**
		 * 初始化iScroll控件
		 */
		function loaded() {
			myScroll = new iScroll('wrapper', {
				scrollbarClass: 'myScrollbar',
				/* 重要样式 */
				useTransition: false,
				/* 此属性不知用意，本人从true改为false */
				onRefresh: function() {		
				},
				onScrollMove: function() {
					if(this.y>30){
						$('#scroller').get(0).className = "down"
					}
					if(this.y<-30){
						$('#scroller').attr('up','up')
					}
				},
				onScrollStart: function () {
					firstEnter=false;
			    },
				onScrollEnd: function() {
					if(myScroll.y==myScroll.maxScrollY){
						scrolled=true;
					}else{
						scrolled=false;
					}
					var position='1';
				     position=this.y;
				     if(this.y!=0){
				    	 return;
				     } 
					 generatedCount++
					 if($('#scroller').get(0).className.match('down')&& arr){
						$('#scroller').attr('class',"")
						if(generatedCount ==1){
							 //var stamp = $(arr[0]).find("forwarded delay").attr('stamp');
						}else{
						    //var stamp = $(arr[arr.length-1]).find("forwarded delay").attr('stamp');
						}
						 //console.log(stamp);
						 arr=[]
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
				
		var arr = [];
		//去重数组
		var removeSame=[];
		function onMessage1(msg) {
			
			arr.push(msg);
			var audiotime=$(msg).find("duration").text();
			var to = msg.getAttribute('to');
			var from = msg.getAttribute('from');
			var type = msg.getAttribute('type');
			var elems = msg.getElementsByTagName('body');
			var fromid = from.split("@")[0];
			if (fromid == follow_user && type == "chat" && elems.length > 0) {
				var body = elems[0];
				var isimg=false;
				if($(msg).find("contentType").text()=='image'){
					isimg=true;
					//console.log("图片");
				}
			    log2(Strophe.getText(body),$(msg).find("duration").text(),isimg);//第三个参数 图片专用		
			}else if (elems.length > 0){
				//提示消息
				if (type == "groupchat" && elems.length > 0) {
					/* var body = elems[0];
					var bodytext=Strophe.getText(body); */
					
					if(bodytext.toString().indexOf('?id=ppp')!=-1){
					}else{
						/* var message= bodytext.replace(/&lt;/g, '<');
				        message = message.replace(/&gt;/g, '>');
				        message = message.replace(/&quot;/g, '"'); */
				        /*弹出  */ 
					}
					
				}else if (type == "chat" && elems.length > 0){
					/* var body = elems[0];
					var bodytext=Strophe.getText(body); */
					
					if(bodytext.toString().indexOf('?id=ppp')!=-1){
						 /*弹出  */ 
					}else{
						/* var message= bodytext.replace(/&lt;/g, '<');
				        message = message.replace(/&gt;/g, '>');
				        message = message.replace(/&quot;/g, '"'); */
				        /*弹出  */   
					}
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
		};
		function keepOling() {
			//console.log("online");
			connection.sendIQ($iq({
				to : _im_path,
				from : jid,
				type : "get"
			}).c('ping', {
				xmlns : "urn:xmpp:ping"
			}));
			setTimeout(keepOling, 22000);	
		}
		function sendMsg(toId, fromId, msg,type,dur) {
			var ctype='';
			var reply='';
			 if(type==1){
				var time=dur;
				ctype='audio';
				reply = $msg({
					to : toId,
					from : fromId,
					type : 'chat',
					id:Math.random()
				}).c('body').t(msg).up().c('contentType').t(ctype).up().c('duration').t(dur.toString()).up().c("nickname").t(myname).up().c("head").t(myheadpath);;
				connection.send(reply.tree());
				log1(msg,dur);
			}else if(type==2){
				ctype='text';
				reply = $msg({
					to : toId,
					from : fromId,
					type : 'chat',
					id:Math.random()
				}).c('body').t(msg).up().c('contentType').t(ctype).up().c("nickname").t(myname).up().c("head").t(myheadpath);;
				connection.send(reply.tree());
				log1(msg);
			}
		}
	$(".jihao").on("click", function(e) {
		e.stopPropagation();
		$("#facebox").remove();
		if($(this).hasClass('on')){
			$(this).removeClass('on')
			$('.hidePh').css({"display": "none"})
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
		$('.hidePh').css({
			"display": "none"
		})
		sendMsgHide()
	});
	$('#t-content').on('keyup', function() {
		sendMsgHide()
	});
	function sendMsgHide(){
		var les =$.trim($('#t-content').val()).length;
		if (les > 0) {
			$(".jihao").css({
				'display': 'none'
			});
			$(".send").css({
				'display': 'block'
			});
		} else {
			$(".jihao").css({
				'display': 'block'
			});
			$(".send").css({
				'display': 'none'
			});
		};
	}
	 $('.send').bind('click', function(e) {
		 e.stopPropagation();
		 $('.hidePh').css('display','none');
		 $(".jihao").css({'display': 'block'});
               $(".send").css({'display': 'none'});
			 toId = toId;
			var fromId = jid;
			var msg = replace_em($('#t-content').val());
			//传递本人昵称
			sendMsg(toId, fromId, msg,2);
		    $('#t-content').val('')
	});
	 
	 //查看聊天信息
	 $('.sz').on('click',function(){
		 window.location.href = _path+ "/wx/businessFriend/talkInfo.do?follow_user="+follow_user;
	 })
	 
	 //公用
	$('.emotion').qqFace({
		id : 'facebox',
		assign : 't-content',
		path : '${path}/resource/img/arclist/' //表情存放的路径
	});
	function replace_em(str) {
		str = str.replace(/\</g, '&lt;');
		str = str.replace(/\>/g, '&gt;');
		str = str.replace(/\n/g, '<br/>');
		str = str.replace(/\[em_([0-9]*)\]/g,'<img class="emotioneee" src="${path}/resource/img/arclist/$1.gif" border="0" />');
		return str;
	}
	
	//专门用于发送图片
	
	function sendPicture(toId, fromId, msg) {
		var reply = $msg({
			to : toId,
			from : fromId,
			type : 'chat',
			id:Math.random()
		}).c('body', {
			class : 'tupians'
		}).t(msg).up().c('contentType').t('image').up().c("nickname").t(myname).up().c("head").t(myheadpath);;
		connection.send(reply.tree());
		log1(msg);
		$(".jihao").click();
	} 
	
	
	function doSelectPic1() {
		zdy_chooseImg(function(data,res){
			//console.log(data)
    	 	if(res.code == 0){
    	 		toId = toId;
				fromId = jid;
				var msg = data.img_url+'?id=ppp';
				sendPicture(toId, fromId, msg);
    	 	}
    	 },"chat");
	} 
   
	//滚动到指定位置
	  function scrollToP(obj){
		 /*  myScroll.refresh(); */
			 myScroll.scrollToElement(document.querySelector('#scroller #'+obj),200);
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
		//alert("开始录音。。。");
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
		    					/*  playMedia(mediaId); */
		    					var fromId = jid;
		    				    sendMsg(toId, fromId,mediaId+'?t='+t_seconds,1,t_seconds); 
		    				   
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
		 if(!cookieRID &&  !cookieSID){
			 //console.log(cookieRID+'   '+cookieSID);
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
			    historyTalk(stamp);
			    connection.addHandler(onMessage1, null, 'message', null, null, null);
				connection.xmlOutput = function (e) {
			             var RID = $(e).attr('rid');
			             var SID = $(e).attr('sid');
			             sessionStorage.setItem('Rid',RID);
			             sessionStorage.setItem('Sid',SID);
			             //console.log('RID=' + RID + ' [SID=' + SID + ']');     
			    };   
		 }else{
			 //断开重新连接
			 console.log('连接断开');

		 }
		 
	}
					
	</script>
</body>
</html>


