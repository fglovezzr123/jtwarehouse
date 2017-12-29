<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div class="search-div" id="projectsignup_toolbar">
	<div class="search-content">
		<span>
			<label style="width:60px;">姓名：</label>
			<input	type="text" id="userName" class="span2"/>
		</span>
		<span>
			<label style="width:100px;">手机号：</label>
			<input	type="text" id="ss_mobile" class="span2"/>
		</span>
		<span>
			<label style="width: 100px;">报名时间：</label>
			<input id="createTimewill" style="width:90px;" size=15 class="Wdate time-field" 
				onfocus="WdatePicker({readOnly:true})"
				onClick="WdatePicker({readOnly:true})" 
				readonly="readonly"/>
			
		</span>
		<span style="padding-left:20px;">
			<button class="btn btn-primary btn-small" type="button" onclick="loadprojectSignup()">查询</button>&nbsp;
		</span>
		<span>
			<button class="btn btn-primary btn-small" type="button" onclick="doExportSignup(this)">导出报表</button>&nbsp;
		</span>
	</div>
</div>
<div style="display:none">
	<form id="export_signup_form" action="project/will/export.do" target="export_signup_hidden_frame">
		<input type="hidden" name="prjId" id="exp_prjId">
		<input type="hidden" name="userName" id="exp_userName">
		<input type="hidden" name="mobile" id="exp_mobile">
		<input type="hidden" name="createTime" id="exp_createTime">
	</form>
	<iframe id="export_signup_hidden_frame" name="export_signup_hidden_frame" width="0" height="0"/>
</div>
<table id="projectsignup_datagrid" toolbar="#projectsignup_toolbar"></table>
<script type="text/javascript">	
$(function() {
	loadprojectSignup();			
});
function loadprojectSignup(){
	$("#projectsignup_datagrid").datagrid({
		url : "project/signupquery.do",
		rownumbers: true,
		queryParams: { 
			prjId: "${prjId}",
			userName: $("#userName").val(),
			mobile: $("#ss_mobile").val(),
			createTime: $("#createTimewill").val()
		},
		columns : [[
			 {
				field : "titles2",
				title : "项目名称",
				width : $(this).width() * 0.25,
				align : "left"
			},{
				field : "willTypeName",
				title : "咨询类型",
				width : $(this).width() * 0.15,
				align : "center"
			},{
				field : "userName",
				title : "姓名",
				width : $(this).width() * 0.15,
				align : "center"
			},
			{
				field : "mobile",
				title : "手机号",
				width : $(this).width() * 0.15,
				align : "center"
			},
			{
				field : "comName",
				title : "所属公司",
				width : $(this).width() * 0.15,
				align : "left"
			},
			{
				field : "kfTelephone",
				title : "服务秘书",
				width : $(this).width() * 0.15,
				align : "left"
			},
			
			{
				field : "createTime",
				title : "咨询时间",
				width : $(this).width() * 0.15,
				align : "center",
				formatter : function(value, row, index) {
					return value!=null?new Date(value)
							.format("yyyy-MM-dd HH:mm"):"";
				}
			},
			{
				field : "status",
				title : "是否处理",
				width : $(this).width() * 0.12,
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
						return   "<a href = 'javaScript:void(0)' onclick = updateWilled('"+row.id+"')>编辑</a>"
					}
				}
			}
		]],
	});
}
function doExportSignup(obj){
	$("#exp_prjId").val("${prjId}");
	$("#exp_userName").val($("#userName").val());
	$("#exp_mobile").val($("#ss_mobile").val());
	$("#exp_createTime").val($("#createTimewill").val());
	$("#export_signup_form").submit();
}
//编辑
function updateWilled(fid){
	var params = { closed: false,cache: false,modal:true,width:800,height:500,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('编辑','project/signup/detail.do?id='+fid,"willdetailpage",params)
}
</script>