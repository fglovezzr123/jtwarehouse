<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
			<%-- <input id="${param.rel}_classId" name="classId" type="hidden" value="0"/> --%>
				<div class="search-content">
					<span>
						<label style="width: 100px;">文章名称：</label>
						<input	type="text" name="title" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">文章类别：</label>
						<select name="oneclass">
							<option value="">所有</option>
						<c:forEach items="${classes }"   var="oc">
							<option value="${oc.id }">${oc.name }</option>
						</c:forEach>
						</select>
					</span>
					<span>
						<label style="width: 100px;">文章标签：</label>
						<a href="libraryclass/lookUpPage.do?type=1"  lookupGroup="libraryclass" title="分类查询">
							<input type="text" readonly="readonly"  toName="libraryclass.name"  class="easyui-validatebox"  value='<my:outLibraryClassName id="${param.classId }"/>'/>
						</a>
						<input type="hidden" name="classId"  toName="libraryclass.id"  value="${param.classId }"/>
					</span>
					<span>
						<label style="width: 100px;">发布时间：</label>
						<input type="text"   name="stime" id="stime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'etime\')}'})" style="width: 120px;"/>至
						<input type="text"   name="etime" id="etime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'stime\')}'})" style="width: 120px;"/>
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
						<shiro:hasPermission name="library:add">
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="library/addPage.do?rel=${param.rel }" title="新增" target="dialog"
								width="1000" height="600" rel="${param.rel}_add"><span>新增</span>
							</a>
						</shiro:hasPermission> 
						<shiro:hasPermission name="library:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="library/updatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="1000" height="600"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="library:delete">
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="library/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
				       </shiro:hasPermission>
						</span> 
		
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
					</span>
		
				</div>
			</form>
		</div>
		<table id="${param.rel }_datagrid" toolbar="#${param.rel}_toolbar"  ></table>

				

<script type="text/javascript">	
	$(function() {
		loadMain();			
	});
	
	function loadMain(){
		$('#<%=request.getParameter("rel")%>_datagrid').datagrid(
			{
				url : "library/query.do", 
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						 {
								field : "title",
								title : "文章标题",
								width : $(this).width() * 0.15,
								align : "center",
						},
						{
							field : "onename",
							title : "文章类别",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "name",
							title : "文章标签",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "sort",
							title : "权重",
							width : $(this).width() * 0.1,
							align : "center"
						},
						{
							field : "updateTime",
							title : "创建时间",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm:ss");
							}
						},
						{
							field : "createTime",
							title : "发布时间",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm:ss");
							}
						},
						{
							field : "recommend",
							title : "推荐",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								
								switch(value){
								 case  0 :
								 		return "否";
								 case  1 :
								 		return "是";
								}
							}
						},
						{
							field : "op",
							title : "操作",
							align:"center",
							width : $(this).width() * 0.15,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = showviewn('"+rowData.id+"')>查看</a>|<a href = 'javaScript:void(0)' onclick = yulan('"+rowData.id+"')>预览</a>";
							}
						}
						
				] ],
				
			});
	}
	
	function showviewn(fid){
		MUI.openDialog('查看详情','library/viewPage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_show',{width:1000,height:600});
	}
	function yulan(fid){
		MUI.openDialog('预览','library/viewyulan.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_show',{width:350,height:650});
	}
</script>














