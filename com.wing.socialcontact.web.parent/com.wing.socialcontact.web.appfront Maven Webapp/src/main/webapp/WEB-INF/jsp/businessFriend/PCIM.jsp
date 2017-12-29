<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="keywords" content="好友信息" />
<title>PC端IM</title>
	<style>
	   .clear{
	      clear:both;
	      height:0;
	      width:0;
	      line-height:0;
	   }
	    .PCIM-box{
	        box-sizing:border-box;
	        width:1280px;
	        height:100%;
	        border:1px solid #c8c8c8;
	        margin:0 auto;
	        font-size:20px;
	        border-radius:10px;
	        position:relative;
	        font-family:"Microsoft YaHei","微软雅黑",STHeiTi,MingLiu;
	    }
	    .PCIM-LEFT{
	       box-sizing:border-box;
	       width:22%;
	       height:100%;
	       background:#d8d8d8;
	       float:left;
	       position:relative;
	      overflow-y:auto;
	      }
	    .PCIM-RIGHT{
	       box-sizing:border-box;
	       height:100%;
	       width:78%;
	       background:#fbfbfb;
	       float:right;
	       position:relative;
	    }
	     .PCIN-HEAD{
	        height:13%;
	        width:100%;
	        background:#ebebeb;
	        position:relative;
	        line-height:100px;
	    }
	    .PCIN-BODY{
	        height:54%;
	        width:100%;
	        overflow-y:scroll
	    }
	    .getChatHis{
	       height:6.5%;
	       line-height:50px;
	       text-align:center;
	       cursor:pointer;
	       color:grey;
	    }
	    .PCIN-FOOTER{
	        width:100%;
	        border-top:3px solid #f4f4f4;
	      /*   height:195px; */
	    }
	    .PCIM-FHEAD{
	        height:6.5%;
	        border-bottom:1px solid #f4f4f4;
	    }
	    #PCIMTEXT{
	       width:100%;
	       max-width:100%;
	       max-height:80px;
	       height:10%;
	       border:none;
	       background:#fbfbfb;
	       font-size:20px;
	       padding:10px;
	    }
	    .sentzone{
	       width:100%;
	       height:10%;
	       text-align:right;
	    }
	    #sendText{
	       font-size:20px;
	       height:40px;
	       line-height:20px;
	       color:white;
	       background:#5fb878;
	       padding-left:24px;
	       padding-right:24px;
	       margin-top:15px;
	       margin-right:30px;
	    }
	    .IP-ITEM{
	       display:block;
	       height:35px;
	       width:35px;
	       float:left;
	       margin-top:5px;
	       margin-left:25px;
	    }
	    #FACES{
	       background:url(${path}/resource/img/images/imFace.png) no-repeat center;
	       background-size:100%;
	    }
	    #PHOTO{
	        background:url(${path}/resource/img/icons/photo_03.png) no-repeat center;
	        background-size:100%;
	    }
	    .usicon{
	        border-radius:50%;
	        vertical-align:middle;
	    }
	    #userIcon{
	       height:70px;
	       margin-left:20px;
	       vertical-align:middle;
	       width:70px;
	    }
	    #currentName{
	       display:inline-block;
	       height:100px;
	    }
	    .friendItem{
	       height:50px;
	       line-height:50px;
	       position:relative;
	       width:96%;
	       margin:0 auto;
	       padding-bottom:5px;
	       padding-top:5px;
	    }
	    .FListIcon{
	        height:100%;
	        width:50px;
	        float:left;
	    }
	    #flist-box{
	        margin-top:30px;
	    }
	    #flist-contact{
	       margin-top:30px;
	     
	    }
	    .FListName{
	         height:50px;
	         width:160px;
	         line-height:50px;
	         display:inline-block;
	         float:left;
	         padding-left:5px;
	         white-space: nowrap;
             overflow: hidden;
             text-overflow: ellipsis;
	    }
	    .leftHeadtab{
	          width:100%;
	          height:50px;
	    }
	    .leftHeadtab span{
	         width:100%;
	         float:left;
	         height:50px;
	         line-height:50px;
	         text-align:center;
	    }
	    .chosen{
	       border-bottom:2px solid green;
	    }
	    .MSG{
	      width:96%;
	      padding-top:10px;
	      padding-bottom:10px;
	     /*  border:1px solid blue; */
	      margin:0 auto;
	      /* margin-top:10px; */
	      position:relative;
	    }
	    
	   .yourfriends .talkingIcon{
	       height:80px;
	       width:80px;
	       float:left;
	    }
	   .yourfriends .chatText{
	       float:left;
	       border-radius:5px;
	       max-width:85%;
	       padding-left:2%;
	       margin-top:30px;
	       background:url(${path}/resource/img/images/PCMSG1.png) no-repeat left top /*  #e1e2e2 */;
	       background-size:30px 30px;
	       background-position-y:5px;
	    }
	   .yourfriends .textCore{
	        background:#e2e2e2;
	        padding-top:15px;
	        padding-bottom:15px;
	        border-radius:5px;
	        padding-left:10px;
	        padding-right:10px;
	    }
	    /*本人信息样式  */
	    .myself .talkingIcon{
	       height:80px;
	       width:80px;
	       float:right;
	    }
	   .myself .chatText{
	       float:right;
	       border-radius:5px;
	       max-width:85%;
	       padding-right:2%;
	       margin-top:30px;
	       background:url(${path}/resource/img/images/PCMSG2.png) no-repeat right top /*  #e1e2e2 */;
	       background-size:30px 30px;
	       background-position-y:5px;
	    }
	   .myself .textCore{
	        background:#5fb878;
	        padding-top:15px;
	        padding-left:10px;
	        padding-bottom:15px;
	        border-radius:5px;
	        padding-right:10px;
	    }
	    #select{
	      text-indent:20px;
	    }
	    .audioIm{
	        height:40px;
	        vertical-align:middle;
	    }
	    .maudioIm{
	        height:50px;
	        vertical-align:middle;
	    }
	    #audio_id{
	       display:none;
	    }
	    .imgMsg{
	       max-height:300px;
	       max-width:300px;
	    }
	    .unreads{
	       display:inline-block;
	       height:30px;
	       width:30px;
	       text-align:center;
	       border-radius:50%;
	       background:#6fc4f0; 
	       line-height:30px;
	       font-size:12px;
	       color:white;
	       margin-top:10px;
	       display:none;
	    }
	    #haveNewMsg{
	       position:absolute;
	       right:20px;
	       top:25px;
	       width:400px;
	    }
	  #haveNewMsg  .msgTip{
	     height:50px;
	     width:50px;
	     border-radius:50%;
	     border:none;
	     float:left;
	  }
	  #haveNewMsg .msgTipbody{
	     height:50px;
	     line-height:50px;
	     float:left;
	     width:350px;
	     white-space:nowrap; overflow:hidden; text-overflow:ellipsis;
	     overflow:hidden;
	  }
	</style>
