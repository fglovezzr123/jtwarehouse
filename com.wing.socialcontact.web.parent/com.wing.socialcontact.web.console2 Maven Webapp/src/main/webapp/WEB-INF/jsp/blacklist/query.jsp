<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% 
	String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.getSession().setAttribute("path", path);
%>
<div id="cc" class="easyui-layout" data-options="fit:true">  
    <div id='center' data-options="region:'center',title:'',split:true" border="false">
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 80px;">用户姓名：</label>
						<input type="text" name="tjyUser.nickname" class="span2" placeholder="用户姓名"/>
					</span>
					<span>
						<label style="width: 80px;">手机号：</label>
						<input type="text" name="tjyUser.mobile" class="span2" placeholder="用户手机号"/>
					</span>
					<span>
						<label style="width: 80px;">操作人：</label>
						<input type="text" name="managerUserName" class="span2" placeholder="操作人名称"/>
					</span>
				</div>
				<div class="search-toolbar">
					<span style="float: left;"> 
						<shiro:hasPermission name="blacklist:add">
							<a class="easyui-linkbutton" icon="icon-add" plain="true" href="blacklist/addPage.do?rel=${param.rel }" title="新增黑名单" target="dialog" width="500" height="300" rel="${param.rel}_add"><span>新增</span></a>
						</shiro:hasPermission> 
						<shiro:hasPermission name="blacklist:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改黑名单" href="blacklist/updatePage.do?id={id}&rel=${param.rel}" target="dialog" width="500" height="300" rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span></a>
						</shiro:hasPermission>
						<shiro:hasPermission name="blacklist:delete">
					   		<a  class="easyui-linkbutton" icon="icon-remove" plain="true" href="blacklist/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
				       	</shiro:hasPermission>
					</span>
					<span style="float:right;">
						<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
					</span>
				</div>
			</form>
		</div>
		<table id="${param.rel }_datagrid" toolbar="#${param.rel}_toolbar"></table>
	</div>
</div>
<script type="text/javascript">	
	$(function() {
		loadMain();			
	});
	
	function loadMain(){
		$('#<%=request.getParameter("rel")%>_datagrid').datagrid(
			{
				border:true,
				nowrap : false,
				url : "blacklist/query.do", 
				fitColumns: true,
				columns : [ [
			             {
					    	field:"ck",
					    	title : "勾选",
					    	checkbox:true
						 },
						 {
							field : "nickName",
							title : "用户姓名",
							width : 200,
							align : "center",
						},{
							field : "mobile",
							title : "手机号",
							width : 100,
							align : "center",
						},{
							field : "comName",
							title : "公司名称",
							width : 200,
							align : "center"
						},{
							field : "beginTime",
							title : "开始时间",
							width : 160,
							align : "center",
							formatter : function(value, row, index) {
								var v = "";
								if(value == null || value.length == 0){
									v = "";
								}else{
									v = new Date(value).format("yyyy-MM-dd HH:mm");
								}
								return v;
							}
						},{
							field : "endTime",
							title : "结束时间",
							width : 160,
							align : "center",
							formatter : function(value, row, index) {
								var v = "";
								if(value == null || value.length == 0){
									v = "";
								}else{
									v = new Date(value).format("yyyy-MM-dd HH:mm");
								}
								return v;
							}
						},
						{
							field : "managerUserName",
							title : "操作人",
							width : 120,
							align : "center",
						},
						{
							field : "managerTime",
							title : "操作时间",
							width : 160,
							align : "center",
							formatter : function(value, row, index) {
								var v = "";
								if(value == null || value.length == 0){
									v = "";
								}else{
									v = new Date(value).format("yyyy-MM-dd HH:mm");
								}
								return v;
							}
						}
				] ],
				onClickRow:function(rowIndex, rowData){
					//给下面的控件datagrid赋值
					$('#${param.rel}_datagrid').datagrid("clearChecked");
					$("#center").find("tr[datagrid-row-index="+rowIndex+"]").find(":checkbox").click();
				}
			});
	}
</script>