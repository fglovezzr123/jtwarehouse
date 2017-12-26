<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%--
	模块：系统管理
--%>

	<form  action="news/classupdate.do"   method="post"   onsubmit="return validateSubmitForm(this)" enctype="multipart/form-data">
		 <div  style="margin-left: 10px;margin-top: 10px;margin-bottom: 5px;">
				<shiro:hasPermission name="newsclass:add">
					
					<c:url var="url_addxjclass" value="news/classaddPage.do" >
						<c:param name="rel" value="${param.rel}" />
						<c:param name="parentId" value="${requestScope.c.id }" />
						<c:param name="superName" value="${requestScope.c.tagName }" />
					</c:url>
					<c:if test="${requestScope.c.tagName=='资讯'}">
					<a class="btn btn-primary" href="${url_addxjclass }" rel="${param.rel }_box" target="ajax" ><i class="boot_icon-plus-sign boot_icon-white"></i> 添加下级分类</a>
					</c:if>
				</shiro:hasPermission>
				<shiro:hasPermission name="newsclass:delete">
					<c:url var="url_delxjclass" value="news/nclassdel.do" >
						<c:param name="id" value="${requestScope.c.id}" />
						<c:param name="otherBoxId" value="${param.rel}_box" />
						<c:param name="otherCallback" value="news/classaddPage.do?rel=${param.rel}" />
						<c:param name="callback_fn" value="queryLeftClass" />
					</c:url>
				
					<a class="btn btn-danger" target="ajaxTodo"  href="${url_delxjclass }" title="您确定删除分类[${requestScope.c.tagName }]吗？如果此分类有下级分类，或者此分类有资讯，将无法删除！"><i class="boot_icon-remove-sign boot_icon-white"></i> 删除</a>
				</shiro:hasPermission>
			
		 </div>
		 <table class="table table-nobordered " style="margin-top: 25px;">
			  <tr>
			    	<th style="width: 80px">标签名称：</th>
			    	<td>
			     		<input type="text" name="tagName" class="easyui-validatebox" required="true"  data-options="validType:['length[1,4]']" maxlength="4"  value="${c.tagName}"/>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">标签序号：</th>
			    	<td>
			     		<input type="text" name="tagNum" value="${c.tagNum}"  class="easyui-numberbox" required="true"    maxlength="3" />
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 80px">是否显示：</th>
			    	<td>
			     		<select name="isShow" >
						<option  value="1" <c:if test="${c.isShow==1}">selected</c:if>>显示</option>
						<option  value="2" <c:if test="${c.isShow==2}">selected</c:if>>不显示</option>
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
		  
		  <input type="hidden" name="id" value="${c.id }"/>
		  <input id="createUserId" name="createUserId" value="${c.createUserId}"  type="hidden" />
		  <input id="createUserName" name="createUserName" value="${c.createUserName}"  type="hidden" />
		   <input type="hidden" name="parentId" value="${c.parentId}"/>
		  <input type="hidden" name="createTime" value="<fmt:formatDate  value="${c.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />"/>
		   <input type="hidden" name="currentCallback" value="refresh" /> 
	      <input type="hidden" name="callback_fn" value="queryLeftClass"/>
		   
	</form>


