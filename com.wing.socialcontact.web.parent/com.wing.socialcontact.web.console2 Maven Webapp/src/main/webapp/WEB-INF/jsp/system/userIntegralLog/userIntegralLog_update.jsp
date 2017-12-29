<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>

<div style="width: 95%; margin: 0 auto;">
	<form action="template/add.do" method="post" enctype="multipart/form-data" onsubmit="return validateSubmitForm(this)">
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

					<select class="combox required" class="easyui-validatebox" required="true" name="type">
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
					<textarea class="editor" name="content" rows="6" cols="100" style="width: 98%;"></textarea>
				</td>
			</tr>
			
			<input type="hidden" name="datagridId" value="${param.rel }_datagrid" />
			<input type="hidden" name="currentCallback" value="close" />
			
			<input type="hidden" name="id" value="${dto.id}" />
			<input type="text" name="createTime" value="${dto.createTime}" />
			<input type="hidden" name="createUserId" value="${dto.createUserId}" />
			<input type="hidden" name="createUserName" value="${dto.createUserName}" />
			<input type="hidden" name="updateTime" value="${dto.updateTime}" />
			<input type="hidden" name="updateUserId" value="${dto.updateUserId}" />
			
			
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