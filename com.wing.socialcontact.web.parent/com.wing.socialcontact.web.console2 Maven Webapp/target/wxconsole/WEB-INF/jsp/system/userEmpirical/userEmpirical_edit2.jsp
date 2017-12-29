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
	<form action="userEmpirical/${operateMethod}.do" method="post" id="empirical_form2" enctype="multipart/form-data" onsubmit="return validateSubmitForm(this)">
		<table class="table table-bordered ">
			
			
			<tr>
				<th width="15%">
					等级名称：
				</th>
				<td width="85%" colspan="3">
					<input type="text"     required="true" disabled="disabled" maxlength="20" value="${dto.level}"/>
				</td>
			</tr>
			<tr >
				<th>
					经验区间：<br>
				</th>
				<td width="85%" colspan="3">
					<input type="text"  id="empiricalLow" name="empiricalLow"   disabled="disabled" required="true"  class="easyui-numberbox"  maxlength="8" value="${dto.empiricalLow}"/>~
					<input type="text"  id="empiricalHigh" name="empiricalHigh"  disabled="disabled"  required="true"   class="easyui-numberbox"  maxlength="8"  value="${dto.empiricalHigh}"/>
				</td>
			</tr>
			<tr>
				<th width="15%">
					赠送积分：
				</th>
				<td width="85%" colspan="3">
					<input type="text"  id="integralTotal" name="integralTotal" disabled="disabled"  required="true"  class="easyui-numberbox"  maxlength="8" value="${dto.integralTotal}"/>
				</td>
			</tr>
			<tr>
				<th width="15%">
					打呼招数量：
				</th>
				<td width="85%" colspan="3">
					<input type="text"  id="greetingsCount" name="greetingsCount"  required="true"  class="easyui-numberbox"  maxlength="11" value="${dto.greetingsCount}"/>
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
					<input type="hidden" name="level" value="${dto.level}" />	
					<input type="hidden" name="updateTime" value="<fmt:formatDate  value="${dto.updateTime}" pattern="yyyy-MM-dd hh:mm:ss" />"/>			
				</c:if>
					
			
			
			
			
			<tr>
				<th>
				</th>
				<td colspan="3">
					<div style="margin-top: 10px; margin-bottom: 10px;">
						<button type="button" class="btn btn-primary" onclick="save()">
							保存
						</button>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="btn " id="zhikong">
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
  $("#zhikong").click(function(){
	  $("#greetingsCount").val("");
	});
});

function save(){
	var empiricalLow=$("#empiricalLow").val();
	var empiricalHigh=$("#empiricalHigh").val();
	if(isEmpty(empiricalLow)||isEmpty(empiricalHigh)){
		Msg.alert("提示","经验区间未填写完整！","warning",null);
		return;
	}
	if(parseInt(empiricalLow)>=parseInt(empiricalHigh)){
		Msg.alert("提示","经验区间填写有误！","warning",null);
		return;
	}
	
	if(!validateSubmitForm($('#empirical_form2'))){
		return;
	}
}
</script>