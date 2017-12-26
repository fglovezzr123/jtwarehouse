<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">标题：</label>
						<input	type="text" name="title" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">直播类别：</label>
						<select name="type">
							<option value="">所有</option>
							<option value="1">俊卿解惑</option>
							<option value="2">与总统谈心</option>
							<option value="3">商界冠军直播秀</option>
							<option value="4">总裁读书会</option>
						</select>
					</span>
					<span>
					<label style="width: 100px;">创建时间：</label>
					<input type="text"   name="stime" id="stime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'etime\')}'})" style="width: 120px;"/>至
					<input type="text"   name="etime" id="etime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'stime\')}'})" style="width: 120px;"/>
				</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="librarylive/addPage.do?rel=${param.rel }" title="新增" target="dialog"
								width="1000" height="600" rel="${param.rel}_add"><span>新增</span>
							</a>
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="librarylive/updatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="1000" height="600"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
							<a  class="easyui-linkbutton"  icon="icon-edit"	plain="true"  id="updatestatus"
								href="librarylive/userload.do?id={id}&rel=${param.rel}_a" target="dialog"  rel="${param.rel}_read1" title="报名用户" warn="选择一条信息">报名用户</a>
						
						<%-- <shiro:hasPermission name="library:delete">
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="library/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
				       </shiro:hasPermission> --%>
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
				url : "librarylive/query.do", 
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						 {
								field : "title",
								title : "标题",
								width : $(this).width() * 0.15,
								align : "center",
						},
						{
							field : "type",
							title : "直播类型",
							width : 130,
							align : "center",
							formatter : function(value, row, index) {
								if(value==1){
									return "俊卿解惑";
								}else if(value==2){
									return "与总统谈心";
								}else if(value==3){
									return "商界冠军直播秀";
								}else if(value==4){
									return "总裁读书会";
								}
							}
						},
						{
							field : "times",
							title : "直播时间",
							width : 230,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(row.startTime).format("yyyy/MM/dd HH:mm")+"至"
										+new Date(row.endTime).format("yyyy/MM/dd HH:mm");
							}
						},
						{
							field : "sort",
							title : "权重",
							width : $(this).width() * 0.05,
							align : "center"
						},
						{
							field : "isShow",
							title : "是否显示",
							width : $(this).width() * 0.08,
							align : "center",
							formatter : function(value, row, index) {
								return value==1?"是":"否";
							}
						},
						{
							field : "isEnd",
							title : "结束后是否收费",
							width : $(this).width() * 0.08,
							align : "center",
							formatter : function(value, row, index) {
								if(row.ticketPrice>0){
									if(value==1){
										return  row.ticketPrice+"J币";
									}else{
										return "否";
									}
								}
								return "免费";
							}
						},
						{
							field : "createTime",
							title : "添加时间",
							width : $(this).width() * 0.1,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm:ss");
							}
						},
						{
							field : "op",
							title : "操作",
							align:"center",
							width : $(this).width() * 0.1,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = showviewli('"+rowData.id+"')>查看</a>|<a href = 'javaScript:void(0)' onclick = deletelive('"+rowData.id+"')>删除</a>";
							}
						}
						
				] ],
				
			});
	}
	
	function showviewli(fid){
		MUI.openDialog('查看详情','librarylive/viewPage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_show',{width:1000,height:600});
	}
	function deletelive(id){
		Msg.confirm('提示',"确定要删除吗？<br/>", function(r){
	          if (r){
				var pm = {"id":id};
				var dg = $('#<%=request.getParameter("rel")%>_datagrid');
				selectedTodo_doPost("librarylive/del.do",pm,dg,'');
	          }
	     });
	}
</script>














