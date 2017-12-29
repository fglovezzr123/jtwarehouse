<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
<div id="${param.rel}_adduser_toolbar" class="search-div">
	<form onsubmit="return datagridSearch(this,'${param.rel}_adduser_datagrid');" >
		<!--input type="hidden" name="isRealname" value="1"/-->
		<div class="search-content">
			<span>
				<label style="width: 60px;">用户名：</label>
				<input type="text" name="nickname" style="width:150px;"/>
			</span>
			<span>
				<label style="width: 60px;">手机号：</label>
				<input type="text" name="mobile" style="width:150px;"/>
			</span>
			<span style="padding-left:10px;">
				<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
			</span>
		<shiro:hasPermission name="globalwhitelist:add">
			<span style="float:right;">
				<button class="btn btn-primary btn-small" type="button" onclick="addGlobalWhitelistUsers()">添加</button>&nbsp;
			</span>
			<span style="float:right;">
				<label style="width: 100px;">白名单类型：</label>
				<select id="ppppp__types">
					<option value="1">投洽峰会</option>
				</select>
			</span>
		</shiro:hasPermission>
		</div>
	</form>
</div>
<table id="${param.rel}_adduser_datagrid" toolbar="#${param.rel}_adduser_toolbar"></table>
<script type="text/javascript">	
var rel = "<%=request.getParameter("rel")%>";
$(function() {
	loadGlobalWhitelistAddUser();			
});

function loadGlobalWhitelistAddUser(){
	$('#'+rel+'_adduser_datagrid').datagrid(
		{
			url : "globalwhitelist/user/query.do",
			queryParams:{},
			columns : [ 
			    [{
					field:"ck",
					title : "勾选",
					checkbox:true
				},{
					field : "nickname",
					title : "用户名",
					width : 200,
					align : "center",
				},{
					field : "mobile",
					title : "手机号",
					width : 200,
					align : "center",
				},{
					field : "comName",
					title : "所属公司",
					width : 200,
					align : "center",
				}]
			]
		});
}
function addGlobalWhitelistUsers(){
	var rows = $('#'+rel+'_adduser_datagrid').datagrid("getChecked");
	if($("#ppppp__types").val()==""){
		alert("请先选择要添加的白名单类型");
	}else if(rows&&rows.length==0){
		alert("请先选择用户");
	}else{
		var arr=[];
		for(var i=0;i<rows.length;i++){
			var row = rows[i];
			arr.push(row.id);
		}
		$.ajax({
			url:"globalwhitelist/add.do",
			type: 'post',	
			data: {types: $("#ppppp__types").val(), ids: arr.join(";")},
			cache: false,
			dataType:"json",
			success:function(json){
				if(json&&json["statusCode"]==200){
					$('#'+rel+'_datagrid').datagrid('reload');
					$('#'+rel+'_add').dialog("destroy");
				}else{
					alert("操作失败");
				}			
			}
		});
	}
}
</script>