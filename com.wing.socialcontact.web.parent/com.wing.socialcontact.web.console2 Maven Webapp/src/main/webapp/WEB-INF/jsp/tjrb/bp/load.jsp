<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel}_datagrid');" >
				<div class="search-content">
				<span>
					<label style="width: 70px;">项目名称：</label>
					<input type="text" name="orgNam"/>
				</span>
				<span>
					<label style="width: 70px;">姓名：</label>
					<input type="text" name="productName"/>
				</span>
					<span>
					<label style="width: 70px;">地域：</label>
					<input type="text" name="orgNam"/>
				</span>
				<span>
					<label style="width: 100px;">行业：</label>
					<select name="financeType" style="width: 120px;">
						<option value="">全部</option>
					</select>
				</span>
				</div>

				<div class="search-toolbar">
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="button" onclick="loadMain()">查询</button>&nbsp;
						<button class="btn btn-primary btn-small" type="button">导出</button>&nbsp;
					</span>
		
				</div>
			</form>
		</div>
		<table id="${param.rel}_datagrid" toolbar="#${param.rel}_toolbar"></table>

				

<script type="text/javascript">	
	$(function() {
		loadMain();			
	});
	
	function loadMain(){
		$('#${param.rel}_datagrid').datagrid(
			{
				url : "bpPerson/bpquery.do",
				columns : [ [
						{
							field:"ck",
							title : "勾选",
							checkbox:true
						},   
						{
								field : "productName",
								title : "项目名称",
								width : $(this).width() * 0.10,
								align : "center",
						},
						{
								field : "dpName",
								title : "姓名",
								width : $(this).width() * 0.20,
								align : "center",
						},
					
					{
						field: "mobile",
						title: "联系方式",
						width: $(this).width() * 0.20,
						align: "center",
					},
					{
						field: "are",
						title: "地域",
						width: $(this).width() * 0.20,
						align: "center",
					},
					{
						field: "focusOn",
						title: "行业",
						width: $(this).width() * 0.20,
						align: "center",
					}
						
				] ],
				
			});
	}
</script>














