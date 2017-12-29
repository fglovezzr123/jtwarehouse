<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
<div id="cc" class="easyui-layout" data-options="fit:true">  
    <div id='center' data-options="region:'center',title:'',split:true" style="" border="false">
		<div id="qfy_entrycustomer_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'qfy_entrycustomer_datagrid');" formatFilterData="true">
				<div class="search-content">
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
						<shiro:hasPermission name="entrycustomer:add">
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="qfy/entrycustomer/addPage.do?rel=qfy_entrycustomer&pid=${pid}" title="新增" target="dialog"
								width="800" height="400" rel="qfy_entrycustomer_add"><span>新增</span>
							</a>
						</shiro:hasPermission> 
						<shiro:hasPermission name="entrycustomer:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="qfy/entrycustomer/updatePage.do?id={id}&rel=qfy_entrycustomer"
								target="dialog" width="800" height="400"
								rel="qfy_entrycustomer_update" warn="请先选择一条记录"><span>修改</span>
							</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="entrycustomer:delete">
					   		<a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
							href="qfy/entrycustomer/del.do" target="selectedTodo"  title="确定要删除吗?" warn="请至少选择一条记录">批量删除</a>
				        </shiro:hasPermission>
						</span> 
				</div>
			</form>
		</div>
		<table id="qfy_entrycustomer_datagrid" toolbar="#qfy_entrycustomer_toolbar"></table>
	</div>
	
</div>
				

<script type="text/javascript">	
	$(function() {
		loadMain();			
	});
	
	function loadMain(){
		$('#qfy_entrycustomer_datagrid').datagrid(
			{
				url : "qfy/entrycustomer/query.do?entryId=${pid}", 
				columns : [ [
						{
							field:"ck",
							title : "勾选",
							checkbox:true
						}, 
						{
							field : "name",
							title : "企业名称",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "title",
							title : "联盟标题",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "ssUser",
							title : "所属用户",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "customerPhoneNum",
							title : "客服电话",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "customerName",
							title : "客服名称",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "sortNum",
							title : "序号",
							width : $(this).width() * 0.15,
							align : "center",
						}
				] ],
			});
	}
	
	
//-->
</script>














