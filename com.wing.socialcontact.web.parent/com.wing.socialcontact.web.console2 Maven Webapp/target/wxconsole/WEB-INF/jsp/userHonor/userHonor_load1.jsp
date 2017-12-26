<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
	
<div id="cc2" class="easyui-layout" data-options="fit:true">  
    <div id='center' data-options="region:'center',title:'',split:true" style="" border="false">			
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">用户姓名：</label>
						<input	type="text" name="true_name" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">注册手机号：</label>
						<input	type="text" name="mobile" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">认证手机号：</label>
						<input	type="text" name="recon_mobile" class="span2"/>
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
					<span>
					  	<label style="width: 100px;">审核状态：</label>
					  	<select name="reconStatus"  style="width: 120px;">
						<option value="">全部</option>
						<option value="1">提交审核</option>
						<option value="2">审核通过</option>
						<option value="3">审核不通过</option>
						</select>
					</span>
				</div>

				<div class="search-toolbar">
					<!-- <span style="float: left;"> 
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="userhonor/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
						</span>  -->
			
					 
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
    var pid = ""; 
    var userHonorId="";
	$(function() {
		
		loadMain();			
	});
	
	function loadMain(){
		$('#<%=request.getParameter("rel")%>_datagrid').datagrid({
				url : "userhonor/querySh.do", 
				columns : [ [
						 {
								field : "true_name",
								title : "用户姓名",
								width : $(this).width() * 0.15,
								align : "center"
						 },
						 {
								field : "com_name",
								title : "公司名称",
								width : $(this).width() * 0.15,
								align : "center",
						},
						{
							field : "job_name",
							title : "职位",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "industry_name",
							title : "行业",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "mobile",
							title : "注册手机号",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "recon_mobile",
							title : "认证手机号",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "province_name",
							title : "地区",
							width : $(this).width() * 0.15,
							align : "center",
							formatter: function(value,row,index){
								if(row.province_name!=null&&row.city_name!=null){
									return row.province_name+row.city_name;
								}else if(row.province_name!=null){
									return row.province_name;
								}else if(row.city_name!=null){
									return row.city_name;
								}
							}
						},
						
						//{
						//	field : "address",
						//	title : "具体地址",
						//	width : $(this).width() * 0.15,
						//	align : "center"
						//},
						{
							field : "recon_capital",
							title : "注册资金",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								if(value!=null){
									return value+"万元";
								}else{
									return "";
								}
								
							}
						},
						{
							field : "tj_recon_date",
							title : "认证时间",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								if(value!=null){
									return new Date(value).format("yyyy-MM-dd");
								}else{
									return "";
								}
								
							}
						},
						{
							field : "last_reg_date",
							title : "认证到期时间",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								if(value!=null){
									return new Date(value).format("yyyy-MM-dd");
								}else{
									return "";
								}
								
							}
						},
						{
							field : "recon_status",
							title : "审核状态",
							width:$(this).width() * 0.1,
							align : "center",
							formatter: function(value,row,index){
								if (value=='0') {
									value="未提交";
								}else if (value=='1') {
									value="提交审核";
								}else if (value=='2') {
									value="审核通过";
								}else if (value=='3') {
									value="审核不通过";
								}else{
									value="未提交";
								}
								return value;
							}
						},
						{
							field : "op",
							title : "操作",
							width:$(this).width() * 0.1,
							align : "center",
							formatter: function(value,row,index){
								return '<a href="userhonor/view.do?id='+row.id+'&rel=<%=request.getParameter("rel")%>" target="dialog" width="1000" height="550"  rel="<%=request.getParameter("rel")%>_show"  title="详情" >查看</a>';
							}
						}
						
						
				] ],
				onSelect : function(index, row){
					pid = row.id;
					/* loadUserHonor();
					loadUserFriends();
					loadUserGroups(); */
				}
				
			});
	}
	
	

</script>














