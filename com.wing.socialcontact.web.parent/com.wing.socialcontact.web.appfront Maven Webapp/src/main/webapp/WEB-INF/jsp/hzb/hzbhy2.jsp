<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
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
		 		<span>互助宝状态：</span>
		 		<span>待支付尾款</span>
		 	</div>
		 	<div class="_rank">
		 		<div class="kt">
		 			<span class="_left">开通等级:</span>
		 			<div class="_cont">
		 				<p>黄金等级</p>
		 				<p>获取方式：</p>
		 				<p>1.电笔互殴曲靖今个超过20再接再了个超过20再接再了</p>
		 			</div>
		 		</div>
		 		<div class="r-item">
		 			<span class="item_left">已支付:</span>
		 			<div class="item_right">
		 				20w
		 			</div>
		 		</div>
		 		<div class="r-item">
		 			<span class="item_left">待支付:</span>
		 			<div class="item_right">
		 				15w
		 			</div>
		 		</div>
		 		<div class="r-item">
		 			<span class="item_left">对公账号:</span>
		 			<div class="item_right">
		 				2349877666454432
		 			</div>
		 		</div>
		 	</div>
		 	<p class="zczf">再次支付</p>
		 	<div class="check">
		 		<div class="c-bottom">
		 			<input type="radio" name="xs" id="xs" value="" checked="checked"/ ><span >线上支付</span>
		 		</div>
		 	</div>
		 	<div class="check">
		 		<div class="c-bottom">
		 			<input type="radio" name="xs" id="xs" value=""/><span >线下支付</span>
		 		</div>
		 	</div>
		 	<div class="zfmaney">
		 		<p>请填写本次支付金额：</p>
		 		<input type="text" id="" value="" placeholder="请输入金额"/><label for="">W元</label>
		 	</div>
		 	<div class="zfq">
		 		<div class="item">
		 			<span>支付金额:</span>
		 			<input type="number" name="" id="" value="" placeholder="请输入金额"/>
		 		</div>
		 		<div class="item">
		 			<span>支付时间:</span>
		 			<input type="number" name="" id="" value="" />
		 		</div>
		 		<div class="item">
		 			<span>支付凭证:</span>
		 			<p>上传图片</p>
		 		</div>
		 	</div>
		 	<div class="fumaney">
		 		<p class="fuk">付款记录</p>
		 		<div class="fklx">
		 			<p><span>线上付款:</span><span>已确认</span></p>
		 			<p><span>支付金额:</span><span>28w</span></p>
		 			<p><span>付款时间:</span><span>2013-06-04</span></p>
		 		</div>
		 		<div class="fklx">
		 			<p><span>线下付款:</span><span>已确认</span></p>
		 			<p><span>支付金额:</span><span>28w</span></p>
		 			<div class="zfpz"><span>支付金额</span><span></span></div>
		 			<p><span>付款时间:</span><span>2013-06-04</span></p>
		 		</div>
		 		<div class="fklx">
		 			<p><span>线下付款:</span><span>已确认</span></p>
		 			<p><span>支付金额:</span><span>28w</span></p>
		 			<div class="zfpz"><span>支付金额</span><span></span></div>
		 			<p><span>付款时间:</span><span>2013-06-04</span></p>
		 		</div>
		 	</div>
	        <div class="in-cz">
	        	确认支付
	        </div>
	        <script type="text/javascript">
	        	$('.check').on('click',function(e){
	        		$(this).find('input[type=radio]').prop('checked',true)
	        		if($(this).find('span').text()=='线上支付'){
	        			$('.zfmaney').show()
	        			$('.zfq').hide()
	        		}else{
	        			$('.zfmaney').hide()
	        			$('.zfq').show()
	        		}
             	})
	        </script>
    </body>
</html>