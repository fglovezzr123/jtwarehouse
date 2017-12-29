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
	<link rel="stylesheet" href="${path}/resource/css/pc_im.css?v=${sversion}">
</head>
<body>
	
	<script type="text/javascript" src="${path}/resource/js/jquery.PCqqFace.js?v=${sversion}" charset="utf-8"></script>
	<script type="text/javascript">	      	 
	   //PC聊天主模块
	   //头像地址 myheadpath；
      var PCIM=(function(){
    	  var data = {};
    	  var nim='';
    	  var myheadpath='';
    	  var myownInfor='';
    	  var currentFriend='';
    	  var appKeys='e7382694a60dc94ecc2550f20d6612de';
    	  var tokens='';
    	  var myuserid='';
    	  //回调函数
	      function onConnect() {
		      console.log('连接成功');
		  }
		  function onWillReconnect(obj) {
		      // 此时说明 SDK 已经断开连接, 请开发者在界面上提示用户连接已断开, 而且正在重新建立连接
		      console.log('即将重连', obj);
		  }
		  function onDisconnect(error) {
		      // 此时说明 SDK 处于断开状态, 开发者此时应该根据错误码提示相应的错误信息, 并且跳转到登录页面
		      console.log('连接断开', error);
		      if (error) {
		          switch (error.code) {
		          // 账号或者密码错误, 请跳转到登录页面并提示错误
		          case 302:
		              break;
		          // 重复登录, 已经在其它端登录了, 请跳转到登录页面并提示错误
		          case 417:
		              break;
		          // 被踢, 请提示错误后跳转到登录页面
		          case 'kicked':
		              break;
		          default:
		              break;
		          }
		      }
		  }
		  function onError(error, obj) {
		      console.log('发生错误', error, obj);
		  }
		  function onLoginPortsChange(loginPorts) {
		      console.log('当前登录帐号在其它端的状态发生改变了', loginPorts);
		  }
		  function onBlacklist(blacklist) {
		      console.log('收到黑名单', blacklist);
		      data.blacklist = nim.mergeRelations(data.blacklist, blacklist);
		      data.blacklist = nim.cutRelations(data.blacklist, blacklist.invalid);
		      refreshBlacklistUI();
		  }
		  function onMarkInBlacklist(obj) {
		      console.log(obj.account + '被你' + (obj.isAdd ? '加入' : '移除') + '黑名单', obj);
		      if (obj.isAdd) {
		          addToBlacklist(obj);
		      } else {
		          removeFromBlacklist(obj);
		      }
		  }
		  function addToBlacklist(obj) {
		      data.blacklist = nim.mergeRelations(data.blacklist, obj.record);
		      refreshBlacklistUI();
		  }
		  function removeFromBlacklist(obj) {
		      data.blacklist = nim.cutRelations(data.blacklist, obj.record);
		      refreshBlacklistUI();
		  }
		  function refreshBlacklistUI() {
		      // 刷新界面
		  }
		  function onMutelist(mutelist) {
		      console.log('收到静音列表', mutelist);
		      data.mutelist = nim.mergeRelations(data.mutelist, mutelist);
		      data.mutelist = nim.cutRelations(data.mutelist, mutelist.invalid);
		      refreshMutelistUI();
		  }
		  function onMarkInMutelist(obj) {
		      console.log(obj.account + '被你' + (obj.isAdd ? '加入' : '移除') + '静音列表', obj);
		      if (obj.isAdd) {
		          addToMutelist(obj);
		      } else {
		          removeFromMutelist(obj);
		      }
		  }
		  function addToMutelist(obj) {
		      data.mutelist = nim.mergeRelations(data.mutelist, obj.record);
		      refreshMutelistUI();
		  }
		  function removeFromMutelist(obj) {
		      data.mutelist = nim.cutRelations(data.mutelist, obj.record);
		      refreshMutelistUI();
		  }
		  function refreshMutelistUI() {
		      // 刷新界面
		  }
		  function onFriends(friends) {
		      console.log('收到好友列表', friends);
		      data.friends = nim.mergeFriends(data.friends, friends);
		      data.friends = nim.cutFriends(data.friends, friends.invalid);
		      refreshFriendsUI();
		  }
		  function onSyncFriendAction(obj) {
		      console.log('收到好友操作', obj);
		      switch (obj.type) {
		      case 'addFriend':
		          console.log('你在其它端直接加了一个好友' + obj);
		          onAddFriend(obj.friend);
		          break;
		      case 'applyFriend':
		          console.log('你在其它端申请加了一个好友' + obj);
		          break;
		      case 'passFriendApply':
		          console.log('你在其它端通过了一个好友申请' + obj);
		          onAddFriend(obj.friend);
		          break;
		      case 'rejectFriendApply':
		          console.log('你在其它端拒绝了一个好友申请' + obj);
		          break;
		      case 'deleteFriend':
		          console.log('你在其它端删了一个好友' + obj);
		          onDeleteFriend(obj.account);
		          break;
		      case 'updateFriend':
		          console.log('你在其它端更新了一个好友', obj);
		          onUpdateFriend(obj.friend);
		          break;
		      }
		  }
		  function onAddFriend(friend) {
		      data.friends = nim.mergeFriends(data.friends, friend);
		      refreshFriendsUI();
		  }
		  function onDeleteFriend(account) {
		      data.friends = nim.cutFriendsByAccounts(data.friends, account);
		      refreshFriendsUI();
		  }
		  function onUpdateFriend(friend) {
		      data.friends = nim.mergeFriends(data.friends, friend);
		      refreshFriendsUI();
		  }
		  function refreshFriendsUI() {
		      // 刷新界面
		  }
		  function onMyInfo(user) {
		      console.log('收到我的名片', user);
		      data.myInfo = user;
		      updateMyInfoUI();
		      getHistroyList(historytimes,'',lastMSGid);
		     
		  }
		  function onUpdateMyInfo(user) {
		      console.log('我的名片更新了', user);
		      data.myInfo = NIM.util.merge(data.myInfo, user);
		      updateMyInfoUI();
		  }
		  function updateMyInfoUI() {
		      // 刷新界面
		  }
		  function onUsers(users) {
		      console.log('收到用户名片列表', users);
		      data.users = nim.mergeUsers(data.users, users);
		  }
		  function onUpdateUser(user) {
		      console.log('用户名片更新了', user);
		      data.users = nim.mergeUsers(data.users, user);
		  }
		  function onTeams(teams) {
		      console.log('群列表', teams);
		      data.teams = nim.mergeTeams(data.teams, teams);
		      onInvalidTeams(teams.invalid);
		  }
		  function onInvalidTeams(teams) {
		      data.teams = nim.cutTeams(data.teams, teams);
		      data.invalidTeams = nim.mergeTeams(data.invalidTeams, teams);
		      refreshTeamsUI();
		  }
		  function onCreateTeam(team) {
		      console.log('你创建了一个群', team);
		      data.teams = nim.mergeTeams(data.teams, team);
		      refreshTeamsUI();
		      onTeamMembers({
		          teamId: team.teamId,
		          members: owner
		      });
		  }
		  function refreshTeamsUI() {
		      // 刷新界面
		  }
		  function onTeamMembers(obj) {
		      //console.log('收到群成员', obj);
		      var teamId = obj.teamId;
		      var members = obj.members;
		      data.teamMembers = data.teamMembers || {};
		      data.teamMembers[teamId] = nim.mergeTeamMembers(data.teamMembers[teamId], members);
		      data.teamMembers[teamId] = nim.cutTeamMembers(data.teamMembers[teamId], members.invalid);
		      refreshTeamMembersUI();
		  }
		  function onSyncTeamMembersDone() {
		      console.log('同步群列表完成');
		  }
		  function onUpdateTeamMember(teamMember) {
		      console.log('群成员信息更新了', teamMember);
		      onTeamMembers({
		          teamId: teamMember.teamId,
		          members: teamMember
		      });
		  }
		  function refreshTeamMembersUI() {
		      // 刷新界面
		  }
		  function onSessions(sessions) {
		       for(var i=0;i<sessions.length;i++){
	    	      saveLocalSessions(sessions[i].to,sessions[i],true);
	          }
		      //console.log('收到会话列表', sessions);
		      data.sessions = nim.mergeSessions(data.sessions, sessions);
		      updateSessionsUI();
		  }
		  function onUpdateSession(session) {
			  saveLocalSessions(session.to,session,true);
		      console.log('会话更新了', session);
		      data.sessions = nim.mergeSessions(data.sessions, session);
		      updateSessionsUI();
		  }
		  
		  function updateSessionsUI() {
		      // 刷新界面
		  }
		  function onRoamingMsgs(obj) {
		      console.log('漫游消息', obj);
		      pushMsg(obj.msgs);
		  }
		  function onOfflineMsgs(obj) {
		      console.log('离线消息', obj);
		      pushMsg(obj.msgs);
		  }
		  function onMsg(msg) {
		      console.log('收到消息', msg.scene, msg.type, msg);
		      pushMsg(msg);
		      var heads="";
		      if(msg.custom){
		    	  heads=JSON.parse(msg.custom);
		    	  console.log(heads);
		      }else{
		    	  heads={};
		    	  heads.headparth='';
		      }
		      if(msg.scene=="p2p" && msg.to==myaccounts  && msg.from==fAccount){
		    	  nim.resetSessionUnread("p2p-"+fAccount);
		    	  var varId=yzm();
	    		  if(msg.fromClientType=="Web"){
	    			  if(msg.content){
	    	    		  var urls=JSON.parse(msg.content)
	    	    		  if(urls.type==2){
	    	    			  $("#talkbody").append('<div class="MSG yourfriends"><img class="usicon talkingIcon" src="'+heads.headparth+'"/><div class="chatText"><div class="textCore"><img class="imgMsg" src="'+urls.data.url+'"/></div></div><div class="clear"></div></div>');
	    	    		  }else if(urls.type==3){
	    	    			  $("#talkbody").append('<div class="MSG yourfriends"><img class="usicon talkingIcon" src="'+heads.headparth+'"/><div class="chatText"><div class="textCore"><img class="audioIm" FR="1" src="${path}/resource/img/images/FFFFFFFFFFFF.png" onclick="playAudio($(this))" times="'+urls.data.times+'" playurl="'+urls.data.url+'"/>'+urls.data.times+'\"'+'</div></div><div class="clear"></div></div>');
	    	    		  }
	    	    		}else{
	    	    			 $("#talkbody").append('<div class="MSG yourfriends"><img class="usicon talkingIcon" src="'+heads.headparth+'"/><div class="chatText"><div class="textCore">'+$.fn.decodeOoutEmoj("${path}/resource/img/emoji/",msg.text)+'</div></div><div class="clear"></div></div>');
	    	    		}
	    		  }else{
	    			  if( msg.type=="text"){
	    				  $("#talkbody").append('<div class="MSG yourfriends"><img class="usicon talkingIcon" src="'+heads.headparth+'"/><div class="chatText"><div class="textCore">'+$.fn.decodeOoutEmoj("${path}/resource/img/emoji/",msg.text)+'</div></div><div class="clear"></div></div>');
	    			  }else if(msg.type=="image"){
	        			  $("#talkbody").append('<div class="MSG yourfriends"><img class="usicon talkingIcon" src="'+heads.headparth+'"/><div class="chatText"><div class="textCore"><img class="imgMsg" src="'+msg.file.url+'"/></div></div><div class="clear"></div></div>');
	    			  }else if(msg.type=="audio"){
	    				  $("#talkbody").append('<div class="MSG yourfriends"><img class="usicon talkingIcon" src="'+heads.headparth+'"/><div class="chatText"><div class="textCore"><img class="audioIm" FR="1" src="${path}/resource/img/images/FFFFFFFFFFFF.png" onclick="playAudio($(this))" times="'+parseInt(msg.file.dur/1000)+'" playurl="'+msg.file.mp3Url+'"/>'+parseInt(msg.file.dur/1000)+'\"'+'</div></div><div class="clear"></div></div>');
	    			  }
	    		  }
		       
		    	
		      }
		  }
		  function pushMsg(msgs) {
		      if (!Array.isArray(msgs)) { msgs = [msgs]; }
		      var sessionId = msgs[0].sessionId;
		      data.msgs = data.msgs || {};
		      data.msgs[sessionId] = nim.mergeMsgs(data.msgs[sessionId], msgs);
		  }
		  function onOfflineSysMsgs(sysMsgs) {
		      console.log('收到离线系统通知', sysMsgs);
		      pushSysMsgs(sysMsgs);
		  }
		  function onSysMsg(sysMsg) {
		      console.log('收到系统通知', sysMsg)
		      pushSysMsgs(sysMsg);
		  }
		  function onUpdateSysMsg(sysMsg) {
		      pushSysMsgs(sysMsg);
		  }
		  function pushSysMsgs(sysMsgs) {
		      data.sysMsgs = nim.mergeSysMsgs(data.sysMsgs, sysMsgs);
		      refreshSysMsgsUI();
		  }
		  function onSysMsgUnread(obj) {
		      console.log('收到系统通知未读数', obj);
		      data.sysMsgUnread = obj;
		      refreshSysMsgsUI();
		  }
		  function onUpdateSysMsgUnread(obj) {
		      console.log('系统通知未读数更新了', obj);
		      data.sysMsgUnread = obj;
		      refreshSysMsgsUI();
		  }
		  function refreshSysMsgsUI() {
		      // 刷新界面
		  }
		  function onOfflineCustomSysMsgs(sysMsgs) {
		      console.log('收到离线自定义系统通知', sysMsgs);
		  }
		  function onCustomSysMsg(sysMsg) {
		      console.log('收到自定义系统通知', sysMsg);
		  }
		  function onSyncDone() {
		      console.log('同步完成');
		  }
		  //保存最近联系人
		  function saveLocalSessions(pushID,session,cur){
			    var datas;
			    var myfriendP='';
			    var myfriendNicname='';
			    var myStorage=localStorage.getItem("recentContact");
				if(!myStorage){
				   console.log('不存在');	
				   var datas={};
				   if(session.scene=="p2p"){
					   nim.getUser({
						    account: pushID,
						    done: function(error, user) {
							    if (!error && user) {
				    	         myfriendP=user.avatar;
				    	         myfriendNicname=user.nick;
								 datas[pushID]=session;
								 if(!cur){
									 datas[pushID]["myunread"]=1;
								 }else{
								     if(pushID==fAccount){
								         datas[pushID]["myunread"]=0;
								     }else{
								         datas[pushID]["myunread"]=1;
								     }
									 
								 }
								 datas[pushID]["lastMsg"]["fps"]=myfriendP;
								 datas[pushID]["lastMsg"]["fns"]=myfriendNicname;
							     localStorage.setItem("recentContact",JSON.stringify(datas));	
							    }
							}
						});
				   }
				}else{
					//如果本地存储存在
					   datas=JSON.parse(myStorage);
						if(datas[pushID]){
						   if(datas[pushID].scene=="p2p"){
							   nim.getUser({
							    account: pushID,
							    done: function(error, user) {
								    if (!error && user) {
						    	        var b=datas[pushID]["myunread"];
						    	         myfriendP=user.avatar;
						    	         myfriendNicname=user.nick;
										 datas[pushID]=session;
											 if(pushID==fAccount){
											         datas[pushID]["myunread"]=0;
											  }else{
											         datas[pushID]["myunread"]=b+1;
											 }
										 datas[pushID]["lastMsg"]["fps"]=myfriendP;
										 datas[pushID]["lastMsg"]["fns"]=myfriendNicname;
										 console.log(datas);
									     localStorage.setItem("recentContact",JSON.stringify(datas));	
								    }
								}
							});
						   }	
						}else{
						      if(session.scene=="p2p"){
							   nim.getUser({
							    account: pushID,
							    done: function(error, user) {
								    if (!error && user) {
						    	         myfriendP=user.avatar;
						    	         myfriendNicname=user.nick;
										 datas[pushID]=session;
											 if(pushID==fAccount){
											         datas[pushID]["myunread"]=0;
											  }else{
											         datas[pushID]["myunread"]=1;
											 }
										 datas[pushID]["lastMsg"]["fps"]=myfriendP;
										 datas[pushID]["lastMsg"]["fns"]=myfriendNicname;
										 console.log(datas);
									     localStorage.setItem("recentContact",JSON.stringify(datas));	
								    }
								}
							});
						   }
						}
				}  
			}
    	  //清楚当前用户未读条数
    	   function clearUnread(){
			 var datas='';
			 var myStorage=localStorage.getItem("recentContact");
			 if(myStorage){
				datas=JSON.parse(myStorage);
				if(datas[fAccount]){
					datas[fAccount]["myunread"]=0;
					localStorage.setItem("recentContact",JSON.stringify(datas));
				}
			 }
		 }
    	  /*绑定必要点击事件  */
    	   
		  //入口
		  var mains={
			  /*界面初始化  */	  
			   startUI:function(){
				   var str='<div id="checkMsg">查看消息</div><div id="pcimshadows" style="height:'+$(document).height()+'px"></div><div id="pcimcapsule"><div class="PCIM-box"><div class="PCIM-LEFT"><div class="leftHeadtab"><span class="chosen" id="recentContact">最近联系</span><div class="clear"></div></div><div id="flist-box"></div><div id="flist-contact" style="display:block"></div></div><div class="PCIM-RIGHT"><div class="PCIN-HEAD"><img class="usicon" id="userIcon" src=""/><span id="currentName" talkingID="0"></span><div id="select" style="display:none">您还没有选择联系人</div><div id="closeImWindow">关闭窗口</div></div><audio id="audio_id" src=""></audio><div class="getChatHis" ><span id="getChatHis" style="display:none">点击获取聊天记录</span></div><div class="PCIN-BODY" id="talkbody"></div><div class="PCIN-FOOTER"><div class="PCIM-FHEAD"><span id="FACES"  class="IP-ITEM emotion"></span><span id="PHOTO" class="IP-ITEM"></span><form id="formData" enctype="multipart/form-data" method="post"><input type="file" style="display:none"   name="imgurl_file"  id="sendPIC"/></form><div class="clear"></div></div><textarea id="PCIMTEXT"></textarea><div class="sentzone"><input id="sendText" type="button" value="发送消息"/></div></div></div><div class="clear"></div></div></div>';
				   $("body").append(str);
				   /*绑定必要点击事件  */
				   bindallClick();
			   }	  
    		   setCurrentFriend:function(friendId){
    			   currentFriend=friendId;
    		   },
    		   getChatHistory:function(timestamp,lastMsgId,msgid){
    			    
    		   },
    		   beforeConnection:function(myId,token){
    			   myuserid=myId;
    			   tokens=token;
    		   },
    		   getrecentSession:function(){
    			   var recent = JSON.parse(localStorage.getItem("recentContact"));
    				 var str="";
    				 for(var i in recent ){
    					 console.log(recent[i])
    				    if(recent[i].scene=="p2p"){ 
    				    	$("#flist-contact").append(
    			            		 '<div class="friendItem recents" id="recent'+recent[i].to+'" friend_user='+recent[i].to+'  names="'+recent[i].lastMsg.fns+'" headp="'+recent[i].lastMsg.fps+'">'+
    			            		   '<img class="usicon FListIcon" src="'+recent[i].lastMsg.fps+'"/>'+
    			            		   '<div class="FListName" >'+recent[i].lastMsg.fns+'</div>'+
    			            		   '<span id="unread'+recent[i].to+'" class="unreads">'+recent[i].myunread+'</span>'+
    			         		     '</div>');
    				    }	
    				 } 		     
    		   },
    		   connnectIm:function(){
    			   nim= new NIM({
    				      // 初始化SDK
    				      // debug: true
    				      appKey: appKeys,
    				      account: 6770,
    				      syncRoamingMsgs:true,
    				      token: "1d9bd7bf255488d2235eb75ef1fb82c8",
    				      onconnect: onConnect,
    				      onerror: onError,
    				      autoMarkRead:true,
    				      onwillreconnect: onWillReconnect,
    				      ondisconnect: onDisconnect,
    				      // 多端
    				      onloginportschange: onLoginPortsChange,
    				      // 用户关系
    				      onblacklist: onBlacklist,
    				      onsyncmarkinblacklist: onMarkInBlacklist,
    				      onmutelist: onMutelist,
    				      onsyncmarkinmutelist: onMarkInMutelist,
    				      // 好友关系
    				      onfriends: onFriends,
    				      onsyncfriendaction: onSyncFriendAction,
    				      // 用户名片
    				      onmyinfo: onMyInfo,
    				      onupdatemyinfo: onUpdateMyInfo,
    				      onusers: onUsers,
    				      onupdateuser: onUpdateUser,
    				      // 群组
    				      onteams: onTeams,
    				      onsynccreateteam: onCreateTeam,
    				      onteammembers: onTeamMembers,
    				      onsyncteammembersdone: onSyncTeamMembersDone,
    				      onupdateteammember: onUpdateTeamMember,
    				      // 会话
    				      onsessions: onSessions,
    				      onupdatesession: onUpdateSession,
    				      // 消息
    				      onroamingmsgs: onRoamingMsgs,
    				      onofflinemsgs: onOfflineMsgs,
    				      onmsg: onMsg,
    				      // 系统通知
    				      onofflinesysmsgs: onOfflineSysMsgs,
    				      onsysmsg: onSysMsg,
    				      onupdatesysmsg: onUpdateSysMsg,
    				      onsysmsgunread: onSysMsgUnread,
    				      onupdatesysmsgunread: onUpdateSysMsgUnread,
    				      onofflinecustomsysmsgs: onOfflineCustomSysMsgs,
    				      oncustomsysmsg: onCustomSysMsg,
    				      // 同步完成
    				      onsyncdone: onSyncDone,
    				      db:false,
    				      secure:true
    				  });
    		   }  	    
    	   }
    	  
    	  return mains;
    	   
      }())
   
     PCIM.connnectIm();
	</script>
</body>
</html>