<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div class="easyui-layout" fit="true">
				
		<!-- 左侧-->
	<div region="west" title="分类列表" split="true"	style="width:250px;" >
	
	   	<ul id="${param.rel }_classleft" class="ztree"></ul>
		
	</div>
  	
	<div  region="center" border="false">
		<div id="${param.rel }_box" title="分类管理" class="easyui-panel" fit="true" >
			<shiro:hasPermission name="entryServiceClass:add">
				<%@ include file="/WEB-INF/jsp/qfy/serviceClass/classadd.jsp" %>
			</shiro:hasPermission>
			<shiro:lacksPermission name="entryServiceClass:add">
				 <img src="resource/images/big/arrow_left_48.png" style="vertical-align: middle;"/>
				 <span style="font-weight: bold;">请先点击左侧选择分类</span>	
			</shiro:lacksPermission>
		</div>
	</div>

</div>				

<script type="text/javascript">	
$(function(){
	queryLeftClass();
});
function queryLeftClass(){
	$.ajax({
		url:"entryServiceClass/load/all.do",
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
						onClick: zTreeClick
					}
			};
			var rel='<%= request.getParameter("rel") %>';
			var zNodes = new Array();
			$.each(json, function(i, c){
				zNodes.push({id : c.id,name : c.className,
					pId : c.parentId,href:"entryServiceClass/updatePage.do?id="+c.id+"&rel="+rel,dwzTarget:"ajax",
					rel:rel+"_box",open:true});
			});
			$.fn.zTree.init($('#<%= request.getParameter("rel") %>_classleft'), setting, zNodes);
		}
	});
}
</script>














