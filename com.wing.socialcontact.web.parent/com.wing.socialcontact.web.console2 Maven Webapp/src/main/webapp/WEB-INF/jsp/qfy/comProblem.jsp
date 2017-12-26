<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<%--
	模块：
--%>

	<form  action="entryDescConfig/update.do"  id="comProblem_form"  method="post"   onsubmit="return validateSubmitForm(this)">
	    <input id="imgPath" name="imgPath" value="${dto.imgPath}"  type="hidden" />
		<div class="divider"></div>
		  <table class="table table-nobordered " style="margin-top: 25px;">
		 
			  <tr>
			    	<th style="width: 120px">链接：</th>
			    	<td>
			     		<input type="text" name="link" class="easyui-validatebox" value="${dto.link}" required="true"  data-options="validType:['length[1,255]']"   maxlength="255" />
			    	</td>
			  </tr>
			  <tr>
				 <th></th>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="button" class="btn btn-primary"  onclick="save()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
						  <!-- <button type="button" class="btn clear" >清空</button> -->
					</div>
				 </td>
			  </tr>
		  
		  </table>
		  <input type="hidden" name="createTime" value="<fmt:formatDate  value="${dto.createTime}" pattern="yyyy-MM-dd hh:mm:ss" />"/>
		  <input type="hidden" name="createUserId" value="${dto.createUserId}"/>
		  <input type="hidden" name="id" value="${dto.id}"/>
		  <input type="hidden" name="status" value="${dto.status}"/>
		  <input type="hidden" name="type" value="${dto.type}"/>
	</form>
<script type="text/javascript">
function save(){
	var flag = true;
	if(flag){
		if(!validateSubmitForm($('#comProblem_form'))){
			return;
		}
	}
}
</script>
