<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel}_datagrid');" >
				<div class="search-content">
				 <span>
					<label style="width: 100px;">产品类型：</label>
					<select name="financeType" style="width: 120px;">
						<option value="">全部</option>
					</select>
				</span>
				<span>
					<label style="width: 100px;">是否显示：</label>
					<select name="isShow" id="isShow" required="true" style="width: 120px;">
						<option  value="1">是</option>
						<option  value="2">否</option>
					</select>
				</span>
				<span>
					<label style="width: 100px;">是否推荐：</label>
						<select name="isRecommend" id="isRecommend" required="true" style="width: 120px;">
						<option  value="1">是</option>
						<option  value="2">否</option>
					</select>
				</span> 
				
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
						<%--<shiro:hasPermission name="entryQuickDetailBanner:delete">--%>
					   		<a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
							href="investProduct/del.do" target="selectedTodo"  title="确定要删除吗?" warn="请至少选择一条记录">批量删除</a>
				       <%--</shiro:hasPermission>--%>
						</span> 
		
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="button" onclick="loadMain()">查询</button>&nbsp;
						<button class="btn btn-primary btn-small" type="button">导出</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
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
				url : "investProduct/query.do",
				columns : [ [
						{
							field:"ck",
							title : "勾选",
							checkbox:true
						},   
						{
								field : "productName",
								title : "项目或产品名称",
								width : $(this).width() * 0.10,
								align : "center",
						},
						{
								field : "orgName",
								title : "产品类型",
								width : $(this).width() * 0.20,
								align : "center",
						},
					
					{
						field: "orgName",
						title: "姓名",
						width: $(this).width() * 0.20,
						align: "center",
					},
						{
							field : "createDate",
							title : "创建时间",
							width : $(this).width() * 0.30,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm");
							}
						}/* ,
						{
							field : "op",
							title : "操作",
							align:"center",
							width : $(this).width() * 0.15,
							formatter: function(rowIndex, rowData){
								//活动开始前可编辑
								if(rowData.status<4){
								return  "<a href = 'javaScript:void(0)' onclick = updateview('"+rowData.id+"')>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href = 'javaScript:void(0)' onclick = deleteactivity('"+rowData.id+"')>删除</a>";
								}
								return "<a href = 'javaScript:void(0)' onclick = deleteactivity('"+rowData.id+"')>删除</a>";
							}
						} */
						
				] ],
				
			});
	}
</script>














