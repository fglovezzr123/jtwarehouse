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
<script type="text/javascript" src="${path}/resource/js/libs/huojianchao-decodeEmoji.js?v=${sversion}" charset="utf-8"></script>
<script type="text/javascript" src="${path}/resource/js/libs/iscroll.js?v=${sversion}" charset="utf-8"></script> 
<style>
    #myemoji{
       width:100%;
       box-sizing:border-box;
       padding-left:0.1rem;
       padding-right:0.1rem;
       padding-bottom:0.2rem;
       display:flex;
       justify-content: space-between;
       flex-wrap: wrap;
    } 
    #myemoji img{
       height:0.5rem;
       width:0.5rem;
    }
    .printEmoji{
        height:0.6rem;
        width:0.6rem;
    }
</style>
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
		<div class="cfooter" id="cfooter">
				<div class="footer-top">
					<i class="imboard"  id="torecord"></i>
					<iframe id="uploadPicFrame1" src="" style="display:none;"></iframe>
					<!-- <input type="file" style="display:none" id="uploadPhoto" /> -->
					<input type="text" name="t-content" id="t-content" value="" placeholder="请输入..." />
					<div style="display:none"  id="pressRecord" >开始录音</div>
					<i class="imFace emotion" id="emotion"><!-- &#xe60e; --></i>
					<i class="imgSpred jihao" style=""><!-- &#xe609; --></i>
					<span id="sendTexts" class="send">发送</span>
				</div>
				<div class="hidePh">
				    <div class="iconfont1"  id="file_button_1" onclick="doSelectPic1();"></div>
					<div class="sz"><i class="iconfont"><!-- &#xe619; --></i></div>
					<br style="clear:both"/>
				</div>
				<div id="myemoji" style="display:none">
				     <img class="emojiImg" names="[大笑]" src="${path}/resource/img/emoji/emoji_0.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[可爱]" src="${path}/resource/img/emoji/emoji_01.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[色]" src="${path}/resource/img/emoji/emoji_02.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[嘘]" src="${path}/resource/img/emoji/emoji_03.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[亲]" src="${path}/resource/img/emoji/emoji_04.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[呆]" src="${path}/resource/img/emoji/emoji_05.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[口水]" src="${path}/resource/img/emoji/emoji_06.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[汗]" src="${path}/resource/img/emoji/emoji_145.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[呲牙]" src="${path}/resource/img/emoji/emoji_07.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[鬼脸]" src="${path}/resource/img/emoji/emoji_08.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[害羞]" src="${path}/resource/img/emoji/emoji_09.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[偷笑]" src="${path}/resource/img/emoji/emoji_10.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[调皮]" src="${path}/resource/img/emoji/emoji_11.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[可怜]" src="${path}/resource/img/emoji/emoji_12.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[敲]" src="${path}/resource/img/emoji/emoji_13.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[惊讶]" src="${path}/resource/img/emoji/emoji_14.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[流感]" src="${path}/resource/img/emoji/emoji_15.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[委屈]" src="${path}/resource/img/emoji/emoji_16.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[流泪]" src="${path}/resource/img/emoji/emoji_17.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[嚎哭]" src="${path}/resource/img/emoji/emoji_18.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[惊恐]" src="${path}/resource/img/emoji/emoji_19.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[怒]" src="${path}/resource/img/emoji/emoji_20.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[酷]" src="${path}/resource/img/emoji/emoji_21.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[不说]" src="${path}/resource/img/emoji/emoji_22.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[鄙视]" src="${path}/resource/img/emoji/emoji_23.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[阿弥陀佛]" src="${path}/resource/img/emoji/emoji_24.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[奸笑]" src="${path}/resource/img/emoji/emoji_25.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[睡着]" src="${path}/resource/img/emoji/emoji_26.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[口罩]" src="${path}/resource/img/emoji/emoji_27.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[努力]" src="${path}/resource/img/emoji/emoji_28.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[抠鼻孔]" src="${path}/resource/img/emoji/emoji_29.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[疑问]" src="${path}/resource/img/emoji/emoji_30.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[怒骂]" src="${path}/resource/img/emoji/emoji_31.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[晕]" src="${path}/resource/img/emoji/emoji_32.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[呕吐]" src="${path}/resource/img/emoji/emoji_33.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[拜一拜]" src="${path}/resource/img/emoji/emoji_160.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[惊喜]" src="${path}/resource/img/emoji/emoji_161.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[流汗]" src="${path}/resource/img/emoji/emoji_162.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[卖萌]" src="${path}/resource/img/emoji/emoji_163.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[默契眨眼]" src="${path}/resource/img/emoji/emoji_164.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[烧香拜佛]" src="${path}/resource/img/emoji/emoji_165.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[晚安]" src="${path}/resource/img/emoji/emoji_166.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[强]" src="${path}/resource/img/emoji/emoji_34.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[弱]" src="${path}/resource/img/emoji/emoji_35.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[OK]" src="${path}/resource/img/emoji/emoji_36.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[拳头]" src="${path}/resource/img/emoji/emoji_37.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[胜利]" src="${path}/resource/img/emoji/emoji_38.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[鼓掌]" src="${path}/resource/img/emoji/emoji_39.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[握手]" src="${path}/resource/img/emoji/emoji_200.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[发怒]" src="${path}/resource/img/emoji/emoji_40.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[骷髅]" src="${path}/resource/img/emoji/emoji_41.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[便便]" src="${path}/resource/img/emoji/emoji_42.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[火]" src="${path}/resource/img/emoji/emoji_43.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[溜]" src="${path}/resource/img/emoji/emoji_44.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[爱心]" src="${path}/resource/img/emoji/emoji_45.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[心碎]" src="${path}/resource/img/emoji/emoji_46.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[钟情]" src="${path}/resource/img/emoji/emoji_47.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[唇]" src="${path}/resource/img/emoji/emoji_48.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[戒指]" src="${path}/resource/img/emoji/emoji_49.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[钻石]" src="${path}/resource/img/emoji/emoji_50.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[太阳]" src="${path}/resource/img/emoji/emoji_51.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[有时晴]" src="${path}/resource/img/emoji/emoji_52.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[多云]" src="${path}/resource/img/emoji/emoji_53.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[雷]" src="${path}/resource/img/emoji/emoji_54.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[雨]" src="${path}/resource/img/emoji/emoji_55.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[雪花]" src="${path}/resource/img/emoji/emoji_56.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[爱人]" src="${path}/resource/img/emoji/emoji_57.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[帽子]" src="${path}/resource/img/emoji/emoji_58.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[皇冠]" src="${path}/resource/img/emoji/emoji_59.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[篮球]" src="${path}/resource/img/emoji/emoji_60.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[足球]" src="${path}/resource/img/emoji/emoji_61.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[垒球]" src="${path}/resource/img/emoji/emoji_62.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[网球]" src="${path}/resource/img/emoji/emoji_63.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[台球]" src="${path}/resource/img/emoji/emoji_64.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[咖啡]" src="${path}/resource/img/emoji/emoji_65.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[啤酒]" src="${path}/resource/img/emoji/emoji_66.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[干杯]" src="${path}/resource/img/emoji/emoji_67.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[柠檬汁]" src="${path}/resource/img/emoji/emoji_68.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[餐具]" src="${path}/resource/img/emoji/emoji_69.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[汉堡]" src="${path}/resource/img/emoji/emoji_70.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[鸡腿]" src="${path}/resource/img/emoji/emoji_71.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[面条]" src="${path}/resource/img/emoji/emoji_72.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[冰淇淋]" src="${path}/resource/img/emoji/emoji_73.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[沙冰]" src="${path}/resource/img/emoji/emoji_74.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[生日蛋糕]" src="${path}/resource/img/emoji/emoji_75.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[蛋糕]" src="${path}/resource/img/emoji/emoji_76.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[糖果]" src="${path}/resource/img/emoji/emoji_77.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[葡萄]" src="${path}/resource/img/emoji/emoji_78.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[西瓜]" src="${path}/resource/img/emoji/emoji_79.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[光碟]" src="${path}/resource/img/emoji/emoji_80.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[手机]" src="${path}/resource/img/emoji/emoji_81.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[电话]" src="${path}/resource/img/emoji/emoji_82.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[电视]" src="${path}/resource/img/emoji/emoji_83.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[声音开启]" src="${path}/resource/img/emoji/emoji_84.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[声音关闭]" src="${path}/resource/img/emoji/emoji_85.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[铃铛]" src="${path}/resource/img/emoji/emoji_86.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[锁头]" src="${path}/resource/img/emoji/emoji_87.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[放大镜]" src="${path}/resource/img/emoji/emoji_88.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[灯泡]" src="${path}/resource/img/emoji/emoji_89.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[锤头]" src="${path}/resource/img/emoji/emoji_90.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[烟]" src="${path}/resource/img/emoji/emoji_91.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[炸弹]" src="${path}/resource/img/emoji/emoji_92.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[枪]" src="${path}/resource/img/emoji/emoji_93.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[刀]" src="${path}/resource/img/emoji/emoji_94.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[药]" src="${path}/resource/img/emoji/emoji_95.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[打针]" src="${path}/resource/img/emoji/emoji_96.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[钱袋]" src="${path}/resource/img/emoji/emoji_97.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[钞票]" src="${path}/resource/img/emoji/emoji_98.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[银行卡]" src="${path}/resource/img/emoji/emoji_99.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[手柄]" src="${path}/resource/img/emoji/emoji_100.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[麻将]" src="${path}/resource/img/emoji/emoji_101.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[调色板]" src="${path}/resource/img/emoji/emoji_102.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[电影]" src="${path}/resource/img/emoji/emoji_103.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[麦克风]" src="${path}/resource/img/emoji/emoji_104.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[耳机]" src="${path}/resource/img/emoji/emoji_105.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[音乐]" src="${path}/resource/img/emoji/emoji_106.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[吉他]" src="${path}/resource/img/emoji/emoji_107.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[火箭]" src="${path}/resource/img/emoji/emoji_108.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[飞机]" src="${path}/resource/img/emoji/emoji_109.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[火车]" src="${path}/resource/img/emoji/emoji_110.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[公交]" src="${path}/resource/img/emoji/emoji_111.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[轿车]" src="${path}/resource/img/emoji/emoji_112.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[出租车]" src="${path}/resource/img/emoji/emoji_113.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[警车]" src="${path}/resource/img/emoji/emoji_114.png" onclick="focusoninput($(this))"/><img class="emojiImg" names="[自行车]" src="${path}/resource/img/emoji/emoji_115.png" onclick="focusoninput($(this))"/>
				</div>
			</div>
			<audio id="audio_id" src=""></audio>
    <%@ include file="/WEB-INF/jsp/commons/include_upload.jsp"%>
	<script type="text/javascript">
	var data = {};
	var myheadpath='';
	var historytimes='';
	var myScroll;
	var lastMSGid='';
	var myaccounts='<%=userid %>',fAccount=window.location.search.split("=")[1],groupid=window.location.search.split("=")[1];
	clearUnread();
	var scrolled=false;
	var firstGetHistory=false;
	var mytoken=localStorage.getItem("mytoken");
	var nim='';
     nim = new NIM({
      // 初始化SDK
      // debug: true
      appKey: 'e7382694a60dc94ecc2550f20d6612de',
      account: myaccounts,
      syncRoamingMsgs:true,
      token: mytoken,
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
		      
	
	  
	  //重置未读条数
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
	      if(msg.custom){
	    	  heads=JSON.parse(msg.custom);
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
    	    			  $('#scroller').append('<div class="yourFriend" id="'+varId+'" ><img src="'+heads.headparth+'" class="_head" /><span>' + '<img class="messageIMGS" style="max-width:3.5rem;max-height:3.5rem;" onclick="showimgs(this)" src="'+urls.data.url+'"/></div>' + '</span></div><div  class="poles" style="height:2.5rem;"></div>'); 
    	    		  }else if(urls.type==3){
    	    			  $('#scroller').append('<div class="yourFriend" id="'+varId+'"><img src="'+heads.headparth+'" class="_head" /><span style="line-height:0.5rem;display:flex;">'+ '<img class="faudios" style="width:0.35rem;height:0.5rem;" FR="1" count="'+urls.data.times+'" onclick="fplayvoidce(this)" audio="'+urls.data.url+'" src="${path}/resource/img/images/FFFFFFFFFFFF.png"><span style="padding:0">'+urls.data.times+'\" '+'</span></span></div>');
    	    		  }
    	    		}else{
    	    			 $('#scroller').append('<div class="yourFriend" id="'+varId+'"><img src="'+heads.headparth+'" class="_head" /><span>' + $.fn.decodeOoutEmoj("${path}/resource/img/emoji/",msg.text) + '</span></div>');
    	    		}
    		  }else{
    			  if( msg.type=="text"){
    	    		  $('#scroller').append('<div class="yourFriend" id="'+varId+'"><img src="'+heads.headparth+'" class="_head" /><span>' + $.fn.decodeOoutEmoj("${path}/resource/img/emoji/",msg.text) + '</span></div>');
    	    	  }else if(msg.type=="image"){
        			  $('#scroller').append('<div class="yourFriend" id="'+varId+'"><img src="'+heads.headparth+'" class="_head" /><span>' + '<img class="messageIMGS" style="max-width:3.5rem;max-height:3.5rem;" onclick="showimgs(this)" src="'+msg.file.url+'"/></div>' + '</span></div><div  class="poles" style="height:2.5rem;"></div>'); 
        		  }else if(msg.type=="audio"){
        			  $('#scroller').append('<div class="yourFriend" id="'+varId+'"><img src="'+heads.headparth+'" class="_head" /><span style="line-height:0.5rem;display:flex;">'+ '<img class="faudios" style="width:0.35rem;height:0.5rem;" FR="1" count="'+parseInt(msg.file.dur/1000)+'" onclick="fplayvoidce(this)" audio="'+msg.file.mp3Url+'" src="${path}/resource/img/images/FFFFFFFFFFFF.png"><span style="padding:0">'+parseInt(msg.file.dur/1000)+'\" '+'</span></span></div>');
        		  }
    		  }
	       
	    	if(!scrolled){
				myScroll.refresh();
				$(".poles").remove();
				return;
			};
			myScroll.refresh();
		    scrollToP(varId);
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
			   }else if(session.scene=="team"){
					     datas[pushID]=session;
						 datas[pushID]["myunread"]=1;
				         localStorage.setItem("recentContact",JSON.stringify(datas));
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
					   }else if(datas[pushID].scene=="team"){
						    var b=datas[pushID]["myunread"];
							 datas[pushID]=session;
							 datas[pushID]["myunread"]= b+1;
						     localStorage.setItem("recentContact",JSON.stringify(datas));
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
					   }else if(session.scene=="team"){
							 datas[pushID]=session;
							 datas[pushID]["myunread"]=1;
						     localStorage.setItem("recentContact",JSON.stringify(datas));
					   }
					}
			}  
		}
		
	  $(".jihao").on("click", function(e) {
			e.stopPropagation();
			//$("#facebox").remove();
			    $("#emotion").addClass("emotion")
				$("#myemoji").hide();
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
			});
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
		};
		
