<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理-- 聚合页面管理--添加
--%>

<div style="width: 98%;margin: 0 auto;" >
	<form  action="hzb/manager.do"  method="post" id="manager_form" enctype="multipart/form-data" >
		<table class="table table-bordered">
			<tr>
				<th style="width:20%">操作项：</th>
		    	<td style="width:80%">
		     		<select name="type" class="easyui-validatebox" required="true" onchange="view_model(this);" style="width: 200px;">
						<option>请选择操作项</option>
						<!-- 互助开通在开通申请管理列表，此处不提供开通功能 -->
						<c:if test="${wxUser.hzbOpenFlag eq 3 }">
							<option value="1" style="display: none;">开通互助宝</option>
						</c:if>
						<c:if test="${wxUser.hzbOpenFlag eq 1 }">
							<option value="2">停用互助宝</option>
						</c:if>
						<c:if test="${wxUser.hzbOpenFlag eq 2 }">
							<option value="3">启用互助宝</option>
						</c:if>
						<option value="4">增加互助宝余额</option>
						<option value="5">扣除互助宝余额</option>
						<option value="6">调整互助宝等级</option>
					</select>
		    	</td>
		  	</tr>
		  	<tr id="tr_type_4" style="display: none;">
		    	<th>调整金额：</th>
		    	<td>
		    		<c:choose>
		    			<c:when test="${empty wxUser.hzbAmount}">
		    				<c:set var="hzbAmount" value="0"></c:set>
		    			</c:when>
		    			<c:otherwise>
		    				<c:set var="hzbAmount" value="${wxUser.hzbAmount}"></c:set>
		    			</c:otherwise>
		    		</c:choose>
               		<input type="text" min="1" name="managerMoney" class="easyui-validatebox" maxlength="8" onkeyup="clearNoNum2(this);"/><font color="red">（互助宝余额： <fmt:formatNumber type="number" value="${hzbAmount}" maxFractionDigits="0"></fmt:formatNumber>）</font>
               	</td>
			</tr>
			<tr id="tr_type_6" style="display: none;">
		    	<th>调整等级：</th>
		    	<td>
               		<select id="hzbLevel" class="easyui-validatebox" style="width: 200px;">
						<option>请选择互助宝等级</option>
						<c:choose>
							<c:when test="${wxUser.hzbLevel eq 1 }">
								<option value="4">白金等级</option>
								<option value="5">钻石等级</option>
							</c:when>
							<c:when test="${wxUser.hzbLevel eq 2 }">
								<option value="3">黄金等级</option>
								<option value="5">钻石等级</option>
							</c:when>
							<c:otherwise>
								<option value="3">黄金等级</option>
								<option value="4">白金等级</option>
							</c:otherwise>
						</c:choose>
					</select>
               	</td>
			</tr>
			<tr>
		    	<th>备注：</th>
		    	<td>
		    		<textarea style="width: 220px;height: 70px;" id="remark" name="remark" maxlength="50"></textarea>
               	</td>
			</tr>
		  	<tr>
				<th></th>
				<td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
					  	<button type="button" class="btn btn-primary"  onclick="save()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
					  	<!-- <button type="button" class="btn clear" >清空</button> -->
					</div>
			 	</td>
		  	</tr>
		</table>
		<input type="hidden" name="userId" value="${wxUser.id}" />
		<input type="hidden" name="pdType" id="pdType" />
		<input type="hidden" name="datagridId" value="${param.rel }_datagrid" />
		<input type="hidden" name="currentCallback" value="close" />
	</form>
</div>

<script type="text/javascript">	
$(function(){
});

var view_model=function(obj){
	var v=$(obj).val();
	if(v == 4 || v == 5){
		$("#tr_type_4").show();
		$("#tr_type_6").hide();
	}else if(v == 6){
		$("#tr_type_4").hide();
		$("#tr_type_6").show();
	}else{
		$("#tr_type_4").hide();
		$("#tr_type_6").hide();
	}
}

var flag = true;
function save(){
	var v=$("select[name='type']").val();
	if(v == 4){
		$("#pdType").val(1);
	}else if(v == 5){
		$("#pdType").val(2);
	}
	if(v == 4 || v == 5){
		var managerMoney=$("input[name='managerMoney']").val();
		if(isEmpty(managerMoney)){
			Msg.alert("提示","调整金额不能为空，请输入");
			return;
		}
		if(managerMoney < 1){
			Msg.alert("提示","调整金额不能小于1元");
			return;
		}
		if(v == 5){
			var hzbYe="${wxUser.hzbAmount}"*1;
			if(hzbYe < managerMoney){
				Msg.alert("提示","扣除金额不能大于互助宝余额");
				return;
			}
		}
	}else if(v == 6){
		var hzbLevel = $("#hzbLevel").val();
		if(isEmpty(pdType)){
			Msg.alert("提示","调整等级不能为空，请选择");
			return;
		}
		$("#pdType").val(hzbLevel);
	}
	var remark=$("#remark").val();
	if(isEmpty(remark)){
		Msg.alert("提示","备注不能为空，请输入");
		return;
	}
	if(!validateSubmitForm($('#manager_form'))){
		return;
	}
}
</script>