</head>
<body>
	<div class="PCIM-box">
	   <div class="PCIM-LEFT">
	         <div class="leftHeadtab">
               <!--  <span  id="myfriend-list">好友列表</span> -->
                <span class="chosen" id="recentContact">最近联系</span>
                <div class="clear"></div>
	         </div>
	         
	         <div id="flist-box">
		         <!-- 好友列表 -->
		     </div>
		     <div id="flist-contact" style="display:block">
		     
		     </div>    
	   </div>
	   
	   <div class="PCIM-RIGHT">
	       <div class="PCIN-HEAD">
	           <img class="usicon" id="userIcon" src="${path}/resource/img/images/haoyou.png"/>
	           <span id="currentName" talkingID="0">霍思涵</span>
	           <div id="select" style="display:none">您还没有选择联系人</div>
	           <div id="haveNewMsg" style="display:none">
	               <%-- <img  class="msgTip" src="${path}/resource/img/images/haoyou.png"/>
	               <div class="msgTipbody">nihao</div>
	               <br style="clear:both"/> --%>
	           </div>
	       </div>
	       <audio id="audio_id" src=""></audio>
	       <div class="getChatHis" ><span id="getChatHis" style="display:none">点击获取聊天记录</span></div>
	       <div class="PCIN-BODY" id="talkbody">
	           <!-- 聊天显示区域 -->
	       </div>
	       <div class="PCIN-FOOTER">
	           <div class="PCIM-FHEAD">
	              <span id="FACES"  class="IP-ITEM emotion"></span>
	              <span id="PHOTO" class="IP-ITEM"></span>
	               <form id="formData" action="${path}/im/m/upload.do" enctype="multipart/form-data" method="post">
	                <input type="file" style="display:none" onchange="upImg()"  name="imgurl_file"  multiple="multiple" id="sendPIC"/>
	              </form>
	              <div class="clear"></div>
	           </div>
	           <textarea id="PCIMTEXT"></textarea>
	           <div class="sentzone">
	                 <input id="sendText" type="button" value="发送消息"/>
	           </div>
	       </div>
	   </div>
	   <div class="clear"></div>
	</div>
	<script type="text/javascript" src="${path}/resource/js/jquery.PCqqFace.js?v=${sversion}" charset="utf-8"></script>
	<script type="text/javascript">	      	 
	   //PC聊天主模块
	   //头像地址 myheadpath；
 var PCmsg=(function(){
	 var myheadpath = '<%=headpic%>';
	 var myname = '<%=nickname%>' ;
     var myImId = ${sessionScope.me.id}+'@'+_im_path;
     var BOSH_SERVICE = _im_path_http+":5280/http-bind/";
     var connection=null;
     var connected=false;
     var friendsID="";
     var arr=[];//聊天记录临时存储体
     var stamps='';
		//滚动
	     function scrollTop(){
	    	 $("#talkbody").animate({  
                 scrollTop: document.getElementById("talkbody").scrollHeight-$("#talkbody").innerHeight()+300  
             }, 500); 
		   };
        var pcmsg=function(){
		   this.xmppID=myImId;
		   this.friendsId='';
		   this.myphoto=myheadpath;
		   this.myid=myImId;
		   this.stamp='';
	   };
	     pcmsg.prototype={
			   connectIM:function(){
				   connectXMPP();
				   return this;
			    },
			   getMyId:function(){
				   return this.myid;
			   },
			   setStamp:function(stamp){
				   this.stamp=stamp;
				   stamps=stamp;
				   return this;
			   },
			   getchatHistory:function(){
				   historyTalk(stamps,myImId,friendsID);
				   return this;
			   },
			   getRecentInTouch:function(){
						connection.sendIQ($iq({
							to: _im_path,
							id:new Date(),
							type: "get"
						}).c('recent-contact', {
							xmlns: 'urn:xmpp:recent-contact'
						}), function(iq){
							/* console.log(iq);  */
				             var data = $(iq).find('item');
				             var str='';
				             $("#flist-contact").empty();
				            
				             $.each(data,function(i){/* onclick="changecolor($(this))" */
				            	 if($(data[i]).attr('jid').split("@")[1].indexOf("group")==-1){
				            		 var fids=$(data[i]).attr("jid").split("@")[0];
				            		 var head=$(data[i]).attr("head_portrait");
				            		 var nickname=$(data[i]).attr("nickname");
				            		 var session=sessionStorage.getItem("unread"+fids);
						             if(!session){
						            	 
						             
					            		 $("#flist-contact").append(
							            		 '<div class="friendItem recents" id="recent'+fids+'" friend_user='+fids+'  names="'+nickname+'" headp="'+head+'">'+
							            		   '<img class="usicon FListIcon" src="'+head+'"/>'+
							            		   '<div class="FListName" >'+nickname+'</div>'+
							            		   '<span id="unread'+fids+'" class="unreads">0</span>'+
							         		     '</div>');
							             
							             $("#recent"+fids).bind("click",function(){
							            	 recentF($(this));
							             });
						             }else{
						            	 $("#flist-contact").append(
							            		 '<div class="friendItem recents" id="recent'+fids+'" friend_user='+fids+'  names="'+nickname+'" headp="'+head+'">'+
							            		   '<img class="usicon FListIcon" src="'+head+'"/>'+
							            		   '<div class="FListName" >'+nickname+'</div>'+
							            		   '<span id="unread'+fids+'" style="display:inline-block;" class="unreads">'+session+'</span>'+
							         		     '</div>');
							             
							             $("#recent"+fids).bind("click",function(){
							            	 recentF($(this));
							             });
						             }
				            	 }
				                 
				             });
				             
				             $(".recents").bind("click",function(){
				            	 recentF($(this));
				             });
						});
					  
			   },
			   fistEter:function(){
					 $("#userIcon").hide();
					 $("#currentName").hide();
					 $("#select").show();
				      return this;
			   },
			   setfriendsId:function(id){
				   this.friendsId=id;
				   friendsID=id;
				   return this
			   },
			   clearMSGlist:function(){
				   //请求聊天记录钱 清除 存在arr中的信息 ，避免重复
				   arr=[];
				   return this;
			   },
			   sendText:function(text,to,from){
				   var msg = replace_em(text)
				   reply = $msg({
						to : to+'@'+_im_path,
						from : from,
						type : 'chat',
						id:Math.random()
					}).c('body').t(msg).up().c('contentType').t('text').up().c("nickname").t(myname).up().c("head").t(myheadpath);
					connection.send(reply.tree());
				   return this;
			   },
			   sendPic:function(text,to,from){
				   var reply = $msg({
						to : to+'@'+_im_path,
						from : from,
						type :'chat'
					}).c('body').t(text).up().c('contentType').t('image').up().c("nickname").t(myname).up().c("head").t(myheadpath);;
					connection.send(reply.tree());
					return this;
			   },
			   pcLog1:function(msg){
			     if(msg.toString().indexOf('?id=ppp')!=-1){
			    	 $("#talkbody").append('<div class="MSG myself" ><img class="usicon talkingIcon" src="'+myheadpath+'"/><div class="chatText"><div class="textCore"><img class="imgMsg" src="'+msg+'"/></div></div><div class="clear"></div></div>');
			     }else{
			    	    msg =replace_em(msg);
						
			    	 $("#talkbody").append('<div class="MSG myself"><img class="usicon talkingIcon" src="'+this.myphoto+'"/><div class="chatText"><div class="textCore">'+msg+'</div></div><div class="clear"></div></div>');
			     }
			     
			     scrollTop();
			     return this;
			   },
			   pcLog2:function(msg){
				   if(msg.toString().indexOf('?id=ppp')!=-1){
				    	 $("#talkbody").append('<div class="MSG yourfriends" ><img class="usicon talkingIcon" src="'+$("#userIcon").attr("src")+'"/><div class="chatText"><div class="textCore">'+msg+'</div></div><div class="clear"></div></div>');
				     }else{
				    	    msg = msg.replace(/&lt;/g, '<');
							msg = msg.replace(/&gt;/g, '>');
							msg = msg.replace(/&quot;/g, '"');
				    	 $("#talkbody").append('<div class="MSG yourfriends"><img class="usicon talkingIcon" src="'+$("#userIcon").attr("src")+'"/><div class="chatText"><div class="textCore">'+msg+'</div></div><div class="clear"></div></div>');
				     }
				   //若不在底部 就不滑动
				   if($("#talkbody").scrollTop() < document.getElementById("talkbody").scrollHeight-$("#talkbody").innerHeight()-200){
					   return; 
				   }
				   scrollTop();
			   },
			   pclogAudio:function(msg,time){
				   $("#talkbody").append('<div class="MSG yourfriends"><img class="usicon talkingIcon" src="'+$("#userIcon").attr("src")+'"/><div class="chatText"><div class="textCore"><img class="audioIm" FR="1" src="${path}/resource/img/images/FFFFFFFFFFFF.png" onclick="playAudio($(this))" times="'+time+'" playurl="'+msg+'"/>'+time+'\"'+'</div></div><div class="clear"></div></div>');
				   if($("#talkbody").scrollTop() < document.getElementById("talkbody").scrollHeight-$("#talkbody").innerHeight()-100){
					   return; 
				   }
				   scrollTop();
			   },
			   pclogImg:function(msg){
				   $("#talkbody").append('<div class="MSG yourfriends"><img class="usicon talkingIcon" src="'+$("#userIcon").attr("src")+'"/><div class="chatText"><div class="textCore"><img class="imgMsg" src="'+msg+'"/></div></div><div class="clear"></div></div>');
				   if($("#talkbody").scrollTop() < document.getElementById("talkbody").scrollHeight-$("#talkbody").innerHeight()-100){
					   return; 
				   }
				   scrollTop();
			   },
			   //打印朋友的历史记录
			   pcHisFlogImg:function(msg){
				   $("#talkbody").prepend('<div class="MSG yourfriends"><img class="usicon talkingIcon" src="'+$("#userIcon").attr("src")+'"/><div class="chatText"><div class="textCore"><img class="imgMsg" src="'+msg+'"/></div></div><div class="clear"></div></div>');
			   },
			   pcHisFlogAudio:function(msg,time){
				   $("#talkbody").prepend('<div class="MSG yourfriends"><img class="usicon talkingIcon" src="'+$("#userIcon").attr("src")+'"/><div class="chatText"><div class="textCore"><img class="audioIm" FR="1" src="${path}/resource/img/images/FFFFFFFFFFFF.png" onclick="playAudio($(this))" times="'+time+'" playurl="'+msg+'"/>'+time+'\"'+'</div></div><div class="clear"></div></div>');
			   },
               pcHisFlogText:function(msg){
            	    msg = msg.replace(/&lt;/g, '<');
					msg = msg.replace(/&gt;/g, '>');
					msg = msg.replace(/&quot;/g, '"');
		    	   $("#talkbody").prepend('<div class="MSG yourfriends"><img class="usicon talkingIcon" src="'+$("#userIcon").attr("src")+'"/><div class="chatText"><div class="textCore">'+msg+'</div></div><div class="clear"></div></div>');
			   },
			   //我的历史记录打印函数
               pcHisMlogImg:function(msg){
            	   $("#talkbody").prepend('<div class="MSG myself"><img class="usicon talkingIcon" src="'+myheadpath+'"/><div class="chatText"><div class="textCore"><img class="imgMsg" src="'+msg+'"/></div></div><div class="clear"></div></div>');
			   },
			   pcHisMlogAudio:function(msg,time){
				   $("#talkbody").prepend('<div class="MSG myself"><img class="usicon talkingIcon" src="'+myheadpath+'"/><div class="chatText"><div class="textCore">'+time+'\"'+'<img class="maudioIm" FR="0" src="${path}/resource/img/images/MMMMMMMMMMMM.png" onclick="mplayAudio($(this))" times="'+time+'" playurl="'+msg+'"/>'+'</div></div><div class="clear"></div></div>');
			   },
               pcHisMlogText:function(msg){
            	    msg = msg.replace(/&lt;/g, '<');
					msg = msg.replace(/&gt;/g, '>');
					msg = msg.replace(/&quot;/g, '"');
            	   $("#talkbody").prepend('<div class="MSG myself"><img class="usicon talkingIcon" src="'+myheadpath+'"/><div class="chatText"><div class="textCore">'+msg+'</div></div><div class="clear"></div></div>');
			   }
	      }
	     
			//抖动提示
			 jQuery.fn.shake = function (intShakes /*Amount of shakes*/, intDistance /*Shake distance*/, intDuration /*Time duration*/) {
			      this.each(function () {
			          var jqNode = $(this);
			          jqNode.css({ position: 'relative' });
			           for (var x = 1; x <= intShakes; x++) {
			              jqNode.animate({ left: (intDistance * -1) }, (((intDuration / intShakes) / 4)))
			              .animate({ left: intDistance }, ((intDuration / intShakes) / 2))
			              .animate({ left: 0 }, (((intDuration / intShakes) / 4)));
			          }
			     });
			    return this;
			 } 
			
			//PC信息监听
			var PCIM =new pcmsg();
			function connectXMPP(){
		    	 //获取会话ID
		    	 var cookieRID=sessionStorage.getItem('PCRid');
				 var cookieSID=sessionStorage.getItem('PCSid');
		    	 connection = new Strophe.Connection(BOSH_SERVICE);
		    	 if(!cookieRID && !cookieSID){
		    		 connection.connect(myImId, null, onConnect);
		    		 connection.xmlOutput = function (e) {
			             var RID = $(e).attr('rid');
			             var SID = $(e).attr('sid');
			             sessionStorage.setItem('PCRid',RID);
			             sessionStorage.setItem('PCSid',SID);
			             console.log('RID=' + RID + ' [SID=' + SID + ']');
			      };
		    		
		    	 }else if(cookieRID && cookieSID){
		    		 connection.attach(myImId,cookieSID,parseInt(cookieRID)+1,attachingCallback);
		    	 } 
		      }
			  //首次登录连接监听事件；
			  function onConnect(status) {
				  
					if (status == Strophe.Status.CONNECTED) {
						console.log("连接成功，可以开始聊天了！");
						connected = true;
						connection.send($pres().tree());
						connection.addHandler(onMessage, null,'message', null, null, null);
						PCIM.getRecentInTouch();
						 
					}
				};
				
				//attach回调函数
				function attachingCallback(e){
					console.log(e);
					 if(e == Strophe.Status.ATTACHED){ 
						    connected = true;
						    /* historyTalk(stamp); */
						    console.log('二次连接成功'); 
						    connection.addHandler(onMessage, null,'message', null, null, null);
						    PCIM.getRecentInTouch();
							connection.xmlOutput = function (e) {
						             var RID = $(e).attr('rid');
						             var SID = $(e).attr('sid');
						             sessionStorage.setItem('PCRid',RID);
						             sessionStorage.setItem('PCSid',SID);
						             console.log('RID=' + RID + ' [SID=' + SID + ']');     
						    };
					 }else{
						 //断开重新连接
						 console.log('连接断开');
						 sessionStorage.removeItem('PCRid');
			             sessionStorage.removeItem('PCSid');
						 connectXMPP();
					 }
				}
				//替换表情
				$('#FACES').pcqqFace({
					id : 'facebox',
					assign : 'PCIMTEXT',
					path : '${path}/resource/img/arclist/' //表情存放的路径
				});
				function replace_em(str) {
						str = str.replace(/\</g, '&lt;');
						str = str.replace(/\>/g, '&gt;');
						str = str.replace(/\n/g, '<br/>');
						str = str.replace(/\[em_([0-9]*)\]/g,'<img class="emotioneee" src="${path}/resource/img/arclist/$1.gif" border="0" />');
						return str;
					}
				
			 function onMessage(msg){
				console.log(msg);
				arr.push(msg);
				var contentType=$(msg).find("contentType").text();
				var to = msg.getAttribute('to');
				var from = msg.getAttribute('from');
				var type = msg.getAttribute('type');
				var elems = msg.getElementsByTagName('body');
				var fromid = from.split("@")[0];
				var headp=$(msg).find("head").text();
				var nickname=$(msg).find("nickname").text();
				if(fromid==friendsID && type == "chat" && elems.length > 0){
					var body = elems[0];
					var time= $(msg).find("duration").text()
					if(contentType=="text"){
						PCIM.pcLog2(Strophe.getText(body));
					}else if(contentType=="audio"){
						PCIM.pclogAudio(Strophe.getText(body),time);
					}else if(contentType=="image"){
						PCIM.pclogImg(Strophe.getText(body));
					}		
				}else if(fromid != friendsID && type == "chat" && elems.length > 0){
					$("#unread"+fromid).css("display","inline-block");
					if(!sessionStorage.getItem("unread"+fromid)){
						sessionStorage.setItem("unread"+fromid,1);
						$("#unread"+fromid).text(1);
						$("#recent"+fromid).shake(10, 10, 300);
					}else{
						var count=parseInt(sessionStorage.getItem("unread"+fromid));
						$("#unread"+fromid).text(count+1);
						sessionStorage.setItem("unread"+fromid,count+1);
						$("#recent"+fromid).shake(10, 10, 300);
					}
					var body = elems[0];
					$("#haveNewMsg").html('<img  class="msgTip" src="'+headp+'"/><div class="msgTipbody">'+nickname+' : '+Strophe.getText(body)+'</div><br style="clear:both"/>');
					$("#haveNewMsg").fadeIn("fast",function(){
						setTimeout(function(){
							$("#haveNewMsg").fadeOut(200)
						},600);
					});
					if(!$("#recent"+fromid).hasClass("friendItem")){
						 sessionStorage.setItem("unread"+fromid,1);
						 $("#flist-contact").prepend(
			            		 '<div class="friendItem recents" id="recent'+fromid+'" friend_user='+fromid+'  names="'+nickname+'" headp="'+headp+'">'+
			            		   '<img class="usicon FListIcon" src="'+headp+'"/>'+
			            		   '<div class="FListName" >'+nickname+'</div>'+
			            		   '<span id="unread'+fromid+'" style="display:inline-block" class="unreads">1</span>'+
			         		     '</div>');
			             
			             $("#recent"+fromid).bind("click",function(){
			            	 recentF($(this));
			             });
					};
				}
		 		return true;
		    };
		    
		    function recentF(obj){
		     var fids=obj.attr("friend_user");
		    /*  console.log(fids); */
   			 $("#getChatHis").show();
   			 PCIM.setfriendsId(obj.attr("friend_user")).setStamp('');
        		sessionStorage.removeItem("unread"+fids);
        		$("#unread"+fids).text(0);
        		$("#unread"+fids).hide();
        		obj.css("background","#F3F3F3").siblings().css("background","#d8d8d8");
        		 $("#userIcon").attr("src",obj.attr("headp"));
        		 $("#currentName").text(obj.attr("names"));
        		 $("#currentName").attr("talkingID",obj.attr("friend_user")); 
        		obj.addClass("THIS").siblings().removeClass("THIS");
        		 $("#userIcon").show();
        		 $("#currentName").show();
        		 $("#select").hide();
        		 $("#talkbody").empty();
        		 PCIM.clearMSGlist().getchatHistory();
   		 }
		  //历史记录
			 function historyTalk(stamp1,myid,friendid){
				 var ssss = "";
				 if(stamp1!=""){
					 var time = Number(stamp1.split('.')[1].split("Z")[0])-100000+"Z"
				     ssss = stamp1.slice(0,stamp1.indexOf('.')+1)+time
				 }
				 connection.mam.query(myid, {
						"before":"",
						"with": friendid+'@'+_im_path,
						"end":ssss,
						"max": "6", 
						onMessage: function(message) {
						},
						onComplete: function(response) {	
							if(arr.length){
								arr.reverse();
								for(var index = 0;index < arr.length;index++){
									var sjstr = $(arr[index]).find("forwarded delay").attr('stamp');
									var from = $(arr[index]).find("forwarded message").attr('from').split("@")[0];
									var text = $(arr[index]).find("forwarded message body").text();
									var time = $(arr[index]).find("duration").text();
									if(from == friendsID) {
										  if($(arr[index]).find("contentType").text()=='image'){
											  PCIM.pcHisFlogImg(text);
										  }else if($(arr[index]).find("contentType").text()=='text'){
											  PCIM.pcHisFlogText(text);
										  }else if($(arr[index]).find("contentType").text()=='audio'){
											  PCIM.pcHisFlogAudio(text,time);
										  }
							        }else{
							        	if($(arr[index]).find("contentType").text()=='image'){
							        		  PCIM.pcHisMlogImg(text);
										  }else if($(arr[index]).find("contentType").text()=='text'){
											  PCIM.pcHisMlogText(text);
										  }else if($(arr[index]).find("contentType").text()=='audio'){
											  PCIM.pcHisMlogAudio(text,time); 
										  } 
							        }
						       	}
						    	stamps = $(arr[arr.length-1]).find("forwarded delay").attr('stamp');	
							};
						}
			});
		}
		  
		  
		  
		  return PCIM;
  }());
 //链接服务器
	PCmsg.connectIM().fistEter();

 

