<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
	
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
			<input id="${param.rel}_columnType" name="columnType" type="hidden" value="0"/>
				<div class="search-toolbar">
					<span style="float: left;"> 
						<shiro:hasPermission name="banner:add">
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="banner/guide/addPage.do?rel=${param.rel }&columnType={#${param.rel}_columnType}" title="新增" target="dialog"
								width="800" height="400" rel="${param.rel}_add"><span>新增</span>
							</a>
						</shiro:hasPermission> 
						<shiro:hasPermission name="banner:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="banner/guide/updatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="800" height="400"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="banner:delete">
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="banner/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
				       </shiro:hasPermission>
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
				url : "banner/guide/query.do", 
				fitColumns: false,
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						},{
							field : "title",
							title : "名称",
							width : 200,
							align : "center",
						},
						{
							field : "orderNum",
							title : "排序",
							width : 80,
							align : "center",
						},
						{
							field : "createTime",
							title : "发布时间",
							width : 120,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd");
							}
						},
						{
							field : "op",
							title : "操作",
							align:"center",
							width : 80,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = showviewb1('"+rowData.id+"')>查看</a>";
							}
						}
						
				] ],
				
			});
	}
	
	function showviewb1(fid){
		MUI.openDialog('查看详情','banner/guide/viewPage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:800,height:400});
	}
</script>
