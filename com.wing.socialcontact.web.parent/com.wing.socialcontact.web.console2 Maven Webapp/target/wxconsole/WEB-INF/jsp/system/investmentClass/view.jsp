<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理-- 资讯分类管理--添加分类
--%>

<div style="width: 800px;margin: 0 auto;" >
	<form  action="investmentClass/update.do"  method="post" id="investmentClass_form" afterCallback="saveCallback" enctype="multipart/form-data" >
		  <input id="imgpath" name="imgpath" value=""  type="hidden" />
		  <table class="table table-nobordered " style="margin-top: 25px;">
		 
			  <tr>
			    	<th style="width: 80px">投资分类名称：</th>
			    	<td >
			     		<input type="text"  readonly="readonly" value="${dto.name}" name="name" class="easyui-validatebox" required="true"  data-options="validType:['length[1,20]']"   maxlength="20" />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">排序：</th>
			    	<td >
			     		<input type="text" readonly="readonly"   value="${dto.sort}" name="sort"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" class="easyui-numberbox" required="true"  data-options="validType:['length[1,20]']"   maxlength="20" />
			    	</td>
			  </tr>
			
		  </table>
		  
		  <input type="hidden" name="datagridId" value="${param.rel }_datagrid" />	
		  <input type="hidden" name="currentCallback" value="close" />
		  <input type="hidden" name="id" value="${dto.id }"/>
		  <input type="hidden" name="createTime" value="<fmt:formatDate  value="${dto.createTime}" pattern="yyyy-MM-dd hh:mm:ss" />"/>
		  <input type="hidden" name="createUserId" value="${dto.createUserId}"/>
	</form>
	
	
</div>

<script type="text/javascript">	

function save(){
	
	if(true){
		if(!validateSubmitForm($('#investmentClass_form'))){
			return;
		}
	}
}
var saveCallback=function(obj,msg){
	if(msg.statusCode == 200){
		$("#${param.rel}_datagrid").datagrid('reload');
		$("#${param.rel}_update").dialog("close");
	}else{
		Msg.alert("提示",msg.message,"error");
	}
}
v



</script>
