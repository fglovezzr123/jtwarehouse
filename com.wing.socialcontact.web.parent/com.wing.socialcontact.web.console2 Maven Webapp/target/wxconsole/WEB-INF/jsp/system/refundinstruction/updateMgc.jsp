<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
%>    
<%--
	模块：
--%>
<div  >
	<form  action="refundinstruction/update.do"  id="minganci_form"  method="post"   onsubmit="return validateSubmitForm(this)">
		  <table class="table table-nobordered " style="margin-top: 25px;">
		 	  <input type="hidden"  value="3" name="id"/>
		 	  <input type="hidden"  value="${dto.remark }" name="remark"/>
			  <tr>
			    	<th style="width:150px">说明：</th>
			    	<td>
			     		${dto.remark }
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 150px">敏感词：</th>
			    	<td>
			     		<textarea rows="30" style="height:300px; width:500px;"  name="content" >${dto.content }</textarea>
			    	</td>
			  </tr>
			  <tr>
				 <th></th>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="button" class="btn btn-primary"  onclick="save()">保存</button>
					</div>
				 </td>
			  </tr>
		  </table>
		<input type="hidden" name="currentCallback" value="refresh" />
	</form>
</div>
<script type="text/javascript">

function save(){
	$("#minganci_form").submit();
}
var saveCallback=function(obj,msg){
	if(msg.statusCode == 200){
		location.reload() ;
	}else{
		Msg.alert("提示",msg.message,"error");
	}
}

</script>
