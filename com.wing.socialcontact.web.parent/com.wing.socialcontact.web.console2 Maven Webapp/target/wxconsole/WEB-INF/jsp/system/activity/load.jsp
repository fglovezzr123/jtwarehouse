<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
				<span>
					<label style="width: 100px;">活动主题：</label>
					<input type="text" name="titles"/>
				</span>
				<span>
					<label style="width: 100px;">活动方式：</label>
					<select name="pattern">
						<option value="0">参与方式</option>
						<option value="1">线下参与</option>
						<option value="2">线上参与</option>
					</select>
				</span>
				<span>
					<label style="width: 100px;">活动分类：</label>
					<select name="classId">
						<option value="">活动分类</option>
						<option value="1">以玩会友</option>
						<option value="2">以书会友</option>
					</select>
				</span>
				<span>
					<label style="width: 100px;">活动标签：</label>
					<select name="tag">
						<option value="">所有</option>
						<c:forEach items="${tag }" var="t">
						<option value="${t.id }">${t.name }</option>
						</c:forEach>
					</select>
				</span>
				<span>
					<label style="width: 100px;">是否显示：</label>
					<select name="showEnable">
						<option value="" >全部</option>
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</span>
				<span>
					<label style="width: 100px;">创建时间：</label>
					<input type="text"   name="createTime"   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 150px;"/>
					
				</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
						<shiro:hasPermission name="activity:add">
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="activity/addPage.do?rel=${param.rel }" title="新增" target="dialog"
								width="800" height="600" rel="${param.rel}_add"><span>新增</span>
							</a>
						</shiro:hasPermission> 
						<%-- <shiro:hasPermission name="activity:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="activity/updatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="800" height="600"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
						</shiro:hasPermission> --%>
						<shiro:hasPermission name="activityuser:read">
							<a  class="easyui-linkbutton"  icon="icon-edit"	plain="true"  id="updatestatus"
								href="activityUser/load.do?id={id}&rel=${param.rel}_a" target="dialog"  rel="${param.rel}_read" title="报名用户" warn="选择一条信息">报名用户</a>
						</shiro:hasPermission>
						<%-- <shiro:hasPermission name="activity:delete">
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="activity/del.do" target="selectedTodo"  title="确定要删除吗?" warn="请至少选择一条记录">批量删除</a>
				       </shiro:hasPermission> --%>
						</span> 
		
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
				url : "activity/query.do", 
				columns : [ [
						/* {
							field:"ck",
							title : "勾选",
							checkbox:true
						}, */   
						{
								field : "titles",
								title : "活动标题",
								width : $(this).width() * 0.2,
								align : "center",
						},
						{
								field : "createUserName",
								title : "发起者",
								width : $(this).width() * 0.1,
								align : "center",
						},
						{
								field : "mobile",
								title : "发起者电话",
								width : $(this).width() * 0.1,
								align : "center",
						},
						{
								field : "sort",
								title : "权重",
								width : 70 ,
								align : "center"
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
								field : "pattern",
								title : "活动方式",
								width : $(this).width() * 0.1,
								align : "center",
								formatter : function(value, row, index) {
									switch(value){
									 case  1 :
									 		return "线下参与";
									 case  2 :
									 		return "线上参与";
									}
								}
						},
						{
							field : "tagName",
							title : "活动标签",
							width : $(this).width() * 0.1,
							align : "center"
						},
						{
							field : "times",
							title : "活动开始时间",
							width : 320,
							align : "center",
							formatter : function(rowIndex, rowData) {
								return new Date(rowData.startTime).format("yyyy-MM-dd HH:mm")+"至"+
								new Date(rowData.endTime).format("yyyy-MM-dd HH:mm");
							}
						},
						{
							field : "status",
							title : "活动状态",
							width : $(this).width() * 0.1,
							align : "center",
							formatter : function(rowIndex, rowData) {
								
								switch(rowData.status){
								 case  1 :
								 		return "未审核";
								 case  2 : 
								 		return "报名中";
								 case  3 :
								 		return "报名结束";
								 case  4 :
								 		return "进行中";
								 case  5 :
								 		return "已结束";
								 case  6 :
								 		return "已取消";
								}
							}
						},
						{
							field : "showEnable",
							title : "显示",
							width : $(this).width() * 0.05,
							align : "center",
							formatter : function(value, row, index) {
								
								switch(value){
								 case  0 :
								 		return "否";
								 case  1 :
								 		return "是";
								}
							}
						},
						{
							field : "recommendEnable",
							title : "推荐首页",
							width : $(this).width() * 0.05,
							align : "center",
							formatter : function(value, row, index) {
								
								switch(value){
								 case  0 :
								 		return "否";
								 case  1 :
								 		return "是";
								}
							}
						},
						{
							field : "recommendList",
							title : "推荐列表",
							width : $(this).width() * 0.05,
							align : "center",
							formatter : function(value, row, index) {
								
								switch(value){
								 case  0 :
								 		return "否";
								 case  1 :
								 		return "是";
								}
							}
						},
						{
							field : "createTime",
							title : "创建时间",
							width : $(this).width() * 0.1,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm:ss");
							}
						},
						{
							field : "op",
							title : "操作",
							width : $(this).width() * 0.2,
							align : "center",
							formatter : function(rowIndex, rowData) {
								var status= rowData.status;//活动状态
								var isdelay= rowData.isdelay;//延期申请
								var iscancel= rowData.iscancel;//取消申请
								var iscod=rowData.iscod;//是否结算
								var str="";
								if(status==1){
									str+="<a href = 'javaScript:void(0)' onclick = checkactivity('"+rowData.id+"',2)>同意</a>"+
										"&nbsp;&nbsp;&nbsp;<a href = 'javaScript:void(0)' onclick = updateactivity('"+rowData.id+"')>编辑</a>"+
										"&nbsp;&nbsp;&nbsp;<a href = 'javaScript:void(0)' onclick = cancelactivity('"+rowData.id+"')>取消</a>";
								}else if(status==2||status==3||status==4){
									str+="<a href = 'javaScript:void(0)' onclick = checkactivity('"+rowData.id+"',1)>不同意</a>"+
										"&nbsp;&nbsp;&nbsp;<a href = 'javaScript:void(0)' onclick = updateactivity('"+rowData.id+"')>编辑</a>"+
										"&nbsp;&nbsp;&nbsp;<a href = 'javaScript:void(0)' onclick = cancelactivity('"+rowData.id+"')>取消</a>";
								}else if(status==5){
									str+="<a href = 'javaScript:void(0)' onclick = updateactivity('"+rowData.id+"')>编辑</a>";
								}else if(status==6){
									str+="<a href = 'javaScript:void(0)' onclick = updateactivity('"+rowData.id+"')>打开</a>";
								}
								
								str+="&nbsp;&nbsp;&nbsp;<a href = 'javaScript:void(0)' onclick = viewPage('"+rowData.id+"')>查看</a>";
								
								return str;
							}
						}
						
				] ],
				
			});
	}
	
	function updateactivity(fid){
		MUI.openDialog('编辑活动','activity/updatePage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:800,height:600});
	}
	function viewPage(fid){
		MUI.openDialog('查看活动','activity/viewPage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_view',{width:800,height:600});
	}
	function checkcancel(fid){
		MUI.openDialog('取消审核','activity/cancelPage.do?acid='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_cancel',{width:600,height:700});
	}
	function checkdelay(fid){
		MUI.openDialog('延期审核','activity/delayPage.do?acid='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_delay',{width:600,height:700});
	}
	function deleteactivity(id){
		Msg.confirm('提示',"确定要删除吗？<br/>", function(r){
	          if (r){
				var pm = {"id":id};
				var dg = $('#<%=request.getParameter("rel")%>_datagrid');
				selectedTodo_doPost("activity/del.do",pm,dg,'');
	          }
	     });
	}
	function delay(id,type){
				var pm = {"id":id,"type":type};
				var dg = $('#<%=request.getParameter("rel")%>_datagrid');
				selectedTodo_doPost("activity/delay.do",pm,dg,'');
	}
	function checkactivity(id,status){
		var str="确定要同意吗？<br/>";
		if(status==1){
			str="确定要不同意吗？<br/>";
		}
		Msg.confirm('提示',str, function(r){
	          if (r){
	        	  var pm = {"id":id,"status":status};
				  var dg = $('#<%=request.getParameter("rel")%>_datagrid');
				  selectedTodo_doPost("activity/checkactivity.do",pm,dg,'');
	          }
	     });
	}
	function cancelactivity(id){
		Msg.confirm('提示',"确定要取消活动吗？<br/>", function(r){
	          if (r){
	        	  var pm = {"id":id};
					var dg = $('#<%=request.getParameter("rel")%>_datagrid');
					selectedTodo_doPost("activity/cancelactivity.do",pm,dg,'');
	          }
	     });
				
	}
</script>














