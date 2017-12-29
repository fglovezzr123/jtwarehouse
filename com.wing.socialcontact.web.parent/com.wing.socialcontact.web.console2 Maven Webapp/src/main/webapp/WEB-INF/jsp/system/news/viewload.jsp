<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
			  <div class="search-content">
					<span>
						<label style="width: 100px;">专访人：</label>
						<input	type="text" name="viewUser" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">专访标题：</label>
						<input	type="text" name="newsTitle" class="span2"/>
					</span>
					<span><label style="width: 100px;">创建时间：</label>
					<input type="text" name="startTime" id="stime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'etime\')}'})"  style="width:120px;"  />  
						至
					<input type="text" name="endTime" id="etime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'stime\')}'})" style="width:120px;" />
					</span>
				</div>
				<div class="search-toolbar">
					<span style="float: left;"> 
						<shiro:hasPermission name="newsclass:add">
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="news/addPageView.do?rel=${param.rel }" title="新增" target="dialog"
								width="800" height="400" rel="${param.rel}_add"><span>新增</span>
							</a>
						</shiro:hasPermission> 
						<shiro:hasPermission name="newsclass:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="news/updatePageview.do?id={id}&rel=${param.rel}"
								target="dialog" width="800" height="400"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="newsclass:delete">
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="news/delview.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
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
				url : "news/queryview.do", 
				fitColumns: false,
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },{
								field : "viewUser",
								title : "专访人",
								width : 180,
								align : "center",
						},{
								field : "newsTitle",
								title : "专访标题",
								width : 300,
								align : "center",
						},{
							field : "source",
							title : "来源",
							width : 150,
							align : "center"
						},{
							field : "sort",
							title : "权重",
							width : $(this).width() * 0.1,
							align : "center"
						},{
							field : "isRecommend",
							title : "是否推荐",
							width : 100,
							align : "center",
							formatter: function(rowIndex, rowData){
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
							field : "createTime",
							title : "创建时间",
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
							width : 100,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = showviewv('"+rowData.id+"')>编辑</a>|<a href = 'javaScript:void(0)' onclick = showviewzhuanfang('"+rowData.id+"')>查看</a>|<a href = 'javaScript:void(0)' onclick = yulan('"+rowData.id+"')>预览</a>";
							}
						}
						
				] ],
				
			});
	}
	
	function showviewv(fid){
		MUI.openDialog('编辑','news/updatePageview.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:800,height:400});
	}
	function showviewzhuanfang(fid){
		MUI.openDialog('查看详情','news/viewPageview.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_show',{width:800,height:400});
	}
	function yulan(fid){
		MUI.openDialog('预览','news/viewyulan.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_show',{width:350,height:650});
	}
</script>














