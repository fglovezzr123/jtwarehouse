<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
	
<div id="cc" class="easyui-layout" data-options="fit:true">  
    <div id='center' data-options="region:'center',title:'',split:true" style="" border="false">			
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">用户姓名：</label>
						<input	type="text" name="nickname" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">注册手机号：</label>
						<input	type="text" name="mobile" class="span2"/>
					</span>
					<span>
						<label style="width: 120px;">app绑定手机号：</label>
						<input	type="text" name="bind_phone" class="span2"/>
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
				</div>
				<div class="search-toolbar">
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
				url : "userhonor/query.do", 
				frozenColumns:[[
		             {
							field : "选择",
							title : "选择",
							align : "center",
							width : $(this).width() * 0.05,
							formatter : function(value, row, index) {
								return "<a href='javascript:;' onclick=$.bringBack({id:'" + row.id + "',name:'" + row.nickname + "'})>选择</a>";
							}
						},
					 {
							field : "nickname",
							title : "用户姓名",
							width : 80,
							align : "center"
					 },
					 {
							field : "mobile",
							title : "注册电话",
							width : 100,
							align : "center"
					 },
					 {
							field : "com_name",
							title : "公司名称",
							width : 120,
							align : "center",
					},
					{
						field : "job_name",
						title : "职位",
						width : 80,
						align : "center"
					}  
	            ]],  
				columns : [ [
						{
							field : "province_name",
							title : "地区",
							width : 180,
							align : "center",
							formatter: function(value,row,index){
								if(row.province_name!=null&&row.city_name!=null&&row.county_name!=null){
									return row.province_name+row.city_name+row.county_name;
								}else if(row.province_name!=null&&row.city_name!=null){
									return row.province_name+row.city_name;
								}else if(row.province_name!=null){
									return row.province_name;
								}
							}
						},
					    {
							field : "addTime",
							title : "注册时间",
							width : 200,
							align : "center",
							formatter : function(value, row, index) {
								if(null == value){
									return "";
								}else{
									return new Date(value)
											.format("yyyy-MM-dd HH:mm");
								}
							}
						},
						{
							field : "op",
							title : "操作",
							width : 80,
							align : "center",
							formatter: function(value,row,index){
								return '<a href="userhonor/user_view.do?id='+row.id+'&rel=<%=request.getParameter("rel")%>" target="dialog" width="1000" height="550"  rel="<%=request.getParameter("rel")%>_show"  title="详情" >查看</a>';
							}
						}
						
						
				] ],
			});
	}
	
</script>














