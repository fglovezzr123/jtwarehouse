<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0"/>
    <meta content="yes" name="apple-mobile-web-app-capable" /> 
    <meta content="black" name="apple-mobile-web-app-status-bar-style" /> 
    <meta content="telephone=no" name="format-detection" />
	<title></title>
	<style>
	    body{
	       width:100%;
	    }
	    img{
	        width:100%;
	        height:100%;
	    }
	</style>
</head>
<body>
	<img src="${path}/resource/img/images/waitting.jpg"/>
    <script>
         $("img").click(function(){
        	 
        	 window.location.href="${path}/wx/index.do";
         })
    </script>
</body>
</html>