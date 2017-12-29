<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="资讯">
		<title>积分规则</title>
		<style>
		
		.title1{
			width: 100%;
			height: .80rem;
			font-size: .30rem;
			background: #fff;
			line-height: .80rem;
			padding:0 0.3rem;
			box-sizing:border-box;
       }
       .newsContent1{
			width: 100%;
			padding: .2rem .3rem .30rem .3rem;
			font-size: .28rem;
			color: #3B3B3B;
			background: #fff;
			box-sizing: border-box;
			margin-bottom: .16rem;
		}
		.newsContent1 p{
			line-height: .45rem;
			font-family: "Microsoft YaHeiLight";
			margin-bottom: .32rem;
		}
		/* .newsContent1 p{
		  text-align:center;
		} */
		/* .newsContent1 img{
		max-height: 6rem;
        max-width: 6.9rem;
		} */
		</style>
	</head>
	<body>
		<div class="T-infor" style="background: #f2f3f4;width: 100%; height:100%;">
			<div class="wrapper" id="wrapper">
				<div class="scrollbar" id="scrollbar">
				</div>
			</div>
			<div class="newsContent1">
				${userIntegralEmpirical.ruleExplain}
			</div>
			
		</div>
	</body>

</html>