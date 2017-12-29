<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="查看认证信息">
		<title>查看认证信息</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/recon.css" />
	</head>
	<body>
		<div class="Z-register-login" style="background: #f2f3f4;width: 100%;height: 100%;">
			<div class="Z-item">
				<span>认证状态</span>
				<c:choose>
					<c:when test="${tjyUser.reconStatus eq 1 }">
						<input type="text" id="reconStatus" name="reconStatus" class="name" value="认证中" readonly="readonly" onfocus="this.blur();" />
					</c:when>
					<c:when test="${tjyUser.reconStatus eq 2 }">
						<input type="text" id="reconStatus" name="reconStatus" class="name" value="认证通过" readonly="readonly" onfocus="this.blur();" style="color: green;"/>
					</c:when>
					<c:when test="${tjyUser.reconStatus eq 3 }">
						<input type="text" id="reconStatus" name="reconStatus" class="name" value="认证不通过" readonly="readonly" onfocus="this.blur();" style="color: red;"/>
					</c:when>
				</c:choose>
			</div>
			<div class="Z-item">
				<span>姓名</span>
				<input type="text" value="${tjyUser.trueName }" class="name" readonly="readonly" onfocus="this.blur();" />
			</div>
			<div class="Z-item">
				<span style="width:20%">公司名称</span>
				<input type="text" value="${tjyUser.comName }" class="name" readonly="readonly" style="width:80%" onfocus="this.blur();" />
			</div>
			<div class="Z-item">
				<span>职位</span>
				<input type="text" value="${jobName }" class="name" readonly="readonly" onfocus="this.blur();" />
			</div>
			<div class="Z-item">
				<span>注册资金(万元)</span>
				<input type="text" class="name" value="<fmt:formatNumber type='number' value='${tjyUser.reconCapital}' maxFractionDigits='0'></fmt:formatNumber>" readonly="readonly" onfocus="this.blur();" /> 
			</div>
			<div class="Z-item">
				<span>行业</span>
				<input type="text" value="${industryName }" class="name" readonly="readonly" onfocus="this.blur();" /> 
			</div>
			<div class="Z-item">
				<span >地区</span>
				<input type="text" value="${tjyUser.province} ${tjyUser.city} ${tjyUser.county}" class="name" readonly="readonly" onfocus="this.blur();" />
			</div>
			<div class="Z-item" style="display: none;">
				<span>具体地址</span>
				<input type="text" value="${tjyUser.address}" class="name" readonly="readonly" onfocus="this.blur();" />
			</div>
			<div class="Z-item">
				<span>电话</span>
				<input type="tel" id="reconMobile" name="reconMobile" class="name" value="${tjyUser.reconMobile }" readonly="readonly" onfocus="this.blur();" />
			</div>
			<div class="gsj">
				<p>公司简介</p>
				<textarea id="comProfile" name="comProfile" rows="" cols="" placeholder="请简述公司概况，不得超过200字" class="nerong" maxlength="200" readonly="readonly" onfocus="this.blur();">${tjyUser.comProfile }</textarea>
			</div>
			<div class="p-head">营业执照、法人身份证正面、反面共三张照片</div>
			<div class="photo">
				<div class="photo-oper">
					<c:forEach items="${imgList }" var="img">
						<c:if test="${img.type eq 1 }">
							<div class="imagess">
								<img  src="${ossurl }${img.imgUrl }" class="up_pic_img" style="width:100%; height:100%;"/>
							</div>
						</c:if>
					</c:forEach>
				</div>
			</div>
			<div class="p-head" style="display: none;">银联认证</div>
			<div class="photo phlast" style="display: none;">
				<c:forEach items="${imgList }" var="img">
					<c:if test="${img.type eq 2 }">
						<div class="imagess">
							<img  src="${ossurl }${img.imgUrl }" class="up_pic_img" style="width:100%; height:100%;"/>
						</div>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</body>
</html>