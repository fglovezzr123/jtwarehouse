<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<div style="width: 400px;margin: 0 auto;" >
<form  action="coupon/grantCoupon.do"  method="post" id="news_form" afterCallback="saveCallback" enctype="multipart/form-data" >
<input id="fkId" name="fkId" value="${fkId}"  type="hidden" />
<table class="table table-nobordered " style="margin-top: 25px;">
			  <tr>
			    	<td >
			     		 <textarea  name="mobeils" id="mobeils" rows="6" maxlength="50" style="width:350px" placeholder="请输入发放手机号，以英文逗号隔开"></textarea>
			     		
			    	</td>
			  </tr>
			  <tr>
			    	<td >
			     		<strong style="color:#F00">*该批优惠券剩余数量为${remainNum}张</strong>  
			    	</td>
			  </tr>
			  <tr>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="button" class="btn btn-primary" onclick="save()">发放</button>
						  <button type="button" class="btn btn-primary"  onclick="closeForm()">取消</button>&nbsp;&nbsp;&nbsp;&nbsp; 
					</div>
				 </td>
			  </tr>
 </table>
</form>
</div>

<table id="vhall_datagrid"></table>
<script type="text/javascript">	
$(function() {
		
});

function save(){
	var flag = true;
	var mobeils = $("#mobeils").val();
	if(mobeils==""||mobeils==null){
		Msg.alert("提示","发放手机号不能为空！！","warning",null);
		return;
	}
	var str = mobeils.split(",");
	if(str.length>parseInt('${remainNum}')){
		Msg.alert("提示","该批优惠券发放数量大于剩余数量！","warning",null);
		return;
	}
	if(flag){
		if(!validateSubmitForm($('#news_form'))){
			return;
		}
	}
}
var saveCallback=function(obj,msg){
	if(msg.statusCode == 200){
		$("#couponlog_datagrid").datagrid('reload');
		$("#couponlog_add").dialog("close");
	}else{
		Msg.alert("提示",msg.message,"error");
	}
}
function closeForm(){
	$("#couponlog_add").dialog("close");
}
</script>