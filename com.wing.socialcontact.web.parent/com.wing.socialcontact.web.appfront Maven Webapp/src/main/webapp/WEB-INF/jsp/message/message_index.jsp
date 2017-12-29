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
	/*  zdy_ajax({
			url: _path+"/m/sys/getTjyUserByid.do",
		    showLoading:false,
		    data:{"uid":6401},
			success: function(data,res){
			      console.log(data);
			     // mytoken=data;
			      
			}
		}); */
	var u = navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
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
		
   function getTeamInfor(teamId){
    	 var temsName=''; 
    	 if(data.teams){
    		 for(var i=0;i<data.teams.length;i++){
     	    	if(teamId==data.teams[i].teamId){
     	    		temsName=data.teams[i].name;
     	    		return temsName;
     	    	}
     	    }
    	 }
	     
    }
		
	//获取最近联系人	
	var b =setTimeout(function(){
		getrecentContacts();
	},1000);
	
	 function getrecentContacts(){
		 var recent = JSON.parse(localStorage.getItem("recentContact"));
		 var str="";
		 for(var i in recent ){
			 console.log(recent[i])
		    if(recent[i].scene=="p2p"){ 
		    // var myPortrait=JSON.parse(recent[i].lastMsg.custom);	
			 str +=  '<div class="goingto content-item bottom-border active_A" scene="'+recent[i].scene+'" onclick="clickto($(this))"  id="'+recent[i].to+'"   >'+
				'<div class="con-l " style="background:none">'+
				'	<div style="float:left">'+
				'		<img class="portrait imgbox" src="'+recent[i].lastMsg.fps+'" />'+
				'	</div>'+
				'	<div class="message-jishu">'+recent[i].myunread+'</div>'+
				'	<div class="sysMes">'+
				'		<ul>'+
				'			<li style="display:block;height:50%;font-size:0.3rem;">'+recent[i].lastMsg.fns+'</li>'+
				'			<li class="sign-text clear" style="display:block;height:50%;width:2.7rem;white-space: nowrap;text-overflow: ellipsis;-o-text-overflow: ellipsis;overflow: hidden;">'+recent[i].lastMsg.text+'</li>'+
				'		</ul>'+
				'	</div>'+
				'<div class="iconfont " style="float:right" >&#xe605;</div>'+
				'	<div class="textDate">'+getNowFormatDate(recent[i].updateTime)+'</div>'+
				'</div>'+
				'<div style="clear:both"></div>'+
			'</div>';
		    }else{
		    	str +=  '<div class="goingto content-item bottom-border active_A" scene="'+recent[i].scene+'" onclick="clickto($(this))" id="'+recent[i].to+'"   >'+
				'<div class="con-l " style="background:none">'+
				'	<div style="float:left">'+
				'		<img class="portrait imgbox" src="${path}/resource/img/images/qun.png"/>'+
				'	</div>'+
				'	<div class="message-jishu">'+recent[i].myunread+'</div>'+
				'	<div class="sysMes">'+
				'		<ul>'+
				'			<li style="display:block;height:50%;font-size:0.3rem;">'+getTeamInfor(recent[i].to)+'</li>'+
				'			<li class="sign-text clear" style="display:block;height:50%;width:2.7rem;white-space: nowrap;text-overflow: ellipsis;-o-text-overflow: ellipsis;overflow: hidden;">'+recent[i].lastMsg.fromNick+" : "+recent[i].lastMsg.text+'</li>'+
				'		</ul>'+
				'	</div>'+
				'<div class="iconfont " style="float:right" >&#xe605;</div>'+
				'	<div class="textDate">'+getNowFormatDate(recent[i].updateTime)+'</div>'+
				'</div>'+
				'<div style="clear:both"></div>'+
			'</div>';
		    }	
		 }
		 $("#msg_div").html(str);
		 
	 }	 
	//获取群信息
     function clickto(the){
		if(the.attr("scene")=="p2p"){
			window.location.href = "${path}/wx/businessFriend/talkAbout.do?follow_user="+the.attr("id");
		}else{
			window.location.href = "${path}/wx/businessFriend/talkAboutQl.do?qid="+the.attr("id"); 
		}
	}
  
	</script>
</body>
</html>