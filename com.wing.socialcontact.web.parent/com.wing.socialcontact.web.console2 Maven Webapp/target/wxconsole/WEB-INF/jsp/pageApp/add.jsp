<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理-- 聚合页面管理--添加
--%>

<div style="width: 98%;margin: 0 auto;" >
	<form  action="pageConfig/add.do"  method="post" id="pageConfig_form" enctype="multipart/form-data" >
		<table class="table table-bordered">
			<tr>
				<th style="width:20%">页面名称：</th>
		    	<td style="width:80%">
		     		<input type="text" name="pageName" class="easyui-validatebox" required="true" data-options="validType:['length[1,20]']"  maxlength="20" />
		    	</td>
		  	</tr>
			<tr>
		    	<th>是否有轮播图：</th>
		    	<td>
               		<input type="radio" name="lbtFlag" value="1" style="margin-top: -2px;"/><span style="margin-right:10px;margin-left: 2px;">是</span>
               		<input type="radio" name="lbtFlag" value="0" style="margin-top: -2px;" checked="checked"/><span style="margin-left: 2px;">否</span>
               	</td>
			</tr>
			<tr id="look_lbt_td" style="display: none;">
		    	<th>轮播图：</th>
		    	<td>
		    		<select name="lbtId"  id="lbtId" style="width: 220px;">
						<c:forEach var="c" items="${values}">
							<option value="${c.id}">${c.listValue}</option>
						</c:forEach>	
					</select>
               	</td>
			</tr>
			<tr>
				<th>禁用跳转链接：</th>
		    	<td>
		    		<input type="text" name="tyUrl" class="easyui-validatebox" required="true" data-options="validType:['length[1,150]']"  maxlength="150" />
		    	</td>
		  	</tr>
		  	<tr>
				<th>页面状态：</th>
		    	<td>
		    		禁用
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
		<input type="hidden" name="pageType" value="2"/>
		<input type="hidden" name="kjrkFlag" value="0"/>
		<input type="hidden" name="status" value="0" />
		<input type="hidden" name="datagridId" value="${param.rel }_datagrid" />
		<input type="hidden" name="currentCallback" value="close" />
	</form>
	<span class="typefile" id="filePicker"></span>
</div>

<script type="text/javascript">	
$(function(){
	$(":radio[name='lbtFlag']").click(function(){
		var v=$(":radio[name='lbtFlag']:checked").attr("value");
		if(v == 1){
			$("#look_lbt_td").show();
		}else{
			$("#look_lbt_td").hide();
		}
	});
});

var flag = true;
function save(){
	if(!validateSubmitForm($('#pageConfig_form'))){
		return;
	}
}
</script>
