<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>

<c:choose>
	<c:when test="${dto==null}">
		<c:set value="add" var="operateMethod"></c:set>
	</c:when>
	<c:when test="${dto.id==null or dto.id eq ''}">
		<c:set value="add" var="operateMethod"></c:set>
	</c:when>
	<c:otherwise>
		<c:set value="update" var="operateMethod"></c:set>
	</c:otherwise>
</c:choose>

<div style="width: 95%; margin: 0 auto;">
	<form action="messagebulk/${operateMethod}.do" method="post" enctype="multipart/form-data" onsubmit="return validateSubmitForm(this)">
		<table class="table table-bordered ">
			
			
			<tr>
				<th width="15%">
					推送类型：
				</th>
				<td width="85%">
					<select class="combox required" class="easyui-validatebox" required="true" id="msgType" name="msgType" sValue="${dto.msgType }">
							<option value="1">手机短信</option>
							 <!--<option value="2">微信</option> 
							 <option value="3">系统消息</option> -->
					</select>
				</td>
			</tr>
			<tr id="template" style="display: none">
				<th>
					短信消息模板：<br>
				    例：SMS_62730335
				</th>
				<td >
					<textarea  name="templateId" rows="1" cols="100" style="width: 98%;"><c:out value="${dto.templateId}"/></textarea>
				</td>
			</tr>
			<tr>
				<th>
					消息内容：<br>
				    例： { name: "dada" }多个参数逗号隔开{ name: "dada", age: "18" }
				</th>
				<td >
					<textarea  name="content" rows="6" cols="100" style="width: 98%;"><c:out value="${dto.content}"/></textarea>
				</td>
			</tr>
			<tr>
				<th>
					推送用户：
				</th>
				<td>

					<select class="combox required" class="easyui-validatebox" required="true" name="sendType" sValue="${dto.sendType }">
							<option value="0">全部用户</option>
							<option value="1">认证用户</option>
							<option value="2">注册用户</option>
							<option value="3">企服云用户</option>
					</select>

				</td>
			</tr>
			
			
			<input type="hidden" name="datagridId" value="${param.rel }_datagrid" />
			<input type="hidden" name="currentCallback" value="close" />
			
			
				
				<c:if test="${dto.id!=null}">
					<input type="hidden" name="id" value="${dto.id}" />
					<input type="hidden" name="createTime" value="<fmt:formatDate  value="${dto.createTime}" pattern="yyyy-MM-dd hh:mm:ss" />"/>
					<input type="hidden" name="createUserId" value="${dto.createUserId}" />
					<input type="hidden" name="createUserName" value="${dto.createUserName}" />
					<input type="hidden" name="updateUserId" value="${dto.updateUserId}" />	
					<input type="hidden" name="updateTime" value="<fmt:formatDate  value="${dto.updateTime}" pattern="yyyy-MM-dd hh:mm:ss" />"/>			
				</c:if>
					
			
			
			
			
			<tr>
				<th>
				</th>
				<td colspan="3">
					<div style="margin-top: 10px; margin-bottom: 10px;">
						<button type="submit" class="btn btn-primary">
							保存
						</button>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="btn clear">
							清空
						</button>
					</div>
				</td>
			</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
$(document).ready(function(){
  $("#msgType").change(function(){
	  var selectVal = $(this).val();
	  if(selectVal==1){
		  $("#template").show();
	  }else{
		  $("#template").hide();
	  }
    
  });
});
</script>