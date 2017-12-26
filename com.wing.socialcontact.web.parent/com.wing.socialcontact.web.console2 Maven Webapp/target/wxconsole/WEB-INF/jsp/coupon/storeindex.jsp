<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<div class="search-div" id="vhall_toolbar">
<form onsubmit="return datagridSearch(this,'store_datagrid');" >
	<div class="search-content">
	    <span>
						<label style="width: 100px;">店铺名称：</label>
						<input	type="text" name="store_name" class="span2"/>
						 <button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
		</span>
		<span style="float:right">
			<button class="btn btn-primary btn-small" type="button" onclick="sureStore()">确定</button>&nbsp;
			<button class="btn btn-primary btn-small" type="button" onclick="closeStore()">取消</button>&nbsp;
		</span>
	</div>
</form>
</div>
<table id="store_datagrid"></table>
<script type="text/javascript">	
$(function() {
	loadStoreList();			
});
function loadStoreList(){
	$("#store_datagrid").datagrid({
		url : "coupon/storequery.do",
		fitColumns: false,
		rownumbers: true,
		columns : [[{
				field : "ck",
				title : "勾选",
				width : 10,
				checkbox : true
			},{
				field : "userName",
				title : "用户姓名",
				width : 100,
				align : "center",
			},{
				field : "mobile",
				title : "注册手机号",
				width : 150,
				align : "center"
			},
			{
				field : "id",
				title : "店铺ID",
				width : 100,
				align : "center",
			},{
				field : "store_name",
				title : "店铺名称",
				width : 150,
				align : "center"
			},
			{
				field : "store_ower",
				title : "店主",
				width : 150,
				align : "center"
			},
			{
				field : "store_telephone",
				title : "店主手机号",
				width : 150,
				align : "center"
			}
		]],
	});
}
function sureStore(){
	var ids = [];
	var rows = $("#store_datagrid").datagrid("getChecked");
	if(rows&&rows.length==0){
		alert("请先选择店铺");
	}else{
		for(var i=0;i<rows.length;i++){
			 ids.push(rows[i].mobile);    
		}
		$("#mau_storeTelephone").val(ids);
		$("#useStore").val(ids);
		closeStore();
	}
}
function closeStore(){
	$("#storeindexfornews").dialog("destroy");
}
</script>