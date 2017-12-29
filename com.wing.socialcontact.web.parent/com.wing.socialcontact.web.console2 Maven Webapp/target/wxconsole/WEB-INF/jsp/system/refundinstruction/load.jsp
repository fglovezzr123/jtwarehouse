<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
				</div>
				<div class="search-toolbar">
					<span style="float: left;"> 
						<shiro:hasPermission name="refundinstruction:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="refundinstruction/updatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="800" height="600"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
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
				url : "refundinstruction/query.do", 
				columns : [ [
						{
							field : "remark",
							title : "备注",
							width : $(this).width() * 0.1,
							align : "center"
						},
						{
							field : "id",
							title : "ID",
							width : $(this).width() * 0.1,
							align : "center"
						},
						{
							field : "content",
							title : "退款说明",
							width : $(this).width() * 0.3,
							align : "center"
						}
						
				] ],
				
			});
	}
</script>














