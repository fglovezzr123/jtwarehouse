<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">PK话题：</label>
						<input	type="text" name="topicDesc" class="span2"/>
					</span>
					<span><label style="width: 100px;">话题状态：</label>
					<select name="status"  style="width: 120px;">
			    	<option  value="0" >全部</option>
						<option  value="1">显示</option>
						<option  value="2">不显示</option>
					</select>
					</span>
					<span><label style="width: 100px;">是否推荐：</label>
					<select name="isRecommend"  style="width: 120px;">
			    	<option  value="0" >全部</option>
						<option  value="1">推荐</option>
						<option  value="2">不推荐</option>
					</select>
					</span>
					<span><label style="width: 120px;">是否允许评论：</label>
					<select name="allowComment"  style="width: 120px;">
			    	<option  value="0" >全部</option>
						<option  value="1">允许</option>
						<option  value="2">不允许</option>
					</select>
					</span>
					<span><label style="width: 100px;">创建时间：</label>
					<input type="text" name="startTime"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;"  />  
						至
					<input type="text" name="endTime"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;" />
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
					 <shiro:hasPermission name="newsclass:add">
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="topic/addPage.do?rel=${param.rel }" title="新增" target="dialog"
								width="650" height="450" rel="${param.rel}_add"><span>发布</span>
							</a>
						</shiro:hasPermission>  	
						<shiro:hasPermission name="newsclass:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="topic/updatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="650" height="450"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="newsclass:delete">
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="topic/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
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
				url : "topic/query.do", 
				fitColumns: false,
				columns : [ [
						 {
							field : "选择",
							title : "选择",
							align : "center",
							width : $(this).width() * 0.05,
							formatter : function(value, row, index) {
								return "<a href='javascript:;' onclick=$.bringBack({id:'" + row.id + "',name:'" + row.topicDesc + "'})>选择</a>";
							}
						 },
						 {
							field : "topicDesc",
							title : "PK话题",
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
						},
						{
							field : "sort",
							title : "权重",
							width : 100,
							align : "center"
						},
						{
							field : "createUserName",
							title : "发布人电话",
							width : 100,
							align : "center"
						},
						{
							field : "bluePoint",
							title : "蓝方观点",
							width : 160,
							align : "center"
						},{
							field : "redPoint",
							title : "红方观点",
							width : 160,
							align : "center"
						},{
							field : "status",
							title : "话题状态",
							width : 60,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.status==1){
									str = "显示";
								}else if(rowData.status==2){
									str = "不显示";
								}
								return str;
							}
						},{
							field : "allowComment",
							title : "是否允许评论",
							width : 80,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.allowComment==1){
									str = "允许";
								}else if(rowData.allowComment==2){
									str = "不允许";
								}
								return str;
							}
						},{
							field : "isRecommend",
							title : "是否推荐",
							width : 60,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.isRecommend==1){
									str = "推荐";
								}else if(rowData.isRecommend==2){
									str = "不推荐";
								}
								return str;
							}
						},
						{
							field : "createTime",
							title : "发布时间",
							width : 80,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd");
							}
						}
						
				] ],
				
			});
	}
	
	function showviewt(fid){
		MUI.openDialog('查看详情','topic/updatePage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:650,height:450});
	}
</script>
