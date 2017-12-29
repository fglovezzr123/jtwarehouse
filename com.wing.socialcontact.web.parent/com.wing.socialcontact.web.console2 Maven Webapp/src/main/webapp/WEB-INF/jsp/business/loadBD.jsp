<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">合作标题：</label>
						<input	type="text" name="titles" class="span2"/>
					</span>
					<span><label style="width: 100px;">商洽状态：</label>
					<select name="isShow"  style="width: 120px;">
			    	<option  value="0" >全部</option>
						<option  value="1">显示</option>
						<option  value="2">不显示</option>
					</select>
					</span>
					<span><label style="width: 100px;">创建时间：</label>
					<input type="text" name="startTimef" id="stime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'etime\')}'})"  style="width:120px;"  />  
						至
					<input type="text" name="endTimef" id="etime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'stime\')}'})" style="width:120px;" />
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
						<shiro:hasPermission name="business:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="business/updatePageDB.do?id={id}&rel=${param.rel}"
								target="dialog" width="650" height="450"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="business:delete">
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="business/delDB.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
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
				url : "business/queryBD.do", 
				fitColumns: false,
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						 {
								field : "titles",
								title : "合作标题",
								width :200,
								align : "center",
						},
						{
							field : "fbUserName",
							title : "发布人",
							width : 120,
							align : "center",
						},
						{
							field : "fbMobile",
							title : "发布人电话",
							width : 120,
							align : "center",
						},
						 {
								field : "content",
								title : "商洽内容",
								width : 300,
								align : "center",
						},
						{
							field : "userName",
							title : "商洽人",
							width : 100,
							align : "center",
						},{
							field : "mobile",
							title : "商洽人电话",
							width : 100,
							align : "center",
						},
						{
							field : "isShow",
							title : "商洽状态",
							width :100,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.isShow==1){
									str = "显示";
								}else if(rowData.isShow==2){
									str = "不显示";
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
										.format("yyyy-MM-dd HH:mm");
							}
						},
						{
							field : "op",
							title : "操作",
							align:"center",
							width :100,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = showviewbd('"+rowData.id+"')>编辑</a>";
							}
						}
						
				] ],
				
			});
	}
	
	function showviewbd(fid){
		MUI.openDialog('编辑','business/updatePageDB.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:650,height:450});
	}
</script>














