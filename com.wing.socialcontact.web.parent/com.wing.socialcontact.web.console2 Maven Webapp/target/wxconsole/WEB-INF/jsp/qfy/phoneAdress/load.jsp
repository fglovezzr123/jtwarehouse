<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">用户ID：</label>
						<input type="text" name="ssUserId" name="ssUserId1"/>
					</span>
					<span>
						<label style="width: 100px;">账号姓名：</label>
						<input type="text" name="ssUserName" name="ssUserName1"/>
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
						<a class="easyui-linkbutton" icon="icon-export" plain="true" id="exportId"><span>导出excel</span>
						</a>
<!-- 						<a class="easyui-linkbutton" icon="icon-add" plain="true" -->
<!-- 							href="qfy/phoneAdress/export.do" title="导出excel"><span>导出excel</span> -->
<!-- 						</a> -->
					</span>
						<%-- <shiro:hasPermission name="entryQuickDoor:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="quickDoor/updatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="600" height="500"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="entryQuickDoor:delete">
					   		<a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="quickDoor/del.do" target="selectedTodo"  title="确定要删除吗?" warn="请至少选择一条记录">批量删除</a>
				       </shiro:hasPermission>
						</span>  --%>
		
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
					</span>
		
				</div>
			</form>
			<form id="exportForm2"> 
				<input type="hidden" id="ssUserId" name="ssUserId" />
				<input type="hidden" id="ssUserName" name="ssUserName" />
			</form>
		</div>
		<table id="${param.rel }_datagrid" toolbar="#${param.rel}_toolbar"></table>

				

<script type="text/javascript">	
	$(function() {
		$("#exportId").click(function(){
			$("#exportForm2").attr("action","qfy/phoneAdress/export.do");
			$("#exportForm2").attr("target","exportFram");
			
			$("#ssUserId").val($("#ssUserId1").val());
			$("#ssUserName").val($("#ssUserName1").val());
			
			$("#exportForm2").submit();
		});	
	});

	$(function() {
		loadMain();			
	});
	
	function loadMain(){
		$('#<%=request.getParameter("rel")%>_datagrid').datagrid(
			{
				url : "qfy/phoneAdress/query.do", 
				columns : [ [
						{
							field:"ck",
							title : "勾选",
							checkbox:true
						},   
						{
								field : "userName",
								title : "通讯录姓名",
								width : $(this).width() * 0.10,
								align : "center",
						},   
						{
							field : "userPhone",
							title : "手机号",
							width : $(this).width() * 0.20,
							align : "center",
						},
						{
							field : "ssUserPhone",
							title : "所属手机号",
							width : $(this).width() * 0.20,
							align : "center",
						},
						{
							field : "ssUserId",
							title : "用户ID",
							width : $(this).width() * 0.10,
							align : "center",
						},
						{
							field : "ssUserName",
							title : "账号人姓名",
							width : $(this).width() * 0.20,
							align : "center",
						},
						{
							field : "createTime",
							title : "创建时间",
							width : $(this).width() * 0.30,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm");
							}
						}
						
				] ],
				
			});
	}
</script>














