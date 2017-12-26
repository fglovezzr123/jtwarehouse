<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理-- 聚合页面管理--添加
--%>

<div style="width: 98%;margin: 0 auto;" >
	<form  action="im/update.do"  method="post" id="group_form" enctype="multipart/form-data" >
		<table class="table table-bordered">
	    	<tr>
				<th style="width: 15%">群主名称：</th>
		    	<td style="width: 85%">
		    		<input type="text" name="creator_name" class="easyui-validatebox" required="true" maxlength="100" readonly="readonly" value="${tjyUser.nickname }"/>
		    		<input type="hidden" name="creator" class="easyui-validatebox" required="true" maxlength="100" value="${tjyUser.id }"/>
		    	</td>
		  	</tr>
		  	  <tr>
				<th style="width: 15%">群权重：</th>
		    	<td style="width: 85%">
		    		<input type="text" name="groupPower" class="easyui-validatebox" readonly="readonly" required="true" maxlength="100" onkeyup="clearNoNum2(this);"   value="${imGroupinfo.groupPower}"/>
		    	</td>
		  	</tr>
		  	 
		  	
		  	<tr>
				<th style="width: 15%">群名称：</th>
		    	<td style="width: 85%">
		    		<input type="text" name="groupName" class="easyui-validatebox" readonly="readonly"  required="true" maxlength="100" value="${imGroupinfo.groupName }"/>
		    	</td>
		  	</tr>
			<tr >
		    	<th>群标签：</th>
		    	<td>
		    		<c:forEach items="${favs }" var="fav">
		    			<c:choose>
		    				<c:when test="${fn:indexOf(fav_id,fav.id) ne -1}">
		    					<input type="checkbox" value="${fav.id }" v_text="${fav.listValue }" name="favs" style="margin-top: -2px;" checked="checked"/><span style="margin-right:20px;margin-left: 2px;">${fav.listValue }</span>
		    				</c:when>
		    				<c:otherwise>
		    					<input type="checkbox" value="${fav.id }" v_text="${fav.listValue }" name="favs" style="margin-top: -2px;"/><span style="margin-right:20px;margin-left: 2px;">${fav.listValue }</span>
		    				</c:otherwise>
		    			</c:choose>
		    		</c:forEach>
               	</td>
			</tr>
			
		  	
		  	<tr id="to_user_ids" >
				<th>
					群成员：
				</th>
				<!-- <td>
					<a id="users"  href="user/lookUpPage2.do?type=3&rel=select_users&userId=${tjyUser.id }" lookupGroup="groupUsers" title="用户查询">
						<textarea  readonly="readonly"  name="names"  toName="groupUsers.userName"   rows="2" style="width: 400px">${user_names}</textarea>
					</a>
					<input type="hidden"  name="users" toName="groupUsers.id" value="${user_ids }" />
					<a class="easyui-linkbutton clearLookup"  icon="icon-cancel" 	plain="true"  href="javascript:;"  clearLookup="groupUsers"  title="清空"></a>
				</td> -->
				
				 
				<td>
					<textarea name="users"  id="users" rows="2" style="width:95%;" readonly="readonly"  required="true">${user_names }</textarea>
				</td>
			</tr>
			<tr >
		    	<th>私密群：</th>
		    	<td>
                   <c:choose>
	    				<c:when test="${imGroupinfo.groupType eq 1}">
	    					<input type="radio" name="groupType" value="1" style="margin-top: -2px;" checked="checked"/><span style="margin-right:20px;margin-left: 2px;">非私密群</span>
               				<input type="radio" name="groupType" value="2" style="margin-top: -2px;"/><span style="margin-left: 2px;">私密群</span>
	    				</c:when>
	    				<c:otherwise>
	    					<input type="radio" name="groupType" value="1" style="margin-top: -2px;"/><span style="margin-right:20px;margin-left: 2px;">非私密群</span>
               				<input type="radio" name="groupType" value="2" style="margin-top: -2px;" checked="checked"/><span style="margin-left: 2px;">私密群</span>
	    				</c:otherwise>
    				</c:choose>
               	</td>
			</tr>
		  	
		  	
			
		</table>
		<input type="hidden" name="id" id="id" value="${imGroupinfo.id }"/>
		
		<input type="hidden" name="createTime" value='<fmt:formatDate value="${imGroupinfo.createTime }" type="both" dateStyle="medium" timeStyle="medium"/>' />
		<input type="hidden" name="datagridId" value="${param.rel }_datagrid" />
		<input type="hidden" name="currentCallback" value="close" />
	</form>
	<span class="typefile" id="filePicker"></span>
</div>

<script type="text/javascript">

function save(){
	var _users=$("#users").val();
	if(isEmpty(_users)){
		Msg.alert("提示","群成员不能为空!");
		return;
	}
	if(!validateSubmitForm($('#group_form'))){
		return;
	}
}
</script>
