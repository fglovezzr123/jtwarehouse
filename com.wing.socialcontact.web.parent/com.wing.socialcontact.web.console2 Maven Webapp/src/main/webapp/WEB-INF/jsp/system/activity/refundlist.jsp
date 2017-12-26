<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
				<span>
					<label style="width: 100px;">活动主题：</label>
					<input type="text" name="titles"/>
				</span>
				<!-- <span>
					<label style="width: 100px;">报名者：</label>
					<input type="text" name="userName"/>
				</span> -->
				<!-- <span>
					<label style="width: 100px;">创建时间：</label>
					<input type="text"   name="createTime"   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 150px;"/>
					
				</span> -->
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
				url : "activity/refundquery.do", 
				columns : [ [
						{
								field : "titles",
								title : "活动标题",
								width : $(this).width() * 0.2,
								align : "center",
						},
						{
							field : "create_user_name",
							title : "主办方",
							width : $(this).width() * 0.1,
								align : "center",
						},
						{
							field : "mobile2",
							title : "电话",
							width : $(this).width() * 0.1,
								align : "center",
						},
						{
							field : "nickname",
							title : "退款用户",
							width : $(this).width() * 0.1,
								align : "center",
						},
						{
							field : "mobile1",
							title : "退款用户电话",
							width : $(this).width() * 0.1,
								align : "center",
						},
						{
							field : "amount",
							title : "退款金额",
							width : $(this).width() * 0.1,
								align : "center",
						},
						{
							field : "class_id",
							title : "活动类别",
							width : $(this).width() * 0.1,
							align : "center",
							formatter : function(value, row, index) {
								
								switch(value){
								 case  '1' :
								 		return "以玩会友";
								 case  '2' : 
								 		return "以书会友";
								}
							}
						},
						{
							field : "sta",
							title : "退款状态",
							width : $(this).width() * 0.1,
							align : "center",
							formatter : function(rowIndex, rowData) {
								
								switch(rowData.sta){
								 case  0 :
								 		return "未申请退款";
								 case  1 : 
								 		return "已申请退款";
								}
							}
						},
						{
							field : "type",
							title : "退款原因",
							width : $(this).width() * 0.1,
							align : "center",
							formatter : function(value, row, index) {
								
								switch(value){
								 case  1 : 
								 		return "报名未通过审核";
								 case  2 :
								 		return "活动取消";
								}
							}
						},
						/* {
							field : "createTime",
							title : "创建时间",
							width : $(this).width() * 0.1,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm:ss");
							}
						}, */
						{
							field : "refundTime",
							title : "退款时间",
							width : $(this).width() * 0.1,
							align : "center",
							formatter : function(value, row, index) {
								if(value!=null){
								return new Date(value)
										.format("yyyy-MM-dd HH:mm:ss");
								}
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














