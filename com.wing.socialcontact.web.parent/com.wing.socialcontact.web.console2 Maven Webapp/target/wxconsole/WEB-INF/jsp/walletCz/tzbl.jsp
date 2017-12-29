<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：调整比例
--%>

<div style="width: 98%;margin: 0 auto;" >
	<form  action="walletCz/tzblSave.do"  method="post" id="news_form" enctype="multipart/form-data" >
		<table class="table table-nobordered " style="margin-top: 25px;">
		 	<thead>
		 		<tr><td style="text-align: center;font-weight: bolder;font-size: 16px;">J币充值比例</td></tr>
		 	</thead>
		 	<tbody>
				<tr><td style="text-align: center;"><font color="red">当前J币兑换比例为${listValue.listDesc}</font></td></tr>
				<tr><td style="text-align: center;">1&nbsp;RMB&nbsp;&nbsp;=&nbsp;&nbsp;<input type="text" name="tzbl" id="tzbl" value="${listValue.listValue}" style="width:60px" onkeyup="this.value=this.value.replace(/\D/g,'');" onafterpaste="this.value=this.value.replace(/\D/g,'');" maxlength="8"/>&nbsp;&nbsp;J币</td></tr>
			</tbody>
			<tfoot>
				<tr>
					<td style="text-align: center;">
						<button type="button" class="btn btn-primary"  onclick="save()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="btn clear">清空</button>
				 	</td>
			  	</tr>
			  </tfoot>
		  </table>
		  <input type="hidden" name="currentCallback" value="close" />
		  <input type="hidden" name="callback_fn" value="otherCallback" />
	</form>
</div>

<script type="text/javascript">	
function save(){
	var tzbl=$.trim($("#tzbl").val());
	if(tzbl.length == 0){
		Msg.alert("提示","请输入J币数量","info");
		return;
	}
	validateSubmitForm($('#news_form'));
}
function otherCallback(){
	var tb=navTab.mainTab.tabs("getSelected");
	tb.panel('refresh');
}
</script>
