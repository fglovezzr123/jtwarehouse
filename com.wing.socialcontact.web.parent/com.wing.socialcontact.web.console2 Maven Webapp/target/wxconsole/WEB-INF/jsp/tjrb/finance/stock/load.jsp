<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
				<span>
					<label style="width: 100px;">项目名称：</label>
					<input type="text" name="productName"/>
				</span>
				<span>
					<label style="width: 100px;">融资类型：</label>
					<select name="financeType" id="financeType" style="width: 120px;">
						<option value="">全部</option>

					</select>
				</span>
				
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
						<%--<shiro:hasPermission name="entryQuickDetailBanner:add">--%>
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="financeProductStock/addPage.do?rel=${param.rel }" title="新增" target="dialog"
								width="800" height="550" rel="${param.rel}_add"><span>新增</span>
							</a>
						<%--</shiro:hasPermission> --%>
						<%--<shiro:hasPermission name="entryQuickDetailBanner:update">--%>
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="financeProductStock/updatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="800" height="550"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
						<%--</shiro:hasPermission>--%>
						<%--<shiro:hasPermission name="entryQuickDetailBanner:delete">--%>
					   		<a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
							href="financeProductStock/del.do" target="selectedTodo"  title="确定要删除吗?" warn="请至少选择一条记录">批量删除</a>
				       <%--</shiro:hasPermission>--%>
						</span> 
		
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
					</span>
		
				</div>
			</form>
		</div>
		<table id="financeProductStack_datagrid" toolbar="#${param.rel}_toolbar"></table>

				

<script type="text/javascript">	
	$(function() {
//	    alert("dafdasfa");
		loadMain();			
	});
	
	function loadMain(){
//        alert("1111");
		$('#financeProductStack_datagrid').datagrid(
			{
				url : "financeProductStock/query.do",
				columns : [ [
						{
							field:"ck",
							title : "勾选",
							checkbox:true
						},   
						{
								field : "productName",
								title : "项目名称",
								width : $(this).width() * 0.10,
								align : "center",
						},
						{
								field : "orgName",
								title : "公司名称",
								width : $(this).width() * 0.20,
								align : "center",
						},
                    {
                        field : "orgName",
                        title : "项目平台",
                        width : $(this).width() * 0.20,
                        align : "center",
                    },
                    {
                        field : "orgName",
                        title : "项目进度",
                        width : $(this).width() * 0.20,
                        align : "center",
                    },
                    {
                        field : "orgName",
                        title : "目标金额",
                        width : $(this).width() * 0.20,
                        align : "center",
                    },
                    {
                        field : "orgName",
                        title : "权重",
                        width : $(this).width() * 0.20,
                        align : "center",
                    },
                    {
                        field : "orgName",
                        title : "是否显示",
                        width : $(this).width() * 0.20,
                        align : "center",
                    },
                    {
                        field : "orgName",
                        title : "是否推荐",
                        width : $(this).width() * 0.20,
                        align : "center",
                    },
						{
							field : "createDate",
							title : "创建时间",
							width : $(this).width() * 0.30,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm");
							}
						}/* ,
						{
							field : "op",
							title : "操作",
							align:"center",
							width : $(this).width() * 0.15,
							formatter: function(rowIndex, rowData){
								//活动开始前可编辑
								if(rowData.status<4){
								return  "<a href = 'javaScript:void(0)' onclick = updateview('"+rowData.id+"')>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href = 'javaScript:void(0)' onclick = deleteactivity('"+rowData.id+"')>删除</a>";
								}
								return "<a href = 'javaScript:void(0)' onclick = deleteactivity('"+rowData.id+"')>删除</a>";
							}
						} */
						
				] ],
				
			});
	}
	
	<%-- function updateview(fid){
		MUI.openDialog('编辑活动','activity/updatePage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:800,height:600});
	}
	function deleteactivity(id){
		Msg.confirm('提示',"确定要删除吗？<br/>", function(r){
	          if (r){
				var pm = {"id":id};
				var dg = $('#<%=request.getParameter("rel")%>_datagrid');
				selectedTodo_doPost("activity/del.do",pm,dg,'');
	          }
	     });
	} --%>
</script>














