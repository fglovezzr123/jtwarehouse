<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div id="cc" class="easyui-layout" data-options="fit:true">  
    <div id='center' data-options="region:'center',title:'',split:true" border="false">
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
					<span>
						<label style="width: 100px;">状态：</label>
						<select name="state"  style="width: 120px;">
						<option value="">全部</option>
						<option  value="0">待审核</option>
						<option  value="1">审核通过</option>
						<option  value="2">审核屏蔽</option>
					    </select>
					</span>
				
				</div>

				<div class="search-toolbar">
				     <div  id="tijiaodiv1" style="display: inline;">
							<a class="easyui-linkbutton" icon="icon-ok" plain="true"
							   	 href="walletTx/tongguo.do" target="selectedTodo"  title="请谨慎操作！确定要‘通过’该数据?" warn="至少选择一条记录">通过</a>
					</div> 
				     <div  id="tijiaodiv2" style="display: inline;">
							<a class="easyui-linkbutton" icon="icon-cancel" plain="true"
							   	 href="walletTx/pinbi.do" target="selectedTodo" title="请谨慎操作！确定要‘屏蔽’该数据?" warn="至少选择一条记录">屏蔽</a>
					</div> 
			
					 
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="submit" id="main_subimt">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
					</span>
		
				</div>
			</form>
		</div>
		<table id="${param.rel}_datagrid" toolbar="#${param.rel}_toolbar"></table>	
	</div>
</div>
<script type="text/javascript">	
	$(function() {
		loadMain();			
	});
	
	function loadMain(){
		$('#<%=request.getParameter("rel")%>_datagrid').datagrid(
			{
				border:true,
				nowrap : false,
				url : "walletTx/query.do", 
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
							field : "com_name",
							title : "公司名称",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "txje",
							title : "提现金额",
							width : $(this).width() * 0.15,
							align : "center",
						},
						//{
						//	field : "pay_status",
						//	title : "提现状态",
						//	width : $(this).width() * 0.15,
						//	align : "center",
						//	formatter : function(value, row, index) {
						//		if(row.state == 1){
						//			if(value == 1){
						//				return "成功";
						//			}else{
						//				return "失败";
						//			}
						//		}else{
						//			return "/";
						//		}
						//	}
						//},
						{
							field : "create_time",
							title : "提现时间",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
								.format("yyyy-MM-dd HH:mm:ss ");
							}
						},
						{
							field : "state",
							title : "审核状态",
							width:$(this).width() * 0.1,
							align : "center",
							formatter: function(value,row,index){
								if (value=='0') {
									value="待审核";
								}else if (value=='1') {
									value="审核通过";
								}else if (value=='2') {
									value="审核屏蔽";
								}else{
									value="";
								}
								return value;
							}
						}
				] ],
				onClickRow:function(rowIndex, rowData){
					$('#${param.rel}_datagrid').datagrid("clearChecked");
					$("#center").find("tr[datagrid-row-index="+rowIndex+"]").find(":checkbox").click();
					if(rowData.state == 1 || rowData.state == 2){
						$(".search-toolbar").find("a[icon='icon-ok'],a[icon='icon-cancel']").hide();
					}else{
						$(".search-toolbar").find("a[icon='icon-ok'],a[icon='icon-cancel']").show();
					}
				},
				onBeforeLoad:function(rowIndex, rowData){
					$(".datagrid-header-check").html("");
				},
				onCheck: function(rowIndex,rowData){
					$('#${param.rel}_datagrid').datagrid('selectRow',rowIndex);
					$("#center").find("tr").find(":checkbox").each(function(){
						var _tr=$(this).parent().parent().parent();
						if(_tr.attr("datagrid-row-index") != rowIndex){
							$('#${param.rel}_datagrid').datagrid("uncheckRow", _tr.attr("datagrid-row-index"));
						}
					});
					if(rowData.state == 1 || rowData.state == 2){
						$("#center").find("a[icon='icon-ok'],a[icon='icon-cancel']").hide();
					}else{
						$("#center").find("a[icon='icon-ok'],a[icon='icon-cancel']").show();
					}
				}
			});
	}
	
	function showviewb(fid){
		
	}
</script>














