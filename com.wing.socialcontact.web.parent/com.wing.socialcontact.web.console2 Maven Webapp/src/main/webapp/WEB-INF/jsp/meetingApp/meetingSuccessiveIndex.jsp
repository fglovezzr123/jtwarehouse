<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div class="search-div" id="meeting_successive_toolbar">
	<div class="search-content">
		<span>
			<label style="width: 80px;">会议主题：</label>
			<input type="text" id="titles"/>
		</span>
		<span>
			<label style="width: 80px;">会议类型：</label>
			<select id="types">
				<option value="">全部类型</option>
				<option value="1">线上会议</option>
				<option value="2">线下会议</option>
				<option value="3">线上会议+线下会议</option>
			</select>
		</span>
		<span>
			<label style="width: 80px;">是否显示：</label>
			<select id="showEnable">
				<option value="">全部</option>
				<option value="1">是</option>
				<option value="0">否</option>
			</select>
		</span>
		<span>
			<label style="width: 80px;">创建时间：</label>
			<input id="createTime" style="width:90px;" size=15 class="Wdate time-field"
				   onfocus="WdatePicker({readOnly:true})"
				   onClick="WdatePicker({readOnly:true})"
				   readonly="readonly"/>
		</span>
		<span style="padding-left:10px;">
			<button class="btn btn-primary btn-small" type="button" onclick="loadMetting()">查询</button>&nbsp;
			<button class="btn btn-primary btn-small" type="button" onclick="sureMeetingSuccessive()">确定</button>&nbsp;
			<button class="btn btn-primary btn-small" type="button" onclick="closeMeetingSuccessive()">取消</button>&nbsp;
		</span>
	</div>
</div>
<table id="meeting_successive_datagrid" toolbar="#meeting_successive_toolbar"></table>
<script type="text/javascript">
	$(function() {
		loadMetting();
	});
	function loadMetting(){
		$("#meeting_successive_datagrid").datagrid({
			url : "meetingApp/meetingquery.do",
			rownumbers: true,
			pageSize:10,
			/* pageList:[5,10,20,50],
			 pageSize:20, */
			fitColumns: false,
			queryParams: {
				titles: $("#titles").val(),
				types: $("#types").val(),
				showEnable: $("#showEnable").val(),
				createTime: $("#createTime").val()
			},
			columns : [[
				{
					field : "ck",
					title : "勾选",
					width : 10,
					checkbox : true
				},
				{
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
				}
			]],
		});
	}

	function sureMeetingSuccessive(){
		var rows = $("#meeting_successive_datagrid").datagrid("getChecked");
		if(rows&&rows.length==0){
			alert("请先选择项目");
		}else{
			var arr=[];
			for(var i=0;i<rows.length;i++){
				var row = rows[i];
				arr.push({id: row.id, titles: row.titles});
			}
			addMeetingSuccessive(arr);
			closeMeetingSuccessive();
		}
	}
	function closeMeetingSuccessive(){
		$("#meetingSuccessiveindexformeeting").dialog("destroy");
	}

</script>