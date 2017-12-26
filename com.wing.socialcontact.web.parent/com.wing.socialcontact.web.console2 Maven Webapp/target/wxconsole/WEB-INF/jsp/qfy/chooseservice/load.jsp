<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
				<span>
					<label style="width: 100px;">企业名称：</label>
					<input type="text" name="name"/>
				</span>
				<span>
					<label style="width: 100px;">联盟标题：</label>
					<input type="text" name="title"/>
				</span>
				<span>
					<label style="width: 100px;">所属用户：</label>
					<input type="text" name="ssUserId"/>
				</span>
				<span>
					<label style="width: 100px;">创建时间：</label>
					<input type="text"   name="createTime"   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 150px;"/>
				</span>
				</div>
				<div class="search-toolbar">
					<span style="float: left;"> 
						<shiro:hasPermission name="entryPrise:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="添加精选企服"
								href="qfy/chooseservice/updatePage.do?rel=${param.rel}"
								target="navTab" 
								rel="${param.rel}_update" ><span>添加精选企服</span>
							</a>
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
				url : "qfy/chooseservice/query.do?type=1", 
				columns : [ [
						{
								field : "name",
								title : "企业名称",
								width : $(this).width() * 0.15,
								align : "center"
						},
						{
								field : "title",
								title : "联盟标题",
								width : $(this).width() * 0.15,
								align : "center"
						},
						{
								field : "serviceCount",
								title : "服务数量",
								width : $(this).width() * 0.15,
								align : "center"
						},
						{
							field : "ssUserId",
							title : "所属用户ID",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "sortNum",
							title : "排序",
							width : $(this).width() * 0.1,
							align : "center"
						},
						{
							field : "createTime",
							title : "创建时间",
							width : $(this).width() * 0.1,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm");
							}
						},
						{
							field : "op",
							title : "操作",
							align:"center",
							width : 50,
							formatter: function(rowIndex, rowData){
									return "<a href = 'javaScript:void(0)' onclick=removesservice('"+rowData.id+"')>移除</a>";
							}
						}
						
				] ],
				
			});
	}
	
	function removesservice(id){
		$.ajax({
			url : "qfy/chooseservice/update.do",
			dataType : "json",
			data : {
				id : id,
				type: 0
			},
			async : false,
			success: function(data){
				$("#${param.rel}_datagrid").datagrid('reload');
			}
		});
	}
</script>














