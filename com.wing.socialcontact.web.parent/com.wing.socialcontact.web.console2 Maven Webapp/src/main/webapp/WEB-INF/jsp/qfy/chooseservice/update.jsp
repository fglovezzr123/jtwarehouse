<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}0_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }0_datagrid');" >
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
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
					</span>
				</div>
			</form>
		</div>
		<table id="${param.rel }0_datagrid" toolbar="#${param.rel}0_toolbar"></table>
<script type="text/javascript">	
var pid = ""; 
var flag = "";
	$(function() {
		loadMain();			
	});
	
	function loadMain(){
		$('#<%=request.getParameter("rel")%>0_datagrid').datagrid(
			{
				url : "qfy/chooseservice/query.do?type=0", 
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
									return "<a href = 'javaScript:void(0)' onclick=addservice('"+rowData.id+"')>添加</a>";
							}
						}
						
				] ],
				
			});
	}
	
	function addservice(id){
		
		$.ajax({
			url:"qfy/chooseservice/update.do",
			dataType: "json",
			data:{
				id : id,
				type:1
				},
			async : false,
			success: function(data){
					$("#${param.rel}0_datagrid").datagrid('reload');
			} 
		});
		
	}
	
//-->
</script>

