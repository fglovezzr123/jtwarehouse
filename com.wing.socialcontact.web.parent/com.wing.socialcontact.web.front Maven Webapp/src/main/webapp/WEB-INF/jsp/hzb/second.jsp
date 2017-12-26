<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">互助宝会员-选择开通等级</title>
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
          		font-size: .40rem;
          		display: flex;
          		justify-content: space-between;
          		align-items: center;
          		/* margin-top: .40rem; */
          		margin-bottom: .20rem;
          		box-sizing:border-box;
          		font-weight: bold;
          	}
          	.hzb-header span:nth-of-type(2){
          		color: #0F88EB;
          	}
			._rank{
				width: 100%;
				background: #fff;
				font-size: .28rem;
				/* padding-bottom: .9rem; */
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
				width: 6rem;
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
			.zczf{
				width: 100%;
				padding: 0 .3rem;
				height: .84rem;
				background: #fff;
				font-size: .28rem;
				border-bottom: #F2F3F4 solid 1px;
				line-height: .84rem;
				margin-top: 0.2rem;
				box-sizing:border-box;
			}
			.check{
				width: 100%;
				padding-left: .30rem;
				box-sizing:border-box;
				
				background: #fff;
			}
			.check .c-bottom{
				padding-right: .30rem;
				font-size: .28rem;
				height: 1rem;
				border-bottom: #F2F3F4 solid 1px;
				display: flex;
				align-items: center;
				box-sizing:border-box;
			}
			input[type="radio"]{
				-webkit-appearance: none;
				width: .39rem;
				height: 0.39rem;
				margin-top: .65rem;
				border-radius:100%;
	           	background:url(${path}/resource/img/icons/radio.png) no-repeat;
	           	background-size: 100%;
			}
			input[type="radio"]:checked{
	           background:url(${path}/resource/img/icons/radiocheck.png) no-repeat;
	        	 background-size: 100%;
			}
			.check span{
				margin-left: .25rem;
			}
			.zfmaney{
				width: 100%;
				background:#fff;
				padding: .1rem .30rem .50rem .30rem;
				font-size: .30rem;
				box-sizing:border-box;
			}
			.zfmaney p{
				line-height: .90rem;
			}
			.zfmaney input{
				width: 5.8rem;
				height: .65rem;
				background: #ececec;
				padding-left: .1rem;
				box-sizing:border-box;
				
			}
			.zfmaney label{
				margin-left:.2rem ;
			}
			.fumaney{
				width: 100%;
				background: #fff;
				font-size: .28rem;
				margin-top: 0.2rem;
				padding-bottom: .9rem;
				box-sizing:border-box;
			}
			.fumaney .fuk{
				width: 100%;
				height: .84rem;
				background: #fff;
				font-size: .30rem;
				border-bottom: #F2F3F4 solid 1px;
				line-height: .84rem;
				padding:0 .3rem;
				box-sizing:border-box;
				
			}
			.fumaney .fklx{
				width: 100%;
				padding-left: 0.3rem;
				padding-top: .20rem;
				font-size: .28rem;
				color: #8B8B8B;
				box-sizing:border-box;
			}
			.fumaney .fklx p{
				width: 100%;
				line-height: .56rem;
				padding-right: .30rem;
				display: flex;
				justify-content: space-between;
				box-sizing:border-box;
			}
			.fumaney .fklx p:nth-of-type(3){
				border-bottom: #F2F3F4 solid 1px;
				padding-bottom: 0.2rem;
				box-sizing:border-box;
			}
			.zfpz{
				display: flex;
				justify-content: space-between;
				padding-right: .30rem;
				box-sizing:border-box;
			}
			.zfpz span:nth-of-type(2){
				display: block;
				width: 2.28rem;
				height: 1.4rem;
				background: #eaeaea;
			}
			.zfq{
				width: 100%;
			   padding: .30rem .30rem .26rem .3rem;
			   background: #fff;
			   font-size: .28rem;
			   display: none;
			   box-sizing:border-box;
			}
			.zfq .item{
				display: flex;
				align-items: center;
				height: .70rem;
				justify-content: space-between;
				margin-top: .18rem;
			}
			.zfq .item input{
				width: 5.35rem;
				background: #ececec;
				height: .70rem;
				font-size: .28rem;
				padding-left: .1rem;
				box-sizing:border-box;
				
			}
			.zfq .item:nth-of-type(1){
				margin-top: 0;
			}
			.zfq .item p{
				width: 2.25rem;
				height: .6rem;
				line-height: .60rem;
				background: #ececec;
				font-size: .28rem;
				text-align: center;
				margin-right: 3.1rem;
				color: #808080;
			}
          </style>
    </head>
    <body>
		 	<div class="hzb-header">
		 		<span>选择开通等级</span>
		 	</div>
		 	<div class="_rank">
		 		<div class="kt">
		 			<span class="_left"><input type="radio" name="level" value="1" style="vertical-align: middle;" checked="checked" /></span>
		 			<div class="_cont">
		 				<p>黄金等级</p>
		 				<p>获得方式：</p>
		 				<p>1、单笔互助宝充值20W，承诺年度消费累计20W</p>
		 			</div>
		 		</div>
		 		<div class="kt">
		 			<span class="_left"><input type="radio" name="level" value="2" /></span>
		 			<div class="_cont">
		 				<p>白金等级</p>
		 				<p>获得方式：</p>
		 				<p>1、年度累计互助宝充值50W，承诺年度消费累计40W</p>
		 			</div>
		 		</div>
		 		<div class="kt">
		 			<span class="_left"><input type="radio" name="level" value="3"/></span>
		 			<div class="_cont">
		 				<p>钻石等级</p>
		 				<p>获得方式：</p>
		 				<p>1、年度累计互助宝充值100W，承诺年度消费累计80W</p>
		 			</div>
		 		</div>
		 	</div>
	        <div class="in-cz">
	        	立即开通
	        </div>
	        <script type="text/javascript">
	        	$(function(){
		        	$('.kt').on('click',function(e){
		        		$(this).find('input[type=radio]').prop('checked',true)
	             	});
		        	$(".in-cz").click(function(){
		        		var level=$(":radio[name='level']:checked").prop("value");
		        		self.location.href="${path}/m/hzb/third.do?level="+level;
		        	});
	        	});
	        </script>
    </body>
</html>