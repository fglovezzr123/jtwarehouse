<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--

--%>

<div style="width: 95%; margin: 0 auto;">
	<form action="userEmpiricalLog/add2.do" method="post"  id="userEmpiricalLog_add2" onsubmit="return validateSubmitForm(this)">
		<table class="table table-bordered ">
		 	 
			 <tr>
				<th width="15%">
					操作原因：
				</th>
				<td width="85%">
			     	<textarea  id="remark" name="remark" rows="1" cols="100" required="required" style="width: 98%;"></textarea>
			     	
				</td>
			</tr>
			 
			 <tr>
				<th width="15%">
					经验值变化 ：
				</th>
				<td width="85%">
			     	<select class="combox required" class="easyui-validatebox" required="true" id="empiricalType" name="empiricalType" >
							<option value="1">+</option>
							 <option value="2">-</option> 
					</select>
			     	<input type="text" name="empirical"   required="required" class="easyui-numberbox"  maxlength="11" />
				</td>
			</tr>
			  <tr >
				<th>
					手机号：
				</th>
				<td >
				    <input type="text" id="mobile"  name="mobile"   required="required"  class="easyui-validatebox"  data-options="validType:['mPhone']"   maxlength="11" />
				</td>
			</tr>
			 
			 
			  <tr>
				 <th></th>
				 <td>
					<div style="margin-top: 10px; margin-bottom: 10px;">
						<button type="button" class="btn btn-primary"  onclick="save_1()">
							保存
						</button>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="btn clear">
							清空
						</button>
					</div>
				 </td>
			  </tr>
		  </table>
		  
		  <input type="hidden" name="datagridId" value="${param.rel }_datagrid" />	
		  <input type="hidden" name="currentCallback" value="close" />

		
	</form>
	
	
</div>

<script type="text/javascript">


function save_1(){

	var	remark = $("#remark").val();
	
	if($.trim(remark).length > 100){
		Msg.alert("提示","操作原因字数不能超过100个字！","warning",null);
		return ;
	}
	
	var isPhone=/^((\+?86)|(\+86))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|17[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/; 
	if(!isPhone.test($("#mobile").val())){
	    Msg.alert("提示","手机号码格式错误","warning",null);
		return ;
	}
		
	if($.trim(remark).length <= 0){
		Msg.alert("提示","操作原因不能为空！","warning",null);
		return ;
	}else{
		if(!validateSubmitForm($('#userEmpiricalLog_add2'))){
			return;
		}
	}

}

</script>
