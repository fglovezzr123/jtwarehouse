<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理-- pageContentType管理--添加
--%>

<div style="width: 95%; margin: 0 auto;">
	<form action="walletLog/add.do" method="post" id="walletLog_rmb" onsubmit="return validateSubmitForm(this)">
		<table class="table table-bordered ">
		 	 
			 <tr>
				<th width="15%">
					操作明细：
				</th>
				<td width="85%">
			     	<textarea  name="remark" id="remark" rows="1" cols="100" required="true" style="width: 98%;" maxlength="200" ></textarea>
			     	
				</td>
			</tr>
			 
			 <tr>
				<th width="15%">
					余额变化 ：
				</th>
				<td width="85%">
			     	<select class="combox required" class="easyui-validatebox" required="true" id="pdType" name="pdType" >
							<option value="1">+</option>
							 <option value="2">-</option> 
					</select>
			     	<input type="text" name="amount"   required="required" class="easyui-numberbox"  maxlength="8" />
				</td>
			</tr>
			  <tr >
				<th>
					手机号：
				</th>
				<td >
				    <input type="text" name="mobile"   required="required"  class="easyui-validatebox"  data-options="validType:['mPhone']"   maxlength="11" />
				</td>
			</tr>
			 
			 
			  <tr>
				 <th></th>
				 <td>
					<div style="margin-top: 10px; margin-bottom: 10px;">
						<button type="button"  class="btn btn-primary"  onclick="save()">
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
		  <input type="hidden" name="type" value="1" />
		
	</form>
	
	
</div>

<script type="text/javascript">




function save(){

	var	remark = $("#remark").val();
	if($.trim(remark).length > 200){
		Msg.alert("提示","操作明细字数不能超过200个字！","warning",null);
		return ;
	}
	if($.trim(remark).length <= 0){
		Msg.alert("提示","操作明细不能为空！","warning",null);
		return ;
	}else{
		if(!validateSubmitForm($('#walletLog_rmb'))){
			return;
		}
	}

	
}

</script>
