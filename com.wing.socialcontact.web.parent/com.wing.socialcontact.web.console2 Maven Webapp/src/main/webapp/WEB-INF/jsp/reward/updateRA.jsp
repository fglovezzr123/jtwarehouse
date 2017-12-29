<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>

<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理
--%>
<div style="width: 600px;margin: 0 auto;" >
	<form  action="reward/updateRA.do"   method="post"  id="news_form"  afterCallback="saveCallback" enctype="multipart/form-data">
		 <input id="createTime" name="createTime" value="<fmt:formatDate  value='${a.fbcreateTime}' pattern='yyyy-MM-dd HH:mm:ss' />" type="hidden" />
		 <table class="table table-nobordered " style="margin-top: 25px;">
			 <tr>
			    	<th style="width: 100px">发布人：</th>
			    	<td >
			     		<div>${a.fbUserName}</div>
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
			     		${a.question}
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">所属行业：</th>
			    	<td >
			     		${a.className}
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">悬赏金额：</th>
			    	<td >
			     		${a.reward}
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">悬赏有效期：</th>
			    	<td >
			     		<fmt:formatDate  value='${a.startTime}' pattern='yyyy-MM-dd' />-<fmt:formatDate  value='${a.endTime}' pattern='yyyy-MM-dd' />
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">应答人：</th>
			    	<td >
			     		<div>${a.createUserName}</div>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">应答状态：</th>
			    	<td>
			    	<c:if test="${a.isAccept==1}">已采纳</c:if>
			    	<c:if test="${a.isAccept==2}">未采纳</c:if>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">应答内容：</th>
			    	<td >
			     	 <textarea  name="content"  rows="5" maxlength="50" style="width:400px">${a.content}</textarea>
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
		  
		  <input type="hidden" name="id" value="${a.id }"/>
		  <input type="hidden" name="currentCallback" value="close" />
		  <input type="hidden" name="datagridId" value="${param.rel }_datagrid" />	
		   
	</form>
</div>

<script type="text/javascript">	
$(function() {
	$("#cjsj").html($("#createTime").val()); 
});
function save(){
	var flag = true;
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
