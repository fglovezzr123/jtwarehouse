<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">互助宝会员介绍</title>
        <style type="text/css">
		       body{
		          	width: 100%;
		         	background: #F2F3F4;
		       }
		       .hzwhat{
		       	width: 100%;
		       	padding: 0.24rem 0.3rem .28rem .30rem;
		       	background: #fff;
		       	font-size: .30rem;
		       	box-sizing: border-box;
		       }
		       .hzwhat p:nth-of-type(1){
		       	line-height: .50rem;
		       }
		       .hzwhat p:nth-of-type(2){
		       	font-size: .26rem;
		       	color: #808080;
		       	line-height: .50rem;
		       }
		       .hzyh{
		       	width: 100%;
		       	padding: .1rem .30rem .14rem;
		       	background: #fff;
		       	font-size: .26rem;
		       	margin-top: .20rem;
		       	box-sizing: border-box;
		       }
		       .hzyh p.wt{
		       	line-height: .60rem;
		       	font-size: .30rem;
		       }
		       .hzyh p{
		       	line-height: .50rem;
		       }
		       div.hzyh{
		       	border-bottom: 1px solid #F2F3F4;
		       }
		       ._cont{
				width: 100%;
				color: #808080;
				padding: 0 .30rem;
				padding-top: .10rem;
				font-size: .28rem;
				background: #fff;
				box-sizing: border-box;
			}
			._cont p.hei{
				color: #3b3b3b;
			}
			._cont p{
				line-height: .50rem;
			}
			._cont p:nth-of-type(1){
				line-height: .60rem;
			}
   		</style>
    </head>
    <body>
		 <div class="hzwhat">
		 	<p>互助宝是什么？</p>
		 	<p>互助宝是天九云打造的一个高端用户之间的诚信交易空间。</p>
		 </div>
		 <div class="hzyh">
		 	<p class="wt">成为互助宝用户能够得到什么？</p>
		 	<p>在天九云平台向其他互助宝用户出售商品；</p>
		 	<p>在天九云平台以互助宝价格购买其他互助宝用户的商品；</p>
		 	<p>参加平台组织的各类互助宝会员活动；</p>
		 	<p>获得平台互助宝特殊勋章，享受以一系列互助宝尊享特权</p>
		 </div>
		 <div class="hzyh">
		 	<p class="wt">如何加入互助宝？</p>
		 	<p>在天九云平台通过互助宝充值一定额度并承诺每年的消费额度，即可成为互助宝会员。</p>
		 </div>
		 <div class="hzyh">
		 	<p class="wt">互助宝的分级：</p>
		 	<p>互助宝用户分为三级，分别充值：20W、50W、100W</p>
		 	<p>会员有效期1年，达到标准自动获得1年会员身份。</p>
		 </div>
	     <div class="_cont">
			<p class="hei">黄金级</p>
			<p>获得方式：</p>
			<p>1、单笔互助宝充值20W，承诺年度消费累计20W</p>
		 </div> 
		 <div class="_cont">
			<p class="hei">白金级</p>
			<p>获得方式：</p>
			<p>1、年度累计互助宝充值50W，承诺年度消费累计40W</p>
		 </div> 
		 <div class="_cont" style="padding-bottom: .90rem;">
			<p class="hei">钻石级</p>
			<p>获取方式：</p>
			<p>1、年度累计互助宝充值100W，承诺年度消费累计80W</p>
		 </div> 
		 <div class="in-cz">
		 	开通互助宝
	     </div>
	     <script type="text/javascript">
	     	$(function(){
	     		$(".in-cz").click(function(){
	     			self.location.href="${path}/m/hzb/second.do";
	     		});
	     	});
	     </script>
    </body>
</html>