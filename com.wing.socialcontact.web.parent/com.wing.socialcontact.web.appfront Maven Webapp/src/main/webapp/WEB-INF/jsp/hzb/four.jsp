<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">互助宝会员</title>
          <style type="text/css">
           body{
           		width: 100%;
          		background: #F2F3F4;
           }
          	.hzb-header{
          		width: 100%;
          		height: .90rem;
          		background: #fff;
          		padding: 0 .3rem;
          		font-size: .30rem;
          		display: flex;
          		justify-content: space-between;
          		align-items: center;
          		margin-bottom: .20rem;
          		box-sizing:border-box;
          	}
          	.hzb-header span:nth-of-type(2){
          		color: #0F88EB;
          	}
			._rank{
				width: 100%;
				background: #fff;
				font-size: .28rem;
			}
			._rank .kt{
				padding: .1rem .3rem;
				display: flex;
				justify-content: space-between;
				border-bottom: 1px solid #f2f3f4;
				box-sizing:border-box;
			}
			.kt ._left{
				display: block;
				line-height: .60rem;
			}
			._cont{
				width: 5.3rem;
				color: #808080;
			}
			._cont p{
				line-height: .50rem;
			}
			._cont p:nth-of-type(1){
				line-height: .60rem;
			}
			.r-item{
				width: 100%;
				height: .87rem;
				border-bottom: solid 1px #F2F3F4;
				font-size: .28rem;
				display: flex;
				justify-content: space-between;
				align-items: center;
				padding: 0 .3rem;
				box-sizing:border-box;
			}
			.r-item .item_right{
				width: 5.3rem;
				color: #808080;
			}
			.ljcz{
				width: 6.9rem;
		  		height: .85rem;
		  		font-size: .30rem;
		  		background: #0F88EB;
		  		text-align: center;
		  		line-height: .90rem;
		  		margin: 0 auto;
		  		color: #fff;
		  		border-radius: .1rem;
		  		margin-top: .70rem;
			}

          </style>
    </head>
    <body>
		 	<div class="hzb-header">
		 		<span>互助宝状态：</span>
		 		<span>
					<c:choose>
		 				<c:when test="${wxUser.hzbOpenFlag eq 0}">未开通</c:when>
		 				<c:when test="${wxUser.hzbOpenFlag eq 1}">已开通</c:when>
		 				<c:when test="${wxUser.hzbOpenFlag eq 2}">已停用</c:when>
		 				<c:when test="${wxUser.hzbOpenFlag eq 3}">已过期</c:when>
		 				<c:otherwise>未知</c:otherwise>
		 			</c:choose>
				</span>
		 	</div>
		 	<div class="_rank">
		 		<div class="kt">
		 			<span class="_left">开通等级:</span>
		 			<div class="_cont">
		 				<c:choose>
		 					<c:when test="${empty order }">
			 					<c:set var="level" value="${wxUser.hzbLevel}"></c:set>
		 					</c:when>
		 					<c:otherwise>
		 						<c:set var="level" value="${order.level}"></c:set>
		 					</c:otherwise>
		 				</c:choose>
		 				<c:choose>
			 				<c:when test="${level eq 1}">
				 				<p>黄金等级</p>
		 						<p>获得方式：</p>
		 						<p>1、单笔互助宝充值20W，承诺年度消费累计20W</p>
			 				</c:when>
			 				<c:when test="${wxUser.hzbLevel eq 2}">
			 					<p>白金等级</p>
		 						<p>获得方式：</p>
		 						<p>1、年度累计互助宝充值50W，承诺年度消费累计40W</p>
			 				</c:when>
			 				<c:otherwise>
			 					<p>钻石等级</p>
		 						<p>获得方式：</p>
		 						<p>1、年度累计互助宝充值100W，承诺年度消费累计80W</p>
			 				</c:otherwise>
		 				</c:choose>
		 			</div>
		 		</div>
		 		<div class="r-item">
		 			<span class="item_left">累计充值:</span>
		 			<div class="item_right">
		 				<fmt:formatNumber type="number" value="${ljcz}" maxFractionDigits="0"></fmt:formatNumber>元
		 			</div>
		 		</div>
		 		<div class="r-item">
		 			<span class="item_left">累计消费:</span>
		 			<div class="item_right">
		 				<fmt:formatNumber type="number" value="${ljxf}" maxFractionDigits="0"></fmt:formatNumber>元
		 			</div>
		 		</div>
		 		<div class="r-item">
		 			<span class="item_left">互助宝余额:</span>
		 			<div class="item_right">
		 				<fmt:formatNumber type="number" value="${wxUser.hzbAmount}" maxFractionDigits="0"></fmt:formatNumber>元
		 			</div>
		 		</div>
		 	</div>
		 	<c:if test="${wxUser.hzbOpenFlag eq 1 }">
		        <div class="ljcz">
		        	立即充值
		        </div>
		 	</c:if>
	        <script type="text/javascript">
	        	$(function(){
	        		$(".ljcz").click(function(){
	        			if(zfflag){
		        			self.location.href="${path}/m/my/hzb_cz.do";
	        			}else{
	        				layer.msg("该功能尚未开通")
	        			}
	        		});
	        	});
	        </script>
    </body>
</html>