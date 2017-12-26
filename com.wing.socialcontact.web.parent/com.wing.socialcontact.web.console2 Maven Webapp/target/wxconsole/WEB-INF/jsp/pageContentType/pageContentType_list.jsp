<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
	
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
			<input id="${param.rel}_columnType" name="columnType" type="hidden" value="0"/>
				<div class="search-toolbar">
					<span style="float: left;"> 
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="pageContentType/addPage.do?rel=${param.rel }" title="新增" target="dialog"
								width="800" height="400" rel="${param.rel}_add"><span>新增</span>
							</a>
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="pageContentType/updatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="800" height="400"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="pageContentType/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
						</span> 
		
				</div>
			</form>
		</div>
		<table id="${param.rel }_datagrid" toolbar="#${param.rel}_toolbar"></table>	

<script type="text/javascript">	
	$(function() {
		loadMain();			
	});
	
	function loadMain(){
		$('#<%=request.getParameter("rel")%>_datagrid').datagrid(
			{
				border:true,
				nowrap : false,
				url : "pageContentType/query.do", 
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						{
							field : "name",
							title : "分类名称",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "contentKey",
							title : "键值",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "listUrl",
							title : " 列表页地址",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "orderNum",
							title : "排序",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "op",
							title : "操作",
							align:"center",
							width : $(this).width() * 0.15,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = showviewb('"+rowData.id+"')>查看</a>";
							}
						}
						
				] ],
				
			});
	}
	
	function showviewb(fid){
		MUI.openDialog('查看详情','pageContentType/updatePage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:800,height:400});
	}
</script>














