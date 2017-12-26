<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1"/>
		<meta name="keywords" content="邀请好友"/>
		<title>邀请好友</title>
		<script type="text/javascript">
			var hdticket="$!hdticket";
			if(hdticket != ""){
				document.location.href="${path}/m/my/fxylshare.do?t=${hdticket}&uid=${userId}";
			}
		</script>
	</head>
	<body style="background:#fff;">
	</body>
</html>
