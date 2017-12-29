<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<div class="search-div" id="vhall_toolbar">
	<div class="search-content">
		<span style="padding-left:20px;">
			<button class="btn btn-primary btn-small" type="button" onclick="sureVhall()">确定</button>&nbsp;
			<button class="btn btn-primary btn-small" type="button" onclick="closeVhall()">取消</button>&nbsp;
		</span>
	</div>
</div>
<table id="vhall_datagrid"></table>
<script type="text/javascript">	
$(function() {
	loadWebinarList();			
});
function loadWebinarList(){
	$("#vhall_datagrid").datagrid({
		url : "news/vhallquery.do",
		fitColumns: false,
		rownumbers: true,
		singleSelect: true,
		selectOnCheck:true,
		columns : [[{
				field : "ck",
				title : "勾选",
				width : 10,
				checkbox : true
			},
			{
				field : "webinar_id",
				title : "视频ID",
				width : 230,
				align : "center",
			},{
				field : "subject",
				title : "标题",
				width : 230,
				align : "center"
			},
			{
				field : "status",
				title : "直播状态",
				width : 120,
				align : "center",
				formatter : function(value, row, index) {
					// 1:直播进行中,2:预约中,3:结束
					if(4==value){
						return "点播";
					}else{
						return "未知";
					}
				}
			}
		]],
	});
}
function sureVhall(){
	var rows = $("#vhall_datagrid").datagrid("getChecked");
	if(rows&&rows.length==0){
		alert("请先选择一个视频");
	}else{
		$("#mau_webinarId").val(rows[0].webinar_id);
		$("#mau_webinarSubject").val(rows[0].subject);
		closeVhall();
	}
}
function closeVhall(){
	$("#vhallindexfornews").dialog("destroy");
}
</script>