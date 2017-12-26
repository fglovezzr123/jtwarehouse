<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div id="cc" >
	<div id="center" data-options="region:'center',title:'',split:true" style="" border="false">
		<div style="width: 100%; margin: 0 auto;">
			<form action="userhonor/shenhe.do" method="post" enctype="multipart/form-data" onsubmit="return validateSubmitForm(this)" id="userForm">
				<table class="table table-bordered ">
					<tr>
						<th width="15%">
							用户姓名：
						</th>
						<td width="35%">
							<c:out value="${tjyUser.true_name}" />
						</td>
						<th width="15%">
							职位：
						</th>
						<td width="35%">
							<c:out value="${tjyUser.job_name}" />
						</td>
					</tr>
					<tr>
						<th width="15%">
							行业：
						</th>
						<td width="35%">
							<c:out value="${tjyUser.industry_name}" />
						</td>
						<th width="15%">
							所在区域：
						</th>
						<td width="35%" >
							<c:out value="${tjyUser.province_name}${tjyUser.city_name}" />
						</td>
						
					</tr>
					<tr style="display: none;">
						<th width="15%">
							具体地址：
						</th>
						<td width="85%" colspan="3" >
							<c:out value="${tjyUser.address}" />
						</td>
					</tr>
					<tr>
						<th width="15%">
							注册手机号：
						</th>
						<td width="35%" colspan="">
							<c:out value="${tjyUser.mobile}" />
						</td>
						<th width="15%">
							认证手机号：
						</th>
						<td width="35%" colspan="">
							<c:out value="${tjyUser.recon_mobile}" />
						</td>
					</tr>
					<tr>
						<th width="15%">
							公司名称：
						</th>
						<td width="35%">
							<c:out value="${tjyUser.com_name}" />
						</td>
						<th width="15%">
							公司简介：
						</th>
						<td width="35%" colspan="">
							<c:out value="${tjyUser.com_profile}" />
						</td>
					</tr>
					<tr>
						<th width="15%">
							注册资金：
						</th>
						<td width="35%" colspan="">
							<c:out value="${tjyUser.recon_capital}" />万元
						</td>
					</tr>
					<tr>
						<th width="15%">
							营业执照、法人身份证正面、反面共三张照片：
						</th>
						<td width="85%" colspan="3">
							<c:forEach items="${imgList }" var="img">
								<c:if test="${img.type eq 1 }">
									<img  src="${ossurl }${img.imgUrl }" class="up_pic_img" style="width:240px; height:160px;"/>
								</c:if>
							</c:forEach>
						</td>
					</tr>
					<tr style="display: none;">
						<th width="15%">
							银联认证：
						</th>
						<td width="85%" colspan="3">
							<c:forEach items="${imgList }" var="img">
								<c:if test="${img.type eq 2 }">
									<img  src="${ossurl }${img.imgUrl }" class="up_pic_img" style="width:240px; height:160px;"/>
								</c:if>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th width="15%">
							审核：
						</th>
						<td width="85%" colspan="3">
							<input type="radio" name="reconStatus"  value="2" <c:if test="${tjyUser.recon_status eq '2'}">checked</c:if> />&nbsp;&nbsp;审核通过&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="reconStatus"  value="3" <c:if test="${tjyUser.recon_status eq '3'}">checked</c:if> />&nbsp;&nbsp;审核不通过
						</td>
					</tr>
					<tr>
						<th width="15%">
							分配秘书电话：
						</th>
						<td width="85%" colspan="3">
							<!-- <input type="text" name="kfTelephone" id="kfTelephone" value="${tjyUser.kf_telephone }" reconStatus="${tjyUser.reconStatus }" maxlength="11"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/> -->
							<input type="text" name="kfTelephone" id="kfTelephone" value="${tjyUser.kf_telephone }" reconStatus="${tjyUser.reconStatus }"/> 
						</td>
					</tr>
					<tr>
						<th width="15%">
							推荐人姓名：
						</th>
						<td width="85%" colspan="3">
							<!-- <input type="text" name="kfTelephone" id="kfTelephone" value="${tjyUser.kf_telephone }" reconStatus="${tjyUser.reconStatus }" maxlength="11"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/> -->
							<input type="text" name="tjName" id="tjName" value="${tjyUser.tj_name }" "/> 
						</td>
					</tr>
					
					<tr>
						<th width="15%">
							推荐人电话：
						</th>
						<td width="85%" colspan="3">
							<!-- <input type="text" name="kfTelephone" id="kfTelephone" value="${tjyUser.kf_telephone }" reconStatus="${tjyUser.reconStatus }" maxlength="11"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/> -->
							<input type="text" name="tjMobile" id="tjMobile" value="${tjyUser.tj_mobile }" "/> 
						</td>
					</tr>
					
					
					<tr>
						<th width="15%">
							认证有效截止时间：
						</th>
						<td width="85%" colspan="3">
						    <input type="text" name="lastRegDate" id="lastRegDate"  class="easyui-validatebox" <c:if test="${!empty tjyUser.last_reg_date }"> disabled='disabled'</c:if>  onFocus="WdatePicker({skin:'whyGreen',startDate:'%y-%M-%d ',dateFmt:'yyyy-MM-dd ',minDate: '%y-%M-{%d+1} 00:00:00'})"   value="${tjyUser.last_reg_date }"/>
						     <!-- <input type="text" name="lastRegDate" id="lastRegDate"  class="easyui-validatebox" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   value="${tjyUser.last_reg_date }"/>  -->
						    
						</td>
					</tr>
					<tr>
						<th>
						</th>
						<td colspan="3">
							<button type="button" class="btn btn-primary" id="submit_button">
								保存
							</button>
						</td>
					</tr> 
				</table>
				<input type="hidden" name="datagridId" value="${param.rel }_datagrid" />
				<input type="hidden" name="currentCallback" value="close" />
				<input type="hidden" name="id" value="${tjyUser.id}" />
			</form>
		</div>
	</div>	
</div>			
<script type="text/javascript">	
$(function(){
	$("#submit_button").click(function(){
		var reconStatus=$("#kfTelephone").attr("reconStatus");
		
		if(reconStatus*1 < 2){
			var x_reconStatus=$("input[type='radio'][name='reconStatus']:checked").attr("value");
			if(x_reconStatus*1 == 2){
				var kfTelephone=$("#kfTelephone").val();
				if($.trim(kfTelephone) == 0){
					alert("请输入分配秘书电话");
					return;
				}
				if(!(/^0\d{2,3}-?\d{7,8}$/.test(kfTelephone))&&!(/^1(3|4|5|7|8)\d{9}$/.test(kfTelephone))){
					alert('秘书电话有误，请重填');
					return false;
				}
			}
		}
		var kfTelephone=$("#kfTelephone").val();
		if($.trim(kfTelephone) == 0){
			alert("请输入分配秘书电话");
			return;
		}
		if(!(/^0\d{2,3}-?\d{7,8}$/.test(kfTelephone))&&!(/^1(3|4|5|7|8)\d{9}$/.test(kfTelephone))){
			alert('秘书电话有误，请重填');
			return false;
		}
		var tjMobile=$("#tjMobile").val();
		if($.trim(tjMobile) != 0){
			if(!(/^0\d{2,3}-?\d{7,8}$/.test(tjMobile))&&!(/^1(3|4|5|7|8)\d{9}$/.test(tjMobile))){
				alert('推荐人电话有误，请重填');
				return false;
			}
		}
		
		
		
		
	
		$("#userForm").submit();
	});
});
</script>
	




