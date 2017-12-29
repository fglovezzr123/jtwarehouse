<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>

<div style="width: 95%; margin: 0 auto;">
	<form action="userhonor/adduserHonor.do" method="post" enctype="multipart/form-data" onsubmit="return validateSubmitForm(this)">
		<table class="table table-bordered ">
			
			
			<tr>
				<th width="15%">
					用户：
				</th>
				<td width="85%">
					${user.nickname}
					<input type="hidden" name="userId" value="${user.id}" />
				</td>
			</tr>
			<tr>
				<th width="15%">
					勋章：
				</th>
				<td width="85%">
					<select name="honorId" id="honorId" style="width: 120px;">
						
						<c:forEach var="c" items="${honors}">
						<option  value="${c.id}">${c.title}</option>
						</c:forEach>	
					</select>
				</td>
			</tr>
			
			<input type="hidden" name="datagridId" value="${param.rel }0_datagrid" />
			<input type="hidden" name="currentCallback" value="close" />
			
			
			
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