<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
				<input type="hidden" name="rel" value="${param.rel}" />
				<span>
					<label style="width: 100px;">姓名：</label>
					<input type="text" name="userName"/>
				</span>
				<span>
					<label style="width: 100px;">手机号码：</label>
					<input type="text" name="phone"/>
				</span>
				<span>
					<label style="width: 100px;">创建时间：</label>
					<input type="text"   name="createTime"   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 150px;"/>
					
				</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
					</span> 
		
					<span style="float:right">
						<a id="exportBtn8"  class="easyui-linkbutton">导出报表</a>
						<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
					</span>
				</div>
			</form>
			<form id="exportForm8" > 
				<input type="hidden" id="acid1" name="acid1" />
				<input type="hidden" id="userName1" name="userName1" />
				<input type="hidden" id="phone1" name="phone1" />
				<input type="hidden" id="createTime1" name="createTime1" />
			</form>
		</div>
		<table id="${param.rel }_datagrid" toolbar="#${param.rel}_toolbar"></table>

				

<script type="text/javascript">	
var _index=-1;
	$(function() {
		loadMain();			
	});
	
	function loadMain(){
		$('#<%=request.getParameter("rel")%>_datagrid').datagrid(
			{
				url : "activityUser/query.do?acid=${id}", 
				singleSelect:true,
				selectOnCheck:true,
				columns : [ [
						{
								field : "titles",
								title : "活动标题",
								width : $(this).width() * 0.1,
								align : "center",
						},
						{
								field : "classId",
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
							field : "userName",
							title : "姓名",
							width : $(this).width() * 0.1,
							align : "center"
						},
						{
							field : "phone",
							title : "手机号码",
							width : $(this).width() * 0.1,
							align : "center"
						},
						{
							field : "price",
							title : "门票价格",
							width : $(this).width() * 0.1,
							align : "center",
							formatter : function(value, row, index) {
								if(value==0){
									return "免费";
								}else{
									return value;
								}
							}
						},
						{
							field : "company",
							title : "公司名称",
							width : $(this).width() * 0.1,
							align : "center"
						},
						{
							field : "createTime",
							title : "报名时间",
							width : $(this).width() * 0.1,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:ss");
							}
						},
						{
							field : "status",
							title : "报名状态",
							width : $(this).width() * 0.1,
							align : "center",
							formatter : function(value, row, index) {
								
								switch(value){
								 case  1 :
								 		return "未确认";
								 case  2 :
								 		return "用户取消";
								 case  3 :
								 		return "活动取消";
								 case  4 :
								 		return "已确认";
								 case  5 :
								 		return "已拒绝";
								}
							}
						}/* ,
						{
							field : "orderStatus",
							title : "订单状态",
							width : $(this).width() * 0.1,
							align : "center",
							formatter : function(value, row, index) {
								
								switch(value){
								 case  1 :
								 		return "未支付";
								 case  2 :
								 		return "已支付";
								 case  3 :
								 		return "已退款";
								}
							}
						} */
						
				] ]
				
			});
	}
	
	$(function() {
		$("#exportBtn8").click(function(){
			var  acid1='${id}';
			var  userName1=$("#userName1").val();
			var  phone1=$("#phone1").val();
			var  createTime1=$("#createTime1").val();
			$("#acid1").val(acid1);
			$("#userName1").val(userName1);
			$("#phone1").val(phone1);
			$("#createTime1").val(createTime1);
			$("#exportForm8").attr("action","activityUser/export.do");
			$("#exportForm8").attr("target","exportFram");
			$("#exportForm8").submit();
		});	
	});
	function refuse(id){
				var pm = {"id":id};
				var dg = $('#<%=request.getParameter("rel")%>_datagrid');
				selectedTodo_doPost("activityUser/refuse.do",pm,dg,'');
	}
	function agree(id){
				var pm = {"id":id};
				var dg = $('#<%=request.getParameter("rel")%>_datagrid');
				selectedTodo_doPost("activityUser/agree.do",pm,dg,'');
	}
</script>














