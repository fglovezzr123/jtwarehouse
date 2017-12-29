<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >

				<div class="search-toolbar">
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
						<!-- <button class="btn btn-small clear" type="button">清空</button>&nbsp; -->
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
				url : "pastatic/query.do", 
				columns : [ [
						{
								field : "fCount",
								title : "拒绝",
								width : $(this).width() * 0.5,
								align : "center",
						},   
						{
							field : "tCount",
							title : "同意",
							width : $(this).width() * 0.5,
							align : "center",
					}
						
				] ],
				
			});
	}
</script>














