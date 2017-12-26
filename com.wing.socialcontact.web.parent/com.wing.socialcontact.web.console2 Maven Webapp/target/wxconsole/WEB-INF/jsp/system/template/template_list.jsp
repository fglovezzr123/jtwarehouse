<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
<div id="cc" class="easyui-layout" data-options="fit:true">  
    <div id='center' data-options="region:'center',title:'',split:true" style="" border="false">
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" formatFilterData="true">
				<div class="search-toolbar">
				
					<span style="float: left;"> 
					<%-- 	<shiro:hasPermission name="template:add"> --%>	
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="template/addPage.do?rel=${param.rel }" title="新增" target="dialog"
								width="1000" height="500" rel="${param.rel}_add"><span>新增</span>
							</a>
						<%-- </shiro:hasPermission> 
						<shiro:hasPermission name="template:update"> --%>	
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="template/updatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="1000" height="500"
								rel="${param.rel}_update" warn="请先选择一条信息"><span>修改</span>
							</a>
						<%-- </shiro:hasPermission> 	
						 <shiro:hasPermission name="template:delete">--%>
							<a class="easyui-linkbutton" icon="icon-remove" plain="true" children="${param.rel }0_datagrid"
								href="template/del.do?rel=${param.rel }" target="selectedTodo"
								rel="ids" title="确定要删除吗?" warn="至少选择一条信息"><span>删除</span>
							</a>
					 	<%-- </shiro:hasPermission> --%>
					 	<div  id="tijiaodiv0" style="display: inline;">
							<a class="easyui-linkbutton"  icon="icon-remove"	plain="true"
							   	 href="template/isOpen.do" target="selectedTodo"  title="请谨慎操作！确定要‘启用’该数据?" warn="至少选择一条记录">启用</a>
						</div>
						
					 	<div  id="tijiaodiv1" style="display: inline;">
							<a class="easyui-linkbutton"  icon="icon-remove"	plain="true"
							   	 href="template/isNotOpen.do" target="selectedTodo"  title="请谨慎操作！确定要‘禁用’该数据?" warn="至少选择一条记录">禁用</a>
						</div>  	
						
						<%--	<a class="easyui-linkbutton"  icon="icon-search"	plain="true" 
							title="高级搜索" href="pm/xianchang/HntShikuai/searchTag.do?rel=${param.rel}" target="dialog"  width="650" height="350"  rel="${param.rel}_queryfilter"  >高级查询</a>
								--%>
						 </span> 
		
					<span style="float: right">
						<%--	期间：
						<input type="text" name="o.lengthName" placeholder="例:2014年01月第01周" style="width: 140px" op="like"/> &nbsp;<i class="boot_icon-question-sign" selectLike="tooltip"/>
						
						<button class="btn btn-primary btn-small" type="submit">
							查询
						</button>&nbsp;
						<button class="btn btn-small clear" type="button">
							清空
						</button>&nbsp;  --%>
					
					</span>
		
				</div>
			</form>
		</div>
		<table id="${param.rel }_datagrid" toolbar="#${param.rel}_toolbar"></table>
	</div>
	
	
</div>
				

<script type="text/javascript">	
	var isopen = ""; 
	$(function() {
		loadMain();			
	});
	
	function loadMain(){
		$('#<%=request.getParameter("rel")%>_datagrid').datagrid(
			{
				url : "template/query.do", 
				columns : [ [
						{
							field : "ck",
							title : "勾选",
							width : 10,
							checkbox : true
						},
						{
							field : "mark",
							title : "模板标识",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "title",
							title : "模板标题",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "type",
							title : "消息类型",
							width : $(this).width() * 0.15,
							align : "center",
							formatter: function(value,row,index){
								
								if(1==value){
									return '短信';
								}else if(2==value){
									return '微信';
								}
							}
			
						},
						{
							field : "isOpen",
							title : "禁用",
							width : $(this).width() * 0.15,
							align : "center",
							formatter: function(value,row,index){
								
								if(1==value){
									return '<span class="label label-success">禁用</span>';
								}else{
									return '<span class="label">可用</span>';
								}
							}
			
						},
						{
							field : "createName",
							title : "编制人",
							width : $(this).width() * 0.1,
							align : "center"
						},
						{
							field : "createTime",
							title : "编制时间",
							width : $(this).width() * 0.1,
							align : "center",
							formatter: function(value,row,index){
								return new Date(value).format("yyyy-MM-dd");
							}
						}
				] ],
				onDblClickRow:function(rowIndex, rowData){
					MUI.openDialog('修改','template/updatePage.do?id='+rowData.id+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:1000,height:500});
				},
				onSelect : function(index, row){
					pid = row.id;
					isopen = row.isOpen
					setlinkbutton2_();
				}
			});
	}
	
	function setlinkbutton2_(){
        
		if(isopen==0){
			document.getElementById("tijiaodiv0").style.display="none";
			document.getElementById("tijiaodiv1").style.display="inline";
		}else{
			document.getElementById("tijiaodiv0").style.display="inline";
			document.getElementById("tijiaodiv1").style.display="none";
		}
	
	}
	
//-->
</script>