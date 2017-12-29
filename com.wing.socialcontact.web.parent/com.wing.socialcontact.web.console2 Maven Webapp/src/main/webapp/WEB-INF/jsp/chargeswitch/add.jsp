<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理-- 聚合页面管理--添加
--%>

<div style="width: 98%;margin: 0 auto;" >
	<form  action="chargeswitch/add.do"  method="post" id="chargeswitch_form" enctype="multipart/form-data" >
		<table class="table table-bordered">
			<tr>
				<th>名称：</th>
		    	<td>
		    		<input type="text" name="name" id="name" class="easyui-validatebox" required="true" maxlength="150" />
				</td>
		  	</tr>
			<tr>
				<th>标识(唯一不可重复)：</th>
		    	<td>
		    		<input type="text" name="sign" id="sign" class="easyui-validatebox" required="true" maxlength="150" /><font color="red">(唯一不可重复)</font>
				</td>
		  	</tr>
		  	<tr>
				<th>平台费(JB/分钟)：</th>
		    	<td>
		    		<input type="text" name="platformFee" id="platformFee" class="easyui-validatebox" required="true" maxlength="150" /><font color="red">(JB/分钟)</font>
		    	</td>
		  	</tr>
		  	<tr>
				<th>排序:</th>
				<td><input type="text" name="orderNum" class="easyui-validatebox" required="true" maxlength="5" onkeyup="clearNoNum2(this);"/></td>
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
		<input type="hidden" name="status" value="0" />
		<input type="hidden" name="datagridId" value="${param.rel }_datagrid" />
		<input type="hidden" name="currentCallback" value="close" />
	</form>
	<span class="typefile" id="filePicker"></span>
</div>

<script type="text/javascript">
var uploader = null;
function removeGuest(obj){
	$("#upload").remove();
	$("#addGuest").show();
	$("#removeButton").hide();
}

function save(){
	if(!validateSubmitForm($('#chargeswitch_form'))){
		return;
	}
}
</script>
