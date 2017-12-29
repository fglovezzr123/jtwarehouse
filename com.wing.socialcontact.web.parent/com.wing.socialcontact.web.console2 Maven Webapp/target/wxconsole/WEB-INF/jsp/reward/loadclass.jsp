<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">行业名称：</label>
						<input	type="text" name="className" class="span2"/>
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
						<shiro:hasPermission name="newsclass:add">
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="reward/addPageClass.do?rel=${param.rel }" title="新增" target="dialog"
								width="700" height="350" rel="${param.rel}_add"><span>新增</span>
							</a>
						</shiro:hasPermission> 
						<shiro:hasPermission name="newsclass:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="reward/updatePageClass.do?id={id}&rel=${param.rel}"
								target="dialog" width="700" height="350"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
						</shiro:hasPermission>
						
						</span> 
		
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
				url : "reward/queryClass.do", 
				fitColumns: false,
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						 {
								field : "classNum",
								title : "排序",
								width : 120,
								align : "center",
						},
						{
							field : "className",
							title : "行业名称",
							width : 300,
							align : "center"
						},{
							field : "isRecommend",
							title : "推荐",
							width : 150,
							align : "center",
							formatter : function(rowIndex, rowData)  {
								var str = "";
								if(rowData.isRecommend==1){
									str = "<a href = 'javaScript:void(0)' onclick = tuijian('"+rowData.id+"','2')>取消推荐</a>";
								}else if(rowData.isRecommend==2){
									str = "<a href = 'javaScript:void(0)' onclick = tuijian('"+rowData.id+"','1')>推荐</a>";
								}
								return str;
							}
						},
						{
							field : "op",
							title : "操作",
							align:"center",
							width : 150,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = showviewc('"+rowData.id+"')>编辑</a>&nbsp;&nbsp;"
								+"<a href = 'javaScript:void(0)' onclick = deleteClass('"+rowData.id+"')>删除</a>"
								
							}
						}
						
				] ],
				
			});
	}
	
	function showviewc(fid){
		MUI.openDialog('编辑','reward/updatePageClass.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:700,height:350});
	}
	//删除
	function deleteClass(fid){
		Msg.confirm('提示',"确定要删除吗？<br/>", function(r){
	          if (r){
	        	  var pm = {"id":fid};
					var dg = $('#<%=request.getParameter("rel")%>_datagrid');
					selectedTodo_doPost("reward/delClass.do",pm,dg,'');
	          }
	     });
	}
	//推荐
	function tuijian(fid,rec){
		var str = "";
		if(rec=='1'){
			str = "确定要推荐吗？<br/>";
		}else if(rec=='2'){
			str = "确定取消推荐吗？<br/>";
		}
		Msg.confirm('提示',str, function(r){
	          if (r){
	        	  var pm = {"id":fid,"isRecommend":rec};
					var dg = $('#<%=request.getParameter("rel")%>_datagrid');
					selectedTodo_doPost("reward/updateRecommend.do",pm,dg,'');
	          }
	     });
	}
</script>














