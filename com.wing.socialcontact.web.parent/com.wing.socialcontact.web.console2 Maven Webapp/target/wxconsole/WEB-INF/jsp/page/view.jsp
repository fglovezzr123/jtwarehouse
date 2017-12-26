<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理-- 聚合页面管理--添加
--%>
<div style="width: 98%;margin: 20px auto;" >
	<iframe src="${pageUrl }&viewFlag=1" width="100%" height="540" frameborder="no" border="0"></iframe>
</div>
