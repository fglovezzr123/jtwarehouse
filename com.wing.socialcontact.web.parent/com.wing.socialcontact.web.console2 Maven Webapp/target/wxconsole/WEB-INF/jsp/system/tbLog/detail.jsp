<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div id="logToolbar" class="search-div">
	<div class="search-content">
		<input type="hidden" name="id" id="id" value="${obj.id}"/>
		<span>
			<label style="width: 80px;">操作人：</label>
			${obj.trueName}
		</span>
		<span>
			<label style="width: 80px;">操作表：</label>
			${obj.comment}（${obj.tableName})
		</span>
		<span>
			<label style="width: 80px;">操作类型：</label>
			<c:if test="${1==obj.type}">新建</c:if>
			<c:if test="${2==obj.type}">更新</c:if>
			<c:if test="${3==obj.type}">删除</c:if>
		</span>
	</div>
</div>
<table id="logDatagrid" toolbar="logToolbar"></table>
<script type="text/javascript">	
$(function() {
	loadLogContent();
});
function loadLogContent(){
	$("#logDatagrid").datagrid({
		url : "tbLog/queryTbLogContentList.do",
		rownumbers: true,  //行号
		queryParams: {
			logId:$("#id").val()
		},
		fitColumns: false,
		columns : [[
			{
				field : "tbKey",
				title : "字段",
				width : 200,
				align : "left"
			},{
				field : "oldTbValue",
				title : "旧值",
				width : 160,
				align : "center"
			},{
				field : "currentTbValue",
				title : "新值",
				width : 160,
				align : "center"
			}
		]],
	});
}

</script>