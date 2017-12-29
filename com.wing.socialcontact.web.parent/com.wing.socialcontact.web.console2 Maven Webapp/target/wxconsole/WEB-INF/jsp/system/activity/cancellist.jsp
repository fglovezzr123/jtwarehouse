<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
				<span>
					<label style="width: 100px;">活动主题：</label>
					<input type="text" name="titles"/>
				</span>
				<span>
					<label style="width: 100px;">发起人：</label>
					<input type="text" name="userName"/>
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
				url : "activity/cancelquery.do", 
				columns : [ [
						{
								field : "titles",
								title : "活动标题",
								width : $(this).width() * 0.2,
								align : "center",
						},
						{
							field : "create_user_name",
							title : "发起者",
							width : $(this).width() * 0.1,
								align : "center",
						},
						{
							field : "mobile",
							title : "发起者电话",
							width : $(this).width() * 0.1,
							align : "center",
						},
						{
								field : "id",
								title : "申请类型",
								width : $(this).width() * 0.1,
								align : "center",
								formatter : function(value, row, index) {
									 		return "取消申请";
								}
						},
						{
							field : "times",
							title : "活动时间",
							width : 320,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(row.start_time).format("yyyy-MM-dd HH:mm")+"至"
										+new Date(row.end_time).format("yyyy-MM-dd HH:mm");
							}
						},
						{
							field : "ticket_price",
							title : "门票",
							width : $(this).width() * 0.1,
							align : "center"
						},
						{
							field : "sta",
							title : "审核状态",
							width : $(this).width() * 0.1,
							align : "center",
							formatter : function(rowIndex, rowData) {
								
								switch(rowData.sta){
								 case  0 :
								 		return "未审核";
								 case  1 : 
								 		return "已同意";
								 case  2 :
								 		return "已拒绝";
								}
							}
						},
						{
							field : "cretime",
							title : "创建时间",
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
							width : $(this).width() * 0.2,
							align : "center",
							formatter : function(rowIndex, rowData) {
								var status= rowData.status;//活动状态
								var sta= rowData.sta;//审核状态状态
								var isdelay= rowData.isdelay;//延期申请
								var iscancel= rowData.iscancel;//取消申请
								var iscod=rowData.iscod;//是否结算
								var str="";
								if(status<5){
									if(sta==0){
										str="<a href = 'javaScript:void(0)' onclick = checkcancel('"+rowData.id+"')>编辑</a>";
									}else if(sta==1){
										str="已同意";
									}else if(sta==2){
										str="已拒绝";
									}
								}else if(status==5){
									str="活动已结束";
								}else{
									str="活动已取消";
								}
								
								return str;
							}
						}
						
				] ],
				
			});
	}
	
	function checkcancel(fid){
		MUI.openDialog('取消审核','activity/cancelPage.do?acid='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_cancel',{width:600,height:700});
	}
	<%-- function checkdelay(fid){
		MUI.openDialog('延期审核','activity/delayPage.do?acid='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_delay',{width:600,height:700});
	} --%>
</script>














