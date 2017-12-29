<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理-- 话题管理--添加
--%>

<div style="width: 600px;margin: 0 auto;" >
	<form  action="topic/add.do"  method="post" id="news_form"  enctype="multipart/form-data" afterCallback="saveCallback">
		  <table class="table table-nobordered " style="margin-top: 25px;">
			    <tr>
			    	<th style="width: 100px">发布人手机号：</th>
			    	<td>
			     		<input type="text"  id="mobile" name="createUserId" class="easyui-numberbox"  required="true"  maxlength="11" />
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">话题PK：</th>
			    	<td >
			     		<input type="text" name="topicDesc" class="easyui-validatebox" required="true"  data-options="validType:['length[1,100]']"   maxlength="100" style="width: 400px;" placeholder="抛出本次PK话题，不超过100个汉字"/>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">红方观点：</th>
			    	<td >
			     		<input type="text" name="redPoint" class="easyui-validatebox" required="true"  data-options="validType:['length[1,50]']"   maxlength="50" style="width: 400px;" placeholder="不超过50个汉字"/>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">蓝方观点：</th>
			    	<td >
			     		<input type="text" name="bluePoint" class="easyui-validatebox" required="true"  data-options="validType:['length[1,50]']"   maxlength="50" style="width: 400px;" placeholder="不超过50个汉字"/>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">话题阐述：</th>
			    	<td >
			     	 <textarea  name="topicExplain" id="topicExplain" rows="5" maxlength="500" style="width:400px" placeholder="请输入您要阐述的内容" ></textarea>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">权重：</th>
			    	<td>
			     	<input type="text" name="sort"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" class="easyui-numberbox" required="true" maxlength="8" min="0" max="99999999" />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">备注：</th>
			    	<td >
			     	 <textarea  name="remark"  rows="2" maxlength="50" style="width:400px"></textarea>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">仅好友可见：</th>
			    	<td >
			     		<select name="isShow" id="isShow" style="width: 120px;">
						<option  value="1" >是</option>
						<option  value="2"  selected>否</option>
					</select>
			    	</td>
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
	if(msg.statusCode == 200){
		$("#${param.rel}_datagrid").datagrid('reload');
		$("#${param.rel}_add").dialog("close");
	}else{
		Msg.alert("提示",msg.message,"error");
	}
}
</script>
