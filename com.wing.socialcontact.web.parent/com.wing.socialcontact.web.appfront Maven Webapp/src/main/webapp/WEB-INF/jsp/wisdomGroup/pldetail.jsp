<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">解惑详情</title>
        <style type="text/css">
        	body{
        		background: #F3F4F5;
        	}
            .item{
            	width: 100%;
            	padding-top: .20rem;
            	background: #fff;
            	position: relative;
            }
            .item .i-top{
            	width: 100%;
            	display: flex;
            	box-sizing: border-box;
            	padding: 0 0.3rem;
            }
            .item .i-top img{
            	width: 0.73rem;
            	height: 0.73rem;
            	
            }
            .item .i-top .t-right{
            	font-size: .20rem;
            	color: #B8B8B8;
            	padding-left: 0.2rem;
            	
            }
            .item .i-top .t-right p:nth-of-type(1){
            	font-size: .30rem;
            	color: #3b3b3b;
            	line-height: .46rem;
            }
            .item .i-top .t-right p:nth-of-type(2){
            	line-height: .40rem;
            }
             .item h2{
             	width: 100%;
             	box-sizing: border-box;
             	padding: 0 .30rem;
             	font-size: .30rem;
             	line-height: .70rem;
             	border-bottom: #F3F4F5 solid 1px;
             }
             .item .icon{
             	width: 100%;
             	height:.70rem;
             	padding:0 0.3rem;
             	box-sizing: border-box;
             	display: flex;
             	justify-content: space-between;
             	align-items: center;
             	font-size: .24rem;
             }
             .item .icon span:nth-of-type(2){
             	background: url(${path}/resource/img/icons/zhuge-a8.png) no-repeat left center;
        		background-size: 0.33rem;
        		color: #f8ce61;
        		padding-left: .40rem;
             }
             .item .seft{
             	position: absolute;
             	font-size: .20rem;
             	color: #B8B8B8;
             	
             	text-align: center;
             	line-height: .50rem;
             	top: .20rem;
             	right: .30rem;
             }
             .items{
             	margin-top: .20rem;
             }
             .items .list{
             	width: 100%;
             	padding-top: .20rem;
             		display: flex;
             	background: #fff;
             	position: relative;
             }
             .list img{
             	width: 0.73rem;
            	height: 0.73rem;
            	padding-left: .30rem;
             }
             .list .right{
             	width: calc(100% - 1.03rem);
             	padding-left: 0.2rem;
             }
             .list .right p:nth-of-type(1){
             	font-size: .30rem;
            	color: #3b3b3b;
            	line-height: .46rem;
            	box-sizing: border-box;
             	padding-right: .30rem;
             }
             .list .right p:nth-of-type(2){
             	font-size: .20rem;
            	line-height: .40rem;
            	box-sizing: border-box;
             	padding-right: .30rem;
             }
             .list .right h2{
             	box-sizing: border-box;
             	padding-right: .30rem;
             	font-size: .28rem;
             	line-height: .46rem;
             }
             .icons{
             	height:.46rem;
             	box-sizing: border-box;
             	padding-right: .30rem;
             	padding-bottom: .1rem;
             	display: flex;
             	justify-content: space-between;
             	align-items: center;
             	font-size: .24rem;
             	border-bottom: #F3F4F5 solid 1px;
             }
             .icons span:nth-of-type(2){
             	background: url(${path}/resource/img/icons/zhuge-a9.png) no-repeat left center;
        		background-size: 0.26rem;
        		padding-left: .40rem;
             }
             .list .cn{
             	width: .86rem;
             	height: .73rem;
             	position: absolute;
             	top:0;
             	right: 0;
             }
             .hf{
             	padding: 0.2rem 0;
             	border-bottom: #F3F4F5 solid 1px;
             }
             .hf b{
             	color: #2f9ff2;
             	font-style: normal;
             }
             .hf em{
             	color: #B8B8B8;
             }
        </style>
    </head>
    <body>
    	<div class="item">
    		<div class="i-top">
    			<img src="${path}/resource/img/icons/zxt_03.png"/>
    			<div class="t-right">
    				<p>金利福</p>
    				<p>100分钟以前&nbsp;&nbsp;法人/北京人艺有限公司</p>
    			</div>
    		</div>
    		<h2>在马里摇杆生呢</h2>
    		<div class="icon">
    			<span>悬赏时间：2017-01-17至201705-17</span>
    			<span>200</span>
    		</div>
    		<span class="seft">2017-16</span>
    	</div>
    	<div class="items">
    		<div class="list">
    			<img src="${path}/resource/img/icons/zxt_03.png"/>
    			<div class="right">
    				<p>金利福</p>
    				<p>100分钟以前&nbsp;&nbsp;法人/北京人艺有限公司</p>
    				<h2>在马里摇杆生呢在马里摇杆生呢在马里摇杆生呢在马里摇杆生呢</h2>
    				<div class="icons">
		    			<span>悬赏时间：2017-01-17至201705-17</span>
		    			<span>200</span>
    		        </div>
    		        <div class="hf">
    		        	<p><b>王石&nbsp;</b>回复<em>&nbsp;刘子健</em>：</p>
    		        	<p>100分钟以前&nbsp;&nbsp;法人/北京人艺有限公司法人/北京人艺有限公司法人/北京人艺有限公司</p>
    		        </div>
    		        <div class="hf">
    		        	<p><b>王石&nbsp;</b>回复<em>&nbsp;刘子健</em>：</p>
    		        	<p>100分钟以前&nbsp;&nbsp;法人/北京人艺有限公司法人/北京人艺有限公司法人/北京人艺有限公司</p>
    		        </div>
    			</div>
    		</div>
    	</div>
    </body>
</html>