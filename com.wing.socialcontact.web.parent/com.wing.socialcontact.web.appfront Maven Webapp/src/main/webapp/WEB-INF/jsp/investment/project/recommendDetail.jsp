<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">项目自荐详细</title>

<link rel="stylesheet" href="${path}/resource/css/project-selfRecon.css?v=${sversion}" />

</head>
<body>
<div class="wrapper">
         <div class="xiaobiaoti">企业信息</div>
         <div class="info-forms">
              <div class="info-forms-name">企业名称</div>
              <span>${obj.comName}</span>
         </div>
         <div class="info-forms" style="border:none">
              <div class="info-forms-name">注册资本</div>
              <span>${fns:fixed(obj.registeredCapital)}万</span>
         </div>
         <div class="xiaobiaoti">项目类型</div>
         <div class="info-forms">
       		<span>${obj.prjTypeName}</span>
         </div>
         <div class="xiaobiaoti">项目详情</div>
           <div class="info-forms">
              <div class="info-forms-name">项目名称</div>
              <span>${obj.prjName}</span>
         </div>
         <div class="projectDpict">
              <div>项目描述</div>
              <textarea readonly="readonly" style="resize: none;">${obj.prjDesc}</textarea>
              <br class="clear"/>
         </div>
         <div class="projectDpict">
              <div>项目介绍</div>
              <textarea readonly="readonly" style="resize: none;">${obj.prjProfile}</textarea>
              <br class="clear"/>
         </div>
         <div class="xiaobiaoti">项目照片</div>
         <div class="photo">
         	 <c:forEach items="${obj.projectImages}" var="item">
			 <p style="margin-bottom:5px;">
         	 	 <img class="activePoster" src="${item.imageUrl}" style="width:100%;"/>
         	 </p>
			</c:forEach>
         </div>
   </div>
</body>
</html>