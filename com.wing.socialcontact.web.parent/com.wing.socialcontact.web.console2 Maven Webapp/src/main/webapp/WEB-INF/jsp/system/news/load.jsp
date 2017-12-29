<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
		
<div class="easyui-layout" fit="true" >		
	<!-- 左侧-->
	<div region="west" title="资讯分类列表" split="true"	style="width:200px;" >
	   		<ul id="${param.rel }_leftTree" class="ztree"></ul>	
	</div>
	
	<div  region="center" border="false">
	   <%@ include file="/WEB-INF/jsp/system/news/query.jsp"%>
	</div>		
</div>	

<script type="text/javascript">	
$(function(){
	$.ajax({
		url:"news/nclassquery.do",
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
						onClick: updateNewsId
					}
			};
			var rel='<%= request.getParameter("rel") %>_datagrid';
			var zNodes = new Array();
			$.each(json, function(i, d) {
				zNodes.push({id : d.id,name : d.tagName,pId :d.parentId,open:true,
				datagrid : rel,param : {"types":d.id},updateTitle:"资讯查询--"+d.tagName});
			});
			$.fn.zTree.init($('#<%= request.getParameter("rel") %>_leftTree'), setting, zNodes);
			
		}
	});
	
});
function updateNewsId(event,treeId, treeNodeJSON){
	$('#<%= request.getParameter("rel") %>_types').val(treeNodeJSON.id);
	$('#<%= request.getParameter("rel") %>_typename').val(treeNodeJSON.name);
	refreshDatagrid(event,treeId, treeNodeJSON);
}

</script>














