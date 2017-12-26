<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%--
	模块：系统管理--组织机构 -- 部门管理--查找带回(单选)
--%>

<div class="easyui-tabs" fit="true" border="false" >
	<div   title="轮播图" 	style="padding:2px; ">
			<%-- <div id="${param.rel}_toolbar1" class="search-div">
				<form  onsubmit="return datagridSearch(this,'${param.rel }_banner');"  >
					<div  class="search-content " >
					</div>
					<div class="search-toolbar" >
						<span style="float:right">
							<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
							<button class="btn btn-small clear" type="button" >清空</button>&nbsp;
						</span>
					</div>
				</form>
			</div> --%>
			<table id="${param.rel }_banner"   toolbar="#${param.rel}_toolbar1" ></table>
	</div>
</div>
<script type="text/javascript">
<!--
	$(function(){
		loadbanner();
	});
	function loadbanner(){
		$('#<%=request.getParameter("rel")%>_banner').datagrid({
				border:true,
				nowrap : false,
				url : "banner/query.do", 
				fitColumns: false,
				singleSelect:true,
				checkOnSelect:true,
				selectOnCheck:true,
				columns : [ [
				        {
							field : "选择",
							title : "选择",
							align : "center",
							width : $(this).width() * 0.05,
							formatter : function(value, row, index) {
								return "<a href='javascript:;' onclick=$.bringBack({id:'" + row.id + "',name:'" + row.title + "'})>选择</a>";
							}
						},
						{
								field : "listValue",
								title : "栏目类别",
								width : 150,
								align : "center",
						},{
							field : "title",
							title : "幻灯片名称",
							width : 200,
							align : "center",
						},{
							field : "jumpUrl",
							title : "幻灯片链接",
							width : 200,
							align : "center",
						},{
							field : "orderNum",
							title : "排序",
							width : 80,
							align : "center",
						},
						{
							field : "createTime",
							title : "发布时间",
							width : 120,
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
							width : 80,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = showviewb('"+rowData.id+"')>查看</a>";
							}
						}
				] ],
			});
	}
//-->
</script>
