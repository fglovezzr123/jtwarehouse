<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="quheye">
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/swiper-3.3.1.min.css" />
		<script src="${path }/resource/js/libs/swiper-3.3.1.min.js" type="text/javascript" charset="utf-8"></script>
		<title>聚合页</title>
		<style type="text/css">
		/*公共样式部分*/
		html,body,div,p,h1,h2,h3,h4,h5,h6,a{
			box-sizing:border-box;
		}
		 body{
       		width: 100%;
      		background: #F2F3F4;
           }
			/* #banner{
				height: 3rem;
			} */
			.nav{
				width: 100%;
				padding: 0 0.3rem;
				height: 1.6rem;
				display: flex;
				justify-content: space-between;
				align-items: center;
				background: #fff;
			}
			.nav a{
				font-size: .24rem;
				display: flex;
                flex-direction: column;	
                align-items: center;	
             	}
             	.nav a img{
             		width: .76rem;
             		height: .76rem;
             		margin-bottom: .13rem;
             	}
             	.nav a:nth-of-type(1){
             		margin-left: .1rem;
             	}
             	.nav a:nth-of-type(5){
             		margin-right: .1rem;
             	}
             	/*公共样式部分              end*/   
             	/*栏目1*/
             	.lm1{
             		width: 100%;
             		background: #fff;
             		margin-top: .20rem;
             	}
             	.lm1 .lm1-header{
             		font-size: .28rem;
             		display: flex;
             		justify-content: space-between;
             		align-items: center;
             		padding: 0 0.3rem;
             		height: .70rem;
             		border-bottom: 1px #F2F3F4 solid;
             	}
             	.lm1 .lm1-header a{
             		font-size: .24rem;
				    color: #808080;
				    padding-right: 0.2rem;
    background: url(${path}/resource/img/icons/arrow.png) no-repeat center;
    background-size: .18rem .24rem;
    background-position-x: 0.5rem;
             	}
             	/*.lm1-box{
             		padding-bottom: .1rem;
             	}*/
             	.lm1-box .item{
             		border-bottom: #F2F3F4 solid 1px;
             	}
             	.lm1-box div:nth-last-of-type(1){
             		border: 0;
             	}
             	.lm1 .img1{
             		width: 100%;
             		padding: .25rem .30rem 0;
             		height: 4.37rem;
             	}
             	.lm1 .img1 img{
             		width: 100%;
             	}
             	.lm1 p{
             		padding:0 .3rem .0;
             		line-height: .65rem;
             		font-size: .26rem;
             	}
             	/*栏目名称2*/
             	.lm2{
             		width: 100%;
             		background: #fff;
             		margin-top: .20rem;
             	}
             	.lm2 .lm2-header{
             		font-size: .28rem;
             		display: flex;
             		justify-content: space-between;
             		align-items: center;
             		padding: 0 0.3rem;
             		height: .70rem;
             		border-bottom: 1px #F2F3F4 solid;
             	}
             	.lm2 .lm2-header a{
             		font-size: .24rem;
				    color: #808080;
				    padding-right: 0.2rem;
				    background: url(${path}/resource/img/icons/arrow.png) no-repeat center;
				    background-size: .18rem .24rem;
				    background-position-x: 0.5rem;
             	}
             	.lm2-box{
             		padding-bottom: .25rem;
             	}
             	.lm2 .img1{
             		width: 100%;
             		padding: .25rem .30rem 0;
             		height: 4.37rem;
             		position: relative;
             		top: 0;
             		left: 0;
             	}
             	.lm2 .img1 img{
             		width: 100%;
             	}
             	.lm2 .lm2-position{
             		font-size: .28rem;
             		position: absolute;
             		bottom: 0rem;
             		left: .30rem;
             		width: 6.9rem;
             		color: #fff;
             		background: rgba(0,0,0,0.6);
             		padding: .12rem 0.2rem;
             	}
             	.lm2 .lm2-position p{
             		width: 100%;
             		overflow: hidden;
             		text-overflow: ellipsis;
             		white-space: nowrap;
             		line-height: .40rem;
             	}
             	.lm2 .lm2-position p:nth-of-type(2){
             		font-size: .22rem;
             	}
             	/*栏目名称3*/
             	.lm3{
             		width: 100%;
             		background: #fff;
             		margin-top: .20rem;
             	}
             	.lm3 .lm3-header{
             		font-size: .28rem;
             		display: flex;
             		justify-content: space-between;
             		align-items: center;
             		padding: 0 0.3rem;
             		height: .70rem;
             		border-bottom: 1px #F2F3F4 solid;
             	}
             	.lm3 .lm3-header a{
             		font-size: .24rem;
				    color: #808080;
				    padding-right: 0.5rem;
				    padding-right: 0.2rem;
    background: url(${path}/resource/img/icons/arrow.png) no-repeat center;
    background-size: .18rem .24rem;
    background-position-x: 0.5rem;
             	}
             	.lm3 .img-text{
             		width: 100%;
             		padding: 0.25rem 0.3rem;
             		display: flex;
             		border-bottom: 1px solid #F2F3F4;
             	}
             	.lm3 .img-text .lm3-img{
             		width: 2.25rem;
             		height: 1.35rem;
             	}
             	.lm3 .img-text .lm3-img img{
             		height: 100%;
             	}
             	.lm3 .img-text .lm3-tx{
             		width: 4.65rem;
             		padding-left: .20rem;
             	}
             	.lm3 .img-text .lm3-tx h2{
             		font-weight: normal;
             		padding-top: .08rem;
             	   padding-bottom: .15rem;
             	   font-size: .30rem;
             	   overflow: hidden;
             	   white-space: nowrap;
             	   text-overflow: ellipsis;
             	}
             	.lm3 .img-text .lm3-tx p{
             		line-height: .36rem;
             		font-size: .24rem;
             		color: #808080;
             		word-break: break-all;
				    text-overflow: ellipsis;
				    display: -webkit-box;
				    -webkit-box-orient: vertical;
				    -webkit-line-clamp: 2;
				    overflow: hidden;
             	}
             	/*栏目名称4*/
             	.lm4{
             		width: 100%;
             		background: #fff;
             		margin-top: .20rem;
             		overflow: hidden;
             	}
             	.lm4 .lm4-header{
             		font-size: .28rem;
             		display: flex;
             		justify-content: space-between;
             		align-items: center;
             		padding: 0 0.3rem;
             		height: .70rem;
             		border-bottom: 1px #F2F3F4 solid;
             	}
             	.lm4 .lm4-header a{
             		font-size: .24rem;
				    color: #808080;
				   padding-right: 0.2rem;
    background: url(${path}/resource/img/icons/arrow.png) no-repeat center;
    background-size: .18rem .24rem;
    background-position-x: 0.5rem;
             	}
             	/*滑动区域*/
             	.swiper-container1{
             		height: 2.90rem;
             		font-size: .30rem;
             		padding-left: .30rem;
             	}
             	.swiper-container1 .swiper-slide{
             	  width: 2.5rem;
             	  padding-top: .25rem;
             	  padding-bottom: .20rem;
             	}
             	.swiper-container1 .swiper-slide img{
             		width: 2.5rem;
             		height: 1.5rem;
             		margin-bottom: .1rem;
             	}
             	.swiper-container1 .swiper-slide p{
             		overflow: hidden;
             		white-space: nowrap;
             		text-overflow: ellipsis;
             	}
             	.swiper-container1 .swiper-slide p:nth-of-type(1){
             		line-height: .46rem;
             		font-size: .28rem;
             	}
             	.swiper-container1 .swiper-slide p:nth-of-type(2){
             		line-height: .40rem;
             		font-size: .22rem;
             		color: #808080;
             	}
             	/*栏目名称5*/
             	.lm5{
             		width: 100%;
             		background: #fff;
             		margin-top: .20rem;
             	}
             	.lm5 .lm3-header{
             		font-size: .28rem;
             		display: flex;
             		justify-content: space-between;
             		align-items: center;
             		padding: 0 0.3rem;
             		height: .70rem;
             		border-bottom: 1px #F2F3F4 solid;
             	}
             	.lm5 .lm3-header a{
             		font-size: .24rem;
				    color: #808080;
				   padding-right: 0.2rem;
    background: url(${path}/resource/img/icons/arrow.png) no-repeat center;
    background-size: .18rem .24rem;
    background-position-x: 0.5rem;
             	}
             	.lm5 .img-text{
             		width: 100%;
             		padding: 0.25rem 0.3rem;
             		display: flex;
             		border-bottom: 1px solid #F2F3F4;
             	}
             	.lm5 .img-text .lm3-img{
             		width: 3.33rem;
             		height: 2.00rem;
             	}
             	.lm5 .img-text .lm3-img img{
             		height: 100%;
             	}
             	.lm5 .img-text .lm3-tx{
             		width: 3.57rem;
             		padding-left: .20rem;
             	}
             	.lm5 .img-text .lm3-tx h2{
             		font-weight: normal;
             	   font-size: .30rem;
             	   word-break: break-all;
				    text-overflow: ellipsis;
				    display: -webkit-box;
				    -webkit-box-orient: vertical;
				    -webkit-line-clamp: 3;
				    overflow: hidden;
				        height: 1.40rem;
                  line-height: .45rem;
             	   
             	}
             	.lm5 .img-text .lm3-tx p{
             		line-height: .45rem;
             		font-size: .24rem;
             		color: #808080;
             		overflow: hidden;
             	   white-space: nowrap;
             	   text-overflow: ellipsis;
             	}
             	/*栏目名称6*/
             	.lm6{
             		width: 100%;
             		background: #fff;
             		margin-top: .20rem;
             	}
             	.lm6 .lm6-header{
             		font-size: .28rem;
             		display: flex;
             		justify-content: space-between;
             		align-items: center;
             		padding: 0 0.3rem;
             		height: .70rem;
             		border-bottom: 1px #F2F3F4 solid;
             	}
             	.lm6 .lm6-header a{
             		font-size: .24rem;
				    color: #808080;
				   padding-right: 0.2rem;
    background: url(${path}/resource/img/icons/arrow.png) no-repeat center;
    background-size: .18rem .24rem;
    background-position-x: 0.5rem;
             	}
             	.lm6-cont {
             		width: 100%;
             		padding: 0 0.3rem;
             		display: flex;
             		justify-content: space-between;
             		flex-wrap: wrap;
             	}
             	.lm6-cont .item{
             		padding: .20rem 0 .10rem;
             	}
             	.lm6-cont .item .lm6-img{
             		width: 3.33rem;
             		height: 2.00rem;
             		margin-bottom: .1rem;
             	}
             	.lm6-cont .item .lm6-img img{
             		width: 100%;
             	}
             	.lm6-cont .item p{
             		white-space: nowrap;
             		overflow: hidden;
             		text-overflow: ellipsis;
             		line-height: .40rem;
             		font-size: .28rem;
             	}
             	
             	.lm6-cont .item p:nth-of-type(2){
             		font-size: .22rem;
             		color: #808080;
             	} 
            	/*栏目名称7*/
             	.lm7{
             		width: 100%;
             		background: #fff;
             		margin-top: .20rem;
             	}
             	.lm7 .lm6-header{
             		font-size: .28rem;
             		display: flex;
             		justify-content: space-between;
             		align-items: center;
             		padding: 0 0.3rem;
             		height: .70rem;
             		border-bottom: 1px #F2F3F4 solid;
             	}
             	.lm7 .lm6-header a{
             		font-size: .24rem;
				    color: #808080;
				   padding-right: 0.2rem;
					    background: url(${path}/resource/img/icons/arrow.png) no-repeat center;
					    background-size: .18rem .24rem;
					    background-position-x: 0.5rem;
             	}
             	.lm7-cont {
             		width: 100%;
             		padding-left: .30rem;
             		display: flex;
             		flex-wrap: wrap;
             	}
             	.lm7-cont .item{
             		padding: .25rem 0 .10rem;
             	}
             	.lm7-cont .item{
             		margin-right: .22rem;
             	}
             	.lm7-cont .item .lm7-img{
             		width: 2.15rem;
             		height: 1.35rem;
             	}
             	.lm7-cont .item .lm7-img img{
             		width: 100%;
             	} 
             	.lm7-cont .item p{
             		white-space: nowrap;
             		overflow: hidden;
             		text-overflow: ellipsis;
             		line-height: .65rem;
             		font-size: .28rem;
             	}
		</style>
	</head>
	<body>
		<!--公共区域banner和nav快速导航-->
		<div class="swiper-container banner" id="banner">
			<ul class="swiper-wrapper banner_ul">
				<li class="swiper-slide"><img src="${path}/resource/img/icons/qhbanner.png"" /></li>
				<li class="swiper-slide"><img src="${path}/resource/img/images/banner/bannerPk_02.png"" /></li>
				<li class="swiper-slide"><img src="${path}/resource/img/icons/qhbanner.png"" /></li>
				<li class="swiper-slide"><img src="${path}/resource/img/images/banner/bannerPk_02.png"" /></li>
			</ul>
			<div class="swiper-pagination"></div>
		</div>
		<div class="nav">
			<a href="#">
				<img src="${path}/resource/img/icons/tqfh.png"/>
				<span>投洽峰会</span>
			</a>
			<a href="#">
				<img src="${path}/resource/img/icons/xmly.png"/>
				<span>项目联营</span>
			</a>
			<a href="#">
				<img src="${path}/resource/img/icons/xmzj.png"/>
				<span>项目征集</span>
			</a>
			<a href="#">
				<img src="${path}/resource/img/icons/jjtz.png"/>
				<span>基金投资</span>
			</a>
			<a href="#">
				<img src="${path}/resource/img/icons/jflm.png"/>
				<span>金服联盟</span>
			</a>
		</div>
		<!--公共区域banner和nav快速导航                       end-->
		<!--栏目名称1-->
		<div class="lm1">
			<div class="lm1-header">
				<span>栏目名称</span>
				<a href="#">更多</a>
			</div>
			<div class="lm1-box">
				<div class="item">
					<div class="img1">
					  <img src="${path}/resource/img/icons/lm1.png"/>
				    </div>
				   <p>北京第二节叫例会北京第二节叫</p>
			    </div>
			    <div class="item">
					<div class="img1">
					  <img src="${path}/resource/img/icons/lm1.png"/>
				    </div>
				   <p>北京第二节叫例会北京第二节叫</p>
			    </div>
			    <div class="item">
					<div class="img1">
					  <img src="${path}/resource/img/icons/lm1.png"/>
				    </div>
				   <p>北京第二节叫例会北京第二节叫</p>
			    </div>
			</div>
			
		</div>
		<!--栏目名称2-->
		<div class="lm2">
			<div class="lm2-header">
				<span>栏目名称</span>
				<a href="#">更多</a>
			</div>
			<div class="lm2-box">
				<div class="img1">
				<img src="${path}/resource/img/icons/lm2.png"/>
				<div class="lm2-position">
					<p>北京放大来看极乐空间老地方</p>
					<p>北京放大来看极乐空间老地方</p>
				</div>
			</div>
			<div class="img1">
				<img src="${path}/resource/img/icons/lm2.png"/>
				<div class="lm2-position">
					<p>北京放大来看极乐空间老地方</p>
					<p>北京放大来看极乐空间老地方</p>
				</div>
			</div>
			</div>
			
		</div>
		<!--栏目名称3-->
		<div class="lm3">
			<div class="lm3-header">
				<span>栏目名称</span>
				<a href="#">更多</a>
				
			</div>
			<div class="img-text">
				<div class="lm3-img">
					<img src="${path}/resource/img/icons/lm3-1.png"/>
				</div>
				<div class="lm3-tx">
					<h2>本基金上的法律乐扣乐扣乐扣乐扣</h2>
					<p>dfkkjfldklkklfda费德勒尽快离开家里负担 拉费德勒快快乐乐开放考虑考虑快快乐乐开放考虑考虑快快乐乐开放考虑考虑</p>
				</div>
			</div>
			<div class="img-text">
				<div class="lm3-img">
					<img src="${path}/resource/img/icons/lm3-2.png"/>
				</div>
				<div class="lm3-tx">
					<h2>本基金上的法律乐扣乐扣乐扣乐扣</h2>
					<p>dfkkjfldklkklfda费德勒尽快离开家里负担 拉费德勒快快乐乐开放考虑考虑快快乐乐开放考虑考虑快快乐乐开放考虑考虑</p>
				</div>
			</div>
			<div class="img-text">
				<div class="lm3-img">
					<img src="${path}/resource/img/icons/lm3-3.png"/>
				</div>
				<div class="lm3-tx">
					<h2>本基金上的法律乐扣乐扣乐扣乐扣</h2>
					<p>dfkkjfldklkklfda费德勒尽快离开家里负担 拉费德勒快快乐乐开放考虑考虑快快乐乐开放考虑考虑快快乐乐开放考虑考虑</p>
				</div>
			</div>
		</div>
		<!--栏目名称4-->
		<div class="lm4">
			<div class="lm4-header">
				<span>栏目名称</span>
				<a href="#">更多</a>
			</div>
			<div class="swiper-container1">
				<ul class="swiper-wrapper">
					<li class="swiper-slide">
						<img src="${path}/resource/img/icons/lm4-1.png"/>
						<p>清晨阳关</p>
						<p>清晨阳关加快对方尽快尽快</p>
					</li>
					<li class="swiper-slide"><img src="${path}/resource/img/icons/lm4-1.png"/>
						<p>清晨阳关</p>
						<p>清晨阳关加快对方尽快尽快</p></li>
					<li class="swiper-slide">
						<img src="${path}/resource/img/icons/lm4-1.png"/>
						<p>清晨阳关</p>
						<p>清晨阳关加快对方尽快尽快</p>
					</li>
					<li class="swiper-slide">
						<img src="${path}/resource/img/icons/lm4-1.png"/>
						<p>清晨阳关</p>
						<p>清晨阳关加快对方尽快尽快</p>
					</li>
				</ul>
		    </div>
		    <script>
		    var swiper = new Swiper('.swiper-container1', {
		        slidesPerView: 'auto',
		        paginationClickable: true,
		        spaceBetween: 15
		    });
             </script>
		</div>
		
		<!--栏目名称5-->
		<div class="lm5">
			<div class="lm3-header">
				<span>栏目名称</span>
				<a href="#">更多</a>
			</div>
			<div class="img-text">
				<div class="lm3-img">
					<img src="${path}/resource/img/icons/lm5.png"/>
				</div>
				<div class="lm3-tx">
					<h2>本基金上的法律乐扣乐扣乐扣乐扣klfda费德勒尽快离开家里负担 拉费德勒快快乐乐开放考虑考虑快快乐乐开放考虑考虑快快乐乐开放考虑考</h2>
					<p>2017-06-24&nbsp;05:44</p>
				</div>
			</div>
			<div class="img-text">
				<div class="lm3-img">
					<img src="${path}/resource/img/icons/lm3-2.png"/>
				</div>
				<div class="lm3-tx">
					<h2>本基金上的法律乐扣乐扣乐扣乐扣</h2>
					<p>2017-06-24&nbsp;05:44</p>
				</div>
			</div>
			<div class="img-text">
				<div class="lm3-img">
					<img src="${path}/resource/img/icons/lm3-3.png"/>
				</div>
				<div class="lm3-tx">
					<h2>本基金上的法律乐扣乐扣乐扣乐扣</h2>
					<p>2017-06-24&nbsp;05:44</p>
					
				</div>
			</div>
		</div>
		<!--栏目名称6-->
		<div class="lm6">
			<div class="lm6-header">
				<span>栏目名称</span>
				<a href="#">更多</a>
			</div>
			<div class="lm6-cont">
				<div class="item">
					<div class="lm6-img">
						<img src="${path}/resource/img/icons/lm4-1.png"/>
					</div>
				    <p>清晨阳关</p>
				    <p>清晨阳关加快对方尽快尽快</p>
					
				</div>
				<div class="item">
					<div class="lm6-img">
						<img src="${path}/resource/img/icons/lm4-1.png"/>
					</div>
				    <p>清晨阳关</p>
				    <p>清晨阳关加快对方尽快尽快</p>
					
				</div><div class="item">
					<div class="lm6-img">
						<img src="${path}/resource/img/icons/lm4-1.png"/>
					</div>
				    <p>清晨阳关</p>
				    <p>清晨阳关加快对方尽快尽快</p>
					
				</div><div class="item">
					<div class="lm6-img">
						<img src="${path}/resource/img/icons/lm4-1.png"/>
					</div>
				    <p>清晨阳关</p>
				    <p>清晨阳关加快对方尽快尽快</p>
					
				</div><div class="item">
					<div class="lm6-img">
						<img src="${path}/resource/img/icons/lm4-1.png"/>
					</div>
				    <p>清晨阳关</p>
				    <p>清晨阳关加快对方尽快尽快</p>
					
				</div><div class="item">
					<div class="lm6-img">
						<img src="${path}/resource/img/icons/lm4-1.png"/>
					</div>
				    <p>清晨阳关</p>
				    <p>清晨阳关加快对方尽快尽快</p>
					
				</div><div class="item">
					<div class="lm6-img">
						<img src="${path}/resource/img/icons/lm4-1.png"/>
					</div>
				    <p>清晨阳关</p>
				    <p>清晨阳关加快对方尽快尽快</p>
					
				</div>
			</div>
		</div>
		<div class="lm7">
			<div class="lm6-header">
				<span>栏目名称</span>
				<a href="#">更多</a>
			</div>
			<div class="lm7-cont">
				<div class="item">
					<div class="lm7-img">
						<img src="${path}/resource/img/icons/lm4-1.png"/>
					</div>
				    <p>清晨阳关</p>
				</div>
				<div class="item">
					<div class="lm7-img">
						<img src="${path}/resource/img/icons/lm7.png"/>
					</div>
				    <p>清晨阳关</p>
				</div>
				<div class="item">
					<div class="lm7-img">
						<img src="${path}/resource/img/icons/lm7.png"/>
					</div>
				    <p>清晨阳关</p>
				</div>
				<div class="item">
					<div class="lm7-img">
						<img src="${path}/resource/img/icons/lm7.png"/>
					</div>
				    <p>清晨阳关</p>
				</div>
				<div class="item">
					<div class="lm7-img">
						<img src="${path}/resource/img/icons/lm7.png"/>
					</div>
				    <p>清晨阳关</p>
				</div>
				</div>
		</div>
		<script type="text/javascript">
			
				 var swiper = new Swiper('.swiper-container', {
		        pagination: '.swiper-pagination',
		        autoplay:4000,
		         });
	
		</script>
	</body>

</html>