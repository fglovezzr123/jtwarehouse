<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.getSession().setAttribute("path", path);
%>
<style>
	* { margin:0; padding:0;}
	.hyjb_box {}
	.hyjb_dl {float:left; margin: 12px 12px; width:125px; list-style:none; }
	.hyjb_dl dl { position:relative; width:125px; height:105px; }
	.hyjb_dl dl dt { width:120px; height:100px; border:1px solid #dddddd;}
	.hyjb_dl dl dd { position:absolute; right:-10px; top:-10px; }
	.hyjb_dl .hyjb_shuru { width:125px; margin-top:-22px;}
	.hyjb_dl .hyjb_shuru input { border:none; width:110px; height:26px; background:#f7f7f7; text-align:center;}
	.hyjb_tj { float:left; margin:5px 0px 0 5px; }
	#dataForm img{width:100%;height:100%;}
	.ke-icon-tel {
		background-image: url(resource/js/editor/themes/default/timg.jpg);
		width: 16px;
		height: 16px;
	}
	.distant1 {
		color: green;
		font-weight: bold;
		text-shadow: 0 0 1px currentColor,-1px -1px 1px #030,0 -1px 1px #030,1px -1px 1px #030,1px 0 1px #030,1px 1px 1px #030,0 1px 1px #030,-1px 1px 1px #030,-1px 0 1px #030;
	}
</style>

<div style="width:100%;">
	<form  id="dataForm">

		<input type="hidden" name="id" value="${obj.id}"/>
		<input type="hidden" name="createTime"  value="${fns:fmt(obj.createTime,'yyyy-MM-dd HH:mm:ss')}">
		<input type="hidden" name="createUserId"  value="${obj.createUserId}">
		<input type="hidden" name="updateTime" value="${fns:fmt(obj.updateTime,'yyyy-MM-dd HH:mm:ss')}">
		<input type="hidden" name="updateUserId"  value="${obj.updateUserId}">
		<input type="hidden" name="images" id="images">
		<table class="table table-bordered" style="margin-top:0px;width:99%;">

			<tr>
				<th style="width:15%;">用户姓名：</th>
				<td colspan=3>
					${obj.trueName}
				</td>
			</tr>
			<tr>
				<th>照片：</th>
				<td colspan=3>
					<img style="height: 500px;width: 500px;" src="${ossUrl}${obj.photoUrl}"/>
				</td>
			</tr>

			<tr>
				<th>审核状态：</th>
				<td>
					<c:if test="${0==obj.status}">未通过</c:if>
					<c:if test="${1==obj.status}">通过</c:if>
				</td>
			</tr>

		</table>
	</form>
	<span class="typefile" id="filePicker"></span>
</div>
<script src="http://malsup.github.io/jquery.form.js"></script>