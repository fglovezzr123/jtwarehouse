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
	<form action="userIntegral/${operateMethod}.do" method="post" enctype="multipart/form-data" onsubmit="return validateSubmitForm(this)">
		<table class="table table-bordered ">
			
			<tr>
				<th width="15%">
					埋点类型：
				</th>
				<td width="85%" colspan="3">
					<select class="combox required" class="easyui-validatebox" required="true" id="taskStatus" name="taskStatus" sValue="${dto.taskStatus }">
							<option value="">-请选择-</option>
							<option value="1">积分等级</option>
							 <option value="2">用户勋章</option> 
					</select>
				</td>
			</tr>
			<tr>
				<th width="15%">
					任务编码：
				</th>
				<td width="85%" colspan="3">
					<input type="text"  id="code" name="code"   required="true"  maxlength="20" value="${dto.code}"/>
				</td>
			</tr>
			<tr >
				<th width="15%">
					任务体系：<br>
				</th>
				<td width="85%" colspan="3">
					<textarea  name="taskSystem" rows="1" cols="100" style="width: 98%;"><c:out value="${dto.taskSystem}"/></textarea>
				</td>
			</tr>
			
			<tr id="to_user_honor" style="display: none">
				<th width="15%">
					授权勋章：
				</th>
				<td width="85%" colspan="3">
					<a href="userhonor/lookUpPage1.do?type=2&rel=select_user" lookupGroup="userHonor" title="用户勋章">
						<textarea  readonly="readonly"    toName="userHonor.title"   value="${titles}"  rows="2" style="width: 400px">${titles}</textarea>
					</a>
					<input type="hidden"  name="remark" toName="userHonor.id"   value="${dto.remark}"  />
                    
					
					<a class="easyui-linkbutton clearLookup"  icon="icon-cancel" 	plain="true"  href="javascript:;"  clearLookup="userHonor"  title="清空"></a>
				</td>
			</tr>
			<tr>
				
				<th width="15%">
					获得经验：
				</th>
				<td width="35%">
					<input type="text"  id="empiricalTotal" name="empiricalTotal"   required="true"   class="easyui-numberbox" maxlength="11" value="${dto.empiricalTotal}"/>
				</td>
				<th width="15%">
					获得积分：
				</th>
				<td width="35%">
					<input type="text"  id="integralTotal" name="integralTotal"   required="true"  class="easyui-numberbox"  maxlength="11" value="${dto.integralTotal}"/>
				</td>
			</tr>
			<tr>
				<th>
					说明：<br>
				</th>
				<td width="85%" colspan="3">
					<textarea  name="integralExplain" rows="6" cols="100" style="width: 98%;"><c:out value="${dto.integralExplain}"/></textarea>
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

});

$("#taskStatus").change(function(){
	  var selectVal = $(this).val();
	  if(selectVal==2){
		  $("#to_user_honor").show();
	  }else{
		  $("#to_user_honor").hide();
	  }
  
});
function inithonor(){
	  var selectVal = "${dto.taskStatus}";
	 /// alert(selectVal)
	  if(selectVal==2){
		  $("#to_user_honor").show();
	  }else{
		  $("#to_user_honor").hide();
	  }
}
inithonor();
</script>