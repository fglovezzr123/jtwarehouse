<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
	
<div class="easyui-layout" fit="true" >		
	<!-- 左侧-->
	<div region="west" title="栏目类别列表" split="true"	style="width:200px;" >
	   	<ul id="${param.rel }_leftTree" class="ztree"></ul>	
	</div>
	
	<div  region="center" border="false">
	   <%@ include file="/WEB-INF/jsp/banner/query.jsp"%>
	</div>			
</div>		

<script type="text/javascript">	
$(function(){

	$.ajax({
		url:"list/showSelectList.do",
		cache: false,
		dataType:"json",
		data:{
			type:'${type}'
		},
		success:function(json){
			var setting = {
					view: {
						showIcon: false
					},
					data: {
						simpleData: {
							enable: true
						}
					},
					callback: {
						onClick: updateBannerId
					}
			};
			var rel='<%= request.getParameter("rel") %>_datagrid';
			var zNodes = new Array();
			zNodes.push({id : 0,name : "栏目类别",open:true,datagrid : rel,param : {"columnType":""},updateTitle:"banner查询--全部类别"});
			$.each(json, function(i, d) {
				zNodes.push({id : d.id,name : d.listValue,pId : 0,open:true,
				datagrid : rel,param : {"columnType":d.id},updateTitle:"banner查询--"+d.listValue});
				
			});
			$.fn.zTree.init($('#<%= request.getParameter("rel") %>_leftTree'), setting, zNodes);
			
		}
	});
	
});
function updateBannerId(event,treeId, treeNodeJSON){
	$('#<%= request.getParameter("rel") %>_columnType').val(treeNodeJSON.id);
	refreshDatagrid(event,treeId, treeNodeJSON);
}
	
</script>














