<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
  String bp = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"; 
%>  				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">标题：</label>
						<input	type="text" name="newsTitle" class="span2"/>
					</span>
					<span><label style="width: 100px;">创建时间：</label>
					<input type="text" name="startTimef"    id="stime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'etime\')}'})"   style="width:120px;"  />  
						至
					<input type="text" name="endTimef"     id="etime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'stime\')}'})"   style="width:120px;" />
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
						<shiro:hasPermission name="bossnews:add">
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="news/addPageBoss.do?rel=${param.rel }" title="新增" target="dialog"
								width="800" height="350" rel="${param.rel}_add"><span>新增</span>
							</a>
						</shiro:hasPermission> 
						<shiro:hasPermission name="bossnews:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="news/updatePageBoss.do?id={id}&rel=${param.rel}"
								target="dialog" width="800" height="350"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="bossnews:delete">
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="news/delBoss.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
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
				url : "news/queryBoss.do", 
				fitColumns: false,
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						{
							field : "newsTitle",
							title : "文档标题",
							width : 350,
							align : "center"
						},
						{
							field : "sort",
							title : "序号",
							width : 100,
							align : "center",
						},{
							field : "charge",
							title : "收费",
							width : 150,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.isFree==2){
									str = "否";
								}else{
									str = rowData.charge+"J币";
								}
								return  str;
							}
						},
						{
							field : "createTime",
							title : "创建时间",
							width :150,
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
								return  "<a href = 'javaScript:void(0)' onclick = showviewh('"+rowData.id+"')>编辑</a>&nbsp;&nbsp;"
								+"<a href = 'javaScript:void(0)' onclick = deleteboss('"+rowData.id+"')>删除</a>";
							}
						}
						
				] ],
				
			});
	}
	
	function showviewh(fid){
		MUI.openDialog('编辑','news/updatePageBoss.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:800,height:350});
	}
	//删除
	function deleteboss(fid){
		Msg.confirm('提示',"确定要删除吗？<br/>", function(r){
	          if (r){
	        	  var pm = {"ids":fid};
					var dg = $('#<%=request.getParameter("rel")%>_datagrid');
					selectedTodo_doPost("news/delBoss.do",pm,dg,'');
	          }
	     });
	}
</script>














