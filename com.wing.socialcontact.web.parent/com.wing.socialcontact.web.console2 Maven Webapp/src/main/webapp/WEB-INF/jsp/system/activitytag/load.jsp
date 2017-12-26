<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">标签名称：</label>
						<input	type="text" name="name" class="span2"/>
					</span>
					<span>
					<label style="width: 100px;">活动分类：</label>
					<select name="classId">
						<option value="">活动分类</option>
						<option value="1">以玩会友</option>
						<option value="2">以书会友</option>
					</select>
				</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
						<shiro:hasPermission name="activitytag:add">
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="activitytag/addPage.do?rel=${param.rel }" title="新增" target="dialog"
								width="800" height="600" rel="${param.rel}_add"><span>新增</span>
							</a>
						</shiro:hasPermission> 
						<shiro:hasPermission name="activitytag:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="activitytag/updatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="800" height="600"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
						</shiro:hasPermission>
						<%-- <shiro:hasPermission name="activitytag:delete">
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="activitytag/del.do" target="selectedTodo"  title="确定要删除吗?" warn="请至少选择一条记录">批量删除</a>
				       </shiro:hasPermission> --%>
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
				url : "activitytag/query.do", 
				columns : [ [
						/* {
							field:"ck",
							title : "勾选",
							checkbox:true
						},   */ 
						{
								field : "classId",
								title : "活动类别",
								width : $(this).width() * 0.15,
								align : "center",
								formatter : function(value, row, index) {
									
									switch(value){
									 case  '1' :
									 		return "以玩会友";
									 case  '2' :
									 		return "以书会友";
									}
								}
						},
						{
							field : "name",
							title : "活动标签",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "recommend",
							title : "推荐",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								
								switch(value){
								 case  0 :
								 		return "否";
								 case  1 :
								 		return "是";
								}
							}
						},
						{
							field : "sort",
							title : "序号",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "createTime",
							title : "添加时间",
							width : $(this).width() * 0.15,
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
							width : $(this).width() * 0.15,
							formatter: function(rowIndex, rowData){
								/* return  "<a href = 'javaScript:void(0)' onclick = updateview('"+rowData.id+"')>编辑</a>"; */
								return  "<a href = 'javaScript:void(0)' onclick = updateview('"+rowData.id+"')>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href = 'javaScript:void(0)' onclick = deleteactivitytag('"+rowData.id+"')>删除</a>"; 
							}
						}
						
				] ],
				
			});
	}
	
	function updateview(fid){
		MUI.openDialog('编辑活动标签','activitytag/updatePage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:800,height:600});
	}
	function deleteactivitytag(id){
		Msg.confirm('提示',"确定要删除吗？如果标签下存在活动，禁止删除！", function(r){
	          if (r){
				var pm = {"id":id};
				var dg = $('#<%=request.getParameter("rel")%>_datagrid');
				selectedTodo_doPost("activitytag/del.do",pm,dg,'');
	          }
	     });
	}
</script>














