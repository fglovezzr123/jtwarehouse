<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div class="search-div" id="toolbar">
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
		</span>
		<shiro:hasPermission name="newsclass:update">
		<span>
			<button class="btn btn-primary btn-small" type="button" onclick="addMeeting()">发布会议</button>&nbsp;
		</span>
		</shiro:hasPermission>
	</div>
</div>
<table id="meetingindex_datagrid" toolbar="#toolbar"></table>
<script type="text/javascript">	
$(function() {
	loadMetting();			
});
function loadMetting(){
	$("#meetingindex_datagrid").datagrid({
		url : "meeting/meetingquery.do",
		rownumbers: true,
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
					return   "<a href = 'javaScript:void(0)' onclick = showSignupRemind('"+row.id+"')>报名提醒用户</a>|"
					        +"<a href = 'javaScript:void(0)' onclick = showSignup('"+row.id+"')>报名用户</a>|"
					        +"<a href = 'javaScript:void(0)' onclick = showWhitelist('"+row.id+"')>白名单</a>|"
							+"<a href = 'javaScript:void(0)' onclick = updateMetting('"+row.id+"')>编辑</a>|"
							+"<a href = 'javaScript:void(0)' onclick = detailMetting('"+row.id+"')>查看</a>";
				}
			}
		]],
	});
}
//发布
function addMeeting(fid){
	var params = { onClose:function(){  
        $("#meetingaddpage").dialog('destroy');  
    }, closed: false,cache: false,modal:true,width:1000,height:600,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('发布会议','meeting/meetingaddpage.do?id=',"meetingaddpage",params)
}
//报名提醒用户
function showSignupRemind(fid){
	var params = { closed: false,cache: false,modal:true,width:1024,height:400,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('报名提醒用户','meeting/signupremind/index.do?meetingId='+fid,"meetingsingupremindpage",params)
}
//报名用户
function showSignup(fid){
	var params = { closed: false,cache: false,modal:true,width:1100,height:400,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('报名用户','meeting/signupindex.do?meetingId='+fid,"meetingsinguppage",params)
}
//白名单
function showWhitelist(fid){
	var params = { closed: false,cache: false,modal:true,width:800,height:400,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('白名单','meeting/meetingwhitelist.do?meetingId='+fid,"meetingwhitelistpage",params)
}
//编辑
function updateMetting(fid){
	var params = { onClose:function(){  
        $("#meetingaddpage").dialog('destroy');  
    },  closed: false,cache: false,modal:true,width:1000,height:600,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('编辑会议','meeting/meetingupdatepage.do?id='+fid,"meetingaddpage",params)
}
//明细
function detailMetting(fid){
	var params = { closed: false,cache: false,modal:true,width:1000,height:600,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('会议明细','meeting/meetingdetail.do?id='+fid,"meetingdetailpage",params)
}
</script>