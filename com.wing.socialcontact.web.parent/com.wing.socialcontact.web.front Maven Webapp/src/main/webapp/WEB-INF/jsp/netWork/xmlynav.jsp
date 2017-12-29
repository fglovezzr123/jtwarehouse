<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="quheye">
		<title>项目联营</title>
		<link rel="stylesheet" type="text/css" href="${path }/resource/css/libs/swiper-3.3.1.min.css" />
		<script src="${path }/resource/js/libs/swiper-3.3.1.min.js" type="text/javascript" charset="utf-8"></script>
		<style type="text/css">
		/*公共样式部分*/
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
				height: 2.06rem;
				display: flex;
				justify-content: space-between;
				align-items: center;
				background: #fff;
			}
			.nav a{
				font-size: .28rem;
				display: flex;
                flex-direction: column;	
                align-items: center;
                flex-grow: 1;	
             	}
             	.nav a img{
             		width: .88rem;
             		height: .88rem;
             		margin-bottom: .20rem;
             	}
             	/*公共样式部分end*/
             	.items{
             		padding-bottom: .90rem;
             	}
     		.item{
     			margin-top: .20rem;
     			width: 100%;
     			padding: 0 .3rem;
     			background: #fff;
     			display: flex;
     			
     		}
     		.item .it-img{
     			width: 2.5rem;
     			padding: 0.15rem 0;
     			position: relative;
     			height: 1.8rem;
     		}
     		.item .it-img img{
     			width: 100%;
     		}
     		.item .it-cont{
     			width: 4.4rem;
     			font-size: .26rem;
     			padding-left: .15rem;
     		}
     		.item .it-cont h3{
     			font-size: .30rem;
     			line-height: .6rem;
     			font-weight: normal;
     			white-space: nowrap;
			    overflow: hidden;
			    text-overflow: ellipsis;
     		}
     		.item .it-cont .redu{
     			width: 100%;
     			height: .30rem;
     			display: flex;
     			align-items: center;
     			color: #808080;
     			margin-top: .08rem;
     		}
     		.item .it-cont .progress{
     			width: 2.5rem;
     			height: .1rem;
     			background: #ececec;
     			border-radius: .1rem;
     			margin-left: .1rem;
     		}
     		.item .it-cont .progress .progress-bar{
     			height: .1rem;
     			background: #08ffeb;
     			width: 50%;
     			border-bottom-left-radius: 0.5rem;
     			border-top-left-radius: 0.5rem;
     		}
     		.item .it-cont .bot{
     			display: flex;
     			justify-content: space-between;
     			margin-top: .15rem;
     		}
     		.item .it-cont .bot span{
     			line-height: 2.5;
     		}
     		.item .it-cont .bot span:nth-of-type(2){
     			background: url(${path }/resource/img/icons/gz.png) no-repeat center;
     			background-size: .25rem;
     			padding-left: .35rem;
     			background-position-x: 0;	
     		}
     		.item .it-cont .bot span:nth-last-of-type(1){
     			width: 1.30rem;
     			height: .50rem;
     			background: #0F88EB;
     			border-radius: .1rem;
     			font-size: .24rem;
     			color: #fff;
     			text-align: center;
     			line-height: .50rem;
     		}
		</style>
	</head>
	<body>
		   <div class="search-box">
				<div id="search">搜索动态</div>
			</div> 
		<!--公共区域banner和nav快速导航-->
		<div class="swiper-container banner" id="banner">
			<ul class="swiper-wrapper banner_ul">
				<li class="swiper-slide"><img src="${path }/resource/img/icons/qhbanner.png"" /></li>
				<li class="swiper-slide"><img src="${path }/resource/img/images/banner/bannerPk_02.png"" /></li>
				<li class="swiper-slide"><img src="${path }/resource/img/icons/qhbanner.png"" /></li>
				<li class="swiper-slide"><img src="${path }/resource/img/images/banner/bannerPk_02.png"" /></li>
			</ul>
			<div class="swiper-pagination"></div>
		</div>
		<div class="nav">
			<a href="#">
				<img src="${path }/resource/img/icons/msjs.png"/>
				<span>模式介绍</span>
			</a>
			<a href="#">
				<img src="${path }/resource/img/icons/cgal.png"/>
				<span>成功案例</span>
			</a>
			<a href="#">
				<img src="${path }/resource/img/icons/hzlc.png"/>
				<span>合作流程</span>
			</a>
		</div>
		<!--公共区域banner和nav快速导航                       end-->
		
		<div class="items">
			<div class="item">
				<div class="it-img">
					<img src="${path }/resource/img/icons/ly2.png"/>
					<!--精品图片-->
					<!--<img src="${path }/resource/img/icons/jp.png" class="jpIcon"/>-->
					
				</div>
				<div class="it-cont">
					<h3>梦想还是要有的</h3>
					<div class="redu">
						<span>热度50%</span>
						<div class="progress">
							<div class="progress-bar"></div>
						</div>
					</div>
					<div class="bot">
						<span>收藏(2017)</span>
						<span>收藏</span>
						<span>立即进入</span>
					</div>
				</div>
			</div>
			
		</div>
		<div class="in-cz">
	                  项目自荐
	      </div>
		<script type="text/javascript">
			
				 var swiper = new Swiper('.swiper-container', {
		        pagination: '.swiper-pagination',
		        autoplay:4000,
		         });
	
		</script>
	</body>

</html>