//好友列表点击样式变化



$("#sendText").click(function(){
	if($("#currentName").attr("talkingID")==0){
		layer.msg('您还没有选择联系人');
		return;
	}
	if(!$("#PCIMTEXT").val()){
		layer.msg('不可以发送空信息哦');
		return;
	}
	var text=$("#PCIMTEXT").val();
	PCmsg.sendText(text,PCmsg.friendsId,PCmsg.getMyId()).pcLog1(text);
	$("#PCIMTEXT").val('');
})
 
var t_seconds=0;
var tvoice='';
var thisVoice='';
var timelength=0;
function playAudio(obj){
	stopCount();
	t_seconds=0;
	thisVoice=$(obj);
	timelength=$(obj).attr("times");
	var audio=$("#audio_id")[0];
	audio.src=obj.attr("playurl");
	$(obj).attr("src","${path}/resource/img/images/msgFromF.gif"); 
	$(".audioIm").not($(obj)).attr("src","${path}/resource/img/images/FFFFFFFFFFFF.png");
	audio.play();
	timedCount();
}

function mplayAudio(obj){
	stopCount();
	t_seconds=0;
	thisVoice=$(obj);
	timelength=$(obj).attr("times");
	var audio=$("#audio_id")[0];
	audio.src=obj.attr("playurl");
	$(obj).attr("src","${path}/resource/img/images/msgFormM.gif"); 
	$(".maudioIm").not($(obj)).attr("src","${path}/resource/img/images/MMMMMMMMMMMM.png");
	audio.play();
	timedCount();
}

