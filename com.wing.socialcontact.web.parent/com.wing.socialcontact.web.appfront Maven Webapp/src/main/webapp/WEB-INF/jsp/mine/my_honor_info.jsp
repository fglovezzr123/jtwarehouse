<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="资讯">
		<title>勋章详情页</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/informationDetail.css?v=${sversion}" />
	</head>
	<body>
		<div class="T-infor" style="background: #f2f3f4;width: 100%; height:100%;">
			<div class="wrapper" id="wrapper">
				<div class="scrollbar" id="scrollbar">
				</div>
			</div>
			<h2 class="title">${honor.title}</h2>
			<p class="news"></p>
			<div class="newsContent">
				${honor.remark}
			</div>
		</div>
	</body>

</html>