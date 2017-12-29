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
	<form action="template/${operateMethod}.do" method="post" enctype="multipart/form-data" onsubmit="return validateSubmitForm(this)">
		<table class="table table-bordered ">
			
			
			<tr>
				<th width="15%">
					模板描述：
				</th>
				<td width="85%">
					<textarea name="info" rows="1" cols="100" style="width: 660px" maxlength="500">${dto.info}</textarea>
				</td>
			</tr>
			<tr>
				<th width="15%">
					模板标题：
				</th>
				<td width="85%">
					<textarea name="title" rows="1" cols="100" style="width: 660px" maxlength="500">${dto.title}</textarea>
				</td>
			</tr>
			<tr>
				<th width="15%">
					模板标识：
				</th>
				<td width="85%">
					<textarea name="mark" rows="1" cols="100" style="width: 660px" maxlength="500">${dto.mark}</textarea>
				</td>
			</tr>
			<tr>
				<th>
					模板类型：
				</th>
				<td>

					<select class="combox required" class="easyui-validatebox" required="true" name="type" sValue="${dto.type }">
							<option value="1">手机短信模板</option>
							<option value="2">微信模板</option>
					</select>

				</td>
			</tr>
			<tr>
				<th>
					内容：
				</th>
				<td >
					<textarea  name="content" rows="6" cols="100" style="width: 98%;"><c:out value="${dto.content}"/></textarea>
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