function timedCount(){  
	t_seconds++ ;
	tvoice=setTimeout("timedCount()",1000);
	if(t_seconds==timelength){
			if(thisVoice.attr("FR")=='0'){
				thisVoice.attr("src","${path}/resource/img/images/MMMMMMMMMMMM.png");
			}else{
				thisVoice.attr("src","${path}/resource/img/images/FFFFFFFFFFFF.png");
			}
		    stopCount();
		    timelength=0;
		    thisVoice='';
		    tt_seconds=0;
	}
	
}

function stopCount(){
	clearTimeout(tvoice);
}

//上传图片 
 function  upImg(){
	  //如果没有选择练习人 则不可以 上传
	 if($("#currentName").attr("talkingID")==0){
			layer.msg('您还没有选择联系人');
			return;
		}
        //验证是否为图片
	   var filepath=$("#sendPIC").val();
	   var extStart=filepath.lastIndexOf(".");
	   var ext=filepath.substring(extStart,filepath.length).toUpperCase();
	   if(ext!=".BMP" && ext!=".PNG" && ext!=".GIF" && ext!=".JPG" && ext!=".JPEG"){
	      layer.msg("图片限于png,gif,jpeg,jpg格式");
	      return;
	    }
	    //
		var formData = new FormData($("#formData"));
		var fileUpload = $('#sendPIC')[0].files[0];
		var formData = new FormData();
		var reader = new FileReader();
		reader.readAsDataURL(fileUpload);
		formData.append("imgurl_file",fileUpload);
	    $.ajax({  
	  		url: '${path}/im/m/upload.do',  
	        type: 'POST',  
	        data: formData,  
	        async: false,  
	        cache: false,  
	        contentType: false,  
	        processData: false,  
	        success: function (json) {
	        	console.log(json.dataobj[0].url);
	        	//发送图片
	        	PCmsg.sendPic(json.dataobj[0].url+"?id=ppp",PCmsg.friendsId,PCmsg.getMyId()).pcLog1(json.dataobj[0].url+"?id=ppp");
	        },  
	        error: function (json) {  
	        	alert(JSON.stringify(json));  
	                           }  
	         });  		
		
		
    }

 $("#PHOTO").click(function(){
	 $('#sendPIC').val('');
	 $('#sendPIC').click();
 })
 
 $("#getChatHis").click(function(){
	 PCmsg.clearMSGlist().getchatHistory();
 })
 
 $("#recentContact").click(function(){
	 PCmsg.getRecentInTouch();
 })
 
   
  
	</script>
</body>
</html>