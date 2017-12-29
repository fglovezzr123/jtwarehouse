<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ taglib uri="/WEB-INF/tag/myTag.tld" prefix="my" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<c:choose>
	<c:when test="${config==null}">
		<c:set value="add" var="operateMethod"></c:set>
	</c:when>
	<c:when test="${config.id==null or config.id eq ''}">
		<c:set value="add" var="operateMethod"></c:set>
	</c:when>
	<c:otherwise>
		<c:set value="update" var="operateMethod"></c:set>
	</c:otherwise>
</c:choose>

<div style="width: 1000px;margin: 0 auto;" >
	<form  action="tjyconfig/${operateMethod}.do"   method="post" id="userie_form"   enctype="multipart/form-data" onsubmit="return validateSubmitForm(this)">
		 <table class="table table-nobordered " style="margin-top: 25px;">
			  
			  <tr>
				<th width="20%">
					每日推荐数量设置：
				</th>
				<td width="80%" colspan="3">
					<input type="text"  id="config1" name="config1"   required="true"   class="easyui-numberbox" maxlength="8" precision="0" data-options="required:true,min:0,precision:0" value="${config.config1}"/>
					默认：30人，建议10的倍数调整
					
				</td>
			</tr>
			  <tr>
				<th width="20%">
					每日添加好友数量设置：
				</th>
				<td width="75%" colspan="3">
					<input type="text"   id="config2" name="config2"   required="true"   class="easyui-numberbox" maxlength="11" precision="0" value="${config.config2}"/>
					注：控制平台用户每日主动添加好友申请，默认10人
				</td>
			</tr>
			  <tr>
				<th width="20%">
					每日接收请求数量设置：
				</th>
				<td width="80%" colspan="3">
					<input type="text"   id="config3" name="config3"   required="true"   class="easyui-numberbox" maxlength="11" precision="0" value="${config.config3}"/>
					注：控制平台用户每日接收添加好友申请信息，默认10人
				</td>
			</tr>
			  <tr>
				 <th></th>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						<button type="button" class="btn btn-primary"  onclick="save()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;

						&nbsp;&nbsp;&nbsp;&nbsp;
						<!-- <button type="button" class="btn clear">
							清空
						</button> -->
					</div>
				 </td>
			  </tr>
		</table>	
		 
	    <input type="hidden" name="type" value="1" />
		<c:if test="${config.id!=null}">
			<input type="hidden" name="id" value="${config.id}" />
			<input type="hidden" name="createTime" value="<fmt:formatDate  value="${config.createTime}" pattern="yyyy-MM-dd hh:mm:ss" />"/>
			<input type="hidden" name="createUserId" value="${config.createUserId}" />
			<input type="hidden" name="createUserName" value="${config.createUserName}" />
		</c:if>
		   
	</form>
</div>

<script type="text/javascript">	
function save(){
	var config1 = $("#config1").val()*1;
	if(config1<0){
		alert("每日推荐数量设置应大于等于0");
		return;
	}
	var config2 = $("#config2").val()*1;
	if(config2<0){
		alert("每日添加好友数量设置应大于等于0");
		return;
	}
	var config3 = $("#config3").val()*1;
	if(config3<0){
		alert("每日接收请求数量设置应大于等于0");
		return;
	}
	if(!validateSubmitForm($('#userie_form'))){
		return;
	}
}
</script>
