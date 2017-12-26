<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
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
		url : "${path}/meeting/vhall/query.do",
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
				title : "活动ID",
				width : 100,
				align : "center",
			},{
				field : "subject",
				title : "直播标题",
				width : 100,
				align : "center"
			},{
				field : "start_time",
				title : "开始时间",
				width : 100,
				align : "center"
			},
			{
				field : "status",
				title : "直播状态",
				width : 50,
				align : "center",
				formatter : function(value, row, index) {
					// 1:直播进行中,2:预约中,3:结束
					if(1==value){
						return "直播进行中";
					}else if(2==value){
						return "预约中";
					}else if(3==value){
						return "结束";
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
		alert("请先选择视频");
	}else{
		$("#mau_webinarId").val(rows[0].webinar_id);
		$("#mau_webinarSubject").val(rows[0].subject);
		closeVhall();
	}
}
function closeVhall(){
	$("#vhallindexformeeting").dialog("destroy");
}
</script>