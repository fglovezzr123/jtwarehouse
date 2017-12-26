<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 

<% 
	String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.getSession().setAttribute("sversion", "0.18");
	request.getSession().setAttribute("path", path);

	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1 
	response.setHeader("Pragma","no-cache"); //HTTP 1.0 
	response.setDateHeader ("Expires", 0);
	
%>

<meta name="format-detection" content="telephone=no" />
<script src="${path}/resource/js/scroll.js?v=${sversion}"></script>

<script type="text/javascript" src="${path}/resource/js/jquery/jquery-1.10.2.min.js?v=${sversion}"></script>
<script type="text/javascript" src="${path}/resource/js/rem.js"></script>

<link rel="stylesheet" href="${path}/resource/css/common.css?v=${sversion}">
<link rel="stylesheet" href="${path}/resource/css/qifuyun.css?v=${sversion}">
<!-- <link rel="stylesheet" href="${path}/resource/css/libs/dropload.min.css?v=${sversion}"> -->

<script src="${path}/resource/js/layer-v2.1/layer/layer.js?v=${sversion}"></script>

<script src="${path}/resource/js/login.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resource/js/util.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>

<script type="text/javascript" src="${path}/resource/js/libs/iscroll.js?v=${sversion}" charset="utf-8"></script> 
<%-- <script src="${path}/resource/js/libs/dropload.min.js?v=${sversion}" type="text/javascript" charset="utf-8"></script> --%>

<script type="text/javascript">
	var _path="${path}";
	var _oss_url="http://tianjiu.oss-cn-beijing.aliyuncs.com/";
</script>
