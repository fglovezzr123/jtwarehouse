<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="user-scalable=no,initial-scale=1,maximum-scale=1, minimum-scale=1, width=device-width, height=device-height"/>
	<%@ include file="/WEB-INF/jsp/commons/include.com.jsp" %>
	<%@ include file="/WEB-INF/jsp/commons/include_login.jsp" %>
    <title>首页</title>
    <style>
         strong,b{font-weight:normal;}
    </style>
</head>
<body>
<div class="wrap">
	<header>
    	<span class="header_return"><img onclick="backForAndroid(1)" src="${path }/resource/images/return.png"></span>
        <h1>账号安全</h1>
        
    </header>
	<section>
        <div class="box_box zhaq_li"><span onclick="changePhone1()"><img src="${path }/resource/images/bianjixx_06.png"></span><span onclick="changePhone1()">${user.mobile }</span>手机号</div>
    	<div class="box_box zhaq_li" id="bindstatus"></div>
  </section>
</div>
</body>
<script type="text/javascript">
	var wxUnId = '${user.wxUniqueId}';
	if(wxUnId){
		$("#bindstatus").html('<span>已绑定</span>微信号');
	}else{
		$("#bindstatus").html('<span onclick="openNewUrl(2)"><img src="${path }/resource/images/bianjixx_06.png"></span><span onclick="backForAndroid()">未绑定</span>微信号');
	}
	
	function backForAndroid(type){
	  	//document.location.href= "${path}/m/qfy/mineIndex.do";
	  	if(type == 1){
			window.location.href='${path}/m/qfy/mineIndex.do';
		}else{
			window.location.href='${path}/m/qfy/bindWxAfter.do';
		}
	}
	
	/* function openNewUrl(type){
		if(type == 1){
			window.location.href='${path}/m/qfy/mineIndex.do';
		}else{
			window.location.href='${path}/m/qfy/bindWxAfter.do';
		}
	} */
	function changePhone1(){
		window.location.href='${path}/m/qfy/changePhone.do';
	}
</script>
</html>