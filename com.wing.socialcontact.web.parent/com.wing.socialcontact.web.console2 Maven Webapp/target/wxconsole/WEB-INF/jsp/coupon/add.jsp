<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理-- 优惠券管理--添加
--%>

<div style="width: 650px;margin: 0 auto;" >
	<form  action="coupon/add.do"  method="post" id="news_form"  enctype="multipart/form-data" afterCallback="saveCallback">
		  <table class="table table-nobordered " style="margin-top: 25px;">
		    <tr>
			    	<th style="width: 100px">优惠券类型：</th>
			    	<td >
			     		<select name="couponType" id="couponType" style="width: 120px;" onchange = "couponTypeChange(this)">
						<option  value="1" >代金券</option>
						<option  value="2" >满减券</option>
						<option  value="3" >兑换券</option>
						<option  value="4" >会议券</option>
					</select>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">优惠券名称：</th>
			    	<td >
			     		<input type="text" name="couponName" class="easyui-validatebox" required="true"  data-options="validType:['length[1,20]']"   maxlength="20" style="width: 400px;"/>
			    	</td>
			  </tr>
			  <tr id="_couponCoinType">
			    	<th style="width: 100px">优惠券币种：</th>
			    	<td >
			     		<select name="couponCoinType" id="couponCoinType" style="width: 120px;">
						<option  value="1" >J币</option>
						<option  value="2" >RMB</option>
					</select>
			    	</td>
			  </tr>
			  <tr id="_couponAmount">
			    	<th style="width: 100px">优惠券金额：</th>
			    	<td>
			     	<input type="text" name="couponAmount" class="easyui-numberbox"  required="true" maxlength="8" min="1" max="99999999"/>
			    	</td>
			  </tr>
			  <tr id="_couponCount">
			    	<th style="width: 100px">优惠券次数：</th>
			    	<td>
			     	<input type="text" name="couponCount" class="easyui-numberbox"  required="true" maxlength="8" min="1" max="99999999"/>
			    	</td>
			  </tr>
			  <tr id="_couponState">
			    	<th style="width: 100px">优惠券有效：</th>
			    	<td>
						<select name="state" id="state" style="width: 120px;">
							<option value="0">无效</option>
							<option value="1">有效</option>
						</select>
					</td>
			  </tr>
			   <tr id="zdxf">
			    	<th style="width: 100px">订单最低消费：</th>
			    	<td>
			     	<input type="text" name="orderMinBuy" id="orderMinBuy" class="easyui-numberbox"  maxlength="8" min="1"  max="99999999"/>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">使用范围：</th>
			    	<td >
			     		<select name="useRange" id="useRange" style="width: 120px;" onchange = "useRangeChange(this)">
						<option  value="1" >全平台</option>
						<option  value="2" >会议</option>
						<option  value="3" >活动</option>
						<option  value="4" >商城</option>
					</select>
			    	</td>
			  </tr>
			  <tr id="zddp">
			    	<th style="width: 80px">指定店铺：</th>
			    	<td>
			     	
			     	<textarea   id="mau_storeTelephone" readonly="readonly" rows="6" maxlength="50" style="width:350px" ></textarea>
			     	<input class="living" type="hidden" id="useStore" name="useStore" value=""/>
				    <button class="btn btn-primary btn-small living" type="button" onclick="addStoreDialog()">选择店铺</button>&nbsp;
				     <button class="btn btn-primary btn-small living" type="button" onclick="delStoreDialog()">取消</button>
			    	</td>
			    	<td>&nbsp;</td>
			  </tr>
			  <tr>
				 <th></th>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="button" class="btn btn-primary" onclick="save()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
						  <!-- <button type="button" class="btn clear" >清空</button> -->
					</div>
				 </td>
			  </tr>
		  </table>
		  
		  <input type="hidden" name="datagridId" value="${param.rel }_datagrid" />	
		  <input type="hidden" name="currentCallback" value="close" />
		
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
});
function save(){
	var flag = true;
	var type = $("#couponType").val(); 
	if(type=="2"){
		var orderMinBuy = $("#orderMinBuy").val(); 
		if(orderMinBuy==null){
			Msg.alert("提示","请填写最低消费金额！","warning",null);
			flag=false;
			return;
		}
		if(orderMinBuy<=0){
			Msg.alert("提示","最低消费金额必须大于0！","warning",null);
			flag=false;
			return;
		}
		
	}
	if(flag){
		if(!validateSubmitForm($('#news_form'))){
			return;
		}
	}
}
var saveCallback=function(obj,msg){
	if(msg.statusCode == 200){
		$("#${param.rel}_datagrid").datagrid('reload');
		$("#${param.rel}_add").dialog("close");
	}else{
		Msg.alert("提示",msg.message,"error");
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
	if(type=="1"||type=="3"||type=="4"){
		$("#zdxf").hide();
	}else{
        $("#zdxf").show();
    }
    //会议券
    if (type=="4"){
        $("#_couponCoinType").hide();
        $("#_couponAmount").find("input").validatebox({
            required: false
        });
        $("#_couponAmount").hide();
        $("#_couponCount").find("input").validatebox({
            required: true
        });
        $("#_couponState").find("select").validatebox({
            required: true
        });
        $("#_couponCount").show();
        $("#_couponState").show();
    }else{
        $("#_couponCoinType").show();
        $("#_couponAmount").find("input").validatebox({
            required: true
        });
        $("#_couponAmount").show();
        $("#_couponCount").find("input").validatebox({
            required: false
        });
        $("#_couponState").find("select").validatebox({
            required: false
        });
        $("#_couponCount").hide();
        $("#_couponState").hide();
    }
}
function  delStoreDialog(){
	$("#useStore").val("");
	$("#mau_storeTelephone").val("");
}
</script>
