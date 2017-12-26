<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理-- 资讯分类管理--添加分类
--%>

<div style="width: 800px;margin: 0 auto;" >
	<form  action="investmentIntention/update.do"  method="post" id="investmentIntention_form" afterCallback="saveCallback" enctype="multipart/form-data" >
		  <input id="imgpath" name="imgpath" value=""  type="hidden" />
		  <table class="table table-nobordered " style="margin-top: 25px;">
		 
			  <tr>
			    	<th style="width: 80px">投资兴趣：</th>
			    	<td >
			     		<input type="text"  value="${dto.className}" name="className" class="easyui-validatebox" required="true"  data-options="validType:['length[1,20]']"   maxlength="20" />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">投资额度：</th>
			    	<td >
			     		<input type="text" readonly="readonly"  value="${dto.position}" name="position" class="easyui-numberbox" required="true"  data-options="min:0,precision:2"   maxlength="20" />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">投资状态：</th>
			    	<td >
			     		<select name="status">
			     			<option id="1" value="1">待联系</option>
			     			<option id="2" value="2">待考虑</option>
			     			<option id="3" value="3">已投资</option>
			     			<option id="4" value="4">已拒绝</option>
			     		</select>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">联系人：</th>
			    	<td >
			     		<input type="text"  value="${dto.userName}" readonly="readonly" name="userName" class="easyui-validatebox" required="true"  data-options="validType:['length[1,20]']"   maxlength="20" />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">联系电话：</th>
			    	<td >
			     		<input type="text"  value="${dto.phone}" readonly="readonly" name="phone" class="easyui-validatebox" required="true"  data-options="validType:['length[1,20]']"   maxlength="20" />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">投资意向留言：</th>
			    	<td >
			     		<textarea rows="5" cols="" name="message" readonly="readonly" >${dto.message}</textarea>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">客服回复：</th>
			    	<td >
			     		<textarea rows="5" cols="" name="answer" >${dto.answer}</textarea>
			    	</td>
			  </tr>
			  <tr>
				 <th></th>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="button" class="btn btn-primary"  onclick="save()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
				 </td>
			  </tr>
		  </table>
		  
		  <input type="hidden" name="datagridId" value="${param.rel }_datagrid" />	
		  <input type="hidden" name="currentCallback" value="close" />
		  <input type="hidden" name="id" value="${dto.id }"/>
		  <input type="hidden" name="createTime" value="<fmt:formatDate  value="${dto.createTime}" pattern="yyyy-MM-dd hh:mm:ss" />"/>
		  <input type="hidden" name="createUserId" value="${dto.createUserId}"/>
	</form>
	
	
</div>

<script type="text/javascript">	

function save(){
	
	if(true){
		if(!validateSubmitForm($('#investmentIntention_form'))){
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
$(document).ready(function(){
	$("#"+${dto.status}).attr("selected","selected");
})



</script>
