<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>

<c:choose>
	<c:when test="${entrycustomer==null}">
		<c:set value="add" var="operateMethod"></c:set>
	</c:when>
	<c:when test="${entrycustomer.id==null or entrycustomer.id eq ''}">
		<c:set value="add" var="operateMethod"></c:set>
	</c:when>
	<c:otherwise>
		<c:set value="update" var="operateMethod"></c:set>
	</c:otherwise>
</c:choose>

 

<div style="width: 95%; margin: 0 auto;">

	<form action="qfy/entrycustomer/${operateMethod}.do" method="post" id="entrycustomer_form" >
		<table class="table table-bordered ">
			<tr>
				<th>
					手机号：
				</th>
				<td>
					<input type="text" id="customerPhoneNum" name="customerPhoneNum" data-options="validType:['mPhone']" value="${entrycustomer.customerPhoneNum}"  class="easyui-validatebox" maxlength="50" required="true" />
				</td>
			</tr>
			<tr>
				<th>
					用户名：
				</th>
				<td>
					<input type="text" id="customerName" name="customerName" value="${entrycustomer.customerName}"  class="easyui-validatebox" maxlength="50" required="true" />
				</td>
			</tr>
			<tr>
				<th>
					序号：
				</th>
				<td>
					<input type="text" id="sortNum" name="sortNum"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  value="${entrycustomer.sortNum}"  class="easyui-numberbox" maxlength="10" required="true" />
				</td>
			</tr>
			<c:choose>
			<c:when test="${entrycustomer==null}">
				<input type="hidden" id="entryId" name="entryId" value="${pid}">
			</c:when>
			<c:when test="${entrycustomer.id==null or entrycustomer.id eq ''}">
				<input type="hidden" id="entryId" name="entryId" value="${pid}">
			</c:when>
			<c:otherwise>
				<input type="hidden" id="entryId" name="entryId" value="${entrycustomer.entryId}">
				<input type="hidden" id="id" name="id" value="${entrycustomer.id}">
				<input type="hidden" id="createuserid" name="createUserId" value="${entrycustomer.createUserId}">
				<input type="hidden" name="createTime"  value="<fmt:formatDate  value="${entrycustomer.createTime}" pattern="yyyy-MM-dd hh:mm:ss" />"/>
			</c:otherwise>
			</c:choose>
			<tr>
				<th>
				</th>
				<td colspan="3">
					<div style="margin-top: 10px; margin-bottom: 10px;">
						<button type="button" onclick="save()" class="btn btn-primary">
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
<script>
function save(){
	var ssUserId = $("#customerPhoneNum").val();
	$.ajax({
		url:"qfy/entrycustomer/validUserId.do",
		cache: false,
		dataType:"json",
		data:{
			ssUserId:ssUserId
		},
		success:function(data){
			if(data.code == 0){
				if(data.flag == 1){
				}else{
					Msg.alert("提示","用户手机不是天九集团用户","warning",null);
					return;
				}
			}
		}
	});
		if(!validateSubmitForm($('#entrycustomer_form'))){
			return;
	}
}

</script>