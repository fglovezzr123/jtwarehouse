<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>

<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理
--%>
<div style="width: 600px;margin: 0 auto;" >
	<form  action="business/updateDB.do"   method="post"  id="news_form"  afterCallback="saveCallback" enctype="multipart/form-data">
		 <input id="createUserName" name="createUserName" value="${b.createUserName}"  type="hidden" />
		 <input id="createUserId" name="createUserId" value="${b.createUserId}"  type="hidden" />
		 <input id="createTime" name="createTime" value="<fmt:formatDate  value='${b.createTime}' pattern='yyyy-MM-dd HH:mm:ss' />" type="hidden" />
		 <input id="titles" name="titles" value="${b.titles}"  type="hidden" />
		  <input id="fkId" name="fkId" value="${b.fkId}"  type="hidden" />
		 <table class="table table-nobordered " style="margin-top: 25px;">
			 <tr>
			    	<th style="width: 100px">商洽人：</th>
			    	<td >
			     		<div>${b.createUserName}</div>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">创建时间：</th>
			    	<td >
			     		<div id="cjsj"></div>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">合作标题：</th>
			    	<td >
			     		${b.titles}
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">是否显示：</th>
			    	<td>
			     		<select name="isShow"  style="width: 120px;">
						<option  value="1" selected="selected" <c:if test="${b.isShow==1}">selected</c:if>>显示</option>
						<option  value="2" <c:if test="${b.isShow==2}">selected</c:if>>不显示</option>
					</select>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">商洽内容：</th>
			    	<td >
			     	 <textarea  name="content"  rows="5" maxlength="50" style="width:400px">${b.content}</textarea>
			    	</td>
			  </tr>
			  <tr>
				 <th></th>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="button" class="btn btn-primary" onclick="save()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
						  <!-- <button type="button" class="btn clear" >清空</button> -->
					</div>
				 </td>
			  </tr>
		</table>	 
		  
		  <input type="hidden" name="id" value="${b.id }"/>
		  <input type="hidden" name="currentCallback" value="close" />
		  <input type="hidden" name="datagridId" value="${param.rel }_datagrid" />	
		   
	</form>
</div>

<script type="text/javascript">	
$(function() {
 //	var cj = new Date($("#createTime").val()).format("yyyy-MM-dd HH:mm:ss");
	$("#cjsj").html($("#createTime").val()); 
});
function save(){
	var flag = true;
//	$("#createTime").val(new Date($("#createTime").val()));
	if(flag){
		if(!validateSubmitForm($('#news_form'))){
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
</script>
