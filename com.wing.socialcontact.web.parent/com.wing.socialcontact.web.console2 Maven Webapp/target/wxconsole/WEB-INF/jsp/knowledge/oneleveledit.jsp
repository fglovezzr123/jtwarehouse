<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>

<c:choose>
	<c:when test="${dto==null}">
		<c:set value="classadd/levelone" var="operateMethod"></c:set>
	</c:when>
	<c:when test="${dto.id==null or dto.id eq ''}">
		<c:set value="classadd/levelone" var="operateMethod"></c:set>
	</c:when>
	<c:otherwise>
		<c:set value="classupdate/levelone" var="operateMethod"></c:set>
	</c:otherwise>
</c:choose>

 

<div style="width: 95%; margin: 0 auto;">

	<form action="libraryclass/${operateMethod}.do" method="post" id="libraryclass_form" afterCallback="saveCallback">
		<table class="table table-bordered ">
			<tr>
				<th>
					文库类别名称：
				</th>
				<td>
					<input type="text" id="name" name="name" value="${dto.name}"  class="easyui-validatebox" maxlength="50" required="true" />
				</td>
			</tr>
			<tr>
				<th>
					是否推荐：
				</th>
				<td>
					<select name="recommond" > 
					<option id="1" value="1">是</option>
					<option id="0" value="0">否</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					序号：
				</th>
				<td>
					<input type="text" id="sort" name="sort"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" value="${dto.sort}"  class="easyui-numberbox" maxlength="3" required="true" />
				</td>
			</tr>
			
			<input type="hidden" id="id" name="id" value="${dto.id}">
			<input type="hidden" name="createTime" id="createTime" value="${dto.createTime}" />
			<input type="hidden" name="createUserId"  value="${dto.createUserId}" />
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
/* $(function(){
	if("${operateMethod}"=="classupdate/levelone"){
		$("#"+${dto.recommond}).Attr("selected","selected");
	}
}); */
function save(){
	var flag = true;
	var ct;
	if($("#createTime").val()!=""){
		ct = new Date('${dto.createTime}');
	}else{
		ct = new Date();
	}
	$("#createTime").val(ct);
	if(flag){
		if(!validateSubmitForm($('#libraryclass_form'))){
			return;
		}
	}
}

var saveCallback=function(obj,msg){
	if(msg.statusCode == 200){
		$("#${param.rel}_datagrid").datagrid('reload');
		//$('#${param.rel}_datagrid').datagrid('clearSelections');
		$("#${param.rel}_${operateMethod}").dialog("close");
	}else{
		Msg.alert("提示",msg.message,"error");
	}
}

</script>