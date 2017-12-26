<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%--
	模块：系统管理--组织机构 -- 部门管理--查找带回(单选)
--%>

<div class="easyui-tabs" fit="true" border="false" >
	<div title="资讯" style="padding:2px; ">
		<div id="${param.rel}_toolbar4" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_news');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">资讯名称：</label>
						<input	type="text" name="newsTitle" class="span2"/>
						
					</span>
				</div>
				<div class="search-toolbar">
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
					</span>
		
				</div>
			</form>
		</div>
		<table id="${param.rel }_news" toolbar="#${param.rel}_toolbar4"></table>
	</div>
</div>
<script type="text/javascript">
<!--
	$(function(){
		loadNews();
	});
	
	function loadNews(){

		$('#<%=request.getParameter("rel")%>_news').datagrid(
			{
				url : "news/query.do", 
				fitColumns: false,
				columns : [ [
				             
				             
						 {
							field : "选择",
							title : "选择",
							align : "center",
							width : $(this).width() * 0.05,
							formatter : function(value, row, index) {
								return "<a href='javascript:;' onclick=$.bringBack({id:'" + row.id + "',name:'" + row.newsTitle + "'})>选择</a>";
							}
						},{
								field : "newsTitle",
								title : "资讯标题",
								width : 300,
								align : "center",
						},{
							field : "lookCount",
							title : "阅读人数",
							width :100,
							align : "center",
						},{
							field : "count",
							title : "留言人数",
							width : 100,
							align : "center",
						},{
							field : "sort",
							title : "权重",
							width : $(this).width() * 0.1,
							align : "center"
						},
						{
							field : "createTime",
							title : "发布时间",
							width : 100,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd");
							}
						},
						{
							field : "updateTime",
							title : "更新时间",
							width : 100,
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
							width :80,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = showviewn('"+rowData.id+"')>查看</a>";
							}
						}
						
				] ],
				
			});
	}
//-->
</script>
