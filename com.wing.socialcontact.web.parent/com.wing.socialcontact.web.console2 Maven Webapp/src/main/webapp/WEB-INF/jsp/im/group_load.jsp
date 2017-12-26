<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% 
	String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.getSession().setAttribute("path", path);
%>
	
<div id="cc" class="easyui-layout" data-options="fit:true">  
    <div id='center' data-options="region:'center',title:'',split:true" border="false">
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 80px;">创建人：</label>
						<input type="text" id="userId" name="userId" style="width: 100px;"/>
					</span>
					<span>
						<label style="width: 80px;">群名称：</label>
						<input type="text" id="groupName" name="groupName" style="width: 100px;"/>
					</span>
					<span>
						<label style="width: 80px;">群人数：</label>
						<input type="text" id="userCount"  name="userCount" style="width: 100px;"/>
					</span>
					<span>
						<label style="width: 80px;">私密群：</label>
						<select id="groupType" name="groupType" style="width: 100px;">
							<option value="" selected="selected">全部</option>
							<option value="2" >是</option>
							<option value="1" >否</option>
						</select>
					</span>
					<span><label style="width: 100px;">创建时间：</label>
					<input type="text" name="startTime"    id="stime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'etime\')}'})"   style="width:120px;"  />  
						至
					<input type="text" name="endTime"     id="etime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'stime\')}'})"   style="width:120px;" />
					</span>
				</div>
			
				<div class="search-toolbar">
					<span style="float:left;">
					<shiro:hasPermission name="im:add">
						<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="im/addPage.do?rel=${param.rel }" title="新增" target="dialog"
								width="800" height="600" rel="${param.rel}_add"><span>新增</span>
							</a>
							 </shiro:hasPermission>
							<shiro:hasPermission name="im:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="im/updatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="800" height="600"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
							 </shiro:hasPermission>
							<shiro:hasPermission name="im:delete">
							 <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						    href="im/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
						     </shiro:hasPermission>
					</span>
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
					</span>
				</div>
			</form>
		</div>
		<table id="${param.rel }_datagrid" toolbar="#${param.rel}_toolbar"></table>	
	</div>
	<div id="south" data-options="region:'south',title:'',split:true" style="height:280px;" border="false">
    	<div id="tt" class="easyui-tabs" data-options="fit:true" style="width:auto;height:auto;" border="false">
		    <div id="groupUser" title="群成员" style="overflow:hidden;width: auto;height:auto;">
		    	
		    	<div id="groupUser_toolbar" class="search-div">
					<form onsubmit="return datagridSearch(this,'groupUser_datagrid');"  >
					<div class="search-content">
						<span>
							<label style="width: 100px;">用户姓名：</label>
							<input	type="text"  id="nickname" name="nickname" class="span2"/>
						</span>
						<span>
							<label style="width: 100px;">公司名称：</label>
							<input	type="text"  id="comName" name="comName" class="span2"/>
						</span>
						<span>
							<label style="width: 100px;">等级：</label>
							<select id="level" name="level">
									<option value=""></option>
									<c:forEach items="${userLevelList }" var="t">
									<option value="${t.level }">${t.level }</option>
									</c:forEach>
							</select>
						</span>
						<span>
							<label style="width: 100px;">注册手机号：</label>
							<input	type="text"   id="mobile"  name="mobile" class="span2"/>
						</span>
						<span>
							<label style="width: 100px;">职位：</label>
							<select id="jobName"  name="jobName"  style="width: 120px;">
							<option value="">全部</option>
							<c:forEach var="c" items="${jobs}">
							<option  value="${c.id}">${c.listValue}</option>
							</c:forEach>	
						</select>
						</span>
						<span>
							<label style="width: 100px;">群身份：</label>
							<select id="affiliations"  name="affiliations"  style="width: 120px;">
							<option value=""></option>
							<option value="owner">群主</option>
							<option value="member">群成员</option>
						
						</select>
						</span>
						<!-- <span>
							<label style="width: 100px;">行业：</label>
							<select id="industryName"   name="industryName"  style="width: 120px;">
							<option value="">全部</option>
							<c:forEach var="c" items="${industrys}">
							<option  value="${c.id}">${c.listValue}</option>
							</c:forEach>
							</select>
						</span> -->
					</div>
						<div class="search-toolbar">
							 <span style="float:left;">
								<a class="easyui-linkbutton" icon="icon-add" plain="true" href="im/addUsersPage.do?rel=groupUser" target="dialog" width="1000" height="550" rel="groupUser_add" warn="请先选择一条主表记录" p_datagrid="${param.rel }_datagrid" title="新增成员" type="2">新增成员</a>
							</span> 
							<span style="float:right;">
								<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
								<button class="btn btn-small clear" type="button">清空</button>&nbsp;
							</span>
						</div>
					</form>
				</div>
				<table id="groupUser_datagrid" toolbar="#groupUser_toolbar" ></table>
		    </div>
	    </div>
	</div>
