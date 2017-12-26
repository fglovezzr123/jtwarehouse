<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% 
	String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.getSession().setAttribute("path", path);
%>
	
<div id="cc" class="easyui-layout" data-options="fit:true">  
    <div id='center' data-options="region:'center',title:'',split:true" border="false">
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 60px;">关键字：</label>
						<input type="text" name="keyword" class="span2" placeholder="用户姓名/公司名称"/>
					</span>
					<span>
						<label style="width: 60px;">等级：</label>
						<select name="hzbLevel" class="span2">
							<option></option>
							<option value="1">黄金级</option>
							<option value="2">白金级</option>
							<option value="3">钻石级</option>
						</select>
					</span>
					<span>
						<label style="width: 60px;">审核人：</label>
						<input type="text" name="shUser" class="span2" placeholder="审核人"/>
					</span>
					<span>
						<label style="width: 60px;">状态：</label>
						<select name="hzbOpenFlag" class="span2">
							<option></option>
							<option value="1">已开通</option>
							<option value="2">已停用</option>
							<option value="3">已过期</option>
						</select>
					</span>
					<span>
						<label style="width: 100px;">互助宝余额：</label>
						<input type="text" name="amountlow" class="span2" placeholder="下限" maxlength="10" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"/>
						<input type="text" name="amounthigh" class="span2" placeholder="上限" maxlength="10" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"/>
					</span>
				</div>
			
				<div class="search-toolbar">
					<span style="float:left;">
						<a class="easyui-linkbutton clearDgChecked" icon="icon-redo" plain="true" title="清空所有勾选项" datagrid="${param.rel }_datagrid">清空勾选</a>
						<a class="easyui-linkbutton" icon="icon-add" plain="true" href="hzb/managerPage.do?id={id}&rel=${param.rel }" target="dialog"  width="700" height="400" rel="${param.rel}_manager" warn="请先选择一条记录" title="用户互助宝管理" datagrid="${param.rel}_datagrid">管理</a>
					</span>
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
					</span>
				</div>
			</form>
		</div>
		<table id="${param.rel }_datagrid" toolbar="#${param.rel}_toolbar"></table>	
	</div>
	<div id="south" data-options="region:'south',title:'',split:true" style="height:240px;" border="false">
    	<div id="tt" class="easyui-tabs" data-options="fit:true" style="width:auto;height:auto;" border="false">
		    <div id="hzbManagerLog" title="操作记录表" style="overflow:hidden;width: auto;height:auto;">
		    	<div id="hzbManagerLog_toolbar" class="search-div">
					<form onsubmit="return datagridSearch(this,'hzbManagerLog_datagrid');"  formatFilterData="true">
						<div class="search-toolbar" style="display: none;">
							<span style="float:left;">
								<a class="easyui-linkbutton clearDgChecked" icon="icon-redo" plain="true" title="清空所有勾选项" datagrid="hzbManagerLog_datagrid">清空勾选</a>
								<a class="easyui-linkbutton" icon="icon-ok" plain="true" href="hzb/sh_1.do?rel=hzbManagerLog" target="selectedTodo" rel="hzbManagerLog_sh_1" warn="请先选择一条记录" datagrid="hzbManagerLog_datagrid">审核通过</a>
								<a class="easyui-linkbutton" icon="icon-cancel" plain="true" href="hzb/sh_2.do?rel=hzbManagerLog" target="selectedTodo" rel="hzbManagerLog_sh_2" warn="请先选择一条记录" datagrid="hzbManagerLog_datagrid">驳回</a>
							</span>
							<span style="float:right;display: none;">
								<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
								<button class="btn btn-small clear" type="button">清空</button>&nbsp;
							</span>
						</div>
					</form>
				</div>
				<table id="hzbManagerLog_datagrid" toolbar="#hzbManagerLog_toolbar"></table>
		    </div>
	    </div>
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
				url : "hzb/user/query.do", 
				fitColumns: false,
				columns : [ [
		             {
					    	field:"ck",
					    	title : "勾选",
					    	checkbox:true
					 },
					 {
							field : "nickName",
							title : "用户姓名",
							width : 100,
							align : "center",
							formatter : function(value, row, index) {
								//可在这里做查看详情功能
								return value;
							}
					},
					{
						field : "mobile",
						title : "手机号",
						width : 100,
						align : "center"
					},
					{
						field : "comName",
						title : "公司名称",
						width : 200,
						align : "center"
					},
					{
						field : "region",
						title : "地区",
						width : 200,
						align : "center"
					},
					{
						field : "hzbLevel",
						title : "互助宝等级",
						width : 80,
						align : "center",
						formatter : function(value, row, index) {
							var v = "";
							if(value == 1){
								v = "黄金级";
							}else if(value == 2){
								v = "白金级";
							}else{
								v = "钻石级";
							}
							return v;
						}
					},
					{
						field : "ljcz",
						title : "累计充值",
						width : 100,
						align : "center",
						formatter : function(value, row, index) {
							return number_format(value,2);
						}
					},
					{
						field : "hzbAmount",
						title : "互助宝余额",
						width : 100,
						align : "center",
						formatter : function(value, row, index) {
							return number_format(value,2);
						}
					},
					{
						field : "hzbOpenTime",
						title : "开通时间",
						width : 120,
						align : "center",
						formatter : function(value, row, index) {
							var v = "";
							if(value == null || value.length == 0){
								v = "";
							}else{
								v = new Date(value).format("yyyy-MM-dd HH:mm");
							}
							return v;
						}
					},
					{
						field : "shUserName",
						title : "审核人",
						width : 120,
						align : "center",
						formatter : function(value, row, index) {
							return value;
						}
					},
					{
						field : "hzbOpenFlag",
						title : "互助宝状态",
						width : 100,
						align : "center",
						formatter : function(value, row, index) {
							var v = "";
							if(value == 0){
								v = "未开通";
							}else if(value == 1){
								v = "已开通";
							}else if(value == 2){
								v = "已停用";
							}else{
								v = "已过期";
							}
							return v;
						}
					}
				] ],
				onClickRow:function(rowIndex, rowData){
					//给下面的控件datagrid赋值
					var orderId=rowData.id;
					$('#${param.rel}_datagrid').datagrid("clearChecked");
					$("#center").find("tr[datagrid-row-index="+rowIndex+"]").find(":checkbox").click();
					load_hzbManagerLog(orderId);
				},
				onBeforeLoad:function(rowIndex, rowData){
					$(".datagrid-header-check").html("");
				},
				onLoadSuccess: function(data) {
					$('#${param.rel}_datagrid').datagrid("clearChecked");
					//if(data.rows.length == 0){
					//$('#hzbManagerLog_datagrid').datagrid('loadData',{total:0,rows:[]}); 
					//}
				},
				onCheck: function(rowIndex,rowData){
					$('#${param.rel}_datagrid').datagrid('selectRow',rowIndex);
					$("#center").find("tr").find(":checkbox").each(function(){
						var _tr=$(this).parent().parent().parent();
						if(_tr.attr("datagrid-row-index") != rowIndex){
							$('#${param.rel}_datagrid').datagrid("uncheckRow", _tr.attr("datagrid-row-index"));
						}
					});
					var orderId=rowData.id;
					load_hzbManagerLog(orderId);
				}
			});
	}
	
	var load_hzbManagerLog=function(orderId){
		$('#hzbManagerLog_datagrid').datagrid({
			url : "hzb/managerLog/query.do?pid="+orderId,
			columns:[[
				{
					field : "managerUserName",
					title : "操作人",
					width : 100,
					align : "center",
					formatter: function(value,row,index){
						var v = "";
						if(row.type != 7 && row.type != 8){
							v = value;
						}else{
							v = "本人";
						}
						if(isEmpty(v)){
							v = "系统";
						}
						return v;
					}
				},
				{
					field : "type",
					title : "操作项",
					width : 100,
					align : "center",
					formatter: function(value,row,index){
						var v = "";
						switch(value){
							case 1:
								v = "开通互助宝";
								break;
							case 2:
								v = "停用互助宝";
								break;
							case 3:
								v = "启用互助宝";
								break;
							case 4:
								v = "增加互助宝余额";
								break;
							case 5:
								v = "扣除互助宝余额";
								break;
							case 6:
								v = "调整互助宝等级";
								break;
							case 7:
								v = "客户充值";
								break;
							case 8:
								v = "客户消费";
								break;
							case 9:
								v = "订单退款";
								break;
						}
						return v;
					}
				},
				{
					field : "managerTime",
					title : "操作时间",
					width : 120,
					align:"center",
					formatter: function(value,row,index){
						var v = "";
						if(value == null || value.length == 0){
							v = "";
						}else{
							v = new Date(value).format("yyyy-MM-dd HH:mm");
						}
						return v;
					}
				},
				{
					field : "managerMoney",
					title : "余额变化",
					width : 120,
					align:"center",
					formatter: function(value,row,index){
						var v = "";
						if(row.type == 1 || row.type == 2 || row.type == 3 || row.type == 6){
							v = "/";
						}else{
							if(row.type == 4 || row.type == 7 || row.type == 9){
								v = "+" + number_format(value,2);
							}else{
								v = "-" + number_format(value,2);
							}
						}
						return v;
					}
				},
				{
					field : "currYe",
					title : "当前余额",
					width : 120,
					align:"center",
					formatter: function(value,row,index){
						var v = "";
						if(row.type == 1 || row.type == 2 || row.type == 3 || row.type == 6){
							v = "/";
						}else{
							v = number_format(value,2);
						}
						return v;
					}
				},
				{
					field : "remark",
					title : "备注",
					width : 300,
					align:"center",
					formatter: function(value,row,index){
						return value;
					}
				}
            ]],
		});
	};
	
	function showviewb(fid){
		MUI.openDialog('预览','pageConfig/view.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_view',{width:380,height:640});
	}
</script>