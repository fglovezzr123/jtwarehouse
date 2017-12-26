<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<%--
	模块：
--%>

	<form  action="qfyConfig/update.do"  id="qfyConfig_form"  method="post"   onsubmit="return validateSubmitForm(this)">
		<div class="divider"></div>
		  <table class="table table-nobordered " style="margin-top: 25px;">
		 
		 	 <tr>
			    	<th style="width: 80px">是否控制：</th>
			    	<td>
			     		<select name="cStatus" sValue="${qc.cStatus }">
						<option  value="2">是</option>
						<option  value="1">否</option>
					    </select>
			    	</td>
			  </tr>
			  <tr>
				 <th></th>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="button" class="btn btn-primary"  onclick="save()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
				 </td>
			  </tr>
		  
		  </table>
		  <input type="hidden" name="id" value="${qc.id}"/>
	</form>
<script type="text/javascript">
function save(){
	var flag = true;
	if(flag){
		if(!validateSubmitForm($('#qfyConfig_form'))){
			return;
		}
	}
}
</script>