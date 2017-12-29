<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
<div id="${param.rel}_toolbar" class="search-div">
	<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
		<div class="search-content">
			<span>
				<label style="width: 60px;">用户名：</label>
				<input type="text" name="nickname" style="width:150px;"/>
			</span>
			<span>
				<label style="width: 100px;">手机号：</label>
				<input type="text" name="mobile" style="width:150px;"/>
			</span>
			<span>
				<label style="width: 100px;">白名单类型：</label>
				<select name="pattern">
					<option value="">全部</option>
					<option value="1">投洽峰会</option>
				</select>
			</span>
			<span style="padding-left:10px;">
				<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
			</span>
			<shiro:hasPermission name="globalwhitelist:add">
			<span>
				<button class="btn btn-primary btn-small" type="button" onclick="addGlobalWhitelist()">添加</button>&nbsp;
			</span>
			</shiro:hasPermission>
			<shiro:hasPermission name="globalwhitelist:delete">
			<span>
				<button class="btn btn-primary btn-small" type="button" onclick="delGlobalWhitelist()">删除</button>&nbsp;
			</span>
			</shiro:hasPermission>
		</div>
	</form>
</div>
<table id="${param.rel}_datagrid" toolbar="#${param.rel}_toolbar"></table>
<script type="text/javascript">	
var rel = "<%=request.getParameter("rel")%>";
$(function() {
	loadGlobalWhitelist();			
});

function loadGlobalWhitelist(){
	$("#"+rel+"_datagrid").datagrid(
		{
			url : "globalwhitelist/query.do",
			columns : [ 
			    [{
					field:"ck",
					title : "勾选",
					checkbox:true
				},{
						field : "types",
						title : "白名单类型",
						width : 100,
						align : "center",
						formatter:function(value){
							if(1==value){
								return "投洽峰会";
							}else{
								return "";
							}
						}
				},{
					field : "nickname",
					title : "用户名",
					width : 100,
					align : "center",
				},{
					field : "mobile",
					title : "手机号",
					width : 100,
					align : "center",
				},{
					field : "createTime",
					title : "添加时间",
					width : 100,
					align : "center",
					formatter : function(rowIndex, rowData) {
						return new Date(rowData.createTime).format("yyyy-MM-dd HH:mm");
					}
				},{
					field : "comName",
					title : "所属公司",
					width : 100,
					align : "center",
				}]
			]
		});
}
function addGlobalWhitelist(fid){
	var id=rel+"_add";
	var params = { 
			onClose:function(){ }, closed: false,cache: false,modal:true,
			width:1000,height:600,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('添加','globalwhitelist/addPage.do?id='+fid+'&rel='+rel,id,params);
}
function delGlobalWhitelist(){
	var rows = $('#'+rel+'_datagrid').datagrid("getChecked");
	if(rows&&rows.length==0){
		alert("请先选择需要删除的用户");
	}else{
		var arr=[];
		for(var i=0;i<rows.length;i++){
			var row = rows[i];
			arr.push(row.id);
		}
		Msg.confirm('提示',"确定要删除吗？<br/>", function(r){
          	if (r){
          		$.ajax({
    				url:"globalwhitelist/del.do",
    				type: 'post',	
    				data: {ids: arr.join(";")},
    				cache: false,
    				dataType:"json",
    				success:function(json){
    					if(json&&json["statusCode"]==200){
    						$('#'+rel+'_datagrid').datagrid('reload');
    					}else{
    						alert("操作失败");
    					}			
    				}
    			});
          	}
     	});
	}
}
</script>