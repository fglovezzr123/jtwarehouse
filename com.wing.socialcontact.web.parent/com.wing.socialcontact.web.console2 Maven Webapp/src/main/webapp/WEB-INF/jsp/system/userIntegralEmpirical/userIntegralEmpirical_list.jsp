<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
<div id="cc" class="easyui-layout" data-options="fit:true">  
    <div id='center' data-options="region:'center',title:'',split:true" style="" border="false">
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" formatFilterData="true">
				<div class="search-toolbar">
				
					<span style="float: left;"> 
					<%-- 	<shiro:hasPermission name="messageBulk:add"> --%>	
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="messagebulk/addPage.do?rel=${param.rel }" title="新增" target="dialog"
								width="1000" height="500" rel="${param.rel}_add"><span>发布推送消息</span>
							</a>
					 	
						
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
				url : "messagebulk/query.do", 
				columns : [ [
						{
							field : "ck",
							title : "勾选",
							width : 10,
							checkbox : true
						},
						{
							field : "msgType",
							title : "消息类型",
							width : $(this).width() * 0.15,
							align : "center",
							formatter: function(value,row,index){
								
								if('1'==value){
									return '短信';
								}else if('2'==value){
									return '微信';
								}else if('3'==value){
									return '系统消息';
								}
							}
			
						},
						{
							field : "content",
							title : "消息内容",
							width : $(this).width() * 0.35,
							align : "center"
			
						},
						{
							field : "sendType",
							title : "推送用户",
							width : $(this).width() * 0.15,
							align : "center",
							formatter: function(value,row,index){
								
								if('0'==value){
									return '全部用户';
								}else if('1'==value){
									return '认证用户';
								}else if('2'==value){
									return '注册用户';
								}else if('3'==value){
									return '企服云用户';
								}
							}
			
						},
						{
							field : "createTime",
							title : "发布时间",
							width : $(this).width() * 0.1,
							align : "center",
							formatter: function(value,row,index){
								return new Date(value).format("yyyy-MM-dd HH:mm");
							}
						}
				] ],
				onDblClickRow:function(rowIndex, rowData){
					//MUI.openDialog('修改','messagebulk/updatePage.do?id='+rowData.id+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:1000,height:500});
				},
				onSelect : function(index, row){
					pid = row.id;
				}
			});
	}
	
	
//-->
</script>