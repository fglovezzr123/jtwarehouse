<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">投资分类名称：</label>
						<input	type="text" name="name" class="span2"/>
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
						<shiro:hasPermission name="investmentClass:add">
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="investmentClass/addPage.do?rel=${param.rel }" title="新增" target="dialog"
								width="1200" height="800" rel="${param.rel}_add"><span>新增</span>
							</a>
						</shiro:hasPermission> 
						<shiro:hasPermission name="investmentClass:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="investmentClass/updatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="800" height="400"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="investmentClass:delete">
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="investmentClass/del.do" target="selectedTodo"  title="确定要删除吗?如果分类下有投资信息将不会被删除" warn="请至少选择一条记录">批量删除</a>
				       </shiro:hasPermission>
						</span> 
		
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
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
				url : "investmentClass/query.do", 
				columns : [ [
				             
						{
							field:"ck",
							title : "勾选",
							checkbox:true
						},   
			             {		field : "name",
								title : "投资分类名称",
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
										.format("yyyy-MM-dd");
							}
						},
						{
							field : "op",
							title : "操作",
							align:"center",
							width : $(this).width() * 0.15,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = showviewn('"+rowData.id+"')>查看</a>";
							}
						}
						
				] ],
				
			});
	}
	
	function showviewn(fid){
		MUI.openDialog('查看详情','investmentClass/viewPage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_show',{width:800,height:400});
	}
</script>














