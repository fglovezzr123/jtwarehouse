<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div id="projectindex_toolbar" class="search-div">
	<div class="search-content">
		<span>
			<label style="width: 80px;">key：</label>
			<input type="text" id="keyWord" style="width:120px;"/>
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
		url : "switchCnf/query.do",
		rownumbers: true,
		queryParams: {
			key: $("#key").val()
		},
		fitColumns: false,
		columns : [[
			 {
				field : "keyWord",
				title : "key",
				width : 200,
				align : "left",
			},
			{
				field : "cnfValue",
				title : "配置值",
				width : 60,
				align : "center"
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
					return   "<a href = 'javaScript:void(0)' onclick = update('"+row.id+"')>编辑</a>|"
							+"<a href = 'javaScript:void(0)' onclick = del('"+row.id+"')>删除</a>|"
							+"<a href = 'javaScript:void(0)' onclick = detail('"+row.id+"')>查看</a>";
				}
			}
		]],
	});
}
//发布
function add(fid){
	var params = {closed: false,cache: false,modal:true,width:1000,height:600,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('新建','switchCnf/addPage.do?id=',"addPage",params)
}

//编辑
function update(fid){
	var params = { closed: false,cache: false,modal:true,width:1000,height:600,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('编辑','switchCnf/updatePage.do?id='+fid,"updatePage",params)
}
//删除
function del(fid){
	confirmx("确定删除吗？",function(s){
		if(s==1){
			$.ajax({
				url:"switchCnf/delete.do",
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
function detail(fid){
	var params = { closed: false,cache: false,modal:true,width:1000,height:600,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('明细','switchCnf/detail.do?id='+fid,"detailpage",params)
}
</script>