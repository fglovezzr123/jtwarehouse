<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>

<div id="cc" class="easyui-layout" data-options="fit:true">
	<div id='center' data-options="region:'center',title:'',split:true"
		style="" border="false">
		<div id="${param.rel}_toolbar" class="search-div">
			<form
				onsubmit="return datagridSearch(this,'${param.rel }_datagrid');"
				formatFilterData="true">
				<div class="search-content">
					<span> <label style="width: 100px;">类别名称：</label> <input
						type="text" name="name" class="span2" />
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> <shiro:hasPermission
							name="libraryclass:add">
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="libraryclass/classaddPage/levelone.do?rel=${param.rel }" title="新增"
								target="dialog" width="800" height="400" rel="${param.rel}_add"><span>新增</span>
							</a>
						</shiro:hasPermission> <shiro:hasPermission name="libraryclass:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true"
								title="修改"
								href="libraryclass/classupdatePage/levelone.do?id={id}&rel=${param.rel}"
								target="dialog" width="800" height="400"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span> </a>
						</shiro:hasPermission>
					</span> <span style="float:right">
						<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
					</span>

				</div>
			</form>
		</div>
		<table id="${param.rel }_datagrid" toolbar="#${param.rel}_toolbar"></table>
	</div>

</div>


<script type="text/javascript">	
	$(function() {
		loadMain();			
	});
	
	function loadMain(){
		$('#<%=request.getParameter("rel")%>_datagrid').datagrid({
			url : "libraryclass/query/classquery.do",
			queryParams: {
				level: 1
			},
			columns : [ [

			{
				field : "id",
				title : "ID",
				width : $(this).width() * 0.1,
				align : "center"
			}, {
				field : "name",
				title : "分类名称",
				width : $(this).width() * 0.15,
				align : "center",
			}, {
				field : "recommond",
				title : "推荐",
				width : $(this).width() * 0.15,
				align : "center"
			}, {
				field : "sort",
				title : "顺序",
				width : $(this).width() * 0.15,
				align : "center"
			}

			] ],

		});
	}
//-->
</script>














