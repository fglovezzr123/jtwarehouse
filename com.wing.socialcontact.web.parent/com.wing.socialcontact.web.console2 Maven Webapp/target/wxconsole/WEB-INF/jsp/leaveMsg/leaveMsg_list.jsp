<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
	
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">反馈人：</label>
						<input	type="text" name="nickname" class="span2"/>
					</span>
				
				</div>

				<div class="search-toolbar">
					 <span style="float: left;"> 
					 <shiro:hasPermission name="leaveMsg:delete">
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="leaveMsg/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
						   </shiro:hasPermission>
						</span> 
			
					 
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="submit" id="main_subimt">查询</button>&nbsp;
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
				border:true,
				nowrap : false,
				url : "leaveMsg/query.do", 
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						{
							field : "nickname",
							title : "反馈人",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "mobile",
							title : "手机号",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "source",
							title : "来源",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								if(value == 1){
									return "微信企业号";
								}else if(value == 2){
									return "企服云app";
								}else{
									return "未知";
								}
							}
						},
						{
							field : "type",
							title : "反馈类型",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								if(value == 1){
									return "im";
								}else if(value ==2){
									return "个人中心";
								}else if(value == 3){
									return "企服云app";
								}else{
									return "未知";
								}
							}
						},{
							field : "content",
							title : "反馈内容",
							width : $(this).width() * 0.35,
							align : "center",
						},
						{
							field : "create_time",
							title : "反馈时间",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm");
							}
						}
						
				] ],
				
			});
	}
	
	function showviewb(fid){
		
	}
</script>














