<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span><label style="width: 100px;">行业：</label>
					<select name="voType"  id="voType" style="width: 120px;">
					    <option  value="0" >全部</option>
						<c:forEach var="c" items="${list}">
						<option  value="${c.id}">${c.className}</option>
						</c:forEach>	
					</select>
					</span>
					<span>
						<label style="width: 100px;">问题：</label>
						<input	type="text" name="question" class="span2"/>
					</span>
					<span><label style="width: 100px;">有效期：</label>
					<input type="text" name="startTimeyx"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;"  />  
						至
					<input type="text" name="endTimeyx"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;" />
					</span>
					<br/>
					<span><label style="width: 100px;">状态：</label>
					<select name="status"  style="width: 120px;">
			    	<option  value="0" >全部</option>
						<option  value="1">待审核</option>
						<option  value="2">审核成功</option>
						<option  value="3">审核失败</option>
						<option  value="4">已到期</option>
						<option  value="5">已完成</option>
						<option  value="6">已取消</option>
					</select>
					</span>
					<span><label style="width: 100px;">创建时间：</label>
					<input type="text" name="startTimef"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;"  />  
						至
					<input type="text" name="endTimef"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;" />
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
				url : "reward/query.do", 
				fitColumns: false,
				columns : [ [
						{
							field : "选择",
							title : "选择",
							align : "center",
							width : $(this).width() * 0.05,
							formatter : function(value, row, index) {
								return "<a href='javascript:;' onclick=$.bringBack({id:'" + row.id + "',name:'" + row.question + "'})>选择</a>";
							}
						},
						 {
								field : "className",
								title : "行业",
								width : 80,
								align : "center",
						},{
							field : "question",
							title : "问题",
							width : 200,
							align : "center",
						},
						{
							field : "createUserName",
							title : "发布人",
							width : 100,
							align : "center",
						},{
							field : "status",
							title : "状态",
							width : 80,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.status==1){
									str = "待审核";
								}else if(rowData.status==2){
									str = "审核成功";
								}else if(rowData.status==3){
									str = "审核失败";
								}else if(rowData.status==4){
									str = "已到期";
								}else if(rowData.status==5){
									str = "已完成";
								}else if(rowData.status==6){
									str = "已取消";
								}
								return str;
							}
						},{
								field : "countNum",
								title : "应答",
								width : 80,
								align : "center",
						},
						{
							field : "reward",
							title : "悬赏J币",
							width : 80,
							align : "center"
						},
						{
							field : "yxq",
							title : "悬赏有效期",
							width : 150,
							align : "center",
							formatter : function(rowIndex, rowData) {
								return new Date(rowData.startTime)
										.format("yyyy-MM-dd")+"至"+new Date(rowData.endTime)
										.format("yyyy-MM-dd");
							}
						},{
							field : "allowComment",
							title : "是否允许评论",
							width : 80,
							align : "center",
							formatter : function(rowIndex, rowData)  {
								var str = "";
								if(rowData.allowComment==1){
									str = "是";
								}else if(rowData.allowComment==2){
									str = "否";
								}
								return str;
							}
						},
						{
							field : "createTime",
							title : "创建时间",
							width : 130,
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
							width : 80,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = showviewr('"+rowData.id+"')>查看</a>";
							}
						}
						
				] ],
				
			});
	}
	
	function showviewr(fid){
		MUI.openDialog('详情','reward/showPage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_show',{width:800,height:550});
	}
</script>














