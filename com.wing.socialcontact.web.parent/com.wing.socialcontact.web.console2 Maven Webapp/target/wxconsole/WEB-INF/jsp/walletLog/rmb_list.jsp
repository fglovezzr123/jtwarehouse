<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
	
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">用户姓名：</label>
						<input	type="text" name="nickname" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">手机号：</label>
						<input	type="text" name="mobile" class="span2"/>
					</span>
					
				
				</div>

				<div class="search-toolbar">
				  <!--   <span style="float: left;"> 
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="walletLog/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
					</span>-->
					<span style="float: left;"> 
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="walletLog/addRmbPage.do?rel=${param.rel }" title="添加余额" target="dialog"
								width="800" height="600" rel="${param.rel}_add"><span>添加余额</span>
							</a>
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
				url : "walletLog/rmb_query.do", 
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						{
							field : "nickname",
							title : "用户姓名",
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
							field : "remark",
							title : "操作明细",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "amount",
							title : "余额变化",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								if(!isEmpty(row.pay_status)){
									if(row.pay_status == 1){
										if(row.pd_type == 1){
											return "+"+value;
										}else if(row.pd_type == 2){
											return "-"+value;
										}
									}else{
										//提现失败的需要显示余额变化
										if(row.business_type == 2){
											if(row.pd_type == 1){
												return "+"+value;
											}else if(row.pd_type == 2){
												return "-"+value;
											}
										}
										return "/";
									}
								}else{
									if(row.pd_type == 1){
										return "+"+value;
									}else if(row.pd_type == 2){
										return "-"+value;
									}
								}
							}
						},
						{
							field : "ye_amount",
							title : "余额",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								if(isEmpty(value)){
									return "/";
								}else{
									return value;
								}
							}
						},
						{
							field : "pay_status",
							title : "支付状态",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								if(isEmpty(value)){
									return "成功";
								}else{
									if(value == 1){
										return "成功";
									}else{
										return "失败";
									}
								}
							}
						},
						{
							field : "create_time",
							title : "操作时间",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
								.format("yyyy-MM-dd HH:mm:ss ");
							}
						}
				] ],
				
			});
	}
	
	function showviewb(fid){
		
	}
</script>














