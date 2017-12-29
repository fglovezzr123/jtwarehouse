<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">邀请记录</title>
          <style type="text/css">
          	.wrapper{
          		width: 100%;
          		padding-left: .30rem;
          		box-sizing: border-box;
          		padding-bottom:0.9rem ;
          	}
          	.item{
          		width: 100%;
          		height: .90rem;
          		box-sizing: border-box;
          		padding-right: .30rem;
          		background: #fff;
          		display: flex;
          		font-size: .24rem;
          		justify-content: space-between;
          		align-items: center;
          		border-bottom: 1px solid #f2f3f4;
          		color: #808080;
          	}
          	.item span:nth-of-type(1){
          		color: #3B3B3B;
          		font-size: .30rem;
          	}
          	.item span:nth-of-type(2){
          		
          		margin-left: 2rem;
          	}
          	.in-cz{
          		width: 100%;
          		height: .90rem;
          		font-size: .30rem;
          		background: #0F88EB;
          		text-align: center;
          		line-height: .90rem;
          		position: fixed;
          		bottom: 0;
          		left: 0;
          		color: #fff;
          	}
          </style>
    </head>
    <body>
		 <div class="wrapper">
		 	<div class="item">
		 		<span>邀请记录</span>
		 		<span>2017-08-09</span>
		 		<span>邀请成功</span>
		 	</div>
		 	<div class="item">
		 		<span>邀请记录</span>
		 	</div>
		 	<div class="item">
		 		<span>邀请记录</span>
		 	</div>
		 	
		 </div>
        <div class="in-cz">
        	发送邀请
        </div>
    </body>
</html>