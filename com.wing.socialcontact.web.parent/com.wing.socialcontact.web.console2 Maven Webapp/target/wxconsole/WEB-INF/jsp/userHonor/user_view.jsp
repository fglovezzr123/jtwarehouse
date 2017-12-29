<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div id="cc" >
	<div id="center" data-options="region:'center',title:'',split:true" style="" border="false">
		<div style="width: 100%; margin: 0 auto;">
						
	<form action="userhonor/shenhe.do" method="post" enctype="multipart/form-data" onsubmit="return validateSubmitForm(this)">
		<table class="table table-bordered ">
			<tr>
				<th width="100%" colspan="4" style="text-align:center;font-weigh:bold">
					资料信息
				</th>
			</tr>	
			<tr>
				<th width="15%">
					用户姓名：
				</th>
				<td width="35%">
					<c:out value="${tjyUser.nickname}" />
				</td>
				<th width="15%">
					性别：
				</th>
				<td width="35%">
				    <c:if test="${tjyUser.sex==1}">男</c:if>
				    <c:if test="${tjyUser.sex==2}">女</c:if>
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
			<tr>
				<th width="15%">
					个人简介：
				</th>
				<td width="85%" colspan="3">
					<c:out value="${tjyUser.user_profile}" />
				</td>
			</tr>
			<tr>
				<th width="15%">
					个人爱好：
				</th>
				<td width="85%" colspan="3">
				    <c:forEach  var="userFav" items="${userFavs}" >
				        ${userFav.favId} 
				    </c:forEach>
				</td>
			</tr>
			<tr>
				<th width="100%" colspan="4" style="text-align:center;font-weigh:bold">
					账户信息
				</th>
			</tr>	
			<tr>
				<th width="15%">
					互助宝余额：
				</th>
				<td width="35%">
					<c:out value="${tjyUser.hzb_amount}" />
				</td>
				<th width="15%">
					J币余额：
				</th>
				<td width="35%">
					<fmt:parseNumber integerOnly="true" value="${tjyUser.jb_amount}" />
				</td>
			</tr>		
			<tr>
				<th width="15%">
					RMB余额：
				</th>
				<td width="35%" >
					<c:out value="${tjyUser.availableBalance}" />
				</td>
				<th width="15%">
					等级：
				</th>
				<td width="35%">
					<c:out value="${tjyUser.level}" />
				</td>
			</tr>		
			<tr>
				<th width="15%">
					积分：
				</th>
				<td width="85%" colspan="3">
					<c:out value="${tjyUser.integral_total}" />
				</td>
				
			</tr>		
			<tr>
				<th width="100%" colspan="4" style="text-align:center;font-weigh:bold">
					认证信息
				</th>
				
			</tr>			
			<tr>
				<th width="15%">
					认证姓名：
				</th>
				<td width="35%">
					<c:out value="${tjyUser.nickname}" />
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
			<tr>
				<th width="15%">
					公司名称：
				</th>
				<td width="35%">
					<c:out value="${tjyUser.com_name}" />
				</td>
				<th width="15%">
					注册资金：
				</th>
				<td width="35%" >
					<c:out value="${tjyUser.recon_capital}" />（万元）
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
					电话：
				</th>
				<td width="85%" colspan="3">
					<c:out value="${tjyUser.mobile}" />
				</td>
			</tr>
			<tr>
				<th width="15%">
					公司简介：
				</th>
				<td width="85%" colspan="3">
					<c:out value="${tjyUser.com_profile}" />
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
				</table>
			</form>
		</div>
	</div>	
</div>			
<script type="text/javascript">	

$(function() {
});

</script>
	




