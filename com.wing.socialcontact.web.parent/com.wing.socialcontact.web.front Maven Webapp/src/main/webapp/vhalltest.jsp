<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ page import="com.wing.socialcontact.util.*" %>
<% 
	String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.getSession().setAttribute("path", path);
	request.getSession().setAttribute("sversion", "0.2");

	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1 
	response.setHeader("Pragma","no-cache"); //HTTP 1.0 
	response.setDateHeader ("Expires", 0);

	java.util.Map<String, Object>  signObj =com.wing.socialcontact.vhall.api.BaseAPI.createVedioSign("6300", "小梁", "404064228");
	request.setAttribute("signObj", signObj);
%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1, maximum-scale=1.0, user-scalable=no">
    <title>demo1</title>
    <link rel="stylesheet" href="${path}/resource/css/main.css?v=${sversion}">
<link rel="stylesheet" href="${path}/resource/css/libs/public.css?v=${sversion}">

<script type="text/javascript" src="${path}/resource/js/jquery/jquery-1.10.2.min.js?v=${sversion}"></script>
<script type="text/javascript" src="${path}/resource/js/layer-v2.1/layer/layer.js?v=${sversion}"></script>
<script src="${path}/resource/js/login.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="${path}/resource/js/libs/strophe.min.js?v=${sversion}" charset="utf-8"></script> <script type="text/javascript" src="${path}/resource/js/libs/xmpp.mam.js?v=${sversion}" charset="utf-8"></script>
<script type="text/javascript" src="${path}/resource/js/jquery.qqFace.js?v=${sversion}" charset="utf-8"></script>
<script src="${path}/resource/js/libs/public.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resource/js/libs/dropload.min.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?v=${sversion}"/>
<link rel="stylesheet" href="${path}/resource/css/living.css?v=${sversion}" />
<script src="${path}/resource/js/libs/zepto.min.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="${path}/resource/js/vhallSDK.js"></script>
    <style type="text/css">
    </style>
</head>

<body>
    <div class="container">
       
        <div class="video" id="video" style="background-color:gray;">
        
        </div>
        
    </div>
  
    <script type="text/javascript">
    $(document).ready(function() {
        
        VHALL_SDK.init({
            facedom: "",
            textdom: "",
            app_key:  "${signObj.app_key}",// 第三方app_key
            signedat: "${signObj.signedat}",// 签名时间戳
            sign: "${signObj.sign}",// 签名
            email: "${signObj.email}",
            roomid: "${signObj.roomid}",// 活动id
            account: "${signObj.account}",// 第三方用户id
            username: encodeURIComponent("${signObj.username}") ? encodeURIComponent("${signObj.username}") : '',// 用户昵称
            videoContent: "#video",
            docContent: ""
        });
      });   
       
    </script>
</body>

</html>