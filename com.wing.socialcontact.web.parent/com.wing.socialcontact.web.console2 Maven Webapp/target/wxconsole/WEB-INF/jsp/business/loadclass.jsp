<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">合作类别：</label>
						<input	type="text" name="className" class="span2"/>
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
						<shiro:hasPermission name="newsclass:add">
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="business/addPageClass.do?rel=${param.rel }" title="新增" target="dialog"
								width="600" height="350" rel="${param.rel}_add"><span>新增</span>
							</a>
						</shiro:hasPermission> 
						<shiro:hasPermission name="newsclass:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="business/updatePageClass.do?id={id}&rel=${param.rel}"
								target="dialog" width="600" height="350"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
						</shiro:hasPermission>
						
						<%-- <shiro:hasPermission name="newsclass:delete">
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="business/delClass.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
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
				url : "business/queryClass.do", 
				fitColumns: false,
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						 {
								field : "classNum",
								title : "排序",
								width : 120,
								align : "center",
						},
						{
							field : "className",
							title : "合作类别",
							width : 300,
							align : "center"
						},{
							field : "isRecommend",
							title : "推荐",
							width : 150,
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
						},
						{
							field : "op",
							title : "操作",
							align:"center",
							width : 150,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = showviewc('"+rowData.id+"')>编辑</a>&nbsp;&nbsp;"
								+"<a href = 'javaScript:void(0)' onclick = deleteClass('"+rowData.id+"')>删除</a>"
								
							}
						}
						
				] ],
				
			});
	}
	
	function showviewc(fid){
		MUI.openDialog('编辑','business/updatePageClass.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:600,height:350});
	}
	//删除
	function deleteClass(fid){
		Msg.confirm('提示',"确定要删除吗？<br/>", function(r){
	          if (r){
	        	  var pm = {"id":fid};
					var dg = $('#<%=request.getParameter("rel")%>_datagrid');
					selectedTodo_doPost("business/delClass.do",pm,dg,'');
	          }
	     });
	}
</script>














