<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
<div id="cc" class="easyui-layout" data-options="fit:true">  
    <div id='center' data-options="region:'center',title:'',split:true" style="" border="false">
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">任务体系：</label>
						<input	type="text" name="taskSystem" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">积分：</label>
						<input	type="text" name="integralTotal" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">经验值：</label>
						<input	type="text" name="empiricalTotal" class="span2"/>
					</span>
				</div>
				<div class="search-toolbar">
					<span style="float: left;"> 
					 <shiro:hasPermission name="userIntegral:add">
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="userIntegral/addPage.do?rel=${param.rel }" title="新增" target="dialog"
								width="1000" height="500" rel="${param.rel}_add"><span>新增</span>
							</a>
					</shiro:hasPermission> 
					<shiro:hasPermission name="userIntegral:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="userIntegral/updatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="1000" height="500"
								rel="${param.rel}_update" warn="请先选择一条信息"><span>修改</span>
							</a>
					</shiro:hasPermission> 
					<shiro:hasPermission name="userIntegral:delete">
							<a class="easyui-linkbutton" icon="icon-remove" plain="true" 
								href="userIntegral/del.do?rel=${param.rel }" target="selectedTodo"
								rel="ids" title="确定要删除吗?" warn="至少选择一条信息"><span>删除</span>
							</a>
					</shiro:hasPermission> 
						
						<%--	<a class="easyui-linkbutton"  icon="icon-search"	plain="true" 
							title="高级搜索" href="pm/xianchang/HntShikuai/searchTag.do?rel=${param.rel}" target="dialog"  width="650" height="350"  rel="${param.rel}_queryfilter"  >高级查询</a>
								--%>
						 </span> 
		
					<span style="float: right">
					    <a class="easyui-linkbutton" icon="icon-add" plain="true"
						 		href="userIntegralEmpirical/addPage.do?rel=${param.rel }" title="积分规则" target="dialog"
								width="1000" height="500" rel="${param.rel}_add"><span>积分规则</span>
						</a>
					    <a class="easyui-linkbutton" icon="icon-add" plain="true"
						 		href="userIntegralEmpirical/addPage3.do?rel=${param.rel }" title="每天积分经验值上下限配制" target="dialog"
								width="1000" height="500" rel="${param.rel}_add"><span>积分经验值上下限</span>
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
				url : "userIntegral/query.do", 
				columns : [ [
						{
							field : "ck",
							title : "勾选",
							width : 10,
							checkbox : true
						},
						{
							field : "taskStatus",
							title : "埋点类型",
							width : $(this).width() * 0.15,
							align : "center",
							formatter: function(value,row,index){
								
								if('1'==value){
									return '积分等级';
								}else if('2'==value){
									return '用户勋章';
								}
							}
			
						},
						{
							field : "code",
							title : "任务编码",
							width : $(this).width() * 0.10,
							align : "center"
						},
						{
							field : "taskSystem",
							title : "任务体系",
							width : $(this).width() * 0.25,
							align : "center"
			
						},
						{
							field : "empiricalTotal",
							title : "获得经验",
							width : $(this).width() * 0.10,
							align : "center"
			
						},
						{
							field : "integralTotal",
							title : "获得积分",
							width : $(this).width() * 0.10,
							align : "center"
			
						},
						{
							field : "integralExplain",
							title : "说明",
							width : $(this).width() * 0.25,
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
					//MUI.openDialog('修改','userIntegral/updatePage.do?id='+rowData.id+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:1000,height:500});
				},
				onSelect : function(index, row){
					pid = row.id;
				}
			});
	}
	
	
//-->
</script>