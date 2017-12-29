<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
	
<div id="cc2" class="easyui-layout" data-options="fit:true">  
    <div id='center' data-options="region:'center',title:'',split:true" style="" border="false">			
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">用户姓名：</label>
						<input	type="text" name="nickname" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">手机号：</label>
						<input	type="text" name="mobile" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">职位：</label>
						<select name="job"  style="width: 120px;">
						<option value="">全部</option>
						<c:forEach var="c" items="${jobs}">
						<option  value="${c.id}">${c.listValue}</option>
						</c:forEach>	
					</select>
					</span>
					<span>
						<label style="width: 100px;">行业：</label>
						<select name="industry"  style="width: 120px;">
						<option value="">全部</option>
						<c:forEach var="c" items="${industrys}">
						<option  value="${c.id}">${c.listValue}</option>
						</c:forEach>
						</select>
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
					   <a class="easyui-linkbutton" href="walletTx/load.do?rel=tjy_wallet_tx" target="navTab" rel="tjy_wallet_tx" title="提现管理">提现管理</a>
					   &nbsp;&nbsp;&nbsp;&nbsp;
					   <a class="easyui-linkbutton" href="walletCz/load.do?rel=tjy_wallet_cz" target="navTab" rel="tjy_wallet_cz" title="充值管理">充值管理</a>
					</span>
					 
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="submit" id="main_subimt">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
					</span>
				</div>
			</form>
		</div>
		<table id="${param.rel }_datagrid" toolbar="#${param.rel}_toolbar"></table>
	</div>
</div>
				

<script type="text/javascript">	
	$(function() {
		loadMain();			
	});
	
	function loadMain(){
		$('#<%=request.getParameter("rel")%>_datagrid').datagrid({
				url : "userhonor/query.do", 
				columns : [ [
						 {
								field : "nickname",
								title : "用户姓名",
								width : 100,
								align : "center"
						 },
						 {
								field : "mobile",
								title : "手机号",
								width : 100,
								align : "center",
						 },
						 {
								field : "com_name",
								title : "公司名称",
								width : 150,
								align : "center",
						 },
						{
							field : "job_name",
							title : "职位",
							width : 80,
							align : "center"
						},
						{
							field : "availableBalance",
							title : "RMB余额",
							width : 100,
							align : "center",
							formatter: function(value,row,index){
								if(null == value){
									return "0.00";
								}else if(0 == value){
									return "0.00";
								}else{
									return value;
								}
							}
						},
						{
							field : "jb_amount",
							title : "J币余额",
							width : 100,
					 		align : "center",
							formatter: function(value,row,index){
								if(null == value){
									return "0";
								}else{
									return value;
								}
							}
					    },
					    {
							field : "hzb_amount",
							title : "互助宝余额",
							width : 100,
					 		align : "center",
							formatter: function(value,row,index){
								if(null == value){
									return "0.00";
								}else if(0 == value){
									return "0.00";
								}else{
									return value;
								}
							}
					    }
				] ]
			});
	}
</script>