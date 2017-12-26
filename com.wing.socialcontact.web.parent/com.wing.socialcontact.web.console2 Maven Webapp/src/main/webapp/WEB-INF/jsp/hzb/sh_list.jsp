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
						<input type="text" name="keyword" class="span2" style="width: 200px;" placeholder="用户姓名/公司名称/手机号"/>
					</span>
					<span>
						<label style="width: 60px;">类型：</label>
						<select name="logType" class="span2">
							<option></option>
							<option value="1">开通订单</option>
							<option value="2">充值</option>
						</select>
					</span>
					<span>
						<label style="width: 80px;">审核状态：</label>
						<select name="shStatus" class="span2">
							<option></option>
							<option value="0">待审核</option>
							<option value="1">审核通过</option>
							<option value="2">驳回</option>
						</select>
					</span>
				</div>
				<div class="search-toolbar">
					<span style="float:left;">
						<a class="easyui-linkbutton clearDgChecked" icon="icon-redo" plain="true" title="清空所有勾选项" datagrid="${param.rel }_datagrid">清空勾选</a>
						<a class="easyui-linkbutton" icon="icon-ok" plain="true" href="hzb/sh_1.do?rel=${param.rel }" target="selectedTodo" rel="${param.rel }_sh_1" warn="请先选择一条记录" datagrid="${param.rel }_datagrid">审核通过</a>
						<a class="easyui-linkbutton" icon="icon-cancel" plain="true" href="hzb/sh_2.do?rel=${param.rel }" target="selectedTodo" rel="${param.rel }_sh_2" warn="请先选择一条记录" datagrid="${param.rel }_datagrid">驳回</a>
					</span>
					<span style="float:right;">
						<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
					</span>
				</div>
			</form>
		</div>
		<table id="${param.rel }_datagrid" toolbar="#${param.rel}_toolbar"></table>	
	</div>
</div>
<script type="text/javascript">	
	$(function() {
		loadMain();			
	});
	
	function loadMain(){
		$('#<%=request.getParameter("rel")%>_datagrid').datagrid({
				border:true,
				nowrap : false,
				url : "hzb/sh/query.do", 
				fitColumns: false,
				frozenColumns:[[
	                {
				    	field:"chk",
				    	title : "勾选",
				    	checkbox:true
				    },
				    {
						field : "nickName",
						title : "用户名称",
						width : 100,
						align : "center"
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
						width : 140,
						align : "center"
					}
				]],
				columns:[[
					{
						field : "logType",
						title : "记录类型",
						width : 100,
						align : "center",
						formatter: function(value,row,index){
							var v = "";
							if(value == 1){
								v = "开通订单";
							}else{
								v = "充值";
							}
							return v;
						}
					},
					{
						field : "fkMoney",
						title : "支付金额",
						width : 100,
						align : "center",
						formatter: function(value,row,index){
							return number_format(value,0);
						}
					},
					{
						field : "fkTime",
						title : "付款时间",
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
						field : "fkPz",
						title : "付款凭证",
						width : 120,
						align:"center",
						formatter: function(value,row,index){
							if(row.fkType == 1){
								return "/";
							}else{
								//'<a href="ims/pm/general/tixing/view.do?rel=${param.rel }&id=' + row.id + '" rel="${param.rel }_tixing_view" title="日常提醒-查看" target="dialog" width="1000" height="500">' + value + '</a>'
								return '<a href="javascript:viewPz(\''+value+'\');">点击查看付款凭证</a>';
							}
						}
					},
					{
						field : "shStatus",
						title : "审核状态",
						width : 100,
						align:"center",
						formatter: function(value,row,index){
							var v = "";
							if(row.fkType == 1){
								v = "/";
							}else{
								if(value == 0){
									v = "待审核";
								}else if(value == 1){
									v = "审核通过";
								}else if(value == 2){
									v = "驳回";
								}
							}
							return v;
						}
					},
					{
						field : "shUserName",
						title : "审核人",
						width : 100,
						align:"center",
						formatter: function(value,row,index){
							var v = "";
							if(row.fkType == 1){
								v = "/";
							}else{
								v = value;
							}
							return v;
						}
					},
					{
						field : "shTime",
						title : "审核时间",
						width : 120,
						align:"center",
						formatter: function(value,row,index){
							var v = "";
							if(row.fkType == 1){
								v = "/";
							}else{
								if(value == null || value.length == 0){
									v = "";
								}else{
									v = new Date(value).format("yyyy-MM-dd HH:mm");
								}
							}
							return v;
						}
					}
	            ]],
				onClickRow:function(rowIndex, rowData){
					$('#${param.rel}_datagrid').datagrid("clearChecked");
					$("#center").find("tr[datagrid-row-index="+rowIndex+"]").find(":checkbox").click();
					if(rowData.shStatus == 1 || rowData.shStatus == 2){
						$("#center").find("a[icon='icon-ok'],a[icon='icon-cancel']").hide();
					}else{
						$("#center").find("a[icon='icon-ok'],a[icon='icon-cancel']").show();
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
					if(rowData.shStatus == 1 || rowData.shStatus == 2){
						$("#center").find("a[icon='icon-ok'],a[icon='icon-cancel']").hide();
					}else{
						$("#center").find("a[icon='icon-ok'],a[icon='icon-cancel']").show();
					}
				}
			});
	}
	function viewPz(url){
		var url = "${ossurl}"+url;
		Msg.alert("查看付款凭证",'<img src="'+url+'"/>');
	}
</script>














