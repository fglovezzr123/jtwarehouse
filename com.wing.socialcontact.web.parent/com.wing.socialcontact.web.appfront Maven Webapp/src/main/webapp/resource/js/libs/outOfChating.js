            var connected = false;
            var myUserId =localStorage.getItem("currentUserId");
			var myjid =myUserId+'@'+_im_path;
			//console.log(myjid);
			var myheadpath = '<%=headpic%>';
			var myname = "<%=nickname  %>";
			   //var qid = window.location.href.split("=")[1]
				// XMPP服务器BOSH地址
			var BOSH_SERVICE = _im_path_http+':5280/http-bind/';
			// 房间JID
			//var ROOM_JID = qid + '@group.'+_im_path;
			// XMPP连接
			var connection = null;
			// 当前状态是否连接
			var connected = false; 
            //连接服务器   
	       joinxmpp();
         //链接状态监控
	     $(document).ready(function(){
	    	 showUnreadCount();
	     });  
	      
      	function onConnects(status) {
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
					connection.addHandler(instantMessageT, null, 'message', null, null, null);
					connection.send($pres().tree());
					/*if(getrecentContact && typeof getrecentContact=="function"){
						getrecentContact();
						
					}*/
					/*keepOling888();*/
				}
			}
	    function joinxmpp(){
		 if((window.location.href).indexOf('messagePage')>-1 || (window.location.href).indexOf('talkAboutQl')>-1 || (window.location.href).indexOf('businessFriend/talkAbout')>-1 || (window.location.href).indexOf('businessFriend/talkInfo')>-1 || (window.location.href).indexOf('businessFriend/msg')>-1){
			 return;
		 };
		 var cookieRID=sessionStorage.getItem('Rid');
		 var cookieSID=sessionStorage.getItem('Sid');
		 connection = new Strophe.Connection(BOSH_SERVICE);
		 if(myUserId != null && cookieRID==null &&  cookieSID==null){
			 connection.connect(myjid, null, onConnects);
			 connection.xmlOutput = function (e) {
                      if(connected){
			             var RID = $(e).attr('rid');
			             var SID = $(e).attr('sid');
			             sessionStorage.setItem('Rid',RID);
			             sessionStorage.setItem('Sid',SID);
			             console.log('RID=' + RID + ' [SID=' + SID + ']');
                      }
			    };
		   }else if(myUserId != null && cookieRID!=null && cookieSID!=null){
			   
			 connection.attach(myjid,cookieSID,parseInt(cookieRID)+1,attachingCallback);
			 connection.xmlOutput = function (e) {
	             var RID = $(e).attr('rid');
	             var SID = $(e).attr('sid');
	             sessionStorage.setItem('Rid',RID);
	             sessionStorage.setItem('Sid',SID);
	             console.log('RID=' + RID + ' [SID=' + SID + ']');     
	          }; 
		 };
	 }	    
	  //信息监控

	function instantMessageT(msg) {
		console.log(msg);
		/*console.log($('.wrapper'));*/
		var from = msg.getAttribute('from');
		var type = msg.getAttribute('type');
		var elems = msg.getElementsByTagName('body');
		var jid = from.split("@")[0];
		if (type == "groupchat" && elems.length > 0) {
			var body = elems[0];
			var bodytext=Strophe.getText(body);
			if(bodytext.toString().indexOf('?id=ppp')!=-1){
				/*layer.msg('[图片]', {
					  offset: ['20'],
					  anim: 1
					});*/
			}else{
				var message= bodytext.replace(/&lt;/g, '<');
		        message = message.replace(/&gt;/g, '>');
		        message = message.replace(/&quot;/g, '"');
		   
				/*layer.msg(message, {
					  offset: ['20'],
					  anim: 1
					});*/
			}	
		}else if (type == "chat" && elems.length > 0){
			var body = elems[0];
			var bodytext=Strophe.getText(body);
			if(bodytext.toString().indexOf('?id=ppp')!=-1){
				/*var sender=bodytext.toString().split('?id=ppp')[1];*/
				/*layer.msg(sender+' : '+'[图片]', {
					  offset: ['20'],
					  anim: 1
					  
					});*/
			}else{
				var message= bodytext.replace(/&lt;/g, '<');
		        message = message.replace(/&gt;/g, '>');
		        message = message.replace(/&quot;/g, '"');
		     /*   var sender =message.toString().split('mynicknamess')[1];*/
				/*layer.msg(sender+' : '+message, {
					  offset: ['20'],
					  anim: 1
					 
					});*/
			}      
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


	//长轮询
function keepOling888() {
				console.log("online");
				connection.sendIQ($iq({
					to: _im_path,
					from: myjid,
					type: "get"
				}).c('ping', {
					xmlns: "urn:xmpp:ping"
				}));
				setTimeout(keepOling888, 22000);
		}


function attachingCallback(e){
	 console.log(e);
	 if(e == Strophe.Status.ATTACHED){ 
		    connected = true;
		    connection.addHandler(instantMessageT, null, 'message', null, null, null);
			/*keepOling888();  */
	 }else if(e==2){
		 //断开重新连接
		 console.log('连接断开');
		 sessionStorage.removeItem('Rid');
         sessionStorage.removeItem('Sid');
         joinxmpp();
	 }
	 
}

function storageLoginedUser(userid,sid,rid){
	 if(getSessionStorageValue('loginedUser')){
		var olderuser= getSessionStorageValue('loginedUser')
		    olderuser[userid].logined=true;
		    olderuser[userid].sid=sid;
		    olderuser[rid].rid=rid;
		    setSessionStorageValue('loginedUser',olderuser)
	 }else{
		 var olderuser={};
		  olderuser[userid].logined=true;
		  olderuser[userid].sid=sid;
		  olderuser[rid].rid=rid;
		  setSessionStorageValue('loginedUser',olderuser)
	 }
	console.log(getSessionStorageValue('loginedUser'));
}

//信息 
function showUnreadCount(){
	//console.log(getLocalStorageValue("msg_notread_count"))
	var amount=getLocalStorageValue("msg_notread_count");
	var totalUnread=0;
	if(amount){
		for(var i in amount){
			if(amount.hasOwnProperty(i) && i != localStorage.getItem("currentUserId")){
				totalUnread +=amount[i];
			}
		}
		//console.log(totalUnread);
		if(totalUnread==0){
			$("#unreadSig").removeClass('hasMsgunread');
		}else{
			$("#unreadSig").addClass('hasMsgunread');
		}
	}else{
		console.log('不存在');
	}
	
}


     




