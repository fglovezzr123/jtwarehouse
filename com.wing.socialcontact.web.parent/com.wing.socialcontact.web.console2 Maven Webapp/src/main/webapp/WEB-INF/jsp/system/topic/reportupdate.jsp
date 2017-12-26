<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ taglib uri="/WEB-INF/tag/myTag.tld" prefix="my" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理
--%>
<div style="width: 600px;margin: 0 auto;" >
	<form  action="topic/reportupdate.do"   method="post" id="news_form"  afterCallback="saveCallback" enctype="multipart/form-data"> 
		  <table class="table table-nobordered " style="margin-top: 25px;">
		  		<tr>
			    	<th style="width: 100px">举报人：</th>
			    	<td >
			     		<div id="jbr"></div>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">创建时间：</th>
			    	<td >
			     		<div id="cjsj"></div>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">话题PK：</th>
			    	<td >
			     		<div id="htpk"></div>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">状态：</th>
			    	<td >
			     		<select name="isShow" id="isShow" style="width: 120px;">
						<option  value="1" <c:if test="${r.isShow==1}">selected</c:if>>显示</option>
						<option  value="2" <c:if test="${r.isShow==2}">selected</c:if>>不显示</option>
					</select>
			    	</td>
			  </tr> 
			  <tr>
			    	<th style="width: 100px">举报内容：</th>
			    	<td >
			     	 <textarea  name="content" id="content" rows="5" maxlength="50" style="width:400px">${r.content}</textarea>
			    	</td>
			  </tr>
			  <tr>
				 <th></th>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="submit" class="btn btn-primary" onclick="save()" >保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
						  <button type="button"  onclick="clear1()">清空</button>
					</div>
				 </td>
			  </tr>
		</table>	 
		  
		  <input type="hidden" name="id" value="${r.id }"/>
		 <input id="createTime" name="createTime" value="<fmt:formatDate  value='${r.createTime}' pattern='yyyy-MM-dd HH:mm:ss' />"  type="hidden" />
		  <input id="userId" name="userId" value="${r.userId}"  type="hidden" />
		  <input id="fkId" name="fkId" value="${r.fkId}"  type="hidden" />
		   <input id="rtType" name="rtType" value="${r.rtType}"  type="hidden" />
		  <input type="hidden" name="currentCallback" value="close" />
		  <input type="hidden" name="datagridId" value="${param.rel }_datagrid" />	
		   
	</form>
</div>

<script type="text/javascript">	
var content = "";
$(function() {
//	var cj = new Date($("#createTime").val()).format("yyyy-MM-dd HH:mm:ss");
	$("#jbr").html('${r.nickname}');
	$("#cjsj").html($("#createTime").val());
	$("#htpk").html('${r.topicDesc}');
	content = '${r.content}';
});
function save(){
	var flag = true;
//	$("#createTime").val(new Date('${r.createTime}'));
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
function clear1(){
	$("#content").val('${r.content}');
}

</script>
