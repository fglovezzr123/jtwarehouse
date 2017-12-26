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
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
							<a class="easyui-linkbutton" icon="icon-tip" plain="true" id="exportId"
								href="qfy/entry/staticExport.do?rel=${param.rel }" title="excel"><span>导出excel</span>
							</a>
						</span> 
		
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
					</span>
		
				</div>
			</form>
			<form id="exportFormForEntryStatic"> 
				<input type="hidden" id="name" name="name" />
				<input type="hidden" id="title" name="title" />
				<input type="hidden" id="ssUserId" name="ssUserId" />
			</form>
		</div>
		<table id="${param.rel }_datagrid" toolbar="#${param.rel}_toolbar"></table>

				

<script type="text/javascript">	
	$(function() {
		$("#exportId").click(function(){
			$("#exportFormForEntryStatic").attr("action","qfy/entry/staticExport.do");
			$("#exportFormForEntryStatic").attr("target","exportFram");
			
			$("#ssUserId").val($("#ssUserId1").val());
			$("#ssUserName").val($("#ssUserName1").val());
			
			$("#exportFormForEntryStatic").submit();
		});	
		loadMain();			
	});
	
	function loadMain(){
		$('#<%=request.getParameter("rel")%>_datagrid').datagrid(
			{
				url : "qfy/entry/staticQuery.do", 
				columns : [ [
						{
							field:"ck",
							title : "勾选",
							checkbox:true
						},   
						{
								field : "name",
								title : "企业名称",
								width : $(this).width() * 0.2,
								align : "center"
						},
						{
								field : "title",
								title : "联盟标题",
								width : $(this).width() * 0.3,
								align : "center"
						},
						{
							field : "ssUserId",
							title : "所属用户ID",
							width : $(this).width() * 0.1,
							align : "center"
						},
						{
							field : "phoneCount",
							title : "电话咨询",
							width : $(this).width() * 0.1,
							align : "center"
						},
						{
							field : "msgCount",
							title : "在线咨询",
							width : $(this).width() * 0.1,
							align : "center"
						},
						{
							field : "totalCount",
							title : "合计",
							width : $(this).width() * 0.1,
							align : "center"
						},
						{
							field : "serviceCount",
							title : "服务数量",
							width : $(this).width() * 0.1,
							align : "center"
						},
						{
							field : "sortNum",
							title : "排序",
							width : $(this).width() * 0.1,
							align : "center"
						}/* ,
						{
							field : "createTime",
							title : "创建时间",
							width : $(this).width() * 0.1,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm");
							}
						} */
						
				] ],
				
			});
	}
</script>














