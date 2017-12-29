<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%--
	模块：系统管理-- 资讯分类管理--添加分类
--%>


	<form  action="clubclass/add.do"  method="post"   onsubmit="return validateSubmitForm(this)">
	       <input id="parentId" name="parentId" value="${param.parentId}"  type="hidden" />
		  <div  style="margin-left: 20px;margin-top: 10px">
				<c:choose>
					<c:when test="${param.parentId!=null}">
					<h4><p class="text-info">添加分类：上级分类为：<span style="color: red">[ <c:out  value="${param.superName }" /> ]</span></p></h4>
						
					</c:when>
					<c:otherwise>
						<h4><p class="text-info">添加顶级分类</p></h2>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="divider"></div>
		  <table class="table table-nobordered " style="margin-top: 25px;">
		 
			  <tr>
			    	<th style="width: 80px">分类名称：</th>
			    	<td>
			     		<input type="text" name="name" class="easyui-validatebox" required="true"  data-options="validType:['length[1,20]']"   maxlength="20" />
			    	</td>
			  </tr>
			 <tr>
			    	<th style="width: 80px">分类序号：</th>
			    	<td>
			     		<input type="text" name="sort"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" class="easyui-numberbox" required="true"   maxlength="3" />
			    	</td>
			  </tr>
			  <!-- <tr>
			    	<th style="width: 80px">是否推荐：</th>
			    	<td>
			     		<select name="recommend" >
						<option  value="1">是</option>
						<option  value="0">否</option>
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
		  
		  <input type="hidden" name="currentCallback" value="refresh" />
		
		  <input type="hidden" name="callback_fn" value="queryLeftClass"/>
		
	</form>
	
