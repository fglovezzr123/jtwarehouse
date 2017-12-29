<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0"/>
    <meta content="yes" name="apple-mobile-web-app-capable" /> 
    <meta content="black" name="apple-mobile-web-app-status-bar-style" /> 
    <meta content="telephone=no" name="format-detection" />
	<title>错误导航</title>
	<style>
	body{
		font-size: 18;
		text-align:center;
		padding-top:1.5rem;
		color: red;
	}
	</style>
</head>
<body>
	<c:choose>
		<c:when test="${empty error_info}">
			出错了，请在微信中进行注册
		</c:when>
		<c:otherwise>
			${error_info }
		</c:otherwise>
	</c:choose>
</body>
</html>