//文本发送	
	  $("#sendTexts").click(function(e){
		  nim.resetSessionUnread("p2p-"+fAccount);
		  e.stopPropagation();
		  $('.hidePh').css('display','none');
		  $(".jihao").css({'display': 'block'});
	      $(".send").css({'display': 'none'});
		  var msg = nim.sendText({
			    scene: 'p2p',
			    to: fAccount,
			    isHistoryable:true,
			    text: $("#t-content").val(),
			    done: sendMsgDone,
			    custom:JSON.stringify({headparth:data.myInfo.avatar}),
			    needPushNick:true
			});
			//console.log('正在发送p2p text消息, id=' + msg.idClient);
			pushMsg(msg);
			function sendMsgDone(error, msg) {
				console.log(msg);
				var varId=yzm();
			     console.log('发送' + msg.scene + ' ' + msg.type + '消息' + (!error?'成功':'失败') + ', id=' + msg.idClient, error, msg);
			    if(!error){
			    	 $('#scroller').append('<div class="myself" id="'+varId+'"><span>' +  $.fn.decodeOoutEmoj("${path}/resource/img/emoji/",$("#t-content").val()) + '</span><img src="'+data.myInfo.avatar+'" class="_head"/></div>');
			         
			    }
			    $("#t-content").val('');
			    if($("#scroller").height()<$(window).height()*0.8){
					$(".poles").remove();
					myScroll.refresh();
					return;
				} 
		        myScroll.refresh();
			    scrollToP(varId); 
			}
		 
	  });
	//发送消息 滚动到指定位置
	  function yzm(){
		   /*生成随机ID*/
           var arr = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
           var str = '';
            for(var i = 0 ; i < 4 ; i ++ )
                str += ''+arr[Math.floor(Math.random() * arr.length)];
                return str;
      }
	  function scrollToP(obj){
		 /*  myScroll.refresh(); */
			 myScroll.scrollToElement(document.querySelector('#scroller #'+obj),200);
			 myScroll.refresh();
			 $(".poles").remove();
			 scrolled=true;
		 
	  }
	  //图片发送
	  function doSelectPic1(){
		  zdy_chooseImg(function(data,res){
	    	 	if(res.code == 0){
					 sendimage(data.img_url,2);
	    	 	}
	    	 },"im");
		 
	  }
	  
	  
	  function sendimage(urls,ty,time){
		  if(!time){
			  time='';
		  }
		  var msg = nim.sendCustomMsg({
		      scene: 'p2p',
		      to: fAccount,
		      isHistoryable:true,
		      content:JSON.stringify({type: ty, data: {url: urls,times:time}}),
		      custom:JSON.stringify({headparth:data.myInfo.avatar}),
		      done: function(error,msg){
		    	  if(!error){
		    		  console.log(msg);
		    		  var varId=yzm();
		    		   if(ty==2){
			    		$('#scroller').append('<div class="myself "><span>'+'<img class="messageIMGS" style="max-width:3.5rem;max-height:3.5rem;" onclick="showimgs(this)" src="'+urls+'"/>' + '</span><img src="'+data.myInfo.avatar+'" class="_head" /></div><div id="'+varId+'" class="poles" style="height:2.5rem;"></div>');
		    		   }else{
		    			   $('#scroller').append('<div class="myself" id="'+varId+'"><span style="display:flex;line-height:0.5rem;"><span style="padding:0">' +time+'\" '+'</span><img class="audios" FR="0" style="width:0.35rem;height:0.5rem;" count="'+time+'" onclick="playvoidce(this)" audio="'+urls+'" src="${path}/resource/img/images/MMMMMMMMMMMM.png">'+'</span><img src="'+data.myInfo.avatar+'" class="_head" /></div>');   
		    		   }
		    		    myScroll.refresh();
					    scrollToP(varId); 
		    		 } 
		      }
		  });
		//  console.log('正在发送p2p自定义消息, id=' + msg.idClient);
		  pushMsg(msg);
	  }
	  
	  //语音发送
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
		    					serverId : serverId
		    				},
		    				success: function(data,res){
		    					mediaId = data.img_url;
		    					/*  playMedia(mediaId); */
		    					
		    				    sendimage(data.img_url,3,t_seconds); 
		    				   
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
			thisVoice='';
		}
		
	} 
	//清除定时器
	function stopCount(){
		clearTimeout(t);
	}
	
	document.addEventListener('touchmove', function(e) {
		//e.preventDefault();
	}, false);
	document.addEventListener('DOMContentLoaded', loaded, false);	
	//历史记录操作
	function getHistroyList(timestamp,withWho,msgid){
		if(!timestamp){
			timestamp=0;
		}
		if(!withWho){
			withWho=6771;
		}
	    if(!msgid){
	    	msgid=0;
	    }
		nim.getHistoryMsgs({
			    scene: 'p2p',
			    to: fAccount,
			    limit:10,
			    lastMsgId:msgid,
			    endTime:timestamp,
			    done: function (error, obj) {
				    console.log('获取云端历史记录' + (!error?'成功':'失败'), error, obj);
				   
				    if (!error) {
				    	 if(!obj.msgs.length){
				    		 layer.msg("无更多聊天记录")
				    	 }
				    	 console.log(obj);
				    	 lastMSGid=obj.msgs[obj.msgs.length-1].idServer;
				         historytimes=obj.msgs[obj.msgs.length-1].time;
				         console.log(lastMSGid);
				        for(var i=0;i<obj.msgs.length;i++){
				        	 var heads='';
				        	 if(obj.msgs[i].custom){
				        		 heads=JSON.parse(obj.msgs[i].custom);
				        	 }else{
				        		 heads={};
				        		 heads.headparth='';
				        	 }
				        	 if(obj.msgs[i].from==myaccounts){
					    		  if(obj.msgs[i].type=="text" || obj.msgs[i].type=="custom"){
					    			  if(obj.msgs[i].content){
					    				  var urls=JSON.parse(obj.msgs[i].content);
					    	    		  if(urls.type==2){
					    	    			  $('#scroller').prepend('<div class="myself "><span>'+'<img class="messageIMGS" style="max-width:3.5rem;max-height:3.5rem;" onclick="showimgs(this)" src="'+urls.data.url+'"/>' + '</span><img src="'+data.myInfo.avatar+'" class="_head" /></div>'); 
					    	    		  }else{
					    	    			  $('#scroller').prepend('<div class="myself"><span style="display:flex;line-height:0.5rem;"><span style="padding:0">' +urls.data.times+'\" '+'</span><img class="audios" FR="0" style="width:0.35rem;height:0.5rem;" count="'+urls.data.times+'" onclick="playvoidce(this)" audio="'+urls.data.url+'" src="${path}/resource/img/images/MMMMMMMMMMMM.png">'+'</span><img src="'+data.myInfo.avatar+'" class="_head" /></div>');   
					    	    		  }
					    	    	  }else if(obj.msgs[i].type=="text" && !obj.msgs[i].content){
					    	    		  $('#scroller').prepend('<div class="myself"><span>' + $.fn.decodeOoutEmoj("${path}/resource/img/emoji/",obj.msgs[i].text)+ '</span><img src="'+data.myInfo.avatar+'" class="_head"/></div>');
					    	    	  } 
					    		  }else if(obj.msgs[i].type=="image"){ 
					    			  $('#scroller').prepend('<div class="myself "><span>'+'<img class="messageIMGS" style="max-width:3.5rem;max-height:3.5rem;" onclick="showimgs(this)" src="'+obj.msgs[i].file.url+'"/>' + '</span><img src="'+data.myInfo.avatar+'" class="_head" /></div>'); 
					    		  }else if(obj.msgs[i].type=="audio"){
					    			  $('#scroller').prepend('<div class="myself"><span style="display:flex;line-height:0.5rem;"><span style="padding:0">' +parseInt(obj.msgs[i].file.dur/1000)+'\" '+'</span><img class="audios" FR="0" style="width:0.35rem;height:0.5rem;" count="'+parseInt(obj.msgs[i].file.dur/1000)+'" onclick="playvoidce(this)" audio="'+obj.msgs[i].file.mp3Url+'" src="${path}/resource/img/images/MMMMMMMMMMMM.png">'+'</span><img src="'+data.myInfo.avatar+'" class="_head" /></div>');
					    		  }
				        	 }else{
				        		 if(obj.msgs[i].type=="text" || obj.msgs[i].type=="custom"){
					    			  if(obj.msgs[i].content){
					    				  var urls=JSON.parse(obj.msgs[i].content);
					    	    		  if(urls.type==2){
					    	    			  $('#scroller').prepend('<div class="yourFriend" ><img src="'+heads.headparth+'" class="_head" /><span>' + '<img class="messageIMGS" style="max-width:3.5rem;max-height:3.5rem;" onclick="showimgs(this)" src="'+urls.data.url+'"/></div>' + '</span></div>'); 
					    	    		  }else{
					    	    			  $('#scroller').prepend('<div class="yourFriend" ><img src="'+heads.headparth+'" class="_head" /><span style="line-height:0.5rem;display:flex;">'+ '<img class="faudios" style="width:0.35rem;height:0.5rem;" FR="1" count="'+urls.data.times+'" onclick="fplayvoidce(this)" audio="'+urls.data.url+'" src="${path}/resource/img/images/FFFFFFFFFFFF.png"><span style="padding:0">'+urls.data.times+'\" '+'</span></span></div>');
					    	    		  }
					    	    	  }else if(obj.msgs[i].type=="text" && !obj.msgs[i].content){
					    	    		  $('#scroller').prepend('<div class="yourFriend"><img src="'+heads.headparth+'" class="_head" /><span>' + $.fn.decodeOoutEmoj("${path}/resource/img/emoji/",obj.msgs[i].text) + '</span></div>');
					    	    	  } 
					    		  }else if(obj.msgs[i].type=="image"){
					    			  $('#scroller').prepend('<div class="yourFriend" ><img src="'+heads.headparth+'" class="_head" /><span>' + '<img class="messageIMGS" style="max-width:3.5rem;max-height:3.5rem;" onclick="showimgs(this)" src="'+obj.msgs[i].file.url+'"/></div>' + '</span></div>'); 
					    		  }else if(obj.msgs[i].type=="audio"){
					    			  $('#scroller').prepend('<div class="yourFriend" ><img src="'+heads.headparth+'" class="_head" /><span style="line-height:0.5rem;display:flex;">'+ '<img class="faudios" style="width:0.35rem;height:0.5rem;" FR="1" count="'+parseInt(obj.msgs[i].file.dur/1000)+'" onclick="fplayvoidce(this)" audio="'+obj.msgs[i].file.mp3Url+'" src="${path}/resource/img/images/FFFFFFFFFFFF.png"><span style="padding:0">'+parseInt(obj.msgs[i].file.dur/1000)+'\" '+'</span></span></div>');
					    		  }
				        	 }
				        }
				        myScroll.refresh();
				    }
				}
			});
			
	}
	
	//iscroll滚动获取聊天记录
	function pullDownAction(stamp) {
			 
				setTimeout(function() { 
					getHistroyList(historytimes,'',lastMSGid)
					myScroll.refresh(); 
				}, 1); 
			 
		}
		 function pullUpAction() {
				setTimeout(function() {
					myScroll.refresh(); 
				}, 1000); 
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
					/* firstEnter=false; */
			    },
				onScrollEnd: function() {
					if(myScroll.y==myScroll.maxScrollY){
						scrolled=true;
						myScroll.refresh();
					}else{
						scrolled=false;
					}
					var position='1';
				     position=this.y;
				     if(this.y!=0){
				    	 return;
				     } 
					 if($('#scroller').get(0).className.match('down')){
						$('#scroller').attr('class',"");
						 pullDownAction()
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
		
		//表情业务流程控制
		$("#emotion").click(function(){
		    $(".jihao ").removeClass("on");
				$(".hidePh").hide();
			if($(this).hasClass("emotion")){
				$(this).removeClass("emotion")
				$("#myemoji").show();
			}else{
				$(this).addClass("emotion")
				$("#myemoji").hide();
			}
		});
		 $('.sz').on('click',function(){
			 window.location.href = _path+ "/wx/businessFriend/talkInfo.do?follow_user="+fAccount;
		 })
		//最近联系人未读条数初始化
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
	</script>
</body>
</html>


