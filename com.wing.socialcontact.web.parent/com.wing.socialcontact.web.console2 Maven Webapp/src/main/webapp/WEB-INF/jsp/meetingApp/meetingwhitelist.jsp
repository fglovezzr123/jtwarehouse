<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" session="false"%>
<%@include file="/WEB-INF/jsp/commons/jstl_message_tld.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="easyui-tabs" style="height:365px;">
	<div title="常设白名单" style="height:365px;">
		<div id="MGlobalWhitelist_datagrid"></div>
	</div>
	<div title="会议白名单">
		<div id="MeetingWhitelist_datagrid" toolbar="#MeetingWhitelist_toolbar"></div>
	</div>
</div>
<div class="search-div" id="MeetingWhitelist_toolbar">
	<div class="search-content">
		<span>
			<button class="btn btn-primary btn-small" type="button" onclick="addMeetingWhitelist()">添加</button>
			<button class="btn btn-primary btn-small" type="button" onclick="delMeetingWhitelist()">删除</button>
		</span>
	</div>
</div>
<script>
$(function(){
	loadMGlobalWhitelist();
	loadMeetingWhitelist();
});
function loadMGlobalWhitelist(){
	$("#MGlobalWhitelist_datagrid").datagrid({
		url : "globalwhitelist/query.do",
		rownumbers: true,
		fitColumns: true,
		columns : [ 
		    [{
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
				field : "comName",
				title : "所属公司",
				width : 100,
				align : "center",
			}]
		]
	});
}
function loadMeetingWhitelist(){
	$("#MeetingWhitelist_datagrid").datagrid({
		url : "meetingApp/meetingwhitelistquery.do",
		queryParams:{meetingId:"${meetingId}"},
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
				width : 100,
				align : "center",
			},{
				field : "mobile",
				title : "手机号",
				width : 100,
				align : "center",
			},{
				field : "comName",
				title : "所属公司",
				width : 100,
				align : "center",
			}]
		]
	});
}
function addMeetingWhitelist(){
	var params = { 
			onClose:function(){ }, closed: false,cache: false,modal:true,
			width:600,height:400,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('添加','meetingApp/addwhitelistpage.do?meetingId=${meetingId}',"MeetingWhitelist_add",params);
}
function delMeetingWhitelist(){
	var rows = $("#MeetingWhitelist_datagrid").datagrid("getChecked");
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
    				url:"meetingApp/whitelistpage/del.do",
    				type: 'post',	
    				data: {ids: arr.join(";")},
    				cache: false,
    				dataType:"json",
    				success:function(json){
    					if(json&&json["statusCode"]==200){
    						$("#MeetingWhitelist_datagrid").datagrid('reload');
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