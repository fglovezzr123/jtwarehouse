<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div id="projectzjindex_toolbar" class="search-div">
	<div class="search-content">
		<span>
			<label style="width: 80px;">项目名称：</label>
			<input type="text" id="zj_prjName" style="width:120px;"/>
		</span>
		<span>
			<label style="width: 80px;">提交人：</label>
			<input type="text" id="zj_userName" style="width:120px;"/>
		</span>
		<span>
			<label style="width: 80px;">是否处理：</label>
			<select id="zj_status" style="width:70px;">
				<option value="">全部</option>
				<option value="2">未审核</option>
				<option value="1">通过</option>
				<option value="0">不通过</option>
			</select>
		</span>
		<span>
			<label style="width: 80px;">项目类型：</label>
			<select id="zj_prj_type" style="width:180px;">
				<option value="">全部</option>
				<c:forEach items="${list }" var="t">
				<option value="${t.id }">${t.listValue }</option>
				</c:forEach>
			</select>
		</span>
		<span>
			<label style="width: 100px;">提交时间：</label>
			<input id="zj_createTime" style="width:90px;" size=15 class="Wdate time-field" 
				onfocus="WdatePicker({readOnly:true})"
				onClick="WdatePicker({readOnly:true})" 
				readonly="readonly"/>
		</span>
		<span style="padding-left:20px;">
			<button class="btn btn-primary btn-small" type="button" onclick="loadProject()">查询</button>&nbsp;
		</span>
		<shiro:hasPermission name="newsclass:update">
		<span>
			<button class="btn btn-primary btn-small" type="button" onclick="exportRecommend()">导出报表</button>&nbsp;
		</span>
		</shiro:hasPermission>
	</div>
</div>
<div style="display:none">
	<form id="export_collect_form" action="project/collect/export.do" target="export_collect_hidden_frame">
		<input type="hidden" name="prjName" id="exp_collect_prjName">
		<input type="hidden" name="userName" id="exp_collect_userName">
		<input type="hidden" name="status" id="exp_collect_status">
		<input type="hidden" name="createTime" id="exp_collect_createTime">
	</form>
	<iframe id="export_collect_hidden_frame" name="export_collect_hidden_frame" width="0" height="0"/>
</div>
<table id="projectzjindex_datagrid" toolbar="#projectzjindex_toolbar"></table>
<script type="text/javascript">	
$(function() {
	loadProject();			
});
function loadProject(){
	$("#projectzjindex_datagrid").datagrid({
		url : "project/collectquery.do",
		rownumbers: true,
		queryParams: { 
			prjName: $("#zj_prjName").val(),
			userName: $("#zj_userName").val(),
			status: $("#zj_status").val(),
			createTime: $("#zj_createTime").val(),
			prjType: $("#zj_prj_type").val(),
		},
		fitColumns: false,
		columns : [[
			 {
				field : "prjName",
				title : "项目名称",
				width : 200,
				align : "left",
			},{
				field : "prjTypeName",
				title : "项目类别",
				width : 120,
				align : "center"
			},{
				field : "userName",
				title : "提交人",
				width : 60,
				align : "center"
			},{
				field : "mobile",
				title : "联系方式",
				width : 100,
				align : "center"
			},{
				field : "comName",
				title : "认证企业",
				width : 150,
				align : "left"
			},
			{
				field : "kfTelephone",
				title : "服务秘书电话",
				width : $(this).width() * 0.15,
				align : "left"
			},
			{
				field : "status",
				title : "处理状态",
				width : 60,
				align : "center",
				formatter : function(value, row, index) {
					
					if(0==value){
						return "不通过";
					}else if(1==value){
						return "通过";
					}else{
						return "未审核";
					}
				}
			},
			{
				field : "showEnable",
				title : "是否显示",
				width : 60,
				align : "center",
				formatter : function(value, row, index) {
					return 1==value?"是":"否";
				}
			},
			{
				field : "createTime",
				title : "提交时间",
				width : 120,
				align : "center",
				formatter : function(value, row, index) {
					return value==null?"":new Date(value).format("yyyy-MM-dd HH:mm:ss");
				}
			},
			{
				field : "op",
				title : "操作",
				align:"center",
				width : 50,
				formatter: function(value, row, index){
					return   "<a href = 'javaScript:void(0)' onclick = updateProject('"+row.id+"')>编辑</a>";
				}
			}
		]],
	});
}
function exportRecommend(){
	$("#exp_collect_prjName").val($("#zj_prjName").val());
	$("#exp_collect_userName").val($("#zj_userName").val());
	$("#exp_collect_status").val($("#zj_status").val());
	$("#exp_collect_createTime").val($("#zj_createTime").val());
	$("#export_collect_form").submit();
}
//编辑
function updateProject(fid){
	var params = { closed: false,cache: false,modal:true,width:1000,height:600,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('编辑项目','project/collect/detail.do?id='+fid,"collectdetailpage",params)
}
</script>