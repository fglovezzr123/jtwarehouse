<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%--
	模块：系统管理--组织机构 -- 用户管理--查找带回(多选)
--%>
<div id="${param.rel}_toolbar" class="search-div">
	<form  onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
		
	<div class="search-content">
					<span>
						<label style="width: 100px;">勋章名称：</label>
						<input	type="text" name="title" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">勋章类型：</label>
						<select name="hornorType"  style="width: 120px;">
					    	<option  value="" >全部</option>
								<option  value="1">认证勋章</option>
								<option  value="2">互助宝勋章</option>
								<option  value="3">平台活动勋章</option>
						</select>
					</span>
					<!-- <span><label style="width: 100px;">发布时间：</label>
					<input type="text" name="startTimef"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;"  />  
						至
					<input type="text" name="endTimef"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;" />
					</span> -->
					
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
	loadMain();			
});

function loadMain(){
	$('#<%=request.getParameter("rel")%>_datagrid').datagrid(
		{
			border:true,
			nowrap : false,
			url : "honor/query.do", 
			columns : [ [
		             {
					    	field:"ck",
					    	title : "勾选",
					    	checkbox:true
					 },
					 {
							field : "hornor_type",
							title : "勋章类型",
							width : $(this).width() * 0.15,
							align : "center",
							formatter: function(value,row,index){
								
								if('1'==value){
									return '认证勋章';
								}else if('2'==value){
									return '互助宝勋章';
								}else if('3'==value){
									return '平台活动勋章';
								}
							}
			
						},
					{
						field : "honor_code",
						title : "勋章编码",
						width : $(this).width() * 0.15,
						align : "center",
					},
					{
						field : "title",
						title : "勋章名称",
						width : $(this).width() * 0.15,
						align : "center",
						formatter: function(value,row,index){
							
							return  "<a href='javascript:;' onclick=$.bringBack({id:'"+row.id+"',title:'"+row.title+"'})>"+value+"</a>";
							
						}
					},
					
					{
						field : "order_num",
						title : "排序",
						width : $(this).width() * 0.15,
						align : "center",
					},
					{
						field : "create_time",
						title : "发布时间",
						width : $(this).width() * 0.15,
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
						width : $(this).width() * 0.15,
						formatter: function(rowIndex, rowData){
							return  "<a href = 'javaScript:void(0)' onclick = showviewb('"+rowData.id+"')>查看</a>";
						}
					}
					
			] ],
			
		});
}

function showviewb(fid){
	MUI.openDialog('查看详情','honor/updatePage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:800,height:400});
}

//-->		
</script>
	

