<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
<div id="cc" class="easyui-layout" data-options="fit:true">  
    <div id='center' data-options="region:'center',title:'',split:true" style="" border="false">
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				 <div class="search-content">
					<span>
						<label style="width: 100px;">等级：</label>
						<input	type="text" name="level" class="span2"/>
					</span>
				</div> 
				<div class="search-toolbar">
					<span style="float: left;"> 
					<shiro:hasPermission name="userEmpirical:add">
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="userEmpirical/addPage.do?rel=${param.rel }" title="新增" target="dialog"
								width="1000" height="500" rel="${param.rel}_add"><span>新增</span>
							</a>
						</shiro:hasPermission> 
					<shiro:hasPermission name="userEmpirical:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="userEmpirical/updatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="1000" height="500"
								rel="${param.rel}_update" warn="请先选择一条信息"><span>修改</span>
							</a>
						</shiro:hasPermission> 
					<shiro:hasPermission name="userEmpirical:delete">
							<a class="easyui-linkbutton" icon="icon-remove" plain="true" 
								href="userEmpirical/del.do?rel=${param.rel }" target="selectedTodo"
								rel="ids" title="确定要删除吗?" warn="至少选择一条信息"><span>删除</span>
							</a>
						</shiro:hasPermission> 
					 	
						
						<%--	<a class="easyui-linkbutton"  icon="icon-search"	plain="true" 
							title="高级搜索" href="pm/xianchang/HntShikuai/searchTag.do?rel=${param.rel}" target="dialog"  width="650" height="350"  rel="${param.rel}_queryfilter"  >高级查询</a>
								--%>
						 </span> 
		
					<span style="float: right">
					    <a class="easyui-linkbutton" icon="icon-add" plain="true"
						 		href="userIntegralEmpirical/addPage2.do?rel=${param.rel }" title="等级规则" target="dialog"
								width="1000" height="500" rel="${param.rel}_add"><span>等级规则</span>
						</a>
							&nbsp;
						 <button class="btn btn-primary btn-small" type="submit">
							查询
						</button>&nbsp;
						<button class="btn btn-small clear" type="button">
							清空
						</button>&nbsp;  
					</span>
		
				</div>
			</form>
		</div>
		<table id="${param.rel }_datagrid" toolbar="#${param.rel}_toolbar"></table>
	</div>
	
	
</div>
				

<script type="text/javascript">	
	var isopen = ""; 
	$(function() {
		loadMain();			
	});
	
	function loadMain(){
		$('#<%=request.getParameter("rel")%>_datagrid').datagrid(
			{
				url : "userEmpirical/query.do", 
				columns : [ [
						{
							field : "ck",
							title : "勾选",
							width : 10,
							checkbox : true
						},
						{
							field : "level",
							title : "等级",
							width : $(this).width() * 0.05,
							align : "center"
						},
						{
							field : "empiricalLow",
							title : "等级经验",
							width : $(this).width() * 0.25,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								str = rowData.empiricalLow+"~"+rowData.empiricalHigh;
								return str;
							}
			
						},
						{
							field : "integralTotal",
							title : " 赠送积分",
							width : $(this).width() * 0.10,
							align : "center"
			
						},
						{
							field : "updateTime",
							title : "修改时间",
							width : $(this).width() * 0.15,
							align : "center",
							formatter: function(value,row,index){
								return new Date(value).format("yyyy-MM-dd HH:mm");
							}
						}
				] ],
				onDblClickRow:function(rowIndex, rowData){
					//MUI.openDialog('修改','userEmpirical/updatePage.do?id='+rowData.id+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:1000,height:500});
				},
				onSelect : function(index, row){
					pid = row.id;
				}
			});
	}
	
	
//-->
</script>