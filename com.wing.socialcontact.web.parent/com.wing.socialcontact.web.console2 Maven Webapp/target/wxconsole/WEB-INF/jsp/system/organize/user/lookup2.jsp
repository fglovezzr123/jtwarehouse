<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%--
	模块：系统管理--组织机构 -- 用户管理--查找带回
--%>

<div class="easyui-layout"  fit="true">
   <!-- 左侧
	<div region="west" title="部门列表" split="true"	style="width:250px;" >
	
	   	<ul id="${param.rel }_leftTree" class="ztree"></ul>
		
	</div>-->
  	
	<div  region="center" border="false">
		
		<!--<div id="${param.rel }_box" class="easyui-panel" fit="true"  title="用户查询">-->
			 
			<c:choose>
				<c:when test="${param.type==1 }">
					<%--单选--%>
					 <%@ include file="/WEB-INF/jsp/system/organize/user/lookup_query2.jsp"%>
				</c:when>
				<c:when test="${param.type==2 }">
					<%--多选--%>
					 <%@ include file="/WEB-INF/jsp/system/organize/user/lookup_more_query2.jsp"%>
				</c:when>
				
				<c:when test="${param.type==3 }">
					<%--多选--%>
					 <%@ include file="/WEB-INF/jsp/system/organize/user/lookup_more_query3.jsp"%>
				</c:when>
				
			</c:choose>
		
		
		<!--</div>-->
	</div>
   
</div>

<script type="text/javascript">

</script>