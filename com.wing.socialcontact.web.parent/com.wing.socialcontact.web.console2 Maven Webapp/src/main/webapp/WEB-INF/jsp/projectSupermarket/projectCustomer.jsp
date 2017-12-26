<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div id="toolbar" class="search-div">
	<div class="search-content">
		<input type="hidden" name="fkId" id="fkId" value="${fkId}"/>
		<span>
			<label style="width: 80px;">姓名：</label>
			<input type="text" id="trueName" style="width:120px;"/>
		</span>
		<span>
			<label style="width: 80px;">手机号码：</label>
			<input type="text" id="mobile" style="width:120px;"/>
		</span>
		<span>
			<label style="width: 80px;">是否处理：</label>
			<select id="isHandle" style="width:70px;">
				<option value="">全部</option>
				<option value="1">是</option>
				<option value="0">否</option>
			</select>
		</span>
		<span>
			<label style="width: 100px;">提交时间：</label>
			<input id="createTime" style="width:90px;" size=15 class="Wdate time-field" 
				onfocus="WdatePicker({readOnly:true})"
				onClick="WdatePicker({readOnly:true})" 
				readonly="readonly"/>
		</span>
		<span style="padding-left:20px;">
			<button class="btn btn-primary btn-small" type="button" onclick="loadCustomer()">查询</button>&nbsp;
		</span>
		<span style="padding-left:20px;">
			<button class="btn btn-primary btn-small" type="button" onclick="exportExcel()">导出</button>&nbsp;
		</span>
		<span style="padding-left:20px;">
			<button class="btn btn-primary btn-small" type="button" onclick="handelData()">批量处理</button>&nbsp;
		</span>
	</div>
</div>
<div style="display:none">
	<form id="export_form" action="projectSupermarket/export.do" target="export_hidden_frame">
		<input type="hidden" name="fkId" id="exp_fkId">
		<input type="hidden" name="trueName" id="exp_trueName">
		<input type="hidden" name="mobile" id="exp_mobile">
		<input type="hidden" name="isHandle" id="exp_isHandle">
		<input type="hidden" name="createTime" id="exp_createTime">
	</form>
	<iframe id="export_hidden_frame" name="export_hidden_frame" width="0" height="0"/>
</div>
<table id="datagrid" toolbar="toolbar"></table>
<script type="text/javascript">	
$(function() {
	loadCustomer();
});
function loadCustomer(){
	$("#datagrid").datagrid({
		url : "projectSupermarket/projectCustomerQuery.do",
		rownumbers: true,  //行号
		singleSelect: false, //允许选择多行
		selectOnCheck: true,//true勾选会选择行，false勾选不选择行, 1.3以后有此选项。重点在这里
		checkOnSelect: false, //true选择行勾选，false选择行不勾选, 1.3以后有此选项
		queryParams: {
			fkId:$("#fkId").val(),
			trueName: $("#trueName").val(),
			mobile: $("#mobile").val(),
			isHandle: $("#isHandle").val(),
			createTime: $("#createTime").val()
		},
		fitColumns: false,
		columns : [[
			{field:'ck',checkbox:true},
			{
				field : "name",
				title : "项目名称",
				width : 200,
				align : "left",

			},
			{
				field : "trueName",
				title : "姓名",
				width : 100,
				align : "center"
			},{
				field : "mobile",
				title : "手机号码",
				width : 160,
				align : "center"
			},{
				field : "comName",
				title : "所属公司",
				width : 260,
				align : "center"
			},{
				field : "jobName",
				title : "职位",
				width : 260,
				align : "center"
			},{
				field : "kfTelephone",
				title : "服务秘书电话",
				width : 160,
				align : "center"
			},
			{
				field : "createTime",
				title : "提交时间",
				width : 120,
				align : "center",
				formatter : function(value, row, index) {
					return value==null?"":new Date(value).format("yyyy-MM-dd HH:mm");
				}
			},

			{
				field : "isHandle",
				title : "是否处理",
				width : 60,
				align : "center",
				formatter : function(value, row, index) {
					return 1==value?"是":"否";
				}
			}
		]],
	});
}


function exportExcel(){
	$("#exp_fkId").val($("#fkId").val());
	$("#exp_trueName").val($("#trueName").val());
	$("#exp_mobile").val($("#mobile").val());
	$("#exp_isHandle").val($("#isHandle").val());
	$("#exp_createTime").val($("#createTime").val());
	$("#export_form").submit();
}

//批量处理
function handelData() {
	//返回选中多行
	var selRow = $('#datagrid').datagrid('getSelections')

	//判断是否选中行
	if (selRow.length==0) {
		$.messager.alert("提示", "请选择要处理的行！", "info");
		return;
	}else{
		var ids ;
		//循环给提交删除参数赋值
		for( var i  =0;i<selRow.length;i++ ){
			if (i == 0) {
				ids =  selRow[i].id;
			} else {
				ids = selRow[i].id + "," + ids;
			}
		}

		$.messager.confirm('提示', '是否处理选中数据?', function (r) {

			if (!r) {
				return;
			}
			//提交
			$.ajax({
				type: "POST",
				async: false,
				url: "/projectSupermarket/projectCustomerHandle.do",
				data: {ids:ids},
				dataType:"json",
				success: function (json) {
					if(json&&json["code"]==="0"){
						$('#datagrid').datagrid('clearSelections');
						$.messager.alert("提示", "批量处理成功！", "info");
						$('#datagrid').datagrid('reload');
					} else {
						$.messager.alert("提示", "批量处理失败，请重新操作！", "info");
						return;
					}
				}
			});
		});

	}
};

</script>