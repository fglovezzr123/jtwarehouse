<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0" name="viewport">
        <title id="title">我的钱包</title>
        <style type="text/css">
        	/*公共样式部分*/
		   body{
       		width: 100%;
      		background: #F2F3F4;
           }
           .top{
           	width: 100%;
           	height: 2.25rem;
           	padding: 0 .30rem;
           	box-sizing: border-box;
           	font-size: .26rem;
           	color: #fff;
           	display: flex;
           	background: #0F88EB;
           }
           .top .maney{
           	width: 50%;
           }
           .top .maney span{
           	display: block;
           }
           .top .maney span:nth-of-type(1){
              margin-top: .70rem;
           }
           .top .maney span:nth-of-type(2){
           	font-size: .48rem;
           	 margin-top: .10rem;
           }
           .top .maney.jb{
           	margin-left: .80rem;
           }
           .tx{
           	width: 100%;
           	height: 1rem;
           	background: #fff;
           	display: flex;
           	box-sizing: border-box;
           	font-size: .30rem;  
           	padding: 0 .30rem;
            }
            .tx a{
            	width: 50%;
            	height:1rem;
            	line-height: 1rem;
            	text-align: center;
            	box-sizing: border-box;
            }
            .tx a+a{
            	border-left:  1px #F2F3F4 solid;
            }
            .tx a:nth-of-type(1){
            	background: url(${path}/resource/img/icons/cz.png) no-repeat center;
            	background-size: .49rem;
            	background-position-x: .80rem;
            }
            .tx a:nth-of-type(2){
            	background: url(${path}/resource/img/icons/tx.png) no-repeat center;
            	background-size: .49rem;
            	background-position-x: .80rem;
            }
            .jy{
            	width: 100%;
            	padding-left: .30rem;
            	box-sizing: border-box;
            	font-size: .30rem;
            	background: #fff;
            	margin-top: .20rem;
            }
            .jy .jf,.yhq{
            	display: flex;
            	width: 100%;
            	height: 1rem;
            	justify-content: space-between;
            	align-items: center;
            	border-bottom: 1px #F2F3F4 solid;
            	padding-right: .30rem;
            	box-sizing: border-box;
            }
            .jy .jf span:nth-of-type(1){
            	background: url(${path}/resource/img/icons/my.png) no-repeat center;
            	background-size: .38rem;
            	background-position-x: 0;
            	padding-left: .68rem;
            }
            .jy .jf span:nth-of-type(2){
            	color: #808080;
            	background: url(${path}/resource/img/icons/arrow.png) no-repeat right center;
            	background-size: .38rem;
            	padding-right: .38rem;
            }
             .jy .yhq span:nth-of-type(1){
            	background: url(${path}/resource/img/icons/qb.png) no-repeat center;
            	background-size: .38rem;
            	background-position-x: 0;
            	padding-left: .68rem;
            }
            .jy .yhq span:nth-of-type(2){
            	color: #808080;
            	background: url(${path}/resource/img/icons/arrow.png) no-repeat right center;
            	background-size: .38rem;
            	padding-right: .38rem;
            }
            .hzbsj{
            	width: 100%;
            	padding: 0 .30rem;
            	padding-bottom: .38rem;
            	box-sizing: border-box;
            	font-size: .26rem;
            	background: #fff;
            }
            .hzbsj p{
            	line-height: .90rem;
            	display: flex;
            	justify-content: space-between;
            }
            .hzbsj p span:nth-of-type(2){
            	color: orange;
            }
            .hzbsj .erd{
	            width: 100%;
	           	height: 1rem;
	           	background: #fff;
	           	display: flex;
	           	box-sizing: border-box;
	           	font-size: .24rem;  
            }
            .hzbsj .erd .zed{
            	box-sizing: border-box;
            	width: 50%;
            	text-align: center;
            }
            .hzbsj .erd .zed+.zed{
            	border-left: 1px #F2F3F4 solid;
            }
            .hzbsj .erd .zed span{
            	display: block;
            }
            .hzbsj .erd .zed span:nth-of-type(1){
            	line-height: .60rem;
            	font-size: .38rem;
            	color: #ff811b;
            }
        </style>
    </head>
    <body>
        <div class="top">
        	<div class="maney">
        		<span>金额(元)</span>
        		<span>310000</span>
        	</div>
        	<div class="maney jb">
        		<span>J币</span>
        		<span>3100</span>
        	</div>
        </div>
        <div class="tx">
        	<a href="#">充值</a>
        	<a href="#">提现</a>
        </div>
        <div class="jy">
        	<div class="jf">
        		<span>积分</span>
        		<span> ${user.integral_total}</span>
        	</div>
        	<div class="yhq">
        		<span>优惠券</span>
        		<span>504</span>
        	</div>
        </div>
        <div class="hzbsj">
        	<p><span>互助宝授信金</span><span>黄金级</span></p>
	        <div class="erd">
	        	<div class="zed">
	        		<span>300,00.00</span>
	        		<span>总金额(元)</span>
	        	</div>
	        	<div class="zed">
	        		<span>300,00.00</span>
	        		<span>可用额度(元)</span>
	        	</div>
	        </div>
        </div>
    </body>
</html>