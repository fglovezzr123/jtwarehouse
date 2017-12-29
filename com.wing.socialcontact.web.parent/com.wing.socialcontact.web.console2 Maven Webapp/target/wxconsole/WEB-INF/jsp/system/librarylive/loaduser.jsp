<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
				<input type="hidden" name="rel" value="${param.rel}" />
				<span>
					<label style="width: 100px;">姓名：</label>
					<input type="text" name="name"/>
				</span>
				<span>
					<label style="width: 100px;">手机号码：</label>
					<input type="text" name="mobile"/>
				</span>
				<span>
					<label style="width: 100px;">创建时间：</label>
					<input type="text"   name="createtime"   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 150px;"/>
					
				</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
					</span> 
		
					<span style="float:right">
						<!-- <a id="exportBtn8"  class="easyui-linkbutton">导出报表</a> -->
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
				url : "librarylive/userquery.do?id=${id}", 
				singleSelect:true,
				selectOnCheck:true,
				columns : [ [
						{
								field : "title",
								title : "活动标题",
								width : $(this).width() * 0.1,
								align : "center",
						},
						{
								field : "type",
								title : "活动类别",
								width : $(this).width() * 0.1,
								align : "center",
								formatter : function(value, row, index) {
									switch(value){
									 case  '1' :
									 		return "俊卿解惑";
									 case  '2' :
									 		return "与总统谈心";
									 case  '3' :
									 		return "商界冠军直播秀";
									 case  '4' :
									 		return "总裁读书会";
									}
								}
						},
						{
							field : "name",
							title : "姓名",
							width : $(this).width() * 0.1,
							align : "center"
						},
						{
							field : "mobile",
							title : "手机号码",
							width : $(this).width() * 0.1,
							align : "center"
						},
						{
							field : "amount",
							title : "价格(J币)",
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
							field : "createtime",
							title : "报名时间",
							width : $(this).width() * 0.1,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:ss");
							}
						},
						{
							field : "paystatus",
							title : "报名状态",
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
						}
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














