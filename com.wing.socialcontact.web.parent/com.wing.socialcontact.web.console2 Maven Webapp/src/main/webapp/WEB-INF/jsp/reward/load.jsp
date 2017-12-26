<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div id="cc" class="easyui-layout" data-options="fit:true">  
    <div id='center' data-options="region:'center',title:'',split:true" border="false">					
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span><label style="width: 100px;">行业：</label>
					<select name="voType"  id="voType" style="width: 120px;">
					    <option  value="0" >全部</option>
						<c:forEach var="c" items="${list}">
						<option  value="${c.id}">${c.className}</option>
						</c:forEach>	
					</select>
					</span>
					<span>
						<label style="width: 100px;">问题：</label>
						<input	type="text" name="question" class="span2"/>
					</span>
					<span><label style="width: 100px;">有效期：</label>
					<input type="text" name="startTimeyx"  id="stime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'etime\')}'})"  style="width:120px;"  />  
						至
					<input type="text" name="endTimeyx"    id="etime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'stime\')}'})"   style="width:120px;" />
					</span>
					<br/>
					<span><label style="width: 100px;">状态：</label>
					<select name="status"  style="width: 120px;">
			    	<option  value="0" >全部</option>
						<option  value="1">待审核</option>
						<option  value="2">审核通过</option>
						<option  value="3">取消</option>
					</select>
					</span>
					<span><label style="width: 100px;">创建时间：</label>
					<input type="text" name="startTimef"  id="sftime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'eftime\')}'})"  style="width:120px;"  />  
						至
					<input type="text" name="endTimef"   id="eftime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'sftime\')}'})"   style="width:120px;" />
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
						<shiro:hasPermission name="business:delete">
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="reward/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
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
		    <div id="rewardAnswer" title="应答列表" style="overflow:hidden;width: auto;height:auto;">
		    	<div id="rewardAnswer_toolbar" class="search-div">
					<form onsubmit="return datagridSearch(this,'rewardAnswer_datagrid');"  formatFilterData="true">
						<div class="search-toolbar" style="display: none;">
							
							<span style="float:right;display: none;">
								<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
								<button class="btn btn-small clear" type="button">清空</button>&nbsp;
							</span>
						</div>
					</form>
				</div>
				<table id="rewardAnswer_datagrid" toolbar="#rewardAnswer_toolbar"></table>
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
				url : "reward/query.do", 
				fitColumns: false,
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						 {
								field : "className",
								title : "行业",
								width : 80,
								align : "center",
						},{
							field : "question",
							title : "问题",
							width : 200,
							align : "center",
						},
						{
							field : "nickname",
							title : "发布人",
							width : 100,
							align : "center",
						},
						{
							field : "mobile",
							title : "发布人电话",
							width : 100,
							align : "center",
						},{
							field : "status",
							title : "状态",
							width : 80,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.status==1){
									str = "待审核";
								}else if(rowData.status==2){
									str = "审核通过";
								}else if(rowData.status==3){
									str = "已取消";
								}else if(rowData.status==4){
									str = "已完成";
								}else if(rowData.status==5){
									str = "已过期";
								}
								return str;
							}
						},{
								field : "countNum",
								title : "应答",
								width : 80,
								align : "center",
						},
						{
							field : "reward",
							title : "悬赏J币",
							width : 80,
							align : "center"
						},
						{
							field : "yxq",
							title : "悬赏有效期",
							width : 150,
							align : "center",
							formatter : function(rowIndex, rowData) {
								return new Date(rowData.startTime)
										.format("yyyy-MM-dd")+"至"+new Date(rowData.endTime)
										.format("yyyy-MM-dd");
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
								return  "<a href = 'javaScript:void(0)' onclick = showviewb('"+rowData.id+"','"+rowData.status+"')>编辑</a>&nbsp;&nbsp;<a href = 'javaScript:void(0)' onclick = showviewr('"+rowData.id+"')>查看</a>";
							}
						}
						
				] ],
				
				onClickRow:function(rowIndex, rowData){
					//给下面的控件datagrid赋值
					var orderId=rowData.id;
					$('#${param.rel}_datagrid').datagrid("clearChecked");
					$("#center").find("tr[datagrid-row-index="+rowIndex+"]").find(":checkbox").click();
					load_rewardAnswerList(orderId);
				}
			});
	}
	var load_rewardAnswerList=function(id){
		$('#rewardAnswer_datagrid').datagrid(
				{
					url : "reward/queryRA.do?fkId="+id,
					fitColumns: false,
					columns : [ [
				             {
							    	field:"ck",
							    	title : "勾选",
							    	checkbox:true
							 },
							 {
									field : "question",
									title : "问题",
									width :200,
									align : "center",
							},
							{
								field : "fbUserName",
								title : "发布人",
								width : 120,
								align : "center",
							},{
								field : "fbMobile",
								title : "发布人电话",
								width : 100,
								align : "center",
							},
							 {
									field : "content",
									title : "应答",
									width : 300,
									align : "center",
							},
							{
								field : "userName",
								title : "应答人",
								width : 100,
								align : "center",
							},{
								field : "mobile",
								title : "应答人电话",
								width : 100,
								align : "center",
							},
							{
								field : "createTime",
								title : "应答时间",
								width : 100,
								align : "center",
								formatter : function(value, row, index) {
									return new Date(value)
											.format("yyyy-MM-dd");
								}
							},{
								field : "isAccept",
								title : "应答状态",
								width :100,
								align : "center",
								formatter: function(rowIndex, rowData){
									var str = "";
									if(rowData.isAccept==1){
										str = "已采纳";
									}else if(rowData.isAccept==2){
										str = "未采纳";
									}
									return str;
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
					
				});
	}
	function showviewr(fid){
		MUI.openDialog('详情','reward/showPage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_show',{width:800,height:550});
	}
	function showviewbd(fid){
		MUI.openDialog('编辑','reward/updatePageRA.do?id='+fid+'&rel=rewardAnswer_datagrid','rewardAnswer_datagrid_update',{width:650,height:450});
	}
	function showviewb(fid,stu){
		if(stu=='5'){
			Msg.alert("提示","该悬赏已过期,不能修改！","warning",null);
			return;
		}
		if(stu=='3'){
			Msg.alert("提示","该悬赏已取消,不能修改！","warning",null);
			return;
		}
		if(stu=='4'){
			Msg.alert("提示","该悬赏已完成,不能修改！","warning",null);
			return;
		}
		MUI.openDialog('修改','reward/updatePage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:650,height:550});
	}
</script>














