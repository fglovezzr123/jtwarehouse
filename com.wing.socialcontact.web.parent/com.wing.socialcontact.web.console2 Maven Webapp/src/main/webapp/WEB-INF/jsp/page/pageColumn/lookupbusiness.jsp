<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">合作标题：</label>
						<input	type="text" name="titles" class="span2"/>
					</span>
					<span><label style="width: 100px;">合作类别：</label>
					<select name="bizType"  id="bizType" style="width: 120px;">
					    <option  value="0" >全部</option>
						<c:forEach var="c" items="${list}">
						<option  value="${c.id}">${c.className}</option>
						</c:forEach>	
					</select>
					</span>
					<span><label style="width: 100px;">合作状态：</label>
					<select name="status"  style="width: 120px;">
			    	<option  value="0" >全部</option>
						<option  value="1">审核中</option>
						<option  value="2">审核成功</option>
						<option  value="3">审核失败</option>
					</select>
					</span>
					<span><label style="width: 100px;">创建时间：</label>
					<input type="text" name="startTimef"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;"  />  
						至
					<input type="text" name="endTimef"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;" />
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
						<shiro:hasPermission name="business:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="business/updatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="650" height="550"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="business:delete">
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="business/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
				       </shiro:hasPermission>
				      <shiro:hasPermission name="business:update">
					   <a  class="easyui-linkbutton" plain="true"
						href="business/updateStatus.do" target="selectedTodo"  title="确定该操作吗?" warn="至少勾选一条记录">审核失败</a>
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
				url : "business/query.do", 
				fitColumns: false,
				columns : [ [
			             {
							field : "选择",
							title : "选择",
							align : "center",
							width : $(this).width() * 0.05,
							formatter : function(value, row, index) {
								return "<a href='javascript:;' onclick=$.bringBack({id:'" + row.id + "',name:'" + row.titles + "'})>选择</a>";
							}
						},
						 {
								field : "className",
								title : "合作类别",
								width : 80,
								align : "center",
						},
						{
							field : "userName",
							title : "发布人",
							width : 100,
							align : "center",
						},
						{
							field : "createUserName",
							title : "发布人电话",
							width : 100,
							align : "center",
						},
						 {
								field : "titles",
								title : "合作标题",
								width : 150,
								align : "center",
						},
						{
							field : "reward",
							title : "悬赏J币",
							width : 80,
							align : "center"
						},
						{
							field : "bluePoint",
							title : "合作有效期",
							width : 180,
							align : "center",
							formatter : function(rowIndex, rowData) {
								return new Date(rowData.startTime)
										.format("yyyy-MM-dd")+"至"+new Date(rowData.endTime)
										.format("yyyy-MM-dd");
							}
						},{
							field : "status",
							title : "合作状态",
							width : 80,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.status==1){
									str = "审核中";
								}else if(rowData.status==2){
									str = "审核成功";
								}else if(rowData.status==3){
									str = "审核失败";
								}
								return str;
							}
						},{
							field : "isRecommend",
							title : "推荐",
							width : 80,
							align : "center",
							formatter : function(rowIndex, rowData)  {
								var str = "";
								if(rowData.isRecommend==1){
									str = "推荐";
								}else if(rowData.isRecommend==2){
									str = "不推荐";
								}
								return str;
							}
						},{
							field : "appealType",
							title : "诉求分类",
							width : 60,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.appealType==1){
									str = "供给";
								}else if(rowData.appealType==2){
									str = "需求";
								}
								return str;
							}
						},
						{
							field : "createTime",
							title : "创建时间",
							width : 130,
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
							width : 80,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = showviewbusiness('"+rowData.id+"')>查看</a>";
							}
						}
						
				] ],
				
			});
	}
	
	function showviewbusiness(fid){
		MUI.openDialog('查看','business/viewPage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_show',{width:800,height:550});
	}
</script>