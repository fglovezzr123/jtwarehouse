<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div class="search-div" id="meetingsignupremind_toolbar">
	<div class="search-content">
		<span>
			<label style="width:60px;">姓名：</label>
			<input	type="text" id="swwr_trueName" class="span2" style="width:90px;"/>
		</span>
		<span>
			<label style="width:60px;">手机号：</label>
			<input	type="text" id="swwr_mobile" class="span2" style="width:90px;"/>
		</span>
		<span>
			<label style="width: 80px;">会议名称：</label>
			<input	type="text" id="swwr_titles" class="span2" style="width:90px;"/>
		</span>
		<span>
			<label style="width:60px;">职位：</label>
			<input	type="text" id="swwr_job" class="span2" style="width:90px;"/>
		</span>
		<span>
			<label style="width:60px;">地区：</label>
			<input	type="text" id="swwr_region" class="span2" style="width:90px;"/>
		</span>
		<span style="padding-left:5px;">
			<button class="btn btn-primary btn-small" type="button" onclick="loadMeetingSignupRemind()">查询</button>&nbsp;
		</span>
	</div>
</div>
<table id="meetingsignupremind_datagrid" toolbar="#meetingsignupremind_toolbar"></table>
<script type="text/javascript">	
$(function() {
	loadMeetingSignupRemind();			
});
function loadMeetingSignupRemind(){
	$("#meetingsignupremind_datagrid").datagrid({
		url : "meeting/signupremind/query.do",
		rownumbers: true,
		queryParams: { 
			fkId: '${meetingId}',
			trueName: $("#swwr_trueName").val(),
			mobile: $("#swwr_mobile").val(),
			titles: $("#swwr_titles").val(),
			job: $("#swwr_job").val(),
			region: $("#swwr_region").val()
		},
		columns : [[
			 {
				field : "titles",
				title : "会议主题",
				width : $(this).width() * 0.25,
				align : "left"
			},{
				field : "trueName",
				title : "用户姓名",
				width : $(this).width() * 0.1,
				align : "center"
			},{
				field : "mobile",
				title : "手机号",
				width : $(this).width() * 0.1,
				align : "center"
			},
			{
				field : "comName",
				title : "所属公司",
				width : $(this).width() * 0.15,
				align : "left"
			},
			
			{
				field : "remindTime",
				title : "报名提醒时间",
				width : $(this).width() * 0.18,
				align : "left",
				formatter : function(value, row, index) {
					if(value!=null){
					return new Date(value)
							.format("yyyy-MM-dd HH:mm:ss");
					}
					return "";
				}
			},
			{
				field : "job",
				title : "职位",
				width : $(this).width() * 0.15,
				align : "center"
			}
		]]
	});
}
</script>