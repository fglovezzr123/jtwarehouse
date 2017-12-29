<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">报名成功</title>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?v=${sversion}"/>
<link rel="stylesheet" href="${path}/resource/css/name-suc.css?v=${sversion}" />
<script src="${path}/resource/js/libs/zepto.min.js" type="text/javascript" charset="utf-8"></script>
</head>
 <body>
 <div class="wrapper">
   <div class="right-signal"> 恭喜您报名成功！ </div>
   <div class="name-info">
        <div class="name-head">您的入场凭证</div>
        <div class="name-head1">
                姓名 : <span>${user.userName}</span> <br/>
                手机 : <span>${user.phone}</span> 
        </div>
   </div>
   <div class="active_A name-btn" onclick="toMeetingIndex()">返回</div>
 </div>  
<script type="text/javascript">
$(document).ready(function() {
  	var deviceWidth = document.documentElement.clientWidth;
  	document.documentElement.style.fontSize = deviceWidth / 7.5 + 'px';
})
function toMeetingIndex(){
	self.location.href = "${path}/m/my/myfootPrint_activity.do";
}
</script>
</body>
</html>