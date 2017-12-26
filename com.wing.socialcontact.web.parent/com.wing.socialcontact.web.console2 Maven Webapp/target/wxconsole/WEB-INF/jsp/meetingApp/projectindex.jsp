<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div class="search-div" id="projectformeeting_toolbar">
	<div class="search-content">
		<span>
			<label style="width: 80px;">项目名称：</label>
			<input type="text" id="mps_titles" style="width:120px;"/>
		</span>
		<span>
			<label style="width: 80px;">项目类别：</label>
			<select id="mps_prjType" style="width:120px;">
				<option value="">全部</option>
				<option value="8006001">项目联营</option>
				<option value="8006002">项目孵化</option>
			</select>
		</span>
		<span>
			<label style="width: 70px;">开始时间：</label>
			<input id="mps_startTime" name="startTime" style="width:90px;" size=15 class="Wdate time-field" 
				onfocus="WdatePicker({readOnly:true})"
				onClick="WdatePicker({readOnly:true})" 
				readonly="readonly"/>
			
		</span>
		<span>
			<label style="width: 70px;">结束时间：</label>
			<input id="mps_endTime" name="startTime" style="width:90px;" size=15 class="Wdate time-field" 
				onfocus="WdatePicker({readOnly:true})"
				onClick="WdatePicker({readOnly:true})" 
				readonly="readonly"/>
			
		</span>
		<span style="padding-left:20px;">
			<button class="btn btn-primary btn-small" type="button" onclick="loadProjectformeeting()">查询</button>&nbsp;
			<button class="btn btn-primary btn-small" type="button" onclick="sureProjectformeeting()">确定</button>&nbsp;
			<button class="btn btn-primary btn-small" type="button" onclick="closeProjectformeeting()">取消</button>&nbsp;
		</span>
	</div>
</div>
<table id="projectformeeting_datagrid" toolbar="#projectformeeting_toolbar"></table>
<script type="text/javascript">	
$(function() {
	loadProjectformeeting();			
});
function loadProjectformeeting(){
	$("#projectformeeting_datagrid").datagrid({
		url : "project/projectquery.do",
		queryParams: { 
			titles: $("#mps_titles").val(),
			prjType: $("#mps_prjType").val(),
			leStartTime: $("#mps_startTime").val(),
			geEndTime: $("#mps_endTime").val()
		},
		pagination: true,
		rownumbers: true,
		pageList:[5,10,15,20,30],
		pageSize:10,
		columns : [[{
				field : "ck",
				title : "勾选",
				width : 10,
				checkbox : true
			},
			{
				field : "titles",
				title : "项目名称",
				width : 100,
				align : "left",
			},{
				field : "prjType",
				title : "项目类别",
				width : 60,
				align : "center",
				formatter : function(value, row, index) {
					return 8006001==value?"项目联营":"项目孵化";
				}
			},{
				field : "statusDesc",
				title : "项目状态",
				width : 100,
				align : "center"
			},
			{
				field : "isRecommend",
				title : "是否推荐",
				width : 50,
				align : "center",
				formatter : function(value, row, index) {
					return 1==value?"推荐":"不推荐";
				}
			},
			{
				field : "recommendEnable",
				title : "是否显示",
				width : 50,
				align : "center",
				formatter : function(value, row, index) {
					return 1==value?"是":"否";
				}
			},
			{
				field : "createTime",
				title : "创建时间",
				width : 100,
				align : "center",
				formatter : function(value, row, index) {
					return value==null?"":new Date(value).format("yyyy-MM-dd HH:mm");
				}
			}
		]],
	});
}
function sureProjectformeeting(){
	var rows = $("#projectformeeting_datagrid").datagrid("getChecked");
	if(rows&&rows.length==0){
		alert("请先选择项目");
	}else{
		var arr=[];
		for(var i=0;i<rows.length;i++){
			var row = rows[i];
			arr.push({id: row.id, titles: row.titles, prjType: row.prjType,prjTypeName: row.prjTypeName});
		}
		addProject(arr);
		closeProjectformeeting();
	}
}
function closeProjectformeeting(){
	$("#projectindexformeeting").dialog("destroy");
}
</script>