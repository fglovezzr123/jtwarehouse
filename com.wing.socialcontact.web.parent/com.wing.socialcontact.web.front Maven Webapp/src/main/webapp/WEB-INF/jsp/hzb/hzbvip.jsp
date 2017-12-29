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
		 		<span>已开通</span>
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
		 			<span class="item_left">累计充值:</span>
		 			<div class="item_right">
		 				20w
		 			</div>
		 		</div>
		 		<div class="r-item">
		 			<span class="item_left">累计消费:</span>
		 			<div class="item_right">
		 				15w
		 			</div>
		 		</div>
		 		<div class="r-item">
		 			<span class="item_left">互助宝余额:</span>
		 			<div class="item_right">
		 				2349877666454432W
		 			</div>
		 		</div>
		 	</div>
		 	
	        <div class="ljcz">
	        	立即充值
	        </div>
	        
    </body>
</html>