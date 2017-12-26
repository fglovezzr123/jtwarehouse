<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<%--
	模块：
--%>

	<form  action="activity/cancel.do"  id="activity_cancel"  method="post"   onsubmit="return validateSubmitForm(this)">
		<div class="divider"></div>
		  <table class="table table-nobordered " style="margin-top: 25px;">
		 
		 	<tr>
			    	<th style="width: 120px">活动主题：</th>
			    	<td>
			     		<input type="text"  class="easyui-validatebox" value="${activity.titles}" readonly="readonly" />
			    	</td>
			</tr>
			<tr>
			    	<th style="width: 120px">门票价格：</th>
			    	<td>
			     		<input type="text"  value="${activity.ticketPrice}"  precision="2" class="easyui-numberbox"  readonly="readonly" />
			    	</td>
			</tr>
			<tr>
			    	<th style="width: 120px">发起者：</th>
			    	<td>
			     		<input type="text"  class="easyui-validatebox" value="${activity.sponsor}"  readonly="readonly" />
			    	</td>
			</tr>
			<tr>
			    	<th style="width: 120px">申请类型：</th>
			    	<td>
			     		<input type="text"  class="easyui-validatebox" value="取消申请" readonly="readonly"/>
			    	</td>
			</tr>
			<tr>
				<th style="width: 120px">取消原因：</th>
				<td>
					<textarea rows="5" cols="" readonly="readonly">${dto.description }</textarea>
				</td>
			</tr>
			<tr>
				<th  style="width: 120px">审核人意见</th>
				<td>
					<input type="radio" name="type"  value="1" checked>同意
					<input type="radio" name="type"  value="2" >拒绝
				</td>
			</tr>
		 
			  <tr>
				 <th></th>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="button" class="btn btn-primary"  onclick="save()">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
				 </td>
			  </tr>
		  
		  </table>
		  <input type="hidden" name="id" value="${dto.id }"/>
		  <input type="hidden" name="datagridId" value="${param.rel }_datagrid" />	
		  <input type="hidden" name="currentCallback" value="close" />
		  <input type="hidden" name="createTime" value="<fmt:formatDate  value="${dto.createTime}" pattern="yyyy-MM-dd hh:mm:ss" />"/>
		  <input type="hidden" name="userId" value="${dto.userId}"/>
		  <input type="hidden" name="activityId" value="${dto.activityId}"/>
	</form>
<script type="text/javascript">
function save(){
	var flag = true;
	if(flag){
		if(!validateSubmitForm($('#activity_cancel'))){
			return;
		}
	}
}
var saveCallback=function(obj,msg){
	if(msg.statusCode == 200){
		$("#${param.rel}_datagrid").datagrid('reload');
		$("#${param.rel}_cancel").dialog("close");
	}else{
		Msg.alert("提示",msg.message,"error");
	}
}
</script>
