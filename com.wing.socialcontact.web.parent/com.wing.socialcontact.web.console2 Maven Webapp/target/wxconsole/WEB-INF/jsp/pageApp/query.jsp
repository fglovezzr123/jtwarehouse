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
						<label style="width: 100px;">页面名称：</label>
						<input type="text" name="pageName" class="span2"/>
					</span>
					<span>
						<label style="width: 60px;">状态：</label>
						<select name="status" class="span2">
							<option></option>
							<option value="1">已启用</option>
							<option value="0">已禁用</option>
						</select>
					</span>
				</div>
			
				<div class="search-toolbar">
					<span style="float:left;">
						<a class="easyui-linkbutton clearDgChecked" icon="icon-redo" plain="true" title="清空所有勾选项" datagrid="${param.rel}_datagrid">清空勾选</a>
						<a class="easyui-linkbutton" icon="icon-add" plain="true" href="pageConfigApp/addPage.do?rel=${param.rel }" title="新增聚合页面" target="dialog" width="700" height="400" rel="${param.rel}_add"><span>新增</span></a>
						<a class="easyui-linkbutton" icon="icon-edit" plain="true" href="pageConfigApp/updatePage.do?id={id}&rel=${param.rel }" target="dialog"  width="700" height="400" rel="${param.rel}_update" warn="请先选择一条记录" title="修改聚合页面" datagrid="${param.rel}_datagrid">修改</a>
						<a class="easyui-linkbutton" icon="icon-remove"	plain="true" href="pageConfigApp/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录" datagrid="${param.rel}_datagrid">删除</a>
						<a class="easyui-linkbutton" icon="icon-cancel"	plain="true" href="pageConfig/clearCache.do" target="selectedTodo"  title="确定要清除缓存吗?" warn="至少勾选一条记录" datagrid="${param.rel}_datagrid">清除缓存</a>
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
		    <div id="pageQuickEntry" title="快捷入口" style="overflow:hidden;width: auto;height:auto;">
		    	<div id="pageQuickEntry_toolbar" class="search-div">
					<form onsubmit="return datagridSearch(this,'pageQuickEntry_datagrid');"  formatFilterData="true">
						<div class="search-toolbar">
							<span style="float:left;">
								<a class="easyui-linkbutton clearDgChecked" icon="icon-redo" plain="true" title="清空所有勾选项" datagrid="pageQuickEntry_datagrid">清空勾选</a>
								<a class="easyui-linkbutton" icon="icon-add" plain="true" href="pageConfig/pageQuickEntry/addPage.do?rel=pageQuickEntry" target="dialog" width="800" height="420" rel="pageQuickEntry_add" warn="请先选择一条主表记录" p_datagrid="${param.rel }_datagrid" title="新增快捷入口" type="2">新增</a>
								<a class="easyui-linkbutton" icon="icon-edit" plain="true" href="pageConfig/pageQuickEntry/updatePage.do?id={id}&rel=pageQuickEntry" target="dialog"  width="800" height="420" rel="pageQuickEntry_update" warn="请先选择一条记录" title="修改快捷入口" datagrid="pageQuickEntry_datagrid">修改</a>
								<a class="easyui-linkbutton" icon="icon-remove"	plain="true" href="pageConfig/pageQuickEntry/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录" datagrid="pageQuickEntry_datagrid">批量删除</a>
							</span>
							<span style="float:right;display: none;">
								<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
								<button class="btn btn-small clear" type="button">清空</button>&nbsp;
							</span>
						</div>
					</form>
				</div>
				<table id="pageQuickEntry_datagrid" toolbar="#pageQuickEntry_toolbar"></table>
		    </div>
		    <div id="pageColumn" title="栏目" style="overflow:hidden;width: auto;height:auto;">
		    	<div id="pageColumn_toolbar" class="search-div">
					<form onsubmit="return datagridSearch(this,'pageColumn_datagrid');"  formatFilterData="true">
						<div class="search-toolbar">
							<span style="float:left;">
								<a class="easyui-linkbutton clearDgChecked" icon="icon-redo" plain="true" title="清空所有勾选项" datagrid="pageColumn_datagrid">清空勾选</a>
								<a class="easyui-linkbutton" icon="icon-add" plain="true" href="pageConfigApp/pageColumn/addPage.do?rel=pageColumn" target="dialog" width="1000" height="550" rel="pageColumn_add" warn="请先选择一条主表记录" p_datagrid="${param.rel }_datagrid" title="新增栏目" type="2">新增</a>
								<a class="easyui-linkbutton" icon="icon-edit" plain="true" href="pageConfigApp/pageColumn/updatePage.do?id={id}&rel=pageColumn" target="dialog"  width="1000" height="550" rel="pageColumn_update" warn="请先选择一条记录" title="修改栏目" datagrid="pageColumn_datagrid">修改</a>
								<a class="easyui-linkbutton" icon="icon-remove"	plain="true" href="pageConfigApp/pageColumn/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录" datagrid="pageColumn_datagrid">批量删除</a>
							</span>
							<span style="float:right;display: none;">
								<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
								<button class="btn btn-small clear" type="button">清空</button>&nbsp;
							</span>
						</div>
					</form>
				</div>
				<table id="pageColumn_datagrid" toolbar="#pageColumn_toolbar" ></table>
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
				url : "pageConfigApp/query.do", 
				fitColumns: false,
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						 {
								field : "pageName",
								title : "页面名称",
								width : 200,
								align : "center",
						},{
							field : "pageUrl",
							title : "链接地址",
							width : 500,
							align : "center",
							formatter : function(value, row, index) {
								var v = "${pageUrl}"+row.id;
								return v;
							}
						},{
							field : "updateTime",
							title : "修改时间",
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
							width : 60,
							align : "center",
							formatter : function(value, row, index) {
								var v = "";
								if(value == 0){
									v = "已禁用";
								}else{
									v = "已启用";
								}
								return v;
							}
						},
						{
							field : "op",
							title : "操作",
							align:"center",
							width : 100,
							formatter: function(rowIndex, rowData){
								var v="";
								if(rowData.status == 0){
									v += "<a href = 'javaScript:void(0)' onclick = update_status('"+rowData.id+"',1)>启用</a>";
								}else{
									v += "<a href = 'javaScript:void(0)' onclick = update_status('"+rowData.id+"',0)>禁用</a>";
								}
								//v += "<a href = 'javaScript:void(0)' style= 'margin-left:20px;'  onclick = showviewb('"+rowData.id+"')>预览</a>";
								return v;
							}
						}
						
				] ],
				onClickRow:function(rowIndex, rowData){
					//给下面的控件datagrid赋值
					var pageId=rowData.id;
					$('#${param.rel}_datagrid').datagrid("clearChecked");
					$("#center").find("tr[datagrid-row-index="+rowIndex+"]").find(":checkbox").click();
					$("#south").find("a[icon='icon-add']").each(function(){
						var _href=$(this).attr("href");
						if(_href.indexOf("pid=")!=-1){
							_href=_href.substring(0,_href.indexOf("pid="));
							$(this).attr("href",_href+"&pid="+pageId);
						}else{
							$(this).attr("href",_href+"&pid="+pageId);
						}
					});
					if(rowData.status == 1){
						$("#center").find("a[icon='icon-remove']").hide();
					}else{
						$("#center").find("a[icon='icon-remove']").show();
					}
					load_pageQuickEntry(pageId);
					load_pageColumn(pageId);
				},
				onCheck: function(rowIndex,rowData){
					$('#${param.rel}_datagrid').datagrid('selectRow',rowIndex);
					$("#center").find("tr").find(":checkbox").each(function(){
						var _tr=$(this).parent().parent().parent();
						if(_tr.attr("datagrid-row-index") != rowIndex){
							$('#${param.rel}_datagrid').datagrid("uncheckRow", _tr.attr("datagrid-row-index"));
						}
					});
					var pageId=rowData.id;
					$("#south").find("a[icon='icon-add']").each(function(){
						var _href=$(this).attr("href");
						if(_href.indexOf("pid=")!=-1){
							_href=_href.substring(0,_href.indexOf("pid="));
							$(this).attr("href",_href+"&pid="+pageId);
						}else{
							$(this).attr("href",_href+"&pid="+pageId);
						}
					});
					if(rowData.status == 1){
						$("#center").find("a[icon='icon-remove']").hide();
					}else{
						$("#center").find("a[icon='icon-remove']").show();
					}
					load_pageQuickEntry(pageId);
					load_pageColumn(pageId);
				},
				onBeforeLoad:function(rowIndex, rowData){
					$(".datagrid-header-check").html("");
				},
				onLoadSuccess: function(data) {
					$('#${param.rel}_datagrid').datagrid("clearChecked");
					//if(data.rows.length == 0){
					//$('#pageQuickEntry_datagrid').datagrid('loadData',{total:0,rows:[]}); 
					//$('#pageColumn_datagrid').datagrid('loadData',{total:0,rows:[]}); 
					//}
				}
			});
	}
	
	var load_pageQuickEntry=function(pageId){
		$('#pageQuickEntry_datagrid').datagrid({
			url : "pageConfig/pageQuickEntry/query.do?pageId="+pageId,
			columns:[[
	            {
			    	field:"chk",
			    	title : "勾选",
			    	checkbox:true
			    },
				{
					field : "name",
					title : "入口名称",
					width : 100,
					align:"center"
	
				},
				{
					field : "imgUrl",
					title : "图片地址",
					width : 200,
					align:"center",
					formatter: function(value,row,index){
						return '<a href="${ossurl}'+value+'" target="_blank">'+value+'</a>';
					}
				},
				{
					field : "linkUrl",
					title : "链接地址",
					width : 200,
					align:"center",
					formatter: function(value,row,index){
						return '<a href="'+value+'" target="_blank">'+value+'</a>';
					}
				},
				{
					field : "orderNum",
					title : "排序",
					width : 50,
					align:"center",
					formatter: function(value,row,index){
						return value;
					}
				}
            ]],
			onDblClickRow:function(rowIndex, rowData){
				MUI.openDialog('修改快捷入口','pageConfig/pageQuickEntry/updatePage.do?id='+rowData.id+'&rel=pageQuickEntry','pageQuickEntry_update',{width:800,height:420});
			},
			onClickRow:function(rowIndex, rowData){
				$("#pageQuickEntry").find("tr[datagrid-row-index="+rowIndex+"]").find(":checkbox").click();
			}
		});
	};
	
	var load_pageColumn=function(pageId){
		$('#pageColumn_datagrid').datagrid({
			url : "pageConfigApp/pageColumn/query.do?pageId="+pageId,
			columns:[[
	            {
			    	field:"chk",
			    	title : "勾选",
			    	checkbox:true
			    },
				{
					field : "columnName",
					title : "栏目名称",
					width : 100,
					align:"center"
	
				},
				{
					field : "columnType",
					title : "栏目类型",
					width : 100,
					align:"center",
					formatter: function(value,row,index){
						var v="";
						if(value == 1){
							v = "普通栏目";
						}else{
							v = "动态栏目";
						}
						return v;
					}
				},
                {
                    field : "columnStatus",
                    title : "是否快速导航",
                    align:"center",
                    width : 100,
                    formatter: function(value,row,index){
                        var v="";
                        if(value == 1){
                            v = "是";
                        }else{
                            v = "否";
                        }
                        return v;
                    }
                },
				/*{
					field : "showStyle",
					title : "展示模板",
					width : 200,
					align:"center",
					formatter: function(value,row,index){
						var v="";
						if(row.columnType == 1){
							v = getValueByKey(1,value);
						}else{
							v = "/";
						}
						return v;
					}
				},*/
				{
					field : "columnNum",
					title : "元素数量",
					width : 100,
					align:"center",
					formatter: function(value,row,index){
						var v="";
						if(row.columnType == 1){
							v = "/";
						}else{
							v = value;
						}
						return v;
					}
				},
				{
					field : "elementValue",
					title : "元素内容",
					width : 100,
					align:"center",
					formatter: function(value,row,index){
						var v="";
						if(row.columnType == 1){
							v = "/";
						}else{
							v = value;
						}
						return v;
					}
				},
				{
					field : "moreUrl",
					title : "更多地址",
					width : 200,
					align:"center",
					formatter: function(value,row,index){
						return value;
					}
				},
				{
					field : "orderNum",
					title : "排序",
					width : 50,
					align:"center",
					formatter: function(value,row,index){
						return value;
					}
				}
            ]],
			onDblClickRow:function(rowIndex, rowData){
				MUI.openDialog('修改栏目','pageConfigApp/pageColumn/updatePage.do?id='+rowData.id+'&rel=pageColumn','pageColumn_update',{width:1000,height:550,buttons:[{
					text: '提交',
					iconCls: 'icon-ok',
					handler: function() {
						eval("save()");
					}
				},{
					text: '关闭',
					iconCls: 'icon-cancel',
					handler: function() {
						$('#pageColumn_update').dialog('close');
					}
				}]});
			},
			onClickRow:function(rowIndex, rowData){
				$("#pageColumn").find("tr[datagrid-row-index="+rowIndex+"]").find(":checkbox").click();
			}
		});
	};
	
	function showviewb(fid){
		MUI.openDialog('预览','pageConfig/view.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_view',{width:380,height:640});
	}
	
	function update_status(pageId,type){
		var tipStr="禁用";
		if(type == 1){
			tipStr="启用";
		}
		Msg.confirm("提示","是否确认"+tipStr+"该页面？",function(t){
			if(t){
				$.ajax({
					url:"pageConfig/updateStatus.do",
					data:{id:pageId},
					type: "post",	
					cache: false,
					dataType:"json",
					success:function(json){
						if(json&&json["code"]==="0"){
							Msg.topCenter({
								title:"提示",
								msg:'页面状态修改成功!',
								msgType:"success"
							});
							loadMain();
						}else{
							Msg.topCenter({
								title:"提示",
								msg: json["msg"] || '面状态修改失败!',
								msgType:"warning"
							});
						}
					}
					
				});
			}
		});
	}
</script>