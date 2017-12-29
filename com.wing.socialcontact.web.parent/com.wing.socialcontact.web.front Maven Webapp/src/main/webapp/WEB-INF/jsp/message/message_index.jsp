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
	var myIdstatus=false;
	zdy_ajax({
		url: _path+"/m/sys/getTjyUserByid.do",
	    showLoading:false,
	    data:{"uid":localStorage.getItem("currentUserId")},
		success: function(data,res){
		      if(data.reconStatus!=2){
		    	  $('.talkList').hide();
		      }else if(data.reconStatus==2){
		    	  $('.talkList').show();
		    	  myIdstatus=true;
		      }
		      
		}
	});
	var u = navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    var myid=localStorage.getItem("currentUserId");
    var arr=[];
    $(document).ready(function() {
			var mag_data = getSessionStorageValue("mag_data")
			if(mag_data){
				//_data(mag_data)
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
			if(myIdstatus){
				self.location.href="${path}/m/message/user_greets.do";
			}else{
				window.location.href=_path+"/wx/commons/to_recon.do";
			}
			
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
			if(myIdstatus){
				self.location.href="${path}/m/message/lasetVistor.do";
			}else{
				window.location.href=_path+"/wx/commons/to_recon.do";
			}
			
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
				console.log(data);
				setSessionStorageValue('mag_data',data)
				//_data(data)
				
				/* console.log(message_top_data) */
			},
		    error:function(e){
			   //alert(e);
		    }
		});	
		};
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
		
	//获取最近联系人	
	 getrecentContacts();
	 function getrecentContacts(){
		// localStorage.removeItem("recentContact");
		 	var treatedMylist='';
			 var recent = JSON.parse(localStorage.getItem("recentContacts"));
			 sortsss(recent);
			 var arrDisorder= arr.sort(compare('updateTime'));
			//alert(arrDisorder);
			 var mytoplist=getSessionStorageValue("mag_data");
			 if(mytoplist){
			 	treatedMylist=treatToplist(mytoplist);
			 }
			 for(var i=0;i<arrDisorder.length;i++){
			 	var str='';
			 	var texts='';
	             if(arrDisorder[i].scene=="team"){
	            	var oldgroupId='';
			    	 if(arrDisorder[i].lastMsg.custom){
			    		oldgroupId=JSON.parse(arrDisorder[i].lastMsg.custom);
			    	 }else{
			    		return;
			    	 };
			    	 if(arrDisorder[i].lastMsg.fromClientType=="Web"){
			    		 if(arrDisorder[i].lastMsg.content){
			    			 var urls=JSON.parse(arrDisorder[i].lastMsg.content);
			    			 if(urls.type=="2"){
			    				 texts="[图片]";
			    			 }else if(urls.type=="3"){
			    				 texts="[语音]";
			    			 }
			    		 }else{
			    			 texts=arrDisorder[i].lastMsg.text;
			    		 }
			    	 }else{
			    		 if( arrDisorder[i].lastMsg.type=="text"){
			    			 texts=arrDisorder[i].lastMsg.text;
		    	    	  }else if(arrDisorder[i].lastMsg.type=="image"){
		    	    		  texts="[图片]";
		        		  }else if(arrDisorder[i].lastMsg.type=="audio"){
		        			  texts="[语音]";
		        		  }
			    	 }
			           str = '<div class="goingto content-item bottom-border active_A" nick="'+arrDisorder[i].teamName+'"  scene="'+arrDisorder[i].scene+'" onclick="clickto($(this))" id="'+arrDisorder[i].to+'" >'+
							'<div class="con-l " style="background:none">'+
							'	<div style="float:left">'+
							'		<img class="portrait imgbox" src="${path}/resource/img/images/qun.png"/>'+
							'	</div>'+
							'	<div class="message-jishu">'+arrDisorder[i].myunread+'</div>'+
							'	<div class="sysMes">'+
							'		<ul>'+
							'			<li style="display:block;height:50%;font-size:0.3rem;">'+arrDisorder[i].teamName+'</li>'+
							'			<li class="sign-text clear" style="display:block;height:50%;width:2.7rem;white-space: nowrap;text-overflow: ellipsis;-o-text-overflow: ellipsis;overflow: hidden;">'+arrDisorder[i].lastMsg.fromNick+" : "+texts+'</li>'+
							'		</ul>'+
							'	</div>'+
							'<div class="iconfont " style="float:right" >&#xe605;</div>'+
							'	<div class="textDate">'+getNowFormatDate(arrDisorder[i].updateTime)+'</div>'+
							'</div>'+
							'<div style="clear:both"></div>'+
						'</div>';
				  if(mytoplist){
		                if(oldgroupId.oldid==treatedMylist[oldgroupId.oldid]){
		                	$("#topmsg_div").prepend(str);
		                }else{
		                	$("#msg_div").prepend(str);
		                }
	                }else{
	                	$("#msg_div").prepend(str);
	                }	 
	             }else if(arrDisorder[i].scene=="p2p"){
	            	 if(arrDisorder[i].lastMsg.fromClientType=="Web"){
			    		 if(arrDisorder[i].lastMsg.content){
			    			 var urls=JSON.parse(arrDisorder[i].lastMsg.content);
			    			 if(urls.type=="2"){
			    				 texts="[图片]";
			    			 }else if(urls.type=="3"){
			    				 texts="[语音]";
			    			 }
			    		 }else{
			    			 texts=arrDisorder[i].lastMsg.text;
			    		 }
			    	 }else{
			    		 if( arrDisorder[i].lastMsg.type=="text"){
			    			 texts=arrDisorder[i].lastMsg.text;
		    	    	  }else if(arrDisorder[i].lastMsg.type=="image"){
		    	    		  texts="[图片]";
		        		  }else if(arrDisorder[i].lastMsg.type=="audio"){
		        			  texts="[语音]";
		        		  }
			    	 }
	                str =  '<div class="goingto content-item bottom-border active_A" nick="'+arrDisorder[i].lastMsg.fns+'" scene="'+arrDisorder[i].scene+'" onclick="clickto($(this))"  id="'+arrDisorder[i].to+'"   >'+
							'<div class="con-l " style="background:none">'+
							'	<div style="float:left">'+
							'		<img class="portrait imgbox" src="'+arrDisorder[i].lastMsg.fps+'" />'+
							'	</div>'+
							'	<div class="message-jishu">'+arrDisorder[i].myunread+'</div>'+
							'	<div class="sysMes">'+
							'		<ul>'+
							'			<li style="display:block;height:50%;font-size:0.3rem;">'+arrDisorder[i].lastMsg.fns+'</li>'+
							'			<li class="sign-text clear" style="display:block;height:50%;width:2.7rem;white-space: nowrap;text-overflow: ellipsis;-o-text-overflow: ellipsis;overflow: hidden;">'+texts+'</li>'+
							'		</ul>'+
							'	</div>'+
							'<div class="iconfont " style="float:right" >&#xe605;</div>'+
							'	<div class="textDate">'+getNowFormatDate(arrDisorder[i].updateTime)+'</div>'+
							'</div>'+
							'<div style="clear:both"></div>'+
						'</div>';
						if(mytoplist){
						  if(arrDisorder[i].to.split("_")[2]==treatedMylist[arrDisorder[i].to.split("_")[2]]){
							$("#topmsg_div").prepend(str);
							}else{
		                        $("#msg_div").prepend(str);
							}
						 }else{
						 	    $("#msg_div").prepend(str);
						 }
						
	             }

			 }
		 }	 
	//获取群信息
	     function clickto(the){
			if(the.attr("scene")=="p2p"){
				var obj={};
				obj.nickName=the.attr("nick");
				sessionStorage.setItem('user_info',JSON.stringify(obj));
				if(the.attr("id").indexOf("_")!=-1){
					var followLocation=the.attr("id").split("_")[2];
				}else{
					var followLocation=the.attr("id");
				}
				
				window.location.href = "${path}/wx/businessFriend/talkAbout.do?follow_user="+followLocation;
			}else{
				sessionStorage.setItem('groupNo163',the.attr("id"));
				var obj={};
				obj.nickName=the.attr("nick");
				sessionStorage.setItem('user_info',JSON.stringify(obj));
				zdy_ajax({
					url: _path+"/im/m/selGroupIdByTid.do",
				    showLoading:false,
				    data:{"tid":the.attr("id")},
					success: function(data,res){
					      console.log(data);
					      window.location.href = "${path}/wx/businessFriend/talkAboutQl.do?qid="+data;
					}
				});
				 
			}
		}


	  function sortsss(b){	  
	  	for(var i in b){
	       arr.push(b[i]);
	  	}
	  	return arr;
	  }
	  function compare(property){
	    return function(a,b){
	        var value1 = a[property];
	        var value2 = b[property];
	        return value1 - value2;
	    }
	}
	  //把置顶好友列表处理成我想要的格式
	 function treatToplist(datas){
	 	var newLIst={};
	    for(var i=0;i<datas.topGgroupList.length;i++){
	       newLIst[datas.topGgroupList[i].group_id]=datas.topGgroupList[i].group_id;
	    }
	    for(var s=0;s<datas.topUserList.length;s++){
	        newLIst[datas.topUserList[s].relat_user]=datas.topUserList[s].relat_user;
	    }
	    return newLIst;
	 }
  
	</script>
</body>
</html>