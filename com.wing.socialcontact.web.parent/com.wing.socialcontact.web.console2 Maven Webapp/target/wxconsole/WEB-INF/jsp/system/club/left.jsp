<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%--
	模块：系统管理--组织机构--用户管理
--%>

<div class="easyui-layout" fit="true" >
   <!-- 左侧-->
	<div region="west" title="分类列表" split="true" 	style="width:250px;" >
	
	   	<ul id="${param.rel }_leftTree" class="ztree"></ul>
		
	</div>
  	
	<div  region="center" border="false">
		<%@ include file="/WEB-INF/jsp/system/club/load.jsp"%>
	</div>
   
</div>

<script type="text/javascript">
<!--
	$(function(){

		$.ajax({
			url:"clubclass/load/all.do",
			cache: false,
			dataType:"json",
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
							onClick: updateclubclassId
						}
				};
				var rel='<%= request.getParameter("rel") %>_datagrid';
				var zNodes = new Array();
				zNodes.push({id : 0,name : "全部分类",open:true,datagrid : rel,param : {"classId":""},updateTitle:"会所查询--全部分类"});
				$.each(json, function(i, d) {
					
					zNodes.push({id : d.id,name : d.name,pId : d.parentId,open:true,
					datagrid : rel,param : {"parentId":d.id},updateTitle:"会所查询--"+d.name});
					
				});
				$.fn.zTree.init($('#<%= request.getParameter("rel") %>_leftTree'), setting, zNodes);
				
			}
		});
		
	});
	function updateclubclassId(event,treeId, treeNodeJSON){
		$('#<%= request.getParameter("rel") %>_classId').val(treeNodeJSON.id);
		refreshDatagrid(event,treeId, treeNodeJSON);
	}
	
//-->
</script>

