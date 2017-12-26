<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
	
<div id="cc" class="easyui-layout" data-options="fit:true">  
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
	
	<div id="south" data-options="region:'south',title:'',split:true" style="height:250px;" border="false">
    	<div id="tt" class="easyui-tabs" data-options="fit:true" style="width:auto;height:auto;" border="false">
		    <div  title="用户勋章" style="overflow:hidden;width: auto;height:auto;" id="q0">
		    	<div id="${param.rel}0_toolbar" class="search-div">
			     	<div class="search-toolbar">
						<span style="float: left;"> 
							<a class="easyui-linkbutton" plain="true" icon="icon-add" href="javascript:userhonor_add()" >新增</a>
							<a class="easyui-linkbutton" plain="true" icon="icon-add" href="javascript:userhonor_update()" >修改</a>
							<a class="easyui-linkbutton" icon="icon-remove" plain="true" id="detailDelete" datagrid="${param.rel }0_datagrid"
								href="userhonor/del.do?rel=${param.rel }0" target="selectedTodo"
								rel="ids" title="确定要删除吗?" warn="至少选择一条信息">
								<span>删除</span>
							</a>
						 </span> 
						<input type="hidden" id="chanceid" name="chanceid" />
					</div>
		    	</div>
		    	<table id="${param.rel }0_datagrid" toolbar="#${param.rel}0_toolbar" border="true"></table>
		    </div>
		</div>
	</div> 
</div>
				

<script type="text/javascript">	
    var pid = ""; 
    var pid2 = ""; 
    var userHonorId="";
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
								return '<a href="userhonor/user_view.do?id='+row.id+'&rel=<%=request.getParameter("rel")%>" target="dialog" width="1000" height="550"  rel="<%=request.getParameter("rel")%>_show"  title="详情" >查看</a>';
							}
						}
						
						
				] ],
				onSelect : function(index, row){
					pid = row.id;
					loadUserHonor();
					//loadUserFriends();
					//loadUserGroups();
				}
				
			});
	}
	
	//编辑
	function addDk(){
		var params = { closed: false,cache: false,modal:true,width:1000,height:600,collapsible:false,minimizable:false,maximizable:false};
		MUI.openDialog('新增大咖','daka/addPage.do?rel=userList',"dakaaddpage",params);
	}
	
	function loadUserHonor(){
		$('#<%=request.getParameter("rel")%>0_datagrid').datagrid({
			url : "userhonor/query2.do?userId="+pid, 
			columns : [ [
			{
				field : "ck",
				title : "勾选",
				checkbox : true
			},
			{
				field : "title",
				title : "勋章",
				width : $(this).width() * 0.15,
				align : "center"
			}
			] ],
			onDblClickRow:function(rowIndex, rowData){
				///MUI.openDialog('计算得分明细','pm/kpi/KpiPingFen/defendetail.do?pid='+pid+'&id='+rowData.id+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update2',{width:1000,height:600});			
				
			},
			onSelect : function(index, row){
				userHonorId=row.id;
			}
		});
	}
	
	
	function userhonor_add(){
		if(pid == ""){
			Msg.topCenter({
				title:"提示",
				msg:'请先选择一条主数据!',
				msgType:"warning"
			});
		}else{
			MUI.openDialog('新增','userhonor/addPage.do?userId='+pid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>0_update',{width:1000,height:500}, 'loadLiangBiaodetail()');	
		}
	}
		
	function userhonor_update(){
		if(userHonorId == ""){
			Msg.topCenter({
				title:"提示",
				msg:'请先选择一条数据!',
				msgType:"warning"
			});
			//return false;
		}else{
			MUI.openDialog('修改','userhonor/updatePage.do?&id='+userHonorId+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>0_update',{width:1000,height:500}, 'loadLiangBiaodetail()');	
		}	
	}
	//获取好友列表
	function loadUserFriends(){
		$('#<%=request.getParameter("rel")%>1_datagrid').datagrid({
			url : "im/queryFriendList.do?userId="+pid, 
			rownumbers: true,
			queryParams: { 
				nickname: $("#nickname").val(),
				comName: $("#comName").val(),
				jobName: $("#jobName").val(),
				cityName: $("#cityName").val(),
				industryName: $("#industryName").val(),
				mobile: $("#mobile").val()
			},
			columns : [ [
			{
				field : "ck",
				title : "勾选",
				checkbox : true
			},
			{
					field : "nickname",
					title : "用户名称",
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
				title : "电话",
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
			}
			] ],
			onDblClickRow:function(rowIndex, rowData){
				///MUI.openDialog('计算得分明细','pm/kpi/KpiPingFen/defendetail.do?pid='+pid+'&id='+rowData.id+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update2',{width:1000,height:600});			
				
			},
			onSelect : function(index, row){
				
			}
		});
	}
	//获取用户群组
	function loadUserGroups(){
		$('#<%=request.getParameter("rel")%>2_datagrid').datagrid({
			url : "im/queryGroupList.do?userId="+pid, 
			rownumbers: true,
			queryParams: { 
				groupName: $("#groupName").val(),
				userCount: $("#userCount").val(),
				groupType: $("#groupType").val()
			},
			columns : [ [
			{
				field : "ck",
				title : "勾选",
				checkbox : true
			},
			{
				field : "group_name",
				title : "群名称",
				width : $(this).width() * 0.15,
				align : "center"
		 	},
		 	{
				field : "group_userCount",
				title : "群人数",
				width : $(this).width() * 0.15,
				align : "center",
			},
			{
				field : "group_type",
				title : "私密群",
				width : $(this).width() * 0.15,
				align : "center",
				formatter: function(value,row,index){
					if(row.group_type == 1){
						return "否";
					}else if(row.group_type == 2){
						return "是";
					}
				}
			}
			] ],
			onDblClickRow:function(rowIndex, rowData){
				///MUI.openDialog('计算得分明细','pm/kpi/KpiPingFen/defendetail.do?pid='+pid+'&id='+rowData.id+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update2',{width:1000,height:600});			
				
			},
			onSelect : function(index, row){
				userHonorId=row.id;
			}
		});
	}

</script>














