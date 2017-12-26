<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%--
	模块：系统管理
--%>

	<form  action="clubclass/update.do"   method="post"   onsubmit="return validateSubmitForm(this)">
		 <div  style="margin-left: 10px;margin-top: 10px;margin-bottom: 5px;">
				<shiro:hasPermission name="clubclass:add">
					<a class="btn btn-primary" href="clubclass/addPage.do?rel=${param.rel}" rel="${param.rel }_box" target="ajax" ><i class="boot_icon-plus-sign boot_icon-white"></i> 添加顶级分类</a>
			
					<c:url var="url_addxjclass" value="clubclass/addPage.do" >
						<c:param name="rel" value="${param.rel}" />
						<c:param name="parentId" value="${requestScope.c.id }" />
						<c:param name="superName" value="${requestScope.c.name }" />
					</c:url>
					
					<a class="btn btn-primary" href="${url_addxjclass }" rel="${param.rel }_box" target="ajax" ><i class="boot_icon-plus-sign boot_icon-white"></i> 添加下级分类</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="clubclass:delete">
					<c:url var="url_delxjclass" value="clubclass/classdel.do" >
						<c:param name="id" value="${requestScope.c.id}" />
						<c:param name="otherBoxId" value="${param.rel}_box" />
						<c:param name="otherCallback" value="clubclass/addPage.do?rel=${param.rel}" />
						<c:param name="callback_fn" value="queryLeftClass" />
					</c:url>
				
					<a class="btn btn-danger" target="ajaxTodo"  href="${url_delxjclass }" title="您确定删除分类[${requestScope.c.name }]吗？如果此分类有下级分类，或者此分类有会所，将无法删除！"><i class="boot_icon-remove-sign boot_icon-white"></i> 删除</a>
				</shiro:hasPermission>
			
		 </div>
		 <table class="table table-nobordered " style="margin-top: 25px;">
			  <tr>
			    	<th style="width: 80px">分类名称：</th>
			    	<td>
			     		<input type="text" name="name" class="easyui-validatebox" data-options="required:true"  value="${c.name}"/>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">分类序号：</th>
			    	<td>
			     		<input type="text" name="sort"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" value="${c.sort}"  class="easyui-numberbox" required="true"    maxlength="3" />
			    	</td>
			  </tr>
			  <!--  <tr>
			    	<th style="width: 80px">推荐：</th>
			    	<td>
						<select name="recommend">
							<option value="1"   id ="1">是</option>
							<option value="0"  id="0">否</option>
						</select>
			    	</td>
			  </tr> -->
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
		  <input type="hidden" name="parentId" value="${c.parentId }"/>
		  <input type="hidden" name="createTime"  value="<fmt:formatDate  value="${dto.createTime}" pattern="yyyy-MM-dd hh:mm:ss" />"/>
		  <input type="hidden" name="currentCallback" value="refresh" /> 
	      <input type="hidden" name="callback_fn" value="queryLeftClass"/>
		   
	</form>
<script type="text/javascript">
<!--

/* $(document).ready(function(){
	$("#"+${c.recommend}).attr("selected","selected");
}) ; */

//-->
</script>

