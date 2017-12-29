<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%--
	模块：系统管理-- 资讯分类管理--添加分类
--%>


	<form  action="news/classadd.do"  method="post"   onsubmit="return validateSubmitForm(this)">
	       <input id="parentId" name="parentId" value="${param.parentId}"  type="hidden" />
		  <div  style="margin-left: 20px;margin-top: 10px">
				<c:choose>
					<c:when test="${param.parentId!=null}">
					<h4><p class="text-info">添加分类：上级分类为：<span style="color: red">[ <c:out  value="${param.superName }" /> ]</span></p></h4>
						
					</c:when>
					
				</c:choose>
			</div>
			<div class="divider"></div>
		  <table class="table table-nobordered " style="margin-top: 25px;">
		 
			  <tr>
			    	<th style="width: 80px">标签名称：</th>
			    	<td>
			     		<input type="text" name="tagName" class="easyui-validatebox" required="true"  data-options="validType:['length[1,4]']"   maxlength="4" />
			    	</td>
			  </tr>
			 <tr>
			    	<th style="width: 80px">标签序号：</th>
			    	<td>
			     		<input type="text" name="tagNum" class="easyui-numberbox" required="true"   maxlength="3" />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">是否显示：</th>
			    	<td>
			     		<select name="isShow" >
						<option  value="1">显示</option>
						<option  value="2">不显示</option>
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
		  
		  <input type="hidden" name="currentCallback" value="refresh" />
		
		  <input type="hidden" name="callback_fn" value="queryLeftClass"/>
		
	</form>
	
