<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理-- 聚合页面管理--添加
--%>

<div style="width: 98%;margin: 0 auto;" >
	<form  action="pageConfig/update.do"  method="post" id="pageConfig_form" enctype="multipart/form-data" >
		<table class="table table-bordered">
			<tr>
				<th style="width:20%">页面名称：</th>
		    	<td style="width:80%">
		     		<input type="text" name="pageName" class="easyui-validatebox" required="true" data-options="validType:['length[1,20]']"  maxlength="20" value="${b.pageName }"/>
		    	</td>
		  	</tr>
		  	<tr>
				<th>页面链接：</th>
		    	<td>
		    		${b.pageUrl }
		    	</td>
		  	</tr>
			<tr>
		    	<th>是否有轮播图：</th>
		    	<td>
		    		<c:choose>
		    			<c:when test="${b.lbtFlag eq 1 }">
		    				<input type="radio" name="lbtFlag" value="1" style="margin-top: -2px;" checked="checked"/><span style="margin-right:10px;margin-left: 2px;">是</span>
               				<input type="radio" name="lbtFlag" value="0" style="margin-top: -2px;"/><span style="margin-left: 2px;">否</span>
		    			</c:when>
		    			<c:otherwise>
		    				<input type="radio" name="lbtFlag" value="1" style="margin-top: -2px;"/><span style="margin-right:10px;margin-left: 2px;">是</span>
               				<input type="radio" name="lbtFlag" value="0" style="margin-top: -2px;" checked="checked"/><span style="margin-left: 2px;">否</span>
		    			</c:otherwise>
		    		</c:choose>
               	</td>
			</tr>
			<c:choose>
    			<c:when test="${b.lbtFlag eq 1 }">
					<tr id="look_lbt_td">
				    	<th>轮播图：</th>
				    	<td>
				    		<select name="lbtId"  id="lbtId" style="width: 220px;">
								<c:forEach var="c" items="${values}">
									<c:choose>
										<c:when test="${b.lbtId eq c.id }">
											<option value="${c.id}" selected="selected">${c.listValue}</option>
										</c:when>
										<c:otherwise>
											<option value="${c.id}">${c.listValue}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>	
							</select>
		               	</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr id="look_lbt_td" style="display: none;">
				    	<th>轮播图：</th>
				    	<td>
				    		<select name="lbtId"  id="lbtId" style="width: 120px;">
								<c:forEach var="c" items="${values}">
									<c:choose>
										<c:when test="${b.lbtId eq c.id }">
											<option value="${c.id}" selected="selected">${c.listValue}</option>
										</c:when>
										<c:otherwise>
											<option value="${c.id}">${c.listValue}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>	
							</select>
		               	</td>
					</tr>
				</c:otherwise>
			</c:choose>
			<tr>
				<th>禁用跳转链接：</th>
		    	<td>
		    		<input type="text" name="tyUrl" class="easyui-validatebox" required="true" value="${b.tyUrl }" maxlength="150"/>
		    	</td>
		  	</tr>
		  	<tr>
				<th>页面状态：</th>
		    	<td>
		    		<c:choose>
		    			<c:when test="${b.status eq 1}">
		    				启用
		    			</c:when>
		    			<c:otherwise>禁用</c:otherwise>
		    		</c:choose>
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
		<input type="hidden" name="id" value="${b.id }"/>
		<input type="hidden" name="pageType" value="${b.pageType }"/>
		<input type="hidden" name="pageUrl" value="${b.pageUrl }"/>
		<input type="hidden" name="kjrkFlag" value="${b.kjrkFlag }"/>
		<input type="hidden" name="createTime" value='<fmt:formatDate value="${b.createTime }" type="both" dateStyle="medium" timeStyle="medium"/>' />
		<input type="hidden" name="createUserId" value="${b.createUserId }" />
		<input type="hidden" name="status" value="${b.status }" />
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
