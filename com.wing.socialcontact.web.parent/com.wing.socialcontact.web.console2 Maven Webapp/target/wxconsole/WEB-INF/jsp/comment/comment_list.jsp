<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">评论人：</label>
						<input	type="text" name="trueNamef" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">评论内容：</label>
						<input	type="text" name="cmeDesc" class="span2"/>
					</span>
					<span><label style="width: 100px;">评论类别：</label>
					<select name="cmeType"  style="width: 120px;">
			    	<option  value="" >全部</option>
						<option  value="1">资讯</option>
						<option  value="2">合作</option>
						<option  value="3">话题</option>
						<option  value="4">活动</option>
						<option  value="5">动态</option>
					</select>
					</span>
					<span><label style="width: 100px;">评论时间：</label>
					<input type="text" name="startTimef"    id="stime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'etime\')}'})"   style="width:120px;"  />  
						至
					<input type="text" name="endTimef"     id="etime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'stime\')}'})"   style="width:120px;" />
					</span>
					<span>
						<label style="width: 100px;">评论主题内容：</label>
						<input	type="text" name="title" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">手机号：</label>
						<input	type="text" name="mobile" class="span2"/>
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
						
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="comment/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
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
				url : "comment/query.do", 
			    ///nowrap:false,//允许换行
			    fitColumns:true,//宽度自适应
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						 {
								field : "cme_type2",
								title : "评论类别",
								width : 80,
								align : "center",
								formatter: function(value,row,index){ 
									if(value=='1'){
										return '资讯';
									}else if(value=='2'){
										return '合作';
									}else if(value=='3'){
										return '话题';
									}else if(value=='4'){
										return '活动';
									}else if(value=='5'){
										return '动态';
									}else if(value=='10'){
										return '资讯回复';
									}else if(value=='20'){
										return '合作回复';
									}else if(value=='30'){
										return '话题回复';
									}else if(value=='40'){
										return '活动回复';
									}else if(value=='50'){
										return '动态回复';
									}else{
										return ' ';
									}
									     
								}
						},
						{
							field : "trueName",
							title : "评论人",
							width : 100,
							align : "center",
						},
						{
							field : "mobile",
							title : "手机号",
							width : 100,
							align : "center",
						},
						 {
								field : "cme_desc",
								title : "评论内容",
								width : 450,
								align : "center",
								formatter: function(value,row,index){  
									   return '<span title='+value+'>'+value+'</span>'  
								}
						},
						{
							field : "create_time",
							title : "评论时间",
							width : 130,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm");
							}
						},
						 {
							field : "title",
							title : "评论主题内容",
							width : 550,
							align : "center"
						}
						
				] ],
				
			});
	}
	
	function showviewb(fid){
		///MUI.openDialog('编辑','business/updatePage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:650,height:550});
	}
</script>














