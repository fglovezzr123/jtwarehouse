<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 40px"></label>
						<select id="from" name="from" style="width: 120x;">
							<option value="发起人姓名">发起人姓名</option>
							<option value="发起人手机号">发起人手机号</option>
						</select>
						<input type="text" value="" style="display: inline" id="fn" name="fromName" class="span2"/>
						<input type="text" value="" style="display: none" id="fm" name="fromTel" class="span2"/>
					</span>
					<span>
						<label style="width: 30px;"></label>
						<select name="to" id="to" style="width: 120x;">
							<option value="被约见人姓名">被约见人姓名</option>
							<option value="被约见人手机号">被约见人手机号</option>
						</select>
						<input type="text" value="" style="display: inline" id="tn" name="toName" class="span2"/>
						<input type="text" value="" style="display: none" id="tm" name="toTel" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">约见状态：</label>
						<select name="status"  style="width: 120px;">
							<option value="">全部</option>
							<option value="待约见">待约见</option>
							<option value="">进行中</option>
							<option value="">已完成</option>
						</select>
					</span>
				</div>
				<div class="search-toolbar">
					<span style="float: left;">
						
						<shiro:hasPermission name="daka:delete">
					   		<a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
								href="appointment/del.do" target="selectedTodo"  title="确定取消?" warn="至少勾选一条记录">取消约见</a>
				       </shiro:hasPermission>
					</span> 
		
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="submit" id="main_subimt">查询</button>&nbsp;
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
		$('#<%=request.getParameter("rel")%>_datagrid').datagrid({
				url : "appointment/query.do?isDk=1", 
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						 {
								field : "from_name",
								title : "发起人姓名",
								width : $(this).width() * 0.15,
								align : "center"
							},
						 {
								field : "from_mobile",
								title : "发起人手机号",
								width : $(this).width() * 0.15,
								align : "center",
						},
						{
							field : "to_name",
							title : "被约见人",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "to_mobile",
							title : "被约见人手机号",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "status",
							title : "约见状态",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "create_time",
							title : "创建时间",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "start_time",
							title : "约见开始时间",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "duration",
							title : "实际时长",
							width : $(this).width() * 0.15,
							align : "center",
						}
						
				] ],
				
			});
	}
	
	//编辑
	function addDk(){
		var params = { closed: false,cache: false,modal:true,width:1000,height:600,collapsible:false,minimizable:false,maximizable:false};
		MUI.openDialog('新增大咖','daka/addPage.do?rel=userList',"dakaaddpage",params);
	}
	$(function() {
		$("#from").change(function(){
			if("发起人姓名"==$("#from").val()){
				document.getElementById("fn").style.display = "inline";
				document.getElementById("fm").style.display = "none";
				document.getElementById("fm").value = "";
				
			}else if("发起人手机号"==$("#from").val()){
				document.getElementById("fm").style.display = "inline";
				document.getElementById("fn").style.display = "none";
				document.getElementById("fn").value = "";
				
			}
		});
		$("#to").change(function(){
			if("被约见人姓名"==$("#to").val()){
				document.getElementById("tn").style.display = "inline";
				document.getElementById("tm").style.display = "none";
				document.getElementById("tm").value = "";
				
			}else if("被约见人手机号"==$("#to").val()){
				document.getElementById("tm").style.display = "inline";
				document.getElementById("tn").style.display = "none";
				document.getElementById("tn").value = "";
				
			}
		});
	});
</script>