<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>


<div class="easyui-layout"  fit="true">
   <!-- 左侧-->
	
	<div  region="center" border="false">
		<div id="${param.rel }_box" class="easyui-panel" fit="true"  title="勋章查询">
			
			<%@ include file="/WEB-INF/jsp/userHonor/lookup_more_query.jsp"%>
		
		
		</div>
	</div>
   
</div>

<script type="text/javascript">


</script>