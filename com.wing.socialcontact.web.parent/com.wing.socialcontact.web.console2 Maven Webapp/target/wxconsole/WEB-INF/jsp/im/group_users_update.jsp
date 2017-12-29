<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理-- 聚合页面管理--添加
--%>

<div style="width: 98%;margin: 0 auto;" >
	<form  action="im/addUsers.do"  method="post" id="group_form" enctype="multipart/form-data" >
		<table class="table table-bordered">
	    	<tr>
				<th style="width: 15%">群主名称：</th>
		    	<td style="width: 85%">
		    		<input type="text" name="creator_name" class="easyui-validatebox" required="true" maxlength="100" readonly="readonly" value="${tjyUser.nickname }"/>
		    		<input type="hidden" name="creator" class="easyui-validatebox" required="true" maxlength="100" value="${tjyUser.id }"/>
		    	</td>
		  	</tr>
		  	
		  	 
		  	
		  	<tr>
				<th style="width: 15%">群名称：</th>
		    	<td style="width: 85%">
		    		<input type="text" name="groupName" class="easyui-validatebox" required="true" maxlength="100" readonly="readonly" value="${imGroupinfo.groupName }"/>
		    	</td>
		  	</tr>
			
			
		  	
		  	<tr id="to_user_ids" >
				<th>
					群成员：
				</th>
				 
				<td>
					<textarea name="users"  id="users"   rows="2" style="width:95%;"></textarea>
				</td>
			</tr>
			
		  	<tr>
				<th></th>
				<td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
					  	<button type="button" class="btn btn-primary"  onclick="save()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
					  	<!-- <button type="button" class="btn clear" >清空</button> -->
					</div>
			 	</td>
		  	</tr>
		  	
			
		</table>
		<input type="hidden" name="pid" id="pid" value="${imGroupinfo.id }"/>
		<input type="hidden" name="id" id="id" value="${imGroupinfo.id }"/>
		
		<input type="hidden" name="createTime" value='<fmt:formatDate value="${imGroupinfo.createTime }" type="both" dateStyle="medium" timeStyle="medium"/>' />
		<input type="hidden" name="datagridId" value="groupUser_datagrid" />
		<input type="hidden" name="callback_fn" value="otherCallback" />
		<input type="hidden" name="currentCallback" value="close" />
	</form>
	<span class="typefile" id="filePicker"></span>
</div>

<script type="text/javascript">

function save(){
	var _users=$("#users").val();
	if(isEmpty(_users)){
		Msg.alert("提示","群成员不能为空!");
		return;
	}
	if(!validateSubmitForm($('#group_form'))){
		
		return;
	}
}
function otherCallback(){
	//刷新表格数据
	$('#tjy_im_groupusers_datagrid').datagrid("reload");
}
</script>
