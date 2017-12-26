<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="user-scalable=no, initial-scale=1,maximum-scale=1, minimum-scale=1, width=device-width, height=device-height"/>
	<%@ include file="/WEB-INF/jsp/commons/include.com.jsp" %>
	<%@ include file="/WEB-INF/jsp/commons/include_login.jsp" %>
    <title>我的</title>
    <style>
         strong,b{font-weight:normal;}
         
    </style>
</head>
  <body>
  	<header>
    	<a href="javaScript:void(0)" onclick="backForAndroid()"><span class="header_return"><img src="${path}/resource/images/return.png"></span></a>
    	<h1>关于企服联盟</h1>
    </header>
	<section>
    	<div class="box_box yjfh_box">
		    <img onclick="openNewUrl()" src="${dto.imgPath}" style="width:100%;height:92%">
<!-- 		    <img onclick="openNewUrl()" src="${dto.imgPath}" style="width:100%;height:92%"> -->
        </div>
    </section>
  </body>
</html>
<script type="text/javascript">
  function backForAndroid(){
  	document.location.href= "${path}/m/qfy/mineIndex.do";
  }
  function openNewUrl(){
		window.location.href='${dto.link }'; 
  }
</script>
