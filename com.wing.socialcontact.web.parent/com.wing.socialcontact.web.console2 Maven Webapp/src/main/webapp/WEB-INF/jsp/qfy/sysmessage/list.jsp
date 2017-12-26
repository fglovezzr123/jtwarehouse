<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
	
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">消息内容：</label>
						<input	type="text" name="content" class="span2"/>
					</span>
					<!-- <span>
						<label style="width: 100px;">用户昵称：</label>
						<input	type="text" name="nickname" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">来源：</label>
						<select name="source">
							<option value=''>所有</option>
							<option value='1'>微信企业号</option>
							<option value='2'>企服云app</option>
						</select>
					</span>
					<span>
						<label style="width: 100px;">反馈类型：</label>
						<select name="type">
							<option value=''>所有</option>
							<option value='1'>im</option>
							<option value='2'>个人中心</option>
							<option value='3'>企服云app</option>
						</select>
					</span> -->
				
				</div>

				<div class="search-toolbar">
					 <!-- <span style="float: left;"> 
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="leaveMsg/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
						</span> --> 
						
						<shiro:hasPermission name="entryPrise:add">
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="qfy/sysmessage/addPage.do?rel=${param.rel }" title="新增" target="dialog"
								width="600" height="400" rel="${param.rel}_add"><span>新增</span>
							</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sysmessage:delete">
					   		<a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
							href="qfy/sysmessage/del.do" target="selectedTodo"  title="确定要删除吗?" warn="请至少选择一条记录">批量删除</a>
				       </shiro:hasPermission>
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="submit" id="main_subimt">查询</button>&nbsp;
<!-- 						<button class="btn btn-small clear" type="button">清空</button>&nbsp; -->
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
				url : "qfy/sysmessage/query.do", 
				columns : [ [
						{
							field:"ck",
							title : "勾选",
							checkbox:true
						},
						{
							field : "content",
							title : "消息内容",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "status",
							title : "发送状态",
							width : $(this).width() * 0.15,
							align : "center",
							formatter:function(value,row,index){
								switch (value) {
								case 0:
									return "已发送";
									break;
								default:
									return "未发送";
									break;
								}
							}
						},
						{
							field : "createTime",
							title : "添加时间",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm:ss");
							}
						}
						
				] ],
				
			});
	}
</script>














