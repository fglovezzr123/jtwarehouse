<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">标题名称：</label>
						<input	type="text" name="topicDesc" class="span2"/>
					</span>
					<span><label style="width: 100px;">创建时间：</label>
					<input type="text" name="startTime" id="stime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'etime\')}'})"  style="width:120px;"  />  
						至
					<input type="text" name="endTime"   id="etime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'stime\')}'})"  style="width:120px;" />
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
					 <shiro:hasPermission name="newsclass:add">
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="topic/addPageAd.do?rel=${param.rel }" title="新增" target="dialog"
								width="650" height="450" rel="${param.rel}_add"><span>发布</span>
							</a>
						</shiro:hasPermission>  	
						<%-- <shiro:hasPermission name="newsclass:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="topic/updatePageAd.do?id={id}&rel=${param.rel}"
								target="dialog" width="650" height="450"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
						</shiro:hasPermission> --%>
						<shiro:hasPermission name="newsclass:delete">
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="topic/delAd.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
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
				url : "topic/queryAd.do", 
				fitColumns: false,
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						 {
								field : "topicDesc",
								title : "标题",
								width : 300,
								align : "center",
						},
						{
							field : "userName",
							title : "发布人",
							width : 100,
							align : "center",
							formatter: function(rowIndex, rowData){
								return rowData.userName;
							}
						},{
							field : "createUserName",
							title : "发布人电话",
							width : 100,
							align : "center"
						},
						{
							field : "url",
							title : "标题链接",
							width : 250,
							align : "center"
						},
						{
							field : "createTime",
							title : "发布时间",
							width : 150,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd");
							}
						},
						{
							field : "op",
							title : "操作",
							align:"center",
							width : 150,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = showviewt('"+rowData.id+"')>编辑</a>";
							}
						}
						
				] ],
				
			});
	}
	
	function showviewt(fid){
		MUI.openDialog('编辑话题','topic/updatePageAd.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:650,height:450});
	}
</script>














