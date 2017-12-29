<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
				<span>
					<label style="width: 100px;">标签名称：</label>
					<input type="text" name="name"/>
				</span>
				<span>
					<label style="width: 100px;">创建时间：</label>
					<input type="text"   name="createTime"   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 150px;"/>
					
				</span>
				</div>

				<div class="search-toolbar">
					<span style="float:right">
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
				url : "qfy/hotservice/query.do", 
				columns : [ [
						   
						{
								field : "name",
								title : "标签名称",
								width : $(this).width() * 0.15,
								align : "center",
						},
						{
							field : "img_path",
							title : "图片",
							width : $(this).width() * 0.15,
							align : "center",
							formatter:function(value,row){
								var str = "";
								if(value != "" && value != null){
									str = "<img style=\"height: 30px;width: 30px;\" src=\""+value+"\"/>";
                                    return str;
								}
							}
						},
						{
							field:"hotSort",
							title : "序号",
							width : $(this).width() * 0.10,
							align : "center",
						},
						{
								field : "hotTitle",
								title : "对应联盟",
								width : $(this).width() * 0.15,
								align : "center",
						},
						{
								field : "hotName",
								title : "企业名称",
								width : $(this).width() * 0.15,
								align : "center",
						},
						{
							field : "createTime",
							title : "创建时间",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm");
							}
						},
						{
							field : "op",
							title : "操作",
							align:"center",
							width : $(this).width() * 0.15,
							formatter: function(rowIndex, rowData){
								//活动开始前可编辑
								if(isEmpty(rowData.hotSort)){
								return  "<a href = 'javaScript:void(0)' onclick = updateview('"+rowData.id+"')>添加</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href = 'javaScript:void(0)' onclick = deleteactivity('"+rowData.id+"')>删除</a>";
								}
								return  "<a href = 'javaScript:void(0)' onclick = updateview('"+rowData.id+"')>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href = 'javaScript:void(0)' onclick = deleteactivity('"+rowData.id+"')>删除</a>";
							}
						}
						
				] ],
				
			});
	}
	
	function updateview(fid){
		MUI.openDialog('关联企业','qfy/hotservice/updatePage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:1200,height:600});
	}
	function deleteactivity(id){
		Msg.confirm('提示',"确定要删除吗？<br/>", function(r){
	          if (r){
				var pm = {"id":id};
				var dg = $('#<%=request.getParameter("rel")%>_datagrid');
				selectedTodo_doPost("qfy/hotservice/del.do",pm,dg,'');
	          }
	     });
	}
</script>














