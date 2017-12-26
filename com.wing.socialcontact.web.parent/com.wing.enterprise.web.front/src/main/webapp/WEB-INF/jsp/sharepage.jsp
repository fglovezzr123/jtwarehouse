<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="user-scalable=1, initial-scale=1,maximum-scale=1, minimum-scale=1, width=device-width, height=device-height"/>
	<%@ include file="/WEB-INF/jsp/commons/include.com.jsp" %>
    <title></title>
    <style>
         strong,b{font-weight:normal;}
    </style>
</head>
<body>
<div class="wrap">
	<%-- <header>
    	<span class="header_return"><img src="${path}/resource/images/return.png"></span>
        <h1>分享页配置</h1>
    </header> --%>
	<section>
    	<div class="fenxiang_lj_box">
            <h2>${dto.title }</h2>
            <span class="fxlj_img"><img src="${dto.imagePath }"></span>
            <div class="fxlj_pp">${dto.content }</div>
        </div>
    </section>
    

</div>
</body>
</html>