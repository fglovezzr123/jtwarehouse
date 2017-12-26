<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">金服联盟</title>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?v=${sversion}" />
<link rel="stylesheet" href="${path}/resource/css/gold-service.css?v=${sversion}" />
<script src="${path}/resource/js/libs/zepto.min.js" type="text/javascript"
	charset="utf-8"></script>
<style>
.banner_ul img {
	width: 100%;
	height: 100%;
}
</style>
</head>
<body>
	<div class="wrapper">
		<div class="alcontent">
			<p>
				<img src="${path}/resource/img/images/investment.png" />
				<div class="head-img"></div>
				<p>简单地说简单地说简单地说简单地说简单地说简单地说简单地说简单地说简单地说简单地说简单地说简单地说简单地说简单地说简单地说</p>
				<img src="${path}/resource/img/images/investment.png" />
				<div class="head-iii"></div>
				<p>简单地说简单地说简单地说简单地说简单地说简单地说简单地说简单地说简单地说简单地说简单地说简单地说简单地说简单地说简单地说</p>
			</p>
		</div>
	</div>
	<div class="btms active_A">
		<div>下载</div>
		<br class="clear" />
	</div>
	<script type="text/javascript">
      $(document).ready(function() {
        var deviceWidth = document.documentElement.clientWidth;
        document.documentElement.style.fontSize = deviceWidth / 7.5 + 'px';

      })
    </script>
</body>
</html>