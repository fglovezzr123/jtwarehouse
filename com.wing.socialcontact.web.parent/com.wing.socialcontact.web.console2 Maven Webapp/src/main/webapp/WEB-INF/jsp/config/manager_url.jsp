<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：邀请好友页面配置
--%>

<div style="width: 800px;margin: 0 auto;" >
	<form  action="config/manager.do"  method="post" id="manager_form" afterCallback="saveCallback" enctype="multipart/form-data" >
		  <table class="table table-nobordered " style="margin-top: 10px;">
			  <tr>
			  		<th>
			  			地址:
			  		</th>
			    	<td>
			    		<input name="listDesc" type="text" required="true" class="easyui-validatebox" data-options="validType:['length[1,150]']" style="width:320px;" id="listDesc" value="${yqConfig.listDesc }" maxlength="150"/>
			    		<strong style="color:#F00">http:// 开头</strong>
			    	</td>
			  </tr>
			  <tr>
			  	 <th>&nbsp;</th>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="button" class="btn btn-primary"  onclick="save()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
						  <!-- <button type="button" class="btn clear" >清空</button> -->
					</div>
				 </td>
			  </tr>
		  </table>
		  <input type="hidden" name="id" value="${yqConfig.id }"/>
		  <input type="hidden" name="listType" value="${yqConfig.listType }"/>
		  <input type="hidden" name="listValue" value="${yqConfig.listValue }"/>
		  <input type="hidden" name="sortno" value="${yqConfig.sortno }"/>
		  <input type="hidden" name="deleted" value="${yqConfig.deleted }"/>
	</form>
</div>

<script type="text/javascript">	
function save(){
	if(!validateSubmitForm($('#manager_form'))){
		return;
	}
}
</script>
