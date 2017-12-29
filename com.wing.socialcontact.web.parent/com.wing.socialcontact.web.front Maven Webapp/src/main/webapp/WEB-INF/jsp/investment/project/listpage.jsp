<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<c:forEach var="item" items="${list}">
<div class="item active_A" data-id="${item.id}" onclick="showDetail(this)">
	<div style="background-image: url('${item.coverImg}');"></div>
	<div>
		<p>${item.titles}</p>
		<p>
			<span>${fns:fmt(item.startTime,'yyyy.MM.dd')}-${fns:fmt(item.endTime,'yyyy.MM.dd')}</span><br />
			<span><b>${item.extProps.willCount}</b>已报名</span>
		</p>
	</div>
	<br class="clear" />
</div>
</c:forEach>