<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
	
<div class="easyui-layout" data-options="fit:true"> 
	<div data-options="region:'center',title:'',split:true" border="false">	
		<div class="easyui-layout" data-options="fit:true"> 
			<div id='center' data-options="region:'center',title:'',split:true" border="false">
				<div id="${param.rel}_bannerLookUp_toolbar" class="search-div">
					<form onsubmit="return datagridSearch(this,'${param.rel}_bwclsydjtz_datagrid');" formatFilterData="true">
						<input id="${param.rel}_bwclsydjtz_id" type="hidden" />
						<input id="${param.rel}_bwclsydjtz_boolean" type="hidden" value="1"/>
						<div class="search-toolbar">
							<span style="float: right">
								
							</span>
						</div>
					</form>
				</div>
				<table id="${param.rel}_bannerLookUp_datagrid" toolbar="#${param.rel}_bannerLookUp_toolbar"></table>	
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">	
	$(function() {
		loadMain();			
	});
	
	function loadMain(){
		$('#<%=request.getParameter("rel")%>_bannerLookUp_datagrid').datagrid(
			{
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
	
	function showviewb(fid){
		MUI.openDialog('查看详情','banner/updatePage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:800,height:400});
	}
</script>














