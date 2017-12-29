<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div class="search-div" id="projectwill_toolbar">
	<div class="search-content">
		<span>
			<label style="width:60px;">姓名：</label>
			<input	type="text" id="projectwill_userName" class="span2"/>
		</span>
		<span>
			<label style="width:100px;">手机号：</label>
			<input	type="text" id="projectwill_mobile" class="span2"/>
		</span>
		<span>
			<label style="width: 100px;">报名时间：</label>
			<input id="projectwill_createTimewill" style="width:90px;" size=15 class="Wdate time-field" 
				onfocus="WdatePicker({readOnly:true})"
				onClick="WdatePicker({readOnly:true})" 
				readonly="readonly"/>
			
		</span>
		<span style="padding-left:20px;">
			<button class="btn btn-primary btn-small" type="button" onclick="loadprojectWill()">查询</button>&nbsp;
		</span>
		<span>
			<button class="btn btn-primary btn-small" type="button" onclick="doExportWill(this)">导出报表</button>&nbsp;
		</span>
	</div>
</div>
<div style="display:none">
	<form id="export_will_form" action="project/will/export.do" target="export_will_hidden_frame">
		<input type="hidden" name="prjId" id="exp_projectwill_prjId">
		<input type="hidden" name="userName" id="exp_projectwill_userName">
		<input type="hidden" name="mobile" id="exp_projectwill_mobile">
		<input type="hidden" name="createTime" id="exp_projectwill_createTime">
	</form>
	<iframe id="export_will_hidden_frame" name="export_will_hidden_frame" width="0" height="0"/>
</div>
<table id="projectwill_datagrid" toolbar="#projectwill_toolbar"></table>
<script type="text/javascript">	
$(function() {
	loadprojectWill();			
});
function loadprojectWill(){
	$("#projectwill_datagrid").datagrid({
		url : "project/signupquery.do",
		rownumbers: true,
		fitColumns: false,
		queryParams: { 
			prjId: "0",
			userName: $("#projectwill_userName").val(),
			mobile: $("#projectwill_mobile").val(),
			createTime: $("#projectwill_createTimewill").val()
		},
		columns : [[
			 {
				field : "titles2",
				title : "项目名称",
				width : 300,
				align : "left"
			},
			{
				field : "willTypeName",
				title : "咨询类型",
				width : 100,
				align : "center"
			},
			{
				field : "userName",
				title : "姓名",
				width : 100,
				align : "center"
			},
			{
				field : "mobile",
				title : "手机号",
				width : 100,
				align : "center"
			},
			{
				field : "comName",
				title : "所属公司",
				width : 150,
				align : "center"
			},
			{
				field : "kfTelephone",
				title : "服务秘书电话",
				width : $(this).width() * 0.15,
				align : "left"
			},
			{
				field : "createTime",
				title : "咨询时间",
				width : 120,
				align : "center",
				formatter : function(value, row, index) {
					return value!=null?new Date(value)
							.format("yyyy-MM-dd HH:mm"):"";
				}
			},
			{
				field : "status",
				title : "是否处理",
				width : 60,
				align : "center",
				formatter : function(value, row, index) {
					return 1==value?"是":"否";
				}
			},
			{
				field : "op",
				title : "操作",
				width : 100,
				align : "center",
				formatter : function(value, row, index) {
					if(2==row.status){
						
					}else{
						return   "<a href = 'javaScript:void(0)' onclick = updateWill('"+row.id+"')>编辑</a>"
					}
				}
			}
		]],
	});
}
//编辑
function updateWill(fid){
	var params = { closed: false,cache: false,modal:true,width:800,height:500,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('编辑','project/signup/detail.do?id='+fid,"willdetailpage",params)
}
function doExportWill(obj){
	$("#exp_projectwill_prjId").val("0");
	$("#exp_projectwill_userName").val($("#projectwill_userName").val());
	$("#exp_projectwill_mobile").val($("#projectwill_mobile").val());
	$("#exp_projectwill_createTime").val($("#projectwill_createTimewill").val());
	$("#export_will_form").submit();
}
</script>