<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div class="search-div" id="meetingsignup_toolbar">
	<div class="search-content">
		<span>
			<label style="width:60px;">姓名：</label>
			<input	type="text" id="sww_nickname" class="span2" style="width:90px;"/>
		</span>
		<span>
			<label style="width:60px;">手机号：</label>
			<input	type="text" id="sww_mobile" class="span2" style="width:90px;"/>
		</span>
		<span>
			<label style="width: 80px;">报名时间：</label>
			<input id="sww_signupTime" style="width:90px;" size=15 class="Wdate time-field" 
				onfocus="WdatePicker({readOnly:true})"
				onClick="WdatePicker({readOnly:true})" 
				readonly="readonly"/>
		</span>
		<span>
			<label style="width: 80px;">参会方式：</label>
			<select id="sww_attendType">
				<option value="">全部参会方式</option>
				<option value="1">线上参会</option>
				<option value="2">线下参会</option>
			</select>
		</span>
		<span>
			<label style="width: 80px;">用户类型：</label>
			<select id="sww_payType">
				<option value="">全部用户</option>
				<option value="2">白名单用户</option>
				<option value="1">非白名单用户</option>
			</select>
		</span>
		<span style="padding-left:5px;">
			<button class="btn btn-primary btn-small" type="button" onclick="loadMeetingSignup()">查询</button>&nbsp;
		</span>
		<span>
			<button class="btn btn-primary btn-small" type="button" onclick="doExportMtSignup(this)">导出报表</button>&nbsp;
		</span>
	</div>
</div>
<div style="display:none">
	<form id="export_mtsignup_form" action="meeting/signup/export.do" target="export_mtsignup_hidden_frame">
		<input type="hidden" name="meetingId" value="${meetingId}">
		<input type="hidden" name="nickname" id="exp_ms_nickname">
		<input type="hidden" name="attendType" id="exp_ms_attendType">
		<input type="hidden" name="mobile" id="exp_ms_mobile">
		<input type="hidden" name="signupTime" id="exp_ms_signupTime">
		<input type="hidden" name="payType" id="exp_ms_payType">
	</form>
	<iframe id="export_mtsignup_hidden_frame" name="export_mtsignup_hidden_frame" width="0" height="0"/>
</div>
<table id="meetingsignup_datagrid" toolbar="#meetingsignup_toolbar"></table>
<script type="text/javascript">	
$(function() {
	loadMeetingSignup();			
});
function loadMeetingSignup(){
	$("#meetingsignup_datagrid").datagrid({
		url : "meeting/signupquery.do",
		rownumbers: true,
		queryParams: { 
			meetingId: '${meetingId}',
			nickname: $("#sww_nickname").val(),
			mobile: $("#sww_mobile").val(),
			attendType: $("#sww_attendType").val(),
			payType: $("#sww_payType").val(),
			signupTime: $("#sww_signupTime").val()
		},
		columns : [[
			 {
				field : "titles",
				title : "会议主题",
				width : $(this).width() * 0.25,
				align : "left"
			},{
				field : "recLinkMan",
				title : "推荐人",
				width : $(this).width() * 0.15,
				align : "center"
			},{
				field : "nickname",
				title : "用户姓名",
				width : $(this).width() * 0.1,
				align : "center"
			},
			{
				field : "mobile",
				title : "手机号",
				width : $(this).width() * 0.15,
				align : "center"
			},
			{
				field : "ticketPrice",
				title : "门票价格(元)",
				width : $(this).width() * 0.12,
				align : "center",
				formatter : function(value, row, index) {
					return !value||value==0?"免费":value
				}
			},{
				field : "attendType",
				title : "参会方式",
				width : $(this).width() * 0.1,
				align : "center",
				formatter : function(value, row, index) {
					return 1==value?"线上参会":"线下参会";
				}
			},
			{
				field : "comName",
				title : "所属公司",
				width : $(this).width() * 0.15,
				align : "left"
			},
			{
				field : "kfTelephone",
				title : "服务秘书电话",
				width : $(this).width() * 0.15,
				align : "left"
			},
			{
				field : "signupTime",
				title : "报名时间",
				width : $(this).width() * 0.2,
				align : "center",
				formatter : function(value, row, index) {
					return new Date(value)
							.format("yyyy-MM-dd HH:mm:ss");
				}
			},{
				field : "orderStatus",
				title : "支付状态",
				width : $(this).width() * 0.1,
				align : "center",
				formatter : function(value, row, index) {
					return 1<value?"已支付":"未支付";
				}
			},{
				field : "payType",
				title : "是否白名单",
				width : $(this).width() * 0.1,
				align : "center",
				formatter : function(value, row, index) {
					return 2==value?"是":"否";
				}
			},
			{
				field : "op",
				title : "操作",
				width : $(this).width() * 0.15,
				align : "center",
				formatter: function(value, row, index){
						return  "<a href = 'javaScript:void(0)' onclick = showMeetingSignupDetail('"+row.id+"')>查看</a>";
					/* if(3==row.orderStatus){
					}else{
						return  "<a href = 'javaScript:void(0)' onclick = changeStatus('"+row.id+"')>支付</a>&nbsp;|&nbsp;"
						       +"<a href = 'javaScript:void(0)' onclick = showMeetingSignupDetail('"+row.id+"')>查看</a>";
					} */
				}
			}
		]]
	});
}
function doExportMtSignup(obj){
	$("#exp_ms_nickname").val($("#sww_nickname").val());
	$("#exp_ms_mobile").val($("#sww_mobile").val());
	$("#exp_ms_signupTime").val($("#sww_signupTime").val());
	$("#exp_ms_attendType").val($("#sww_attendType").val());
	$("#exp_ms_payType").val($("#sww_payType").val());
	$("#export_mtsignup_form").submit();
}
function showMeetingSignupDetail(obj){
	var params = { closed: false,cache: false,modal:true,width:700,height:500,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('详细信息','meeting/signupdetail.do?id='+obj,"meetingsingupdetailpage",params)
}
function changeStatus(id){
	 $.ajax({
	 	url :"meeting/signup/changestatus.do", 
		type: 'post',	
		data: {id: id},
		cache: false,
		dataType:"json",
		success:function(json){
			if(json&&json["code"]==="0"){
				$("#meetingsignup_datagrid").datagrid('reload');
			}else{
				alert("操作失败");
			}			
		}
	});
}
</script>