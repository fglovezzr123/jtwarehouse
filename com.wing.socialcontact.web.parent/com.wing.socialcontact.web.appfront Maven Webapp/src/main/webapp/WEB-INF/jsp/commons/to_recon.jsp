<!-- 认证跳转页 -->
<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
</head>
<body>
<script type="text/javascript">
	var to_recon=function(){
		confirm("您还未认证，请认证后继续此操作。</br>是否马上认证？",function(t){
			if(t == 1){
				self.location.href="${path}/m/my/reconPage.do";
			}else{
				history.go(-1);
			}
		});
	}
	$(function(){
		to_recon();
	});
</script>
</body>
</html>
