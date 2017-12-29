<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>

<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="keywords" content="评论">
<title>评论</title>
<link rel="stylesheet" type="text/css"
	href="${path}/resource/css/libs/public.css?v=${sversion}" />
<link rel="stylesheet" type="text/css"
	href="${path}/resource/css/informationDetail.css?v=${sversion}" />
<script src="${path}/resource/js/libs/jquery-2.2.3.min.js?v=${sversion}"
	type="text/javascript" charset="utf-8"></script>
	<link rel="stylesheet" type="text/css" href="${path}/resource/css/index.css?v=${sversion}" />
</head>

<body>
	<div class="T-infor"
		style="background: #f2f3f4;width: 100%; height:100%;">
		<jsp:include page="../commons/include_comment.jsp">
			<jsp:param name="id" value="${id}" />
			<jsp:param name="cmeType" value="4" />
		</jsp:include><%--cmeType 1:资讯   2：合作 3：话题  4：活动 --%>

	</div>
	<script src="${path}/resource/js/libs/public.js" type="text/javascript"
		charset="utf-8"></script>
	<script src="${path}/resource/js/libs/swiper-3.3.1.min.js"
		type="text/javascript" charset="utf-8"></script>
</body>
