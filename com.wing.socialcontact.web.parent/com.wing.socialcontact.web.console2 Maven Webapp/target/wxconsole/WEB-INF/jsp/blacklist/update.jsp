<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理-- 聚合页面管理--添加
--%>

<div style="width: 98%;margin: 0 auto;" >
	<form  action="blacklist/update.do"  method="post" id="blacklist_form" enctype="multipart/form-data" >
		<table class="table table-bordered">
		  	<tr>
				<th style="width:20%">用户：</th>
		    	<td style="width:80%">
		    		<a href="user/lookUpPage2.do?type=1&rel=select_user" lookupGroup="user" title="用户查询">
						<textarea readonly="readonly"  name="names" class="easyui-validatebox" required="true"  toName="user.userName" style="height: 20px;line-height: 20px;">${b.tjyUser.nickname }</textarea>
					</a>
					<input type="hidden" name="userId" toName="user.id" value="${b.userId }"/>
		    	</td>
		  	</tr>
			<tr>
				<th>开始时间：</th>
		    	<td>
		    		<input type="text" name="beginTime" id="beginTime" value='<fmt:formatDate value="${b.beginTime}" type="both" pattern="yyyy-MM-dd HH:mm"/>' class="easyui-validatebox" required="true" readonly="readonly" onfocus="WdatePicker({skin:'whyGreen',startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm'})"/>
		    	</td>
		  	</tr>
		  	<tr>
				<th>结束时间：</th>
				<td><input type="text" name="endTime" id="endTime" value='<fmt:formatDate value="${b.endTime}" type="both" pattern="yyyy-MM-dd HH:mm"/>' class="easyui-validatebox" required="true" readonly="readonly" onfocus="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'beginTime\')}',dateFmt:'yyyy-MM-dd HH:mm'})"/></td>
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
		<input type="hidden" name="managerUser" value="${b.managerUser }" />
		<input type="hidden" name="managerTime" value='<fmt:formatDate value="${b.managerTime }" type="both" dateStyle="medium" timeStyle="medium"/>'/>
		<input type="hidden" name="id" value="${b.id}" />
		<input type="hidden" name="lastUser" value="${b.lastUser}" />
		<input type="hidden" name="lastTime" value='<fmt:formatDate value="${b.lastTime }" type="both" dateStyle="medium" timeStyle="medium"/>' />
		<input type="hidden" name="datagridId" value="${param.rel }_datagrid" />
		<input type="hidden" name="currentCallback" value="close" />
	</form>
</div>

<script type="text/javascript">
function save(){
	var beginTime = $("#beginTime").val();
	var endTime = $("#endTime").val();
	if(beginTime > endTime){
		Msg.alert("提示","开始时间不能大于结束时间","error");
		return;
	}
	if(!validateSubmitForm($('#blacklist_form'))){
		return;
	}
}
</script>
