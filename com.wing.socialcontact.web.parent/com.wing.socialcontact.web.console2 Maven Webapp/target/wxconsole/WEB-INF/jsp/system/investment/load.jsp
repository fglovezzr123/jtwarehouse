<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<input id="${param.rel}_classId" name="classId" type="hidden" value="0"/>
				<div class="search-content">
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
						<%-- <shiro:hasPermission name="investment:add">
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="investment/addPage.do?rel=${param.rel }" title="新增" target="dialog"
								width="800" height="600" rel="${param.rel}_add"><span>新增</span>
							</a>
						</shiro:hasPermission>  --%>
						<%-- <shiro:hasPermission name="investment:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="investment/updatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="800" height="600"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
						</shiro:hasPermission> --%>
						<%-- <shiro:hasPermission name="investment:delete">
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="investment/del.do" target="selectedTodo"  title="确定要删除吗?" warn="请至少选择一条记录">批量删除</a>
				       </shiro:hasPermission> --%>
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
				url : "investment/query.do", 
				columns : [ [
						 {
								field : "classId",
								title : "投资类型",
								width : $(this).width() * 0.15,
								align : "center",
						},
						/* {
							field : "content",
							title : "文章内容",
							width : $(this).width() * 0.15,
							align : "center"
						}, */
						{
							field : "createTime",
							title : "添加时间",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm:ss");
							}
						},
						{
							field : "op",
							title : "操作",
							align:"center",
							width : $(this).width() * 0.15,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = updateview('"+rowData.id+"')>编 辑</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href = 'javaScript:void(0)' onclick = showview('"+rowData.id+"')>查 看</a>";
							}
						}
						
				] ],
				
			});
	}
	
	function showview(fid){
		MUI.openDialog('查看详情','investment/viewPage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_show',{width:800,height:600});
	}
	function updateview(fid){
		MUI.openDialog('编辑基金投资','investment/updatePage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:800,height:600});
	}
</script>














