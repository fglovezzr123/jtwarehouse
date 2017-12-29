<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--

--%>

<div style="width: 95%; margin: 0 auto;">
	<form action="userEmpiricalLog/add2.do" method="post"  onsubmit="return validateSubmitForm(this)">
		<table class="table table-bordered ">
		 	 
		 	 <tr>
				<th width="15%">
					用户姓名：
				</th>
				<td width="85%">
			        ${user.nickName }
			        <input type="hidden" name="userId" value=" ${user.id }" />
				</td>
			</tr>
			 <tr>
				<th width="15%">
					操作原因：
				</th>
				<td width="85%">
			     	<textarea  name="remark" rows="1" cols="100" required="required" style="width: 98%;"></textarea>
			     	
				</td>
			</tr>
			 
			 <tr>
				<th width="15%">
					经验值变化 ：
				</th>
				<td width="85%">
			     	<select class="combox required" class="easyui-validatebox" required="true" id="empiricalType" name="empiricalType" >
							<option value="1">+</option>
							 <option value="2">-</option> 
					</select>
			     	<input type="text" name="empirical"   required="required" class="easyui-numberbox"  maxlength="11" />
				</td>
			</tr>
			 
			 
			 
			  <tr>
				 <th></th>
				 <td>
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
		  
		  <input type="hidden" name="datagridId" value="${param.rel }_datagrid" />	
		  <input type="hidden" name="currentCallback" value="close" />

		
	</form>
	
	
</div>

