<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
		
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
			<input id="${param.rel}_types" name="types" type="hidden" value="0"/>
			<input id="${param.rel}_typename" name="typename" type="hidden" value="0"/>
				<div class="search-content">
					<span>
						<label style="width: 100px;">资讯名称：</label>
						<input	type="text" name="newsTitle" class="span2"/>
						
					</span>
					<span><label style="width: 100px;">发布时间：</label>
					<input type="text" name="startTimef" id="stime"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'etime\')}'})"  style="width:120px;"  />  
						至
					<input type="text" name="endTimef"   id="etime"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'stime\')}'})"  style="width:120px;" />
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
						<shiro:hasPermission name="newsclass:add">
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="news/addPage.do?rel=${param.rel }&types={#${param.rel}_types}&typename={#${param.rel}_typename}" title="新增" target="dialog"
								width="800" height="400" rel="${param.rel}_add"><span>新增</span>
							</a>
						</shiro:hasPermission> 
						<shiro:hasPermission name="newsclass:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="news/updatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="800" height="400"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="newsclass:delete">
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="news/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
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
				url : "news/query.do", 
				fitColumns: false,
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						 {
								field : "newsTitle",
								title : "资讯标题",
								width : 300,
								align : "center",
						},{
							field : "lookCount",
							title : "阅读人数",
							width :100,
							align : "center",
						},{
							field : "count",
							title : "留言人数",
							width : 100,
							align : "center",
						},{
							field : "sort",
							title : "权重",
							width : 70,
							align : "center"
						},
						{
							field : "createTime",
							title : "发布时间",
							width : 130,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm:ss");
							}
						},
						{
							field : "updateTime",
							title : "创建时间",
							width : 130,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm:ss");
							}
						},
						{
							field : "op",
							title : "操作",
							align:"center",
							width :80,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = showviewn('"+rowData.id+"')>查看</a>|<a href = 'javaScript:void(0)' onclick = yulan('"+rowData.id+"')>预览</a>";
							}
						}
						
				] ],
				
			});
	}
	
	function showviewn(fid){
		MUI.openDialog('查看资讯详情','news/updatePage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:800,height:400});
	}
	function yulan(fid){
		MUI.openDialog('查看资讯详情','news/viewPage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:350,height:650});
	}
</script>














