<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% 
	String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.getSession().setAttribute("path", path);
%>
<div class="easyui-layout" fit="true">
				
		<!-- 左侧-->
	<div region="west" title="资讯分类列表" split="true"	style="width:250px;" >
	
	   	<ul id="${param.rel }_newsclassleft" class="ztree"></ul>
		
	</div>
  	
	<div  region="center" border="false">
		<div id="${param.rel }_box" title="分类管理" class="easyui-panel" fit="true" >
				 <img src="${path}/resource/images/big/arrow_left_48.png" style="vertical-align: middle;"/>
				 <span style="font-weight: bold;">请先点击左侧选择分类</span>	
		</div>
	</div>

</div>				

<script type="text/javascript">	
$(function(){
	queryLeftClass();
});
function queryLeftClass(){
	$.ajax({
		url:"news/classquery.do",
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
				zNodes.push({id : c.id,name : c.tagName,
					pId : c.parentId,href:"news/classupdatePage.do?id="+c.id+"&rel="+rel,dwzTarget:"ajax",
					rel:rel+"_box",open:true});
			});
			$.fn.zTree.init($('#<%= request.getParameter("rel") %>_newsclassleft'), setting, zNodes);
		}
	});
}
</script>














