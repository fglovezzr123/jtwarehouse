<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<%--
	模块：
--%>

	<form  action="quickDetailClass/update.do"  id="entryQuickDetailClass_form"  method="post"   onsubmit="return validateSubmitForm(this)">
		<div class="divider"></div>
		  <table class="table table-nobordered " style="margin-top: 25px;">
		 
		 	  <tr>
			    	<th style="width: 100px">适用位置：</th>
			    	<td >
						<select name="quickDoorId" id="quickDoorId" sValue="${dto.quickDoorId}" style="width: 120px;" required="true">
							<c:forEach var="c" items="${quicks}">
								<option  value="${c.id}">${c.quickName}</option>
							</c:forEach>	
						</select>
                	</td>
			  </tr>
		 	  <tr>
			    	<th style="width: 100px">分类选择：</th>
			    	<td >
						<select name="classId" id="classId" sValue="${dto.classId}" style="width: 120px;" required="true">
							<c:forEach var="c" items="${classes}">
								<option  value="${c.id}">${c.className}</option>
							</c:forEach>	
						</select>
                	</td>
			  </tr>
			  <tr>
			    	<th style="width: 120px">排序：</th>
			    	<td>
			     		<input type="text" name="sortNum" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  value = "${dto.sortNum}" class="easyui-numberbox" required="true" min="1" max="9999" />
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
		
		<input type="hidden" name="createTime" value="<fmt:formatDate  value="${dto.createTime}" pattern="yyyy-MM-dd hh:mm:ss" />"/>
	  	<input type="hidden" name="createUserId" value="${dto.createUserId}"/>
	  	<input type="hidden" name="status" value="${dto.status}"/>
	  	<input type="hidden" name="id" value="${dto.id}"/>
	</form>
<script type="text/javascript">
function save(){
	var flag = true;
	if(flag){
		if(!validateSubmitForm($('#entryQuickDetailClass_form'))){
			return;
		}
	}
}
</script>
