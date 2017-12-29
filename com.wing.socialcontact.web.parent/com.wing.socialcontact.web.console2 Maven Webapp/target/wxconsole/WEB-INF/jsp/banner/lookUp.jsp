<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div class="easyui-layout" data-options="fit:true">
	<div id='center' data-options="region:'center',title:'',split:true" border="false">
		<div class="easyui-layout" data-options="fit:true">
			<div id='center' data-options="region:'center',title:'',split:true" border="false">
				<%@ include file="/WEB-INF/jsp/banner/lookUpList.jsp"%>
			</div>
		</div>
	</div>
</div>