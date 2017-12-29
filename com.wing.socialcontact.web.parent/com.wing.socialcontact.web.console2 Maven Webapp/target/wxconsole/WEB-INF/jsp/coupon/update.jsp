<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ taglib uri="/WEB-INF/tag/myTag.tld" prefix="my" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理
--%>
<div style="width: 650px;margin: 0 auto;" >
	<form  action="coupon/update.do"   method="post" id="news_form"  onsubmit="return validateSubmitForm(this)">
		 <input id="createTime" name="createTime" value="<fmt:formatDate  value='${c.createTime}' pattern='yyyy-MM-dd HH:mm:ss' />"  type="hidden" />
		 <input id="createUserName" name="createUserName" value="${c.createUserName}"  type="hidden" />
		 <input id="createUserId" name="createUserId" value="${c.createUserId}"  type="hidden" />
		  
		 <table class="table table-nobordered " style="margin-top: 25px;">
			 <tr>
			    	<th style="width: 100px">优惠券类型：</th>
			    	<td >
			     		<select name="couponType" id="couponType" style="width: 120px;">
						<option  value="1"  <c:if test="${c.couponType==1}">selected</c:if>>代金券</option>
						<option  value="2"  <c:if test="${c.couponType==2}">selected</c:if>>满减券</option>
						<option  value="3"   <c:if test="${c.couponType==3}">selected</c:if>>兑换券</option>
					</select>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">优惠券名称：</th>
			    	<td >
			     		<input type="text" name="couponName" value="${c.couponName}" class="easyui-validatebox" required="true"  data-options="validType:['length[1,20]']"   maxlength="20" style="width: 400px;"/>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">优惠券币种：</th>
			    	<td >
			     		<select name="couponCoinType" id="couponCoinType" style="width: 120px;">
						<option  value="1"  <c:if test="${c.couponCoinType==1}">selected</c:if>>J币</option>
						<option  value="2" <c:if test="${c.couponCoinType==2}">selected</c:if>>RMB</option>
					</select>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">优惠券金额：</th>
			    	<td>
			     	<input type="text" name="couponAmount" value="${c.couponAmount}" class="easyui-numberbox"  maxlength="8" min="0" max="99999999" />
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">订单最低消费：</th>
			    	<td>
			     	<input type="text" name="orderMinBuy" value="${c.orderMinBuy}" class="easyui-numberbox"  maxlength="8" min="0" max="99999999"/>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">使用范围：</th>
			    	<td >
			     		<select name="useRange" id="useRange" style="width: 120px;" onchange = "useRangeChange(this)">
						<option  value="1" <c:if test="${c.useRange==1}">selected</c:if>>全平台</option>
						<option  value="2" <c:if test="${c.useRange==2}">selected</c:if>>会议</option>
						<option  value="3" <c:if test="${c.useRange==3}">selected</c:if>>活动</option>
						<option  value="4" <c:if test="${c.useRange==4}">selected</c:if>>商城</option>
					</select>
			    	</td>
			  </tr>
			  <tr id="zddp">
			    	<th style="width: 80px">指定店铺：</th>
			    	<td>
			     	<input class="living" readonly="readonly" type="text" id="mau_storeTelephone" value="${c.useStore}" style="width:350px;"/>
			     	<input class="living" type="hidden" id="useStore" name="useStore" value="${c.useStore}"/>
				    <button class="btn btn-primary btn-small living" type="button" onclick="addStoreDialog()">选择店铺</button>&nbsp;
				    <button class="btn btn-primary btn-small living" type="button" onclick="delStoreDialog()">取消</button>
			    	</td>
			    	<td>&nbsp;</td>
			  </tr>
			  <tr>
				 <th></th>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="submit" class="btn btn-primary" >保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
						  <button type="button"  class="btn clear">清空</button>
					</div>
				 </td>
			  </tr>
		</table>	 
		  
		  <input type="hidden" name="id" value="${c.id }"/>
		  <input type="hidden" name="currentCallback" value="close" />
		  <input type="hidden" name="datagridId" value="${param.rel }_datagrid" />	
		   
	</form>
</div>

<script type="text/javascript">	
$(function() {
	var type = $("#couponType").val(); 
	if(type=="1"||type=="3"){
		$("#zdxf").hide();
	}else{
		$("#zdxf").show();
	}
	var useRange = $("#useRange").val(); 
	if(useRange=="1"||useRange=="4"){
		$("#zddp").show();
	}else{
		$("#zddp").hide();
	}
	
});
function save(){
	var flag = true;
	if(flag){
		if(!validateSubmitForm($('#news_form'))){
			return;
		}
	}
}
//选择店铺
function addStoreDialog(){
	var params = {closed: false,cache: false,modal:true,width:900,height:600,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('店铺选择','coupon/storeindex.do',"storeindexfornews",params)
}
function useRangeChange(obj){
	var type = obj.value;
	if(type=="1"||type=="4"){
		$("#zddp").show();
	}else{
		$("#zddp").hide();
	}
}

function  couponTypeChange(obj){
	var type = obj.value;
	if(type=="1"||type=="3"){
		$("#zdxf").hide();
	}else{
		$("#zdxf").show();
	}
}
function  delStoreDialog(){
	$("#useStore").val("");
	$("#mau_storeTelephone").val("");
}
</script>
