<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<c:if test="${type eq 1}">
<c:if test="${empty grid or grid.rows.size() eq 0}">
<div style="top:0;left: 50%;text-align: center;width: 100%;font-size: .28rem;color:#808080;">您还没有收藏的项目</div>
</c:if>
<c:forEach items="${grid.rows}" var="item">
<div class="print-meeting-box" data-id="${item.id}" onclick="detailProject(this)">
	<%-- <div><img style="height:1.70rem;" src="${item.coverImg}"></div> --%>
	<div>
		<h3>${item.titles}</h3>
		<div style="margin-top:.76rem;">
			<span>${fns:fmt(item.startTime,'yyyy/MM/dd')}-${fns:fmt(item.endTime,'yyyy/MM/dd')}</span>
			<span data-id="${item.id}" onclick="attentionDelMeeting(this)" class="active_A">取消收藏</span>
			<br class="clear">
		</div>
	</div>
	<br class="clear">
</div>
</c:forEach>
</c:if>

<c:if test="${type eq 2}">
<c:if test="${empty grid or grid.rows.size() eq 0}">
<div style="top:0;left: 50%;text-align: center;width: 100%;font-size: .28rem;color:#808080;">您还没有意向的项目</div>
</c:if>
<c:forEach items="${grid.rows}" var="item">
<div class="print-meeting-box" data-id="${item.id}" onclick="detailProject(this)">
	<%-- <div><img style="height:1.70rem;" src="${item.coverImg}"></div> --%>
	<div>
		<h3>${item.titles}</h3>
		<div style="margin-top: 0px;font-size: .16rem;color:#808080;line-height: 0.35rem;">
	<c:choose>
		<c:when test="${not empty item.extProps.will}">
			<p>处理状态：${1==item.extProps.will.status?'已处理':'未处理'}</p>
			<p>我的意向：${item.extProps.will.willTypeName}</p>
			<p>时间：${fns:fmt(item.extProps.will.createTime,'yyyy/MM/dd')}</p>
			<p>个人说明：${item.extProps.will.willDesc}</p>
		</c:when>
		<c:otherwise>
			<p>&nbsp;</p>
			<p>&nbsp;</p>
			<p>&nbsp;</p>
			<p>&nbsp;</p>
		</c:otherwise>
	</c:choose>
		</div>
	</div>
	<br class="clear">
</div>
</c:forEach>
</c:if>

<c:if test="${type eq 3}">
<c:if test="${empty grid or grid.rows.size() eq 0}">
<div style="top:0;left: 50%;text-align: center;width: 100%;font-size: .28rem;color:#808080;">您还没自荐过项目</div>
</c:if>
<c:forEach items="${grid.rows}" var="item">
<div class="print-meeting-box" onclick="detailProjectRecommend(this)" data-id="${item.id}">
	 <%-- <div><img style="height:1.70rem;" src="${item.imgUrl}"></div> --%>
	<div>
		 <h3>${item.prjName}</h3>
		<div style="margin-top:0px;font-size: .16rem;color:#808080;line-height: 0.35rem;">
			<p>项目类型：${item.prjTypeName}</p>
			<p>时间：${fns:fmt(item.createTime,'yyyy/MM/dd')}</p>
			<p>处理状态：${1==item.status?'通过':(0==item.status?'不通过':'未审核')}</p>
		</div>
	</div> 
	    
	<br class="clear">
</div>
</c:forEach>
</c:if>
<script>
updatePages('${type}','${grid.pageNum}','${grid.pages}');
</script>