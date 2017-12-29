<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel}_datagrid');" >
				<div class="search-content">
				<span>
					<label style="width: 70px;">产品名称：</label>
					<input type="text" name="productName"/>
				</span>
					<span>
					<label style="width: 70px;">产品机构：</label>
					<input type="text" name="orgNam"/>
				</span>
				<!-- <span>
					<label style="width: 100px;">融资类型：</label>
					<select name="financeType" style="width: 120px;">
						<option value="">全部</option>
					</select>
				</span>
					<span>
					<label style="width: 100px;">融资期限：</label>
					<select name="financeType" style="width: 120px;">
						<option value="">全部</option>
					</select>
				</span>
					<span>
					<label style="width: 100px;">融资额度：</label>
					<select name="financeType" style="width: 120px;">
						<option value="">全部</option>
					</select>
				</span> -->
					<span>
					<label style="width: 100px;">是否显示：</label>
					<select name="isShow" id="isShow" required="true" style="width: 120px;">
						<option  value="1">是</option>
						<option  value="2">否</option>
					</select>
				</span>
					<span>
					<label style="width: 100px;">是否推荐：</label>
						<select name="isRecommend" id="isRecommend" required="true" style="width: 120px;">
						<option  value="1">是</option>
						<option  value="2">否</option>
					</select>
				</span>
				
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
						<%--<shiro:hasPermission name="entryQuickDetailBanner:add">--%>
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="investProductInsure/addPage.do?rel=${param.rel }" title="新增" target="dialog"
								width="800" height="550" rel="${param.rel}_add"><span>新增</span>
							</a>
						<%--</shiro:hasPermission> --%>
						<%--<shiro:hasPermission name="entryQuickDetailBanner:update">--%>
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="investProductInsure/updatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="800" height="550"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
						<%--</shiro:hasPermission>--%>
						<%--<shiro:hasPermission name="entryQuickDetailBanner:delete">--%>
					   		<a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
							href="investProductInsure/del.do" target="selectedTodo"  title="确定要删除吗?" warn="请至少选择一条记录">批量删除</a>
				       <%--</shiro:hasPermission>--%>
						</span> 
		
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="button" onclick="loadMain()">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
					</span>
		
				</div>
			</form>
		</div>
		<table id="${param.rel}_datagrid" toolbar="#${param.rel}_toolbar"></table>

				

<script type="text/javascript">	
	$(function() {
		loadMain();			
	});
	
	function loadMain(){
		$('#${param.rel}_datagrid').datagrid(
			{
				url : "investProductInsure/query.do",
				columns : [ [
						{
							field:"ck",
							title : "勾选",
							checkbox:true
						},   
						{
								field : "productName",
								title : "产品名称",
								width : $(this).width() * 0.10,
								align : "center",
						},
						{
								field : "orgName",
								title : "发行机构",
								width : $(this).width() * 0.20,
								align : "center",
						},
                    {
                        field : "crowdType",
                        title : "产品受众",
                        width : $(this).width() * 0.20,
                        align : "center",
                        formatter : function(value, row, index) {
							if(value==1){
								value = "儿童";
							}else if(value==2){
								value = "成人";
							}else if(value==3){
								value = "老人";
							}else{
								value = "家庭";
							}
                            return value;
                        }
                    },
                    {
                        field : "startPoint",
                        title : "起购额度",
                        width : $(this).width() * 0.20,
                        align : "center",
                        formatter : function(value, row, index) {
                            return value;
                        }
                    },
                    {
                        field : "expectedReturn",
                        title : "最高保额",
                        width : $(this).width() * 0.20,
                        align : "center",
                        formatter : function(value, row, index) {
                            return value;
                        }
                    },
                    {
                        field : "ratio",
                        title : "权重",
                        width : $(this).width() * 0.20,
                        align : "center"
                    },
                    {
                        field : "isShow",
                        title : "是否显示",
                        width : $(this).width() * 0.20,
                        align : "center",
                        formatter : function(value, row, index) {
                            if(value==1){
                                return "是";
                            }else{
                                return "否";
                            }
                        }
                    },
                    {
                        field : "isRecommend",
                        title : "是否推荐",
                        width : $(this).width() * 0.20,
                        align : "center",
                        formatter : function(value, row, index) {
                            if(value==1){
                                return "是";
							}else{
                                return "否";
							}
                        }
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














