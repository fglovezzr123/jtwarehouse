<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ taglib uri="/WEB-INF/tag/myTag.tld" prefix="my" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<c:choose>
	<c:when test="${dto==null}">
		<c:set value="add" var="operateMethod"></c:set>
	</c:when>
	<c:when test="${dto.id==null or dto.id eq ''}">
		<c:set value="add" var="operateMethod"></c:set>
	</c:when>
	<c:otherwise>
		<c:set value="update" var="operateMethod"></c:set>
	</c:otherwise>
</c:choose>

<div style="width: 800px;margin: 0 auto;" >
	<form  action="userIntegralEmpirical/${operateMethod}.do"   method="post" id="userie_form"   enctype="multipart/form-data" onsubmit="return validateSubmitForm(this)">
		 <table class="table table-nobordered " style="margin-top: 25px;">
			  
			  <tr>
				<th width="15%">
					每天积分上限：
				</th>
				<td width="85%" colspan="3">
					<input type="text"  name="ruleExplain"   required="true"   class="easyui-numberbox" maxlength="11" precision="0" value="${dto.ruleExplain}"/>
				</td>
			</tr>
			  <tr>
				<th width="15%">
					每天经验值上限：
				</th>
				<td width="85%" colspan="3">
					<input type="text"   name="remark"   required="true"   class="easyui-numberbox" maxlength="11" precision="0" value="${dto.remark}"/>
				</td>
			</tr>
			  <tr>
				 <th></th>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						 <button type="submit" class="btn btn-primary">
							保存
						</button>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<!-- <button type="button" class="btn clear">
							清空
						</button> -->
					</div>
				 </td>
			  </tr>
		</table>	
		 
	    <input type="hidden" name="ieType" value="3" />
		<input type="hidden" name="currentCallback" value="close" />	
		
		  
		<c:if test="${dto.id!=null}">
			<input type="hidden" name="id" value="${dto.id}" />
			<input type="hidden" name="createTime" value="<fmt:formatDate  value="${dto.createTime}" pattern="yyyy-MM-dd hh:mm:ss" />"/>
			<input type="hidden" name="createUserId" value="${dto.createUserId}" />
			<input type="hidden" name="createUserName" value="${dto.createUserName}" />
			<input type="hidden" name="updateUserId" value="${dto.updateUserId}" />	
			<input type="hidden" name="updateTime" value="<fmt:formatDate  value="${dto.updateTime}" pattern="yyyy-MM-dd hh:mm:ss" />"/>			
		</c:if>
		   
	</form>
</div>

<script type="text/javascript">	

</script>
