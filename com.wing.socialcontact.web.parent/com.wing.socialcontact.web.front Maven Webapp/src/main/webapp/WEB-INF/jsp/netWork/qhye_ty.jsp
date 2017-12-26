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
	<script type="text/javascript">
		var url="${url}";
		if(!isEmpty(url)){
			self.location.href=url;
		}
	</script>
</head>
<body>
	当前页面已停用或不存在
</body>
</html>