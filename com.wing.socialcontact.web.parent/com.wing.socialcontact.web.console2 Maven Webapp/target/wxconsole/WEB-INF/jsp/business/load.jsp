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
						<label style="width: 100px;">合作标题：</label>
						<input	type="text" name="titles" class="span2"/>
					</span>
					<span><label style="width: 100px;">合作类别：</label>
					<select name="bizType"  id="bizType" style="width: 120px;">
					    <option  value="0" >全部</option>
						<c:forEach var="c" items="${list}">
						<option  value="${c.id}">${c.className}</option>
						</c:forEach>	
					</select>
					</span>
					<span><label style="width: 100px;">合作状态：</label>
					<select name="status"  style="width: 120px;">
			    	<option  value="0" >全部</option>
						<option  value="1">审核中</option>
						<option  value="2">审核成功</option>
						<option  value="3">审核失败</option>
					</select>
					</span>
					<span><label style="width: 100px;">创建时间：</label>
					<input type="text" name="startTimef" id="stime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'etime\')}'})"  style="width:120px;"  />  
						至
					<input type="text" name="endTimef" id="etime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'stime\')}'})" style="width:120px;" />
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;">
					  <shiro:hasPermission name="business:delete">
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="business/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
				       </shiro:hasPermission>
				      <shiro:hasPermission name="business:update">
					   <a  class="easyui-linkbutton" plain="true"
						href="business/updateStatus.do" target="selectedTodo"  title="确定该操作吗?" warn="至少勾选一条记录">审核失败</a>
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
  <div id="south" data-options="region:'south',title:'',split:true" style="height:240px;" border="false">
    	<div id="tt" class="easyui-tabs" data-options="fit:true" style="width:auto;height:auto;" border="false">
		    <div id="businessDisscuss" title="合作商洽列表" style="overflow:hidden;width: auto;height:auto;">
		    	<div id="businessDisscuss_toolbar" class="search-div">
					
				</div>
				<table id="businessDisscuss_datagrid" toolbar="#businessDisscuss_toolbar"></table>
		    </div>
	    </div>
	</div>
</div>				

<script type="text/javascript">	
	$(function() {
		loadMain();			
	});
	
	function loadMain(){
		$('#<%=request.getParameter("rel")%>_datagrid').datagrid(
			{
				url : "business/query.do", 
				fitColumns: false,
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						 {
								field : "className",
								title : "合作类别",
								width : 80,
								align : "center",
						},
						{
							field : "userName",
							title : "发布人",
							width : 100,
							align : "center",
						},
						{
							field : "mobile",
							title : "发布人电话",
							width : 100,
							align : "center",
						},
						 {
								field : "titles",
								title : "合作标题",
								width : 150,
								align : "center",
						},
						{
							field : "reward",
							title : "悬赏J币",
							width : 80,
							align : "center"
						},
						{
							field : "bluePoint",
							title : "合作有效期",
							width : 180,
							align : "center",
							formatter : function(rowIndex, rowData) {
								return new Date(rowData.startTime)
										.format("yyyy-MM-dd")+"至"+new Date(rowData.endTime)
										.format("yyyy-MM-dd");
							}
						},{
							field : "status",
							title : "合作状态",
							width : 80,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.status==1){
									str = "审核中";
								}else if(rowData.status==2){
									str = "审核成功";
								}else if(rowData.status==3){
									str = "审核失败";
								}
								return str;
							}
						},{
							field : "isRecommend",
							title : "推荐",
							width : 80,
							align : "center",
							formatter : function(rowIndex, rowData)  {
								var str = "";
								if(rowData.isRecommend==1){
									str = "推荐";
								}else if(rowData.isRecommend==2){
									str = "不推荐";
								}
								return str;
							}
						},{
							field : "appealType",
							title : "诉求分类",
							width : 60,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.appealType==1){
									str = "供给";
								}else if(rowData.appealType==2){
									str = "需求";
								}
								return str;
							}
						},
						{
							field : "createTime",
							title : "创建时间",
							width : 130,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm");
							}
						},
						{
							field : "op",
							title : "操作",
							align:"center",
							width : 80,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = showviewb('"+rowData.id+"','"+rowData.rewardFinish+"')>编辑</a>|<a href = 'javaScript:void(0)' onclick = showviewbusiness('"+rowData.id+"')>查看</a>";
							}
						}
						
				] ],
				onClickRow:function(rowIndex, rowData){
					//给下面的控件datagrid赋值
					var orderId=rowData.id;
					$('#${param.rel}_datagrid').datagrid("clearChecked");
					$("#center").find("tr[datagrid-row-index="+rowIndex+"]").find(":checkbox").click();
					load_businessDisscussList(orderId);
				}
			});
	}
	var load_businessDisscussList=function(id){
		$('#businessDisscuss_datagrid').datagrid(
				{
					url : "business/queryBD.do?fkId="+id,
					fitColumns: false,
					columns : [ [
							{
								field:"ck",
								title : "勾选",
								checkbox:true
							},
							 {
									field : "titles",
									title : "合作标题",
									width :200,
									align : "center",
							},
							{
								field : "fbUserName",
								title : "发布人",
								width : 120,
								align : "center",
							},
							{
								field : "fbMobile",
								title : "发布人电话",
								width : 120,
								align : "center",
							},
							 {
									field : "content",
									title : "商洽内容",
									width : 300,
									align : "center",
							},
							{
								field : "userName",
								title : "商洽人",
								width : 100,
								align : "center",
							},{
								field : "mobile",
								title : "商洽人电话",
								width : 100,
								align : "center",
							},
							{
								field : "isShow",
								title : "商洽状态",
								width :100,
								align : "center",
								formatter: function(rowIndex, rowData){
									var str = "";
									if(rowData.isShow==1){
										str = "<a href = 'javaScript:void(0)' onclick = isShow('"+rowData.id+"','2')>不显示</a>";
									}else if(rowData.isShow==2){
										str = "<a href = 'javaScript:void(0)' onclick = isShow('"+rowData.id+"','1')>显示</a>";
									}
									return str;
								}
							},
							{
								field : "createTime",
								title : "创建时间",
								width : 120,
								align : "center",
								formatter : function(value, row, index) {
									return new Date(value)
											.format("yyyy-MM-dd HH:mm");
								}
							},
							{
								field : "op",
								title : "操作",
								align:"center",
								width :100,
								formatter: function(rowIndex, rowData){
									return  "<a href = 'javaScript:void(0)' onclick = showviewbd('"+rowData.id+"')>编辑</a>";
								}
							}
							
					] ],
					onDblClickRow:function(rowIndex, rowData){
						
					},
					onClickRow:function(rowIndex, rowData){
						
					}
				});
	}
	function showviewb(fid,rf){
		if(rf=='3'){
			Msg.alert("提示","该合作已过期,不能修改！","warning",null);
			return;
		}
		MUI.openDialog('编辑','business/updatePage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:650,height:550});
	}
	function showviewbusiness(fid){
		MUI.openDialog('查看','business/viewPage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_show',{width:800,height:550});
	}
	//显示
	function isShow(fid,rec){
		var str = "";
		if(rec=='1'){
			str = "确定要显示吗？<br/>";
		}else if(rec=='2'){
			str = "确定取消显示吗？<br/>";
		}
		Msg.confirm('提示',str, function(r){
	          if (r){
	        	  var pm = {"id":fid,"isShow":rec};
					var dg = $('#businessDisscuss_datagrid');
					selectedTodo_doPost("business/updateIsShow.do",pm,dg,'');
	          }
	     });
	}
</script>














