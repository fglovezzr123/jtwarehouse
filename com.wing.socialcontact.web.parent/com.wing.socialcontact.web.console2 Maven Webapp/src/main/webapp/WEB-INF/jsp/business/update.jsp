<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ taglib uri="/WEB-INF/tag/myTag.tld" prefix="my" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理
--%>
<div style="width: 600px;margin: 0 auto;" >
	<form  action="business/update.do"   method="post" id="news_form"  onsubmit="return validateSubmitForm(this)" enctype="multipart/form-data">
		 <input id="createUserName" name="createUserName" value="${b.createUserName}"  type="hidden" />
		 <input id="createTime" name="createTime" value="<fmt:formatDate  value='${b.createTime}' pattern='yyyy-MM-dd HH:mm:ss' />"  type="hidden" />
		 <input id="appealType" name="appealType" value="${b.appealType}"  type="hidden" />
		 <input id="titles" name="titles" value="${b.titles}"  type="hidden" />
		 <input id="appealSummary" name="appealSummary" value="${b.appealSummary}"  type="hidden" />
		 <input id="lookCount" name="lookCount" value="${b.lookCount}"  type="hidden" />
		 <input id="rewardFinish" name="rewardFinish" value="${b.rewardFinish}"  type="hidden" />
		 <input id="createUserId" name="createUserId" value="${b.createUserId}"  type="hidden" />
		 
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
			    	<th style="width: 100px">合作标题：</th>
			    	<td >
			     		${b.titles}
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">合作分类：</th>
			    	<td >
			     	<select name="bizType"  id="bizType" style="width: 120px;">
						<c:forEach var="c" items="${list}">
						<c:if test="${b.bizType==c.id}">
						<option  value="${c.id}" selected>${c.className}</option>
						</c:if>
						<c:if test="${b.bizType!= c.id}">
						<option  value="${c.id}">${c.className}</option>
						</c:if>
						</c:forEach>	
					</select>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">悬赏：</th>
			    	<td >
			     		<input type="text" name="reward"  value="${b.reward}" class="easyui-numberbox" required="true"  maxlength="10" />J币
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">诉求分类：</th>
			    	<td >
			     		<div id="sqfl"></div>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">合作诉求：</th>
			    	<td >
			     		${b.appealSummary}
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">有效期：</th>
			    	<td >
			     		<input type="text" name="startTime" id="startTime" value="<fmt:formatDate  value="${b.startTime}" pattern="yyyy-MM-dd" />"  maxlength="50" class="easyui-validatebox" required="true"    onFocus="WdatePicker({skin:'whyGreen',startDate:'%y-%M-%d %H:00:00',dateFmt:'yyyy-MM-dd'})" />
						-
					   <input type="text" name="endTime" id="endTime" value="<fmt:formatDate  value="${b.endTime}" pattern="yyyy-MM-dd" />" maxlength="50" class="easyui-validatebox" required="true"   onFocus="WdatePicker({skin:'whyGreen',startDate:'%y-%M-%d %H:00:00',dateFmt:'yyyy-MM-dd'})" />
			    	</td>
			  </tr>
			
			   <tr>
			    	<th style="width: 100px">仅好友可见：</th>
			    	<td >
			     		<select name="isShow" id="isShow" style="width: 120px;">
						<option  value="1" <c:if test="${b.isShow==1}">selected</c:if>>是</option>
						<option  value="2"  <c:if test="${b.isShow==2}">selected</c:if>>否</option>
					</select>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">状态：</th>
			    	<td>
			     		<select name="status"  style="width: 120px;">
						<option  value="1" <c:if test="${b.status==1}">selected</c:if>>审核中</option>
						<option  value="2" <c:if test="${b.status==2}">selected</c:if>>审核成功</option>
						<option  value="3" <c:if test="${b.status==3}">selected</c:if>>审核失败</option>
					</select>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">是否推荐：</th>
			    	<td >
                		<select name="isRecommend" >
						<option  value="1" <c:if test="${b.isRecommend==1}">selected</c:if>>推荐</option>
						<option  value="2"  <c:if test="${b.isRecommend==2}">selected</c:if>>不推荐</option>
					    </select>   	
                	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">合作说明：</th>
			    	<td >
			     	 <textarea  name="appealDesc"  rows="5" maxlength="500" style="width:400px">${b.appealDesc}</textarea>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">备注：</th>
			    	<td >
			     	 <textarea  name="appealRemark"  rows="2" maxlength="50" style="width:400px">${b.appealRemark}</textarea>
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
		  
		  <input type="hidden" name="id" value="${b.id }"/>
		  <input type="hidden" name="currentCallback" value="close" />
		  <input type="hidden" name="datagridId" value="${param.rel }_datagrid" />	
		   
	</form>
</div>

<script type="text/javascript">	
$(function() {
//	var cj = new Date('${b.createTime}').format("yyyy-MM-dd");
	$("#fbr").html($("#createUserName").val());
	$("#cjsj").html($("#createTime").val());
	var appealType = $("#appealType").val();
	var type = "";
	if(appealType=="1"){
		type = "供给";
	}else if(appealType=="2"){
		type = "需求";
	}
	$("#sqfl").html(type);
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
//	$("#startTime").val(new Date($("#startTimef").val()));
//	$("#endTime").val(new Date($("#endTimef").val()));
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
