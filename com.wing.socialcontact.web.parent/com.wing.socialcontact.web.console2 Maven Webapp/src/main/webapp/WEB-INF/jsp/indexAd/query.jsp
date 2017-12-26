<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% 
	String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.getSession().setAttribute("path", path);
%>
	
<div id="${param.rel}_toolbar" class="search-div">
	<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
		<div class="search-content">
			<span>
				<label style="width: 80px;">状态：</label>
				<select name="status" class="span2">
					<option></option>
					<option value="1">启用</option>
					<option value="0">禁用</option>
				</select>
			</span>
		</div>
		<div class="search-toolbar">
			<span style="float: left;"> 
				<shiro:hasPermission name="indexAd:add">
					<a class="easyui-linkbutton" icon="icon-add" plain="true" href="indexAd/addPage.do?rel=${param.rel }" title="新增" target="dialog" width="1000" height="600" rel="${param.rel}_add"><span>新增</span></a>
				</shiro:hasPermission> 
				<shiro:hasPermission name="indexAd:update">
					<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改" 	href="indexAd/updatePage.do?id={id}&rel=${param.rel}" target="dialog" width="1000" height="600" rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span></a>
				</shiro:hasPermission>
				<shiro:hasPermission name="indexAd:delete">
			   		<a  class="easyui-linkbutton" icon="icon-remove" plain="true" href="indexAd/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
		       	</shiro:hasPermission>
			</span> 
			<span style="float:right;">
				<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
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
				url : "indexAd/query.do", 
				fitColumns: false,
				frozenColumns:[[
	                 {
					    	field:"ck",
					    	title : "勾选",
					    	checkbox:true
					 },
					 {
						field : "imgName",
						title : "图片名称",
						width : 200,
						align : "center",
					},{
						field : "imgLink",
						title : "图片链接",
						width : 200,
						align : "center",
					},{
						field : "userRange",
						title : "用户范围",
						width : 80,
						align : "center",
						formatter : function(value, row, index) {
							if(value == 1){
								return "全部";
							}else{
								return "指定用户";
							}
						}
					}
				]],
				columns : [ [
			            {
							field : "userLevel",
							title : "用户等级",
							width : 200,
							align : "center",
							formatter : function(value, row, index) {
								if(row.userRange == 1){
									return "/";
								}else{
									return row.userLevelName;
								}
							}
						},{
							field : "reconUserFlag",
							title : "认证用户是否可见",
							width : 120,
							align : "center",
							formatter : function(value, row, index) {
								if(row.userRange == 1){
									return "/";
								}else{
									if(value == 1){
										return "是";
									}else{
										return "否";
									}
								}
							}
						},
						//{
						//	field : "regUserFlag",
						//	title : "注册用户是否可见",
						//	width : 120,
						//	align : "center",
						//	formatter : function(value, row, index) {
						//		if(row.userRange == 1){
						//			return "/";
						//		}else{
						//			if(value == 1){
						//				return "是";
						//			}else{
						//				return "否";
						//			}
						//		}
						//	}
						//},
						{
							field : "orderNum",
							title : "排序",
							width : 80,
							align : "center",
						},
						{
							field : "status",
							title : "状态",
							width : 80,
							align : "center",
							formatter : function(value, row, index) {
								if(value == 1){
									return "已启用";
								}else{
									return "已禁用";
								}
							}
						},
						{
							field : "createTime",
							title : "创建时间",
							width : 140,
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
							field : "op",
							title : "操作",
							align:"center",
							width : 80,
							formatter: function(rowIndex, rowData){
								var v="";
								if(rowData.status == 0){
									v += "<a href = 'javaScript:void(0)' onclick = update_status('"+rowData.id+"',1)>启用</a>";
								}else{
									v += "<a href = 'javaScript:void(0)' onclick = update_status('"+rowData.id+"',0)>禁用</a>";
								}
								return v;
							}
						}
						
				] ],
				
			});
	}
	
	function showviewb(fid){
		MUI.openDialog('查看详情','indexAd/updatePage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:800,height:400});
	}
	
	function update_status(id,type){
		var tipStr="禁用";
		if(type == 1){
			tipStr="启用";
		}
		Msg.confirm("提示","是否确认"+tipStr+"该条记录？",function(t){
			if(t){
				$.ajax({
					url:"indexAd/updateStatus.do",
					data:{id:id},
					type: "post",	
					cache: false,
					dataType:"json",
					success:function(json){
						if(json&&json["code"]==="0"){
							Msg.topCenter({
								title:"提示",
								msg:'状态修改成功!',
								msgType:"success"
							});
							loadMain();
						}else{
							Msg.topCenter({
								title:"提示",
								msg:'状态修改失败!',
								msgType:"warning"
							});
						}
					}
					
				});
			}
		});
	}
</script>