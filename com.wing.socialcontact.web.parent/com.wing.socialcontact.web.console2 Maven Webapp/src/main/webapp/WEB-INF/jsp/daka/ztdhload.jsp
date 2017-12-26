<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">用户姓名：</label>
						<input	type="text" name="nickname" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">职位：</label>
						<select name="job"  style="width: 120px;">
						<option value="">全部</option>
						<c:forEach var="c" items="${jobs}">
						<option  value="${c.id}">${c.listValue}</option>
						</c:forEach>	
					</select>
					</span>
					<span>
						<label style="width: 100px;">行业：</label>
						<select name="industry"  style="width: 120px;">
						<option value="">全部</option>
						<c:forEach var="c" items="${industrys}">
						<option  value="${c.id}">${c.listValue}</option>
						</c:forEach>
						</select>
					</span>
					<span>
						<label style="width: 100px;">公司名称：</label>
						<input	type="text" name="comname" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">地区：</label>
						<input	type="text" name="place" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">等级：</label>
						<select name="level"  style="width: 120px;">
							<option value="">全部</option>
							<c:forEach var="c" items="${userEmpirical}">
							<option  value="${c.level}">${c.level}</option>
							</c:forEach>
						</select>
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
						<shiro:hasPermission name="daka:add">
							<a class="easyui-linkbutton" icon="icon-add" plain="true" onclick="addDk()" ><span>新增</span>
							</a>
						</shiro:hasPermission> 
						<shiro:hasPermission name="daka:delete">
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="daka/ztdhdel.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
				       </shiro:hasPermission>
						</span> 
		
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="submit" id="main_subimt">查询</button>&nbsp;
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
		$('#<%=request.getParameter("rel")%>_datagrid').datagrid({
				url : "daka/ztdhquery.do?isztdh=1", 
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						 {
								field : "nickname",
								title : "用户姓名",
								width : $(this).width() * 0.1,
								align : "center"
							},
						 {
								field : "com_name",
								title : "公司名称",
								width : $(this).width() * 0.1,
								align : "center",
						},
						{
							field : "job_name",
							title : "职位",
							width : $(this).width() * 0.08,
							align : "center"
						},
						{
							field : "industry_name",
							title : "行业",
							width : $(this).width() * 0.08,
							align : "center"
						},
						{
							field : "region",
							title : "地区",
							width : $(this).width() * 0.08,
							align : "center"
						},
						
						{
							field : "mobile",
							title : "电话",
							width : $(this).width() * 0.07,
							align : "center"
						},
						{
							field : "level",
							title : "等级",
							width : $(this).width() * 0.04,
							align : "center"
						},
						{
							field : "ztdhsort",
							title : "权重",
							width : $(this).width() * 0.04,
							align : "center"
						},
						{
							field : "integral",
							title : "积分",
							width : $(this).width() * 0.04,
							align : "center"
						},
						{
							field : "op",
							title : "操作",
							width : $(this).width() * 0.08,
							align : "center",
							formatter: function(rowIndex, rowData){
								return  '<a href = "javaScript:void(0)" onclick = updatesort("'+rowData.id+'")>权重设置</a>|<a href="userhonor/user_view.do?id='+rowData.id+'&rel=<%=request.getParameter("rel")%>" target="dialog" width="1000" height="550"  rel="<%=request.getParameter("rel")%>_show"  title="详情" >查看</a>';
							}
						}
						
				] ],
				
			});
	}
	
	//编辑
	function addDk(){
		var params = { closed: false,cache: false,modal:true,width:1000,height:600,collapsible:false,minimizable:false,maximizable:false};
		MUI.openDialog('新增智同道合','daka/ztdhaddPage.do?rel=userList',"ztdhaddpage",params);
	}
	
	//权重
	function updatesort(fid){
		MUI.openDialog('权重设置','daka/ztdhsortPage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:650,height:300});
	}
	<%-- //权重
	function updatesort(fid){
		MUI.openDialog('权重设置','daka/sortPage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:650,height:300});
	} --%>
</script>














