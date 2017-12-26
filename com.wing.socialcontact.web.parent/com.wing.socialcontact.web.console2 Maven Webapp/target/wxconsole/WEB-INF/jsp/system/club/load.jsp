<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<input id="${param.rel}_classId" name="classId" type="hidden" value="0"/>
				<div class="search-content">
					<span>
						<label style="width: 100px;">会所名称：</label>
						<input	type="text" name="title" class="span2"/>
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
						<shiro:hasPermission name="club:add">
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="club/addPage.do?rel=${param.rel }&classId={#${param.rel}_classId}" title="新增" target="dialog"
								width="1000" height="600" rel="${param.rel}_add"><span>新增</span>
							</a>
						</shiro:hasPermission> 
						<shiro:hasPermission name="club:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="club/updatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="1000" height="600"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="club:delete">
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="club/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
				       </shiro:hasPermission>
						</span> 
		
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
					</span>
		
				</div>
			</form>
		</div>
		<table id="${param.rel }_datagrid" toolbar="#${param.rel}_toolbar" title="会所查询--全部分类"></table>

				

<script type="text/javascript">	
	$(function() {
		loadMain();			
	});
	
	function loadMain(){
		$('#<%=request.getParameter("rel")%>_datagrid').datagrid(
			{
				url : "club/query.do", 
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						 {
								field : "title",
								title : "会所名称",
								width : $(this).width() * 0.15,
								align : "center",
						},
						{
							field : "name",
							title : "会所类型",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "phone",
							title : "联系电话",
							width : $(this).width() * 0.15,
							align : "center"
						},
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
								return  "<a href = 'javaScript:void(0)' onclick = showviewn('"+rowData.id+"')>查看</a>|<a href = 'javaScript:void(0)' onclick = yulan('"+rowData.id+"')>预览</a>";
							}
						}
						
				] ],
				
			});
	}
	
	function showviewn(fid){
		MUI.openDialog('查看详情','club/viewPage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_show',{width:1000,height:600});
	}
	function yulan(fid){
		MUI.openDialog('预览','club/viewShow.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_show',{width:350,height:650});
	}
</script>














