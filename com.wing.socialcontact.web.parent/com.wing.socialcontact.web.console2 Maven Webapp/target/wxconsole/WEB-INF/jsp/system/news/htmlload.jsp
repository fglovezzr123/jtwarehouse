<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div class="easyui-layout" fit="true" >		
	<!-- 左侧-->
	<div region="west" title="静态页列表" split="true"	style="width:200px;" >
	   		<ul id="${param.rel }_leftTree" class="ztree"></ul>	
	</div>
	
	<div  region="center" border="false">
	   <div id="${param.rel }_box" title="分类管理" class="easyui-panel" fit="true" >
	   </div>
	</div>			
</div>				

<script type="text/javascript">	
$(function(){
	queryLeftClass();
});
function queryLeftClass(){
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
						onClick: zTreeClick
					}
			};
			var rel='<%= request.getParameter("rel") %>';
			var zNodes = new Array();
			zNodes.push({id : 0,name : "静态页",open:true});
			$.each(json, function(i, c){
				zNodes.push({id : c.id,name : c.listValue,
					pId : 0,href:"news/htmlupdatePage.do?types="+c.id+"&newsTitle="+c.listValue+"&rel="+rel,dwzTarget:"ajax",
					rel:rel+"_box",open:true});
			});
			$.fn.zTree.init($('#<%= request.getParameter("rel") %>_leftTree'), setting, zNodes);
		}
	});
}
</script>














