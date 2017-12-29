<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<%--
	模块：
--%>

	<form  action="qfy/sysmessage/add.do"  id="sysmessage_form"  method="post"   onsubmit="return validateSubmitForm(this)">
		<div class="divider"></div>
		  <table class="table table-nobordered " style="margin-top: 25px;">
		 	  <tr>
			    	<th style="width: 120px">消息内容：</th>
			    	<td>
			    		<textarea id="content"  name="content" rows="10" cols="" required="true"></textarea>
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
	if(!$("#content").val()){
		Msg.alert("提示","请输入消息内容！","warning",null);
		return false;
	}
	var flag = true;
	if(flag){
		if(!validateSubmitForm($('#sysmessage_form'))){
			return;
		}
	}
}
</script>
