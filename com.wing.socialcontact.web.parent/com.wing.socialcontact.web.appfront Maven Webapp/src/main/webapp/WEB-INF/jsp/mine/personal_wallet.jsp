<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">我的钱包</title>
        <link rel="stylesheet" href="${path}/resource/css/my-wallet.css" />
    </head>
    <body>
    	<div class="top">
        	<div class="maney" onclick="wallet_mx(1);">
        		<span>余额(元)</span>
        		<span><c:choose><c:when test="${empty user.availablebalance}">0</c:when><c:otherwise><fmt:formatNumber type="number" value="${user.availablebalance}" maxFractionDigits="2"></fmt:formatNumber></c:otherwise></c:choose></span>
        	</div>
        	<div class="maney jb" onclick="wallet_mx(2);">
        		<span>J币</span>
        		<span><c:choose><c:when test="${empty user.jbAmount}">0</c:when><c:otherwise><fmt:formatNumber type="number" value="${user.jbAmount}" maxFractionDigits="0"></fmt:formatNumber></c:otherwise></c:choose></span>
        	</div>
        </div>
        <div class="tx">
        	<a href="javascript:cz();">充值</a>
        	<a href="javascript:tx();">提现</a>
        </div>
        <div class="jy">
        	<div class="jf" onclick="integral_mx();" style="display:none;">
        		<span>积分</span>
        		<span>${user.integralTotal}</span>
        	</div>
        	<div class="jf" onclick="integral_mx1();" style="display:none;">
        		<span>积分规则</span>
        		<span>&nbsp;</span>
        	</div>
        	<div class="yhq" onclick="myCoupon();">
        		<span>优惠券</span>
        		<span>${ccount}</span>
        	</div>
        </div>
        <div class="hzbsj" onclick="wallet_mx(3);">
        	<p>
        		<span>互助宝授信金</span>
        		<c:choose>
					<c:when test="${user.hzbOpenFlag eq 0 }">
						<span>未开通</span>
					</c:when>
					<c:when test="${user.hzbOpenFlag eq 1 }">
						<c:choose>
							<c:when test="${user.hzbLevel eq 1 }">
			        			<span>黄金级</span>
							</c:when>
							<c:when test="${user.hzbLevel eq 2 }">
				        		<span>白金级</span>
							</c:when>
							<c:otherwise>
								<span>钻石级</span>
							</c:otherwise>
		        		</c:choose>
					</c:when>
					<c:when test="${user.hzbOpenFlag eq 2 }">
						<c:choose>
							<c:when test="${user.hzbLevel eq 1 }">
			        			<span>黄金级-已停用</span>
							</c:when>
							<c:when test="${user.hzbLevel eq 2 }">
				        		<span>白金级-已停用</span>
							</c:when>
							<c:otherwise>
								<span>钻石级-已停用</span>
							</c:otherwise>
		        		</c:choose>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${user.hzbLevel eq 1 }">
			        			<span>黄金级-已过期</span>
							</c:when>
							<c:when test="${user.hzbLevel eq 2 }">
				        		<span>白金级-已过期</span>
							</c:when>
							<c:otherwise>
								<span>钻石级-已过期</span>
							</c:otherwise>
		        		</c:choose>
					</c:otherwise>
				</c:choose>
        	</p>
	        <div class="erd">
	        	<div class="zed">
	        		<span><c:choose><c:when test="${user.hzbOpenFlag eq 0 }">0</c:when><c:otherwise><c:choose><c:when test="${empty ljcz}">0</c:when><c:otherwise><fmt:formatNumber type="number" value="${ljcz}" maxFractionDigits="2"></fmt:formatNumber></c:otherwise></c:choose></c:otherwise></c:choose></span>
	        		<span>累充金额(互助币)</span>
	        	</div>
	        	<div class="zed">
	        		<span><c:choose><c:when test="${user.hzbOpenFlag eq 0 }">0</c:when><c:otherwise><c:choose><c:when test="${empty user.hzbAmount}">0</c:when><c:otherwise><fmt:formatNumber type="number" value="${user.hzbAmount}" maxFractionDigits="2"></fmt:formatNumber></c:otherwise></c:choose></c:otherwise></c:choose></span>
	        		<span>可用额度(互助币)</span>
	        	</div>
	        </div>
        </div>
       <script type="text/javascript">
       		function cz(){
       			if(zfflag){
       				self.location.href="${path}/m/my/wallet_cz.do";
				}else{
					layer.msg("该功能暂未开通");
				}
       		}
       		
       		function wallet_mx(type){
       			if(zfflag){
	       			if(type == 3){
	       				var hzbOpenFlag="${user.hzbOpenFlag}";
	           			if(isEmpty(hzbOpenFlag) || hzbOpenFlag == 0 || hzbOpenFlag == 3){
	           				self.location.href="${path}/m/hzb/first.do";
	           				return;
	           			}else if(hzbOpenFlag == 2){
	           				alert("您的互助宝账户已停用，请联系客服");
	           				return;
	           			}
		       			self.location.href="${path}/m/my/hzb_log.do";
	       			}else{
	       				self.location.href="${path}/m/my/wallet_log.do?type="+type;
	       			}
       			}else{
       				layer.msg("该功能暂未开通");
       			}
       		}
       		
       		function integral_mx(){
       			self.location.href="${path}/m/my/integral_log.do";
       		}
       		function integral_mx1(){
       			self.location.href="${path}/m/my/my_integral_info.do";
       		}
       		function tx(){
       			if(zfflag){
       				self.location.href="${path}/m/my/wallet_tx.do";
       			}else{
					layer.msg("该功能暂未开通");
				}
       		}
       		//我的优惠券
       		function myCoupon(){
       			self.location.href="${path}//m/coupon/myCouponPage.do";
       		}
       		$(function(){
       			if(screenflag){
       				$(".jf").hide();
       			}else{
       				$(".jf").show()
       			}
       		})
       </script>
    </body>
</html>