<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="加好友">
		<title></title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?v=${sversion}" />
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/leaveMsg.css?v=${sversion}" />
	</head>
	<body style="background: #f2f3f4;">
		<div class="msg">
			<textarea id="askInfo" name="" rows="" cols="" placeholder="请输入..." onblur="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')" onpaste="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')" oncontextmenu="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')"></textarea>
			<p>0/200</p>
		</div>
		<div class="pst">
			<div id="joinGroup" class="M-footer active_A">
				发送
			</div>
		</div>
		<script src="${path}/resource/js/applyToAddGroup.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
	</body>

</html>