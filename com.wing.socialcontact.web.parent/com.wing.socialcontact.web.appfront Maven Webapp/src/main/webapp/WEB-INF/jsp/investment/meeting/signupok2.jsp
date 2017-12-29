<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">报名成功</title>
<link rel="stylesheet" href="${path}/resource/css/name-suc.css?v=${sversion}" />
</head>
 <body>
 <div class="wrapper">
   <div class="right-signal"> 恭喜您报名成功！ </div>
   <div class="name-info" style="height:4.5rem;">
        <div class="name-head" style="height:2.3rem;">
        	<p style="color: #3b3b3b;">报名信息</p>
        	<p style="color: #3b3b3b;">姓名:${tjyUser.nickname}</p>
        	<p style="color: #3b3b3b;">手机号:${obj.mobile}</p>
        </div>
        <div class="name-head1" style="padding-left:0.3rem;font-size:14px;">
            <p>请保持电话畅通，我们的会议服务人员会尽快与您联系。</p>
            <p>客服电话：010-53118922</p>
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
	self.location.href = "${path}/m/meeting/index.do";
}
</script>
</body>
</html>