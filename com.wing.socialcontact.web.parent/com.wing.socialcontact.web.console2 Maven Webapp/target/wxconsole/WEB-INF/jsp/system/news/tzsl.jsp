<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<div style="width: 400px;margin: 0 auto;" >
<form  action="news/adjustNum.do"  method="post" id="news_form" afterCallback="saveCallback" enctype="multipart/form-data" >
<input id="id" name="id" value="${id}"  type="hidden" />
<table class="table table-nobordered " style="margin-top: 25px;">
			  <tr>
			    	<th style="width: 80px">评论数：</th>
			    	<td >
			     		<input type="text" name="commentCount"  id="commentCount" class="easyui-numberbox"  data-options="validType:['length[1,20]']"   maxlength="20" />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">阅读人数：</th>
			    	<td >
			     		<input type="text" name="lookCount"  id="lookCount" class="easyui-numberbox"  data-options="validType:['length[1,20]']"   maxlength="20" />
			    	</td>
			  </tr>
			  <tr>
				 <th></th>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="button" class="btn btn-primary" onclick="closeForm()">关闭</button>
						  <button type="button" class="btn btn-primary"  onclick="save()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
						  
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
	if(flag){
		if(!validateSubmitForm($('#news_form'))){
			return;
		}
	}
}
var saveCallback=function(obj,msg){
	var commentCount = $("#commentCount").val();
	var lookCount = $("#lookCount").val();
	if(commentCount!=null&&commentCount!=""){
		$("#count").html(commentCount);
	}
	if(lookCount!=null&&lookCount!=""){
		$("#lookCountv").html(lookCount);
	}
	if(msg.statusCode == 200){
		closeForm();
	}else{
		Msg.alert("提示",msg.message,"error");
	}
}
function closeForm(){
	$("#tzslfornews").dialog("destroy");
}
</script>