<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div id="meetingwhitelist_adduser_toolbar" class="search-div">
	<form onsubmit="return datagridSearch(this,'meetingwhitelist_adduser_datagrid');" >
		<!--input type="hidden" name="isRealname" value="1"/-->
		<div class="search-content">
			<span>
				<label style="width: 60px;">用户名：</label>
				<input type="text" name="nickname" style="width:120px;"/>
			</span>
			<span>
				<label style="width: 60px;">手机号：</label>
				<input type="text" name="mobile" style="width:120px;"/>
			</span>
			<span style="padding-left:10px;">
				<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
			</span>
			<span>
				<button class="btn btn-primary btn-small" type="button" onclick="addMeetingWhitelistUsers()">添加</button>&nbsp;
			</span>
		</div>
	</form>
</div>
<table id="meetingwhitelist_adduser_datagrid" toolbar="#meetingwhitelist_adduser_toolbar"></table>
<script type="text/javascript">	
$(function() {
	loadMeetingWhitelistAddUser();	
});

function loadMeetingWhitelistAddUser(){
	$("#meetingwhitelist_adduser_datagrid").datagrid({
		url : "globalwhitelist/user/query.do",
		rownumbers: true,
		fitColumns: true,
		columns : [ 
		    [{
				field:"ck",
				title : "勾选",
				checkbox:true
			},{
				field : "nickname",
				title : "用户名",
				width : 80,
				align : "center",
			},{
				field : "mobile",
				title : "手机号",
				width : 80,
				align : "center",
			},{
				field : "comName",
				title : "所属公司",
				width : 80,
				align : "center",
			}]
		]
	});
}
function addMeetingWhitelistUsers(){
	var rows = $('#meetingwhitelist_adduser_datagrid').datagrid("getChecked");
	if(rows&&rows.length==0){
		alert("请先选择用户");
	}else{
		var arr=[];
		for(var i=0;i<rows.length;i++){
			var row = rows[i];
			arr.push(row.id);
		}
		$.ajax({
			url:"meetingApp/addwhitelist.do",
			type: 'post',	
			data: {ids: arr.join(";"), meetingId: "${meetingId}"},
			cache: false,
			dataType:"json",
			success:function(json){
				if(json&&json["statusCode"]==200){
					$("#MeetingWhitelist_datagrid").datagrid('reload');
					$("#MeetingWhitelist_add").dialog("destroy");
				}else{
					alert("操作失败");
				}			
			}
		});
	}
}
</script>