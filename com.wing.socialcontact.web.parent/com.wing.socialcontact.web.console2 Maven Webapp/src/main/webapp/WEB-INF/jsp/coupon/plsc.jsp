<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<div style="width: 400px;margin: 0 auto;" >
<form  action="coupon/batchGenerate.do"  method="post" id="news_form" afterCallback="saveCallback" enctype="multipart/form-data" >
<input id="fkId" name="fkId" value="${fkId}"  type="hidden" />
<table class="table table-nobordered " style="margin-top: 25px;">
			  <tr>
			    	<th style="width: 100px">生成数量：</th>
			    	<td >
			     		<input type="text" name="couponNum"  id="couponNum" class="easyui-numberbox" required="true"  data-options="validType:['length[1,6]']"   maxlength="6"  max="999999"/>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">优惠券开始日期：</th>
			    	<td >
			     		<input type="text" name="startTime" id="startTime"  maxlength="50" class="easyui-validatebox" required="true"    onFocus="WdatePicker({skin:'whyGreen',startDate:'%y-%M-%d %H:00:00',dateFmt:'yyyy-MM-dd'})" />
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">优惠券结束日期：</th>
			    	<td >
			     		 <input type="text" name="endTime" id="endTime" maxlength="50" class="easyui-validatebox" required="true"   onFocus="WdatePicker({skin:'whyGreen',startDate:'%y-%M-%d %H:00:00',dateFmt:'yyyy-MM-dd'})" />
			    	</td>
			  </tr>
			  <tr>
				 <th></th>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="button" class="btn btn-primary" onclick="save()">确认</button>
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
	var start=$("#startTime").val();
	var end=$("#endTime").val();
	if(start==""){
		Msg.alert("提示","请选择优惠券开始日期！","warning",null);
		return;
	}
	if(end==""){
		Msg.alert("提示","请选择优惠券结束日期！","warning",null);
		return;
	}
	if(start>=end){
		Msg.alert("提示","优惠券结束日期必须大于开始日期！","warning",null);
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
		$("#${param.rel}_datagrid").datagrid('reload');
		$("#${param.rel}_plsc").dialog("close");
	}else{
		Msg.alert("提示",msg.message,"error");
	}
}
function closeForm(){
	$("#${param.rel}_plsc").dialog("close");
}
</script>