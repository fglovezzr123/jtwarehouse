<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
  String bp = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"; 
%>  				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">标题：</label>
						<input	type="text" name="newsTitle" class="span2"/>
					</span>
					<span><label style="width: 100px;">创建时间：</label>
					<input type="text" name="startTimef"    id="stime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'etime\')}'})"   style="width:120px;"  />  
						至
					<input type="text" name="endTimef"     id="etime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'stime\')}'})"   style="width:120px;" />
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
						<shiro:hasPermission name="newsclass:add">
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="news/hotaddPage.do?rel=${param.rel }" title="新增" target="dialog"
								width="800" height="350" rel="${param.rel}_add"><span>新增</span>
							</a>
						</shiro:hasPermission> 
						<shiro:hasPermission name="newsclass:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="news/hotupdatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="800" height="350"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="newsclass:delete">
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="news/hotdel.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
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
				url : "news/hotquery.do", 
				fitColumns: false,
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						{
							field : "newsTitle",
							title : "标题",
							width : 350,
							align : "center"
						},
						{
							field : "sort",
							title : "排序号",
							width : 100,
							align : "center",
						},{
							field : "url",
							title : "标题链接",
							width : 350,
							align : "center",
							formatter: function(rowIndex, rowData){
								return  "<%=bp%>front/m/news/hotDetailPage.do?id="+rowData.id;
							}
						},
						{
							field : "createTime",
							title : "创建时间",
							width :130,
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
							width :100,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = showviewh('"+rowData.id+"')>查看</a>&nbsp;&nbsp;<a href = 'javaScript:void(0)' onclick = yulan('"+rowData.id+"')>预览</a>";
							}
						}
						
				] ],
				
			});
	}
	
	function showviewh(fid){
		MUI.openDialog('查看热推详情','news/hotupdatePage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:800,height:350});
	}
	function yulan(fid){
		MUI.openDialog('查看热推详情','news/hotviewPage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:350,height:650});
	}
</script>














