<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%--
	模块：系统管理
--%>

	<form  action="entryServiceClass/update.do"   method="post"   onsubmit="return validateSubmitForm(this)">
		 <div  style="margin-left: 10px;margin-top: 10px;margin-bottom: 5px;">
				<shiro:hasPermission name="entryServiceClass:add">
					<a class="btn btn-primary" href="entryServiceClass/addPage.do?rel=${param.rel}" rel="${param.rel }_box" target="ajax" ><i class="boot_icon-plus-sign boot_icon-white"></i> 添加顶级分类</a>
			
					<c:url var="url_addxjclass" value="entryServiceClass/addPage.do" >
						<c:param name="rel" value="${param.rel}" />
						<c:param name="parentId" value="${requestScope.dto.id }" />
						<c:param name="className" value="${requestScope.dto.className }" />
					</c:url>
					
					<a class="btn btn-primary" href="${url_addxjclass }" rel="${param.rel }_box" target="ajax" ><i class="boot_icon-plus-sign boot_icon-white"></i> 添加下级分类</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="entryServiceClass:delete">
					<c:url var="url_delxjclass" value="entryServiceClass/del.do" >
						<c:param name="id" value="${requestScope.dto.id}" />
						<c:param name="otherBoxId" value="${param.rel}_box" />
						<c:param name="otherCallback" value="entryServiceClass/addPage.do?rel=${param.rel}" />
						<c:param name="callback_fn" value="queryLeftClass" />
					</c:url>
				
					<a class="btn btn-danger" target="ajaxTodo"  href="${url_delxjclass }" title="您确定删除分类[${requestScope.dto.className }]吗？"><i class="boot_icon-remove-sign boot_icon-white"></i> 删除</a>
				</shiro:hasPermission>
			
		 </div>
		 <table class="table table-nobordered " style="margin-top: 25px;">
			  <tr>
			    	<th style="width: 80px">分类名称：</th>
			    	<td>
			     		<input type="text" name="className" class="easyui-validatebox" data-options="required:true"  value="${dto.className}" data-options="validType:['length[1,20]']"   maxlength="20" />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">分类序号：</th>
			    	<td>
			     		<input type="text" name="sortNum" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  value="${dto.sortNum}"  class="easyui-numberbox" required="true"    min="1" max="99999999" />
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 80px">是否显示：</th>
			    	<td>
			     		<select name="isShow" sValue="${dto.isShow}">
						<option  value="0">是</option>
						<option  value="1">否</option>
					    </select>
			    	</td>
			  </tr>
			  <tr>
				 <th></th>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="submit" class="btn btn-primary" >保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
						  <!-- <button type="button" class="btn clear" >清空</button> -->
					</div>
				 </td>
			  </tr>
		</table>	 
		  
		  <input type="hidden" name="id" value="${dto.id }"/>
		  <input type="hidden" name="parentId" value="${dto.parentId }"/>
		  <input type="hidden" name="createTime"  value="<fmt:formatDate  value="${dto.createTime}" pattern="yyyy-MM-dd hh:mm:ss" />"/>
		  <input type="hidden" name="currentCallback" value="refresh" /> 
	      <input type="hidden" name="callback_fn" value="queryLeftClass"/>
		   
	</form>

