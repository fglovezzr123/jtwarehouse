<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ taglib uri="/WEB-INF/tag/myTag.tld" prefix="my" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理
--%>
<div style="width: 600px;margin: 0 auto;" >
	<form  action="reward/update.do"   method="post" id="news_form"  afterCallback="saveCallback" enctype="multipart/form-data">
		 <input id="createUserName" name="createUserName" value="${r.createUserName}"  type="hidden" />
		 <input id="createTime" name="createTime" value="<fmt:formatDate  value='${r.createTime}' pattern='yyyy-MM-dd HH:mm:ss' />"  type="hidden" />
		 <input id="question" name="question" value="${r.question}"  type="hidden" />
		 <input id="rewardFinish" name="rewardFinish" value="${r.rewardFinish}"  type="hidden" />
		 <input id="createUserId" name="createUserId" value="${r.createUserId}"  type="hidden" />
		 <input id="isShow" name="isShow" value="${r.isShow}"  type="hidden" />
		 <input id="status" name="status" value="${r.status}"  type="hidden" />
		 
		 <table class="table table-nobordered " style="margin-top: 25px;">
			 <tr>
			    	<th style="width: 100px">发布人：</th>
			    	<td >
			     		<div id="fbr"></div>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">创建时间：</th>
			    	<td >
			     		<div id="cjsj"></div>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">悬赏问题：</th>
			    	<td >
			     		${r.question}
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">所属行业：</th>
			    	<td >
			     	<select name="voType"  id="voType" style="width: 120px;">
						<c:forEach var="c" items="${list}">
						<c:if test="${r.voType==c.id}">
						<option  value="${c.id}" selected>${c.className}</option>
						</c:if>
						<c:if test="${r.voType!= c.id}">
						<option  value="${c.id}">${c.className}</option>
						</c:if>
						</c:forEach>	
					</select>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">悬赏金额：</th>
			    	<td >
			     		<input type="text" name="reward"  value="${r.reward}" class="easyui-numberbox" required="true"  maxlength="10" />J币
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">悬赏有效期时间：</th>
			    	<td >
			     		<input type="text" name="startTime" id="startTime" value="<fmt:formatDate  value="${r.startTime}" pattern="yyyy-MM-dd" />"  maxlength="50" class="easyui-validatebox" required="true"    onFocus="WdatePicker({skin:'whyGreen',startDate:'%y-%M-%d %H:00:00',dateFmt:'yyyy-MM-dd'})" />
						-
					   <input type="text" name="endTime" id="endTime" value="<fmt:formatDate  value="${r.endTime}" pattern="yyyy-MM-dd" />" maxlength="50" class="easyui-validatebox" required="true"   onFocus="WdatePicker({skin:'whyGreen',startDate:'%y-%M-%d %H:00:00',dateFmt:'yyyy-MM-dd'})" />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">仅好友可见：</th>
			    	<td >
			     		<select name="isShow" id="isShow" style="width: 120px;">
						<option  value="1" <c:if test="${r.isShow==1}">selected</c:if>>是</option>
						<option  value="2"  <c:if test="${r.isShow==2}">selected</c:if>>否</option>
					</select>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">状态：</th>
			    	<td>
			    	<c:if test="${r.status==1}">
			    	<input type="radio" name="rstatus" value="2" style="margin-top: -2px;">审核通过</input>
			    	<input type="radio" name="rstatus" value="3" style="margin-top: -2px;">取消</input>
			    	</c:if>
			    	<c:if test="${r.status==2}">
			    	<input type="radio" name="rstatus" value="3" style="margin-top: -2px;">取消</input>
			    	</c:if>
			    	<c:if test="${r.status==4}">
			    	 已完成
			    	</c:if>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">备注：</th>
			    	<td >
			     	 <textarea  name="remark"  rows="2" maxlength="50" style="width:400px">${r.remark}</textarea>
			    	</td>
			  </tr>
			  <tr>
				 <th></th>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="button" class="btn btn-primary" onclick="save()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
						  <!-- <button type="button" class="btn clear" >清空</button> -->
					</div>
				 </td>
			  </tr>
		</table>	 
		  
		  <input type="hidden" name="id" value="${r.id }"/>
		  <input type="hidden" name="currentCallback" value="close" />
		  <input type="hidden" name="datagridId" value="${param.rel }_datagrid" />	
		   
	</form>
</div>

<script type="text/javascript">	
$(function() {
	$("#fbr").html($("#createUserName").val());
	$("#cjsj").html($("#createTime").val());
});
function save(){
	var flag = true;
	var start=$("#startTime").val();
	var end=$("#endTime").val();
	if(start==""){
		Msg.alert("提示","请选择统计开始时间！","warning",null);
		return;
	}
	if(end==""){
		Msg.alert("提示","请选择统计结束时间！","warning",null);
		return;
	}
	if(start>=end){
		Msg.alert("提示","统计结束时间必须大于开始时间！","warning",null);
		return;
	}
	var status = $('input[name="rstatus"]:checked').val();
	if(status!=null&&status!=""){
		$("#status").val(status);
	}
	if(flag){
		if(!validateSubmitForm($('#news_form'))){
			return;
		}
	}
}
var saveCallback=function(obj,msg){
	if(msg.statusCode == 200){
		$("#${param.rel}_datagrid").datagrid('reload');
		$("#${param.rel}_update").dialog("close");
	}else{
		Msg.alert("提示",msg.message,"error");
	}
}
</script>
