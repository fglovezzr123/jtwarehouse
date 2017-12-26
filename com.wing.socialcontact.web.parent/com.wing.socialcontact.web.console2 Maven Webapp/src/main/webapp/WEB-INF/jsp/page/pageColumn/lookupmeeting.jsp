<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%--
	模块：系统管理--组织机构 -- 部门管理--查找带回(单选)
--%>

<div class="easyui-tabs" fit="true" border="false" >
	<div   title="会议" 	style="padding:2px; ">
		<div id="${param.rel}_toolbar3" class="search-div">
			<form  onsubmit="return datagridSearch(this,'meetingindex_datagrid');"  >
			<div class="search-content">
				<span>
					<label style="width: 80px;">会议主题：</label>
					<input type="text" id="titles" name="titles"/>
				</span>
				<span>
					<label style="width: 80px;">会议类型：</label>
					<select id="types" name="types">
						<option value="">全部类型</option>
						<option value="1">线上会议</option>
						<option value="2">线下会议</option>
						<option value="3">线上会议+线下会议</option>
					</select>
				</span>
				<span>
					<label style="width: 80px;">是否显示：</label>
					<select id="showEnable" name="showEnable">
						<option value="">全部</option>
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</span>
				<span>
					<label style="width: 80px;">创建时间：</label>
					<input id="createTime" name="createTime" style="width:90px;" size=15 class="Wdate time-field" 
						onfocus="WdatePicker({readOnly:true})"
						onClick="WdatePicker({readOnly:true})" 
						readonly="readonly"/>
					
				</span>
				<span style="padding-left:10px;">
					<button class="btn btn-primary btn-small" type="button" onclick="loadMetting()">查询</button>&nbsp;
				</span>
			</div>
			</form>
		</div>
		<table id="meetingindex_datagrid"   toolbar="#${param.rel}_toolbar3" ></table>
	</div>
</div>
<script type="text/javascript">
<!--
	$(function(){
		loadMetting();
	});
	
	function loadMetting(){
		$("#meetingindex_datagrid").datagrid({
			url : "meeting/meetingquery.do",
			//rownumbers: true,
			pageList:[20,50],
			pageSize:20,
			//fitColumns: false,
			queryParams: { 
				titles: $("#titles").val(),
				types: $("#types").val(),
				showEnable: $("#showEnable").val(),
				createTime: $("#createTime").val()
			},
			columns : [[
			     
				{
					field : "选择",
					title : "选择",
					align : "center",
					width : $(this).width() * 0.05,
					formatter : function(value, row, index) {
						return "<a href='javascript:;' onclick=$.bringBack({id:'" + row.id + "',name:'" + row.titles + "'})>选择</a>";
					}
				}, {
					field : "titles",
					title : "会议主题",
					width : 190,
					align : "left",
				},{
					field : "types",
					title : "会议类型",
					width : 130,
					align : "center",
					formatter : function(value, row, index) {
						return value===1?"线上会议":(value===2?"线下会议":"线上会议+线下会议");
					}
				},{
					field : "times",
					title : "会议时间",
					width : 230,
					align : "center",
					formatter : function(value, row, index) {
						return new Date(row.startTime).format("yyyy/MM/dd HH:mm")+"至"
								+new Date(row.endTime).format("yyyy/MM/dd HH:mm");
					}
				},
				{
					field : "statusDesc",
					title : "会议状态",
					width : 60,
					align : "center"
				},
				{
					field : "sort",
					title : "权重",
					width : 60,
					align : "center"
				},
				{
					field : "recommendEnable",
					title : "推荐投融宝首页",
					width : 90,
					align : "center",
					formatter : function(value, row, index) {
						return value===1?"是":"否";
					}
				},
				{
					field : "investmentEnable",
					title : "推荐投洽峰会首页",
					width : 100,
					align : "center",
					formatter : function(value, row, index) {
						return value===1?"是":"否";
					}
				},
				{
					field : "showEnable",
					title : "是否显示",
					width : 60,
					align : "center",
					formatter : function(value, row, index) {
						return value===1?"是":"否";
					}
				},
				{
					field : "createTime",
					title : "创建时间",
					width : 130,
					align : "center",
					formatter : function(value, row, index) {
						return new Date(row.createTime)
								.format("yyyy-MM-dd HH:mm:ss");
					}
				},
				{
					field : "op",
					title : "操作",
					align:"center",
					width : 240,
					formatter: function(value, row, index){
						return   "<a href = 'javaScript:void(0)' onclick = detailMetting('"+row.id+"')>查看</a>";
					}
				}
			]],
		});
	}
	
//-->
</script>