</div>
<script type="text/javascript">	
	$(function() {
		loadMain();			
	});
	var groupId='';
	var groupId='';
	
	
	//获取用户群组
	function loadMain(){
		$('#<%=request.getParameter("rel")%>_datagrid').datagrid({
			url : "im/queryGroupList2.do", 
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
				field : "nickname",
				title : "创建人",
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
			},
			{
				field : "group_power",
				title : "权重",
				width : $(this).width() * 0.15,
				align : "center"
			},
			{
				field : "create_time",
				title : "创建时间",
				width : $(this).width() * 0.15,
				align : "center",
				formatter : function(value, row, index) {
					return new Date(value).format("yyyy-MM-dd HH:mm");
				}
			},
			{
				field : "favs",
				title : "群标签",
				width : $(this).width() * 0.15,
				align : "center",
			},
			{
				field : "op",
				title : "操作",
				align:"center",
				width : $(this).width() * 0.15,
				formatter: function(rowIndex, rowData){
					return  "<a href = 'javaScript:void(0)' onclick = showviewgroup('"+rowData.id+"')>查看</a>";
				}
			}
			
			] ],
			onClickRow:function(rowIndex, rowData){
				//给下面的控件datagrid赋值
				var pageId=rowData.id;
				$('#${param.rel}_datagrid').datagrid("clearChecked");
				$("#center").find("tr[datagrid-row-index="+rowIndex+"]").find(":checkbox").click();
				$("#south").find("a[icon='icon-add']").each(function(){
					var _href=$(this).attr("href");
					if(_href.indexOf("pid=")!=-1){
						_href=_href.substring(0,_href.indexOf("pid="));
						$(this).attr("href",_href+"&pid="+pageId);
					}else{
						$(this).attr("href",_href+"&pid="+pageId);
					}
				});
				  groupId=rowData.id;
				  load_groupUser(groupId);
			}
		});
	}
	function showviewgroup(fid){
		MUI.openDialog('查看详情','im/viewPage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:800,height:400});
	}
	var load_groupUser=function(groupId){
		$('#groupUser_datagrid').datagrid({
			url : "im/querygroupUserList.do?groupId="+groupId,
			queryParams: { 
				nickname: $("#nickname").val(),
				comName: $("#comName").val(),
				jobName: $("#jobName").val(),
				cityName: $("#cityName").val(),
				industryName: $("#industryName").val(),
				mobile: $("#mobile").val()
			},
			frozenColumns:[[
				             {
							    	field:"ck",
							    	title : "勾选",
							    	checkbox:true
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
								field : "jobName",
								title : "职位",
								width : 80,
								align : "center"
							}  
			            ]],  
						columns : [ [
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
							    },
							    {
									field : "level",
									title : "等级",
									width : 100,
									align : "center"
								},
								 {
									field : "integral_total",
									title : "积分",
									width : 100,
							 		align : "center",
									formatter: function(value,row,index){
										if(null == value){
											return "0";
										}else if(0 == value){
											return "0";
										}else{
											return value;
										}
									}
							    },
								 {
									field : "affiliations",
									title : "群身份",
									width : 100,
							 		align : "center",
									formatter: function(value,row,index){
										if('member' == value){
											return " 群成员";
										}else if('owner' == value){
											return "群主";
										}else{
											return value;
										}
									}
							    },
							    {
									field : "create_time",
									title : "进群时间",
									width : 100,
									align : "center",
									formatter : function(value, row, index) {
										return new Date(row.create_time)
												.format("yyyy-MM-dd HH:mm");
									}
								},
								{
									field : "op",
									title : "操作",
									align:"center",
									width : 80,
									formatter: function(rowIndex, rowData){
										if('member' == rowData.affiliations){
											return "<a href = 'javaScript:void(0)' onclick = delUser('"+rowData.user_id+"')>删除</a>";
										}else if('owner' == rowData.affiliations){
											return "不可删除";
										}else{
											return "<a href = 'javaScript:void(0)' onclick = delUser('"+rowData.user_id+"')>删除</a>";
										}
										
										 
									}
								}
							    
							    
							    
							   
							   
								
								
						] ],
			onDblClickRow:function(rowIndex, rowData){
				///MUI.openDialog('修改快捷入口','im/pageQuickEntry/updatePage.do?id='+rowData.id+'&rel=pageQuickEntry','pageQuickEntry_update',{width:800,height:420});
			},
			onClickRow:function(rowIndex, rowData){
				///$("#pageQuickEntry").find("tr[datagrid-row-index="+rowIndex+"]").find(":checkbox").click();
			}
		});
	};
	
	
	
	function delUser(id){
		
		Msg.confirm("提示","是否删除该条记录？",function(t){
			if(t){
				$.ajax({
					url:"im/delUser.do",
					data:{groupId:groupId,userIds:id},
					type: "post",	
					cache: false,
					dataType:"json",
					success:function(json){
						if(json&&json["code"]==="0"){
							Msg.topCenter({
								title:"提示",
								msg:'成功删除!',
								msgType:"success"
							});
							 load_groupUser(groupId);
						}else{
							Msg.topCenter({
								title:"提示",
								msg:'删除失败!',
								msgType:"warning"
							});
						}
					}
					
				});
			}
		});
	}
</script>