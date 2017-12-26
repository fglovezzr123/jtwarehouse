<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<%--
	模块：
--%>

	<form  action="refundinstruction/add.do"  id="refundinstruction_form"  method="post"   onsubmit="return validateSubmitForm(this)">
		<div class="divider"></div>
		  <table class="table table-nobordered " style="margin-top: 25px;">
		 
		 	  <tr>
			    	<th style="width: 150px">ID(不可修改)：</th>
			    	<td style="width: 400px">
			     		<input type="text" name="id" class="easyui-numberbox" required="true"   maxlength="3" />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width:150px">备注：</th>
			    	<td>
			     		<input type="text" name="remark" class="easyui-validatebox" required="true"  data-options="validType:['length[1,20]']"   maxlength="100" />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 150px">退款说明：</th>
			    	<td>
			     		<textarea rows="15" cols="30"  name="content" id="content"></textarea>
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
		<input type="hidden" name="datagridId" value="${param.rel }_datagrid" />
		<input type="hidden" name="currentCallback" value="close" />
	</form>
<script type="text/javascript">

function save(){
	
	if($("#content").val().length>200){
		Msg.alert("提示","退款说明限制200字以内！","warning",null);
		return;
	};
	$("#refundinstruction_form").submit();
}
var saveCallback=function(obj,msg){
	if(msg.statusCode == 200){
		$("#${param.rel}_datagrid").datagrid('reload');
		$("#${param.rel}_add").dialog("close");
	}else{
		Msg.alert("提示",msg.message,"error");
	}
}

</script>
