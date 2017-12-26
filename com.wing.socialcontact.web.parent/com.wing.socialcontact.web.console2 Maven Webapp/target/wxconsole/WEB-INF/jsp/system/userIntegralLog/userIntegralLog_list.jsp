<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
<div id="cc" class="easyui-layout" data-options="fit:true">  
    <div id='center' data-options="region:'center',title:'',split:true" style="" border="false">
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">用户姓名：</label>
						<input	type="text" name="nickname" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">注册手机号：</label>
						<input	type="text" name="mobile" class="span2"/>
					</span>
					<!-- <span>
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
					</span> -->
				</div>
				<div class="search-toolbar">
					<span style="float: left;"> 
					<shiro:hasPermission name="userIntegralLog:add">
					     <a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="userIntegralLog/addPage2.do?rel=${param.rel }" title="添加积分" target="dialog"
								width="800" height="600" rel="${param.rel}_add"><span>添加积分</span>
						</a>
					</shiro:hasPermission> 
							<!-- <a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="userIntegralLog/addPage.do?rel=${param.rel }" title="新增" target="dialog"
								width="1000" height="500" rel="${param.rel}_add"><span>新增</span>
							</a>
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="userIntegralLog/updatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="1000" height="500"
								rel="${param.rel}_update" warn="请先选择一条信息"><span>修改</span>
							</a>
							<a class="easyui-linkbutton" icon="icon-remove" plain="true" 
								href="userIntegralLog/del.do?rel=${param.rel }" target="selectedTodo"
								rel="ids" title="确定要删除吗?" warn="至少选择一条信息"><span>删除</span>
							</a> -->
					 	
						
						<%--	<a class="easyui-linkbutton"  icon="icon-search"	plain="true" 
							title="高级搜索" href="pm/xianchang/HntShikuai/searchTag.do?rel=${param.rel}" target="dialog"  width="650" height="350"  rel="${param.rel}_queryfilter"  >高级查询</a>
								--%>
						 </span> 
		
					<span style="float: right">
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
				url : "userIntegralLog/query.do", 
				frozenColumns:[[
					             {
								    	field:"ck",
								    	title : "勾选",
								    	checkbox:true
								 },
								 {
										field : "nickname",
										title : "用户姓名",
										width : 80,
										align : "center"
								 },
								 {
										field : "mobile",
										title : "注册电话",
										width : 100,
										align : "center"
								 },
								 {
										field : "com_name",
										title : "公司名称",
										width : 120,
										align : "center",
								},
								{
									field : "job_name",
									title : "职位",
									width : 80,
									align : "center"
								}  
				            ]],  
				columns : [ [
						{
							field : "province_name",
							title : "地区",
							width : 180,
							align : "center",
							formatter: function(value,row,index){
								if(row.province_name!=null&&row.city_name!=null&&row.county_name!=null){
									return row.province_name+row.city_name+row.county_name;
								}else if(row.province_name!=null&&row.city_name!=null){
									return row.province_name+row.city_name;
								}else if(row.province_name!=null){
									return row.province_name;
								}
							}
						},
						
					
						{
							field : "remark",
							title : "操作明细",
							width : $(this).width() * 0.25,
							align : "center"
			
						},
						{
							field : "integral",
							title : "积分",
							width : $(this).width() * 0.10,
							align : "center",
							formatter : function(value, row, index) {
								if(row.integral_type == 1){
									return "+"+value;
								}else if(row.integral_type == 2){
									return "-"+value;
								}else{
									return "+"+value;
								}
							}
			
						},
						{
							field : "ye_integral",
							title : "积分总量",
							width : $(this).width() * 0.10,
							align : "center"
						},
						{
							field : "create_time",
							title : "创建时间",
							width : $(this).width() * 0.15,
							align : "center",
							formatter: function(value,row,index){
								if(null==value){
									return "";
								}else{
									return new Date(value).format("yyyy-MM-dd HH:mm");
								}
								
							}
						}
				] ],
				onDblClickRow:function(rowIndex, rowData){
					//MUI.openDialog('修改','userIntegralLog/updatePage.do?id='+rowData.id+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:1000,height:500});
				},
				onSelect : function(index, row){
					pid = row.id;
				}
			});
	}
	
	
//-->
</script>