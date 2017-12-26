<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div id="projectindex_toolbar" class="search-div">
	<div class="search-content">
		<span>
			<label style="width: 80px;">项目名称：</label>
			<input type="text" id="name" style="width:120px;"/>
		</span>

		<span>
			<label style="width: 80px;">合作模式：</label>
			<select  name="cooperativeMode"  id="cooperativeMode" >
				<option value="">全部</option>
				<c:forEach items="${cooperativeModeList}" var="p">
					<option id="${p.id }" value="${p.id }" <c:if test="${p.id==obj.cooperativeMode}">selected</c:if>>${p.listValue} </option>
				</c:forEach>
			</select>
		</span>
		<span>
			<label style="width: 80px;">所在行业：</label>
			<select name="industry" id="industry">
				<option value="">全部</option>
				<c:forEach items="${industryList}" var="p">
					<option id="${p.id }" <c:if test="${p.id==obj.industry}">selected</c:if> value="${p.id }">${p.listValue } </option>
				</c:forEach>
			</select>
		</span>
		<span>
			<label style="width: 20px;">省：</label>
			<select  name="province"  id="province">
				<option value="">全部</option>
				<c:forEach items="${provinceList}" var="p">
					<option id="${p.id}" value="${p.id}" <c:if test="${p.id==obj.province}">selected</c:if>>${p.disName}</option>
				</c:forEach>
			</select>
		</span>
		<span>
			<label style="width: 80px;">是否显示：</label>
			<select id="isShow" style="width:70px;">
				<option value="">全部</option>
				<option value="0">是</option>
				<option value="1">否</option>
			</select>
		</span>
		<span>
			<label style="width: 50px;">置顶：</label>
			<select id="isTop" style="width:70px;">
				<option value="">全部</option>
				<option value="1">是</option>
				<option value="0">否</option>
			</select>
		</span>
		<span>
			<label style="width: 100px;">创建时间：</label>
			<input id="createTime" style="width:90px;" size=15 class="Wdate time-field" 
				onfocus="WdatePicker({readOnly:true})"
				onClick="WdatePicker({readOnly:true})" 
				readonly="readonly"/>
		</span>
		<span style="padding-left:20px;">
			<button class="btn btn-primary btn-small" type="button" onclick="load()">查询</button>&nbsp;
		</span>
	   <span style="padding-left:20px;">
			<button class="btn btn-primary btn-small" type="button" onclick="add()">新建</button>&nbsp;
		</span>
		<span style="padding-left:20px;">
			<button class="btn btn-primary btn-small" type="button" onclick="deleteData()">刪除</button>&nbsp;
		</span>
	</div>
</div>
<table id="project_datagrid" toolbar="#project_toolbar"></table>
<script type="text/javascript">

	//刪除
function deleteData() {
		//返回选中多行
		var selRow = $('#project_datagrid').datagrid('getSelections')

		//判断是否选中行
		if (selRow.length==0) {
			$.messager.alert("提示", "请选择要删除的行！", "info");
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

			$.messager.confirm('提示', '是否删除选中数据?', function (r) {

				if (!r) {
					return;
				}
				//提交
				$.ajax({
					type: "POST",
					async: false,
					url: "/projectSupermarket/delete.do",
					data: {ids:ids},
					dataType:"json",
					success: function (json) {
						if(json&&json["code"]==="0"){
							$('#project_datagrid').datagrid('clearSelections');
							$.messager.alert("提示", "删除成功！", "info");
							$('#project_datagrid').datagrid('reload');
						} else {
							$.messager.alert("提示", "删除失败，请重新操作！", "info");
							return;
						}
					}
				});
			});

		}
	};

$(function() {
	load();
});
function load(){
	$("#project_datagrid").datagrid({
		url : "projectSupermarket/queryProjectSupermarketList.do",
		rownumbers: true,  //行号
		singleSelect: false, //允许选择多行
		selectOnCheck: true,//true勾选会选择行，false勾选不选择行, 1.3以后有此选项。重点在这里
		checkOnSelect: false, //true选择行勾选，false选择行不勾选, 1.3以后有此选项
		queryParams: { 
			name: $("#name").val(),
			cooperativeMode: $("#cooperativeMode").val(),
			province: $("#province").val(),
			industry: $("#industry").val(),
			isShow: $("#isShow").val(),
			isTop: $("#isTop").val(),
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
				field : "cooperativeModeName",
				title : "合作模式",
				width : 200,
				align : "left",
			},
			{
				field : "provinceName",
				title : "省",
				width : 100,
				align : "left",
			},
			{
				field : "cityName",
				title : "市",
				width : 100,
				align : "left",
			},
			{
				field : "countyName",
				title : "区",
				width : 100,
				align : "left",
			},
			{
				field : "industryName",
				title : "所属行业",
				width : 200,
				align : "left",
			},
			{
				field : "isShow",
				title : "是否显示",
				width : 60,
				align : "center",
				formatter : function(value, row, index) {
					return 0==value?"是":"否";
				}
			},
			{
				field : "sort",
				title : "权重",
				width : 60,
				align : "center",
			},
			{
				field : "createTime",
				title : "创建时间",
				width : 120,
				align : "center",
				formatter : function(value, row, index) {
					return value==null?"":new Date(value).format("yyyy-MM-dd HH:mm");
				}
			},
			{
				field : "op",
				title : "操作",
				align:"center",
				width : 150,
				formatter: function(value, row, index){
					return   "<a href = 'javaScript:void(0)' onclick = updateProject('"+row.id+"')>编辑</a>|"
							+"<a href = 'javaScript:void(0)' onclick = detailProject('"+row.id+"')>查看</a>|"
							+"<a href = 'javaScript:void(0)' onclick = projectCustomer('"+row.id+"')>约见客户</a>";
				}
			}
		]],
	});
}
//新建
function add(fid){
	var params = {closed: false,cache: false,modal:true,width:1000,height:800,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('新建','projectSupermarket/addPage.do?id=',"addPage",params)
}

//编辑
function updateProject(fid){
	var params = { closed: false,cache: false,modal:true,width:1000,height:800,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('编辑','projectSupermarket/updatePage.do?id='+fid,"updatePage",params)
}

	//意向客户
function projectCustomer(fid){
	var params = { closed: false,cache: false,modal:true,width:1400,height:800,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('意向客户','projectSupermarket/projectCustomerIndex.do?fkId='+fid,"projectCustomer",params)
}

//明细
function detailProject(fid){
	var params = { closed: false,cache: false,modal:true,width:1000,height:600,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('明细','projectSupermarket/detail.do?id='+fid,"detail",params)
}
</script>