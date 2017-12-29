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
						<shiro:hasPermission name="entryPrise:add">
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="qfy/entry/addPage.do?rel=${param.rel }" title="新增" target="dialog"
								width="800" height="600" rel="${param.rel}_add"><span>新增</span>
							</a>
						</shiro:hasPermission> 
						<shiro:hasPermission name="entryPrise:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="qfy/entry/updatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="800" height="600"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="entrycustomer:read">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="客服管理"
								href="qfy/entrycustomer/load.do?pid={id}&rel=${param.rel}"
								target="navTab" rel="${param.rel}_customer" warn="请先选择一条记录"><span>客服管理</span>
							</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="entryPrise:delete">
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="qfy/entry/del.do" target="selectedTodo"  title="确定要删除吗?" warn="请至少选择一条记录">批量删除</a>
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
				url : "qfy/entry/query.do", 
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
								align : "center"
						},
						{
								field : "logoImgPath",
								title : "logo",
								width : $(this).width() * 0.1,
								align : "center",formatter:function(value,row){
									var str = "";
									if(value != "" && value != null){
										str = "<img style=\"height: 30px;width: 30px;\" src=\""+value+"\"/>";
                                        return str;
									}
								}
						},
						{
							field : "className",
							title : "分类",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
								field : "title",
								title : "联盟标题",
								width : $(this).width() * 0.2,
								align : "center"
						},
						{
								field : "serviceCount",
								title : "服务数量",
								width : $(this).width() * 0.1,
								align : "center"
						},
						{
							field : "ssUserId",
							title : "所属用户ID",
							width : $(this).width() * 0.1,
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
						}
						
				] ],
				
			});
	}
</script>














