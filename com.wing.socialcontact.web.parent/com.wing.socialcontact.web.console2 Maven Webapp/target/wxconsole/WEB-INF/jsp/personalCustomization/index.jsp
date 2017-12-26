<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div id="projectindex_toolbar" class="search-div">
	<div class="search-content">
		<span>
			<label style="width: 80px;">服务标题：</label>
			<input type="text" id="title" style="width:120px;"/>
		</span>
		<span>
			<label style="width: 80px;">是否显示：</label>
			<select id="isShow" style="width:70px;">
				<option value="">全部</option>
				<option value="1">是</option>
				<option value="2">否</option>
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
		<span>
			<button class="btn btn-primary btn-small" type="button" onclick="add()">新建</button>&nbsp;
		</span>
	</div>
</div>
<table id="datagrid" toolbar="#toolbar"></table>
<script type="text/javascript">	
$(function() {
	load();
});
function load(){
	$("#datagrid").datagrid({
		url : "personalCustomization/query.do",
		rownumbers: true,
		queryParams: { 
			title: $("#title").val(),
			isShow: $("#isShow").val(),
			createTime: $("#createTime").val()
		},
		fitColumns: false,
		columns : [[
			 {
				field : "title",
				title : "服务标题",
				width : 200,
				align : "left",
			},
			{
				field : "weight",
				title : "权重",
				width : 60,
				align : "center"
			},

			{
				field : "isShow",
				title : "是否显示",
				width : 60,
				align : "center",
				formatter : function(value, row, index) {
					return 1==value?"是":"否";
				}
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
							+"<a href = 'javaScript:void(0)' onclick = deleteProject('"+row.id+"')>删除</a>|"
							+"<a href = 'javaScript:void(0)' onclick = detailProject('"+row.id+"')>查看</a>";
				}
			}
		]],
	});
}
//发布
function add(fid){
	var params = {closed: false,cache: false,modal:true,width:1000,height:600,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('新建','personalCustomization/addPage.do?id=',"addPage",params)
}

//编辑
function updateProject(fid){
	var params = { closed: false,cache: false,modal:true,width:1000,height:600,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('编辑','personalCustomization/updatePage.do?id='+fid,"updatePage",params)
}
//删除
function deleteProject(fid){
	confirmx("确定删除吗？",function(s){
		if(s==1){
			$.ajax({
				url:"personalCustomization/delete.do",
				type: 'post',	
				data: {id:fid},
				cache: false,
				dataType:"json",
				success:function(json){
					if(json&&json["code"]==="0"){
						$("#datagrid").datagrid('reload');
					}else{
						alert("操作失败");
					}			
				}
			});
		}
	})
}
//明细
function detailProject(fid){
	var params = { closed: false,cache: false,modal:true,width:1000,height:600,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('明细','personalCustomization/detail.do?id='+fid,"detailpage",params)
}
</script>