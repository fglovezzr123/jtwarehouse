<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%--
	模块：系统管理--组织机构 -- 用户管理--查找带回(多选)
--%>
<div id="${param.rel}_toolbar" class="search-div">
	<form  onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
		
		<div  class="search-content " >
			
			<span>
				<label>姓名：</label>
				<input	type="text" name="nickname" class="span2"  />
			</span>
			
			
		</div>
	

		<div class="search-toolbar" >
			<span style="float:left;">
				<a class="easyui-linkbutton clearDgChecked"  icon="icon-redo"	plain="true"  title="清空所有勾选项" >清空勾选</a>
				
				<a class="easyui-linkbutton"  icon="icon-add"	plain="true" warn="请先选择用户" 
				 href="javascript:;"  onclick="$.multLookup(this,true,'${param.rel }_datagrid')" >带回并覆盖</a>
				 
				 <a class="easyui-linkbutton"  icon="icon-add"	plain="true" warn="请先选择用户" 
				 href="javascript:;"  onclick="$.multLookup(this,false,'${param.rel }_datagrid')" >带回并追加</a>
				
			</span>
			
			
			<span style="float:right">
				
				<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
				<button class="btn btn-small clear" type="button" >清空</button>&nbsp;
			</span>
		
		</div>
	</form>
	
</div>

<table id="${param.rel }_datagrid"   toolbar="#${param.rel}_toolbar"  ></table>


<script type="text/javascript" >
<!--	

	$(function() {
		
		$('#<%=request.getParameter("rel")%>_datagrid').datagrid({

			url : "user/lookUp3.do",
			queryParams: {
				type: '<%=request.getParameter("type")%>',
				userId: '<%=request.getParameter("userId")%>'
				
			},
			mustParamNames:"deptId,type",
			frozenColumns:[[
					{
						field:"ck",
						title : "勾选",
						checkbox:true
					},
					 {
						field : "userName",
						title : "姓名",
						align:"center",
						width : 100,
						formatter: function(value,row,index){
							
							return  "<a href='javascript:;' onclick=$.bringBack({id:'"+row.id+"',userName:'"+row.userName+"',mobile:'"+row.mobile+"',trueName:'"+row.trueName+"'})>"+value+"</a>";
							
						}
					}
			]],
			columns : [ [ 
			    
				{
					field : "mobile",
					title : "电话",
					align:"center",
					width : 150
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
				},  
			 	 {
					field : "sex",
					title : "性别",
					align:"center",
					width : 40,
					formatter: function(value,row,index){
							if("1"==value){
								return "男";
							}else{
								return "女";
							}
							
					}
				},
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
					field : "address",
					title : "具体地址",
					width : 180,
					align : "center"
				}
			 	  
			
			] ]
		});
		
	});

//-->		
</script>
	

