<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%--
	模块：系统管理--组织机构 -- 部门管理--查找带回(单选)
--%>

<div class="easyui-tabs" fit="true" border="false" >
	<div   title="活动" 	style="padding:2px; ">
			<div id="${param.rel}_toolbar2" class="search-div">
				<form  onsubmit="return datagridSearch(this,'${param.rel }_activity');"  >
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
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
					</span>
		
				</div>
			</form>
				</form>
			</div>
			<table id="${param.rel }_activity"   toolbar="#${param.rel}_toolbar2" ></table>
	</div>
</div>
<script type="text/javascript">
<!--
	$(function(){
		loadactivity();
	});
	function loadactivity(){
		$('#<%=request.getParameter("rel")%>_activity').datagrid(
			{
				url : "activity/query.do", 
				columns : [ [
						{
							field : "选择",
							title : "选择",
							align : "center",
							width : $(this).width() * 0.05,
							formatter : function(value, row, index) {
								return "<a href='javascript:;' onclick=$.bringBack({id:'" + row.id + "',name:'" + row.titles + "'})>选择</a>";
							}
						},
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
								width : $(this).width() * 0.1,
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
								str+="<a href = 'javaScript:void(0)' onclick = viewPage('"+rowData.id+"')>查看</a>";
								return str;
							}
						}
				] ],
			});
	}
	
//-->
</script>
