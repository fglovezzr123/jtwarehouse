<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="好友印象">
		<title>好友印象</title>
		<style type="text/css">
			.uList {
				width: 100%;
				/*padding: 0 .30rem;*/
				background: #FFF;
				font-size: .24rem;
				display: flex;
				flex-wrap: wrap;
				box-sizing: border-box;
				padding-bottom: .30rem;
			}
			
			.uList li {
				/*width: .90rem;*/
				height: .36rem;
				line-height: .36rem;
				text-align: center;
				padding: 0 .22rem;
			}
			
			.uList li.bg {
				background: #0f88eb;
				color: #fff;
				border-radius: .1rem;
			}
			
			.uList li {
				margin-top: .30rem;
				margin-left: .30rem;
			}
			
			.M-footer {
				width: 100%;
				height: 1rem;
				text-align: center;
				line-height: 1rem;
				background: #ff801b;
				color: #fff;
				font-size: .30rem;
				position: fixed;
				bottom: 0;
				left: 0;
			}
		</style>
	</head>

	<body style="background: #f2f3f4;">
		<ul class="uList">
			<c:forEach items="${yxkList}" var="yxk">
				<c:set var="c" value="0"/>
				<c:forEach items="${yxList}" var="yx">
					<c:if test="${yxk.id eq yx.impress_id}">
						<c:set var="c" value="1"/>
					</c:if>
				</c:forEach>
				<c:choose>
					<c:when test="${c eq 1}">
						<li class="bg" id="${yxk.id }">${yxk.listValue }</li>
					</c:when>
					<c:otherwise>
						<li id="${yxk.id }">${yxk.listValue }</li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</ul>
		<div class="M-footer active_A">
			完成
		</div>
	</body>
</html>