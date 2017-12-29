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
						<select name="level" class="span2">
							<option></option>
							<option value="1">黄金级</option>
							<option value="2">白金级</option>
							<option value="3">钻石级</option>
						</select>
					</span>
					<span>
						<label style="width: 60px;">状态：</label>
						<select name="status" class="span2">
							<option></option>
							<option value="1">待支付</option>
							<option value="2">待支付尾款</option>
							<option value="3">已付尾款</option>
							<option value="4">已过期</option>
						</select>
					</span>
					<span>
						<label style="width: 100px;">待付款金额：</label>
						<input type="text" name="dfklow" class="span2" placeholder="下限"  maxlength="10" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"/>
						<input type="text" name="dfkhigh" class="span2" placeholder="上限" maxlength="10" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"/>
					</span>
					<span>
						<label style="width: 100px;">申请开通时间：</label>
						<input type="text"   name="stime"  id="stime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'etime\')}'})"  style="width: 150px;"/>至
						<input type="text"   name="etime"  id="etime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'stime\')}'})"  style="width: 150px;"/>
					</span>
					
				</div>
			
				<div class="search-toolbar">
					<span style="float:left;">
						<a class="easyui-linkbutton clearDgChecked" icon="icon-redo" plain="true" title="清空所有勾选项" datagrid="${param.rel }_datagrid">清空勾选</a>
						<a class="easyui-linkbutton" icon="icon-ok" plain="true" href="javascript:open_hzb();">开通互助宝</a>
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
		    <div id="hzbPayLog" title="付款记录表" style="overflow:hidden;width: auto;height:auto;">
		    	<div id="hzbPayLog_toolbar" class="search-div">
					<form onsubmit="return datagridSearch(this,'hzbPayLog_datagrid');"  formatFilterData="true">
						<div class="search-toolbar">
							<span style="float:left;">
								<a class="easyui-linkbutton clearDgChecked" icon="icon-redo" plain="true" title="清空所有勾选项" datagrid="hzbPayLog_datagrid">清空勾选</a>
								<a class="easyui-linkbutton" icon="icon-ok" plain="true" href="hzb/sh_1.do?rel=hzbPayLog" target="selectedTodo" rel="hzbPayLog_sh_1" warn="请先选择一条记录" datagrid="hzbPayLog_datagrid">审核通过</a>
								<a class="easyui-linkbutton" icon="icon-cancel" plain="true" href="hzb/sh_2.do?rel=hzbPayLog" target="selectedTodo" rel="hzbPayLog_sh_2" warn="请先选择一条记录" datagrid="hzbPayLog_datagrid">驳回</a>
							</span>
							<span style="float:right;display: none;">
								<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
								<button class="btn btn-small clear" type="button">清空</button>&nbsp;
							</span>
						</div>
					</form>
				</div>
				<table id="hzbPayLog_datagrid" toolbar="#hzbPayLog_toolbar"></table>
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
				url : "hzb/order/query.do", 
				fitColumns: false,
				frozenColumns:[[
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
						field : "comName",
						title : "公司名称",
						width : 200,
						align : "center"
					},
					{
						field : "mobile",
						title : "手机号",
						width : 100,
						align : "center"
					},
					{
						field : "level",
						title : "开通等级",
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
						field : "zje",
						title : "总金额",
						width : 80,
						align : "center",
						formatter : function(value, row, index) {
							var v = "";
							if(row.level == 1){
								v = 200000;
							}else if(row.level == 2){
								v = 500000;
							}else{
								v = 1000000;
							}
							return number_format(v,0);
						}
					}
                ]],
				columns : [ [
					{
						field : "yfk",
						title : "已付款金额",
						width : 100,
						align : "center",
						formatter : function(value, row, index) {
							return number_format(value,0);
						}
					},
					{
						field : "dfk",
						title : "待付款金额",
						width : 100,
						align : "center",
						formatter : function(value, row, index) {
							var v = "";
							if(row.level == 1){
								v = 200000 - row.yfk;
							}else if(row.level == 2){
								v = 500000 - row.yfk;
							}else{
								v = 1000000 - row.yfk;
							}
							return number_format(v,0);
						}
					},
					{
						field : "createTime",
						title : "申请开通时间",
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
						field : "status",
						title : "状态",
						width : 100,
						align : "center",
						formatter : function(value, row, index) {
							var v = "";
							if(value == 1){
								v = "待支付";
							}else if(value == 2){
								v = "待支付尾款";
							}else{
								v = "已付尾款";
							}
							return v;
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
					if(rowData.hzbOpenFlag != 0){
						$("#center").find("a[icon='icon-ok']").hide();
					}else{
						$("#center").find("a[icon='icon-ok']").show();
					}
					load_hzbPayLog(orderId);
				},
				onBeforeLoad:function(rowIndex, rowData){
					$(".datagrid-header-check").html("");
				},
				onLoadSuccess: function(data) {
					$('#${param.rel}_datagrid').datagrid("clearChecked");
					//if(data.rows.length == 0){
					//$('#hzbPayLog_datagrid').datagrid('loadData',{total:0,rows:[]}); 
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
					if(rowData.hzbOpenFlag != 0){
						$("#center").find("a[icon='icon-ok']").hide();
					}else{
						$("#center").find("a[icon='icon-ok']").show();
					}
					var orderId=rowData.id;
					load_hzbPayLog(orderId);
				}
			});
	}
	
	var load_hzbPayLog=function(orderId){
		$('#hzbPayLog_datagrid').datagrid({
			url : "hzb/payLog/query.do?pid="+orderId,
			//singleSelect:true,
			//checkOnSelect:true,
			//selectOnCheck:true,
			columns:[[
	            {
			    	field:"chk",
			    	title : "勾选",
			    	checkbox:true
			    },
				{
					field : "fkType",
					title : "付款类型",
					width : 100,
					align : "center",
					formatter: function(value,row,index){
						var v = "";
						if(value == 1){
							v = "线上付款";
						}else{
							v = "线下付款";
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
					title : "线下付款凭证",
					width : 100,
					align:"center",
					formatter: function(value,row,index){
						if(row.fkType == 1){
							return "/";
						}else{
							//'<a href="ims/pm/general/tixing/view.do?rel=hzbPayLog&id=' + row.id + '" rel="hzbPayLog_tixing_view" title="日常提醒-查看" target="dialog" width="1000" height="500">' + value + '</a>'
							return '<a href="javascript:viewPz(\''+value+'\');">点击查看付款凭证</a>';
						}
					}
				},
				{
					field : "xsFkType",
					title : "线上支付方式",
					width : 100,
					align:"center",
					formatter: function(value,row,index){
						var v = "";
						if(row.fkType == 1){
							if(value == 1){
								v = "微信支付";
							}else if(value == 2){
								v = "财付通";
							}else{
								v = "/";
							}
						}else{
							v = "/";
						}
						return v;
					}
				},
				{
					field : "fkStatus",
					title : "线上支付状态",
					width : 100,
					align:"center",
					formatter: function(value,row,index){
						var v = "";
						if(row.fkType == 1){
							if(value == 0){
								v = "待支付";
							}else if(value == 1){
								v = "支付成功";
							}else if(value == 2){
								v = "支付失败";
							}
						}else{
							v = "/";
						}
						return v;
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
				$('#hzbPayLog_datagrid').datagrid("clearChecked");
				$("#hzbPayLog").find("tr[datagrid-row-index="+rowIndex+"]").find(":checkbox").click();
				if(rowData.fkType == 1){
					$("#hzbPayLog").find("a[icon='icon-ok'],a[icon='icon-cancel']").hide();
				}else{
					if(rowData.shStatus == 1 || rowData.shStatus == 2){
						$("#hzbPayLog").find("a[icon='icon-ok'],a[icon='icon-cancel']").hide();
					}else{
						$("#hzbPayLog").find("a[icon='icon-ok'],a[icon='icon-cancel']").show();
					}
				}
			},
			onBeforeLoad:function(rowIndex, rowData){
				$(".datagrid-header-check").html("");
			},
			onLoadSuccess: function(data) {
				$('#hzbPayLog_datagrid').datagrid("clearChecked");
				$('#hzbPayLog_datagrid').datagrid("clearSelections");
			},
			onCheck: function(rowIndex,rowData){
				$('#hzbPayLog_datagrid').datagrid('selectRow',rowIndex);
				$("#hzbPayLog").find(":checkbox").each(function(){
					var _tr=$(this).parent().parent().parent();
					if(_tr.attr("datagrid-row-index") != rowIndex){
						$('#hzbPayLog_datagrid').datagrid("uncheckRow", _tr.attr("datagrid-row-index"));
					}
				});
				if(rowData.shStatus == 1 || rowData.shStatus == 2){
					$("#hzbPayLog").find("a[icon='icon-ok'],a[icon='icon-cancel']").hide();
				}else{
					$("#hzbPayLog").find("a[icon='icon-ok'],a[icon='icon-cancel']").show();
				}
			}
		});
	};
	
	function showviewb(fid){
		MUI.openDialog('预览','pageConfig/view.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_view',{width:380,height:640});
	}
	
	function viewPz(url){
		var url = "${ossurl}"+url;
		Msg.alert("查看付款凭证",'<img src="'+url+'"/>');
	}
	
	function open_hzb(){
		var rows=$("#${param.rel }_datagrid").datagrid('getChecked');
		if(rows.length != 1){
			Msg.topCenter({
				title:"提示",
				msg:"请勾选一条数据!",
				msgType:"warning"
			});
		}else{
			Msg.confirm("提示","是否确认开启已勾选数据的互助宝?",function(r){
				if(r){
					var userId=rows[0]["userId"];
					$.ajax({
						url : "hzb/manager.do",
						data:{
							//id:rows[0]["id"],
							userId:userId,
							type:1
						},
						type : "post",
						dataType : "json",
						success : function(data){
							Msg.topCenter({
								title:"消息",
								msg:"开通成功",
								msgType:"success"
							});
							//刷新表格数据
							$("#${param.rel}_datagrid").datagrid("reload");
							//$datagridBox.datagrid("reload");
							//清空所有选中
							//$datagridBox.datagrid("clearChecked");
						}
					});
				}
			});
		}
	}
	
</script>