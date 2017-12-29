<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="user-scalable=1, initial-scale=1,maximum-scale=1, minimum-scale=1, width=device-width, height=device-height"/>
	<%@ include file="/WEB-INF/jsp/commons/include.com.jsp" %>
	<%@ include file="/WEB-INF/jsp/commons/include_login.jsp" %>
    <title>变更手机号</title>
    <style>
         strong,b{font-weight:normal;}
    </style>
</head>
<body>
<div class="wrap">
	<header>
    	<a href="${path}/m/qfy/mine/security.do"><span class="header_return"><img src="${path}/resource/images/return.png"></span></a>
        <h1>变更手机号</h1>
        
    </header>
	<section>
    	<div class="bgsjh_tu"><img src="${path}/resource/images/bgsjh1_03.png"></div>
    	<div class="bgsjh_hao">您的手机号：${user.mobile }</div>
    	<a href="${path}/m/qfy/changenext.do"><div class="button_box"><button type="button">变更手机号</button></div></a>
  </section>
    

</div>
</body>
</html>